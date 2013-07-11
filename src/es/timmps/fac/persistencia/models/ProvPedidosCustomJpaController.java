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
import es.timmps.fac.persistencia.pojos.ProvPedidosCab;
import es.timmps.fac.persistencia.pojos.CfProvPedCustomEmp;
import es.timmps.fac.persistencia.pojos.ProvPedidosCustom;
import es.timmps.fac.persistencia.pojos.ProvPedidosCustomPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class ProvPedidosCustomJpaController implements Serializable {

    public ProvPedidosCustomJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProvPedidosCustom provPedidosCustom) throws PreexistingEntityException, Exception {
        if (provPedidosCustom.getProvPedidosCustomPK() == null) {
            provPedidosCustom.setProvPedidosCustomPK(new ProvPedidosCustomPK());
        }
        provPedidosCustom.getProvPedidosCustomPK().setCodCampo(provPedidosCustom.getCfProvPedCustomEmp().getId());
        provPedidosCustom.getProvPedidosCustomPK().setCodCab(provPedidosCustom.getProvPedidosCab().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvPedidosCab provPedidosCab = provPedidosCustom.getProvPedidosCab();
            if (provPedidosCab != null) {
                provPedidosCab = em.getReference(provPedidosCab.getClass(), provPedidosCab.getId());
                provPedidosCustom.setProvPedidosCab(provPedidosCab);
            }
            CfProvPedCustomEmp cfProvPedCustomEmp = provPedidosCustom.getCfProvPedCustomEmp();
            if (cfProvPedCustomEmp != null) {
                cfProvPedCustomEmp = em.getReference(cfProvPedCustomEmp.getClass(), cfProvPedCustomEmp.getId());
                provPedidosCustom.setCfProvPedCustomEmp(cfProvPedCustomEmp);
            }
            em.persist(provPedidosCustom);
            if (provPedidosCab != null) {
                provPedidosCab.getProvPedidosCustomCollection().add(provPedidosCustom);
                provPedidosCab = em.merge(provPedidosCab);
            }
            if (cfProvPedCustomEmp != null) {
                cfProvPedCustomEmp.getProvPedidosCustomCollection().add(provPedidosCustom);
                cfProvPedCustomEmp = em.merge(cfProvPedCustomEmp);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProvPedidosCustom(provPedidosCustom.getProvPedidosCustomPK()) != null) {
                throw new PreexistingEntityException("ProvPedidosCustom " + provPedidosCustom + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProvPedidosCustom provPedidosCustom) throws NonexistentEntityException, Exception {
        provPedidosCustom.getProvPedidosCustomPK().setCodCampo(provPedidosCustom.getCfProvPedCustomEmp().getId());
        provPedidosCustom.getProvPedidosCustomPK().setCodCab(provPedidosCustom.getProvPedidosCab().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvPedidosCustom persistentProvPedidosCustom = em.find(ProvPedidosCustom.class, provPedidosCustom.getProvPedidosCustomPK());
            ProvPedidosCab provPedidosCabOld = persistentProvPedidosCustom.getProvPedidosCab();
            ProvPedidosCab provPedidosCabNew = provPedidosCustom.getProvPedidosCab();
            CfProvPedCustomEmp cfProvPedCustomEmpOld = persistentProvPedidosCustom.getCfProvPedCustomEmp();
            CfProvPedCustomEmp cfProvPedCustomEmpNew = provPedidosCustom.getCfProvPedCustomEmp();
            if (provPedidosCabNew != null) {
                provPedidosCabNew = em.getReference(provPedidosCabNew.getClass(), provPedidosCabNew.getId());
                provPedidosCustom.setProvPedidosCab(provPedidosCabNew);
            }
            if (cfProvPedCustomEmpNew != null) {
                cfProvPedCustomEmpNew = em.getReference(cfProvPedCustomEmpNew.getClass(), cfProvPedCustomEmpNew.getId());
                provPedidosCustom.setCfProvPedCustomEmp(cfProvPedCustomEmpNew);
            }
            provPedidosCustom = em.merge(provPedidosCustom);
            if (provPedidosCabOld != null && !provPedidosCabOld.equals(provPedidosCabNew)) {
                provPedidosCabOld.getProvPedidosCustomCollection().remove(provPedidosCustom);
                provPedidosCabOld = em.merge(provPedidosCabOld);
            }
            if (provPedidosCabNew != null && !provPedidosCabNew.equals(provPedidosCabOld)) {
                provPedidosCabNew.getProvPedidosCustomCollection().add(provPedidosCustom);
                provPedidosCabNew = em.merge(provPedidosCabNew);
            }
            if (cfProvPedCustomEmpOld != null && !cfProvPedCustomEmpOld.equals(cfProvPedCustomEmpNew)) {
                cfProvPedCustomEmpOld.getProvPedidosCustomCollection().remove(provPedidosCustom);
                cfProvPedCustomEmpOld = em.merge(cfProvPedCustomEmpOld);
            }
            if (cfProvPedCustomEmpNew != null && !cfProvPedCustomEmpNew.equals(cfProvPedCustomEmpOld)) {
                cfProvPedCustomEmpNew.getProvPedidosCustomCollection().add(provPedidosCustom);
                cfProvPedCustomEmpNew = em.merge(cfProvPedCustomEmpNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProvPedidosCustomPK id = provPedidosCustom.getProvPedidosCustomPK();
                if (findProvPedidosCustom(id) == null) {
                    throw new NonexistentEntityException("The provPedidosCustom with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProvPedidosCustomPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvPedidosCustom provPedidosCustom;
            try {
                provPedidosCustom = em.getReference(ProvPedidosCustom.class, id);
                provPedidosCustom.getProvPedidosCustomPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provPedidosCustom with id " + id + " no longer exists.", enfe);
            }
            ProvPedidosCab provPedidosCab = provPedidosCustom.getProvPedidosCab();
            if (provPedidosCab != null) {
                provPedidosCab.getProvPedidosCustomCollection().remove(provPedidosCustom);
                provPedidosCab = em.merge(provPedidosCab);
            }
            CfProvPedCustomEmp cfProvPedCustomEmp = provPedidosCustom.getCfProvPedCustomEmp();
            if (cfProvPedCustomEmp != null) {
                cfProvPedCustomEmp.getProvPedidosCustomCollection().remove(provPedidosCustom);
                cfProvPedCustomEmp = em.merge(cfProvPedCustomEmp);
            }
            em.remove(provPedidosCustom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProvPedidosCustom> findProvPedidosCustomEntities() {
        return findProvPedidosCustomEntities(true, -1, -1);
    }

    public List<ProvPedidosCustom> findProvPedidosCustomEntities(int maxResults, int firstResult) {
        return findProvPedidosCustomEntities(false, maxResults, firstResult);
    }

    private List<ProvPedidosCustom> findProvPedidosCustomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProvPedidosCustom.class));
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

    public ProvPedidosCustom findProvPedidosCustom(ProvPedidosCustomPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProvPedidosCustom.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvPedidosCustomCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProvPedidosCustom> rt = cq.from(ProvPedidosCustom.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
