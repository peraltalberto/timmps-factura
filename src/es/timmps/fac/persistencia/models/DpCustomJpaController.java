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
import es.timmps.fac.persistencia.pojos.CfDpCustomEmp;
import es.timmps.fac.persistencia.pojos.DatosPersonales;
import es.timmps.fac.persistencia.pojos.DpCustom;
import es.timmps.fac.persistencia.pojos.DpCustomPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class DpCustomJpaController implements Serializable {

    public DpCustomJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DpCustom dpCustom) throws PreexistingEntityException, Exception {
        if (dpCustom.getDpCustomPK() == null) {
            dpCustom.setDpCustomPK(new DpCustomPK());
        }
        dpCustom.getDpCustomPK().setCampo(dpCustom.getCfDpCustomEmp().getId());
        dpCustom.getDpCustomPK().setCodigoPersona(dpCustom.getDatosPersonales().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfDpCustomEmp cfDpCustomEmp = dpCustom.getCfDpCustomEmp();
            if (cfDpCustomEmp != null) {
                cfDpCustomEmp = em.getReference(cfDpCustomEmp.getClass(), cfDpCustomEmp.getId());
                dpCustom.setCfDpCustomEmp(cfDpCustomEmp);
            }
            DatosPersonales datosPersonales = dpCustom.getDatosPersonales();
            if (datosPersonales != null) {
                datosPersonales = em.getReference(datosPersonales.getClass(), datosPersonales.getCodigo());
                dpCustom.setDatosPersonales(datosPersonales);
            }
            em.persist(dpCustom);
            if (cfDpCustomEmp != null) {
                cfDpCustomEmp.getDpCustomCollection().add(dpCustom);
                cfDpCustomEmp = em.merge(cfDpCustomEmp);
            }
            if (datosPersonales != null) {
                datosPersonales.getDpCustomCollection().add(dpCustom);
                datosPersonales = em.merge(datosPersonales);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDpCustom(dpCustom.getDpCustomPK()) != null) {
                throw new PreexistingEntityException("DpCustom " + dpCustom + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DpCustom dpCustom) throws NonexistentEntityException, Exception {
        dpCustom.getDpCustomPK().setCampo(dpCustom.getCfDpCustomEmp().getId());
        dpCustom.getDpCustomPK().setCodigoPersona(dpCustom.getDatosPersonales().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DpCustom persistentDpCustom = em.find(DpCustom.class, dpCustom.getDpCustomPK());
            CfDpCustomEmp cfDpCustomEmpOld = persistentDpCustom.getCfDpCustomEmp();
            CfDpCustomEmp cfDpCustomEmpNew = dpCustom.getCfDpCustomEmp();
            DatosPersonales datosPersonalesOld = persistentDpCustom.getDatosPersonales();
            DatosPersonales datosPersonalesNew = dpCustom.getDatosPersonales();
            if (cfDpCustomEmpNew != null) {
                cfDpCustomEmpNew = em.getReference(cfDpCustomEmpNew.getClass(), cfDpCustomEmpNew.getId());
                dpCustom.setCfDpCustomEmp(cfDpCustomEmpNew);
            }
            if (datosPersonalesNew != null) {
                datosPersonalesNew = em.getReference(datosPersonalesNew.getClass(), datosPersonalesNew.getCodigo());
                dpCustom.setDatosPersonales(datosPersonalesNew);
            }
            dpCustom = em.merge(dpCustom);
            if (cfDpCustomEmpOld != null && !cfDpCustomEmpOld.equals(cfDpCustomEmpNew)) {
                cfDpCustomEmpOld.getDpCustomCollection().remove(dpCustom);
                cfDpCustomEmpOld = em.merge(cfDpCustomEmpOld);
            }
            if (cfDpCustomEmpNew != null && !cfDpCustomEmpNew.equals(cfDpCustomEmpOld)) {
                cfDpCustomEmpNew.getDpCustomCollection().add(dpCustom);
                cfDpCustomEmpNew = em.merge(cfDpCustomEmpNew);
            }
            if (datosPersonalesOld != null && !datosPersonalesOld.equals(datosPersonalesNew)) {
                datosPersonalesOld.getDpCustomCollection().remove(dpCustom);
                datosPersonalesOld = em.merge(datosPersonalesOld);
            }
            if (datosPersonalesNew != null && !datosPersonalesNew.equals(datosPersonalesOld)) {
                datosPersonalesNew.getDpCustomCollection().add(dpCustom);
                datosPersonalesNew = em.merge(datosPersonalesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DpCustomPK id = dpCustom.getDpCustomPK();
                if (findDpCustom(id) == null) {
                    throw new NonexistentEntityException("The dpCustom with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DpCustomPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DpCustom dpCustom;
            try {
                dpCustom = em.getReference(DpCustom.class, id);
                dpCustom.getDpCustomPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dpCustom with id " + id + " no longer exists.", enfe);
            }
            CfDpCustomEmp cfDpCustomEmp = dpCustom.getCfDpCustomEmp();
            if (cfDpCustomEmp != null) {
                cfDpCustomEmp.getDpCustomCollection().remove(dpCustom);
                cfDpCustomEmp = em.merge(cfDpCustomEmp);
            }
            DatosPersonales datosPersonales = dpCustom.getDatosPersonales();
            if (datosPersonales != null) {
                datosPersonales.getDpCustomCollection().remove(dpCustom);
                datosPersonales = em.merge(datosPersonales);
            }
            em.remove(dpCustom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DpCustom> findDpCustomEntities() {
        return findDpCustomEntities(true, -1, -1);
    }

    public List<DpCustom> findDpCustomEntities(int maxResults, int firstResult) {
        return findDpCustomEntities(false, maxResults, firstResult);
    }

    private List<DpCustom> findDpCustomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DpCustom.class));
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

    public DpCustom findDpCustom(DpCustomPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DpCustom.class, id);
        } finally {
            em.close();
        }
    }

    public int getDpCustomCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DpCustom> rt = cq.from(DpCustom.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
