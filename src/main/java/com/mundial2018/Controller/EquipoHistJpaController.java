/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import com.mundial2018.Controller.exceptions.PreexistingEntityException;
import com.mundial2018.Database.Entities.EquipoHist;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.joda.time.DateTime;

/**
 *
 * @author WVQ
 */
public class EquipoHistJpaController implements Serializable {

    public EquipoHistJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EquipoHist equipoHist) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(equipoHist);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquipoHist(equipoHist.getId()) != null) {
                throw new PreexistingEntityException("EquipoHist " + equipoHist + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EquipoHist equipoHist) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            equipoHist = em.merge(equipoHist);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipoHist.getId();
                if (findEquipoHist(id) == null) {
                    throw new NonexistentEntityException("The equipoHist with id " + id + " no longer exists.");
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
            EquipoHist equipoHist;
            try {
                equipoHist = em.getReference(EquipoHist.class, id);
                equipoHist.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipoHist with id " + id + " no longer exists.", enfe);
            }
            em.remove(equipoHist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EquipoHist> findEquipoHistEntities() {
        return findEquipoHistEntities(true, -1, -1);
    }

    public List<EquipoHist> findEquipoHistEntities(int maxResults, int firstResult) {
        return findEquipoHistEntities(false, maxResults, firstResult);
    }

    private List<EquipoHist> findEquipoHistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EquipoHist.class));
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

    public EquipoHist findEquipoHist(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EquipoHist.class, id);
        } finally {
            em.close();
        }
    }
    
    public EquipoHist findEquipoHist(Date fechaPartido, Integer equipoId) {
        EntityManager em = getEntityManager();
        EquipoHist resultadoHist = new EquipoHist();
        fechaPartido = new DateTime(fechaPartido).minusDays(1).toDate();
        
        try {
            Query query = em.createQuery("select e from EquipoHist e where e.equipoId = :equipoId and cast(e.fechaModificacion as date) <= :fechaPartido order by e.fechaModificacion desc");
            query.setParameter("equipoId", equipoId);
            query.setParameter("fechaPartido", fechaPartido);
            resultadoHist = (EquipoHist)query.setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
            
        return resultadoHist;
    }

    public int getEquipoHistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EquipoHist> rt = cq.from(EquipoHist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
