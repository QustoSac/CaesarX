package com.example.caesarx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("caesar_cipher.fxml"));
        Parent root = loader.load();

        CaesarCipherController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("CaesarX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
