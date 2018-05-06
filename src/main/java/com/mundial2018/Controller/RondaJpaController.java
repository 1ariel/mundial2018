/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial2018.Controller.exceptions.IllegalOrphanException;
import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mundial2018.Database.Entities.Partido;
import com.mundial2018.Database.Entities.Ronda;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WVQ
 */
public class RondaJpaController implements Serializable {

    public RondaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ronda ronda) {
        if (ronda.getPartidoCollection() == null) {
            ronda.setPartidoCollection(new ArrayList<Partido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Partido> attachedPartidoCollection = new ArrayList<Partido>();
            for (Partido partidoCollectionPartidoToAttach : ronda.getPartidoCollection()) {
                partidoCollectionPartidoToAttach = em.getReference(partidoCollectionPartidoToAttach.getClass(), partidoCollectionPartidoToAttach.getId());
                attachedPartidoCollection.add(partidoCollectionPartidoToAttach);
            }
            ronda.setPartidoCollection(attachedPartidoCollection);
            em.persist(ronda);
            for (Partido partidoCollectionPartido : ronda.getPartidoCollection()) {
                Ronda oldRondaIdOfPartidoCollectionPartido = partidoCollectionPartido.getRondaId();
                partidoCollectionPartido.setRondaId(ronda);
                partidoCollectionPartido = em.merge(partidoCollectionPartido);
                if (oldRondaIdOfPartidoCollectionPartido != null) {
                    oldRondaIdOfPartidoCollectionPartido.getPartidoCollection().remove(partidoCollectionPartido);
                    oldRondaIdOfPartidoCollectionPartido = em.merge(oldRondaIdOfPartidoCollectionPartido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ronda ronda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ronda persistentRonda = em.find(Ronda.class, ronda.getId());
            Collection<Partido> partidoCollectionOld = persistentRonda.getPartidoCollection();
            Collection<Partido> partidoCollectionNew = ronda.getPartidoCollection();
            List<String> illegalOrphanMessages = null;
            for (Partido partidoCollectionOldPartido : partidoCollectionOld) {
                if (!partidoCollectionNew.contains(partidoCollectionOldPartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partido " + partidoCollectionOldPartido + " since its rondaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Partido> attachedPartidoCollectionNew = new ArrayList<Partido>();
            for (Partido partidoCollectionNewPartidoToAttach : partidoCollectionNew) {
                partidoCollectionNewPartidoToAttach = em.getReference(partidoCollectionNewPartidoToAttach.getClass(), partidoCollectionNewPartidoToAttach.getId());
                attachedPartidoCollectionNew.add(partidoCollectionNewPartidoToAttach);
            }
            partidoCollectionNew = attachedPartidoCollectionNew;
            ronda.setPartidoCollection(partidoCollectionNew);
            ronda = em.merge(ronda);
            for (Partido partidoCollectionNewPartido : partidoCollectionNew) {
                if (!partidoCollectionOld.contains(partidoCollectionNewPartido)) {
                    Ronda oldRondaIdOfPartidoCollectionNewPartido = partidoCollectionNewPartido.getRondaId();
                    partidoCollectionNewPartido.setRondaId(ronda);
                    partidoCollectionNewPartido = em.merge(partidoCollectionNewPartido);
                    if (oldRondaIdOfPartidoCollectionNewPartido != null && !oldRondaIdOfPartidoCollectionNewPartido.equals(ronda)) {
                        oldRondaIdOfPartidoCollectionNewPartido.getPartidoCollection().remove(partidoCollectionNewPartido);
                        oldRondaIdOfPartidoCollectionNewPartido = em.merge(oldRondaIdOfPartidoCollectionNewPartido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ronda.getId();
                if (findRonda(id) == null) {
                    throw new NonexistentEntityException("The ronda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ronda ronda;
            try {
                ronda = em.getReference(Ronda.class, id);
                ronda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ronda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Partido> partidoCollectionOrphanCheck = ronda.getPartidoCollection();
            for (Partido partidoCollectionOrphanCheckPartido : partidoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ronda (" + ronda + ") cannot be destroyed since the Partido " + partidoCollectionOrphanCheckPartido + " in its partidoCollection field has a non-nullable rondaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ronda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ronda> findRondaEntities() {
        return findRondaEntities(true, -1, -1);
    }

    public List<Ronda> findRondaEntities(int maxResults, int firstResult) {
        return findRondaEntities(false, maxResults, firstResult);
    }

    private List<Ronda> findRondaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ronda.class));
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

    public Ronda findRonda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ronda.class, id);
        } finally {
            em.close();
        }
    }

    public int getRondaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ronda> rt = cq.from(Ronda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
