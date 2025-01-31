package com.dinosaur.dinosaurexploder.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LanguageManager {
    // JavaFX property for automatic UI updates
    private final StringProperty selectedLanguage = new SimpleStringProperty("English");
    private final String TRANSLATION_PATH = "/assets/translation/";
    private Map<String, String> translations = new HashMap<>();

    private static LanguageManager instance;

    // Private constructor to prevent instantiation
    private LanguageManager() {}

    // Get the singleton instance
    public static synchronized LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    // Setter for selected language
    public void setSelectedLanguage(String language) {
        // First, load the translations for the new language
        translations = loadTranslations(language);

        // Then, update the property (this triggers UI updates)
        selectedLanguage.set(language);
    }

    // Getter for selected language
    public String getSelectedLanguage() { return selectedLanguage.get(); }

    public StringProperty selectedLanguageProperty() {
        return selectedLanguage;
    }

    public List<String> getAvailableLanguages() {
        List<String> languages = new ArrayList<>();
        try {
            ClassLoader classLoader = LanguageManager.class.getClassLoader();
            InputStream resourceStream = classLoader.getResourceAsStream("assets/translation/");

            if (resourceStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream))) {
                    languages = reader.lines()
                            .filter(line -> line.endsWith(".json"))
                            .map(line -> {
                                String out = line.replace(".json", "");
                                return out.substring(0,1).toUpperCase() + out.substring(1);
                            })
                            .collect(Collectors.toList());
                }
            } else {
                // Running from a JAR, list resources differently
                languages = getLanguagesFromJar();
            }
        } catch (Exception e) {
            System.err.println("Error retrieving available languages: " + e.getMessage());
        }
        return languages;
    }

    /***
     * TRANSLATION_PATH refers to a directory inside the resources folder,
     * and getResource only works reliably for files, not directories in a packaged JAR.
     * getAvailableLanguages() tries to read files using getResourceAsStream().
     * If resourceStream is null, assumes it's running inside a JAR and uses ZipInputStream to read .json files
     * getLanguagesFromJar() Reads the JAR contents to list available language files
     */
    private List<String> getLanguagesFromJar() throws IOException {
        List<String> languages = new ArrayList<>();
        String path = TRANSLATION_PATH.substring(1); // Remove leading slash

        try (InputStream jarStream = LanguageManager.class.getProtectionDomain().getCodeSource().getLocation().openStream();
             ZipInputStream zip = new ZipInputStream(jarStream)) {

            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().startsWith(path) && entry.getName().endsWith(".json")) {
                    languages.add(entry.getName().replace(path, "").replace(".json", ""));
                }
            }
        }
        return languages;
    }

    // Return the translations
    public Map<String, String> loadTranslations(String language) {
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

    // Get a translated string by key
    public String getTranslation(String key) {
        return translations.getOrDefault(key, key); // Default to key if translation not found
    }
}
