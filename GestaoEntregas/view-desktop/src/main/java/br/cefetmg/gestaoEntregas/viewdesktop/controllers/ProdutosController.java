package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.viewdesktop.controllers.interfaces.MenuControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

// TODO resolver problema de dependencias
//import br.cefetmg.gestaoEntregas.entidades.Produto;

public class ProdutosController implements MenuControllerInterface {
    @FXML
    private Button cadastrarButton;

    @FXML
    private TextField localizacaoTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TableView<?> tabelaTableView;

    private MenuController menuController;

//    private ObservableList<Produto> produtos = FXCollections.observableArrayList();

    public void initialize() {
        menuController = new MenuController();
//        tabelaTableView.setItems(produtos);
    }

    public MenuController getMenuController() {
        return menuController;
    }

    @Override
    public void onHandleSairButton(ActionEvent event) throws IOException {
        menuController.onHandleSairButton(event);
    }
}
