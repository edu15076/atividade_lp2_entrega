package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.viewdesktop.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import br.cefetmg.gestaoEntregas.controllers.LoginController;

import java.io.IOException;


// É uma ideia de como implementar essa questão dos menus
public class MenuController {
    @FXML
    private Button sairButton;

    public void onHandleSairButton(ActionEvent event) throws IOException {
        LoginController.logout();
        SceneManager sceneManager = new SceneManager();
        sceneManager.showScene(sceneManager.getStage(event), "login-scene.fxml", "Entrar");
    }
}
