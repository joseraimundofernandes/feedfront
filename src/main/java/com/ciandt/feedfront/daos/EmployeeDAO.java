package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.EmployeeConstraint;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.utils.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements DAO<Employee>, EmployeeConstraint {
    private EntityManager entityManager;

    public EmployeeDAO() {
        entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
    }

    public boolean checkEmailDuplication(String email) {

        Query query = entityManager.createQuery("SELECT e.email FROM Employee e WHERE e.email = ?1");
        query.setParameter(1, email);

        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
//        return result != null ? true : false;
    }

    @Override
    public List<Employee> listar() {

        return entityManager.createQuery("SELECT e FROM Employee e").getResultList();
    }

    @Override
    public Optional<Employee> buscar(long id) {

        Employee employee = entityManager.find(Employee.class, id);

        return Optional.ofNullable(employee);
    }

    @Override
    public Employee salvar(Employee employee) {
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(employee);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally{
            entityManager.clear();
        }
        return employee;
    }

    @Override
    public boolean apagar(long id) {
        Optional<Employee> employee = buscar(id);
        EntityTransaction transaction = null;
        try {
            if (employee.isPresent()) {
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.remove(employee.get());
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
