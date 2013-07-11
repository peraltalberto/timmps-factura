/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.CfCliPedCustomEmp;
import es.timmps.fac.persistencia.CliPedidosCab;
import es.timmps.fac.persistencia.CliPedidosCustom;
import es.timmps.fac.persistencia.CliPedidosCustomPK;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class CliPedidosCustomJpaController implements Serializable {

    public CliPedidosCustomJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CliPedidosCustom cliPedidosCustom) throws PreexistingEntityException, Exception {
        if (cliPedidosCustom.getCliPedidosCustomPK() == null) {
            cliPedidosCustom.setCliPedidosCustomPK(new CliPedidosCustomPK());
        }
        cliPedidosCustom.getCliPedidosCustomPK().setCodCab(cliPedidosCustom.getCliPedidosCab().getId());
        cliPedidosCustom.getCliPedidosCustomPK().setCodCampo(cliPedidosCustom.getCfCliPedCustomEmp().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfCliPedCustomEmp cfCliPedCustomEmp = cliPedidosCustom.getCfCliPedCustomEmp();
            if (cfCliPedCustomEmp != null) {
                cfCliPedCustomEmp = em.getReference(cfCliPedCustomEmp.getClass(), cfCliPedCustomEmp.getId());
                cliPedidosCustom.setCfCliPedCustomEmp(cfCliPedCustomEmp);
            }
            CliPedidosCab cliPedidosCab = cliPedidosCustom.getCliPedidosCab();
            if (cliPedidosCab != null) {
                cliPedidosCab = em.getReference(cliPedidosCab.getClass(), cliPedidosCab.getId());
                cliPedidosCustom.setCliPedidosCab(cliPedidosCab);
            }
            em.persist(cliPedidosCustom);
            if (cfCliPedCustomEmp != null) {
                cfCliPedCustomEmp.getCliPedidosCustomCollection().add(cliPedidosCustom);
                cfCliPedCustomEmp = em.merge(cfCliPedCustomEmp);
            }
            if (cliPedidosCab != null) {
                cliPedidosCab.getCliPedidosCustomCollection().add(cliPedidosCustom);
                cliPedidosCab = em.merge(cliPedidosCab);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliPedidosCustom(cliPedidosCustom.getCliPedidosCustomPK()) != null) {
                throw new PreexistingEntityException("CliPedidosCustom " + cliPedidosCustom + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CliPedidosCustom cliPedidosCustom) throws NonexistentEntityException, Exception {
        cliPedidosCustom.getCliPedidosCustomPK().setCodCab(cliPedidosCustom.getCliPedidosCab().getId());
        cliPedidosCustom.getCliPedidosCustomPK().setCodCampo(cliPedidosCustom.getCfCliPedCustomEmp().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliPedidosCustom persistentCliPedidosCustom = em.find(CliPedidosCustom.class, cliPedidosCustom.getCliPedidosCustomPK());
            CfCliPedCustomEmp cfCliPedCustomEmpOld = persistentCliPedidosCustom.getCfCliPedCustomEmp();
            CfCliPedCustomEmp cfCliPedCustomEmpNew = cliPedidosCustom.getCfCliPedCustomEmp();
            CliPedidosCab cliPedidosCabOld = persistentCliPedidosCustom.getCliPedidosCab();
            CliPedidosCab cliPedidosCabNew = cliPedidosCustom.getCliPedidosCab();
            if (cfCliPedCustomEmpNew != null) {
                cfCliPedCustomEmpNew = em.getReference(cfCliPedCustomEmpNew.getClass(), cfCliPedCustomEmpNew.getId());
                cliPedidosCustom.setCfCliPedCustomEmp(cfCliPedCustomEmpNew);
            }
            if (cliPedidosCabNew != null) {
                cliPedidosCabNew = em.getReference(cliPedidosCabNew.getClass(), cliPedidosCabNew.getId());
                cliPedidosCustom.setCliPedidosCab(cliPedidosCabNew);
            }
            cliPedidosCustom = em.merge(cliPedidosCustom);
            if (cfCliPedCustomEmpOld != null && !cfCliPedCustomEmpOld.equals(cfCliPedCustomEmpNew)) {
                cfCliPedCustomEmpOld.getCliPedidosCustomCollection().remove(cliPedidosCustom);
                cfCliPedCustomEmpOld = em.merge(cfCliPedCustomEmpOld);
            }
            if (cfCliPedCustomEmpNew != null && !cfCliPedCustomEmpNew.equals(cfCliPedCustomEmpOld)) {
                cfCliPedCustomEmpNew.getCliPedidosCustomCollection().add(cliPedidosCustom);
                cfCliPedCustomEmpNew = em.merge(cfCliPedCustomEmpNew);
            }
            if (cliPedidosCabOld != null && !cliPedidosCabOld.equals(cliPedidosCabNew)) {
                cliPedidosCabOld.getCliPedidosCustomCollection().remove(cliPedidosCustom);
                cliPedidosCabOld = em.merge(cliPedidosCabOld);
            }
            if (cliPedidosCabNew != null && !cliPedidosCabNew.equals(cliPedidosCabOld)) {
                cliPedidosCabNew.getCliPedidosCustomCollection().add(cliPedidosCustom);
                cliPedidosCabNew = em.merge(cliPedidosCabNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CliPedidosCustomPK id = cliPedidosCustom.getCliPedidosCustomPK();
                if (findCliPedidosCustom(id) == null) {
                    throw new NonexistentEntityException("The cliPedidosCustom with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CliPedidosCustomPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliPedidosCustom cliPedidosCustom;
            try {
                cliPedidosCustom = em.getReference(CliPedidosCustom.class, id);
                cliPedidosCustom.getCliPedidosCustomPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliPedidosCustom with id " + id + " no longer exists.", enfe);
            }
            CfCliPedCustomEmp cfCliPedCustomEmp = cliPedidosCustom.getCfCliPedCustomEmp();
            if (cfCliPedCustomEmp != null) {
                cfCliPedCustomEmp.getCliPedidosCustomCollection().remove(cliPedidosCustom);
                cfCliPedCustomEmp = em.merge(cfCliPedCustomEmp);
            }
            CliPedidosCab cliPedidosCab = cliPedidosCustom.getCliPedidosCab();
            if (cliPedidosCab != null) {
                cliPedidosCab.getCliPedidosCustomCollection().remove(cliPedidosCustom);
                cliPedidosCab = em.merge(cliPedidosCab);
            }
            em.remove(cliPedidosCustom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CliPedidosCustom> findCliPedidosCustomEntities() {
        return findCliPedidosCustomEntities(true, -1, -1);
    }

    public List<CliPedidosCustom> findCliPedidosCustomEntities(int maxResults, int firstResult) {
        return findCliPedidosCustomEntities(false, maxResults, firstResult);
    }

    private List<CliPedidosCustom> findCliPedidosCustomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CliPedidosCustom as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CliPedidosCustom findCliPedidosCustom(CliPedidosCustomPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CliPedidosCustom.class, id);
        } finally {
            em.close();
        }
    }

    public int getCliPedidosCustomCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CliPedidosCustom as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
