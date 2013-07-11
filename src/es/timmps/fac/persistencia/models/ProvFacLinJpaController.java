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
import es.timmps.fac.persistencia.pojos.ProvFacCab;
import es.timmps.fac.persistencia.pojos.CfTipoImpuesto;
import es.timmps.fac.persistencia.pojos.ProvFacLin;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class ProvFacLinJpaController implements Serializable {

    public ProvFacLinJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProvFacLin provFacLin) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos codProd = provFacLin.getCodProd();
            if (codProd != null) {
                codProd = em.getReference(codProd.getClass(), codProd.getCodigo());
                provFacLin.setCodProd(codProd);
            }
            ProvFacCab codCab = provFacLin.getCodCab();
            if (codCab != null) {
                codCab = em.getReference(codCab.getClass(), codCab.getId());
                provFacLin.setCodCab(codCab);
            }
            CfTipoImpuesto tipoIva = provFacLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva = em.getReference(tipoIva.getClass(), tipoIva.getId());
                provFacLin.setTipoIva(tipoIva);
            }
            em.persist(provFacLin);
            if (codProd != null) {
                codProd.getProvFacLinCollection().add(provFacLin);
                codProd = em.merge(codProd);
            }
            if (codCab != null) {
                codCab.getProvFacLinCollection().add(provFacLin);
                codCab = em.merge(codCab);
            }
            if (tipoIva != null) {
                tipoIva.getProvFacLinCollection().add(provFacLin);
                tipoIva = em.merge(tipoIva);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProvFacLin provFacLin) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvFacLin persistentProvFacLin = em.find(ProvFacLin.class, provFacLin.getId());
            Articulos codProdOld = persistentProvFacLin.getCodProd();
            Articulos codProdNew = provFacLin.getCodProd();
            ProvFacCab codCabOld = persistentProvFacLin.getCodCab();
            ProvFacCab codCabNew = provFacLin.getCodCab();
            CfTipoImpuesto tipoIvaOld = persistentProvFacLin.getTipoIva();
            CfTipoImpuesto tipoIvaNew = provFacLin.getTipoIva();
            if (codProdNew != null) {
                codProdNew = em.getReference(codProdNew.getClass(), codProdNew.getCodigo());
                provFacLin.setCodProd(codProdNew);
            }
            if (codCabNew != null) {
                codCabNew = em.getReference(codCabNew.getClass(), codCabNew.getId());
                provFacLin.setCodCab(codCabNew);
            }
            if (tipoIvaNew != null) {
                tipoIvaNew = em.getReference(tipoIvaNew.getClass(), tipoIvaNew.getId());
                provFacLin.setTipoIva(tipoIvaNew);
            }
            provFacLin = em.merge(provFacLin);
            if (codProdOld != null && !codProdOld.equals(codProdNew)) {
                codProdOld.getProvFacLinCollection().remove(provFacLin);
                codProdOld = em.merge(codProdOld);
            }
            if (codProdNew != null && !codProdNew.equals(codProdOld)) {
                codProdNew.getProvFacLinCollection().add(provFacLin);
                codProdNew = em.merge(codProdNew);
            }
            if (codCabOld != null && !codCabOld.equals(codCabNew)) {
                codCabOld.getProvFacLinCollection().remove(provFacLin);
                codCabOld = em.merge(codCabOld);
            }
            if (codCabNew != null && !codCabNew.equals(codCabOld)) {
                codCabNew.getProvFacLinCollection().add(provFacLin);
                codCabNew = em.merge(codCabNew);
            }
            if (tipoIvaOld != null && !tipoIvaOld.equals(tipoIvaNew)) {
                tipoIvaOld.getProvFacLinCollection().remove(provFacLin);
                tipoIvaOld = em.merge(tipoIvaOld);
            }
            if (tipoIvaNew != null && !tipoIvaNew.equals(tipoIvaOld)) {
                tipoIvaNew.getProvFacLinCollection().add(provFacLin);
                tipoIvaNew = em.merge(tipoIvaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provFacLin.getId();
                if (findProvFacLin(id) == null) {
                    throw new NonexistentEntityException("The provFacLin with id " + id + " no longer exists.");
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
            ProvFacLin provFacLin;
            try {
                provFacLin = em.getReference(ProvFacLin.class, id);
                provFacLin.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provFacLin with id " + id + " no longer exists.", enfe);
            }
            Articulos codProd = provFacLin.getCodProd();
            if (codProd != null) {
                codProd.getProvFacLinCollection().remove(provFacLin);
                codProd = em.merge(codProd);
            }
            ProvFacCab codCab = provFacLin.getCodCab();
            if (codCab != null) {
                codCab.getProvFacLinCollection().remove(provFacLin);
                codCab = em.merge(codCab);
            }
            CfTipoImpuesto tipoIva = provFacLin.getTipoIva();
            if (tipoIva != null) {
                tipoIva.getProvFacLinCollection().remove(provFacLin);
                tipoIva = em.merge(tipoIva);
            }
            em.remove(provFacLin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProvFacLin> findProvFacLinEntities() {
        return findProvFacLinEntities(true, -1, -1);
    }

    public List<ProvFacLin> findProvFacLinEntities(int maxResults, int firstResult) {
        return findProvFacLinEntities(false, maxResults, firstResult);
    }

    private List<ProvFacLin> findProvFacLinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProvFacLin.class));
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

    public ProvFacLin findProvFacLin(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProvFacLin.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvFacLinCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProvFacLin> rt = cq.from(ProvFacLin.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
