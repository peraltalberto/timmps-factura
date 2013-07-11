/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.CfProvAlbCustomEmp;
import es.timmps.fac.persistencia.pojos.ProvAlbCab;
import es.timmps.fac.persistencia.pojos.ProvAlbCustom;
import es.timmps.fac.persistencia.pojos.ProvAlbCustomPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class ProvAlbCustomJpaController implements Serializable {

    public ProvAlbCustomJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProvAlbCustom provAlbCustom) throws PreexistingEntityException, Exception {
        if (provAlbCustom.getProvAlbCustomPK() == null) {
            provAlbCustom.setProvAlbCustomPK(new ProvAlbCustomPK());
        }
        provAlbCustom.getProvAlbCustomPK().setCodCampo(provAlbCustom.getCfProvAlbCustomEmp().getId());
        provAlbCustom.getProvAlbCustomPK().setCodCab(provAlbCustom.getProvAlbCab().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfProvAlbCustomEmp cfProvAlbCustomEmp = provAlbCustom.getCfProvAlbCustomEmp();
            if (cfProvAlbCustomEmp != null) {
                cfProvAlbCustomEmp = em.getReference(cfProvAlbCustomEmp.getClass(), cfProvAlbCustomEmp.getId());
                provAlbCustom.setCfProvAlbCustomEmp(cfProvAlbCustomEmp);
            }
            ProvAlbCab provAlbCab = provAlbCustom.getProvAlbCab();
            if (provAlbCab != null) {
                provAlbCab = em.getReference(provAlbCab.getClass(), provAlbCab.getId());
                provAlbCustom.setProvAlbCab(provAlbCab);
            }
            em.persist(provAlbCustom);
            if (cfProvAlbCustomEmp != null) {
                cfProvAlbCustomEmp.getProvAlbCustomCollection().add(provAlbCustom);
                cfProvAlbCustomEmp = em.merge(cfProvAlbCustomEmp);
            }
            if (provAlbCab != null) {
                provAlbCab.getProvAlbCustomCollection().add(provAlbCustom);
                provAlbCab = em.merge(provAlbCab);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProvAlbCustom(provAlbCustom.getProvAlbCustomPK()) != null) {
                throw new PreexistingEntityException("ProvAlbCustom " + provAlbCustom + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProvAlbCustom provAlbCustom) throws NonexistentEntityException, Exception {
        provAlbCustom.getProvAlbCustomPK().setCodCampo(provAlbCustom.getCfProvAlbCustomEmp().getId());
        provAlbCustom.getProvAlbCustomPK().setCodCab(provAlbCustom.getProvAlbCab().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvAlbCustom persistentProvAlbCustom = em.find(ProvAlbCustom.class, provAlbCustom.getProvAlbCustomPK());
            CfProvAlbCustomEmp cfProvAlbCustomEmpOld = persistentProvAlbCustom.getCfProvAlbCustomEmp();
            CfProvAlbCustomEmp cfProvAlbCustomEmpNew = provAlbCustom.getCfProvAlbCustomEmp();
            ProvAlbCab provAlbCabOld = persistentProvAlbCustom.getProvAlbCab();
            ProvAlbCab provAlbCabNew = provAlbCustom.getProvAlbCab();
            if (cfProvAlbCustomEmpNew != null) {
                cfProvAlbCustomEmpNew = em.getReference(cfProvAlbCustomEmpNew.getClass(), cfProvAlbCustomEmpNew.getId());
                provAlbCustom.setCfProvAlbCustomEmp(cfProvAlbCustomEmpNew);
            }
            if (provAlbCabNew != null) {
                provAlbCabNew = em.getReference(provAlbCabNew.getClass(), provAlbCabNew.getId());
                provAlbCustom.setProvAlbCab(provAlbCabNew);
            }
            provAlbCustom = em.merge(provAlbCustom);
            if (cfProvAlbCustomEmpOld != null && !cfProvAlbCustomEmpOld.equals(cfProvAlbCustomEmpNew)) {
                cfProvAlbCustomEmpOld.getProvAlbCustomCollection().remove(provAlbCustom);
                cfProvAlbCustomEmpOld = em.merge(cfProvAlbCustomEmpOld);
            }
            if (cfProvAlbCustomEmpNew != null && !cfProvAlbCustomEmpNew.equals(cfProvAlbCustomEmpOld)) {
                cfProvAlbCustomEmpNew.getProvAlbCustomCollection().add(provAlbCustom);
                cfProvAlbCustomEmpNew = em.merge(cfProvAlbCustomEmpNew);
            }
            if (provAlbCabOld != null && !provAlbCabOld.equals(provAlbCabNew)) {
                provAlbCabOld.getProvAlbCustomCollection().remove(provAlbCustom);
                provAlbCabOld = em.merge(provAlbCabOld);
            }
            if (provAlbCabNew != null && !provAlbCabNew.equals(provAlbCabOld)) {
                provAlbCabNew.getProvAlbCustomCollection().add(provAlbCustom);
                provAlbCabNew = em.merge(provAlbCabNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProvAlbCustomPK id = provAlbCustom.getProvAlbCustomPK();
                if (findProvAlbCustom(id) == null) {
                    throw new NonexistentEntityException("The provAlbCustom with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProvAlbCustomPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvAlbCustom provAlbCustom;
            try {
                provAlbCustom = em.getReference(ProvAlbCustom.class, id);
                provAlbCustom.getProvAlbCustomPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provAlbCustom with id " + id + " no longer exists.", enfe);
            }
            CfProvAlbCustomEmp cfProvAlbCustomEmp = provAlbCustom.getCfProvAlbCustomEmp();
            if (cfProvAlbCustomEmp != null) {
                cfProvAlbCustomEmp.getProvAlbCustomCollection().remove(provAlbCustom);
                cfProvAlbCustomEmp = em.merge(cfProvAlbCustomEmp);
            }
            ProvAlbCab provAlbCab = provAlbCustom.getProvAlbCab();
            if (provAlbCab != null) {
                provAlbCab.getProvAlbCustomCollection().remove(provAlbCustom);
                provAlbCab = em.merge(provAlbCab);
            }
            em.remove(provAlbCustom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProvAlbCustom> findProvAlbCustomEntities() {
        return findProvAlbCustomEntities(true, -1, -1);
    }

    public List<ProvAlbCustom> findProvAlbCustomEntities(int maxResults, int firstResult) {
        return findProvAlbCustomEntities(false, maxResults, firstResult);
    }

    private List<ProvAlbCustom> findProvAlbCustomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProvAlbCustom.class));
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

    public ProvAlbCustom findProvAlbCustom(ProvAlbCustomPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProvAlbCustom.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvAlbCustomCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProvAlbCustom> rt = cq.from(ProvAlbCustom.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
