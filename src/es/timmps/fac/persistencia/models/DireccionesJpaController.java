/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.DatosPersonales;
import es.timmps.fac.persistencia.Direcciones;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class DireccionesJpaController implements Serializable {

    public DireccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direcciones direcciones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosPersonales codigoPersona = direcciones.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona = em.getReference(codigoPersona.getClass(), codigoPersona.getCodigo());
                direcciones.setCodigoPersona(codigoPersona);
            }
            em.persist(direcciones);
            if (codigoPersona != null) {
                codigoPersona.getDireccionesCollection().add(direcciones);
                codigoPersona = em.merge(codigoPersona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direcciones direcciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direcciones persistentDirecciones = em.find(Direcciones.class, direcciones.getId());
            DatosPersonales codigoPersonaOld = persistentDirecciones.getCodigoPersona();
            DatosPersonales codigoPersonaNew = direcciones.getCodigoPersona();
            if (codigoPersonaNew != null) {
                codigoPersonaNew = em.getReference(codigoPersonaNew.getClass(), codigoPersonaNew.getCodigo());
                direcciones.setCodigoPersona(codigoPersonaNew);
            }
            direcciones = em.merge(direcciones);
            if (codigoPersonaOld != null && !codigoPersonaOld.equals(codigoPersonaNew)) {
                codigoPersonaOld.getDireccionesCollection().remove(direcciones);
                codigoPersonaOld = em.merge(codigoPersonaOld);
            }
            if (codigoPersonaNew != null && !codigoPersonaNew.equals(codigoPersonaOld)) {
                codigoPersonaNew.getDireccionesCollection().add(direcciones);
                codigoPersonaNew = em.merge(codigoPersonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = direcciones.getId();
                if (findDirecciones(id) == null) {
                    throw new NonexistentEntityException("The direcciones with id " + id + " no longer exists.");
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
            Direcciones direcciones;
            try {
                direcciones = em.getReference(Direcciones.class, id);
                direcciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direcciones with id " + id + " no longer exists.", enfe);
            }
            DatosPersonales codigoPersona = direcciones.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona.getDireccionesCollection().remove(direcciones);
                codigoPersona = em.merge(codigoPersona);
            }
            em.remove(direcciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direcciones> findDireccionesEntities() {
        return findDireccionesEntities(true, -1, -1);
    }

    public List<Direcciones> findDireccionesEntities(int maxResults, int firstResult) {
        return findDireccionesEntities(false, maxResults, firstResult);
    }

    private List<Direcciones> findDireccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Direcciones as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Direcciones findDirecciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direcciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionesCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Direcciones as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
