/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.CfCliFacCustomEmp;
import es.timmps.fac.persistencia.CliFacCab;
import es.timmps.fac.persistencia.CliFacCustom;
import es.timmps.fac.persistencia.CliFacCustomPK;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class CliFacCustomJpaController implements Serializable {

    public CliFacCustomJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CliFacCustom cliFacCustom) throws PreexistingEntityException, Exception {
        if (cliFacCustom.getCliFacCustomPK() == null) {
            cliFacCustom.setCliFacCustomPK(new CliFacCustomPK());
        }
        cliFacCustom.getCliFacCustomPK().setCodCampo(cliFacCustom.getCfCliFacCustomEmp().getId());
        cliFacCustom.getCliFacCustomPK().setCodCab(cliFacCustom.getCliFacCab().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfCliFacCustomEmp cfCliFacCustomEmp = cliFacCustom.getCfCliFacCustomEmp();
            if (cfCliFacCustomEmp != null) {
                cfCliFacCustomEmp = em.getReference(cfCliFacCustomEmp.getClass(), cfCliFacCustomEmp.getId());
                cliFacCustom.setCfCliFacCustomEmp(cfCliFacCustomEmp);
            }
            CliFacCab cliFacCab = cliFacCustom.getCliFacCab();
            if (cliFacCab != null) {
                cliFacCab = em.getReference(cliFacCab.getClass(), cliFacCab.getId());
                cliFacCustom.setCliFacCab(cliFacCab);
            }
            em.persist(cliFacCustom);
            if (cfCliFacCustomEmp != null) {
                cfCliFacCustomEmp.getCliFacCustomCollection().add(cliFacCustom);
                cfCliFacCustomEmp = em.merge(cfCliFacCustomEmp);
            }
            if (cliFacCab != null) {
                cliFacCab.getCliFacCustomCollection().add(cliFacCustom);
                cliFacCab = em.merge(cliFacCab);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliFacCustom(cliFacCustom.getCliFacCustomPK()) != null) {
                throw new PreexistingEntityException("CliFacCustom " + cliFacCustom + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CliFacCustom cliFacCustom) throws NonexistentEntityException, Exception {
        cliFacCustom.getCliFacCustomPK().setCodCampo(cliFacCustom.getCfCliFacCustomEmp().getId());
        cliFacCustom.getCliFacCustomPK().setCodCab(cliFacCustom.getCliFacCab().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliFacCustom persistentCliFacCustom = em.find(CliFacCustom.class, cliFacCustom.getCliFacCustomPK());
            CfCliFacCustomEmp cfCliFacCustomEmpOld = persistentCliFacCustom.getCfCliFacCustomEmp();
            CfCliFacCustomEmp cfCliFacCustomEmpNew = cliFacCustom.getCfCliFacCustomEmp();
            CliFacCab cliFacCabOld = persistentCliFacCustom.getCliFacCab();
            CliFacCab cliFacCabNew = cliFacCustom.getCliFacCab();
            if (cfCliFacCustomEmpNew != null) {
                cfCliFacCustomEmpNew = em.getReference(cfCliFacCustomEmpNew.getClass(), cfCliFacCustomEmpNew.getId());
                cliFacCustom.setCfCliFacCustomEmp(cfCliFacCustomEmpNew);
            }
            if (cliFacCabNew != null) {
                cliFacCabNew = em.getReference(cliFacCabNew.getClass(), cliFacCabNew.getId());
                cliFacCustom.setCliFacCab(cliFacCabNew);
            }
            cliFacCustom = em.merge(cliFacCustom);
            if (cfCliFacCustomEmpOld != null && !cfCliFacCustomEmpOld.equals(cfCliFacCustomEmpNew)) {
                cfCliFacCustomEmpOld.getCliFacCustomCollection().remove(cliFacCustom);
                cfCliFacCustomEmpOld = em.merge(cfCliFacCustomEmpOld);
            }
            if (cfCliFacCustomEmpNew != null && !cfCliFacCustomEmpNew.equals(cfCliFacCustomEmpOld)) {
                cfCliFacCustomEmpNew.getCliFacCustomCollection().add(cliFacCustom);
                cfCliFacCustomEmpNew = em.merge(cfCliFacCustomEmpNew);
            }
            if (cliFacCabOld != null && !cliFacCabOld.equals(cliFacCabNew)) {
                cliFacCabOld.getCliFacCustomCollection().remove(cliFacCustom);
                cliFacCabOld = em.merge(cliFacCabOld);
            }
            if (cliFacCabNew != null && !cliFacCabNew.equals(cliFacCabOld)) {
                cliFacCabNew.getCliFacCustomCollection().add(cliFacCustom);
                cliFacCabNew = em.merge(cliFacCabNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CliFacCustomPK id = cliFacCustom.getCliFacCustomPK();
                if (findCliFacCustom(id) == null) {
                    throw new NonexistentEntityException("The cliFacCustom with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CliFacCustomPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliFacCustom cliFacCustom;
            try {
                cliFacCustom = em.getReference(CliFacCustom.class, id);
                cliFacCustom.getCliFacCustomPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliFacCustom with id " + id + " no longer exists.", enfe);
            }
            CfCliFacCustomEmp cfCliFacCustomEmp = cliFacCustom.getCfCliFacCustomEmp();
            if (cfCliFacCustomEmp != null) {
                cfCliFacCustomEmp.getCliFacCustomCollection().remove(cliFacCustom);
                cfCliFacCustomEmp = em.merge(cfCliFacCustomEmp);
            }
            CliFacCab cliFacCab = cliFacCustom.getCliFacCab();
            if (cliFacCab != null) {
                cliFacCab.getCliFacCustomCollection().remove(cliFacCustom);
                cliFacCab = em.merge(cliFacCab);
            }
            em.remove(cliFacCustom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CliFacCustom> findCliFacCustomEntities() {
        return findCliFacCustomEntities(true, -1, -1);
    }

    public List<CliFacCustom> findCliFacCustomEntities(int maxResults, int firstResult) {
        return findCliFacCustomEntities(false, maxResults, firstResult);
    }

    private List<CliFacCustom> findCliFacCustomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CliFacCustom as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CliFacCustom findCliFacCustom(CliFacCustomPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CliFacCustom.class, id);
        } finally {
            em.close();
        }
    }

    public int getCliFacCustomCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CliFacCustom as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
