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
import com.mundial2018.Database.Entities.Equipo;
import com.mundial2018.Database.Entities.Grupo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WVQ
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) {
        if (grupo.getEquipoList() == null) {
            grupo.setEquipoList(new ArrayList<Equipo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Equipo> attachedEquipoCollection = new ArrayList<Equipo>();
            for (Equipo equipoCollectionEquipoToAttach : grupo.getEquipoList()) {
                equipoCollectionEquipoToAttach = em.getReference(equipoCollectionEquipoToAttach.getClass(), equipoCollectionEquipoToAttach.getId());
                attachedEquipoCollection.add(equipoCollectionEquipoToAttach);
            }
            grupo.setEquipoList(attachedEquipoCollection);
            em.persist(grupo);
            for (Equipo equipoCollectionEquipo : grupo.getEquipoList()) {
                Grupo oldGrupoidOfEquipoCollectionEquipo = equipoCollectionEquipo.getGrupoid();
                equipoCollectionEquipo.setGrupoid(grupo);
                equipoCollectionEquipo = em.merge(equipoCollectionEquipo);
                if (oldGrupoidOfEquipoCollectionEquipo != null) {
                    oldGrupoidOfEquipoCollectionEquipo.getEquipoList().remove(equipoCollectionEquipo);
                    oldGrupoidOfEquipoCollectionEquipo = em.merge(oldGrupoidOfEquipoCollectionEquipo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getId());
            List<Equipo> equipoCollectionOld = persistentGrupo.getEquipoList();
            List<Equipo> equipoCollectionNew = grupo.getEquipoList();
            List<String> illegalOrphanMessages = null;
            for (Equipo equipoCollectionOldEquipo : equipoCollectionOld) {
                if (!equipoCollectionNew.contains(equipoCollectionOldEquipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Equipo " + equipoCollectionOldEquipo + " since its grupoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Equipo> attachedEquipoCollectionNew = new ArrayList<Equipo>();
            for (Equipo equipoCollectionNewEquipoToAttach : equipoCollectionNew) {
                equipoCollectionNewEquipoToAttach = em.getReference(equipoCollectionNewEquipoToAttach.getClass(), equipoCollectionNewEquipoToAttach.getId());
                attachedEquipoCollectionNew.add(equipoCollectionNewEquipoToAttach);
            }
            equipoCollectionNew = attachedEquipoCollectionNew;
            grupo.setEquipoList(equipoCollectionNew);
            grupo = em.merge(grupo);
            for (Equipo equipoCollectionNewEquipo : equipoCollectionNew) {
                if (!equipoCollectionOld.contains(equipoCollectionNewEquipo)) {
                    Grupo oldGrupoidOfEquipoCollectionNewEquipo = equipoCollectionNewEquipo.getGrupoid();
                    equipoCollectionNewEquipo.setGrupoid(grupo);
                    equipoCollectionNewEquipo = em.merge(equipoCollectionNewEquipo);
                    if (oldGrupoidOfEquipoCollectionNewEquipo != null && !oldGrupoidOfEquipoCollectionNewEquipo.equals(grupo)) {
                        oldGrupoidOfEquipoCollectionNewEquipo.getEquipoList().remove(equipoCollectionNewEquipo);
                        oldGrupoidOfEquipoCollectionNewEquipo = em.merge(oldGrupoidOfEquipoCollectionNewEquipo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupo.getId();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Equipo> equipoCollectionOrphanCheck = grupo.getEquipoList();
            for (Equipo equipoCollectionOrphanCheckEquipo : equipoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Equipo " + equipoCollectionOrphanCheckEquipo + " in its equipoCollection field has a non-nullable grupoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
