package br.cefetmg.gestaoEntregas.viewdesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public Scene getScene(String sceneName) throws IOException {
        assert sceneName != null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneName));
        return new Scene(fxmlLoader.load());
    }

    public Stage getStageFromMenuItem(MenuItem menuItem) {
        Node parentNode = menuItem.getParentPopup().getOwnerNode();
        if (parentNode == null)
            throw new IllegalStateException("Cannot find the parent node for the MenuItem.");

        Scene scene = parentNode.getScene();
        if (scene == null)
            throw new IllegalStateException("Cannot find the scene for the MenuItem.");

        return (Stage) scene.getWindow();
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
