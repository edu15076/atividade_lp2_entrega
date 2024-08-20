package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

// TODO resolver problema de dependencias
//import br.cefetmg.gestaoEntregas.entidades.Produto;

public class ProdutosController {
    @FXML
    private Button cadastrarButton;

    @FXML
    private TextField localizacaoTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TableView<?> tabelaTableView;

//    private ObservableList<Produto> produtos = FXCollections.observableArrayList();

    public void initialize() {
//        tabelaTableView.setItems(produtos);
    }
}
