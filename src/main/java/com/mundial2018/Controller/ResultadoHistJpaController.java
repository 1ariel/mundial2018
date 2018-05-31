/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import com.mundial2018.Controller.exceptions.PreexistingEntityException;
import com.mundial2018.Database.Entities.ResultadoHist;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author WVQ
 */
public class ResultadoHistJpaController implements Serializable {

    public ResultadoHistJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResultadoHist resultadoHist) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(resultadoHist);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResultadoHist(resultadoHist.getId()) != null) {
                throw new PreexistingEntityException("ResultadoHist " + resultadoHist + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResultadoHist resultadoHist) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            resultadoHist = em.merge(resultadoHist);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = resultadoHist.getId();
                if (findResultadoHist(id) == null) {
                    throw new NonexistentEntityException("The resultadoHist with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResultadoHist resultadoHist;
            try {
                resultadoHist = em.getReference(ResultadoHist.class, id);
                resultadoHist.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resultadoHist with id " + id + " no longer exists.", enfe);
            }
            em.remove(resultadoHist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResultadoHist> findResultadoHistEntities() {
        return findResultadoHistEntities(true, -1, -1);
    }

    public List<ResultadoHist> findResultadoHistEntities(int maxResults, int firstResult) {
        return findResultadoHistEntities(false, maxResults, firstResult);
    }

    private List<ResultadoHist> findResultadoHistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResultadoHist.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ResultadoHist findResultadoHist(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResultadoHist.class, id);
        } finally {
            em.close();
        }
    }

    public int getResultadoHistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResultadoHist> rt = cq.from(ResultadoHist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
