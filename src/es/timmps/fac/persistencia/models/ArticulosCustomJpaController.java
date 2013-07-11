/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.Articulos;
import es.timmps.fac.persistencia.pojos.ArticulosCustom;
import es.timmps.fac.persistencia.pojos.ArticulosCustomPK;
import es.timmps.fac.persistencia.pojos.CfProdCustomEmp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class ArticulosCustomJpaController implements Serializable {

    public ArticulosCustomJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ArticulosCustom articulosCustom) throws PreexistingEntityException, Exception {
        if (articulosCustom.getArticulosCustomPK() == null) {
            articulosCustom.setArticulosCustomPK(new ArticulosCustomPK());
        }
        articulosCustom.getArticulosCustomPK().setCodCampo(articulosCustom.getCfProdCustomEmp().getId());
        articulosCustom.getArticulosCustomPK().setCodProd(articulosCustom.getArticulos().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos articulos = articulosCustom.getArticulos();
            if (articulos != null) {
                articulos = em.getReference(articulos.getClass(), articulos.getCodigo());
                articulosCustom.setArticulos(articulos);
            }
            CfProdCustomEmp cfProdCustomEmp = articulosCustom.getCfProdCustomEmp();
            if (cfProdCustomEmp != null) {
                cfProdCustomEmp = em.getReference(cfProdCustomEmp.getClass(), cfProdCustomEmp.getId());
                articulosCustom.setCfProdCustomEmp(cfProdCustomEmp);
            }
            em.persist(articulosCustom);
            if (articulos != null) {
                articulos.getArticulosCustomCollection().add(articulosCustom);
                articulos = em.merge(articulos);
            }
            if (cfProdCustomEmp != null) {
                cfProdCustomEmp.getArticulosCustomCollection().add(articulosCustom);
                cfProdCustomEmp = em.merge(cfProdCustomEmp);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArticulosCustom(articulosCustom.getArticulosCustomPK()) != null) {
                throw new PreexistingEntityException("ArticulosCustom " + articulosCustom + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ArticulosCustom articulosCustom) throws NonexistentEntityException, Exception {
        articulosCustom.getArticulosCustomPK().setCodCampo(articulosCustom.getCfProdCustomEmp().getId());
        articulosCustom.getArticulosCustomPK().setCodProd(articulosCustom.getArticulos().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArticulosCustom persistentArticulosCustom = em.find(ArticulosCustom.class, articulosCustom.getArticulosCustomPK());
            Articulos articulosOld = persistentArticulosCustom.getArticulos();
            Articulos articulosNew = articulosCustom.getArticulos();
            CfProdCustomEmp cfProdCustomEmpOld = persistentArticulosCustom.getCfProdCustomEmp();
            CfProdCustomEmp cfProdCustomEmpNew = articulosCustom.getCfProdCustomEmp();
            if (articulosNew != null) {
                articulosNew = em.getReference(articulosNew.getClass(), articulosNew.getCodigo());
                articulosCustom.setArticulos(articulosNew);
            }
            if (cfProdCustomEmpNew != null) {
                cfProdCustomEmpNew = em.getReference(cfProdCustomEmpNew.getClass(), cfProdCustomEmpNew.getId());
                articulosCustom.setCfProdCustomEmp(cfProdCustomEmpNew);
            }
            articulosCustom = em.merge(articulosCustom);
            if (articulosOld != null && !articulosOld.equals(articulosNew)) {
                articulosOld.getArticulosCustomCollection().remove(articulosCustom);
                articulosOld = em.merge(articulosOld);
            }
            if (articulosNew != null && !articulosNew.equals(articulosOld)) {
                articulosNew.getArticulosCustomCollection().add(articulosCustom);
                articulosNew = em.merge(articulosNew);
            }
            if (cfProdCustomEmpOld != null && !cfProdCustomEmpOld.equals(cfProdCustomEmpNew)) {
                cfProdCustomEmpOld.getArticulosCustomCollection().remove(articulosCustom);
                cfProdCustomEmpOld = em.merge(cfProdCustomEmpOld);
            }
            if (cfProdCustomEmpNew != null && !cfProdCustomEmpNew.equals(cfProdCustomEmpOld)) {
                cfProdCustomEmpNew.getArticulosCustomCollection().add(articulosCustom);
                cfProdCustomEmpNew = em.merge(cfProdCustomEmpNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ArticulosCustomPK id = articulosCustom.getArticulosCustomPK();
                if (findArticulosCustom(id) == null) {
                    throw new NonexistentEntityException("The articulosCustom with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ArticulosCustomPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArticulosCustom articulosCustom;
            try {
                articulosCustom = em.getReference(ArticulosCustom.class, id);
                articulosCustom.getArticulosCustomPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulosCustom with id " + id + " no longer exists.", enfe);
            }
            Articulos articulos = articulosCustom.getArticulos();
            if (articulos != null) {
                articulos.getArticulosCustomCollection().remove(articulosCustom);
                articulos = em.merge(articulos);
            }
            CfProdCustomEmp cfProdCustomEmp = articulosCustom.getCfProdCustomEmp();
            if (cfProdCustomEmp != null) {
                cfProdCustomEmp.getArticulosCustomCollection().remove(articulosCustom);
                cfProdCustomEmp = em.merge(cfProdCustomEmp);
            }
            em.remove(articulosCustom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ArticulosCustom> findArticulosCustomEntities() {
        return findArticulosCustomEntities(true, -1, -1);
    }

    public List<ArticulosCustom> findArticulosCustomEntities(int maxResults, int firstResult) {
        return findArticulosCustomEntities(false, maxResults, firstResult);
    }

    private List<ArticulosCustom> findArticulosCustomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArticulosCustom.class));
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

    public ArticulosCustom findArticulosCustom(ArticulosCustomPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ArticulosCustom.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticulosCustomCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArticulosCustom> rt = cq.from(ArticulosCustom.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
