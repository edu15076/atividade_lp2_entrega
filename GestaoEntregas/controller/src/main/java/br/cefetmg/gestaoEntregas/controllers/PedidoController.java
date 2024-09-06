package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.ItemPedidoDAO;
import br.cefetmg.gestaoEntregas.dao.PedidoDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.dao.utils.Pair;
import br.cefetmg.gestaoEntregas.entidades.*;
import br.cefetmg.gestaoEntregas.entidades.enums.Status;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoController {
    private final PedidoDAO pedidoDAO;
    private final ItemPedidoDAO itemPedidoDAO;

    public PedidoController() throws DAOException {
        pedidoDAO = new PedidoDAO();
        itemPedidoDAO = new ItemPedidoDAO();
    }

    public Pedido abrirPedido(Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        Date dataAtual = Date.valueOf(LocalDate.now());
        pedido.setData(dataAtual);

        Empresa empresa = LoginController.getFuncionarioLogado().getEmpresa();
        pedido.setEmpresa(empresa);

        return pedido;
    }

    public void criarItemPedido(Pedido pedido, Produto produto, int quantidade, double valorUnitario) {
        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorUnitario(valorUnitario);

        pedido.getItensPedido().add(item);
    }

    public ItemPedido criarItemPedido(Produto produto, int quantidade, double valorUnitario) {
        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorUnitario(valorUnitario);

        return item;
    }

    public void removerItemPedido(Pedido pedido, ItemPedido itemPedido) throws DAOException {
        if(pedido.getId() != null) {
            itemPedidoDAO.deletar(itemPedido);
            pedidoDAO.refrescar(pedido);
        } else {
            List<ItemPedido> listaItens = pedido.getItensPedido();
            listaItens.remove(itemPedido);
            pedido.setItensPedido(listaItens);
        }
    }

    public void fecharPedido(Pedido pedido) throws AtributoInvalidoException, DAOException {
        if (pedido.getItensPedido().isEmpty())
            throw new AtributoInvalidoException("É necessário que pedido tenha ao menos um item.");

        pedido.setStatus(Status.EM_PREPARACAO);

        pedidoDAO.salvar(pedido);
    }

    /**
     * Marca o pedido como saido para entrega e atribui um entregador que realizará a entrega
     */
    public void atualizarPedidoSaindoParaEntrega(Pedido pedido, Entregador entregador) throws DAOException {
        pedido.setEntregador(entregador);
        pedido.setStatus(Status.SAIU_PARA_ENTREGA);
        pedidoDAO.atualizar(pedido);
    }

    /**
     * Marca o pedido como entregue
     */
    public void atualizarPedidoEntregue(Pedido pedido) throws DAOException {
        pedido.setStatus(Status.ENTREGUE);
        pedidoDAO.atualizar(pedido);
    }

    public List<Pedido> listarPedidos() throws DAOException {
        Empresa empresa = LoginController.getFuncionarioLogado().getEmpresa();

        return pedidoDAO.consultarCampo(new Pair<>("empresa", empresa));
    }

    public List<Pedido> listarPedidosEntregador() throws DAOException {
        Entregador entregador;

        try {
            entregador = (Entregador) LoginController.getFuncionarioLogado().getPerfis().getFirst();
        } catch (ClassCastException e) {
            throw new DAOException("O usuário autenticado não é um entregador.", e);
        }

        return pedidoDAO.consultarCampo(new Pair<>("entregador", entregador));
    }

    public List<Pedido> listarPedidosQueSairamParaEntregaEntregador() throws DAOException {
        Entregador entregador;

        try {
            entregador = (Entregador) LoginController.getFuncionarioLogado().getPerfis().getFirst();
        } catch (ClassCastException e) {
            throw new DAOException("O usuário autenticado não é um entregador.", e);
        }

        return pedidoDAO.consultarPedidosQueSairamParaEntrega(entregador);
    }

    public Pedido consultarPedidoPorId(Long id) throws DAOException {
        return pedidoDAO.consultar(id);
    }
}
