/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.Articulos;
import es.timmps.fac.persistencia.pojos.ProvAlbCab;
import es.timmps.fac.persistencia.pojos.CfTipoImpuesto;
import es.timmps.fac.persistencia.pojos.ProvAlbLin;
import es.timmps.fac.persistencia.pojos.Stock;
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
public class ProvAlbLinJpaController implements Serializable {

    public ProvAlbLinJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProvAlbLin provAlbLin) {
        if (provAlbLin.getStockCollection() == null) {
            provAlbLin.setStockCollection(new ArrayList<Stock>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos codProd = provAlbLin.getCodProd();
            if (codProd != null) {
                codProd = em.getReference(codProd.getClass(), codProd.getCodigo());
                provAlbLin.setCodProd(codProd);
            }
            ProvAlbCab codCab = provAlbLin.getCodCab();
            if (codCab != null) {
                codCab = em.getReference(codCab.getClass(), codCab.getId());
                provAlbLin.setCodCab(codCab);
            }
            CfTipoImpuesto tipoIva = provAlbLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva = em.getReference(tipoIva.getClass(), tipoIva.getId());
                provAlbLin.setTipoIva(tipoIva);
            }
            Collection<Stock> attachedStockCollection = new ArrayList<Stock>();
            for (Stock stockCollectionStockToAttach : provAlbLin.getStockCollection()) {
                stockCollectionStockToAttach = em.getReference(stockCollectionStockToAttach.getClass(), stockCollectionStockToAttach.getId());
                attachedStockCollection.add(stockCollectionStockToAttach);
            }
            provAlbLin.setStockCollection(attachedStockCollection);
            em.persist(provAlbLin);
            if (codProd != null) {
                codProd.getProvAlbLinCollection().add(provAlbLin);
                codProd = em.merge(codProd);
            }
            if (codCab != null) {
                codCab.getProvAlbLinCollection().add(provAlbLin);
                codCab = em.merge(codCab);
            }
            if (tipoIva != null) {
                tipoIva.getProvAlbLinCollection().add(provAlbLin);
                tipoIva = em.merge(tipoIva);
            }
            for (Stock stockCollectionStock : provAlbLin.getStockCollection()) {
                ProvAlbLin oldCodAlbLinProvOfStockCollectionStock = stockCollectionStock.getCodAlbLinProv();
                stockCollectionStock.setCodAlbLinProv(provAlbLin);
                stockCollectionStock = em.merge(stockCollectionStock);
                if (oldCodAlbLinProvOfStockCollectionStock != null) {
                    oldCodAlbLinProvOfStockCollectionStock.getStockCollection().remove(stockCollectionStock);
                    oldCodAlbLinProvOfStockCollectionStock = em.merge(oldCodAlbLinProvOfStockCollectionStock);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProvAlbLin provAlbLin) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvAlbLin persistentProvAlbLin = em.find(ProvAlbLin.class, provAlbLin.getId());
            Articulos codProdOld = persistentProvAlbLin.getCodProd();
            Articulos codProdNew = provAlbLin.getCodProd();
            ProvAlbCab codCabOld = persistentProvAlbLin.getCodCab();
            ProvAlbCab codCabNew = provAlbLin.getCodCab();
            CfTipoImpuesto tipoIvaOld = persistentProvAlbLin.getTipoIva();
            CfTipoImpuesto tipoIvaNew = provAlbLin.getTipoIva();
            Collection<Stock> stockCollectionOld = persistentProvAlbLin.getStockCollection();
            Collection<Stock> stockCollectionNew = provAlbLin.getStockCollection();
            if (codProdNew != null) {
                codProdNew = em.getReference(codProdNew.getClass(), codProdNew.getCodigo());
                provAlbLin.setCodProd(codProdNew);
            }
            if (codCabNew != null) {
                codCabNew = em.getReference(codCabNew.getClass(), codCabNew.getId());
                provAlbLin.setCodCab(codCabNew);
            }
            if (tipoIvaNew != null) {
                tipoIvaNew = em.getReference(tipoIvaNew.getClass(), tipoIvaNew.getId());
                provAlbLin.setTipoIva(tipoIvaNew);
            }
            Collection<Stock> attachedStockCollectionNew = new ArrayList<Stock>();
            for (Stock stockCollectionNewStockToAttach : stockCollectionNew) {
                stockCollectionNewStockToAttach = em.getReference(stockCollectionNewStockToAttach.getClass(), stockCollectionNewStockToAttach.getId());
                attachedStockCollectionNew.add(stockCollectionNewStockToAttach);
            }
            stockCollectionNew = attachedStockCollectionNew;
            provAlbLin.setStockCollection(stockCollectionNew);
            provAlbLin = em.merge(provAlbLin);
            if (codProdOld != null && !codProdOld.equals(codProdNew)) {
                codProdOld.getProvAlbLinCollection().remove(provAlbLin);
                codProdOld = em.merge(codProdOld);
            }
            if (codProdNew != null && !codProdNew.equals(codProdOld)) {
                codProdNew.getProvAlbLinCollection().add(provAlbLin);
                codProdNew = em.merge(codProdNew);
            }
            if (codCabOld != null && !codCabOld.equals(codCabNew)) {
                codCabOld.getProvAlbLinCollection().remove(provAlbLin);
                codCabOld = em.merge(codCabOld);
            }
            if (codCabNew != null && !codCabNew.equals(codCabOld)) {
                codCabNew.getProvAlbLinCollection().add(provAlbLin);
                codCabNew = em.merge(codCabNew);
            }
            if (tipoIvaOld != null && !tipoIvaOld.equals(tipoIvaNew)) {
                tipoIvaOld.getProvAlbLinCollection().remove(provAlbLin);
                tipoIvaOld = em.merge(tipoIvaOld);
            }
            if (tipoIvaNew != null && !tipoIvaNew.equals(tipoIvaOld)) {
                tipoIvaNew.getProvAlbLinCollection().add(provAlbLin);
                tipoIvaNew = em.merge(tipoIvaNew);
            }
            for (Stock stockCollectionOldStock : stockCollectionOld) {
                if (!stockCollectionNew.contains(stockCollectionOldStock)) {
                    stockCollectionOldStock.setCodAlbLinProv(null);
                    stockCollectionOldStock = em.merge(stockCollectionOldStock);
                }
            }
            for (Stock stockCollectionNewStock : stockCollectionNew) {
                if (!stockCollectionOld.contains(stockCollectionNewStock)) {
                    ProvAlbLin oldCodAlbLinProvOfStockCollectionNewStock = stockCollectionNewStock.getCodAlbLinProv();
                    stockCollectionNewStock.setCodAlbLinProv(provAlbLin);
                    stockCollectionNewStock = em.merge(stockCollectionNewStock);
                    if (oldCodAlbLinProvOfStockCollectionNewStock != null && !oldCodAlbLinProvOfStockCollectionNewStock.equals(provAlbLin)) {
                        oldCodAlbLinProvOfStockCollectionNewStock.getStockCollection().remove(stockCollectionNewStock);
                        oldCodAlbLinProvOfStockCollectionNewStock = em.merge(oldCodAlbLinProvOfStockCollectionNewStock);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provAlbLin.getId();
                if (findProvAlbLin(id) == null) {
                    throw new NonexistentEntityException("The provAlbLin with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvAlbLin provAlbLin;
            try {
                provAlbLin = em.getReference(ProvAlbLin.class, id);
                provAlbLin.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provAlbLin with id " + id + " no longer exists.", enfe);
            }
            Articulos codProd = provAlbLin.getCodProd();
            if (codProd != null) {
                codProd.getProvAlbLinCollection().remove(provAlbLin);
                codProd = em.merge(codProd);
            }
            ProvAlbCab codCab = provAlbLin.getCodCab();
            if (codCab != null) {
                codCab.getProvAlbLinCollection().remove(provAlbLin);
                codCab = em.merge(codCab);
            }
            CfTipoImpuesto tipoIva = provAlbLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva.getProvAlbLinCollection().remove(provAlbLin);
                tipoIva = em.merge(tipoIva);
            }
            Collection<Stock> stockCollection = provAlbLin.getStockCollection();
            for (Stock stockCollectionStock : stockCollection) {
                stockCollectionStock.setCodAlbLinProv(null);
                stockCollectionStock = em.merge(stockCollectionStock);
            }
            em.remove(provAlbLin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProvAlbLin> findProvAlbLinEntities() {
        return findProvAlbLinEntities(true, -1, -1);
    }

    public List<ProvAlbLin> findProvAlbLinEntities(int maxResults, int firstResult) {
        return findProvAlbLinEntities(false, maxResults, firstResult);
    }

    private List<ProvAlbLin> findProvAlbLinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProvAlbLin.class));
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

    public ProvAlbLin findProvAlbLin(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProvAlbLin.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvAlbLinCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProvAlbLin> rt = cq.from(ProvAlbLin.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
