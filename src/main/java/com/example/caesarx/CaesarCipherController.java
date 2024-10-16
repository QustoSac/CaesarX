package com.example.caesarx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

public class CaesarCipherController {

    @FXML
    private TextArea inputTextEncrypt;

    @FXML
    private TextArea inputTextDecrypt;

    @FXML
    private ComboBox<Integer> shiftKeyEncrypt;

    @FXML
    private ComboBox<Integer> shiftKeyDecrypt;

    @FXML
    private TextArea resultTextEncrypt;

    @FXML
    private TextArea resultTextDecrypt;

    @FXML
    private TextField inputFilePathEncrypt;

    @FXML
    private TextField outputFilePathEncrypt;

    @FXML
    private TextField inputFilePathDecrypt;

    @FXML
    private TextField outputFilePathDecrypt;

    @FXML
    private ComboBox<String> languageSelectorEncrypt;

    @FXML
    private ComboBox<String> languageSelectorDecrypt;

    @FXML
    private RadioButton knownKeyOption;

    @FXML
    private RadioButton bruteForceOption;


    private Stage stage;

    private static final String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String RUSSIAN_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";



    @FXML
    public void initialize() {

        ToggleGroup toggleGroup = new ToggleGroup();
        knownKeyOption.setToggleGroup(toggleGroup);
        bruteForceOption.setToggleGroup(toggleGroup);

        bruteForceOption.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                shiftKeyDecrypt.setVisible(false);
            } else {
                shiftKeyDecrypt.setVisible(true);
            }
        });
        languageSelectorEncrypt.getItems().addAll("English", "Русский");
        languageSelectorEncrypt.setValue("English");
        updateShiftKeyEncrypt();

        languageSelectorDecrypt.getItems().addAll("English", "Русский");
        languageSelectorDecrypt.setValue("English");
        updateShiftKeyDecrypt();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void updateShiftKeyEncrypt() {
        String language = languageSelectorEncrypt.getValue();
        shiftKeyEncrypt.getItems().clear();
        int alphabetLength = getAlphabetLength(language);
        shiftKeyEncrypt.getItems().addAll(IntStream.range(0, alphabetLength).boxed().toArray(Integer[]::new));
        shiftKeyEncrypt.setValue(0);
    }

    @FXML
    public void updateShiftKeyDecrypt() {
        String language = languageSelectorDecrypt.getValue();
        shiftKeyDecrypt.getItems().clear();
        int alphabetLength = getAlphabetLength(language);
        shiftKeyDecrypt.getItems().addAll(IntStream.range(0, alphabetLength).boxed().toArray(Integer[]::new));
        shiftKeyDecrypt.setValue(0);
    }

    private int getAlphabetLength(String language) {
        switch (language) {
            case "Русский":
                return RUSSIAN_ALPHABET.length();
            case "English":
            default:
                return ENGLISH_ALPHABET.length();
        }
    }

    @FXML
    public void encryptText() {
        try {
            String text = inputTextEncrypt.getText();
            int shift = shiftKeyEncrypt.getValue();
            String language = languageSelectorEncrypt.getValue();
            String encrypted = CaesarCipher.encrypt(text, shift, language);
            resultTextEncrypt.setText(encrypted);
        } catch (NumberFormatException e) {
            resultTextEncrypt.setText("Ошибка при выборе ключа");
        }
    }

    public void decryptText() {
        String text = inputTextDecrypt.getText();
        String language = languageSelectorDecrypt.getValue(); // Получаем выбранный язык

        if (knownKeyOption.isSelected()) {
            try {
                int shift = shiftKeyDecrypt.getValue();
                String decrypted = CaesarCipher.decrypt(text, shift, language); // Передаем язык как аргумент
                resultTextDecrypt.setText(decrypted);
            } catch (NumberFormatException e) {
                resultTextDecrypt.setText("Неверный ключ. Пожалуйста, выберите номер.");
            }
        } else if (bruteForceOption.isSelected()) {
            String bruteForceResult = bruteForceDecrypt(text, language); // Передаем язык в brute force
            resultTextDecrypt.setText(bruteForceResult);
        }
    }

    private String bruteForceDecrypt(String text, String language) {
        StringBuilder results = new StringBuilder();

        int alphabetLength = getAlphabetLength(language);
        for (int i = 0; i < alphabetLength; i++) {
            String decryptedAttempt = CaesarCipher.decrypt(text, i, language);
            results.append("Ключ: ").append(i).append("\n")
                    .append("Текст: ").append(decryptedAttempt).append("\n\n");
        }

        return results.toString();
    }

    @FXML
    public void encryptFile() {
        try {
            String inputFile = inputFilePathEncrypt.getText();
            String outputFile = outputFilePathEncrypt.getText();
            int shift = shiftKeyEncrypt.getValue();
            String language = languageSelectorEncrypt.getValue();

            FileHandler.encryptFile(inputFile, outputFile, shift, language);
            resultTextEncrypt.setText("Файл успешно зашифрован");
        } catch (IOException e) {
            resultTextEncrypt.setText("Ошибка при обработке файла: " + e.getMessage());
        }
    }

    @FXML
    public void decryptFile() {
        try {
            String inputFile = inputFilePathDecrypt.getText();
            String outputFile = outputFilePathDecrypt.getText();
            int shift = shiftKeyDecrypt.getValue();
            String language = languageSelectorDecrypt.getValue();

            FileHandler.decryptFile(inputFile, outputFile, shift, language);
            resultTextDecrypt.setText("Файл успешно расшифрован");
        } catch (IOException e) {
            resultTextDecrypt.setText("Ошибка при обработке файла: " + e.getMessage());
        }
    }

    @FXML
    public void chooseInputFileEncrypt() { /*...*/ }

    @FXML
    public void chooseOutputFileEncrypt() { /*...*/ }

    @FXML
    public void chooseInputFileDecrypt() { /*...*/ }

    @FXML
    public void chooseOutputFileDecrypt() { /*...*/ }
}
