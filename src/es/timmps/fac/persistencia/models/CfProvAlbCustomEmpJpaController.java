/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.CfProvAlbCustomEmp;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.CfTipoValores;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.ProvAlbCustom;
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
public class CfProvAlbCustomEmpJpaController implements Serializable {

    public CfProvAlbCustomEmpJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfProvAlbCustomEmp cfProvAlbCustomEmp) {
        if (cfProvAlbCustomEmp.getProvAlbCustomCollection() == null) {
            cfProvAlbCustomEmp.setProvAlbCustomCollection(new ArrayList<ProvAlbCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfTipoValores tipoValor = cfProvAlbCustomEmp.getTipoValor();
            if (tipoValor != null) {
                tipoValor = em.getReference(tipoValor.getClass(), tipoValor.getId());
                cfProvAlbCustomEmp.setTipoValor(tipoValor);
            }
            Empresas codEmp = cfProvAlbCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cfProvAlbCustomEmp.setCodEmp(codEmp);
            }
            Collection<ProvAlbCustom> attachedProvAlbCustomCollection = new ArrayList<ProvAlbCustom>();
            for (ProvAlbCustom provAlbCustomCollectionProvAlbCustomToAttach : cfProvAlbCustomEmp.getProvAlbCustomCollection()) {
                provAlbCustomCollectionProvAlbCustomToAttach = em.getReference(provAlbCustomCollectionProvAlbCustomToAttach.getClass(), provAlbCustomCollectionProvAlbCustomToAttach.getProvAlbCustomPK());
                attachedProvAlbCustomCollection.add(provAlbCustomCollectionProvAlbCustomToAttach);
            }
            cfProvAlbCustomEmp.setProvAlbCustomCollection(attachedProvAlbCustomCollection);
            em.persist(cfProvAlbCustomEmp);
            if (tipoValor != null) {
                tipoValor.getCfProvAlbCustomEmpCollection().add(cfProvAlbCustomEmp);
                tipoValor = em.merge(tipoValor);
            }
            if (codEmp != null) {
                codEmp.getCfProvAlbCustomEmpCollection().add(cfProvAlbCustomEmp);
                codEmp = em.merge(codEmp);
            }
            for (ProvAlbCustom provAlbCustomCollectionProvAlbCustom : cfProvAlbCustomEmp.getProvAlbCustomCollection()) {
                CfProvAlbCustomEmp oldCfProvAlbCustomEmpOfProvAlbCustomCollectionProvAlbCustom = provAlbCustomCollectionProvAlbCustom.getCfProvAlbCustomEmp();
                provAlbCustomCollectionProvAlbCustom.setCfProvAlbCustomEmp(cfProvAlbCustomEmp);
                provAlbCustomCollectionProvAlbCustom = em.merge(provAlbCustomCollectionProvAlbCustom);
                if (oldCfProvAlbCustomEmpOfProvAlbCustomCollectionProvAlbCustom != null) {
                    oldCfProvAlbCustomEmpOfProvAlbCustomCollectionProvAlbCustom.getProvAlbCustomCollection().remove(provAlbCustomCollectionProvAlbCustom);
                    oldCfProvAlbCustomEmpOfProvAlbCustomCollectionProvAlbCustom = em.merge(oldCfProvAlbCustomEmpOfProvAlbCustomCollectionProvAlbCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfProvAlbCustomEmp cfProvAlbCustomEmp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfProvAlbCustomEmp persistentCfProvAlbCustomEmp = em.find(CfProvAlbCustomEmp.class, cfProvAlbCustomEmp.getId());
            CfTipoValores tipoValorOld = persistentCfProvAlbCustomEmp.getTipoValor();
            CfTipoValores tipoValorNew = cfProvAlbCustomEmp.getTipoValor();
            Empresas codEmpOld = persistentCfProvAlbCustomEmp.getCodEmp();
            Empresas codEmpNew = cfProvAlbCustomEmp.getCodEmp();
            Collection<ProvAlbCustom> provAlbCustomCollectionOld = persistentCfProvAlbCustomEmp.getProvAlbCustomCollection();
            Collection<ProvAlbCustom> provAlbCustomCollectionNew = cfProvAlbCustomEmp.getProvAlbCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (ProvAlbCustom provAlbCustomCollectionOldProvAlbCustom : provAlbCustomCollectionOld) {
                if (!provAlbCustomCollectionNew.contains(provAlbCustomCollectionOldProvAlbCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvAlbCustom " + provAlbCustomCollectionOldProvAlbCustom + " since its cfProvAlbCustomEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoValorNew != null) {
                tipoValorNew = em.getReference(tipoValorNew.getClass(), tipoValorNew.getId());
                cfProvAlbCustomEmp.setTipoValor(tipoValorNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cfProvAlbCustomEmp.setCodEmp(codEmpNew);
            }
            Collection<ProvAlbCustom> attachedProvAlbCustomCollectionNew = new ArrayList<ProvAlbCustom>();
            for (ProvAlbCustom provAlbCustomCollectionNewProvAlbCustomToAttach : provAlbCustomCollectionNew) {
                provAlbCustomCollectionNewProvAlbCustomToAttach = em.getReference(provAlbCustomCollectionNewProvAlbCustomToAttach.getClass(), provAlbCustomCollectionNewProvAlbCustomToAttach.getProvAlbCustomPK());
                attachedProvAlbCustomCollectionNew.add(provAlbCustomCollectionNewProvAlbCustomToAttach);
            }
            provAlbCustomCollectionNew = attachedProvAlbCustomCollectionNew;
            cfProvAlbCustomEmp.setProvAlbCustomCollection(provAlbCustomCollectionNew);
            cfProvAlbCustomEmp = em.merge(cfProvAlbCustomEmp);
            if (tipoValorOld != null && !tipoValorOld.equals(tipoValorNew)) {
                tipoValorOld.getCfProvAlbCustomEmpCollection().remove(cfProvAlbCustomEmp);
                tipoValorOld = em.merge(tipoValorOld);
            }
            if (tipoValorNew != null && !tipoValorNew.equals(tipoValorOld)) {
                tipoValorNew.getCfProvAlbCustomEmpCollection().add(cfProvAlbCustomEmp);
                tipoValorNew = em.merge(tipoValorNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCfProvAlbCustomEmpCollection().remove(cfProvAlbCustomEmp);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCfProvAlbCustomEmpCollection().add(cfProvAlbCustomEmp);
                codEmpNew = em.merge(codEmpNew);
            }
            for (ProvAlbCustom provAlbCustomCollectionNewProvAlbCustom : provAlbCustomCollectionNew) {
                if (!provAlbCustomCollectionOld.contains(provAlbCustomCollectionNewProvAlbCustom)) {
                    CfProvAlbCustomEmp oldCfProvAlbCustomEmpOfProvAlbCustomCollectionNewProvAlbCustom = provAlbCustomCollectionNewProvAlbCustom.getCfProvAlbCustomEmp();
                    provAlbCustomCollectionNewProvAlbCustom.setCfProvAlbCustomEmp(cfProvAlbCustomEmp);
                    provAlbCustomCollectionNewProvAlbCustom = em.merge(provAlbCustomCollectionNewProvAlbCustom);
                    if (oldCfProvAlbCustomEmpOfProvAlbCustomCollectionNewProvAlbCustom != null && !oldCfProvAlbCustomEmpOfProvAlbCustomCollectionNewProvAlbCustom.equals(cfProvAlbCustomEmp)) {
                        oldCfProvAlbCustomEmpOfProvAlbCustomCollectionNewProvAlbCustom.getProvAlbCustomCollection().remove(provAlbCustomCollectionNewProvAlbCustom);
                        oldCfProvAlbCustomEmpOfProvAlbCustomCollectionNewProvAlbCustom = em.merge(oldCfProvAlbCustomEmpOfProvAlbCustomCollectionNewProvAlbCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfProvAlbCustomEmp.getId();
                if (findCfProvAlbCustomEmp(id) == null) {
                    throw new NonexistentEntityException("The cfProvAlbCustomEmp with id " + id + " no longer exists.");
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
            CfProvAlbCustomEmp cfProvAlbCustomEmp;
            try {
                cfProvAlbCustomEmp = em.getReference(CfProvAlbCustomEmp.class, id);
                cfProvAlbCustomEmp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfProvAlbCustomEmp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProvAlbCustom> provAlbCustomCollectionOrphanCheck = cfProvAlbCustomEmp.getProvAlbCustomCollection();
            for (ProvAlbCustom provAlbCustomCollectionOrphanCheckProvAlbCustom : provAlbCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfProvAlbCustomEmp (" + cfProvAlbCustomEmp + ") cannot be destroyed since the ProvAlbCustom " + provAlbCustomCollectionOrphanCheckProvAlbCustom + " in its provAlbCustomCollection field has a non-nullable cfProvAlbCustomEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CfTipoValores tipoValor = cfProvAlbCustomEmp.getTipoValor();
            if (tipoValor != null) {
                tipoValor.getCfProvAlbCustomEmpCollection().remove(cfProvAlbCustomEmp);
                tipoValor = em.merge(tipoValor);
            }
            Empresas codEmp = cfProvAlbCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp.getCfProvAlbCustomEmpCollection().remove(cfProvAlbCustomEmp);
                codEmp = em.merge(codEmp);
            }
            em.remove(cfProvAlbCustomEmp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfProvAlbCustomEmp> findCfProvAlbCustomEmpEntities() {
        return findCfProvAlbCustomEmpEntities(true, -1, -1);
    }

    public List<CfProvAlbCustomEmp> findCfProvAlbCustomEmpEntities(int maxResults, int firstResult) {
        return findCfProvAlbCustomEmpEntities(false, maxResults, firstResult);
    }

    private List<CfProvAlbCustomEmp> findCfProvAlbCustomEmpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CfProvAlbCustomEmp as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CfProvAlbCustomEmp findCfProvAlbCustomEmp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfProvAlbCustomEmp.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfProvAlbCustomEmpCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CfProvAlbCustomEmp as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
