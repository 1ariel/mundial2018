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
import com.mundial2018.Database.Entities.Resultado;
import com.mundial2018.Database.Entities.Apuesta;
import com.mundial2018.Database.Entities.Empleado;
import java.util.ArrayList;
import java.util.Collection;
import com.mundial2018.Database.Entities.Login;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WVQ
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        if (empleado.getApuestaCollection() == null) {
            empleado.setApuestaCollection(new ArrayList<Apuesta>());
        }
        if (empleado.getLoginCollection() == null) {
            empleado.setLoginCollection(new ArrayList<Login>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Resultado resultado = empleado.getResultado();
            if (resultado != null) {
                resultado = em.getReference(resultado.getClass(), resultado.getId());
                empleado.setResultado(resultado);
            }
            Collection<Apuesta> attachedApuestaCollection = new ArrayList<Apuesta>();
            for (Apuesta apuestaCollectionApuestaToAttach : empleado.getApuestaCollection()) {
                apuestaCollectionApuestaToAttach = em.getReference(apuestaCollectionApuestaToAttach.getClass(), apuestaCollectionApuestaToAttach.getId());
                attachedApuestaCollection.add(apuestaCollectionApuestaToAttach);
            }
            empleado.setApuestaCollection(attachedApuestaCollection);
            Collection<Login> attachedLoginCollection = new ArrayList<Login>();
            for (Login loginCollectionLoginToAttach : empleado.getLoginCollection()) {
                loginCollectionLoginToAttach = em.getReference(loginCollectionLoginToAttach.getClass(), loginCollectionLoginToAttach.getLoginPK());
                attachedLoginCollection.add(loginCollectionLoginToAttach);
            }
            empleado.setLoginCollection(attachedLoginCollection);
            em.persist(empleado);
            if (resultado != null) {
                Empleado oldEmpleadoIdOfResultado = resultado.getEmpleadoId();
                if (oldEmpleadoIdOfResultado != null) {
                    oldEmpleadoIdOfResultado.setResultado(null);
                    oldEmpleadoIdOfResultado = em.merge(oldEmpleadoIdOfResultado);
                }
                resultado.setEmpleadoId(empleado);
                resultado = em.merge(resultado);
            }
            for (Apuesta apuestaCollectionApuesta : empleado.getApuestaCollection()) {
                Empleado oldEmpleadoidOfApuestaCollectionApuesta = apuestaCollectionApuesta.getEmpleadoid();
                apuestaCollectionApuesta.setEmpleadoid(empleado);
                apuestaCollectionApuesta = em.merge(apuestaCollectionApuesta);
                if (oldEmpleadoidOfApuestaCollectionApuesta != null) {
                    oldEmpleadoidOfApuestaCollectionApuesta.getApuestaCollection().remove(apuestaCollectionApuesta);
                    oldEmpleadoidOfApuestaCollectionApuesta = em.merge(oldEmpleadoidOfApuestaCollectionApuesta);
                }
            }
            for (Login loginCollectionLogin : empleado.getLoginCollection()) {
                Empleado oldEmpleadoOfLoginCollectionLogin = loginCollectionLogin.getEmpleado();
                loginCollectionLogin.setEmpleado(empleado);
                loginCollectionLogin = em.merge(loginCollectionLogin);
                if (oldEmpleadoOfLoginCollectionLogin != null) {
                    oldEmpleadoOfLoginCollectionLogin.getLoginCollection().remove(loginCollectionLogin);
                    oldEmpleadoOfLoginCollectionLogin = em.merge(oldEmpleadoOfLoginCollectionLogin);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getId());
            Resultado resultadoOld = persistentEmpleado.getResultado();
            Resultado resultadoNew = empleado.getResultado();
            Collection<Apuesta> apuestaCollectionOld = persistentEmpleado.getApuestaCollection();
            Collection<Apuesta> apuestaCollectionNew = empleado.getApuestaCollection();
            Collection<Login> loginCollectionOld = persistentEmpleado.getLoginCollection();
            Collection<Login> loginCollectionNew = empleado.getLoginCollection();
            List<String> illegalOrphanMessages = null;
            if (resultadoOld != null && !resultadoOld.equals(resultadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Resultado " + resultadoOld + " since its empleadoId field is not nullable.");
            }
            for (Apuesta apuestaCollectionOldApuesta : apuestaCollectionOld) {
                if (!apuestaCollectionNew.contains(apuestaCollectionOldApuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Apuesta " + apuestaCollectionOldApuesta + " since its empleadoid field is not nullable.");
                }
            }
            for (Login loginCollectionOldLogin : loginCollectionOld) {
                if (!loginCollectionNew.contains(loginCollectionOldLogin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Login " + loginCollectionOldLogin + " since its empleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (resultadoNew != null) {
                resultadoNew = em.getReference(resultadoNew.getClass(), resultadoNew.getId());
                empleado.setResultado(resultadoNew);
            }
            Collection<Apuesta> attachedApuestaCollectionNew = new ArrayList<Apuesta>();
            for (Apuesta apuestaCollectionNewApuestaToAttach : apuestaCollectionNew) {
                apuestaCollectionNewApuestaToAttach = em.getReference(apuestaCollectionNewApuestaToAttach.getClass(), apuestaCollectionNewApuestaToAttach.getId());
                attachedApuestaCollectionNew.add(apuestaCollectionNewApuestaToAttach);
            }
            apuestaCollectionNew = attachedApuestaCollectionNew;
            empleado.setApuestaCollection(apuestaCollectionNew);
            Collection<Login> attachedLoginCollectionNew = new ArrayList<Login>();
            for (Login loginCollectionNewLoginToAttach : loginCollectionNew) {
                loginCollectionNewLoginToAttach = em.getReference(loginCollectionNewLoginToAttach.getClass(), loginCollectionNewLoginToAttach.getLoginPK());
                attachedLoginCollectionNew.add(loginCollectionNewLoginToAttach);
            }
            loginCollectionNew = attachedLoginCollectionNew;
            empleado.setLoginCollection(loginCollectionNew);
            empleado = em.merge(empleado);
            if (resultadoNew != null && !resultadoNew.equals(resultadoOld)) {
                Empleado oldEmpleadoIdOfResultado = resultadoNew.getEmpleadoId();
                if (oldEmpleadoIdOfResultado != null) {
                    oldEmpleadoIdOfResultado.setResultado(null);
                    oldEmpleadoIdOfResultado = em.merge(oldEmpleadoIdOfResultado);
                }
                resultadoNew.setEmpleadoId(empleado);
                resultadoNew = em.merge(resultadoNew);
            }
            for (Apuesta apuestaCollectionNewApuesta : apuestaCollectionNew) {
                if (!apuestaCollectionOld.contains(apuestaCollectionNewApuesta)) {
                    Empleado oldEmpleadoidOfApuestaCollectionNewApuesta = apuestaCollectionNewApuesta.getEmpleadoid();
                    apuestaCollectionNewApuesta.setEmpleadoid(empleado);
                    apuestaCollectionNewApuesta = em.merge(apuestaCollectionNewApuesta);
                    if (oldEmpleadoidOfApuestaCollectionNewApuesta != null && !oldEmpleadoidOfApuestaCollectionNewApuesta.equals(empleado)) {
                        oldEmpleadoidOfApuestaCollectionNewApuesta.getApuestaCollection().remove(apuestaCollectionNewApuesta);
                        oldEmpleadoidOfApuestaCollectionNewApuesta = em.merge(oldEmpleadoidOfApuestaCollectionNewApuesta);
                    }
                }
            }
            for (Login loginCollectionNewLogin : loginCollectionNew) {
                if (!loginCollectionOld.contains(loginCollectionNewLogin)) {
                    Empleado oldEmpleadoOfLoginCollectionNewLogin = loginCollectionNewLogin.getEmpleado();
                    loginCollectionNewLogin.setEmpleado(empleado);
                    loginCollectionNewLogin = em.merge(loginCollectionNewLogin);
                    if (oldEmpleadoOfLoginCollectionNewLogin != null && !oldEmpleadoOfLoginCollectionNewLogin.equals(empleado)) {
                        oldEmpleadoOfLoginCollectionNewLogin.getLoginCollection().remove(loginCollectionNewLogin);
                        oldEmpleadoOfLoginCollectionNewLogin = em.merge(oldEmpleadoOfLoginCollectionNewLogin);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Resultado resultadoOrphanCheck = empleado.getResultado();
            if (resultadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Resultado " + resultadoOrphanCheck + " in its resultado field has a non-nullable empleadoId field.");
            }
            Collection<Apuesta> apuestaCollectionOrphanCheck = empleado.getApuestaCollection();
            for (Apuesta apuestaCollectionOrphanCheckApuesta : apuestaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Apuesta " + apuestaCollectionOrphanCheckApuesta + " in its apuestaCollection field has a non-nullable empleadoid field.");
            }
            Collection<Login> loginCollectionOrphanCheck = empleado.getLoginCollection();
            for (Login loginCollectionOrphanCheckLogin : loginCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Login " + loginCollectionOrphanCheckLogin + " in its loginCollection field has a non-nullable empleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
