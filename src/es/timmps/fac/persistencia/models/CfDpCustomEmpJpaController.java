/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.CfDpCustomEmp;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.DpCustom;
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
public class CfDpCustomEmpJpaController implements Serializable {

    public CfDpCustomEmpJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfDpCustomEmp cfDpCustomEmp) {
        if (cfDpCustomEmp.getDpCustomCollection() == null) {
            cfDpCustomEmp.setDpCustomCollection(new ArrayList<DpCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = cfDpCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cfDpCustomEmp.setCodEmp(codEmp);
            }
            Collection<DpCustom> attachedDpCustomCollection = new ArrayList<DpCustom>();
            for (DpCustom dpCustomCollectionDpCustomToAttach : cfDpCustomEmp.getDpCustomCollection()) {
                dpCustomCollectionDpCustomToAttach = em.getReference(dpCustomCollectionDpCustomToAttach.getClass(), dpCustomCollectionDpCustomToAttach.getDpCustomPK());
                attachedDpCustomCollection.add(dpCustomCollectionDpCustomToAttach);
            }
            cfDpCustomEmp.setDpCustomCollection(attachedDpCustomCollection);
            em.persist(cfDpCustomEmp);
            if (codEmp != null) {
                codEmp.getCfDpCustomEmpCollection().add(cfDpCustomEmp);
                codEmp = em.merge(codEmp);
            }
            for (DpCustom dpCustomCollectionDpCustom : cfDpCustomEmp.getDpCustomCollection()) {
                CfDpCustomEmp oldCfDpCustomEmpOfDpCustomCollectionDpCustom = dpCustomCollectionDpCustom.getCfDpCustomEmp();
                dpCustomCollectionDpCustom.setCfDpCustomEmp(cfDpCustomEmp);
                dpCustomCollectionDpCustom = em.merge(dpCustomCollectionDpCustom);
                if (oldCfDpCustomEmpOfDpCustomCollectionDpCustom != null) {
                    oldCfDpCustomEmpOfDpCustomCollectionDpCustom.getDpCustomCollection().remove(dpCustomCollectionDpCustom);
                    oldCfDpCustomEmpOfDpCustomCollectionDpCustom = em.merge(oldCfDpCustomEmpOfDpCustomCollectionDpCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfDpCustomEmp cfDpCustomEmp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfDpCustomEmp persistentCfDpCustomEmp = em.find(CfDpCustomEmp.class, cfDpCustomEmp.getId());
            Empresas codEmpOld = persistentCfDpCustomEmp.getCodEmp();
            Empresas codEmpNew = cfDpCustomEmp.getCodEmp();
            Collection<DpCustom> dpCustomCollectionOld = persistentCfDpCustomEmp.getDpCustomCollection();
            Collection<DpCustom> dpCustomCollectionNew = cfDpCustomEmp.getDpCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (DpCustom dpCustomCollectionOldDpCustom : dpCustomCollectionOld) {
                if (!dpCustomCollectionNew.contains(dpCustomCollectionOldDpCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DpCustom " + dpCustomCollectionOldDpCustom + " since its cfDpCustomEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cfDpCustomEmp.setCodEmp(codEmpNew);
            }
            Collection<DpCustom> attachedDpCustomCollectionNew = new ArrayList<DpCustom>();
            for (DpCustom dpCustomCollectionNewDpCustomToAttach : dpCustomCollectionNew) {
                dpCustomCollectionNewDpCustomToAttach = em.getReference(dpCustomCollectionNewDpCustomToAttach.getClass(), dpCustomCollectionNewDpCustomToAttach.getDpCustomPK());
                attachedDpCustomCollectionNew.add(dpCustomCollectionNewDpCustomToAttach);
            }
            dpCustomCollectionNew = attachedDpCustomCollectionNew;
            cfDpCustomEmp.setDpCustomCollection(dpCustomCollectionNew);
            cfDpCustomEmp = em.merge(cfDpCustomEmp);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCfDpCustomEmpCollection().remove(cfDpCustomEmp);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCfDpCustomEmpCollection().add(cfDpCustomEmp);
                codEmpNew = em.merge(codEmpNew);
            }
            for (DpCustom dpCustomCollectionNewDpCustom : dpCustomCollectionNew) {
                if (!dpCustomCollectionOld.contains(dpCustomCollectionNewDpCustom)) {
                    CfDpCustomEmp oldCfDpCustomEmpOfDpCustomCollectionNewDpCustom = dpCustomCollectionNewDpCustom.getCfDpCustomEmp();
                    dpCustomCollectionNewDpCustom.setCfDpCustomEmp(cfDpCustomEmp);
                    dpCustomCollectionNewDpCustom = em.merge(dpCustomCollectionNewDpCustom);
                    if (oldCfDpCustomEmpOfDpCustomCollectionNewDpCustom != null && !oldCfDpCustomEmpOfDpCustomCollectionNewDpCustom.equals(cfDpCustomEmp)) {
                        oldCfDpCustomEmpOfDpCustomCollectionNewDpCustom.getDpCustomCollection().remove(dpCustomCollectionNewDpCustom);
                        oldCfDpCustomEmpOfDpCustomCollectionNewDpCustom = em.merge(oldCfDpCustomEmpOfDpCustomCollectionNewDpCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfDpCustomEmp.getId();
                if (findCfDpCustomEmp(id) == null) {
                    throw new NonexistentEntityException("The cfDpCustomEmp with id " + id + " no longer exists.");
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
            CfDpCustomEmp cfDpCustomEmp;
            try {
                cfDpCustomEmp = em.getReference(CfDpCustomEmp.class, id);
                cfDpCustomEmp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfDpCustomEmp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DpCustom> dpCustomCollectionOrphanCheck = cfDpCustomEmp.getDpCustomCollection();
            for (DpCustom dpCustomCollectionOrphanCheckDpCustom : dpCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfDpCustomEmp (" + cfDpCustomEmp + ") cannot be destroyed since the DpCustom " + dpCustomCollectionOrphanCheckDpCustom + " in its dpCustomCollection field has a non-nullable cfDpCustomEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresas codEmp = cfDpCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp.getCfDpCustomEmpCollection().remove(cfDpCustomEmp);
                codEmp = em.merge(codEmp);
            }
            em.remove(cfDpCustomEmp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfDpCustomEmp> findCfDpCustomEmpEntities() {
        return findCfDpCustomEmpEntities(true, -1, -1);
    }

    public List<CfDpCustomEmp> findCfDpCustomEmpEntities(int maxResults, int firstResult) {
        return findCfDpCustomEmpEntities(false, maxResults, firstResult);
    }

    private List<CfDpCustomEmp> findCfDpCustomEmpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CfDpCustomEmp as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CfDpCustomEmp findCfDpCustomEmp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfDpCustomEmp.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfDpCustomEmpCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CfDpCustomEmp as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
