package com.ciandt.feedfront.contracts;

import javax.persistence.EntityManager;
import java.util.List;

public interface DAO<T> {
    List<T> listar();

    T buscar(long id);

    T salvar(T t);

    boolean apagar(long id);

    void setEntityManager(EntityManager entityManager);
}
