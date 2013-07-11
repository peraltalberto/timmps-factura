/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.CliAlbCab;
import es.timmps.fac.persistencia.Articulos;
import es.timmps.fac.persistencia.CfTipoImpuesto;
import es.timmps.fac.persistencia.CliAlbLin;
import es.timmps.fac.persistencia.Stock;
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
public class CliAlbLinJpaController implements Serializable {

    public CliAlbLinJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CliAlbLin cliAlbLin) {
        if (cliAlbLin.getStockCollection() == null) {
            cliAlbLin.setStockCollection(new ArrayList<Stock>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliAlbCab codCab = cliAlbLin.getCodCab();
            if (codCab != null) {
                codCab = em.getReference(codCab.getClass(), codCab.getId());
                cliAlbLin.setCodCab(codCab);
            }
            Articulos codProd = cliAlbLin.getCodProd();
            if (codProd != null) {
                codProd = em.getReference(codProd.getClass(), codProd.getCodigo());
                cliAlbLin.setCodProd(codProd);
            }
            CfTipoImpuesto tipoIva = cliAlbLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva = em.getReference(tipoIva.getClass(), tipoIva.getId());
                cliAlbLin.setTipoIva(tipoIva);
            }
            Collection<Stock> attachedStockCollection = new ArrayList<Stock>();
            for (Stock stockCollectionStockToAttach : cliAlbLin.getStockCollection()) {
                stockCollectionStockToAttach = em.getReference(stockCollectionStockToAttach.getClass(), stockCollectionStockToAttach.getId());
                attachedStockCollection.add(stockCollectionStockToAttach);
            }
            cliAlbLin.setStockCollection(attachedStockCollection);
            em.persist(cliAlbLin);
            if (codCab != null) {
                codCab.getCliAlbLinCollection().add(cliAlbLin);
                codCab = em.merge(codCab);
            }
            if (codProd != null) {
                codProd.getCliAlbLinCollection().add(cliAlbLin);
                codProd = em.merge(codProd);
            }
            if (tipoIva != null) {
                tipoIva.getCliAlbLinCollection().add(cliAlbLin);
                tipoIva = em.merge(tipoIva);
            }
            for (Stock stockCollectionStock : cliAlbLin.getStockCollection()) {
                CliAlbLin oldCodAlbLinCliOfStockCollectionStock = stockCollectionStock.getCodAlbLinCli();
                stockCollectionStock.setCodAlbLinCli(cliAlbLin);
                stockCollectionStock = em.merge(stockCollectionStock);
                if (oldCodAlbLinCliOfStockCollectionStock != null) {
                    oldCodAlbLinCliOfStockCollectionStock.getStockCollection().remove(stockCollectionStock);
                    oldCodAlbLinCliOfStockCollectionStock = em.merge(oldCodAlbLinCliOfStockCollectionStock);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CliAlbLin cliAlbLin) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliAlbLin persistentCliAlbLin = em.find(CliAlbLin.class, cliAlbLin.getId());
            CliAlbCab codCabOld = persistentCliAlbLin.getCodCab();
            CliAlbCab codCabNew = cliAlbLin.getCodCab();
            Articulos codProdOld = persistentCliAlbLin.getCodProd();
            Articulos codProdNew = cliAlbLin.getCodProd();
            CfTipoImpuesto tipoIvaOld = persistentCliAlbLin.getTipoIva();
            CfTipoImpuesto tipoIvaNew = cliAlbLin.getTipoIva();
            Collection<Stock> stockCollectionOld = persistentCliAlbLin.getStockCollection();
            Collection<Stock> stockCollectionNew = cliAlbLin.getStockCollection();
            if (codCabNew != null) {
                codCabNew = em.getReference(codCabNew.getClass(), codCabNew.getId());
                cliAlbLin.setCodCab(codCabNew);
            }
            if (codProdNew != null) {
                codProdNew = em.getReference(codProdNew.getClass(), codProdNew.getCodigo());
                cliAlbLin.setCodProd(codProdNew);
            }
            if (tipoIvaNew != null) {
                tipoIvaNew = em.getReference(tipoIvaNew.getClass(), tipoIvaNew.getId());
                cliAlbLin.setTipoIva(tipoIvaNew);
            }
            Collection<Stock> attachedStockCollectionNew = new ArrayList<Stock>();
            for (Stock stockCollectionNewStockToAttach : stockCollectionNew) {
                stockCollectionNewStockToAttach = em.getReference(stockCollectionNewStockToAttach.getClass(), stockCollectionNewStockToAttach.getId());
                attachedStockCollectionNew.add(stockCollectionNewStockToAttach);
            }
            stockCollectionNew = attachedStockCollectionNew;
            cliAlbLin.setStockCollection(stockCollectionNew);
            cliAlbLin = em.merge(cliAlbLin);
            if (codCabOld != null && !codCabOld.equals(codCabNew)) {
                codCabOld.getCliAlbLinCollection().remove(cliAlbLin);
                codCabOld = em.merge(codCabOld);
            }
            if (codCabNew != null && !codCabNew.equals(codCabOld)) {
                codCabNew.getCliAlbLinCollection().add(cliAlbLin);
                codCabNew = em.merge(codCabNew);
            }
            if (codProdOld != null && !codProdOld.equals(codProdNew)) {
                codProdOld.getCliAlbLinCollection().remove(cliAlbLin);
                codProdOld = em.merge(codProdOld);
            }
            if (codProdNew != null && !codProdNew.equals(codProdOld)) {
                codProdNew.getCliAlbLinCollection().add(cliAlbLin);
                codProdNew = em.merge(codProdNew);
            }
            if (tipoIvaOld != null && !tipoIvaOld.equals(tipoIvaNew)) {
                tipoIvaOld.getCliAlbLinCollection().remove(cliAlbLin);
                tipoIvaOld = em.merge(tipoIvaOld);
            }
            if (tipoIvaNew != null && !tipoIvaNew.equals(tipoIvaOld)) {
                tipoIvaNew.getCliAlbLinCollection().add(cliAlbLin);
                tipoIvaNew = em.merge(tipoIvaNew);
            }
            for (Stock stockCollectionOldStock : stockCollectionOld) {
                if (!stockCollectionNew.contains(stockCollectionOldStock)) {
                    stockCollectionOldStock.setCodAlbLinCli(null);
                    stockCollectionOldStock = em.merge(stockCollectionOldStock);
                }
            }
            for (Stock stockCollectionNewStock : stockCollectionNew) {
                if (!stockCollectionOld.contains(stockCollectionNewStock)) {
                    CliAlbLin oldCodAlbLinCliOfStockCollectionNewStock = stockCollectionNewStock.getCodAlbLinCli();
                    stockCollectionNewStock.setCodAlbLinCli(cliAlbLin);
                    stockCollectionNewStock = em.merge(stockCollectionNewStock);
                    if (oldCodAlbLinCliOfStockCollectionNewStock != null && !oldCodAlbLinCliOfStockCollectionNewStock.equals(cliAlbLin)) {
                        oldCodAlbLinCliOfStockCollectionNewStock.getStockCollection().remove(stockCollectionNewStock);
                        oldCodAlbLinCliOfStockCollectionNewStock = em.merge(oldCodAlbLinCliOfStockCollectionNewStock);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliAlbLin.getId();
                if (findCliAlbLin(id) == null) {
                    throw new NonexistentEntityException("The cliAlbLin with id " + id + " no longer exists.");
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
            CliAlbLin cliAlbLin;
            try {
                cliAlbLin = em.getReference(CliAlbLin.class, id);
                cliAlbLin.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliAlbLin with id " + id + " no longer exists.", enfe);
            }
            CliAlbCab codCab = cliAlbLin.getCodCab();
            if (codCab != null) {
                codCab.getCliAlbLinCollection().remove(cliAlbLin);
                codCab = em.merge(codCab);
            }
            Articulos codProd = cliAlbLin.getCodProd();
            if (codProd != null) {
                codProd.getCliAlbLinCollection().remove(cliAlbLin);
                codProd = em.merge(codProd);
            }
            CfTipoImpuesto tipoIva = cliAlbLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva.getCliAlbLinCollection().remove(cliAlbLin);
                tipoIva = em.merge(tipoIva);
            }
            Collection<Stock> stockCollection = cliAlbLin.getStockCollection();
            for (Stock stockCollectionStock : stockCollection) {
                stockCollectionStock.setCodAlbLinCli(null);
                stockCollectionStock = em.merge(stockCollectionStock);
            }
            em.remove(cliAlbLin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CliAlbLin> findCliAlbLinEntities() {
        return findCliAlbLinEntities(true, -1, -1);
    }

    public List<CliAlbLin> findCliAlbLinEntities(int maxResults, int firstResult) {
        return findCliAlbLinEntities(false, maxResults, firstResult);
    }

    private List<CliAlbLin> findCliAlbLinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CliAlbLin as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CliAlbLin findCliAlbLin(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CliAlbLin.class, id);
        } finally {
            em.close();
        }
    }

    public int getCliAlbLinCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CliAlbLin as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
