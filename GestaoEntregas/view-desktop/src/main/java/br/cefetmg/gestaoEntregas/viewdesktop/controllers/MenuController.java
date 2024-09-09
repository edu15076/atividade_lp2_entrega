package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.viewdesktop.SceneManager;
import br.cefetmg.gestaoEntregas.viewdesktop.controllers.interfaces.MenuControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import br.cefetmg.gestaoEntregas.controllers.LoginController;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;
import java.awt.*;
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

        try {
            if (event.getSource() == funcionariosMenuItem) {
                fxmlFile = "funcionarios-scene.fxml";
                title = "Funcionários";
                LoginController.checkLoginOrException(new TipoPerfil[] {TipoPerfil.ADMINISTRADOR, TipoPerfil.ATENDENTE});
            } else if (event.getSource() == pedidosMenuItem) {
                fxmlFile = "pedidos-scene.fxml";
                title = "Pedidos";
                LoginController.checkLoginOrException(new TipoPerfil[] {TipoPerfil.ADMINISTRADOR, TipoPerfil.ATENDENTE});
            } else if (event.getSource() == clientesMenuItem) {
                fxmlFile = "clientes-scene.fxml";
                title = "Clientes";
                LoginController.checkLoginOrException(new TipoPerfil[] {TipoPerfil.ADMINISTRADOR, TipoPerfil.ATENDENTE});
            } else if (event.getSource() == produtosMenuItem) {
                fxmlFile = "produtos-scene.fxml";
                title = "Produtos";
                LoginController.checkLoginOrException(new TipoPerfil[] {TipoPerfil.ADMINISTRADOR, TipoPerfil.ATENDENTE});
            } else if (event.getSource() == perfilMenuItem) {
                fxmlFile = "editar-perfil-scene.fxml";
                title = "Perfil";
                stage = new Stage();
            }

            sceneManager.showScene(stage, fxmlFile, title);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            alert.setContentText("Error: " + e.getMessage());
            alert.show();
        }
    }

    public void onHandleSairButton(ActionEvent event) throws IOException {
        LoginController.logout();
        SceneManager sceneManager = new SceneManager();
        sceneManager.showScene(sceneManager.getStage(event), "login-scene.fxml", "Entrar");
    }
}
