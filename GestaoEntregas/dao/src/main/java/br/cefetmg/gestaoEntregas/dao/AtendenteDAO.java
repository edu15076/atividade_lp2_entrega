package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Atendente;

public class AtendenteDAO extends BaseDAO<Atendente> {
    public AtendenteDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Atendente> getEntityClass() {
        return Atendente.class;
    }
}
