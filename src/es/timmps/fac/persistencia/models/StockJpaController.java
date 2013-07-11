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
import es.timmps.fac.persistencia.pojos.CliAlbLin;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.Articulos;
import es.timmps.fac.persistencia.pojos.Almacenes;
import es.timmps.fac.persistencia.pojos.ProvAlbLin;
import es.timmps.fac.persistencia.pojos.Stock;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class StockJpaController implements Serializable {

    public StockJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Stock stock) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliAlbLin codAlbLinCli = stock.getCodAlbLinCli();
            if (codAlbLinCli != null) {
                codAlbLinCli = em.getReference(codAlbLinCli.getClass(), codAlbLinCli.getId());
                stock.setCodAlbLinCli(codAlbLinCli);
            }
            Empresas codEmp = stock.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                stock.setCodEmp(codEmp);
            }
            Articulos codProd = stock.getCodProd();
            if (codProd != null) {
                codProd = em.getReference(codProd.getClass(), codProd.getCodigo());
                stock.setCodProd(codProd);
            }
            Almacenes codAlmacen = stock.getCodAlmacen();
            if (codAlmacen != null) {
                codAlmacen = em.getReference(codAlmacen.getClass(), codAlmacen.getCodigo());
                stock.setCodAlmacen(codAlmacen);
            }
            ProvAlbLin codAlbLinProv = stock.getCodAlbLinProv();
            if (codAlbLinProv != null) {
                codAlbLinProv = em.getReference(codAlbLinProv.getClass(), codAlbLinProv.getId());
                stock.setCodAlbLinProv(codAlbLinProv);
            }
            em.persist(stock);
            if (codAlbLinCli != null) {
                codAlbLinCli.getStockCollection().add(stock);
                codAlbLinCli = em.merge(codAlbLinCli);
            }
            if (codEmp != null) {
                codEmp.getStockCollection().add(stock);
                codEmp = em.merge(codEmp);
            }
            if (codProd != null) {
                codProd.getStockCollection().add(stock);
                codProd = em.merge(codProd);
            }
            if (codAlmacen != null) {
                codAlmacen.getStockCollection().add(stock);
                codAlmacen = em.merge(codAlmacen);
            }
            if (codAlbLinProv != null) {
                codAlbLinProv.getStockCollection().add(stock);
                codAlbLinProv = em.merge(codAlbLinProv);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Stock stock) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Stock persistentStock = em.find(Stock.class, stock.getId());
            CliAlbLin codAlbLinCliOld = persistentStock.getCodAlbLinCli();
            CliAlbLin codAlbLinCliNew = stock.getCodAlbLinCli();
            Empresas codEmpOld = persistentStock.getCodEmp();
            Empresas codEmpNew = stock.getCodEmp();
            Articulos codProdOld = persistentStock.getCodProd();
            Articulos codProdNew = stock.getCodProd();
            Almacenes codAlmacenOld = persistentStock.getCodAlmacen();
            Almacenes codAlmacenNew = stock.getCodAlmacen();
            ProvAlbLin codAlbLinProvOld = persistentStock.getCodAlbLinProv();
            ProvAlbLin codAlbLinProvNew = stock.getCodAlbLinProv();
            if (codAlbLinCliNew != null) {
                codAlbLinCliNew = em.getReference(codAlbLinCliNew.getClass(), codAlbLinCliNew.getId());
                stock.setCodAlbLinCli(codAlbLinCliNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                stock.setCodEmp(codEmpNew);
            }
            if (codProdNew != null) {
                codProdNew = em.getReference(codProdNew.getClass(), codProdNew.getCodigo());
                stock.setCodProd(codProdNew);
            }
            if (codAlmacenNew != null) {
                codAlmacenNew = em.getReference(codAlmacenNew.getClass(), codAlmacenNew.getCodigo());
                stock.setCodAlmacen(codAlmacenNew);
            }
            if (codAlbLinProvNew != null) {
                codAlbLinProvNew = em.getReference(codAlbLinProvNew.getClass(), codAlbLinProvNew.getId());
                stock.setCodAlbLinProv(codAlbLinProvNew);
            }
            stock = em.merge(stock);
            if (codAlbLinCliOld != null && !codAlbLinCliOld.equals(codAlbLinCliNew)) {
                codAlbLinCliOld.getStockCollection().remove(stock);
                codAlbLinCliOld = em.merge(codAlbLinCliOld);
            }
            if (codAlbLinCliNew != null && !codAlbLinCliNew.equals(codAlbLinCliOld)) {
                codAlbLinCliNew.getStockCollection().add(stock);
                codAlbLinCliNew = em.merge(codAlbLinCliNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getStockCollection().remove(stock);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getStockCollection().add(stock);
                codEmpNew = em.merge(codEmpNew);
            }
            if (codProdOld != null && !codProdOld.equals(codProdNew)) {
                codProdOld.getStockCollection().remove(stock);
                codProdOld = em.merge(codProdOld);
            }
            if (codProdNew != null && !codProdNew.equals(codProdOld)) {
                codProdNew.getStockCollection().add(stock);
                codProdNew = em.merge(codProdNew);
            }
            if (codAlmacenOld != null && !codAlmacenOld.equals(codAlmacenNew)) {
                codAlmacenOld.getStockCollection().remove(stock);
                codAlmacenOld = em.merge(codAlmacenOld);
            }
            if (codAlmacenNew != null && !codAlmacenNew.equals(codAlmacenOld)) {
                codAlmacenNew.getStockCollection().add(stock);
                codAlmacenNew = em.merge(codAlmacenNew);
            }
            if (codAlbLinProvOld != null && !codAlbLinProvOld.equals(codAlbLinProvNew)) {
                codAlbLinProvOld.getStockCollection().remove(stock);
                codAlbLinProvOld = em.merge(codAlbLinProvOld);
            }
            if (codAlbLinProvNew != null && !codAlbLinProvNew.equals(codAlbLinProvOld)) {
                codAlbLinProvNew.getStockCollection().add(stock);
                codAlbLinProvNew = em.merge(codAlbLinProvNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = stock.getId();
                if (findStock(id) == null) {
                    throw new NonexistentEntityException("The stock with id " + id + " no longer exists.");
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
            Stock stock;
            try {
                stock = em.getReference(Stock.class, id);
                stock.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stock with id " + id + " no longer exists.", enfe);
            }
            CliAlbLin codAlbLinCli = stock.getCodAlbLinCli();
            if (codAlbLinCli != null) {
                codAlbLinCli.getStockCollection().remove(stock);
                codAlbLinCli = em.merge(codAlbLinCli);
            }
            Empresas codEmp = stock.getCodEmp();
            if (codEmp != null) {
                codEmp.getStockCollection().remove(stock);
                codEmp = em.merge(codEmp);
            }
            Articulos codProd = stock.getCodProd();
            if (codProd != null) {
                codProd.getStockCollection().remove(stock);
                codProd = em.merge(codProd);
            }
            Almacenes codAlmacen = stock.getCodAlmacen();
            if (codAlmacen != null) {
                codAlmacen.getStockCollection().remove(stock);
                codAlmacen = em.merge(codAlmacen);
            }
            ProvAlbLin codAlbLinProv = stock.getCodAlbLinProv();
            if (codAlbLinProv != null) {
                codAlbLinProv.getStockCollection().remove(stock);
                codAlbLinProv = em.merge(codAlbLinProv);
            }
            em.remove(stock);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Stock> findStockEntities() {
        return findStockEntities(true, -1, -1);
    }

    public List<Stock> findStockEntities(int maxResults, int firstResult) {
        return findStockEntities(false, maxResults, firstResult);
    }

    private List<Stock> findStockEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stock.class));
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

    public Stock findStock(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Stock.class, id);
        } finally {
            em.close();
        }
    }

    public int getStockCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Stock> rt = cq.from(Stock.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
