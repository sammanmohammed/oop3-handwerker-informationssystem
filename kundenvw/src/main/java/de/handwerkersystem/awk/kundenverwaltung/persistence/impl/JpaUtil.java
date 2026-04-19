package de.handwerkersystem.awk.kundenverwaltung.persistence.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("HANDWERKER-INFOSYSTEM_HA2");

    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
}
