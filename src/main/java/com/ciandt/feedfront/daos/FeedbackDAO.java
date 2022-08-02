package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.utils.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class FeedbackDAO implements DAO<Feedback> {
    private EntityManager entityManager;

    public FeedbackDAO() {
        entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<Feedback> listar() {

        return entityManager.createQuery("SELECT f FROM Feedback f").getResultList();
    }

    @Override
    public Optional<Feedback> buscar(long id) {

        Feedback feedback = entityManager.find(Feedback.class, id);

        return Optional.ofNullable(feedback);
    }

    @Override
    public Feedback salvar(Feedback feedback) {
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(feedback);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally{
            entityManager.clear();
        }
        return feedback;
    }

    @Override
    public boolean apagar(long id) {
        Optional<Feedback> feedback = buscar(id);
        EntityTransaction transaction = null;

        try {
            if (feedback.isPresent()) {
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.remove(feedback.get());
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.clear();
        }
        return false;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
