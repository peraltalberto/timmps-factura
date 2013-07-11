/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Articulos;
import es.timmps.fac.persistencia.CliPedidosCab;
import es.timmps.fac.persistencia.CfTipoImpuesto;
import es.timmps.fac.persistencia.CliPedidosLin;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class CliPedidosLinJpaController implements Serializable {

    public CliPedidosLinJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CliPedidosLin cliPedidosLin) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos codProd = cliPedidosLin.getCodProd();
            if (codProd != null) {
                codProd = em.getReference(codProd.getClass(), codProd.getCodigo());
                cliPedidosLin.setCodProd(codProd);
            }
            CliPedidosCab codCab = cliPedidosLin.getCodCab();
            if (codCab != null) {
                codCab = em.getReference(codCab.getClass(), codCab.getId());
                cliPedidosLin.setCodCab(codCab);
            }
            CfTipoImpuesto tipoIva = cliPedidosLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva = em.getReference(tipoIva.getClass(), tipoIva.getId());
                cliPedidosLin.setTipoIva(tipoIva);
            }
            em.persist(cliPedidosLin);
            if (codProd != null) {
                codProd.getCliPedidosLinCollection().add(cliPedidosLin);
                codProd = em.merge(codProd);
            }
            if (codCab != null) {
                codCab.getCliPedidosLinCollection().add(cliPedidosLin);
                codCab = em.merge(codCab);
            }
            if (tipoIva != null) {
                tipoIva.getCliPedidosLinCollection().add(cliPedidosLin);
                tipoIva = em.merge(tipoIva);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CliPedidosLin cliPedidosLin) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliPedidosLin persistentCliPedidosLin = em.find(CliPedidosLin.class, cliPedidosLin.getId());
            Articulos codProdOld = persistentCliPedidosLin.getCodProd();
            Articulos codProdNew = cliPedidosLin.getCodProd();
            CliPedidosCab codCabOld = persistentCliPedidosLin.getCodCab();
            CliPedidosCab codCabNew = cliPedidosLin.getCodCab();
            CfTipoImpuesto tipoIvaOld = persistentCliPedidosLin.getTipoIva();
            CfTipoImpuesto tipoIvaNew = cliPedidosLin.getTipoIva();
            if (codProdNew != null) {
                codProdNew = em.getReference(codProdNew.getClass(), codProdNew.getCodigo());
                cliPedidosLin.setCodProd(codProdNew);
            }
            if (codCabNew != null) {
                codCabNew = em.getReference(codCabNew.getClass(), codCabNew.getId());
                cliPedidosLin.setCodCab(codCabNew);
            }
            if (tipoIvaNew != null) {
                tipoIvaNew = em.getReference(tipoIvaNew.getClass(), tipoIvaNew.getId());
                cliPedidosLin.setTipoIva(tipoIvaNew);
            }
            cliPedidosLin = em.merge(cliPedidosLin);
            if (codProdOld != null && !codProdOld.equals(codProdNew)) {
                codProdOld.getCliPedidosLinCollection().remove(cliPedidosLin);
                codProdOld = em.merge(codProdOld);
            }
            if (codProdNew != null && !codProdNew.equals(codProdOld)) {
                codProdNew.getCliPedidosLinCollection().add(cliPedidosLin);
                codProdNew = em.merge(codProdNew);
            }
            if (codCabOld != null && !codCabOld.equals(codCabNew)) {
                codCabOld.getCliPedidosLinCollection().remove(cliPedidosLin);
                codCabOld = em.merge(codCabOld);
            }
            if (codCabNew != null && !codCabNew.equals(codCabOld)) {
                codCabNew.getCliPedidosLinCollection().add(cliPedidosLin);
                codCabNew = em.merge(codCabNew);
            }
            if (tipoIvaOld != null && !tipoIvaOld.equals(tipoIvaNew)) {
                tipoIvaOld.getCliPedidosLinCollection().remove(cliPedidosLin);
                tipoIvaOld = em.merge(tipoIvaOld);
            }
            if (tipoIvaNew != null && !tipoIvaNew.equals(tipoIvaOld)) {
                tipoIvaNew.getCliPedidosLinCollection().add(cliPedidosLin);
                tipoIvaNew = em.merge(tipoIvaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliPedidosLin.getId();
                if (findCliPedidosLin(id) == null) {
                    throw new NonexistentEntityException("The cliPedidosLin with id " + id + " no longer exists.");
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
            CliPedidosLin cliPedidosLin;
            try {
                cliPedidosLin = em.getReference(CliPedidosLin.class, id);
                cliPedidosLin.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliPedidosLin with id " + id + " no longer exists.", enfe);
            }
            Articulos codProd = cliPedidosLin.getCodProd();
            if (codProd != null) {
                codProd.getCliPedidosLinCollection().remove(cliPedidosLin);
                codProd = em.merge(codProd);
            }
            CliPedidosCab codCab = cliPedidosLin.getCodCab();
            if (codCab != null) {
                codCab.getCliPedidosLinCollection().remove(cliPedidosLin);
                codCab = em.merge(codCab);
            }
            CfTipoImpuesto tipoIva = cliPedidosLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva.getCliPedidosLinCollection().remove(cliPedidosLin);
                tipoIva = em.merge(tipoIva);
            }
            em.remove(cliPedidosLin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CliPedidosLin> findCliPedidosLinEntities() {
        return findCliPedidosLinEntities(true, -1, -1);
    }

    public List<CliPedidosLin> findCliPedidosLinEntities(int maxResults, int firstResult) {
        return findCliPedidosLinEntities(false, maxResults, firstResult);
    }

    private List<CliPedidosLin> findCliPedidosLinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CliPedidosLin as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CliPedidosLin findCliPedidosLin(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CliPedidosLin.class, id);
        } finally {
            em.close();
        }
    }

    public int getCliPedidosLinCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CliPedidosLin as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
