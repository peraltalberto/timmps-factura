/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.pojos.CfProvFacCustomEmp;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.CfTipoValores;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.ProvFacCustom;
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
public class CfProvFacCustomEmpJpaController implements Serializable {

    public CfProvFacCustomEmpJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfProvFacCustomEmp cfProvFacCustomEmp) {
        if (cfProvFacCustomEmp.getProvFacCustomCollection() == null) {
            cfProvFacCustomEmp.setProvFacCustomCollection(new ArrayList<ProvFacCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfTipoValores tipoValor = cfProvFacCustomEmp.getTipoValor();
            if (tipoValor != null) {
                tipoValor = em.getReference(tipoValor.getClass(), tipoValor.getId());
                cfProvFacCustomEmp.setTipoValor(tipoValor);
            }
            Empresas codEmp = cfProvFacCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cfProvFacCustomEmp.setCodEmp(codEmp);
            }
            Collection<ProvFacCustom> attachedProvFacCustomCollection = new ArrayList<ProvFacCustom>();
            for (ProvFacCustom provFacCustomCollectionProvFacCustomToAttach : cfProvFacCustomEmp.getProvFacCustomCollection()) {
                provFacCustomCollectionProvFacCustomToAttach = em.getReference(provFacCustomCollectionProvFacCustomToAttach.getClass(), provFacCustomCollectionProvFacCustomToAttach.getProvFacCustomPK());
                attachedProvFacCustomCollection.add(provFacCustomCollectionProvFacCustomToAttach);
            }
            cfProvFacCustomEmp.setProvFacCustomCollection(attachedProvFacCustomCollection);
            em.persist(cfProvFacCustomEmp);
            if (tipoValor != null) {
                tipoValor.getCfProvFacCustomEmpCollection().add(cfProvFacCustomEmp);
                tipoValor = em.merge(tipoValor);
            }
            if (codEmp != null) {
                codEmp.getCfProvFacCustomEmpCollection().add(cfProvFacCustomEmp);
                codEmp = em.merge(codEmp);
            }
            for (ProvFacCustom provFacCustomCollectionProvFacCustom : cfProvFacCustomEmp.getProvFacCustomCollection()) {
                CfProvFacCustomEmp oldCfProvFacCustomEmpOfProvFacCustomCollectionProvFacCustom = provFacCustomCollectionProvFacCustom.getCfProvFacCustomEmp();
                provFacCustomCollectionProvFacCustom.setCfProvFacCustomEmp(cfProvFacCustomEmp);
                provFacCustomCollectionProvFacCustom = em.merge(provFacCustomCollectionProvFacCustom);
                if (oldCfProvFacCustomEmpOfProvFacCustomCollectionProvFacCustom != null) {
                    oldCfProvFacCustomEmpOfProvFacCustomCollectionProvFacCustom.getProvFacCustomCollection().remove(provFacCustomCollectionProvFacCustom);
                    oldCfProvFacCustomEmpOfProvFacCustomCollectionProvFacCustom = em.merge(oldCfProvFacCustomEmpOfProvFacCustomCollectionProvFacCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfProvFacCustomEmp cfProvFacCustomEmp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfProvFacCustomEmp persistentCfProvFacCustomEmp = em.find(CfProvFacCustomEmp.class, cfProvFacCustomEmp.getId());
            CfTipoValores tipoValorOld = persistentCfProvFacCustomEmp.getTipoValor();
            CfTipoValores tipoValorNew = cfProvFacCustomEmp.getTipoValor();
            Empresas codEmpOld = persistentCfProvFacCustomEmp.getCodEmp();
            Empresas codEmpNew = cfProvFacCustomEmp.getCodEmp();
            Collection<ProvFacCustom> provFacCustomCollectionOld = persistentCfProvFacCustomEmp.getProvFacCustomCollection();
            Collection<ProvFacCustom> provFacCustomCollectionNew = cfProvFacCustomEmp.getProvFacCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (ProvFacCustom provFacCustomCollectionOldProvFacCustom : provFacCustomCollectionOld) {
                if (!provFacCustomCollectionNew.contains(provFacCustomCollectionOldProvFacCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvFacCustom " + provFacCustomCollectionOldProvFacCustom + " since its cfProvFacCustomEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoValorNew != null) {
                tipoValorNew = em.getReference(tipoValorNew.getClass(), tipoValorNew.getId());
                cfProvFacCustomEmp.setTipoValor(tipoValorNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cfProvFacCustomEmp.setCodEmp(codEmpNew);
            }
            Collection<ProvFacCustom> attachedProvFacCustomCollectionNew = new ArrayList<ProvFacCustom>();
            for (ProvFacCustom provFacCustomCollectionNewProvFacCustomToAttach : provFacCustomCollectionNew) {
                provFacCustomCollectionNewProvFacCustomToAttach = em.getReference(provFacCustomCollectionNewProvFacCustomToAttach.getClass(), provFacCustomCollectionNewProvFacCustomToAttach.getProvFacCustomPK());
                attachedProvFacCustomCollectionNew.add(provFacCustomCollectionNewProvFacCustomToAttach);
            }
            provFacCustomCollectionNew = attachedProvFacCustomCollectionNew;
            cfProvFacCustomEmp.setProvFacCustomCollection(provFacCustomCollectionNew);
            cfProvFacCustomEmp = em.merge(cfProvFacCustomEmp);
            if (tipoValorOld != null && !tipoValorOld.equals(tipoValorNew)) {
                tipoValorOld.getCfProvFacCustomEmpCollection().remove(cfProvFacCustomEmp);
                tipoValorOld = em.merge(tipoValorOld);
            }
            if (tipoValorNew != null && !tipoValorNew.equals(tipoValorOld)) {
                tipoValorNew.getCfProvFacCustomEmpCollection().add(cfProvFacCustomEmp);
                tipoValorNew = em.merge(tipoValorNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCfProvFacCustomEmpCollection().remove(cfProvFacCustomEmp);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCfProvFacCustomEmpCollection().add(cfProvFacCustomEmp);
                codEmpNew = em.merge(codEmpNew);
            }
            for (ProvFacCustom provFacCustomCollectionNewProvFacCustom : provFacCustomCollectionNew) {
                if (!provFacCustomCollectionOld.contains(provFacCustomCollectionNewProvFacCustom)) {
                    CfProvFacCustomEmp oldCfProvFacCustomEmpOfProvFacCustomCollectionNewProvFacCustom = provFacCustomCollectionNewProvFacCustom.getCfProvFacCustomEmp();
                    provFacCustomCollectionNewProvFacCustom.setCfProvFacCustomEmp(cfProvFacCustomEmp);
                    provFacCustomCollectionNewProvFacCustom = em.merge(provFacCustomCollectionNewProvFacCustom);
                    if (oldCfProvFacCustomEmpOfProvFacCustomCollectionNewProvFacCustom != null && !oldCfProvFacCustomEmpOfProvFacCustomCollectionNewProvFacCustom.equals(cfProvFacCustomEmp)) {
                        oldCfProvFacCustomEmpOfProvFacCustomCollectionNewProvFacCustom.getProvFacCustomCollection().remove(provFacCustomCollectionNewProvFacCustom);
                        oldCfProvFacCustomEmpOfProvFacCustomCollectionNewProvFacCustom = em.merge(oldCfProvFacCustomEmpOfProvFacCustomCollectionNewProvFacCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfProvFacCustomEmp.getId();
                if (findCfProvFacCustomEmp(id) == null) {
                    throw new NonexistentEntityException("The cfProvFacCustomEmp with id " + id + " no longer exists.");
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
            CfProvFacCustomEmp cfProvFacCustomEmp;
            try {
                cfProvFacCustomEmp = em.getReference(CfProvFacCustomEmp.class, id);
                cfProvFacCustomEmp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfProvFacCustomEmp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProvFacCustom> provFacCustomCollectionOrphanCheck = cfProvFacCustomEmp.getProvFacCustomCollection();
            for (ProvFacCustom provFacCustomCollectionOrphanCheckProvFacCustom : provFacCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfProvFacCustomEmp (" + cfProvFacCustomEmp + ") cannot be destroyed since the ProvFacCustom " + provFacCustomCollectionOrphanCheckProvFacCustom + " in its provFacCustomCollection field has a non-nullable cfProvFacCustomEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CfTipoValores tipoValor = cfProvFacCustomEmp.getTipoValor();
            if (tipoValor != null) {
                tipoValor.getCfProvFacCustomEmpCollection().remove(cfProvFacCustomEmp);
                tipoValor = em.merge(tipoValor);
            }
            Empresas codEmp = cfProvFacCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp.getCfProvFacCustomEmpCollection().remove(cfProvFacCustomEmp);
                codEmp = em.merge(codEmp);
            }
            em.remove(cfProvFacCustomEmp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfProvFacCustomEmp> findCfProvFacCustomEmpEntities() {
        return findCfProvFacCustomEmpEntities(true, -1, -1);
    }

    public List<CfProvFacCustomEmp> findCfProvFacCustomEmpEntities(int maxResults, int firstResult) {
        return findCfProvFacCustomEmpEntities(false, maxResults, firstResult);
    }

    private List<CfProvFacCustomEmp> findCfProvFacCustomEmpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CfProvFacCustomEmp.class));
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

    public CfProvFacCustomEmp findCfProvFacCustomEmp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfProvFacCustomEmp.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfProvFacCustomEmpCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CfProvFacCustomEmp> rt = cq.from(CfProvFacCustomEmp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
