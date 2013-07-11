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
import es.timmps.fac.persistencia.pojos.CfCliAlbCustomEmp;
import es.timmps.fac.persistencia.pojos.CliAlbCab;
import es.timmps.fac.persistencia.pojos.CliAlbCustom;
import es.timmps.fac.persistencia.pojos.CliAlbCustomPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class CliAlbCustomJpaController implements Serializable {

    public CliAlbCustomJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CliAlbCustom cliAlbCustom) throws PreexistingEntityException, Exception {
        if (cliAlbCustom.getCliAlbCustomPK() == null) {
            cliAlbCustom.setCliAlbCustomPK(new CliAlbCustomPK());
        }
        cliAlbCustom.getCliAlbCustomPK().setCodCampo(cliAlbCustom.getCfCliAlbCustomEmp().getId());
        cliAlbCustom.getCliAlbCustomPK().setCodCab(cliAlbCustom.getCliAlbCab().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfCliAlbCustomEmp cfCliAlbCustomEmp = cliAlbCustom.getCfCliAlbCustomEmp();
            if (cfCliAlbCustomEmp != null) {
                cfCliAlbCustomEmp = em.getReference(cfCliAlbCustomEmp.getClass(), cfCliAlbCustomEmp.getId());
                cliAlbCustom.setCfCliAlbCustomEmp(cfCliAlbCustomEmp);
            }
            CliAlbCab cliAlbCab = cliAlbCustom.getCliAlbCab();
            if (cliAlbCab != null) {
                cliAlbCab = em.getReference(cliAlbCab.getClass(), cliAlbCab.getId());
                cliAlbCustom.setCliAlbCab(cliAlbCab);
            }
            em.persist(cliAlbCustom);
            if (cfCliAlbCustomEmp != null) {
                cfCliAlbCustomEmp.getCliAlbCustomCollection().add(cliAlbCustom);
                cfCliAlbCustomEmp = em.merge(cfCliAlbCustomEmp);
            }
            if (cliAlbCab != null) {
                cliAlbCab.getCliAlbCustomCollection().add(cliAlbCustom);
                cliAlbCab = em.merge(cliAlbCab);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliAlbCustom(cliAlbCustom.getCliAlbCustomPK()) != null) {
                throw new PreexistingEntityException("CliAlbCustom " + cliAlbCustom + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CliAlbCustom cliAlbCustom) throws NonexistentEntityException, Exception {
        cliAlbCustom.getCliAlbCustomPK().setCodCampo(cliAlbCustom.getCfCliAlbCustomEmp().getId());
        cliAlbCustom.getCliAlbCustomPK().setCodCab(cliAlbCustom.getCliAlbCab().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliAlbCustom persistentCliAlbCustom = em.find(CliAlbCustom.class, cliAlbCustom.getCliAlbCustomPK());
            CfCliAlbCustomEmp cfCliAlbCustomEmpOld = persistentCliAlbCustom.getCfCliAlbCustomEmp();
            CfCliAlbCustomEmp cfCliAlbCustomEmpNew = cliAlbCustom.getCfCliAlbCustomEmp();
            CliAlbCab cliAlbCabOld = persistentCliAlbCustom.getCliAlbCab();
            CliAlbCab cliAlbCabNew = cliAlbCustom.getCliAlbCab();
            if (cfCliAlbCustomEmpNew != null) {
                cfCliAlbCustomEmpNew = em.getReference(cfCliAlbCustomEmpNew.getClass(), cfCliAlbCustomEmpNew.getId());
                cliAlbCustom.setCfCliAlbCustomEmp(cfCliAlbCustomEmpNew);
            }
            if (cliAlbCabNew != null) {
                cliAlbCabNew = em.getReference(cliAlbCabNew.getClass(), cliAlbCabNew.getId());
                cliAlbCustom.setCliAlbCab(cliAlbCabNew);
            }
            cliAlbCustom = em.merge(cliAlbCustom);
            if (cfCliAlbCustomEmpOld != null && !cfCliAlbCustomEmpOld.equals(cfCliAlbCustomEmpNew)) {
                cfCliAlbCustomEmpOld.getCliAlbCustomCollection().remove(cliAlbCustom);
                cfCliAlbCustomEmpOld = em.merge(cfCliAlbCustomEmpOld);
            }
            if (cfCliAlbCustomEmpNew != null && !cfCliAlbCustomEmpNew.equals(cfCliAlbCustomEmpOld)) {
                cfCliAlbCustomEmpNew.getCliAlbCustomCollection().add(cliAlbCustom);
                cfCliAlbCustomEmpNew = em.merge(cfCliAlbCustomEmpNew);
            }
            if (cliAlbCabOld != null && !cliAlbCabOld.equals(cliAlbCabNew)) {
                cliAlbCabOld.getCliAlbCustomCollection().remove(cliAlbCustom);
                cliAlbCabOld = em.merge(cliAlbCabOld);
            }
            if (cliAlbCabNew != null && !cliAlbCabNew.equals(cliAlbCabOld)) {
                cliAlbCabNew.getCliAlbCustomCollection().add(cliAlbCustom);
                cliAlbCabNew = em.merge(cliAlbCabNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CliAlbCustomPK id = cliAlbCustom.getCliAlbCustomPK();
                if (findCliAlbCustom(id) == null) {
                    throw new NonexistentEntityException("The cliAlbCustom with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CliAlbCustomPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliAlbCustom cliAlbCustom;
            try {
                cliAlbCustom = em.getReference(CliAlbCustom.class, id);
                cliAlbCustom.getCliAlbCustomPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliAlbCustom with id " + id + " no longer exists.", enfe);
            }
            CfCliAlbCustomEmp cfCliAlbCustomEmp = cliAlbCustom.getCfCliAlbCustomEmp();
            if (cfCliAlbCustomEmp != null) {
                cfCliAlbCustomEmp.getCliAlbCustomCollection().remove(cliAlbCustom);
                cfCliAlbCustomEmp = em.merge(cfCliAlbCustomEmp);
            }
            CliAlbCab cliAlbCab = cliAlbCustom.getCliAlbCab();
            if (cliAlbCab != null) {
                cliAlbCab.getCliAlbCustomCollection().remove(cliAlbCustom);
                cliAlbCab = em.merge(cliAlbCab);
            }
            em.remove(cliAlbCustom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CliAlbCustom> findCliAlbCustomEntities() {
        return findCliAlbCustomEntities(true, -1, -1);
    }

    public List<CliAlbCustom> findCliAlbCustomEntities(int maxResults, int firstResult) {
        return findCliAlbCustomEntities(false, maxResults, firstResult);
    }

    private List<CliAlbCustom> findCliAlbCustomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CliAlbCustom.class));
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

    public CliAlbCustom findCliAlbCustom(CliAlbCustomPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CliAlbCustom.class, id);
        } finally {
            em.close();
        }
    }

    public int getCliAlbCustomCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CliAlbCustom> rt = cq.from(CliAlbCustom.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
