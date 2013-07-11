/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Articulos;
import es.timmps.fac.persistencia.ProvPedidosCab;
import es.timmps.fac.persistencia.CfTipoImpuesto;
import es.timmps.fac.persistencia.ProvPedidosLin;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class ProvPedidosLinJpaController implements Serializable {

    public ProvPedidosLinJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProvPedidosLin provPedidosLin) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos codProd = provPedidosLin.getCodProd();
            if (codProd != null) {
                codProd = em.getReference(codProd.getClass(), codProd.getCodigo());
                provPedidosLin.setCodProd(codProd);
            }
            ProvPedidosCab codCab = provPedidosLin.getCodCab();
            if (codCab != null) {
                codCab = em.getReference(codCab.getClass(), codCab.getId());
                provPedidosLin.setCodCab(codCab);
            }
            CfTipoImpuesto tipoIva = provPedidosLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva = em.getReference(tipoIva.getClass(), tipoIva.getId());
                provPedidosLin.setTipoIva(tipoIva);
            }
            em.persist(provPedidosLin);
            if (codProd != null) {
                codProd.getProvPedidosLinCollection().add(provPedidosLin);
                codProd = em.merge(codProd);
            }
            if (codCab != null) {
                codCab.getProvPedidosLinCollection().add(provPedidosLin);
                codCab = em.merge(codCab);
            }
            if (tipoIva != null) {
                tipoIva.getProvPedidosLinCollection().add(provPedidosLin);
                tipoIva = em.merge(tipoIva);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProvPedidosLin provPedidosLin) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvPedidosLin persistentProvPedidosLin = em.find(ProvPedidosLin.class, provPedidosLin.getId());
            Articulos codProdOld = persistentProvPedidosLin.getCodProd();
            Articulos codProdNew = provPedidosLin.getCodProd();
            ProvPedidosCab codCabOld = persistentProvPedidosLin.getCodCab();
            ProvPedidosCab codCabNew = provPedidosLin.getCodCab();
            CfTipoImpuesto tipoIvaOld = persistentProvPedidosLin.getTipoIva();
            CfTipoImpuesto tipoIvaNew = provPedidosLin.getTipoIva();
            if (codProdNew != null) {
                codProdNew = em.getReference(codProdNew.getClass(), codProdNew.getCodigo());
                provPedidosLin.setCodProd(codProdNew);
            }
            if (codCabNew != null) {
                codCabNew = em.getReference(codCabNew.getClass(), codCabNew.getId());
                provPedidosLin.setCodCab(codCabNew);
            }
            if (tipoIvaNew != null) {
                tipoIvaNew = em.getReference(tipoIvaNew.getClass(), tipoIvaNew.getId());
                provPedidosLin.setTipoIva(tipoIvaNew);
            }
            provPedidosLin = em.merge(provPedidosLin);
            if (codProdOld != null && !codProdOld.equals(codProdNew)) {
                codProdOld.getProvPedidosLinCollection().remove(provPedidosLin);
                codProdOld = em.merge(codProdOld);
            }
            if (codProdNew != null && !codProdNew.equals(codProdOld)) {
                codProdNew.getProvPedidosLinCollection().add(provPedidosLin);
                codProdNew = em.merge(codProdNew);
            }
            if (codCabOld != null && !codCabOld.equals(codCabNew)) {
                codCabOld.getProvPedidosLinCollection().remove(provPedidosLin);
                codCabOld = em.merge(codCabOld);
            }
            if (codCabNew != null && !codCabNew.equals(codCabOld)) {
                codCabNew.getProvPedidosLinCollection().add(provPedidosLin);
                codCabNew = em.merge(codCabNew);
            }
            if (tipoIvaOld != null && !tipoIvaOld.equals(tipoIvaNew)) {
                tipoIvaOld.getProvPedidosLinCollection().remove(provPedidosLin);
                tipoIvaOld = em.merge(tipoIvaOld);
            }
            if (tipoIvaNew != null && !tipoIvaNew.equals(tipoIvaOld)) {
                tipoIvaNew.getProvPedidosLinCollection().add(provPedidosLin);
                tipoIvaNew = em.merge(tipoIvaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provPedidosLin.getId();
                if (findProvPedidosLin(id) == null) {
                    throw new NonexistentEntityException("The provPedidosLin with id " + id + " no longer exists.");
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
            ProvPedidosLin provPedidosLin;
            try {
                provPedidosLin = em.getReference(ProvPedidosLin.class, id);
                provPedidosLin.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provPedidosLin with id " + id + " no longer exists.", enfe);
            }
            Articulos codProd = provPedidosLin.getCodProd();
            if (codProd != null) {
                codProd.getProvPedidosLinCollection().remove(provPedidosLin);
                codProd = em.merge(codProd);
            }
            ProvPedidosCab codCab = provPedidosLin.getCodCab();
            if (codCab != null) {
                codCab.getProvPedidosLinCollection().remove(provPedidosLin);
                codCab = em.merge(codCab);
            }
            CfTipoImpuesto tipoIva = provPedidosLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva.getProvPedidosLinCollection().remove(provPedidosLin);
                tipoIva = em.merge(tipoIva);
            }
            em.remove(provPedidosLin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProvPedidosLin> findProvPedidosLinEntities() {
        return findProvPedidosLinEntities(true, -1, -1);
    }

    public List<ProvPedidosLin> findProvPedidosLinEntities(int maxResults, int firstResult) {
        return findProvPedidosLinEntities(false, maxResults, firstResult);
    }

    private List<ProvPedidosLin> findProvPedidosLinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ProvPedidosLin as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ProvPedidosLin findProvPedidosLin(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProvPedidosLin.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvPedidosLinCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from ProvPedidosLin as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
