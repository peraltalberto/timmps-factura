/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.Almacenes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Stock;
import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class AlmacenesJpaController implements Serializable {

    public AlmacenesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Almacenes almacenes) throws PreexistingEntityException, Exception {
        if (almacenes.getStockCollection() == null) {
            almacenes.setStockCollection(new ArrayList<Stock>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = almacenes.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                almacenes.setCodEmp(codEmp);
            }
            Collection<Stock> attachedStockCollection = new ArrayList<Stock>();
            for (Stock stockCollectionStockToAttach : almacenes.getStockCollection()) {
                stockCollectionStockToAttach = em.getReference(stockCollectionStockToAttach.getClass(), stockCollectionStockToAttach.getId());
                attachedStockCollection.add(stockCollectionStockToAttach);
            }
            almacenes.setStockCollection(attachedStockCollection);
            em.persist(almacenes);
            if (codEmp != null) {
                codEmp.getAlmacenesCollection().add(almacenes);
                codEmp = em.merge(codEmp);
            }
            for (Stock stockCollectionStock : almacenes.getStockCollection()) {
                Almacenes oldCodAlmacenOfStockCollectionStock = stockCollectionStock.getCodAlmacen();
                stockCollectionStock.setCodAlmacen(almacenes);
                stockCollectionStock = em.merge(stockCollectionStock);
                if (oldCodAlmacenOfStockCollectionStock != null) {
                    oldCodAlmacenOfStockCollectionStock.getStockCollection().remove(stockCollectionStock);
                    oldCodAlmacenOfStockCollectionStock = em.merge(oldCodAlmacenOfStockCollectionStock);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlmacenes(almacenes.getCodigo()) != null) {
                throw new PreexistingEntityException("Almacenes " + almacenes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Almacenes almacenes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Almacenes persistentAlmacenes = em.find(Almacenes.class, almacenes.getCodigo());
            Empresas codEmpOld = persistentAlmacenes.getCodEmp();
            Empresas codEmpNew = almacenes.getCodEmp();
            Collection<Stock> stockCollectionOld = persistentAlmacenes.getStockCollection();
            Collection<Stock> stockCollectionNew = almacenes.getStockCollection();
            List<String> illegalOrphanMessages = null;
            for (Stock stockCollectionOldStock : stockCollectionOld) {
                if (!stockCollectionNew.contains(stockCollectionOldStock)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Stock " + stockCollectionOldStock + " since its codAlmacen field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                almacenes.setCodEmp(codEmpNew);
            }
            Collection<Stock> attachedStockCollectionNew = new ArrayList<Stock>();
            for (Stock stockCollectionNewStockToAttach : stockCollectionNew) {
                stockCollectionNewStockToAttach = em.getReference(stockCollectionNewStockToAttach.getClass(), stockCollectionNewStockToAttach.getId());
                attachedStockCollectionNew.add(stockCollectionNewStockToAttach);
            }
            stockCollectionNew = attachedStockCollectionNew;
            almacenes.setStockCollection(stockCollectionNew);
            almacenes = em.merge(almacenes);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getAlmacenesCollection().remove(almacenes);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getAlmacenesCollection().add(almacenes);
                codEmpNew = em.merge(codEmpNew);
            }
            for (Stock stockCollectionNewStock : stockCollectionNew) {
                if (!stockCollectionOld.contains(stockCollectionNewStock)) {
                    Almacenes oldCodAlmacenOfStockCollectionNewStock = stockCollectionNewStock.getCodAlmacen();
                    stockCollectionNewStock.setCodAlmacen(almacenes);
                    stockCollectionNewStock = em.merge(stockCollectionNewStock);
                    if (oldCodAlmacenOfStockCollectionNewStock != null && !oldCodAlmacenOfStockCollectionNewStock.equals(almacenes)) {
                        oldCodAlmacenOfStockCollectionNewStock.getStockCollection().remove(stockCollectionNewStock);
                        oldCodAlmacenOfStockCollectionNewStock = em.merge(oldCodAlmacenOfStockCollectionNewStock);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = almacenes.getCodigo();
                if (findAlmacenes(id) == null) {
                    throw new NonexistentEntityException("The almacenes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Almacenes almacenes;
            try {
                almacenes = em.getReference(Almacenes.class, id);
                almacenes.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The almacenes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Stock> stockCollectionOrphanCheck = almacenes.getStockCollection();
            for (Stock stockCollectionOrphanCheckStock : stockCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Almacenes (" + almacenes + ") cannot be destroyed since the Stock " + stockCollectionOrphanCheckStock + " in its stockCollection field has a non-nullable codAlmacen field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresas codEmp = almacenes.getCodEmp();
            if (codEmp != null) {
                codEmp.getAlmacenesCollection().remove(almacenes);
                codEmp = em.merge(codEmp);
            }
            em.remove(almacenes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Almacenes> findAlmacenesEntities() {
        return findAlmacenesEntities(true, -1, -1);
    }

    public List<Almacenes> findAlmacenesEntities(int maxResults, int firstResult) {
        return findAlmacenesEntities(false, maxResults, firstResult);
    }

    private List<Almacenes> findAlmacenesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Almacenes as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Almacenes findAlmacenes(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Almacenes.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlmacenesCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Almacenes as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
