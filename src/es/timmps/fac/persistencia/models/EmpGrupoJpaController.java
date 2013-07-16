/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.EmpGrupo;
import es.timmps.fac.persistencia.EmpGrupoPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Grupos;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class EmpGrupoJpaController implements Serializable {

    public EmpGrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpGrupo empGrupo) throws PreexistingEntityException, Exception {
        if (empGrupo.getEmpGrupoPK() == null) {
            empGrupo.setEmpGrupoPK(new EmpGrupoPK());
        }
        empGrupo.getEmpGrupoPK().setCodGrupo(empGrupo.getGrupos().getId());
        empGrupo.getEmpGrupoPK().setCodEmp(empGrupo.getEmpresas().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas empresas = empGrupo.getEmpresas();
            if (empresas != null) {
                empresas = em.getReference(empresas.getClass(), empresas.getCodigo());
                empGrupo.setEmpresas(empresas);
            }
            Grupos grupos = empGrupo.getGrupos();
            if (grupos != null) {
                grupos = em.getReference(grupos.getClass(), grupos.getId());
                empGrupo.setGrupos(grupos);
            }
            em.persist(empGrupo);
            if (empresas != null) {
                empresas.getEmpGrupoCollection().add(empGrupo);
                empresas = em.merge(empresas);
            }
            if (grupos != null) {
                grupos.getEmpGrupoCollection().add(empGrupo);
                grupos = em.merge(grupos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpGrupo(empGrupo.getEmpGrupoPK()) != null) {
                throw new PreexistingEntityException("EmpGrupo " + empGrupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpGrupo empGrupo) throws NonexistentEntityException, Exception {
        empGrupo.getEmpGrupoPK().setCodGrupo(empGrupo.getGrupos().getId());
        empGrupo.getEmpGrupoPK().setCodEmp(empGrupo.getEmpresas().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpGrupo persistentEmpGrupo = em.find(EmpGrupo.class, empGrupo.getEmpGrupoPK());
            Empresas empresasOld = persistentEmpGrupo.getEmpresas();
            Empresas empresasNew = empGrupo.getEmpresas();
            Grupos gruposOld = persistentEmpGrupo.getGrupos();
            Grupos gruposNew = empGrupo.getGrupos();
            if (empresasNew != null) {
                empresasNew = em.getReference(empresasNew.getClass(), empresasNew.getCodigo());
                empGrupo.setEmpresas(empresasNew);
            }
            if (gruposNew != null) {
                gruposNew = em.getReference(gruposNew.getClass(), gruposNew.getId());
                empGrupo.setGrupos(gruposNew);
            }
            empGrupo = em.merge(empGrupo);
            if (empresasOld != null && !empresasOld.equals(empresasNew)) {
                empresasOld.getEmpGrupoCollection().remove(empGrupo);
                empresasOld = em.merge(empresasOld);
            }
            if (empresasNew != null && !empresasNew.equals(empresasOld)) {
                empresasNew.getEmpGrupoCollection().add(empGrupo);
                empresasNew = em.merge(empresasNew);
            }
            if (gruposOld != null && !gruposOld.equals(gruposNew)) {
                gruposOld.getEmpGrupoCollection().remove(empGrupo);
                gruposOld = em.merge(gruposOld);
            }
            if (gruposNew != null && !gruposNew.equals(gruposOld)) {
                gruposNew.getEmpGrupoCollection().add(empGrupo);
                gruposNew = em.merge(gruposNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EmpGrupoPK id = empGrupo.getEmpGrupoPK();
                if (findEmpGrupo(id) == null) {
                    throw new NonexistentEntityException("The empGrupo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmpGrupoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpGrupo empGrupo;
            try {
                empGrupo = em.getReference(EmpGrupo.class, id);
                empGrupo.getEmpGrupoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empGrupo with id " + id + " no longer exists.", enfe);
            }
            Empresas empresas = empGrupo.getEmpresas();
            if (empresas != null) {
                empresas.getEmpGrupoCollection().remove(empGrupo);
                empresas = em.merge(empresas);
            }
            Grupos grupos = empGrupo.getGrupos();
            if (grupos != null) {
                grupos.getEmpGrupoCollection().remove(empGrupo);
                grupos = em.merge(grupos);
            }
            em.remove(empGrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpGrupo> findEmpGrupoEntities() {
        return findEmpGrupoEntities(true, -1, -1);
    }

    public List<EmpGrupo> findEmpGrupoEntities(int maxResults, int firstResult) {
        return findEmpGrupoEntities(false, maxResults, firstResult);
    }

    private List<EmpGrupo> findEmpGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EmpGrupo as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EmpGrupo findEmpGrupo(EmpGrupoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpGrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from EmpGrupo as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
