package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Administrador;

public class AdministradorDAO extends BaseDAO<Administrador> {
    public AdministradorDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Administrador> getEntityClass() {
        return Administrador.class;
    }
}
