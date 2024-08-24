package br.cefetmg.gestaoEntregas.viewdesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public Scene getScene(String sceneName) throws IOException {
        assert sceneName != null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneName));

        return new Scene(fxmlLoader.load());
    }

    public void showScene(Stage stage, String sceneName, String sceneTitle) throws IOException {
        Scene scene = getScene(sceneName);

        stage.setTitle(sceneTitle);
        stage.setScene(scene);
        stage.show();
    }

    public Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}
