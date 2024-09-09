package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.EntregadorController;
import br.cefetmg.gestaoEntregas.controllers.PedidoController;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Entregador;
import br.cefetmg.gestaoEntregas.entidades.ItemPedido;
import br.cefetmg.gestaoEntregas.entidades.Pedido;
import br.cefetmg.gestaoEntregas.entidades.enums.Status;
import br.cefetmg.gestaoEntregas.viewdesktop.controllers.wrappers.PedidoWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import net.synedra.validatorfx.Validator;

public class PedidoDetalheController implements Initializable {
    @FXML
    private Label clienteLabel;
    @FXML
    private Label codigoPedidoLabel;

    @FXML
    private Label dataPedidoLabel;

    @FXML
    private Label entregadorLabel;

    @FXML
    private TextField funcionarioTextField;

    @FXML
    private ListView<String> itensListView;

    @FXML
    private Label observacoesLabel;

    @FXML
    private ComboBox<Status> statusComboBox;
    private Pedido pedido;
    private Validator validator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusComboBox.getItems().addAll(Status.SAIU_PARA_ENTREGA, Status.ENTREGUE);

        // Validadores
        validator = new Validator();

        validator.createCheck()
                .withMethod(c -> {
                    String telefoneEntregador = c.get("telefoneEntregador");
                    if (telefoneEntregador == null || telefoneEntregador.trim().isEmpty()) {
                        c.error("Campo obrigatório e formato de telefone");
                    }
                })
                .dependsOn("telefoneEntregador", funcionarioTextField.textProperty())
                .decorates(funcionarioTextField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String telefoneEntregador = c.get("telefoneEntregador");
                    try {
                        EntregadorController controllerEntregador = new EntregadorController();
                        Entregador entregador = controllerEntregador.recuperarPorFuncionarioTelefone(telefoneEntregador);
                        if (entregador == null) {
                            c.error("Telefone de entregador não encontrado");
                        }
                    } catch (DAOException e) {
                        c.error("Erro ao verificar telefone de entregador");
                    }
                })
                .dependsOn("telefoneEntregador", funcionarioTextField.textProperty())
                .decorates(funcionarioTextField)
                .immediate();
    }

    public void loadPedido(PedidoWrapper pedido) {
        clienteLabel.setText(pedido.getNomeCliente());
        entregadorLabel.setText(pedido.getNomeEntregador());
        codigoPedidoLabel.setText(pedido.getCodigo());
        dataPedidoLabel.setText(pedido.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        observacoesLabel.setText(pedido.getObservacoes());

        this.pedido = pedido.getPedido();

        List<ItemPedido> itens = pedido.getItens();
        ObservableList<String> itensObservableList = FXCollections.observableArrayList();
        for (ItemPedido item : itens) {
            itensObservableList.add(item.getProduto().getNome() + " - Quantidade: " + item.getQuantidade());
        }

        itensListView.setItems(itensObservableList);
    }

    @FXML
    public void handleSalvarButtonClick(ActionEvent event) {
        if (!validator.validate()) return;

        String telefoneEntregador = funcionarioTextField.getText();
        Status status = statusComboBox.getValue();

        try {
            PedidoController controller = new PedidoController();
            EntregadorController controllerEntregador = new EntregadorController();

            Entregador entregador = controllerEntregador.recuperarPorFuncionarioTelefone(telefoneEntregador);

            assert entregador != null;

            if(status == Status.SAIU_PARA_ENTREGA)
                controller.atualizarPedidoSaindoParaEntrega(pedido, entregador);
            else if (status == Status.ENTREGUE)
                controller.atualizarPedidoEntregue(pedido);

            loadPedido(new PedidoWrapper(pedido));

        } catch (DAOException | AssertionError e) {
            System.out.println(e.getMessage());
        }

    }
}
