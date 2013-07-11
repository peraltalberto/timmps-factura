/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.CfGlobal;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.CfTipoValores;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class CfGlobalJpaController implements Serializable {

    public CfGlobalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfGlobal cfGlobal) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfTipoValores tipoDato = cfGlobal.getTipoDato();
            if (tipoDato != null) {
                tipoDato = em.getReference(tipoDato.getClass(), tipoDato.getId());
                cfGlobal.setTipoDato(tipoDato);
            }
            em.persist(cfGlobal);
            if (tipoDato != null) {
                tipoDato.getCfGlobalCollection().add(cfGlobal);
                tipoDato = em.merge(tipoDato);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCfGlobal(cfGlobal.getCodigo()) != null) {
                throw new PreexistingEntityException("CfGlobal " + cfGlobal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfGlobal cfGlobal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfGlobal persistentCfGlobal = em.find(CfGlobal.class, cfGlobal.getCodigo());
            CfTipoValores tipoDatoOld = persistentCfGlobal.getTipoDato();
            CfTipoValores tipoDatoNew = cfGlobal.getTipoDato();
            if (tipoDatoNew != null) {
                tipoDatoNew = em.getReference(tipoDatoNew.getClass(), tipoDatoNew.getId());
                cfGlobal.setTipoDato(tipoDatoNew);
            }
            cfGlobal = em.merge(cfGlobal);
            if (tipoDatoOld != null && !tipoDatoOld.equals(tipoDatoNew)) {
                tipoDatoOld.getCfGlobalCollection().remove(cfGlobal);
                tipoDatoOld = em.merge(tipoDatoOld);
            }
            if (tipoDatoNew != null && !tipoDatoNew.equals(tipoDatoOld)) {
                tipoDatoNew.getCfGlobalCollection().add(cfGlobal);
                tipoDatoNew = em.merge(tipoDatoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cfGlobal.getCodigo();
                if (findCfGlobal(id) == null) {
                    throw new NonexistentEntityException("The cfGlobal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfGlobal cfGlobal;
            try {
                cfGlobal = em.getReference(CfGlobal.class, id);
                cfGlobal.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfGlobal with id " + id + " no longer exists.", enfe);
            }
            CfTipoValores tipoDato = cfGlobal.getTipoDato();
            if (tipoDato != null) {
                tipoDato.getCfGlobalCollection().remove(cfGlobal);
                tipoDato = em.merge(tipoDato);
            }
            em.remove(cfGlobal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfGlobal> findCfGlobalEntities() {
        return findCfGlobalEntities(true, -1, -1);
    }

    public List<CfGlobal> findCfGlobalEntities(int maxResults, int firstResult) {
        return findCfGlobalEntities(false, maxResults, firstResult);
    }

    private List<CfGlobal> findCfGlobalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CfGlobal as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CfGlobal findCfGlobal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfGlobal.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfGlobalCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CfGlobal as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
