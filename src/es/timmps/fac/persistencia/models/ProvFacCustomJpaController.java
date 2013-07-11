/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.CfProvFacCustomEmp;
import es.timmps.fac.persistencia.ProvFacCab;
import es.timmps.fac.persistencia.ProvFacCustom;
import es.timmps.fac.persistencia.ProvFacCustomPK;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class ProvFacCustomJpaController implements Serializable {

    public ProvFacCustomJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProvFacCustom provFacCustom) throws PreexistingEntityException, Exception {
        if (provFacCustom.getProvFacCustomPK() == null) {
            provFacCustom.setProvFacCustomPK(new ProvFacCustomPK());
        }
        provFacCustom.getProvFacCustomPK().setCodCab(provFacCustom.getProvFacCab().getId());
        provFacCustom.getProvFacCustomPK().setCodCampo(provFacCustom.getCfProvFacCustomEmp().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfProvFacCustomEmp cfProvFacCustomEmp = provFacCustom.getCfProvFacCustomEmp();
            if (cfProvFacCustomEmp != null) {
                cfProvFacCustomEmp = em.getReference(cfProvFacCustomEmp.getClass(), cfProvFacCustomEmp.getId());
                provFacCustom.setCfProvFacCustomEmp(cfProvFacCustomEmp);
            }
            ProvFacCab provFacCab = provFacCustom.getProvFacCab();
            if (provFacCab != null) {
                provFacCab = em.getReference(provFacCab.getClass(), provFacCab.getId());
                provFacCustom.setProvFacCab(provFacCab);
            }
            em.persist(provFacCustom);
            if (cfProvFacCustomEmp != null) {
                cfProvFacCustomEmp.getProvFacCustomCollection().add(provFacCustom);
                cfProvFacCustomEmp = em.merge(cfProvFacCustomEmp);
            }
            if (provFacCab != null) {
                provFacCab.getProvFacCustomCollection().add(provFacCustom);
                provFacCab = em.merge(provFacCab);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProvFacCustom(provFacCustom.getProvFacCustomPK()) != null) {
                throw new PreexistingEntityException("ProvFacCustom " + provFacCustom + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProvFacCustom provFacCustom) throws NonexistentEntityException, Exception {
        provFacCustom.getProvFacCustomPK().setCodCab(provFacCustom.getProvFacCab().getId());
        provFacCustom.getProvFacCustomPK().setCodCampo(provFacCustom.getCfProvFacCustomEmp().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvFacCustom persistentProvFacCustom = em.find(ProvFacCustom.class, provFacCustom.getProvFacCustomPK());
            CfProvFacCustomEmp cfProvFacCustomEmpOld = persistentProvFacCustom.getCfProvFacCustomEmp();
            CfProvFacCustomEmp cfProvFacCustomEmpNew = provFacCustom.getCfProvFacCustomEmp();
            ProvFacCab provFacCabOld = persistentProvFacCustom.getProvFacCab();
            ProvFacCab provFacCabNew = provFacCustom.getProvFacCab();
            if (cfProvFacCustomEmpNew != null) {
                cfProvFacCustomEmpNew = em.getReference(cfProvFacCustomEmpNew.getClass(), cfProvFacCustomEmpNew.getId());
                provFacCustom.setCfProvFacCustomEmp(cfProvFacCustomEmpNew);
            }
            if (provFacCabNew != null) {
                provFacCabNew = em.getReference(provFacCabNew.getClass(), provFacCabNew.getId());
                provFacCustom.setProvFacCab(provFacCabNew);
            }
            provFacCustom = em.merge(provFacCustom);
            if (cfProvFacCustomEmpOld != null && !cfProvFacCustomEmpOld.equals(cfProvFacCustomEmpNew)) {
                cfProvFacCustomEmpOld.getProvFacCustomCollection().remove(provFacCustom);
                cfProvFacCustomEmpOld = em.merge(cfProvFacCustomEmpOld);
            }
            if (cfProvFacCustomEmpNew != null && !cfProvFacCustomEmpNew.equals(cfProvFacCustomEmpOld)) {
                cfProvFacCustomEmpNew.getProvFacCustomCollection().add(provFacCustom);
                cfProvFacCustomEmpNew = em.merge(cfProvFacCustomEmpNew);
            }
            if (provFacCabOld != null && !provFacCabOld.equals(provFacCabNew)) {
                provFacCabOld.getProvFacCustomCollection().remove(provFacCustom);
                provFacCabOld = em.merge(provFacCabOld);
            }
            if (provFacCabNew != null && !provFacCabNew.equals(provFacCabOld)) {
                provFacCabNew.getProvFacCustomCollection().add(provFacCustom);
                provFacCabNew = em.merge(provFacCabNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProvFacCustomPK id = provFacCustom.getProvFacCustomPK();
                if (findProvFacCustom(id) == null) {
                    throw new NonexistentEntityException("The provFacCustom with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProvFacCustomPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvFacCustom provFacCustom;
            try {
                provFacCustom = em.getReference(ProvFacCustom.class, id);
                provFacCustom.getProvFacCustomPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provFacCustom with id " + id + " no longer exists.", enfe);
            }
            CfProvFacCustomEmp cfProvFacCustomEmp = provFacCustom.getCfProvFacCustomEmp();
            if (cfProvFacCustomEmp != null) {
                cfProvFacCustomEmp.getProvFacCustomCollection().remove(provFacCustom);
                cfProvFacCustomEmp = em.merge(cfProvFacCustomEmp);
            }
            ProvFacCab provFacCab = provFacCustom.getProvFacCab();
            if (provFacCab != null) {
                provFacCab.getProvFacCustomCollection().remove(provFacCustom);
                provFacCab = em.merge(provFacCab);
            }
            em.remove(provFacCustom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProvFacCustom> findProvFacCustomEntities() {
        return findProvFacCustomEntities(true, -1, -1);
    }

    public List<ProvFacCustom> findProvFacCustomEntities(int maxResults, int firstResult) {
        return findProvFacCustomEntities(false, maxResults, firstResult);
    }

    private List<ProvFacCustom> findProvFacCustomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ProvFacCustom as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ProvFacCustom findProvFacCustom(ProvFacCustomPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProvFacCustom.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvFacCustomCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from ProvFacCustom as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
