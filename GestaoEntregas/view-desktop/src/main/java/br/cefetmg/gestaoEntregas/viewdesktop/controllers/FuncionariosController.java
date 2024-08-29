package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.FuncionarioController;
import br.cefetmg.gestaoEntregas.controllers.LoginController;
import br.cefetmg.gestaoEntregas.controllers.PerfilController;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.Perfil;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FuncionariosController extends MenuController {

    @FXML
    private TextField nomeTextField;

    @FXML
    private PasswordField senhaPasswordField;

    @FXML
    private TextField telefoneTextField;

    @FXML
    private ComboBox<String> perfilComboBox;

    @FXML
    private Button cadastrarButton;

    @FXML
    private Label tituloLabel;

    @FXML
    private TableView<FuncionarioExibition> tabelaTableView;

    @FXML
    private TableColumn<FuncionarioExibition, String> nomeColumn;

    @FXML
    private TableColumn<FuncionarioExibition, String> perfilColumn;

    @FXML
    private TableColumn<FuncionarioExibition, String> telefoneColumn;

    private FuncionarioController funcionarioController;
    private PerfilController perfilController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        perfilComboBox.getItems().addAll("ADMINISTRADOR", "ATENDENTE", "ENTREGADOR");
        cadastrarButton.setOnAction(event -> handleCadastrarButtonClick());

        try {
            funcionarioController = new FuncionarioController();
            perfilController = new PerfilController();
        } catch (Exception e) {
            e.printStackTrace();
        }

        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        perfilColumn.setCellValueFactory(new PropertyValueFactory<>("perfil"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        loadFuncionarios();
    }

    private void handleCadastrarButtonClick() {
        String nome = nomeTextField.getText();
        String senha = senhaPasswordField.getText();
        String telefone = telefoneTextField.getText();
        String perfil = perfilComboBox.getValue();

        if (nome.isEmpty() || senha.isEmpty() || telefone.isEmpty() || perfil == null) {
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
            Perfil newPerfil = perfilController.criar(nome, senha, telefone, empresa, TipoPerfil.valueOf(perfil));
            System.out.println("Funcionario cadastrado com sucesso: " + newPerfil.getFuncionario().getNome());

            loadFuncionarios();
        } catch (AtributoInvalidoException | DAOException e) {
            e.printStackTrace();
        }
    }

    private void loadFuncionarios() {
        try {
            Funcionario funcionarioLogado = LoginController.getFuncionarioLogado();
            List<Funcionario> funcionarios = funcionarioController.consultarFuncionarios(funcionarioLogado.getEmpresa());

            ObservableList<FuncionarioExibition> funcionariosList = FXCollections.observableArrayList(new FuncionarioExibition().createFuncionariosExibition(funcionarios));
            tabelaTableView.setItems(funcionariosList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public static class FuncionarioExibition {
        public final String nome, telefone, perfil;

        public FuncionarioExibition() {
            nome = telefone = perfil = null;
        }

        public FuncionarioExibition(String nome, String telefone, String perfil) {
            this.nome = nome;
            this.telefone = telefone;
            this.perfil = perfil;
        }

        public String getNome() {
            return nome;
        }

        public String getTelefone() {
            return telefone;
        }

        public String getPerfil() {
            return perfil;
        }

        public List<FuncionarioExibition> createFuncionariosExibition(List<Funcionario> funcionarios) {
            List<FuncionarioExibition> funcionarioExibitions = new ArrayList<>();
            for (Funcionario funcionario : funcionarios)
                for (Perfil perfil : funcionario.getPerfis())
                    funcionarioExibitions.add(new FuncionarioExibition(funcionario.getNome(),
                            funcionario.getTelefone(), perfil.getTipoPerfil().toString()));
            return funcionarioExibitions;
        }
    }
}
