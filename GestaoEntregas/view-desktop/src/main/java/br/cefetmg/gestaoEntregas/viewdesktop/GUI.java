package br.cefetmg.gestaoEntregas.viewdesktop;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = new SceneManager();
        sceneManager.showScene(stage, "login-scene.fxml", "Desenvolvimento");
    }

    public static void main(String[] args) {
        launch();
    }
}
