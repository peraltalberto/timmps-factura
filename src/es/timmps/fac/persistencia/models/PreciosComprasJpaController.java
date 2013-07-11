/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Articulos;
import es.timmps.fac.persistencia.PreciosCompras;
import es.timmps.fac.persistencia.Proveedores;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class PreciosComprasJpaController implements Serializable {

    public PreciosComprasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreciosCompras preciosCompras) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = preciosCompras.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                preciosCompras.setCodEmp(codEmp);
            }
            Articulos codProducto = preciosCompras.getCodProducto();
            if (codProducto != null) {
                codProducto = em.getReference(codProducto.getClass(), codProducto.getCodigo());
                preciosCompras.setCodProducto(codProducto);
            }
            Proveedores codProv = preciosCompras.getCodProv();
            if (codProv != null) {
                codProv = em.getReference(codProv.getClass(), codProv.getCodigo());
                preciosCompras.setCodProv(codProv);
            }
            em.persist(preciosCompras);
            if (codEmp != null) {
                codEmp.getPreciosComprasCollection().add(preciosCompras);
                codEmp = em.merge(codEmp);
            }
            if (codProducto != null) {
                codProducto.getPreciosComprasCollection().add(preciosCompras);
                codProducto = em.merge(codProducto);
            }
            if (codProv != null) {
                codProv.getPreciosComprasCollection().add(preciosCompras);
                codProv = em.merge(codProv);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreciosCompras preciosCompras) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreciosCompras persistentPreciosCompras = em.find(PreciosCompras.class, preciosCompras.getId());
            Empresas codEmpOld = persistentPreciosCompras.getCodEmp();
            Empresas codEmpNew = preciosCompras.getCodEmp();
            Articulos codProductoOld = persistentPreciosCompras.getCodProducto();
            Articulos codProductoNew = preciosCompras.getCodProducto();
            Proveedores codProvOld = persistentPreciosCompras.getCodProv();
            Proveedores codProvNew = preciosCompras.getCodProv();
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                preciosCompras.setCodEmp(codEmpNew);
            }
            if (codProductoNew != null) {
                codProductoNew = em.getReference(codProductoNew.getClass(), codProductoNew.getCodigo());
                preciosCompras.setCodProducto(codProductoNew);
            }
            if (codProvNew != null) {
                codProvNew = em.getReference(codProvNew.getClass(), codProvNew.getCodigo());
                preciosCompras.setCodProv(codProvNew);
            }
            preciosCompras = em.merge(preciosCompras);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getPreciosComprasCollection().remove(preciosCompras);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getPreciosComprasCollection().add(preciosCompras);
                codEmpNew = em.merge(codEmpNew);
            }
            if (codProductoOld != null && !codProductoOld.equals(codProductoNew)) {
                codProductoOld.getPreciosComprasCollection().remove(preciosCompras);
                codProductoOld = em.merge(codProductoOld);
            }
            if (codProductoNew != null && !codProductoNew.equals(codProductoOld)) {
                codProductoNew.getPreciosComprasCollection().add(preciosCompras);
                codProductoNew = em.merge(codProductoNew);
            }
            if (codProvOld != null && !codProvOld.equals(codProvNew)) {
                codProvOld.getPreciosComprasCollection().remove(preciosCompras);
                codProvOld = em.merge(codProvOld);
            }
            if (codProvNew != null && !codProvNew.equals(codProvOld)) {
                codProvNew.getPreciosComprasCollection().add(preciosCompras);
                codProvNew = em.merge(codProvNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = preciosCompras.getId();
                if (findPreciosCompras(id) == null) {
                    throw new NonexistentEntityException("The preciosCompras with id " + id + " no longer exists.");
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
            PreciosCompras preciosCompras;
            try {
                preciosCompras = em.getReference(PreciosCompras.class, id);
                preciosCompras.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preciosCompras with id " + id + " no longer exists.", enfe);
            }
            Empresas codEmp = preciosCompras.getCodEmp();
            if (codEmp != null) {
                codEmp.getPreciosComprasCollection().remove(preciosCompras);
                codEmp = em.merge(codEmp);
            }
            Articulos codProducto = preciosCompras.getCodProducto();
            if (codProducto != null) {
                codProducto.getPreciosComprasCollection().remove(preciosCompras);
                codProducto = em.merge(codProducto);
            }
            Proveedores codProv = preciosCompras.getCodProv();
            if (codProv != null) {
                codProv.getPreciosComprasCollection().remove(preciosCompras);
                codProv = em.merge(codProv);
            }
            em.remove(preciosCompras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreciosCompras> findPreciosComprasEntities() {
        return findPreciosComprasEntities(true, -1, -1);
    }

    public List<PreciosCompras> findPreciosComprasEntities(int maxResults, int firstResult) {
        return findPreciosComprasEntities(false, maxResults, firstResult);
    }

    private List<PreciosCompras> findPreciosComprasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PreciosCompras as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PreciosCompras findPreciosCompras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreciosCompras.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreciosComprasCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from PreciosCompras as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
