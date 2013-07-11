/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.DatosPersonales;
import es.timmps.fac.persistencia.Mails;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class MailsJpaController implements Serializable {

    public MailsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mails mails) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosPersonales codigoPersona = mails.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona = em.getReference(codigoPersona.getClass(), codigoPersona.getCodigo());
                mails.setCodigoPersona(codigoPersona);
            }
            em.persist(mails);
            if (codigoPersona != null) {
                codigoPersona.getMailsCollection().add(mails);
                codigoPersona = em.merge(codigoPersona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mails mails) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mails persistentMails = em.find(Mails.class, mails.getId());
            DatosPersonales codigoPersonaOld = persistentMails.getCodigoPersona();
            DatosPersonales codigoPersonaNew = mails.getCodigoPersona();
            if (codigoPersonaNew != null) {
                codigoPersonaNew = em.getReference(codigoPersonaNew.getClass(), codigoPersonaNew.getCodigo());
                mails.setCodigoPersona(codigoPersonaNew);
            }
            mails = em.merge(mails);
            if (codigoPersonaOld != null && !codigoPersonaOld.equals(codigoPersonaNew)) {
                codigoPersonaOld.getMailsCollection().remove(mails);
                codigoPersonaOld = em.merge(codigoPersonaOld);
            }
            if (codigoPersonaNew != null && !codigoPersonaNew.equals(codigoPersonaOld)) {
                codigoPersonaNew.getMailsCollection().add(mails);
                codigoPersonaNew = em.merge(codigoPersonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mails.getId();
                if (findMails(id) == null) {
                    throw new NonexistentEntityException("The mails with id " + id + " no longer exists.");
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
            Mails mails;
            try {
                mails = em.getReference(Mails.class, id);
                mails.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mails with id " + id + " no longer exists.", enfe);
            }
            DatosPersonales codigoPersona = mails.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona.getMailsCollection().remove(mails);
                codigoPersona = em.merge(codigoPersona);
            }
            em.remove(mails);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mails> findMailsEntities() {
        return findMailsEntities(true, -1, -1);
    }

    public List<Mails> findMailsEntities(int maxResults, int firstResult) {
        return findMailsEntities(false, maxResults, firstResult);
    }

    private List<Mails> findMailsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Mails as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Mails findMails(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mails.class, id);
        } finally {
            em.close();
        }
    }

    public int getMailsCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Mails as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
