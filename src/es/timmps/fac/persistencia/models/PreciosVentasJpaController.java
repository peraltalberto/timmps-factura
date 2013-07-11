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
import es.timmps.fac.persistencia.pojos.Clientes;
import es.timmps.fac.persistencia.pojos.PreciosVentas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class PreciosVentasJpaController implements Serializable {

    public PreciosVentasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreciosVentas preciosVentas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = preciosVentas.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                preciosVentas.setCodEmp(codEmp);
            }
            Articulos codProducto = preciosVentas.getCodProducto();
            if (codProducto != null) {
                codProducto = em.getReference(codProducto.getClass(), codProducto.getCodigo());
                preciosVentas.setCodProducto(codProducto);
            }
            Clientes codCli = preciosVentas.getCodCli();
            if (codCli != null) {
                codCli = em.getReference(codCli.getClass(), codCli.getCodigo());
                preciosVentas.setCodCli(codCli);
            }
            em.persist(preciosVentas);
            if (codEmp != null) {
                codEmp.getPreciosVentasCollection().add(preciosVentas);
                codEmp = em.merge(codEmp);
            }
            if (codProducto != null) {
                codProducto.getPreciosVentasCollection().add(preciosVentas);
                codProducto = em.merge(codProducto);
            }
            if (codCli != null) {
                codCli.getPreciosVentasCollection().add(preciosVentas);
                codCli = em.merge(codCli);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreciosVentas preciosVentas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreciosVentas persistentPreciosVentas = em.find(PreciosVentas.class, preciosVentas.getId());
            Empresas codEmpOld = persistentPreciosVentas.getCodEmp();
            Empresas codEmpNew = preciosVentas.getCodEmp();
            Articulos codProductoOld = persistentPreciosVentas.getCodProducto();
            Articulos codProductoNew = preciosVentas.getCodProducto();
            Clientes codCliOld = persistentPreciosVentas.getCodCli();
            Clientes codCliNew = preciosVentas.getCodCli();
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                preciosVentas.setCodEmp(codEmpNew);
            }
            if (codProductoNew != null) {
                codProductoNew = em.getReference(codProductoNew.getClass(), codProductoNew.getCodigo());
                preciosVentas.setCodProducto(codProductoNew);
            }
            if (codCliNew != null) {
                codCliNew = em.getReference(codCliNew.getClass(), codCliNew.getCodigo());
                preciosVentas.setCodCli(codCliNew);
            }
            preciosVentas = em.merge(preciosVentas);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getPreciosVentasCollection().remove(preciosVentas);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getPreciosVentasCollection().add(preciosVentas);
                codEmpNew = em.merge(codEmpNew);
            }
            if (codProductoOld != null && !codProductoOld.equals(codProductoNew)) {
                codProductoOld.getPreciosVentasCollection().remove(preciosVentas);
                codProductoOld = em.merge(codProductoOld);
            }
            if (codProductoNew != null && !codProductoNew.equals(codProductoOld)) {
                codProductoNew.getPreciosVentasCollection().add(preciosVentas);
                codProductoNew = em.merge(codProductoNew);
            }
            if (codCliOld != null && !codCliOld.equals(codCliNew)) {
                codCliOld.getPreciosVentasCollection().remove(preciosVentas);
                codCliOld = em.merge(codCliOld);
            }
            if (codCliNew != null && !codCliNew.equals(codCliOld)) {
                codCliNew.getPreciosVentasCollection().add(preciosVentas);
                codCliNew = em.merge(codCliNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = preciosVentas.getId();
                if (findPreciosVentas(id) == null) {
                    throw new NonexistentEntityException("The preciosVentas with id " + id + " no longer exists.");
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
            PreciosVentas preciosVentas;
            try {
                preciosVentas = em.getReference(PreciosVentas.class, id);
                preciosVentas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preciosVentas with id " + id + " no longer exists.", enfe);
            }
            Empresas codEmp = preciosVentas.getCodEmp();
            if (codEmp != null) {
                codEmp.getPreciosVentasCollection().remove(preciosVentas);
                codEmp = em.merge(codEmp);
            }
            Articulos codProducto = preciosVentas.getCodProducto();
            if (codProducto != null) {
                codProducto.getPreciosVentasCollection().remove(preciosVentas);
                codProducto = em.merge(codProducto);
            }
            Clientes codCli = preciosVentas.getCodCli();
            if (codCli != null) {
                codCli.getPreciosVentasCollection().remove(preciosVentas);
                codCli = em.merge(codCli);
            }
            em.remove(preciosVentas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreciosVentas> findPreciosVentasEntities() {
        return findPreciosVentasEntities(true, -1, -1);
    }

    public List<PreciosVentas> findPreciosVentasEntities(int maxResults, int firstResult) {
        return findPreciosVentasEntities(false, maxResults, firstResult);
    }

    private List<PreciosVentas> findPreciosVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreciosVentas.class));
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

    public PreciosVentas findPreciosVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreciosVentas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreciosVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreciosVentas> rt = cq.from(PreciosVentas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
