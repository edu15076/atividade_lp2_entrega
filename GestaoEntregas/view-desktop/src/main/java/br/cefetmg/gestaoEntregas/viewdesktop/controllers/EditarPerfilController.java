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
import net.synedra.validatorfx.Validator;
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
    private Validator validator;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneManager = new SceneManager();
        try {
            funcionarioController = new FuncionarioController();
        } catch (DAOException e) {
            throw new RuntimeException(e);
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
                    String senha = c.get("senha");
                    if (senha == null || senha.trim().isEmpty() || senha.length() < 6) {
                        c.error("Campo obrigatório e mínimo de 6 caracteres");
                    }
                })
                .dependsOn("senha", senhaTextField.textProperty())
                .decorates(senhaTextField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String telefone = c.get("telefone");
                    if (telefone == null || telefone.trim().isEmpty()) {
                        c.error("Campo obrigatório e formato de telefone");
                    }
                })
                .dependsOn("telefone", numeroTextField.textProperty())
                .decorates(numeroTextField)
                .immediate();
    }

    @FXML
    public void handleCancelar(ActionEvent event) {
        sceneManager.getStage(event).close();
    }

    @FXML
    public void handleSalvar(ActionEvent event) {
        if (!validator.validate()) return;

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
