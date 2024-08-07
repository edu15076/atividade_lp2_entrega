package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Perfil;

public class PerfilDAO extends BaseDAO<Perfil> {
     public PerfilDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Perfil> getEntityClass() {
         return Perfil.class;
    }
}
