package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.ClienteController;
import br.cefetmg.gestaoEntregas.controllers.PedidoController;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Cliente;
import br.cefetmg.gestaoEntregas.entidades.ItemPedido;
import br.cefetmg.gestaoEntregas.entidades.Pedido;
import br.cefetmg.gestaoEntregas.entidades.enums.Status;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import br.cefetmg.gestaoEntregas.viewdesktop.SceneManager;
import br.cefetmg.gestaoEntregas.viewdesktop.controllers.wrappers.PedidoWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import net.synedra.validatorfx.Validator;

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
    private TableColumn<PedidoWrapper, Void> detalhesColumn;

    @FXML
    private TableView<PedidoWrapper> tabelaTableView;

    @FXML
    private TextArea observacoesTextArea;

    @FXML
    private VBox cadastroVBox;

    private ArrayList<ItemPedidoComponentController> itens;
    private Validator validator;

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

        detalhesColumn.setCellFactory(param -> new TableCell<PedidoWrapper, Void>() {
            private final Button detalhesButton = new Button("Detalhes");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    detalhesButton.setOnAction(event -> {
                        PedidoWrapper pedidoWrapper = getTableView().getItems().get(getIndex());
                        SceneManager sceneManager = new SceneManager();
                        try {
                            PedidoDetalheController controller = (PedidoDetalheController) sceneManager.openNewWindow("pedido-detalhe-scene.fxml", "Pedido Detalhes");
                            controller.loadPedido(pedidoWrapper);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    });
                    setGraphic(detalhesButton);
                }
            }
        });

        // Validadores
        validator = new Validator();

        validator.createCheck()
                .withMethod(c -> {
                    String codigoCliente = c.get("codigoCliente");
                    if (codigoCliente == null || codigoCliente.trim().isEmpty()) {
                        c.error("Campo obrigatório");
                    }
                })
                .dependsOn("codigoCliente", codigoClienteTextField.textProperty())
                .decorates(codigoClienteTextField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String codigoCliente = c.get("codigoCliente");
                    try {
                        ClienteController controllerCliente = new ClienteController();
                        Cliente cliente = controllerCliente.consultarCodigo(codigoCliente);
                        if (cliente == null) {
                            c.error("Cliente não encontrado");
                        }
                    } catch (DAOException e) {
                        c.error("Erro ao verificar cliente");
                    }
                })
                .dependsOn("codigoCliente", codigoClienteTextField.textProperty())
                .decorates(codigoClienteTextField)
                .immediate();

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
            System.out.println(e.getMessage());
        }
    }

    private void handleCadastrarButtonClick(ActionEvent event) {
        if (!validator.validate()) return;

        String codigoCliente = codigoClienteTextField.getText();
        String observacoes = observacoesTextArea.getText();

        try {
            ClienteController clienteController = new ClienteController();
            Cliente cliente = clienteController.consultarCodigo(codigoCliente);

            assert cliente != null;

            PedidoController controller = new PedidoController();
            Pedido pedido = controller.abrirPedido(cliente);

            List<ItemPedido> itensPedidos = new ArrayList<ItemPedido>();
            for(ItemPedidoComponentController itemPedidoController : itens) {
                ItemPedido item = itemPedidoController.getItemPedido();
                item.setPedido(pedido);
                itensPedidos.add(item);
            }

            System.out.println(itensPedidos.size());
            pedido.setItensPedido(itensPedidos);
            pedido.setObservacoes(observacoes);

            controller.fecharPedido(pedido);

            loadPedidos();

        } catch (DAOException | AtributoInvalidoException e) {
            System.out.println(e.getMessage());
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

