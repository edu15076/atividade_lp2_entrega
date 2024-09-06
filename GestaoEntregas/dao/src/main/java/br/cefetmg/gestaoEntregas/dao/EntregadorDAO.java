package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Entregador;
import br.cefetmg.gestaoEntregas.entidades.Pedido;
import br.cefetmg.gestaoEntregas.entidades.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.sql.Date;
import java.util.List;

public class EntregadorDAO extends BaseDAO<Entregador> {
    public EntregadorDAO() throws DAOException {
        super("persistence");
    }

    @Override
    protected Class<Entregador> getEntityClass() {
        return Entregador.class;
    }

    private List<Pedido> consultarPedidosDiaForQuery(Entregador entity, Date dia, String query) throws DAOException {
        List<Pedido> resultados;

        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            Query queryEntity = entityManager.createQuery(query);
            queryEntity.setParameter("entregadorId", entity.getId());
            queryEntity.setParameter("data", dia);

            resultados = queryEntity.getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar " + nomeEntidade, e);
        }

        return resultados;
    }

    public List<Pedido> consultarPedidosDia(Entregador entity, Date dia) throws DAOException {
        String query = "SELECT p FROM Pedido p WHERE p.entregador.id = :entregadorId AND p.data = :data";
        return this.consultarPedidosDiaForQuery(entity, dia, query);
    }

    public List<Pedido> consultarPedidosEntreguesDia(Entregador entity, Date dia) throws DAOException {
        String query = "SELECT p FROM Pedido p WHERE p.entregador.id = :entregadorId AND p.data = :data AND p.status = 'ENTREGUE'";
        return this.consultarPedidosDiaForQuery(entity, dia, query);
    }

    public List<Entregador> consultarEntregadoresEmpresa(Empresa empresa) throws DAOException {
        String query = "SELECT ent FROM Entregador ent WHERE ent.funcionario.empresa.id = :empresaId";
        List<Entregador> resultados;

        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            Query queryEntity = entityManager.createQuery(query);
            queryEntity.setParameter("empresaId", empresa.getId());

            resultados = queryEntity.getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar " + nomeEntidade, e);
        }

        return resultados;
    }
}
