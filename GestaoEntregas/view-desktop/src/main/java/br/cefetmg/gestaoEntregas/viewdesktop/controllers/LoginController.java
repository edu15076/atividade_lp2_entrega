package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField phoneNumber;
    @FXML private PasswordField senhaPasswordField;
    @FXML private Button loginButton;

    @FXML
    public void handleLoginAction(ActionEvent event) {
        // Add your login logic here
        String phoneNumberText = phoneNumber.getText();
        String passwordText = senhaPasswordField.getText();

        System.out.println("Meu Pauuuuuuuuuuuuu");
    }
}