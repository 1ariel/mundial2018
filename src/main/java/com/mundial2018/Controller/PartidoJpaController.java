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
import com.mundial2018.Database.Entities.Ronda;
import com.mundial2018.Database.Entities.Apuesta;
import com.mundial2018.Database.Entities.Partido;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WVQ
 */
public class PartidoJpaController implements Serializable {

    public PartidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partido partido) {
        if (partido.getApuestaCollection() == null) {
            partido.setApuestaCollection(new ArrayList<Apuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ronda rondaId = partido.getRondaId();
            if (rondaId != null) {
                rondaId = em.getReference(rondaId.getClass(), rondaId.getId());
                partido.setRondaId(rondaId);
            }
            Collection<Apuesta> attachedApuestaCollection = new ArrayList<Apuesta>();
            for (Apuesta apuestaCollectionApuestaToAttach : partido.getApuestaCollection()) {
                apuestaCollectionApuestaToAttach = em.getReference(apuestaCollectionApuestaToAttach.getClass(), apuestaCollectionApuestaToAttach.getId());
                attachedApuestaCollection.add(apuestaCollectionApuestaToAttach);
            }
            partido.setApuestaCollection(attachedApuestaCollection);
            em.persist(partido);
            if (rondaId != null) {
                rondaId.getPartidoCollection().add(partido);
                rondaId = em.merge(rondaId);
            }
            for (Apuesta apuestaCollectionApuesta : partido.getApuestaCollection()) {
                Partido oldPartidoIdOfApuestaCollectionApuesta = apuestaCollectionApuesta.getPartidoId();
                apuestaCollectionApuesta.setPartidoId(partido);
                apuestaCollectionApuesta = em.merge(apuestaCollectionApuesta);
                if (oldPartidoIdOfApuestaCollectionApuesta != null) {
                    oldPartidoIdOfApuestaCollectionApuesta.getApuestaCollection().remove(apuestaCollectionApuesta);
                    oldPartidoIdOfApuestaCollectionApuesta = em.merge(oldPartidoIdOfApuestaCollectionApuesta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partido partido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partido persistentPartido = em.find(Partido.class, partido.getId());
            Ronda rondaIdOld = persistentPartido.getRondaId();
            Ronda rondaIdNew = partido.getRondaId();
            Collection<Apuesta> apuestaCollectionOld = persistentPartido.getApuestaCollection();
            Collection<Apuesta> apuestaCollectionNew = partido.getApuestaCollection();
            List<String> illegalOrphanMessages = null;
            for (Apuesta apuestaCollectionOldApuesta : apuestaCollectionOld) {
                if (!apuestaCollectionNew.contains(apuestaCollectionOldApuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Apuesta " + apuestaCollectionOldApuesta + " since its partidoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rondaIdNew != null) {
                rondaIdNew = em.getReference(rondaIdNew.getClass(), rondaIdNew.getId());
                partido.setRondaId(rondaIdNew);
            }
            Collection<Apuesta> attachedApuestaCollectionNew = new ArrayList<Apuesta>();
            for (Apuesta apuestaCollectionNewApuestaToAttach : apuestaCollectionNew) {
                apuestaCollectionNewApuestaToAttach = em.getReference(apuestaCollectionNewApuestaToAttach.getClass(), apuestaCollectionNewApuestaToAttach.getId());
                attachedApuestaCollectionNew.add(apuestaCollectionNewApuestaToAttach);
            }
            apuestaCollectionNew = attachedApuestaCollectionNew;
            partido.setApuestaCollection(apuestaCollectionNew);
            partido = em.merge(partido);
            if (rondaIdOld != null && !rondaIdOld.equals(rondaIdNew)) {
                rondaIdOld.getPartidoCollection().remove(partido);
                rondaIdOld = em.merge(rondaIdOld);
            }
            if (rondaIdNew != null && !rondaIdNew.equals(rondaIdOld)) {
                rondaIdNew.getPartidoCollection().add(partido);
                rondaIdNew = em.merge(rondaIdNew);
            }
            for (Apuesta apuestaCollectionNewApuesta : apuestaCollectionNew) {
                if (!apuestaCollectionOld.contains(apuestaCollectionNewApuesta)) {
                    Partido oldPartidoIdOfApuestaCollectionNewApuesta = apuestaCollectionNewApuesta.getPartidoId();
                    apuestaCollectionNewApuesta.setPartidoId(partido);
                    apuestaCollectionNewApuesta = em.merge(apuestaCollectionNewApuesta);
                    if (oldPartidoIdOfApuestaCollectionNewApuesta != null && !oldPartidoIdOfApuestaCollectionNewApuesta.equals(partido)) {
                        oldPartidoIdOfApuestaCollectionNewApuesta.getApuestaCollection().remove(apuestaCollectionNewApuesta);
                        oldPartidoIdOfApuestaCollectionNewApuesta = em.merge(oldPartidoIdOfApuestaCollectionNewApuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partido.getId();
                if (findPartido(id) == null) {
                    throw new NonexistentEntityException("The partido with id " + id + " no longer exists.");
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
            Partido partido;
            try {
                partido = em.getReference(Partido.class, id);
                partido.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Apuesta> apuestaCollectionOrphanCheck = partido.getApuestaCollection();
            for (Apuesta apuestaCollectionOrphanCheckApuesta : apuestaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Apuesta " + apuestaCollectionOrphanCheckApuesta + " in its apuestaCollection field has a non-nullable partidoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ronda rondaId = partido.getRondaId();
            if (rondaId != null) {
                rondaId.getPartidoCollection().remove(partido);
                rondaId = em.merge(rondaId);
            }
            em.remove(partido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partido> findPartidoEntities() {
        return findPartidoEntities(true, -1, -1);
    }

    public List<Partido> findPartidoEntities(int maxResults, int firstResult) {
        return findPartidoEntities(false, maxResults, firstResult);
    }

    private List<Partido> findPartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partido.class));
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

    public Partido findPartido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partido> rt = cq.from(Partido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
