package br.cefetmg.gestaoEntregas.viewdesktop.controllers.interfaces;

import br.cefetmg.gestaoEntregas.viewdesktop.controllers.MenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public interface MenuControllerInterface {
    @FXML
    void onHandleSairButton(ActionEvent event) throws IOException;
}
