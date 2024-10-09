package com.example.caesarx;

import java.io.*;

public class FileHandler {
    public static void encryptFile(String inputFile, String outputFile, int shift, String language) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(CaesarCipher.encrypt(line, shift, language));
                writer.newLine();
            }
        }
    }

    public static void decryptFile(String inputFile, String outputFile, int shift, String language) throws IOException {
        encryptFile(inputFile, outputFile, CaesarCipher.ALPHABETS.get(language).length() - shift, language);
    }
}

