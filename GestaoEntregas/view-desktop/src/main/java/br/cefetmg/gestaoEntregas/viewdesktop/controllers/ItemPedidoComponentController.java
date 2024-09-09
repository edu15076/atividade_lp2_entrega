package br.cefetmg.gestaoEntregas.viewdesktop.controllers;

import br.cefetmg.gestaoEntregas.controllers.ProdutoController;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.ItemPedido;
import br.cefetmg.gestaoEntregas.entidades.Produto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.synedra.validatorfx.Validator;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemPedidoComponentController implements Initializable {
    @FXML
    private TextField codigoProdutoField;

    @FXML
    private Button removerButton;

    @FXML
    private TextField valorField;

    @FXML
    private TextField quantidadeField;

    private PedidosController pedidosController;
    @FXML
    private VBox produtoVBox;

    private Validator validator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removerButton.setOnAction(this::handleRemoverButtonClick);

        validator = new Validator();

        validator.createCheck()
                .withMethod(c -> {
                    String codigoProduto = c.get("codigoProduto");
                    if (codigoProduto == null || codigoProduto.trim().isEmpty()) {
                        c.error("Campo obrigatório");
                    }
                })
                .dependsOn("codigoProduto", codigoProdutoField.textProperty())
                .decorates(codigoProdutoField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String valor = c.get("valor");
                    if (valor == null || valor.trim().isEmpty() || !valor.matches("\\d+(\\.\\d+)?")) {
                        c.error("Campo obrigatório e formato de valor");
                    }
                })
                .dependsOn("valor", valorField.textProperty())
                .decorates(valorField)
                .immediate();

        validator.createCheck()
                .withMethod(c -> {
                    String quantidade = c.get("quantidade");
                    if (quantidade == null || quantidade.trim().isEmpty() || !quantidade.matches("\\d+")) {
                        c.error("Campo obrigatório e formato de quantidade");
                    }
                })
                .dependsOn("quantidade", quantidadeField.textProperty())
                .decorates(quantidadeField)
                .immediate();
    }

    ItemPedido getItemPedido() throws DAOException {
        ProdutoController produtoController = new ProdutoController();

        Produto produto = produtoController.consultarProdutoCodigo(codigoProdutoField.getText());
        double valor = Double.parseDouble(valorField.getText());
        int quantidade = Integer.parseInt(quantidadeField.getText());

        ItemPedido novoItem = new ItemPedido();
        novoItem.setQuantidade(quantidade);
        novoItem.setValorUnitario(valor);
        novoItem.setProduto(produto);

        return novoItem;
    }

    public void setProdutoVBox(VBox produtoVBox) {
        this.produtoVBox = produtoVBox;
    }

    @FXML
    public void handleRemoverButtonClick(ActionEvent event) {
        if (!validator.validate()) return;

        if (pedidosController != null) {
            pedidosController.removerItemPedido(produtoVBox, this);
        } else {
            System.out.println("PedidosController não foi inicializado!");
        }
    }

    public PedidosController getPedidosController() {
        return pedidosController;
    }

    public void setPedidosController(PedidosController pedidosController) {
        this.pedidosController = pedidosController;
    }


}
