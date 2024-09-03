package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.viewdesktop.SceneManager;
import br.cefetmg.gestaoEntregas.viewdesktop.controllers.interfaces.MenuControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import br.cefetmg.gestaoEntregas.controllers.LoginController;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements MenuControllerInterface, Initializable {
    @FXML
    private MenuItem funcionariosMenuItem;

    @FXML
    private MenuItem pedidosMenuItem;

    @FXML
    private MenuItem produtosMenuItem;

    @FXML
    private MenuItem clientesMenuItem;

    @FXML
    private MenuItem perfilMenuItem;

    // TODO: Adicionar telas de relatorios, pedidos do entregador (que permite entregador pegar pedido), tela de detalhes de pedidos (pop-up)


    private SceneManager sceneManager;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneManager = new SceneManager(); // Initialize the SceneManager instance
        funcionariosMenuItem.setOnAction(this::handleMenuAction);
        pedidosMenuItem.setOnAction(this::handleMenuAction);
        produtosMenuItem.setOnAction(this::handleMenuAction);
        clientesMenuItem.setOnAction(this::handleMenuAction);
        perfilMenuItem.setOnAction(this::handleMenuAction);
    }

    public void handleMenuAction(ActionEvent event) {
        String fxmlFile = "";
        String title = "";
        Stage stage = sceneManager.getStageFromMenuItem((MenuItem) event.getSource());

        if (event.getSource() == funcionariosMenuItem) {
            fxmlFile = "funcionarios-scene.fxml";
            title = "Funcionários";
        } else if (event.getSource() == pedidosMenuItem) {
            fxmlFile = "pedidos-scene.fxml";
            title = "Pedidos";
        } else if (event.getSource() == clientesMenuItem) {
            fxmlFile = "clientes-scene.fxml";
            title = "Clientes";
        } else if (event.getSource() == produtosMenuItem) {
            fxmlFile = "produtos-scene.fxml";
            title = "Produtos";
        } else if (event.getSource() == perfilMenuItem) {
            fxmlFile = "editar-perfil-scene.fxml";
            title = "Perfil";
            stage = new Stage();
        }

        try {
            sceneManager.showScene(stage, fxmlFile, title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onHandleSairButton(ActionEvent event) throws IOException {
        LoginController.logout();
        SceneManager sceneManager = new SceneManager();
        sceneManager.showScene(sceneManager.getStage(event), "login-scene.fxml", "Entrar");
    }
}
