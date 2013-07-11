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
import es.timmps.fac.persistencia.pojos.CliFacCab;
import es.timmps.fac.persistencia.pojos.Articulos;
import es.timmps.fac.persistencia.pojos.CfTipoImpuesto;
import es.timmps.fac.persistencia.pojos.CliFacLin;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class CliFacLinJpaController implements Serializable {

    public CliFacLinJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CliFacLin cliFacLin) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliFacCab codCab = cliFacLin.getCodCab();
            if (codCab != null) {
                codCab = em.getReference(codCab.getClass(), codCab.getId());
                cliFacLin.setCodCab(codCab);
            }
            Articulos codProd = cliFacLin.getCodProd();
            if (codProd != null) {
                codProd = em.getReference(codProd.getClass(), codProd.getCodigo());
                cliFacLin.setCodProd(codProd);
            }
            CfTipoImpuesto tipoIva = cliFacLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva = em.getReference(tipoIva.getClass(), tipoIva.getId());
                cliFacLin.setTipoIva(tipoIva);
            }
            em.persist(cliFacLin);
            if (codCab != null) {
                codCab.getCliFacLinCollection().add(cliFacLin);
                codCab = em.merge(codCab);
            }
            if (codProd != null) {
                codProd.getCliFacLinCollection().add(cliFacLin);
                codProd = em.merge(codProd);
            }
            if (tipoIva != null) {
                tipoIva.getCliFacLinCollection().add(cliFacLin);
                tipoIva = em.merge(tipoIva);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CliFacLin cliFacLin) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliFacLin persistentCliFacLin = em.find(CliFacLin.class, cliFacLin.getId());
            CliFacCab codCabOld = persistentCliFacLin.getCodCab();
            CliFacCab codCabNew = cliFacLin.getCodCab();
            Articulos codProdOld = persistentCliFacLin.getCodProd();
            Articulos codProdNew = cliFacLin.getCodProd();
            CfTipoImpuesto tipoIvaOld = persistentCliFacLin.getTipoIva();
            CfTipoImpuesto tipoIvaNew = cliFacLin.getTipoIva();
            if (codCabNew != null) {
                codCabNew = em.getReference(codCabNew.getClass(), codCabNew.getId());
                cliFacLin.setCodCab(codCabNew);
            }
            if (codProdNew != null) {
                codProdNew = em.getReference(codProdNew.getClass(), codProdNew.getCodigo());
                cliFacLin.setCodProd(codProdNew);
            }
            if (tipoIvaNew != null) {
                tipoIvaNew = em.getReference(tipoIvaNew.getClass(), tipoIvaNew.getId());
                cliFacLin.setTipoIva(tipoIvaNew);
            }
            cliFacLin = em.merge(cliFacLin);
            if (codCabOld != null && !codCabOld.equals(codCabNew)) {
                codCabOld.getCliFacLinCollection().remove(cliFacLin);
                codCabOld = em.merge(codCabOld);
            }
            if (codCabNew != null && !codCabNew.equals(codCabOld)) {
                codCabNew.getCliFacLinCollection().add(cliFacLin);
                codCabNew = em.merge(codCabNew);
            }
            if (codProdOld != null && !codProdOld.equals(codProdNew)) {
                codProdOld.getCliFacLinCollection().remove(cliFacLin);
                codProdOld = em.merge(codProdOld);
            }
            if (codProdNew != null && !codProdNew.equals(codProdOld)) {
                codProdNew.getCliFacLinCollection().add(cliFacLin);
                codProdNew = em.merge(codProdNew);
            }
            if (tipoIvaOld != null && !tipoIvaOld.equals(tipoIvaNew)) {
                tipoIvaOld.getCliFacLinCollection().remove(cliFacLin);
                tipoIvaOld = em.merge(tipoIvaOld);
            }
            if (tipoIvaNew != null && !tipoIvaNew.equals(tipoIvaOld)) {
                tipoIvaNew.getCliFacLinCollection().add(cliFacLin);
                tipoIvaNew = em.merge(tipoIvaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliFacLin.getId();
                if (findCliFacLin(id) == null) {
                    throw new NonexistentEntityException("The cliFacLin with id " + id + " no longer exists.");
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
            CliFacLin cliFacLin;
            try {
                cliFacLin = em.getReference(CliFacLin.class, id);
                cliFacLin.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliFacLin with id " + id + " no longer exists.", enfe);
            }
            CliFacCab codCab = cliFacLin.getCodCab();
            if (codCab != null) {
                codCab.getCliFacLinCollection().remove(cliFacLin);
                codCab = em.merge(codCab);
            }
            Articulos codProd = cliFacLin.getCodProd();
            if (codProd != null) {
                codProd.getCliFacLinCollection().remove(cliFacLin);
                codProd = em.merge(codProd);
            }
            CfTipoImpuesto tipoIva = cliFacLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva.getCliFacLinCollection().remove(cliFacLin);
                tipoIva = em.merge(tipoIva);
            }
            em.remove(cliFacLin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CliFacLin> findCliFacLinEntities() {
        return findCliFacLinEntities(true, -1, -1);
    }

    public List<CliFacLin> findCliFacLinEntities(int maxResults, int firstResult) {
        return findCliFacLinEntities(false, maxResults, firstResult);
    }

    private List<CliFacLin> findCliFacLinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CliFacLin.class));
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

    public CliFacLin findCliFacLin(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CliFacLin.class, id);
        } finally {
            em.close();
        }
    }

    public int getCliFacLinCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CliFacLin> rt = cq.from(CliFacLin.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
