package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.viewdesktop.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientesController {

    @FXML
    private Button cadastrarButton;

    @FXML
    private TextField codigoTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TableView<?> tabelaTableView;

    @FXML
    private TextField telefoneTextField;

    @FXML
    public void teste(ActionEvent event) {
        SceneManager sceneManager = new SceneManager();
        Stage stage = sceneManager.getStage(event);
        try {
            sceneManager.showScene(stage, "funcionarios-scene.fxml", "Desenvolvimento");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
