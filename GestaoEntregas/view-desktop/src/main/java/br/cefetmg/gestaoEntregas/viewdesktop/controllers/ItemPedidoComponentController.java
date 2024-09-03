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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removerButton.setOnAction(this::handleRemoverButtonClick);
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
