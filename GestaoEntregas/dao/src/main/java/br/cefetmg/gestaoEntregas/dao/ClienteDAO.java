package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Cliente;

public class ClienteDAO extends BaseDAO<Cliente> {
    public ClienteDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Cliente> getEntityClass() {
        return Cliente.class;
    }
}
