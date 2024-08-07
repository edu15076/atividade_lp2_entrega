package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Produto;

public class ProdutoDAO extends BaseDAO<Produto> {
    public ProdutoDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Produto> getEntityClass() {
        return Produto.class;
    }
}
