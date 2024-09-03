package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.EmpresaController;
import br.cefetmg.gestaoEntregas.controllers.FuncionarioController;
import br.cefetmg.gestaoEntregas.dao.EmpresaDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.viewdesktop.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField lojaNumber;

    @FXML
    private TextField phoneNumber;

    @FXML
    private PasswordField senhaPasswordField;

    @FXML
    private Button loginButton;

    private FuncionarioController funcionarioController;
    private EmpresaController empresaController;

    public LoginController() throws DAOException {
        funcionarioController = new FuncionarioController();
        empresaController = new EmpresaController(new EmpresaDAO());
    }

    @FXML
    public void handleLoginAction(ActionEvent event) throws DAOException, IOException {
        String lojaNumberText = lojaNumber.getText();
        String phoneNumberText = phoneNumber.getText();
        String passwordText = senhaPasswordField.getText();

        Empresa empresa;
        Funcionario funcionario;

        try {
            empresa = empresaController.recuperarPorCodigo(lojaNumberText);

            if (empresa == null)
                throw new DAOException("Empresa não encontrada com o número fornecido.");

            funcionario = funcionarioController.consultarTelefone(phoneNumberText);

            System.out.println(empresa.getNome());

            if (funcionario == null)
                throw new DAOException("Funcionário não encontrado com o telefone fornecido.");

            Funcionario funcionarioLogado = br.cefetmg.gestaoEntregas.controllers.LoginController.logar(funcionario, passwordText, empresa);

            if (funcionarioLogado != null)
                System.out.println("Login realizado com sucesso!");
            else
                System.out.println("Falha no login: senha ou empresa incorreta.");
        } catch (DAOException e) {
            e.printStackTrace();
            System.out.println("Erro de login: " + e.getMessage());
        }

        SceneManager sceneManager = new SceneManager();
        sceneManager.showScene(sceneManager.getStage(event), "funcionarios-scene.fxml", "Desenvolvimento");
    }
}