package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Entregador;
import br.cefetmg.gestaoEntregas.entidades.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class PedidoDAO extends BaseDAO<Pedido> {
    public PedidoDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Pedido> getEntityClass() {
        return Pedido.class;
    }

    public List<Pedido> consultarPedidosQueSairamParaEntrega(Entregador entregador) throws DAOException {
        String query = "SELECT p FROM Pedido p WHERE p.entregador.id = :entregadorId AND p.status = 'SAIU_PARA_ENTREGA'";
        List<Pedido> resultados;

        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            Query queryEntity = entityManager.createQuery(query);
            queryEntity.setParameter("entregadorId", entregador.getId());
            resultados = queryEntity.getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar " + nomeEntidade, e);
        }

        return resultados;
    }
}
