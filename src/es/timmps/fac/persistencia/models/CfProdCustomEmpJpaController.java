/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.CfTipoValores;
import es.timmps.fac.persistencia.ArticulosCustom;
import es.timmps.fac.persistencia.CfProdCustomEmp;
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
public class CfProdCustomEmpJpaController implements Serializable {

    public CfProdCustomEmpJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfProdCustomEmp cfProdCustomEmp) {
        if (cfProdCustomEmp.getArticulosCustomCollection() == null) {
            cfProdCustomEmp.setArticulosCustomCollection(new ArrayList<ArticulosCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = cfProdCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cfProdCustomEmp.setCodEmp(codEmp);
            }
            CfTipoValores tipoValor = cfProdCustomEmp.getTipoValor();
            if (tipoValor != null) {
                tipoValor = em.getReference(tipoValor.getClass(), tipoValor.getId());
                cfProdCustomEmp.setTipoValor(tipoValor);
            }
            Collection<ArticulosCustom> attachedArticulosCustomCollection = new ArrayList<ArticulosCustom>();
            for (ArticulosCustom articulosCustomCollectionArticulosCustomToAttach : cfProdCustomEmp.getArticulosCustomCollection()) {
                articulosCustomCollectionArticulosCustomToAttach = em.getReference(articulosCustomCollectionArticulosCustomToAttach.getClass(), articulosCustomCollectionArticulosCustomToAttach.getArticulosCustomPK());
                attachedArticulosCustomCollection.add(articulosCustomCollectionArticulosCustomToAttach);
            }
            cfProdCustomEmp.setArticulosCustomCollection(attachedArticulosCustomCollection);
            em.persist(cfProdCustomEmp);
            if (codEmp != null) {
                codEmp.getCfProdCustomEmpCollection().add(cfProdCustomEmp);
                codEmp = em.merge(codEmp);
            }
            if (tipoValor != null) {
                tipoValor.getCfProdCustomEmpCollection().add(cfProdCustomEmp);
                tipoValor = em.merge(tipoValor);
            }
            for (ArticulosCustom articulosCustomCollectionArticulosCustom : cfProdCustomEmp.getArticulosCustomCollection()) {
                CfProdCustomEmp oldCfProdCustomEmpOfArticulosCustomCollectionArticulosCustom = articulosCustomCollectionArticulosCustom.getCfProdCustomEmp();
                articulosCustomCollectionArticulosCustom.setCfProdCustomEmp(cfProdCustomEmp);
                articulosCustomCollectionArticulosCustom = em.merge(articulosCustomCollectionArticulosCustom);
                if (oldCfProdCustomEmpOfArticulosCustomCollectionArticulosCustom != null) {
                    oldCfProdCustomEmpOfArticulosCustomCollectionArticulosCustom.getArticulosCustomCollection().remove(articulosCustomCollectionArticulosCustom);
                    oldCfProdCustomEmpOfArticulosCustomCollectionArticulosCustom = em.merge(oldCfProdCustomEmpOfArticulosCustomCollectionArticulosCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfProdCustomEmp cfProdCustomEmp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfProdCustomEmp persistentCfProdCustomEmp = em.find(CfProdCustomEmp.class, cfProdCustomEmp.getId());
            Empresas codEmpOld = persistentCfProdCustomEmp.getCodEmp();
            Empresas codEmpNew = cfProdCustomEmp.getCodEmp();
            CfTipoValores tipoValorOld = persistentCfProdCustomEmp.getTipoValor();
            CfTipoValores tipoValorNew = cfProdCustomEmp.getTipoValor();
            Collection<ArticulosCustom> articulosCustomCollectionOld = persistentCfProdCustomEmp.getArticulosCustomCollection();
            Collection<ArticulosCustom> articulosCustomCollectionNew = cfProdCustomEmp.getArticulosCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (ArticulosCustom articulosCustomCollectionOldArticulosCustom : articulosCustomCollectionOld) {
                if (!articulosCustomCollectionNew.contains(articulosCustomCollectionOldArticulosCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ArticulosCustom " + articulosCustomCollectionOldArticulosCustom + " since its cfProdCustomEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cfProdCustomEmp.setCodEmp(codEmpNew);
            }
            if (tipoValorNew != null) {
                tipoValorNew = em.getReference(tipoValorNew.getClass(), tipoValorNew.getId());
                cfProdCustomEmp.setTipoValor(tipoValorNew);
            }
            Collection<ArticulosCustom> attachedArticulosCustomCollectionNew = new ArrayList<ArticulosCustom>();
            for (ArticulosCustom articulosCustomCollectionNewArticulosCustomToAttach : articulosCustomCollectionNew) {
                articulosCustomCollectionNewArticulosCustomToAttach = em.getReference(articulosCustomCollectionNewArticulosCustomToAttach.getClass(), articulosCustomCollectionNewArticulosCustomToAttach.getArticulosCustomPK());
                attachedArticulosCustomCollectionNew.add(articulosCustomCollectionNewArticulosCustomToAttach);
            }
            articulosCustomCollectionNew = attachedArticulosCustomCollectionNew;
            cfProdCustomEmp.setArticulosCustomCollection(articulosCustomCollectionNew);
            cfProdCustomEmp = em.merge(cfProdCustomEmp);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCfProdCustomEmpCollection().remove(cfProdCustomEmp);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCfProdCustomEmpCollection().add(cfProdCustomEmp);
                codEmpNew = em.merge(codEmpNew);
            }
            if (tipoValorOld != null && !tipoValorOld.equals(tipoValorNew)) {
                tipoValorOld.getCfProdCustomEmpCollection().remove(cfProdCustomEmp);
                tipoValorOld = em.merge(tipoValorOld);
            }
            if (tipoValorNew != null && !tipoValorNew.equals(tipoValorOld)) {
                tipoValorNew.getCfProdCustomEmpCollection().add(cfProdCustomEmp);
                tipoValorNew = em.merge(tipoValorNew);
            }
            for (ArticulosCustom articulosCustomCollectionNewArticulosCustom : articulosCustomCollectionNew) {
                if (!articulosCustomCollectionOld.contains(articulosCustomCollectionNewArticulosCustom)) {
                    CfProdCustomEmp oldCfProdCustomEmpOfArticulosCustomCollectionNewArticulosCustom = articulosCustomCollectionNewArticulosCustom.getCfProdCustomEmp();
                    articulosCustomCollectionNewArticulosCustom.setCfProdCustomEmp(cfProdCustomEmp);
                    articulosCustomCollectionNewArticulosCustom = em.merge(articulosCustomCollectionNewArticulosCustom);
                    if (oldCfProdCustomEmpOfArticulosCustomCollectionNewArticulosCustom != null && !oldCfProdCustomEmpOfArticulosCustomCollectionNewArticulosCustom.equals(cfProdCustomEmp)) {
                        oldCfProdCustomEmpOfArticulosCustomCollectionNewArticulosCustom.getArticulosCustomCollection().remove(articulosCustomCollectionNewArticulosCustom);
                        oldCfProdCustomEmpOfArticulosCustomCollectionNewArticulosCustom = em.merge(oldCfProdCustomEmpOfArticulosCustomCollectionNewArticulosCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfProdCustomEmp.getId();
                if (findCfProdCustomEmp(id) == null) {
                    throw new NonexistentEntityException("The cfProdCustomEmp with id " + id + " no longer exists.");
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
            CfProdCustomEmp cfProdCustomEmp;
            try {
                cfProdCustomEmp = em.getReference(CfProdCustomEmp.class, id);
                cfProdCustomEmp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfProdCustomEmp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ArticulosCustom> articulosCustomCollectionOrphanCheck = cfProdCustomEmp.getArticulosCustomCollection();
            for (ArticulosCustom articulosCustomCollectionOrphanCheckArticulosCustom : articulosCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfProdCustomEmp (" + cfProdCustomEmp + ") cannot be destroyed since the ArticulosCustom " + articulosCustomCollectionOrphanCheckArticulosCustom + " in its articulosCustomCollection field has a non-nullable cfProdCustomEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresas codEmp = cfProdCustomEmp.getCodEmp();
            if (codEmp != null) {
                codEmp.getCfProdCustomEmpCollection().remove(cfProdCustomEmp);
                codEmp = em.merge(codEmp);
            }
            CfTipoValores tipoValor = cfProdCustomEmp.getTipoValor();
            if (tipoValor != null) {
                tipoValor.getCfProdCustomEmpCollection().remove(cfProdCustomEmp);
                tipoValor = em.merge(tipoValor);
            }
            em.remove(cfProdCustomEmp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfProdCustomEmp> findCfProdCustomEmpEntities() {
        return findCfProdCustomEmpEntities(true, -1, -1);
    }

    public List<CfProdCustomEmp> findCfProdCustomEmpEntities(int maxResults, int firstResult) {
        return findCfProdCustomEmpEntities(false, maxResults, firstResult);
    }

    private List<CfProdCustomEmp> findCfProdCustomEmpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CfProdCustomEmp as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CfProdCustomEmp findCfProdCustomEmp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfProdCustomEmp.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfProdCustomEmpCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CfProdCustomEmp as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
