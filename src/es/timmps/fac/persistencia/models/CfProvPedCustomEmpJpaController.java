/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.pojos.CfProvPedCustomEmp;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.CfTipoValores;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.ProvPedidosCustom;
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
public class CfProvPedCustomEmpJpaController implements Serializable {

    public CfProvPedCustomEmpJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfProvPedCustomEmp cfProvPedCustomEmp) {
        if (cfProvPedCustomEmp.getProvPedidosCustomCollection() == null) {
            cfProvPedCustomEmp.setProvPedidosCustomCollection(new ArrayList<ProvPedidosCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfTipoValores tipoValor = cfProvPedCustomEmp.getTipoValor();
            if (tipoValor != null) {
                tipoValor = em.getReference(tipoValor.getClass(), tipoValor.getId());
                cfProvPedCustomEmp.setTipoValor(tipoValor);
            }
            Empresas codEmp = cfProvPedCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cfProvPedCustomEmp.setCodEmp(codEmp);
            }
            Collection<ProvPedidosCustom> attachedProvPedidosCustomCollection = new ArrayList<ProvPedidosCustom>();
            for (ProvPedidosCustom provPedidosCustomCollectionProvPedidosCustomToAttach : cfProvPedCustomEmp.getProvPedidosCustomCollection()) {
                provPedidosCustomCollectionProvPedidosCustomToAttach = em.getReference(provPedidosCustomCollectionProvPedidosCustomToAttach.getClass(), provPedidosCustomCollectionProvPedidosCustomToAttach.getProvPedidosCustomPK());
                attachedProvPedidosCustomCollection.add(provPedidosCustomCollectionProvPedidosCustomToAttach);
            }
            cfProvPedCustomEmp.setProvPedidosCustomCollection(attachedProvPedidosCustomCollection);
            em.persist(cfProvPedCustomEmp);
            if (tipoValor != null) {
                tipoValor.getCfProvPedCustomEmpCollection().add(cfProvPedCustomEmp);
                tipoValor = em.merge(tipoValor);
            }
            if (codEmp != null) {
                codEmp.getCfProvPedCustomEmpCollection().add(cfProvPedCustomEmp);
                codEmp = em.merge(codEmp);
            }
            for (ProvPedidosCustom provPedidosCustomCollectionProvPedidosCustom : cfProvPedCustomEmp.getProvPedidosCustomCollection()) {
                CfProvPedCustomEmp oldCfProvPedCustomEmpOfProvPedidosCustomCollectionProvPedidosCustom = provPedidosCustomCollectionProvPedidosCustom.getCfProvPedCustomEmp();
                provPedidosCustomCollectionProvPedidosCustom.setCfProvPedCustomEmp(cfProvPedCustomEmp);
                provPedidosCustomCollectionProvPedidosCustom = em.merge(provPedidosCustomCollectionProvPedidosCustom);
                if (oldCfProvPedCustomEmpOfProvPedidosCustomCollectionProvPedidosCustom != null) {
                    oldCfProvPedCustomEmpOfProvPedidosCustomCollectionProvPedidosCustom.getProvPedidosCustomCollection().remove(provPedidosCustomCollectionProvPedidosCustom);
                    oldCfProvPedCustomEmpOfProvPedidosCustomCollectionProvPedidosCustom = em.merge(oldCfProvPedCustomEmpOfProvPedidosCustomCollectionProvPedidosCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfProvPedCustomEmp cfProvPedCustomEmp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfProvPedCustomEmp persistentCfProvPedCustomEmp = em.find(CfProvPedCustomEmp.class, cfProvPedCustomEmp.getId());
            CfTipoValores tipoValorOld = persistentCfProvPedCustomEmp.getTipoValor();
            CfTipoValores tipoValorNew = cfProvPedCustomEmp.getTipoValor();
            Empresas codEmpOld = persistentCfProvPedCustomEmp.getCodEmp();
            Empresas codEmpNew = cfProvPedCustomEmp.getCodEmp();
            Collection<ProvPedidosCustom> provPedidosCustomCollectionOld = persistentCfProvPedCustomEmp.getProvPedidosCustomCollection();
            Collection<ProvPedidosCustom> provPedidosCustomCollectionNew = cfProvPedCustomEmp.getProvPedidosCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (ProvPedidosCustom provPedidosCustomCollectionOldProvPedidosCustom : provPedidosCustomCollectionOld) {
                if (!provPedidosCustomCollectionNew.contains(provPedidosCustomCollectionOldProvPedidosCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvPedidosCustom " + provPedidosCustomCollectionOldProvPedidosCustom + " since its cfProvPedCustomEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoValorNew != null) {
                tipoValorNew = em.getReference(tipoValorNew.getClass(), tipoValorNew.getId());
                cfProvPedCustomEmp.setTipoValor(tipoValorNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cfProvPedCustomEmp.setCodEmp(codEmpNew);
            }
            Collection<ProvPedidosCustom> attachedProvPedidosCustomCollectionNew = new ArrayList<ProvPedidosCustom>();
            for (ProvPedidosCustom provPedidosCustomCollectionNewProvPedidosCustomToAttach : provPedidosCustomCollectionNew) {
                provPedidosCustomCollectionNewProvPedidosCustomToAttach = em.getReference(provPedidosCustomCollectionNewProvPedidosCustomToAttach.getClass(), provPedidosCustomCollectionNewProvPedidosCustomToAttach.getProvPedidosCustomPK());
                attachedProvPedidosCustomCollectionNew.add(provPedidosCustomCollectionNewProvPedidosCustomToAttach);
            }
            provPedidosCustomCollectionNew = attachedProvPedidosCustomCollectionNew;
            cfProvPedCustomEmp.setProvPedidosCustomCollection(provPedidosCustomCollectionNew);
            cfProvPedCustomEmp = em.merge(cfProvPedCustomEmp);
            if (tipoValorOld != null && !tipoValorOld.equals(tipoValorNew)) {
                tipoValorOld.getCfProvPedCustomEmpCollection().remove(cfProvPedCustomEmp);
                tipoValorOld = em.merge(tipoValorOld);
            }
            if (tipoValorNew != null && !tipoValorNew.equals(tipoValorOld)) {
                tipoValorNew.getCfProvPedCustomEmpCollection().add(cfProvPedCustomEmp);
                tipoValorNew = em.merge(tipoValorNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCfProvPedCustomEmpCollection().remove(cfProvPedCustomEmp);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCfProvPedCustomEmpCollection().add(cfProvPedCustomEmp);
                codEmpNew = em.merge(codEmpNew);
            }
            for (ProvPedidosCustom provPedidosCustomCollectionNewProvPedidosCustom : provPedidosCustomCollectionNew) {
                if (!provPedidosCustomCollectionOld.contains(provPedidosCustomCollectionNewProvPedidosCustom)) {
                    CfProvPedCustomEmp oldCfProvPedCustomEmpOfProvPedidosCustomCollectionNewProvPedidosCustom = provPedidosCustomCollectionNewProvPedidosCustom.getCfProvPedCustomEmp();
                    provPedidosCustomCollectionNewProvPedidosCustom.setCfProvPedCustomEmp(cfProvPedCustomEmp);
                    provPedidosCustomCollectionNewProvPedidosCustom = em.merge(provPedidosCustomCollectionNewProvPedidosCustom);
                    if (oldCfProvPedCustomEmpOfProvPedidosCustomCollectionNewProvPedidosCustom != null && !oldCfProvPedCustomEmpOfProvPedidosCustomCollectionNewProvPedidosCustom.equals(cfProvPedCustomEmp)) {
                        oldCfProvPedCustomEmpOfProvPedidosCustomCollectionNewProvPedidosCustom.getProvPedidosCustomCollection().remove(provPedidosCustomCollectionNewProvPedidosCustom);
                        oldCfProvPedCustomEmpOfProvPedidosCustomCollectionNewProvPedidosCustom = em.merge(oldCfProvPedCustomEmpOfProvPedidosCustomCollectionNewProvPedidosCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfProvPedCustomEmp.getId();
                if (findCfProvPedCustomEmp(id) == null) {
                    throw new NonexistentEntityException("The cfProvPedCustomEmp with id " + id + " no longer exists.");
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
            CfProvPedCustomEmp cfProvPedCustomEmp;
            try {
                cfProvPedCustomEmp = em.getReference(CfProvPedCustomEmp.class, id);
                cfProvPedCustomEmp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfProvPedCustomEmp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProvPedidosCustom> provPedidosCustomCollectionOrphanCheck = cfProvPedCustomEmp.getProvPedidosCustomCollection();
            for (ProvPedidosCustom provPedidosCustomCollectionOrphanCheckProvPedidosCustom : provPedidosCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfProvPedCustomEmp (" + cfProvPedCustomEmp + ") cannot be destroyed since the ProvPedidosCustom " + provPedidosCustomCollectionOrphanCheckProvPedidosCustom + " in its provPedidosCustomCollection field has a non-nullable cfProvPedCustomEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CfTipoValores tipoValor = cfProvPedCustomEmp.getTipoValor();
            if (tipoValor != null) {
                tipoValor.getCfProvPedCustomEmpCollection().remove(cfProvPedCustomEmp);
                tipoValor = em.merge(tipoValor);
            }
            Empresas codEmp = cfProvPedCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp.getCfProvPedCustomEmpCollection().remove(cfProvPedCustomEmp);
                codEmp = em.merge(codEmp);
            }
            em.remove(cfProvPedCustomEmp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfProvPedCustomEmp> findCfProvPedCustomEmpEntities() {
        return findCfProvPedCustomEmpEntities(true, -1, -1);
    }

    public List<CfProvPedCustomEmp> findCfProvPedCustomEmpEntities(int maxResults, int firstResult) {
        return findCfProvPedCustomEmpEntities(false, maxResults, firstResult);
    }

    private List<CfProvPedCustomEmp> findCfProvPedCustomEmpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CfProvPedCustomEmp.class));
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

    public CfProvPedCustomEmp findCfProvPedCustomEmp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfProvPedCustomEmp.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfProvPedCustomEmpCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CfProvPedCustomEmp> rt = cq.from(CfProvPedCustomEmp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
