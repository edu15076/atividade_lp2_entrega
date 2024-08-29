package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import br.cefetmg.gestaoEntregas.viewdesktop.SceneManager;
import br.cefetmg.gestaoEntregas.controllers.LoginController;
import br.cefetmg.gestaoEntregas.controllers.FuncionarioController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarPerfilController implements Initializable {
    @FXML
    public TextField nomeTextField;

    @FXML
    public TextField numeroTextField;

    @FXML
    public TextField senhaTextField;

    @FXML
    public Button cancelarButton;

    @FXML
    public Button salvarButton;

    private SceneManager sceneManager;
    private FuncionarioController funcionarioController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneManager = new SceneManager();
        try {
            funcionarioController = new FuncionarioController();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleCancelar(ActionEvent event) {
        sceneManager.getStage(event).close();
    }

    @FXML
    public void handleSalvar(ActionEvent event) {
        String nome = nomeTextField.getText();
        String numero = numeroTextField.getText();
        String senha = DigestUtils.sha256Hex(senhaTextField.getText());

        Funcionario funcionario = LoginController.getFuncionarioLogado();

        if (funcionario == null)
            throw new RuntimeException("Nenhum funcionário autenticado.");

        try {
            funcionario.setNome(nome);
            funcionario.setSenha(senha);
            funcionario.setTelefone(numero);
        } catch (AtributoInvalidoException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        try {
            funcionarioController.atualizar(funcionario);
        } catch (DAOException e) {
            throw new RuntimeException("Funcionario não pode ser editado", e);
        }

        sceneManager.getStage(event).close();
    }
}
