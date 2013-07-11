/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.DatosPersonales;
import es.timmps.fac.persistencia.pojos.Telefonos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class TelefonosJpaController implements Serializable {

    public TelefonosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telefonos telefonos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosPersonales codigoPersona = telefonos.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona = em.getReference(codigoPersona.getClass(), codigoPersona.getCodigo());
                telefonos.setCodigoPersona(codigoPersona);
            }
            em.persist(telefonos);
            if (codigoPersona != null) {
                codigoPersona.getTelefonosCollection().add(telefonos);
                codigoPersona = em.merge(codigoPersona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefonos telefonos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefonos persistentTelefonos = em.find(Telefonos.class, telefonos.getId());
            DatosPersonales codigoPersonaOld = persistentTelefonos.getCodigoPersona();
            DatosPersonales codigoPersonaNew = telefonos.getCodigoPersona();
            if (codigoPersonaNew != null) {
                codigoPersonaNew = em.getReference(codigoPersonaNew.getClass(), codigoPersonaNew.getCodigo());
                telefonos.setCodigoPersona(codigoPersonaNew);
            }
            telefonos = em.merge(telefonos);
            if (codigoPersonaOld != null && !codigoPersonaOld.equals(codigoPersonaNew)) {
                codigoPersonaOld.getTelefonosCollection().remove(telefonos);
                codigoPersonaOld = em.merge(codigoPersonaOld);
            }
            if (codigoPersonaNew != null && !codigoPersonaNew.equals(codigoPersonaOld)) {
                codigoPersonaNew.getTelefonosCollection().add(telefonos);
                codigoPersonaNew = em.merge(codigoPersonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefonos.getId();
                if (findTelefonos(id) == null) {
                    throw new NonexistentEntityException("The telefonos with id " + id + " no longer exists.");
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
            Telefonos telefonos;
            try {
                telefonos = em.getReference(Telefonos.class, id);
                telefonos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefonos with id " + id + " no longer exists.", enfe);
            }
            DatosPersonales codigoPersona = telefonos.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona.getTelefonosCollection().remove(telefonos);
                codigoPersona = em.merge(codigoPersona);
            }
            em.remove(telefonos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefonos> findTelefonosEntities() {
        return findTelefonosEntities(true, -1, -1);
    }

    public List<Telefonos> findTelefonosEntities(int maxResults, int firstResult) {
        return findTelefonosEntities(false, maxResults, firstResult);
    }

    private List<Telefonos> findTelefonosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefonos.class));
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

    public Telefonos findTelefonos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefonos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefonos> rt = cq.from(Telefonos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
