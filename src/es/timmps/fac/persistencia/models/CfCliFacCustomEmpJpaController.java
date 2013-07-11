/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.pojos.CfCliFacCustomEmp;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.CliFacCustom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class CfCliFacCustomEmpJpaController implements Serializable {

    public CfCliFacCustomEmpJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfCliFacCustomEmp cfCliFacCustomEmp) {
        if (cfCliFacCustomEmp.getCliFacCustomCollection() == null) {
            cfCliFacCustomEmp.setCliFacCustomCollection(new ArrayList<CliFacCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = cfCliFacCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cfCliFacCustomEmp.setCodEmp(codEmp);
            }
            Collection<CliFacCustom> attachedCliFacCustomCollection = new ArrayList<CliFacCustom>();
            for (CliFacCustom cliFacCustomCollectionCliFacCustomToAttach : cfCliFacCustomEmp.getCliFacCustomCollection()) {
                cliFacCustomCollectionCliFacCustomToAttach = em.getReference(cliFacCustomCollectionCliFacCustomToAttach.getClass(), cliFacCustomCollectionCliFacCustomToAttach.getCliFacCustomPK());
                attachedCliFacCustomCollection.add(cliFacCustomCollectionCliFacCustomToAttach);
            }
            cfCliFacCustomEmp.setCliFacCustomCollection(attachedCliFacCustomCollection);
            em.persist(cfCliFacCustomEmp);
            if (codEmp != null) {
                codEmp.getCfCliFacCustomEmpCollection().add(cfCliFacCustomEmp);
                codEmp = em.merge(codEmp);
            }
            for (CliFacCustom cliFacCustomCollectionCliFacCustom : cfCliFacCustomEmp.getCliFacCustomCollection()) {
                CfCliFacCustomEmp oldCfCliFacCustomEmpOfCliFacCustomCollectionCliFacCustom = cliFacCustomCollectionCliFacCustom.getCfCliFacCustomEmp();
                cliFacCustomCollectionCliFacCustom.setCfCliFacCustomEmp(cfCliFacCustomEmp);
                cliFacCustomCollectionCliFacCustom = em.merge(cliFacCustomCollectionCliFacCustom);
                if (oldCfCliFacCustomEmpOfCliFacCustomCollectionCliFacCustom != null) {
                    oldCfCliFacCustomEmpOfCliFacCustomCollectionCliFacCustom.getCliFacCustomCollection().remove(cliFacCustomCollectionCliFacCustom);
                    oldCfCliFacCustomEmpOfCliFacCustomCollectionCliFacCustom = em.merge(oldCfCliFacCustomEmpOfCliFacCustomCollectionCliFacCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfCliFacCustomEmp cfCliFacCustomEmp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfCliFacCustomEmp persistentCfCliFacCustomEmp = em.find(CfCliFacCustomEmp.class, cfCliFacCustomEmp.getId());
            Empresas codEmpOld = persistentCfCliFacCustomEmp.getCodEmp();
            Empresas codEmpNew = cfCliFacCustomEmp.getCodEmp();
            Collection<CliFacCustom> cliFacCustomCollectionOld = persistentCfCliFacCustomEmp.getCliFacCustomCollection();
            Collection<CliFacCustom> cliFacCustomCollectionNew = cfCliFacCustomEmp.getCliFacCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (CliFacCustom cliFacCustomCollectionOldCliFacCustom : cliFacCustomCollectionOld) {
                if (!cliFacCustomCollectionNew.contains(cliFacCustomCollectionOldCliFacCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliFacCustom " + cliFacCustomCollectionOldCliFacCustom + " since its cfCliFacCustomEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cfCliFacCustomEmp.setCodEmp(codEmpNew);
            }
            Collection<CliFacCustom> attachedCliFacCustomCollectionNew = new ArrayList<CliFacCustom>();
            for (CliFacCustom cliFacCustomCollectionNewCliFacCustomToAttach : cliFacCustomCollectionNew) {
                cliFacCustomCollectionNewCliFacCustomToAttach = em.getReference(cliFacCustomCollectionNewCliFacCustomToAttach.getClass(), cliFacCustomCollectionNewCliFacCustomToAttach.getCliFacCustomPK());
                attachedCliFacCustomCollectionNew.add(cliFacCustomCollectionNewCliFacCustomToAttach);
            }
            cliFacCustomCollectionNew = attachedCliFacCustomCollectionNew;
            cfCliFacCustomEmp.setCliFacCustomCollection(cliFacCustomCollectionNew);
            cfCliFacCustomEmp = em.merge(cfCliFacCustomEmp);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCfCliFacCustomEmpCollection().remove(cfCliFacCustomEmp);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCfCliFacCustomEmpCollection().add(cfCliFacCustomEmp);
                codEmpNew = em.merge(codEmpNew);
            }
            for (CliFacCustom cliFacCustomCollectionNewCliFacCustom : cliFacCustomCollectionNew) {
                if (!cliFacCustomCollectionOld.contains(cliFacCustomCollectionNewCliFacCustom)) {
                    CfCliFacCustomEmp oldCfCliFacCustomEmpOfCliFacCustomCollectionNewCliFacCustom = cliFacCustomCollectionNewCliFacCustom.getCfCliFacCustomEmp();
                    cliFacCustomCollectionNewCliFacCustom.setCfCliFacCustomEmp(cfCliFacCustomEmp);
                    cliFacCustomCollectionNewCliFacCustom = em.merge(cliFacCustomCollectionNewCliFacCustom);
                    if (oldCfCliFacCustomEmpOfCliFacCustomCollectionNewCliFacCustom != null && !oldCfCliFacCustomEmpOfCliFacCustomCollectionNewCliFacCustom.equals(cfCliFacCustomEmp)) {
                        oldCfCliFacCustomEmpOfCliFacCustomCollectionNewCliFacCustom.getCliFacCustomCollection().remove(cliFacCustomCollectionNewCliFacCustom);
                        oldCfCliFacCustomEmpOfCliFacCustomCollectionNewCliFacCustom = em.merge(oldCfCliFacCustomEmpOfCliFacCustomCollectionNewCliFacCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfCliFacCustomEmp.getId();
                if (findCfCliFacCustomEmp(id) == null) {
                    throw new NonexistentEntityException("The cfCliFacCustomEmp with id " + id + " no longer exists.");
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
            CfCliFacCustomEmp cfCliFacCustomEmp;
            try {
                cfCliFacCustomEmp = em.getReference(CfCliFacCustomEmp.class, id);
                cfCliFacCustomEmp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfCliFacCustomEmp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CliFacCustom> cliFacCustomCollectionOrphanCheck = cfCliFacCustomEmp.getCliFacCustomCollection();
            for (CliFacCustom cliFacCustomCollectionOrphanCheckCliFacCustom : cliFacCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfCliFacCustomEmp (" + cfCliFacCustomEmp + ") cannot be destroyed since the CliFacCustom " + cliFacCustomCollectionOrphanCheckCliFacCustom + " in its cliFacCustomCollection field has a non-nullable cfCliFacCustomEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresas codEmp = cfCliFacCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp.getCfCliFacCustomEmpCollection().remove(cfCliFacCustomEmp);
                codEmp = em.merge(codEmp);
            }
            em.remove(cfCliFacCustomEmp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfCliFacCustomEmp> findCfCliFacCustomEmpEntities() {
        return findCfCliFacCustomEmpEntities(true, -1, -1);
    }

    public List<CfCliFacCustomEmp> findCfCliFacCustomEmpEntities(int maxResults, int firstResult) {
        return findCfCliFacCustomEmpEntities(false, maxResults, firstResult);
    }

    private List<CfCliFacCustomEmp> findCfCliFacCustomEmpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CfCliFacCustomEmp.class));
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

    public CfCliFacCustomEmp findCfCliFacCustomEmp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfCliFacCustomEmp.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfCliFacCustomEmpCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CfCliFacCustomEmp> rt = cq.from(CfCliFacCustomEmp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
