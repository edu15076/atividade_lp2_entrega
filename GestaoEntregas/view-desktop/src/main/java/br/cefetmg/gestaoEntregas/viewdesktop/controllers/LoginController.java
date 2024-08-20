package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField phoneNumber;
    @FXML private TextField password;
    @FXML private Button loginButton;

    public void handleLoginAction(ActionEvent event) {
        // Add your login logic here
        String phoneNumberText = phoneNumber.getText();
        String passwordText = password.getText();

        System.out.println("Meu Pauuuuuuuuuuuuu");
    }
}