package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.ClienteController;
import br.cefetmg.gestaoEntregas.controllers.PedidoController;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Cliente;
import br.cefetmg.gestaoEntregas.entidades.ItemPedido;
import br.cefetmg.gestaoEntregas.entidades.Pedido;
import br.cefetmg.gestaoEntregas.entidades.enums.Status;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import br.cefetmg.gestaoEntregas.viewdesktop.controllers.wrappers.PedidoWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PedidosController extends MenuController {
    @FXML
    private Button adcionarItemButton;

    @FXML
    private Button cadastrarButton;

    @FXML
    private TextField codigoClienteTextField;

    @FXML
    private TableColumn<PedidoWrapper, String> codigoColumn;

    @FXML
    private TableColumn<PedidoWrapper, LocalDate> dataColumn;

    @FXML
    private TableColumn<PedidoWrapper, String> maisIntormacoesColumn;

    @FXML
    private TableColumn<PedidoWrapper, String> nomeColumn;

    @FXML
    private TableColumn<PedidoWrapper, Double> precoColumn;

    @FXML
    private Button sairButton;

    @FXML
    private TableColumn<PedidoWrapper, Status> statusColumn;

    @FXML
    private TableView<PedidoWrapper> tabelaTableView;

    @FXML
    private TextArea observacoesTextArea;

    @FXML
    private VBox cadastroVBox;

    private ArrayList<ItemPedidoComponentController> itens;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        itens = new ArrayList<>();
        adcionarItemButton.setOnAction(this::handleAdcionarProdutoButtonClick);
        cadastrarButton.setOnAction(this::handleCadastrarButtonClick);

        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        precoColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));

        loadPedidos();
    }

    private void loadPedidos() {
        try {
            PedidoController pedidoController = new PedidoController();
            List<Pedido> pedidos = pedidoController.listarPedidos();

            ObservableList<PedidoWrapper> pedidoList = FXCollections.observableArrayList();

            for (Pedido pedido : pedidos) {
                pedidoList.add(new PedidoWrapper(pedido));
            }

            tabelaTableView.setItems(pedidoList);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCadastrarButtonClick(ActionEvent event) {
        String codigoCliente = codigoClienteTextField.getText();
        String observacoes = observacoesTextArea.getText();

        try {
            ClienteController clienteController = new ClienteController();
            Cliente cliente = clienteController.consultarCodigo(codigoCliente);

            assert cliente != null;

            PedidoController controller = new PedidoController();
            Pedido pedido = controller.abrirPedido(cliente);

            List<ItemPedido> itensPedidos = new ArrayList<ItemPedido>();
            for(ItemPedidoComponentController itemPedidoController : itens)
                itensPedidos.add(itemPedidoController.getItemPedido());

            pedido.setItensPedido(itensPedidos);
            pedido.setObservacoes(observacoes);

            controller.fecharPedido(pedido);

            loadPedidos();

        } catch (DAOException | AtributoInvalidoException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        codigoClienteTextField.setText("");
        observacoesTextArea.setText("");

        for(ItemPedidoComponentController i : itens)
            i.handleRemoverButtonClick(null);
    }

    private void handleAdcionarProdutoButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../item-pedido-component.fxml"));

        VBox produtoVBox = null;
        try {
            produtoVBox = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        ItemPedidoComponentController itemPedidoComponentController = loader.getController();

        itemPedidoComponentController.setPedidosController(this);
        itemPedidoComponentController.setProdutoVBox(produtoVBox);

        itens.add(itemPedidoComponentController);
        cadastroVBox.getChildren().add(produtoVBox);
    }

    public void removerItemPedido(VBox produtoVBox, ItemPedidoComponentController controller) {
        itens.remove(controller);
        cadastroVBox.getChildren().remove(produtoVBox);
    }
}

