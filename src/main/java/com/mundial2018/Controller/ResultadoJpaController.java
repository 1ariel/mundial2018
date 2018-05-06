/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mundial2018.Database.Entities.Empleado;
import com.mundial2018.Database.Entities.Resultado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WVQ
 */
public class ResultadoJpaController implements Serializable {

    public ResultadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resultado resultado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleadoId = resultado.getEmpleadoId();
            if (empleadoId != null) {
                empleadoId = em.getReference(empleadoId.getClass(), empleadoId.getId());
                resultado.setEmpleadoId(empleadoId);
            }
            em.persist(resultado);
            if (empleadoId != null) {
                empleadoId.getResultadoCollection().add(resultado);
                empleadoId = em.merge(empleadoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Resultado resultado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Resultado persistentResultado = em.find(Resultado.class, resultado.getId());
            Empleado empleadoIdOld = persistentResultado.getEmpleadoId();
            Empleado empleadoIdNew = resultado.getEmpleadoId();
            if (empleadoIdNew != null) {
                empleadoIdNew = em.getReference(empleadoIdNew.getClass(), empleadoIdNew.getId());
                resultado.setEmpleadoId(empleadoIdNew);
            }
            resultado = em.merge(resultado);
            if (empleadoIdOld != null && !empleadoIdOld.equals(empleadoIdNew)) {
                empleadoIdOld.getResultadoCollection().remove(resultado);
                empleadoIdOld = em.merge(empleadoIdOld);
            }
            if (empleadoIdNew != null && !empleadoIdNew.equals(empleadoIdOld)) {
                empleadoIdNew.getResultadoCollection().add(resultado);
                empleadoIdNew = em.merge(empleadoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = resultado.getId();
                if (findResultado(id) == null) {
                    throw new NonexistentEntityException("The resultado with id " + id + " no longer exists.");
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
            Resultado resultado;
            try {
                resultado = em.getReference(Resultado.class, id);
                resultado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resultado with id " + id + " no longer exists.", enfe);
            }
            Empleado empleadoId = resultado.getEmpleadoId();
            if (empleadoId != null) {
                empleadoId.getResultadoCollection().remove(resultado);
                empleadoId = em.merge(empleadoId);
            }
            em.remove(resultado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Resultado> findResultadoEntities() {
        return findResultadoEntities(true, -1, -1);
    }

    public List<Resultado> findResultadoEntities(int maxResults, int firstResult) {
        return findResultadoEntities(false, maxResults, firstResult);
    }

    private List<Resultado> findResultadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resultado.class));
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

    public Resultado findResultado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resultado.class, id);
        } finally {
            em.close();
        }
    }

    public int getResultadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resultado> rt = cq.from(Resultado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
