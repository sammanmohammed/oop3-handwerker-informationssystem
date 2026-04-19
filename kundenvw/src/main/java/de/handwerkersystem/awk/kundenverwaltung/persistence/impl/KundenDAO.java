package de.handwerkersystem.awk.kundenverwaltung.persistence.impl;

import java.util.List;

import de.handwerkersystem.awk.kundenverwaltung.entity.internal.Kunde;
import de.handwerkersystem.awk.kundenverwaltung.persistence.IKundenDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;



public class KundenDAO implements IKundenDAO {


    @Override
    public Kunde save(Kunde kunde) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(kunde);
            tx.commit();
            return kunde;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Kunde update(Kunde kunde) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Kunde merged = em.merge(kunde);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

 
    @Override
    public void deleteById(Long id) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Kunde kunde = em.find(Kunde.class, id);
            if (kunde != null) {
                em.remove(kunde);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

 
    @Override
    public Kunde findById(Long id) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.find(Kunde.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Kunde> findAll() {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            TypedQuery<Kunde> query =
                    em.createQuery("SELECT k FROM Kunde k", Kunde.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


    @Override
    public boolean existsById(Long kunde_nr) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(k) FROM Kunde k WHERE k.kunde_nr = :id", Long.class)
                    .setParameter("id", kunde_nr)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }


@Override

    public boolean existsByNameAndPlz(String vorname, String nachname, String plz) {
    EntityManager em = JpaUtil.createEntityManager();
    try {
        Long count = em.createNamedQuery(Kunde.FIND_BY_NAME_PLZ, Kunde.class)
                       .setParameter("vorname", vorname)
                       .setParameter("nachname", nachname)
                       .setParameter("plz", plz)
                       .getResultStream()
                       .count();
        return count > 0;
    } finally {
        em.close();
    }
}


}
