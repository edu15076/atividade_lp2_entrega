package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.LoginController;
import br.cefetmg.gestaoEntregas.controllers.Relatorio;
import br.cefetmg.gestaoEntregas.controllers.RelatorioController;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Entregador;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RelatoriosController extends MenuController {

    @FXML
    private TextField periodoInicioTextField;

    @FXML
    private TextField periodoFimTextField;

    @FXML
    private TableView<List<Object>> relatorioTableView;

    @FXML
    private TableColumn<List<Double>, String> entregadorNomeColumn;

    @FXML
    private TableColumn<List<Double>, Double> totalColumn;

    private final RelatorioController relatorioController = new RelatorioController();
    private Empresa empresa;  // Assuming this is set somewhere in the code

    @FXML
    private void onHandleAplicarButton() {
        // Parse the date input fields
        empresa = LoginController.getFuncionarioLogado().getEmpresa();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataInicio = LocalDate.parse(periodoInicioTextField.getText(), formatter);
        LocalDate dataFim = LocalDate.parse(periodoFimTextField.getText(), formatter);
        int intervalo = (int) java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim);

        try {
            // Get the report data
            var relatorio = relatorioController.relatorioEntregadores(empresa, dataInicio, intervalo);

            // Build the report (this triggers the data collection)
            relatorio.buildRelatorio();

            // Update the TableView with report data
            updateTableView(relatorio, relatorio.getDias());
        } catch (Exception e) {
            e.printStackTrace();  // Handle errors appropriately
        }
    }

    public void updateTableView(Relatorio<Entregador, Double> relatorio, List<LocalDate> dateColumns) {
        relatorioTableView.getColumns().clear(); // Clear previous columns
        relatorioTableView.getItems().clear();   // Clear previous data

        TableColumn<List<Object>, String> entregadorNomeColumn = new TableColumn<>("Entregador Nome");
        entregadorNomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get(1)));

        TableColumn<List<Object>, Double> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty((Double) cellData.getValue().get(2)).asObject());

        relatorioTableView.getColumns().addAll(entregadorNomeColumn, totalColumn);

        // Dynamically create columns for each day in dateColumns
        for (int i = 0; i < dateColumns.size(); i++) {
            String date = dateColumns.get(i).toString();
            TableColumn<List<Object>, Double> dateColumn = new TableColumn<>(date);
            final int columnIndex = i + 3; // 0 - ID, 1 - Name, 2 - Total, so start from 3 for date columns

            dateColumn.setCellValueFactory(cellData -> {
                Double commission = (Double) cellData.getValue().get(columnIndex);
                return new SimpleDoubleProperty(commission).asObject();
            });

            relatorioTableView.getColumns().add(dateColumn);
        }

        // Populate the TableView with rows
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        for (Entregador entregador : relatorio.getChaves()) {
            List<Object> row = new ArrayList<>();
            List<Double> linha = relatorio.getLinha(entregador);

            Double total = 0.0;
            for (var el : linha)
                total += el;

            row.add(entregador.getId());   // Add Entregador ID
            row.add(entregador.getFuncionario().getNome()); // Add Entregador Name
            row.add(total); // Add total

            row.addAll(linha); // Add each commission per day

            data.add(row);
        }

        relatorioTableView.setItems(data); // Set the populated data in the TableView
    }
}
