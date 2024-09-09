package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.ClienteController;
import br.cefetmg.gestaoEntregas.controllers.LoginController;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Cliente;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.Perfil;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import br.cefetmg.gestaoEntregas.viewdesktop.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientesController extends MenuController implements Initializable {
    @FXML
    public TextField bairroTextField;

    @FXML
    public TextField logradouroTextField;

    @FXML
    public TableColumn<Cliente, String> codigoColumn;

    @FXML
    public TableColumn<Cliente, String> nomeColumn;

    @FXML
    public TableColumn<Cliente, String> telefoneColumn;

    @FXML
    public TableColumn<Cliente, String> enderecoColumn;

    @FXML
    private Button cadastrarButton;

    @FXML
    private TextField codigoTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TableView<Cliente> tabelaTableView;

    @FXML
    private TextField telefoneTextField;

    private ClienteController clienteController;

    private Validator validator;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        try {
            clienteController = new ClienteController();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        validator = new Validator();

        validator.createCheck()
                .withMethod(c -> {
                    String nome = c.get("nome");
                    if (nome == null || nome.trim().isEmpty() || nome.length() < 3) {
                        c.error("Campo obrigatório e mínimo de 3 caracteres");
                    }
                })
                .dependsOn("nome", nomeTextField.textProperty())
                .decorates(nomeTextField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String telefone = c.get("telefone");
                    if (telefone == null || telefone.trim().isEmpty()) {
                        c.error("Campo obrigatório e formato de telefone");
                    }
                })
                .dependsOn("telefone", telefoneTextField.textProperty())
                .decorates(telefoneTextField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String bairro = c.get("bairro");
                    if (bairro == null || bairro.trim().isEmpty() || bairro.length() < 3) {
                        c.error("Campo obrigatório e mínimo de 3 caracteres");
                    }
                })
                .dependsOn("bairro", bairroTextField.textProperty())
                .decorates(bairroTextField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String logradouro = c.get("logradouro");
                    if (logradouro == null || logradouro.trim().isEmpty() || logradouro.length() < 5) {
                        c.error("Campo obrigatório e mínimo de 5 caracteres");
                    }
                })
                .dependsOn("logradouro", logradouroTextField.textProperty())
                .decorates(logradouroTextField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String codigo = c.get("codigo");
                    if (codigo == null || codigo.trim().isEmpty() || (codigo.length() != 11 && codigo.length() != 14)) {
                        c.error("Campo obrigatório e formato de código");
                    }
                })
                .dependsOn("codigo", codigoTextField.textProperty())
                .decorates(codigoTextField)
                .immediate();

        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        loadClientes();
    }

    @FXML
    public void handleCadastro(ActionEvent event) {
        if (!validator.validate()) return;

        String nome = nomeTextField.getText();
        String codigo = codigoTextField.getText();
        String telefone = telefoneTextField.getText();
        String bairro = bairroTextField.getText();
        String logradouro = logradouroTextField.getText();

        if (nome.isEmpty() || codigo.isEmpty() || telefone.isEmpty() || bairro.isEmpty() || logradouro.isEmpty()) {
            System.out.println("Preencha todos os campos.");
            return;
        }

        Funcionario funcionarioLogado = LoginController.getFuncionarioLogado();
        if (funcionarioLogado == null) {
            System.out.println("Usuário não está logado.");
            return;
        }

        Empresa empresa = funcionarioLogado.getEmpresa();

        try {
            Cliente newCliente;
            if (codigo.length() == 11)
                newCliente = clienteController.cadastrar(nome, logradouro, bairro, telefone, null, codigo, empresa);
            else
                newCliente = clienteController.cadastrar(nome, logradouro, bairro, telefone, codigo, null, empresa);
            System.out.println("Cliente cadastrado com sucesso: " + newCliente.getNome());

            loadClientes();
        } catch (AtributoInvalidoException | DAOException e) {
            e.printStackTrace();
        }
    }

    public void loadClientes() {
        try {
            Funcionario funcionarioLogado = LoginController.getFuncionarioLogado();
            List<Cliente> clientes = clienteController.consultarClientes(funcionarioLogado.getEmpresa());

            ObservableList<Cliente> clientesList = FXCollections.observableArrayList(clientes);
            tabelaTableView.setItems(clientesList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
