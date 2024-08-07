package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;

public class FuncionarioDAO extends BaseDAO<Funcionario> {
    public FuncionarioDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Funcionario> getEntityClass() {
        return Funcionario.class;
    }
}
