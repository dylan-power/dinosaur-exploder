package com.dinosaur.dinosaurexploder.view;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Map;

public class LanguageManager {
    private static String selectedLanguage;
    private static final String TRANSLATION_PATH = "/assets/translation/";


    // Setter to set the selected language
    public static void setSelectedLanguage(String language) {
        selectedLanguage = language;
    }

    // Getter to retrieve the selected language
    public static String getSelectedLanguage() {
        return selectedLanguage;
    }

    public static Map<String, String> loadTranslations(String language) {
        try {
            String filePath = TRANSLATION_PATH + language.toLowerCase() + ".json";

            // Load JSON file as InputStream
            InputStream inputStream = LanguageManager.class.getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new RuntimeException("Translation file not found: " + filePath);
            }

            // Parse JSON into a Map
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of(); // Return empty map if error occurs
        }
    }
}
