/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.pojos.CfCliAlbCustomEmp;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.CliAlbCustom;
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
public class CfCliAlbCustomEmpJpaController implements Serializable {

    public CfCliAlbCustomEmpJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfCliAlbCustomEmp cfCliAlbCustomEmp) {
        if (cfCliAlbCustomEmp.getCliAlbCustomCollection() == null) {
            cfCliAlbCustomEmp.setCliAlbCustomCollection(new ArrayList<CliAlbCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = cfCliAlbCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cfCliAlbCustomEmp.setCodEmp(codEmp);
            }
            Collection<CliAlbCustom> attachedCliAlbCustomCollection = new ArrayList<CliAlbCustom>();
            for (CliAlbCustom cliAlbCustomCollectionCliAlbCustomToAttach : cfCliAlbCustomEmp.getCliAlbCustomCollection()) {
                cliAlbCustomCollectionCliAlbCustomToAttach = em.getReference(cliAlbCustomCollectionCliAlbCustomToAttach.getClass(), cliAlbCustomCollectionCliAlbCustomToAttach.getCliAlbCustomPK());
                attachedCliAlbCustomCollection.add(cliAlbCustomCollectionCliAlbCustomToAttach);
            }
            cfCliAlbCustomEmp.setCliAlbCustomCollection(attachedCliAlbCustomCollection);
            em.persist(cfCliAlbCustomEmp);
            if (codEmp != null) {
                codEmp.getCfCliAlbCustomEmpCollection().add(cfCliAlbCustomEmp);
                codEmp = em.merge(codEmp);
            }
            for (CliAlbCustom cliAlbCustomCollectionCliAlbCustom : cfCliAlbCustomEmp.getCliAlbCustomCollection()) {
                CfCliAlbCustomEmp oldCfCliAlbCustomEmpOfCliAlbCustomCollectionCliAlbCustom = cliAlbCustomCollectionCliAlbCustom.getCfCliAlbCustomEmp();
                cliAlbCustomCollectionCliAlbCustom.setCfCliAlbCustomEmp(cfCliAlbCustomEmp);
                cliAlbCustomCollectionCliAlbCustom = em.merge(cliAlbCustomCollectionCliAlbCustom);
                if (oldCfCliAlbCustomEmpOfCliAlbCustomCollectionCliAlbCustom != null) {
                    oldCfCliAlbCustomEmpOfCliAlbCustomCollectionCliAlbCustom.getCliAlbCustomCollection().remove(cliAlbCustomCollectionCliAlbCustom);
                    oldCfCliAlbCustomEmpOfCliAlbCustomCollectionCliAlbCustom = em.merge(oldCfCliAlbCustomEmpOfCliAlbCustomCollectionCliAlbCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfCliAlbCustomEmp cfCliAlbCustomEmp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfCliAlbCustomEmp persistentCfCliAlbCustomEmp = em.find(CfCliAlbCustomEmp.class, cfCliAlbCustomEmp.getId());
            Empresas codEmpOld = persistentCfCliAlbCustomEmp.getCodEmp();
            Empresas codEmpNew = cfCliAlbCustomEmp.getCodEmp();
            Collection<CliAlbCustom> cliAlbCustomCollectionOld = persistentCfCliAlbCustomEmp.getCliAlbCustomCollection();
            Collection<CliAlbCustom> cliAlbCustomCollectionNew = cfCliAlbCustomEmp.getCliAlbCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (CliAlbCustom cliAlbCustomCollectionOldCliAlbCustom : cliAlbCustomCollectionOld) {
                if (!cliAlbCustomCollectionNew.contains(cliAlbCustomCollectionOldCliAlbCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliAlbCustom " + cliAlbCustomCollectionOldCliAlbCustom + " since its cfCliAlbCustomEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cfCliAlbCustomEmp.setCodEmp(codEmpNew);
            }
            Collection<CliAlbCustom> attachedCliAlbCustomCollectionNew = new ArrayList<CliAlbCustom>();
            for (CliAlbCustom cliAlbCustomCollectionNewCliAlbCustomToAttach : cliAlbCustomCollectionNew) {
                cliAlbCustomCollectionNewCliAlbCustomToAttach = em.getReference(cliAlbCustomCollectionNewCliAlbCustomToAttach.getClass(), cliAlbCustomCollectionNewCliAlbCustomToAttach.getCliAlbCustomPK());
                attachedCliAlbCustomCollectionNew.add(cliAlbCustomCollectionNewCliAlbCustomToAttach);
            }
            cliAlbCustomCollectionNew = attachedCliAlbCustomCollectionNew;
            cfCliAlbCustomEmp.setCliAlbCustomCollection(cliAlbCustomCollectionNew);
            cfCliAlbCustomEmp = em.merge(cfCliAlbCustomEmp);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCfCliAlbCustomEmpCollection().remove(cfCliAlbCustomEmp);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCfCliAlbCustomEmpCollection().add(cfCliAlbCustomEmp);
                codEmpNew = em.merge(codEmpNew);
            }
            for (CliAlbCustom cliAlbCustomCollectionNewCliAlbCustom : cliAlbCustomCollectionNew) {
                if (!cliAlbCustomCollectionOld.contains(cliAlbCustomCollectionNewCliAlbCustom)) {
                    CfCliAlbCustomEmp oldCfCliAlbCustomEmpOfCliAlbCustomCollectionNewCliAlbCustom = cliAlbCustomCollectionNewCliAlbCustom.getCfCliAlbCustomEmp();
                    cliAlbCustomCollectionNewCliAlbCustom.setCfCliAlbCustomEmp(cfCliAlbCustomEmp);
                    cliAlbCustomCollectionNewCliAlbCustom = em.merge(cliAlbCustomCollectionNewCliAlbCustom);
                    if (oldCfCliAlbCustomEmpOfCliAlbCustomCollectionNewCliAlbCustom != null && !oldCfCliAlbCustomEmpOfCliAlbCustomCollectionNewCliAlbCustom.equals(cfCliAlbCustomEmp)) {
                        oldCfCliAlbCustomEmpOfCliAlbCustomCollectionNewCliAlbCustom.getCliAlbCustomCollection().remove(cliAlbCustomCollectionNewCliAlbCustom);
                        oldCfCliAlbCustomEmpOfCliAlbCustomCollectionNewCliAlbCustom = em.merge(oldCfCliAlbCustomEmpOfCliAlbCustomCollectionNewCliAlbCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfCliAlbCustomEmp.getId();
                if (findCfCliAlbCustomEmp(id) == null) {
                    throw new NonexistentEntityException("The cfCliAlbCustomEmp with id " + id + " no longer exists.");
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
            CfCliAlbCustomEmp cfCliAlbCustomEmp;
            try {
                cfCliAlbCustomEmp = em.getReference(CfCliAlbCustomEmp.class, id);
                cfCliAlbCustomEmp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfCliAlbCustomEmp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CliAlbCustom> cliAlbCustomCollectionOrphanCheck = cfCliAlbCustomEmp.getCliAlbCustomCollection();
            for (CliAlbCustom cliAlbCustomCollectionOrphanCheckCliAlbCustom : cliAlbCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfCliAlbCustomEmp (" + cfCliAlbCustomEmp + ") cannot be destroyed since the CliAlbCustom " + cliAlbCustomCollectionOrphanCheckCliAlbCustom + " in its cliAlbCustomCollection field has a non-nullable cfCliAlbCustomEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresas codEmp = cfCliAlbCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp.getCfCliAlbCustomEmpCollection().remove(cfCliAlbCustomEmp);
                codEmp = em.merge(codEmp);
            }
            em.remove(cfCliAlbCustomEmp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfCliAlbCustomEmp> findCfCliAlbCustomEmpEntities() {
        return findCfCliAlbCustomEmpEntities(true, -1, -1);
    }

    public List<CfCliAlbCustomEmp> findCfCliAlbCustomEmpEntities(int maxResults, int firstResult) {
        return findCfCliAlbCustomEmpEntities(false, maxResults, firstResult);
    }

    private List<CfCliAlbCustomEmp> findCfCliAlbCustomEmpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CfCliAlbCustomEmp.class));
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

    public CfCliAlbCustomEmp findCfCliAlbCustomEmp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfCliAlbCustomEmp.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfCliAlbCustomEmpCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CfCliAlbCustomEmp> rt = cq.from(CfCliAlbCustomEmp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
