/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import com.mundial2018.Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mundial2018.Database.Entities.Empleado;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Entities.LoginPK;
import com.mundial2018.Database.Persistance.EntityManagerFactoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WVQ
 */
public class LoginJpaController implements Serializable {

    public LoginJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Login login) throws PreexistingEntityException, Exception {
        if (login.getLoginPK() == null) {
            login.setLoginPK(new LoginPK());
        }
        login.getLoginPK().setEmpleadoid(login.getEmpleado().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = login.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getId());
                login.setEmpleado(empleado);
            }
            em.persist(login);
            if (empleado != null) {
                empleado.getLoginCollection().add(login);
                empleado = em.merge(empleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLogin(login.getLoginPK()) != null) {
                throw new PreexistingEntityException("Login " + login + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Login login) throws NonexistentEntityException, Exception {
        login.getLoginPK().setEmpleadoid(login.getEmpleado().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login persistentLogin = em.find(Login.class, login.getLoginPK());
            Empleado empleadoOld = persistentLogin.getEmpleado();
            Empleado empleadoNew = login.getEmpleado();
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getId());
                login.setEmpleado(empleadoNew);
            }
            login = em.merge(login);
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getLoginCollection().remove(login);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getLoginCollection().add(login);
                empleadoNew = em.merge(empleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LoginPK id = login.getLoginPK();
                if (findLogin(id) == null) {
                    throw new NonexistentEntityException("The login with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LoginPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login login;
            try {
                login = em.getReference(Login.class, id);
                login.getLoginPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The login with id " + id + " no longer exists.", enfe);
            }
            Empleado empleado = login.getEmpleado();
            if (empleado != null) {
                empleado.getLoginCollection().remove(login);
                empleado = em.merge(empleado);
            }
            em.remove(login);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Login> findLoginEntities() {
        return findLoginEntities(true, -1, -1);
    }

    public List<Login> findLoginEntities(int maxResults, int firstResult) {
        return findLoginEntities(false, maxResults, firstResult);
    }

    private List<Login> findLoginEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Login.class));
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

    public Login findLogin(LoginPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Login.class, id);
        } finally {
            em.close();
        }
    }
    
    public Login checkLogin(Login login) {
        EntityManager em = null;
        Login nuevo = new Login();
        
        try {
            em = getEntityManager();
            Query query = em.createNamedQuery("Login.CheckLogin");
            query.setParameter("user", login.getUsername());
            query.setParameter("password", login.getPassword());
            nuevo = (Login)query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        em.close();
        }
            
        return nuevo;
    }

    public int getLoginCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Login> rt = cq.from(Login.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
