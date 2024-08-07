package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Entregador;

public class EntregadorDAO extends BaseDAO<Entregador> {
    public EntregadorDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Entregador> getEntityClass() {
        return Entregador.class;
    }
}
