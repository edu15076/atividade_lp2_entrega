package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Pedido;

public class PedidoDAO extends BaseDAO<Pedido> {
    public PedidoDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Pedido> getEntityClass() {
        return Pedido.class;
    }
}
