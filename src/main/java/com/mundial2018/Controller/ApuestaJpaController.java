/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import com.mundial2018.Database.Entities.Apuesta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mundial2018.Database.Entities.Empleado;
import com.mundial2018.Database.Entities.Partido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WVQ
 */
public class ApuestaJpaController implements Serializable {

    public ApuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Apuesta apuesta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleadoid = apuesta.getEmpleadoid();
            if (empleadoid != null) {
                empleadoid = em.getReference(empleadoid.getClass(), empleadoid.getId());
                apuesta.setEmpleadoid(empleadoid);
            }
            Partido partidoId = apuesta.getPartidoId();
            if (partidoId != null) {
                partidoId = em.getReference(partidoId.getClass(), partidoId.getId());
                apuesta.setPartidoId(partidoId);
            }
            em.persist(apuesta);
            if (empleadoid != null) {
                empleadoid.getApuestaCollection().add(apuesta);
                empleadoid = em.merge(empleadoid);
            }
            if (partidoId != null) {
                partidoId.getApuestaCollection().add(apuesta);
                partidoId = em.merge(partidoId);
            }
            em.getTransaction().commit();
     
     
        } 
          catch(Exception e){
               String pp = e.getMessage();
               
               }
        
        finally {
            if (em != null) {
                 em.close();
            }
        }
    }

    public void edit(Apuesta apuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apuesta persistentApuesta = em.find(Apuesta.class, apuesta.getId());
            Empleado empleadoidOld = persistentApuesta.getEmpleadoid();
            Empleado empleadoidNew = apuesta.getEmpleadoid();
            Partido partidoIdOld = persistentApuesta.getPartidoId();
            Partido partidoIdNew = apuesta.getPartidoId();
            if (empleadoidNew != null) {
                empleadoidNew = em.getReference(empleadoidNew.getClass(), empleadoidNew.getId());
                apuesta.setEmpleadoid(empleadoidNew);
            }
            if (partidoIdNew != null) {
                partidoIdNew = em.getReference(partidoIdNew.getClass(), partidoIdNew.getId());
                apuesta.setPartidoId(partidoIdNew);
            }
            apuesta = em.merge(apuesta);
            if (empleadoidOld != null && !empleadoidOld.equals(empleadoidNew)) {
                empleadoidOld.getApuestaCollection().remove(apuesta);
                empleadoidOld = em.merge(empleadoidOld);
            }
            if (empleadoidNew != null && !empleadoidNew.equals(empleadoidOld)) {
                empleadoidNew.getApuestaCollection().add(apuesta);
                empleadoidNew = em.merge(empleadoidNew);
            }
            if (partidoIdOld != null && !partidoIdOld.equals(partidoIdNew)) {
                partidoIdOld.getApuestaCollection().remove(apuesta);
                partidoIdOld = em.merge(partidoIdOld);
            }
            if (partidoIdNew != null && !partidoIdNew.equals(partidoIdOld)) {
                partidoIdNew.getApuestaCollection().add(apuesta);
                partidoIdNew = em.merge(partidoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = apuesta.getId();
                if (findApuesta(id) == null) {
                    throw new NonexistentEntityException("The apuesta with id " + id + " no longer exists.");
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
            Apuesta apuesta;
            try {
                apuesta = em.getReference(Apuesta.class, id);
                apuesta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The apuesta with id " + id + " no longer exists.", enfe);
            }
            Empleado empleadoid = apuesta.getEmpleadoid();
            if (empleadoid != null) {
                empleadoid.getApuestaCollection().remove(apuesta);
                empleadoid = em.merge(empleadoid);
            }
            Partido partidoId = apuesta.getPartidoId();
            if (partidoId != null) {
                partidoId.getApuestaCollection().remove(apuesta);
                partidoId = em.merge(partidoId);
            }
            em.remove(apuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Apuesta> findApuestaEntities() {
        return findApuestaEntities(true, -1, -1);
    }

    public List<Apuesta> findApuestaEntities(int maxResults, int firstResult) {
        return findApuestaEntities(false, maxResults, firstResult);
    }

    private List<Apuesta> findApuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Apuesta.class));
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

    public Apuesta findApuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Apuesta.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Apuesta> findApuestasById(Integer partidoId) {
        EntityManager em = getEntityManager();
        List<Apuesta> apuestas = new ArrayList<>();
        
        try {
            Query query = em.createQuery("select a from Apuesta a where a.partidoId.id = :partidoId");
            query.setParameter("partidoId", partidoId);
            apuestas = (List<Apuesta>)query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return apuestas;
    }
    
    public Apuesta findViaEmpleadoAndPartido(Empleado idEmpleado, Partido idPartido){
        EntityManager em = getEntityManager();
        try {
            Apuesta aux = (Apuesta) em.createNamedQuery("Apuesta.findByEmpleadoAndPartido")
                   .setParameter("empleadoid", idEmpleado)
                   .setParameter("partidoId", idPartido).getSingleResult();
            
            return aux;
        } catch(Exception e){
            return null;
        } finally {
              em.close();
        }
    }

    public int getApuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Apuesta> rt = cq.from(Apuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
