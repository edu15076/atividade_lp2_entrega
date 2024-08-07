package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Empresa;

public class EmpresaDAO extends BaseDAO<Empresa> {
    public EmpresaDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Empresa> getEntityClass() {
        return Empresa.class;
    }
}
