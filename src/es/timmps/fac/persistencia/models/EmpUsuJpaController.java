/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.EmpUsu;
import es.timmps.fac.persistencia.EmpUsuPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class EmpUsuJpaController implements Serializable {

    public EmpUsuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpUsu empUsu) throws PreexistingEntityException, Exception {
        if (empUsu.getEmpUsuPK() == null) {
            empUsu.setEmpUsuPK(new EmpUsuPK());
        }
        empUsu.getEmpUsuPK().setCodEmp(empUsu.getEmpresas().getCodigo());
        empUsu.getEmpUsuPK().setUsu(empUsu.getUsuarios().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas empresas = empUsu.getEmpresas();
            if (empresas != null) {
                empresas = em.getReference(empresas.getClass(), empresas.getCodigo());
                empUsu.setEmpresas(empresas);
            }
            Usuarios usuarios = empUsu.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getId());
                empUsu.setUsuarios(usuarios);
            }
            em.persist(empUsu);
            if (empresas != null) {
                empresas.getEmpUsuCollection().add(empUsu);
                empresas = em.merge(empresas);
            }
            if (usuarios != null) {
                usuarios.getEmpUsuCollection().add(empUsu);
                usuarios = em.merge(usuarios);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpUsu(empUsu.getEmpUsuPK()) != null) {
                throw new PreexistingEntityException("EmpUsu " + empUsu + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpUsu empUsu) throws NonexistentEntityException, Exception {
        empUsu.getEmpUsuPK().setCodEmp(empUsu.getEmpresas().getCodigo());
        empUsu.getEmpUsuPK().setUsu(empUsu.getUsuarios().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpUsu persistentEmpUsu = em.find(EmpUsu.class, empUsu.getEmpUsuPK());
            Empresas empresasOld = persistentEmpUsu.getEmpresas();
            Empresas empresasNew = empUsu.getEmpresas();
            Usuarios usuariosOld = persistentEmpUsu.getUsuarios();
            Usuarios usuariosNew = empUsu.getUsuarios();
            if (empresasNew != null) {
                empresasNew = em.getReference(empresasNew.getClass(), empresasNew.getCodigo());
                empUsu.setEmpresas(empresasNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getId());
                empUsu.setUsuarios(usuariosNew);
            }
            empUsu = em.merge(empUsu);
            if (empresasOld != null && !empresasOld.equals(empresasNew)) {
                empresasOld.getEmpUsuCollection().remove(empUsu);
                empresasOld = em.merge(empresasOld);
            }
            if (empresasNew != null && !empresasNew.equals(empresasOld)) {
                empresasNew.getEmpUsuCollection().add(empUsu);
                empresasNew = em.merge(empresasNew);
            }
            if (usuariosOld != null && !usuariosOld.equals(usuariosNew)) {
                usuariosOld.getEmpUsuCollection().remove(empUsu);
                usuariosOld = em.merge(usuariosOld);
            }
            if (usuariosNew != null && !usuariosNew.equals(usuariosOld)) {
                usuariosNew.getEmpUsuCollection().add(empUsu);
                usuariosNew = em.merge(usuariosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EmpUsuPK id = empUsu.getEmpUsuPK();
                if (findEmpUsu(id) == null) {
                    throw new NonexistentEntityException("The empUsu with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmpUsuPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpUsu empUsu;
            try {
                empUsu = em.getReference(EmpUsu.class, id);
                empUsu.getEmpUsuPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empUsu with id " + id + " no longer exists.", enfe);
            }
            Empresas empresas = empUsu.getEmpresas();
            if (empresas != null) {
                empresas.getEmpUsuCollection().remove(empUsu);
                empresas = em.merge(empresas);
            }
            Usuarios usuarios = empUsu.getUsuarios();
            if (usuarios != null) {
                usuarios.getEmpUsuCollection().remove(empUsu);
                usuarios = em.merge(usuarios);
            }
            em.remove(empUsu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpUsu> findEmpUsuEntities() {
        return findEmpUsuEntities(true, -1, -1);
    }

    public List<EmpUsu> findEmpUsuEntities(int maxResults, int firstResult) {
        return findEmpUsuEntities(false, maxResults, firstResult);
    }

    private List<EmpUsu> findEmpUsuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EmpUsu as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EmpUsu findEmpUsu(EmpUsuPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpUsu.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpUsuCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from EmpUsu as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
