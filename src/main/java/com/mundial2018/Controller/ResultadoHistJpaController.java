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
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

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
            if (ResultadoHistJpaController.this.findResultadoHist(resultadoHist.getId()) != null) {
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
                if (ResultadoHistJpaController.this.findResultadoHist(id) == null) {
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
    
    public ResultadoHist findResultadoHist(Date fechaPartido, Integer empleadoId) throws ParseException {
        EntityManager em = getEntityManager();
        ResultadoHist resultadoHist = new ResultadoHist();
        DateTime dt = new DateTime(fechaPartido).minusDays(1).toDateTime(DateTimeZone.UTC);
        fechaPartido = dt.toDate();
        
        try {
            Query query = em.createQuery("select r from ResultadoHist r where r.empleadoId = :empleadoId and cast(r.fechaModificacion as date) <= :fechaPartido order by r.fechaModificacion desc");
            query.setParameter("empleadoId", empleadoId);
            query.setParameter("fechaPartido", fechaPartido);
            resultadoHist = (ResultadoHist)query.setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            em.close();
        }
            
        return resultadoHist;
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
