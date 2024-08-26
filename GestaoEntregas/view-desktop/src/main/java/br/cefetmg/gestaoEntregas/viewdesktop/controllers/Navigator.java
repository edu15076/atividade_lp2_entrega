package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Navigator {
    private static Navigator instance;
    private final Stage stage;

    private Navigator(Stage stage) {
        this.stage = stage;
    }

    public static Navigator getInstance(Stage stage) {
        if (instance == null)
            instance = new Navigator(stage);
        return instance;
    }

    public void navigate(String fxml) throws IOException {
        URL location = getClass().getResource("/br/cefetmg/gestaoEntregas/viewdesktop/" + fxml);
        if (location == null)
            throw new IOException("FXML file not found: " + fxml);
        FXMLLoader loader = new FXMLLoader(location);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
