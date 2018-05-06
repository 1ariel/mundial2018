/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial.entity.UsuarioEntity;
import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import com.mundial2018.Controller.exceptions.PreexistingEntityException;
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
public class UsuarioEntityJpaController implements Serializable {

    public UsuarioEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioEntity usuarioEntity) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuarioEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioEntity(usuarioEntity.getId()) != null) {
                throw new PreexistingEntityException("UsuarioEntity " + usuarioEntity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioEntity usuarioEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuarioEntity = em.merge(usuarioEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuarioEntity.getId();
                if (findUsuarioEntity(id) == null) {
                    throw new NonexistentEntityException("The usuarioEntity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioEntity usuarioEntity;
            try {
                usuarioEntity = em.getReference(UsuarioEntity.class, id);
                usuarioEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuarioEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioEntity> findUsuarioEntityEntities() {
        return findUsuarioEntityEntities(true, -1, -1);
    }

    public List<UsuarioEntity> findUsuarioEntityEntities(int maxResults, int firstResult) {
        return findUsuarioEntityEntities(false, maxResults, firstResult);
    }

    private List<UsuarioEntity> findUsuarioEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioEntity.class));
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

    public UsuarioEntity findUsuarioEntity(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioEntity> rt = cq.from(UsuarioEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
