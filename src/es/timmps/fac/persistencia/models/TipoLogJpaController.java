/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.RegistroLog;
import es.timmps.fac.persistencia.TipoLog;
import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class TipoLogJpaController implements Serializable {

    public TipoLogJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoLog tipoLog) {
        if (tipoLog.getRegistroLogCollection() == null) {
            tipoLog.setRegistroLogCollection(new ArrayList<RegistroLog>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<RegistroLog> attachedRegistroLogCollection = new ArrayList<RegistroLog>();
            for (RegistroLog registroLogCollectionRegistroLogToAttach : tipoLog.getRegistroLogCollection()) {
                registroLogCollectionRegistroLogToAttach = em.getReference(registroLogCollectionRegistroLogToAttach.getClass(), registroLogCollectionRegistroLogToAttach.getId());
                attachedRegistroLogCollection.add(registroLogCollectionRegistroLogToAttach);
            }
            tipoLog.setRegistroLogCollection(attachedRegistroLogCollection);
            em.persist(tipoLog);
            for (RegistroLog registroLogCollectionRegistroLog : tipoLog.getRegistroLogCollection()) {
                TipoLog oldTipoOfRegistroLogCollectionRegistroLog = registroLogCollectionRegistroLog.getTipo();
                registroLogCollectionRegistroLog.setTipo(tipoLog);
                registroLogCollectionRegistroLog = em.merge(registroLogCollectionRegistroLog);
                if (oldTipoOfRegistroLogCollectionRegistroLog != null) {
                    oldTipoOfRegistroLogCollectionRegistroLog.getRegistroLogCollection().remove(registroLogCollectionRegistroLog);
                    oldTipoOfRegistroLogCollectionRegistroLog = em.merge(oldTipoOfRegistroLogCollectionRegistroLog);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoLog tipoLog) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoLog persistentTipoLog = em.find(TipoLog.class, tipoLog.getId());
            Collection<RegistroLog> registroLogCollectionOld = persistentTipoLog.getRegistroLogCollection();
            Collection<RegistroLog> registroLogCollectionNew = tipoLog.getRegistroLogCollection();
            List<String> illegalOrphanMessages = null;
            for (RegistroLog registroLogCollectionOldRegistroLog : registroLogCollectionOld) {
                if (!registroLogCollectionNew.contains(registroLogCollectionOldRegistroLog)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegistroLog " + registroLogCollectionOldRegistroLog + " since its tipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<RegistroLog> attachedRegistroLogCollectionNew = new ArrayList<RegistroLog>();
            for (RegistroLog registroLogCollectionNewRegistroLogToAttach : registroLogCollectionNew) {
                registroLogCollectionNewRegistroLogToAttach = em.getReference(registroLogCollectionNewRegistroLogToAttach.getClass(), registroLogCollectionNewRegistroLogToAttach.getId());
                attachedRegistroLogCollectionNew.add(registroLogCollectionNewRegistroLogToAttach);
            }
            registroLogCollectionNew = attachedRegistroLogCollectionNew;
            tipoLog.setRegistroLogCollection(registroLogCollectionNew);
            tipoLog = em.merge(tipoLog);
            for (RegistroLog registroLogCollectionNewRegistroLog : registroLogCollectionNew) {
                if (!registroLogCollectionOld.contains(registroLogCollectionNewRegistroLog)) {
                    TipoLog oldTipoOfRegistroLogCollectionNewRegistroLog = registroLogCollectionNewRegistroLog.getTipo();
                    registroLogCollectionNewRegistroLog.setTipo(tipoLog);
                    registroLogCollectionNewRegistroLog = em.merge(registroLogCollectionNewRegistroLog);
                    if (oldTipoOfRegistroLogCollectionNewRegistroLog != null && !oldTipoOfRegistroLogCollectionNewRegistroLog.equals(tipoLog)) {
                        oldTipoOfRegistroLogCollectionNewRegistroLog.getRegistroLogCollection().remove(registroLogCollectionNewRegistroLog);
                        oldTipoOfRegistroLogCollectionNewRegistroLog = em.merge(oldTipoOfRegistroLogCollectionNewRegistroLog);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoLog.getId();
                if (findTipoLog(id) == null) {
                    throw new NonexistentEntityException("The tipoLog with id " + id + " no longer exists.");
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
            TipoLog tipoLog;
            try {
                tipoLog = em.getReference(TipoLog.class, id);
                tipoLog.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoLog with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RegistroLog> registroLogCollectionOrphanCheck = tipoLog.getRegistroLogCollection();
            for (RegistroLog registroLogCollectionOrphanCheckRegistroLog : registroLogCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoLog (" + tipoLog + ") cannot be destroyed since the RegistroLog " + registroLogCollectionOrphanCheckRegistroLog + " in its registroLogCollection field has a non-nullable tipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoLog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoLog> findTipoLogEntities() {
        return findTipoLogEntities(true, -1, -1);
    }

    public List<TipoLog> findTipoLogEntities(int maxResults, int firstResult) {
        return findTipoLogEntities(false, maxResults, firstResult);
    }

    private List<TipoLog> findTipoLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TipoLog as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TipoLog findTipoLog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoLog.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoLogCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TipoLog as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
