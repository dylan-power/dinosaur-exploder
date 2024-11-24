package com.dinosaur.dinosaurexploder.view;

public class LanguageManager {
    private static String selectedLanguage;

    // Setter to set the selected language
    public static void setSelectedLanguage(String language) {
        selectedLanguage = language;
    }

    // Getter to retrieve the selected language
    public static String getSelectedLanguage() {
        return selectedLanguage;
    }
}
