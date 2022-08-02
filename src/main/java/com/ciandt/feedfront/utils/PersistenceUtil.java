package com.ciandt.feedfront.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
