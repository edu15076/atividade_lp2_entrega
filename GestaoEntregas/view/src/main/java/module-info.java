module br.cefetmg.gestaoentregas.view {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.cefetmg.gestaoentregas.view to javafx.fxml;
    exports br.cefetmg.gestaoentregas.view;
}