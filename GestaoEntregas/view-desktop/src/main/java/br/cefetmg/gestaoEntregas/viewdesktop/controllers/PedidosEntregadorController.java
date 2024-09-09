package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.LoginController;
import br.cefetmg.gestaoEntregas.controllers.PedidoController;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.Pedido;
import br.cefetmg.gestaoEntregas.entidades.enums.Status;
import br.cefetmg.gestaoEntregas.viewdesktop.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PedidosEntregadorController extends MenuController implements Initializable {
    @FXML
    private Label nomeLabel;

    @FXML
    private Label perfilLabel;

    @FXML
    private Label telefoneLabel;

    @FXML
    private TableView<PedidoExibition> pedidosTableView;

    @FXML
    private TableColumn<PedidoExibition, String> codigoColumn;

    @FXML
    private TableColumn<PedidoExibition, String> enderecoColumn;

    @FXML
    private TableColumn<PedidoExibition, String> dataColumn;

    @FXML
    private TableColumn<PedidoExibition, String> comissaoColumn;

    @FXML
    private TableColumn<PedidoExibition, String> statusColumn;

    @FXML
    private TableColumn<PedidoExibition, Button> actionColumn;

    private PedidoController pedidosController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        try {
            pedidosController = new PedidoController();
        } catch (Exception e) {
            e.printStackTrace();
        }

        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        comissaoColumn.setCellValueFactory(new PropertyValueFactory<>("comissao"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        addButtonToTable();
        loadPedidos();
        refreshUserData();
    }

    private void addButtonToTable() {
        Callback<TableColumn<PedidoExibition, Button>, TableCell<PedidoExibition, Button>> cellFactory = new Callback<>() {
            @Override
            public TableCell<PedidoExibition, Button> call(final TableColumn<PedidoExibition, Button> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Marcar como Entregue");

                    {
                        btn.setOnAction(event -> {
                            PedidoExibition pedidoExibition = getTableView().getItems().get(getIndex());
                            Pedido pedido;
                            try {
                                pedido = pedidosController.consultarPedidoPorId(pedidoExibition.getCodigo());
                            } catch (DAOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                pedidosController.atualizarPedidoEntregue(pedido);
                            } catch (DAOException e) {
                                throw new RuntimeException(e);
                            }
                            pedidosTableView.refresh();
                            loadPedidos();
                        });
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(btn);
                    }
                };
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    private void loadPedidos() {
        try {
            List<Pedido> pedidos = pedidosController.listarPedidosEntregador();
            ObservableList<PedidoExibition> pedidoExibitions = FXCollections.observableArrayList(PedidoExibition.createPedidosExibition(pedidos));
            pedidosTableView.setItems(pedidoExibitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshUserData() {
        Funcionario funcionario = LoginController.getFuncionarioLogado();

        nomeLabel.setText("Nome: " + funcionario.getNome());
        perfilLabel.setText("Perfil: " + funcionario.getPerfis().getFirst().getTipoPerfil());
        telefoneLabel.setText("Telefone: " + funcionario.getTelefone());
    }

    public static class PedidoExibition {
        private Long codigo;
        private String endereco;
        private String data;
        private String comissao;
        private String status;

        public PedidoExibition(Long codigo, String endereco, String data, String comissao, String status) {
            this.codigo = codigo;
            this.endereco = endereco;
            this.data = data;
            this.comissao = comissao;
            this.status = status;
        }

        public Long getCodigo() {
            return codigo;
        }

        public void setCodigo(Long codigo) {
            this.codigo = codigo;
        }

        public String getEndereco() {
            return endereco;
        }

        public void setEndereco(String endereco) {
            this.endereco = endereco;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getComissao() {
            return comissao;
        }

        public void setComissao(String comissao) {
            this.comissao = comissao;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static List<PedidoExibition> createPedidosExibition(List<Pedido> pedidos) {
            List<PedidoExibition> pedidoExibitions = new ArrayList<>();
            var comissao = LoginController.getFuncionarioLogado().getEmpresa().getPorcentagemComissaoEntregador();
            for (Pedido pedido : pedidos) {
                pedidoExibitions.add(new PedidoExibition(pedido.getId(),
                        pedido.getCliente().getEndereco(),
                        pedido.getData().toString(),
                        Double.valueOf(comissao * pedido.getValorTotal()).toString(),
                        pedido.getStatus().name()));
            }
            return pedidoExibitions;
        }
    }
}
