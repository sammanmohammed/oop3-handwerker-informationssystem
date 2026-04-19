package de.handwerkersystem.awk.persistence.impl;

import java.util.List;
import de.handwerkersystem.awk.entity.internal.Ressource;
import de.handwerkersystem.awk.persistence.IRessourceDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import de.handwerkersystem.awk.kundenverwaltung.persistence.impl.JpaUtil;


public class RessourceDAO implements IRessourceDAO {

    @Override
    public Ressource save(Ressource ressource) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(ressource);
            tx.commit();
            return ressource;
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Ressource update(Ressource ressource) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Ressource merged = em.merge(ressource);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
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
            Ressource kunde = em.find(Ressource.class, id);
            if (kunde != null) {
                em.remove(kunde);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Ressource findById(Long id) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.find(Ressource.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Ressource> findAll() {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            TypedQuery<Ressource> query = em.createQuery("SELECT r FROM Ressource r", Ressource.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsById(Long id) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(r) FROM Ressource r WHERE r.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }

    }

    @Override
    public double findKostensatzById(long ressourceId) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Ressource res = em.find(Ressource.class, ressourceId);

            tx.commit();

            if (res == null) {
                return 0.0; 
            }

            return res.getStandrdKostensatz(); 

        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

 

}
