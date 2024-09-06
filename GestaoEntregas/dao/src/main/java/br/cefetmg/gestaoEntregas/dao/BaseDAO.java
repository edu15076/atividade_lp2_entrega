package br.cefetmg.gestaoEntregas.dao;

import java.util.List;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.dao.utils.Pair;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public abstract class BaseDAO<T> {
    protected final EntityManagerFactory entityManagerFactory;
    protected final String nomeEntidade;
    protected final String nomeTabela;

    public BaseDAO(String persistenceUnitName) throws DAOException {
        try {
            this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            this.nomeEntidade = this.getEntityClass().getSimpleName();
            String nomeTabela = "";
            try {
                nomeTabela = this.getEntityClass().getAnnotation(jakarta.persistence.Table.class).name();
            } catch (NullPointerException ignored) {}

            this.nomeTabela = nomeTabela;
        } catch (Exception e) {
            throw new DAOException("Erro ao criar EntityManagerFactory", e);
        }
    }

    public void salvar(T entity) throws DAOException {
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new DAOException("Erro ao salvar " + nomeEntidade, e);
        }
    }

    public T consultar(Long id) throws DAOException {
        T result;
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            result = entityManager.find(getEntityClass(), id);
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar " + nomeEntidade, e);
        }
        return result;
    }

    public <U> List<T> consultarCampo(Pair<String, U> campo) throws DAOException {
        List<T> resultList;

        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
            Root<T> root = criteriaQuery.from(getEntityClass());

            criteriaQuery.where(criteriaBuilder.equal(root.get(campo.getFirst()), campo.getSecond()));

            TypedQuery<T> query = entityManager.createQuery(criteriaQuery);

            resultList = query.getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar " + nomeEntidade + " " + e.getMessage(), e);
        }

        return resultList;
    }

    public List<T> consultarTodos() throws DAOException {
        List<T> result;
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            result = entityManager.createQuery("FROM " + nomeEntidade + " t", getEntityClass()).getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar todos os " + nomeEntidade, e);
        }
        return result;
    }

    public void atualizar(T entity) throws DAOException {
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar " + nomeEntidade, e);
        }
    }

    public void deletar(T entity) throws DAOException {
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            if (!entityManager.contains(entity)) {

                entity = entityManager.merge(entity);
            }
            entityManager.remove(entity);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new DAOException("Erro ao deletar " + nomeEntidade, e);
        }
    }

    public void refrescar(T entity) throws DAOException {
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            entityManager.refresh(entity);
        } catch (Exception e) {
            throw new DAOException("Erro ao deletar " + nomeEntidade, e);
        }
    }

    protected abstract Class<T> getEntityClass();
}
