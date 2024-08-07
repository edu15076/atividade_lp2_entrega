package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.ItemPedido;

public class ItemPedidoDAO extends BaseDAO<ItemPedido> {
    public ItemPedidoDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<ItemPedido> getEntityClass() {
        return ItemPedido.class;
    }
}
