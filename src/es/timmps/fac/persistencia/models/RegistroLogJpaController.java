/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.pojos.RegistroLog;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.Usuarios;
import es.timmps.fac.persistencia.pojos.TipoLog;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class RegistroLogJpaController implements Serializable {

    public RegistroLogJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroLog registroLog) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuario = registroLog.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                registroLog.setUsuario(usuario);
            }
            TipoLog tipo = registroLog.getTipo();
            if (tipo != null) {
                tipo = em.getReference(tipo.getClass(), tipo.getId());
                registroLog.setTipo(tipo);
            }
            em.persist(registroLog);
            if (usuario != null) {
                usuario.getRegistroLogCollection().add(registroLog);
                usuario = em.merge(usuario);
            }
            if (tipo != null) {
                tipo.getRegistroLogCollection().add(registroLog);
                tipo = em.merge(tipo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroLog registroLog) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroLog persistentRegistroLog = em.find(RegistroLog.class, registroLog.getId());
            Usuarios usuarioOld = persistentRegistroLog.getUsuario();
            Usuarios usuarioNew = registroLog.getUsuario();
            TipoLog tipoOld = persistentRegistroLog.getTipo();
            TipoLog tipoNew = registroLog.getTipo();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                registroLog.setUsuario(usuarioNew);
            }
            if (tipoNew != null) {
                tipoNew = em.getReference(tipoNew.getClass(), tipoNew.getId());
                registroLog.setTipo(tipoNew);
            }
            registroLog = em.merge(registroLog);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getRegistroLogCollection().remove(registroLog);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getRegistroLogCollection().add(registroLog);
                usuarioNew = em.merge(usuarioNew);
            }
            if (tipoOld != null && !tipoOld.equals(tipoNew)) {
                tipoOld.getRegistroLogCollection().remove(registroLog);
                tipoOld = em.merge(tipoOld);
            }
            if (tipoNew != null && !tipoNew.equals(tipoOld)) {
                tipoNew.getRegistroLogCollection().add(registroLog);
                tipoNew = em.merge(tipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = registroLog.getId();
                if (findRegistroLog(id) == null) {
                    throw new NonexistentEntityException("The registroLog with id " + id + " no longer exists.");
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
            RegistroLog registroLog;
            try {
                registroLog = em.getReference(RegistroLog.class, id);
                registroLog.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroLog with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuario = registroLog.getUsuario();
            if (usuario != null) {
                usuario.getRegistroLogCollection().remove(registroLog);
                usuario = em.merge(usuario);
            }
            TipoLog tipo = registroLog.getTipo();
            if (tipo != null) {
                tipo.getRegistroLogCollection().remove(registroLog);
                tipo = em.merge(tipo);
            }
            em.remove(registroLog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroLog> findRegistroLogEntities() {
        return findRegistroLogEntities(true, -1, -1);
    }

    public List<RegistroLog> findRegistroLogEntities(int maxResults, int firstResult) {
        return findRegistroLogEntities(false, maxResults, firstResult);
    }

    private List<RegistroLog> findRegistroLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroLog.class));
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

    public RegistroLog findRegistroLog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroLog.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroLog> rt = cq.from(RegistroLog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
