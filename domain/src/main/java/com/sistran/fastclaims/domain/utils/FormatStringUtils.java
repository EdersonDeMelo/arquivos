package com.sistran.fastclaims.domain.utils;

public class FormatStringUtils {

    public static String formatString(String input) {
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                result.append(word.substring(1));
            }
        }

        return result.toString();
    }
}
