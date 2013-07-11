/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.pojos.CfCliPedCustomEmp;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.CliPedidosCustom;
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
public class CfCliPedCustomEmpJpaController implements Serializable {

    public CfCliPedCustomEmpJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfCliPedCustomEmp cfCliPedCustomEmp) {
        if (cfCliPedCustomEmp.getCliPedidosCustomCollection() == null) {
            cfCliPedCustomEmp.setCliPedidosCustomCollection(new ArrayList<CliPedidosCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = cfCliPedCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cfCliPedCustomEmp.setCodEmp(codEmp);
            }
            Collection<CliPedidosCustom> attachedCliPedidosCustomCollection = new ArrayList<CliPedidosCustom>();
            for (CliPedidosCustom cliPedidosCustomCollectionCliPedidosCustomToAttach : cfCliPedCustomEmp.getCliPedidosCustomCollection()) {
                cliPedidosCustomCollectionCliPedidosCustomToAttach = em.getReference(cliPedidosCustomCollectionCliPedidosCustomToAttach.getClass(), cliPedidosCustomCollectionCliPedidosCustomToAttach.getCliPedidosCustomPK());
                attachedCliPedidosCustomCollection.add(cliPedidosCustomCollectionCliPedidosCustomToAttach);
            }
            cfCliPedCustomEmp.setCliPedidosCustomCollection(attachedCliPedidosCustomCollection);
            em.persist(cfCliPedCustomEmp);
            if (codEmp != null) {
                codEmp.getCfCliPedCustomEmpCollection().add(cfCliPedCustomEmp);
                codEmp = em.merge(codEmp);
            }
            for (CliPedidosCustom cliPedidosCustomCollectionCliPedidosCustom : cfCliPedCustomEmp.getCliPedidosCustomCollection()) {
                CfCliPedCustomEmp oldCfCliPedCustomEmpOfCliPedidosCustomCollectionCliPedidosCustom = cliPedidosCustomCollectionCliPedidosCustom.getCfCliPedCustomEmp();
                cliPedidosCustomCollectionCliPedidosCustom.setCfCliPedCustomEmp(cfCliPedCustomEmp);
                cliPedidosCustomCollectionCliPedidosCustom = em.merge(cliPedidosCustomCollectionCliPedidosCustom);
                if (oldCfCliPedCustomEmpOfCliPedidosCustomCollectionCliPedidosCustom != null) {
                    oldCfCliPedCustomEmpOfCliPedidosCustomCollectionCliPedidosCustom.getCliPedidosCustomCollection().remove(cliPedidosCustomCollectionCliPedidosCustom);
                    oldCfCliPedCustomEmpOfCliPedidosCustomCollectionCliPedidosCustom = em.merge(oldCfCliPedCustomEmpOfCliPedidosCustomCollectionCliPedidosCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfCliPedCustomEmp cfCliPedCustomEmp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfCliPedCustomEmp persistentCfCliPedCustomEmp = em.find(CfCliPedCustomEmp.class, cfCliPedCustomEmp.getId());
            Empresas codEmpOld = persistentCfCliPedCustomEmp.getCodEmp();
            Empresas codEmpNew = cfCliPedCustomEmp.getCodEmp();
            Collection<CliPedidosCustom> cliPedidosCustomCollectionOld = persistentCfCliPedCustomEmp.getCliPedidosCustomCollection();
            Collection<CliPedidosCustom> cliPedidosCustomCollectionNew = cfCliPedCustomEmp.getCliPedidosCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (CliPedidosCustom cliPedidosCustomCollectionOldCliPedidosCustom : cliPedidosCustomCollectionOld) {
                if (!cliPedidosCustomCollectionNew.contains(cliPedidosCustomCollectionOldCliPedidosCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliPedidosCustom " + cliPedidosCustomCollectionOldCliPedidosCustom + " since its cfCliPedCustomEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cfCliPedCustomEmp.setCodEmp(codEmpNew);
            }
            Collection<CliPedidosCustom> attachedCliPedidosCustomCollectionNew = new ArrayList<CliPedidosCustom>();
            for (CliPedidosCustom cliPedidosCustomCollectionNewCliPedidosCustomToAttach : cliPedidosCustomCollectionNew) {
                cliPedidosCustomCollectionNewCliPedidosCustomToAttach = em.getReference(cliPedidosCustomCollectionNewCliPedidosCustomToAttach.getClass(), cliPedidosCustomCollectionNewCliPedidosCustomToAttach.getCliPedidosCustomPK());
                attachedCliPedidosCustomCollectionNew.add(cliPedidosCustomCollectionNewCliPedidosCustomToAttach);
            }
            cliPedidosCustomCollectionNew = attachedCliPedidosCustomCollectionNew;
            cfCliPedCustomEmp.setCliPedidosCustomCollection(cliPedidosCustomCollectionNew);
            cfCliPedCustomEmp = em.merge(cfCliPedCustomEmp);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCfCliPedCustomEmpCollection().remove(cfCliPedCustomEmp);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCfCliPedCustomEmpCollection().add(cfCliPedCustomEmp);
                codEmpNew = em.merge(codEmpNew);
            }
            for (CliPedidosCustom cliPedidosCustomCollectionNewCliPedidosCustom : cliPedidosCustomCollectionNew) {
                if (!cliPedidosCustomCollectionOld.contains(cliPedidosCustomCollectionNewCliPedidosCustom)) {
                    CfCliPedCustomEmp oldCfCliPedCustomEmpOfCliPedidosCustomCollectionNewCliPedidosCustom = cliPedidosCustomCollectionNewCliPedidosCustom.getCfCliPedCustomEmp();
                    cliPedidosCustomCollectionNewCliPedidosCustom.setCfCliPedCustomEmp(cfCliPedCustomEmp);
                    cliPedidosCustomCollectionNewCliPedidosCustom = em.merge(cliPedidosCustomCollectionNewCliPedidosCustom);
                    if (oldCfCliPedCustomEmpOfCliPedidosCustomCollectionNewCliPedidosCustom != null && !oldCfCliPedCustomEmpOfCliPedidosCustomCollectionNewCliPedidosCustom.equals(cfCliPedCustomEmp)) {
                        oldCfCliPedCustomEmpOfCliPedidosCustomCollectionNewCliPedidosCustom.getCliPedidosCustomCollection().remove(cliPedidosCustomCollectionNewCliPedidosCustom);
                        oldCfCliPedCustomEmpOfCliPedidosCustomCollectionNewCliPedidosCustom = em.merge(oldCfCliPedCustomEmpOfCliPedidosCustomCollectionNewCliPedidosCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfCliPedCustomEmp.getId();
                if (findCfCliPedCustomEmp(id) == null) {
                    throw new NonexistentEntityException("The cfCliPedCustomEmp with id " + id + " no longer exists.");
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
            CfCliPedCustomEmp cfCliPedCustomEmp;
            try {
                cfCliPedCustomEmp = em.getReference(CfCliPedCustomEmp.class, id);
                cfCliPedCustomEmp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfCliPedCustomEmp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CliPedidosCustom> cliPedidosCustomCollectionOrphanCheck = cfCliPedCustomEmp.getCliPedidosCustomCollection();
            for (CliPedidosCustom cliPedidosCustomCollectionOrphanCheckCliPedidosCustom : cliPedidosCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfCliPedCustomEmp (" + cfCliPedCustomEmp + ") cannot be destroyed since the CliPedidosCustom " + cliPedidosCustomCollectionOrphanCheckCliPedidosCustom + " in its cliPedidosCustomCollection field has a non-nullable cfCliPedCustomEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresas codEmp = cfCliPedCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp.getCfCliPedCustomEmpCollection().remove(cfCliPedCustomEmp);
                codEmp = em.merge(codEmp);
            }
            em.remove(cfCliPedCustomEmp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfCliPedCustomEmp> findCfCliPedCustomEmpEntities() {
        return findCfCliPedCustomEmpEntities(true, -1, -1);
    }

    public List<CfCliPedCustomEmp> findCfCliPedCustomEmpEntities(int maxResults, int firstResult) {
        return findCfCliPedCustomEmpEntities(false, maxResults, firstResult);
    }

    private List<CfCliPedCustomEmp> findCfCliPedCustomEmpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CfCliPedCustomEmp.class));
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

    public CfCliPedCustomEmp findCfCliPedCustomEmp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfCliPedCustomEmp.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfCliPedCustomEmpCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CfCliPedCustomEmp> rt = cq.from(CfCliPedCustomEmp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
