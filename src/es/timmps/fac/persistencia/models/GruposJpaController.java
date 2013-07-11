/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Usuarios;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Grupos;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class GruposJpaController implements Serializable {

    public GruposJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupos grupos) {
        if (grupos.getUsuariosCollection() == null) {
            grupos.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        if (grupos.getEmpresasCollection() == null) {
            grupos.setEmpresasCollection(new ArrayList<Empresas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : grupos.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            grupos.setUsuariosCollection(attachedUsuariosCollection);
            Collection<Empresas> attachedEmpresasCollection = new ArrayList<Empresas>();
            for (Empresas empresasCollectionEmpresasToAttach : grupos.getEmpresasCollection()) {
                empresasCollectionEmpresasToAttach = em.getReference(empresasCollectionEmpresasToAttach.getClass(), empresasCollectionEmpresasToAttach.getCodigo());
                attachedEmpresasCollection.add(empresasCollectionEmpresasToAttach);
            }
            grupos.setEmpresasCollection(attachedEmpresasCollection);
            em.persist(grupos);
            for (Usuarios usuariosCollectionUsuarios : grupos.getUsuariosCollection()) {
                usuariosCollectionUsuarios.getGruposCollection().add(grupos);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            for (Empresas empresasCollectionEmpresas : grupos.getEmpresasCollection()) {
                empresasCollectionEmpresas.getGruposCollection().add(grupos);
                empresasCollectionEmpresas = em.merge(empresasCollectionEmpresas);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupos grupos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupos persistentGrupos = em.find(Grupos.class, grupos.getId());
            Collection<Usuarios> usuariosCollectionOld = persistentGrupos.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = grupos.getUsuariosCollection();
            Collection<Empresas> empresasCollectionOld = persistentGrupos.getEmpresasCollection();
            Collection<Empresas> empresasCollectionNew = grupos.getEmpresasCollection();
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            grupos.setUsuariosCollection(usuariosCollectionNew);
            Collection<Empresas> attachedEmpresasCollectionNew = new ArrayList<Empresas>();
            for (Empresas empresasCollectionNewEmpresasToAttach : empresasCollectionNew) {
                empresasCollectionNewEmpresasToAttach = em.getReference(empresasCollectionNewEmpresasToAttach.getClass(), empresasCollectionNewEmpresasToAttach.getCodigo());
                attachedEmpresasCollectionNew.add(empresasCollectionNewEmpresasToAttach);
            }
            empresasCollectionNew = attachedEmpresasCollectionNew;
            grupos.setEmpresasCollection(empresasCollectionNew);
            grupos = em.merge(grupos);
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    usuariosCollectionOldUsuarios.getGruposCollection().remove(grupos);
                    usuariosCollectionOldUsuarios = em.merge(usuariosCollectionOldUsuarios);
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    usuariosCollectionNewUsuarios.getGruposCollection().add(grupos);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                }
            }
            for (Empresas empresasCollectionOldEmpresas : empresasCollectionOld) {
                if (!empresasCollectionNew.contains(empresasCollectionOldEmpresas)) {
                    empresasCollectionOldEmpresas.getGruposCollection().remove(grupos);
                    empresasCollectionOldEmpresas = em.merge(empresasCollectionOldEmpresas);
                }
            }
            for (Empresas empresasCollectionNewEmpresas : empresasCollectionNew) {
                if (!empresasCollectionOld.contains(empresasCollectionNewEmpresas)) {
                    empresasCollectionNewEmpresas.getGruposCollection().add(grupos);
                    empresasCollectionNewEmpresas = em.merge(empresasCollectionNewEmpresas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupos.getId();
                if (findGrupos(id) == null) {
                    throw new NonexistentEntityException("The grupos with id " + id + " no longer exists.");
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
            Grupos grupos;
            try {
                grupos = em.getReference(Grupos.class, id);
                grupos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupos with id " + id + " no longer exists.", enfe);
            }
            Collection<Usuarios> usuariosCollection = grupos.getUsuariosCollection();
            for (Usuarios usuariosCollectionUsuarios : usuariosCollection) {
                usuariosCollectionUsuarios.getGruposCollection().remove(grupos);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            Collection<Empresas> empresasCollection = grupos.getEmpresasCollection();
            for (Empresas empresasCollectionEmpresas : empresasCollection) {
                empresasCollectionEmpresas.getGruposCollection().remove(grupos);
                empresasCollectionEmpresas = em.merge(empresasCollectionEmpresas);
            }
            em.remove(grupos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupos> findGruposEntities() {
        return findGruposEntities(true, -1, -1);
    }

    public List<Grupos> findGruposEntities(int maxResults, int firstResult) {
        return findGruposEntities(false, maxResults, firstResult);
    }

    private List<Grupos> findGruposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Grupos as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Grupos findGrupos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupos.class, id);
        } finally {
            em.close();
        }
    }

    public int getGruposCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Grupos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
