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
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.Articulos;
import es.timmps.fac.persistencia.pojos.Promociones;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class PromocionesJpaController implements Serializable {

    public PromocionesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promociones promociones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = promociones.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                promociones.setCodEmp(codEmp);
            }
            Articulos codProd = promociones.getCodProd();
            if (codProd != null) {
                codProd = em.getReference(codProd.getClass(), codProd.getCodigo());
                promociones.setCodProd(codProd);
            }
            em.persist(promociones);
            if (codEmp != null) {
                codEmp.getPromocionesCollection().add(promociones);
                codEmp = em.merge(codEmp);
            }
            if (codProd != null) {
                codProd.getPromocionesCollection().add(promociones);
                codProd = em.merge(codProd);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promociones promociones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promociones persistentPromociones = em.find(Promociones.class, promociones.getId());
            Empresas codEmpOld = persistentPromociones.getCodEmp();
            Empresas codEmpNew = promociones.getCodEmp();
            Articulos codProdOld = persistentPromociones.getCodProd();
            Articulos codProdNew = promociones.getCodProd();
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                promociones.setCodEmp(codEmpNew);
            }
            if (codProdNew != null) {
                codProdNew = em.getReference(codProdNew.getClass(), codProdNew.getCodigo());
                promociones.setCodProd(codProdNew);
            }
            promociones = em.merge(promociones);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getPromocionesCollection().remove(promociones);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getPromocionesCollection().add(promociones);
                codEmpNew = em.merge(codEmpNew);
            }
            if (codProdOld != null && !codProdOld.equals(codProdNew)) {
                codProdOld.getPromocionesCollection().remove(promociones);
                codProdOld = em.merge(codProdOld);
            }
            if (codProdNew != null && !codProdNew.equals(codProdOld)) {
                codProdNew.getPromocionesCollection().add(promociones);
                codProdNew = em.merge(codProdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promociones.getId();
                if (findPromociones(id) == null) {
                    throw new NonexistentEntityException("The promociones with id " + id + " no longer exists.");
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
            Promociones promociones;
            try {
                promociones = em.getReference(Promociones.class, id);
                promociones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promociones with id " + id + " no longer exists.", enfe);
            }
            Empresas codEmp = promociones.getCodEmp();
            if (codEmp != null) {
                codEmp.getPromocionesCollection().remove(promociones);
                codEmp = em.merge(codEmp);
            }
            Articulos codProd = promociones.getCodProd();
            if (codProd != null) {
                codProd.getPromocionesCollection().remove(promociones);
                codProd = em.merge(codProd);
            }
            em.remove(promociones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promociones> findPromocionesEntities() {
        return findPromocionesEntities(true, -1, -1);
    }

    public List<Promociones> findPromocionesEntities(int maxResults, int firstResult) {
        return findPromocionesEntities(false, maxResults, firstResult);
    }

    private List<Promociones> findPromocionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promociones.class));
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

    public Promociones findPromociones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promociones.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promociones> rt = cq.from(Promociones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
