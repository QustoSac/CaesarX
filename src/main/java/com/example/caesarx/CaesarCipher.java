package com.example.caesarx;

import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {
    public static final String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String RUSSIAN_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

    public static final Map<String, String> ALPHABETS = new HashMap<>();

    static {
        ALPHABETS.put("English", ENGLISH_ALPHABET);
        ALPHABETS.put("Русский", RUSSIAN_ALPHABET);
    }

    public static String encrypt(String text, int shift, String language) {
        String alphabet = ALPHABETS.get(language);
        if (alphabet == null) {
            throw new IllegalArgumentException("Неподдерживаемый язык: " + language);
        }
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int idx = alphabet.indexOf(Character.toUpperCase(c));

            if (idx != -1) {
                char shiftedChar = alphabet.charAt((idx + shift) % alphabet.length());
                result.append(Character.isLowerCase(c) ? Character.toLowerCase(shiftedChar) : shiftedChar);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static String decrypt(String text, int shift, String language) {
        String alphabet = ALPHABETS.get(language);
        return encrypt(text, alphabet.length() - shift, language);
    }
}
