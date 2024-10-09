package com.example.caesarx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CaesarCipherController {

    @FXML
    private TextArea inputText;

    @FXML
    private TextField shiftKey;

    @FXML
    private TextArea resultText;

    @FXML
    private TextField inputFilePath;

    @FXML
    private TextField outputFilePath;

    @FXML
    private ComboBox<String> languageSelector;

    @FXML
    public void initialize() {
        languageSelector.getItems().addAll("English", "Русский");
        languageSelector.setValue("English"); // Установим английский как язык по умолчанию
    }

    @FXML
    public void encryptText() {
        try {
            String text = inputText.getText();
            int shift = Integer.parseInt(shiftKey.getText());
            String language = languageSelector.getValue();
            String encrypted = CaesarCipher.encrypt(text, shift, language);
            resultText.setText(encrypted);
        } catch (NumberFormatException e) {
            resultText.setText("Неверный ключ. Пожалуйста, введите номер");
        }
    }

    @FXML
    public void decryptText() {
        try {
            String text = inputText.getText();
            int shift = Integer.parseInt(shiftKey.getText());
            String language = languageSelector.getValue();
            String decrypted = CaesarCipher.decrypt(text, shift, language);
            resultText.setText(decrypted);
        } catch (NumberFormatException e) {
            resultText.setText("Неверный ключ. Пожалуйста, введите номер");
        }
    }

    @FXML
    public void encryptFile() {
        try {
            String inputFile = inputFilePath.getText();
            String outputFile = outputFilePath.getText();
            int shift = Integer.parseInt(shiftKey.getText());
            String language = languageSelector.getValue();

            FileHandler.encryptFile(inputFile, outputFile, shift, language);
            resultText.setText("Файл успешно зашифрован");
        } catch (IOException e) {
            resultText.setText("Ошибка при обработке файла: " + e.getMessage());
        } catch (NumberFormatException e) {
            resultText.setText("Неверный ключ. Пожалуйста, введите номер");
        }
    }

    @FXML
    public void decryptFile() {
        try {
            String inputFile = inputFilePath.getText();
            String outputFile = outputFilePath.getText();
            int shift = Integer.parseInt(shiftKey.getText());
            String language = languageSelector.getValue();

            FileHandler.decryptFile(inputFile, outputFile, shift, language);
            resultText.setText("Файл успешно расшифрован");
        } catch (IOException e) {
            resultText.setText("Ошибка при обработке файла: " + e.getMessage());
        } catch (NumberFormatException e) {
            resultText.setText("Неверный ключ. Пожалуйста, введите номер");
        }
    }
}
