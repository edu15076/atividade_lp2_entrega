package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.ClienteController;
import br.cefetmg.gestaoEntregas.controllers.LoginController;
import br.cefetmg.gestaoEntregas.controllers.ProdutoController;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Cliente;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import br.cefetmg.gestaoEntregas.viewdesktop.controllers.interfaces.MenuControllerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// TODO resolver problema de dependencias
import br.cefetmg.gestaoEntregas.entidades.Produto;
import javafx.scene.control.cell.PropertyValueFactory;
import net.synedra.validatorfx.Validator;

public class ProdutosController extends MenuController implements Initializable {
    @FXML
    public TableColumn<Produto, String> nomeColumn;

    @FXML
    public TableColumn<Produto, String> codigoColumn;

    @FXML
    public TableColumn<Produto, String> localizacaoColumn;

    @FXML
    private Button cadastrarButton;

    @FXML
    private TextField codigoTextField;

    @FXML
    private TextField localizacaoTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TableView<Produto> tabelaTableView;

    private ProdutoController produtoController;
    private Validator validator;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        try {
            produtoController = new ProdutoController();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        localizacaoColumn.setCellValueFactory(new PropertyValueFactory<>("localizacao"));

        // Validadores
        validator = new Validator();

        validator.createCheck()
                .withMethod(c -> {
                    String nome = c.get("nome");
                    if (nome == null || nome.trim().isEmpty()) {
                        c.error("Campo obrigatório");
                    }
                })
                .dependsOn("nome", nomeTextField.textProperty())
                .decorates(nomeTextField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String codigo = c.get("codigo");
                    if (codigo == null || codigo.trim().isEmpty()) {
                        c.error("Campo obrigatório");
                    }
                })
                .dependsOn("codigo", codigoTextField.textProperty())
                .decorates(codigoTextField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String localizacao = c.get("localizacao");
                    if (localizacao == null || localizacao.trim().isEmpty()) {
                        c.error("Campo obrigatório");
                    }
                })
                .dependsOn("localizacao", localizacaoTextField.textProperty())
                .decorates(localizacaoTextField)
                .immediate();

        loadProdutos();
    }

    @FXML
    public void handleCadastro(ActionEvent event) {
        if (!validator.validate()) return;

        String nome = nomeTextField.getText();
        String localizacao = localizacaoTextField.getText();
        String codigo = codigoTextField.getText();

        if (nome.isEmpty() || localizacao.isEmpty()) {
            System.out.println("Preencha todos os campos.");
            return;
        }

        Funcionario funcionarioLogado = br.cefetmg.gestaoEntregas.controllers.LoginController.getFuncionarioLogado();
        if (funcionarioLogado == null) {
            System.out.println("Usuário não está logado.");
            return;
        }

        Empresa empresa = funcionarioLogado.getEmpresa();

        try {
            Produto newProduto = produtoController.cradastrar(nome, codigo, localizacao, empresa);
            System.out.println("Produto cadastrado com sucesso: " + newProduto.getNome());

            loadProdutos();
        } catch (AtributoInvalidoException | DAOException e) {
            e.printStackTrace();
        }
    }

    public void loadProdutos() {
        try {
            Funcionario funcionarioLogado = LoginController.getFuncionarioLogado();
            List<Produto> produtos = produtoController.listarProdutosEmpresa(funcionarioLogado.getEmpresa());

            ObservableList<Produto> produtoObservableList = FXCollections.observableArrayList(produtos);
            tabelaTableView.setItems(produtoObservableList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
