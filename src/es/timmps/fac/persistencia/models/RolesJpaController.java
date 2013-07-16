/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Grupos;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.Aplicaciones;
import es.timmps.fac.persistencia.Roles;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class RolesJpaController implements Serializable {

    public RolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roles roles) {
        if (roles.getGruposCollection() == null) {
            roles.setGruposCollection(new ArrayList<Grupos>());
        }
        if (roles.getAplicacionesCollection() == null) {
            roles.setAplicacionesCollection(new ArrayList<Aplicaciones>());
        }
        if (roles.getUsuariosCollection() == null) {
            roles.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Grupos> attachedGruposCollection = new ArrayList<Grupos>();
            for (Grupos gruposCollectionGruposToAttach : roles.getGruposCollection()) {
                gruposCollectionGruposToAttach = em.getReference(gruposCollectionGruposToAttach.getClass(), gruposCollectionGruposToAttach.getId());
                attachedGruposCollection.add(gruposCollectionGruposToAttach);
            }
            roles.setGruposCollection(attachedGruposCollection);
            Collection<Aplicaciones> attachedAplicacionesCollection = new ArrayList<Aplicaciones>();
            for (Aplicaciones aplicacionesCollectionAplicacionesToAttach : roles.getAplicacionesCollection()) {
                aplicacionesCollectionAplicacionesToAttach = em.getReference(aplicacionesCollectionAplicacionesToAttach.getClass(), aplicacionesCollectionAplicacionesToAttach.getId());
                attachedAplicacionesCollection.add(aplicacionesCollectionAplicacionesToAttach);
            }
            roles.setAplicacionesCollection(attachedAplicacionesCollection);
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : roles.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            roles.setUsuariosCollection(attachedUsuariosCollection);
            em.persist(roles);
            for (Grupos gruposCollectionGrupos : roles.getGruposCollection()) {
                gruposCollectionGrupos.getRolesCollection().add(roles);
                gruposCollectionGrupos = em.merge(gruposCollectionGrupos);
            }
            for (Aplicaciones aplicacionesCollectionAplicaciones : roles.getAplicacionesCollection()) {
                aplicacionesCollectionAplicaciones.getRolesCollection().add(roles);
                aplicacionesCollectionAplicaciones = em.merge(aplicacionesCollectionAplicaciones);
            }
            for (Usuarios usuariosCollectionUsuarios : roles.getUsuariosCollection()) {
                usuariosCollectionUsuarios.getRolesCollection().add(roles);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Roles roles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles persistentRoles = em.find(Roles.class, roles.getId());
            Collection<Grupos> gruposCollectionOld = persistentRoles.getGruposCollection();
            Collection<Grupos> gruposCollectionNew = roles.getGruposCollection();
            Collection<Aplicaciones> aplicacionesCollectionOld = persistentRoles.getAplicacionesCollection();
            Collection<Aplicaciones> aplicacionesCollectionNew = roles.getAplicacionesCollection();
            Collection<Usuarios> usuariosCollectionOld = persistentRoles.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = roles.getUsuariosCollection();
            Collection<Grupos> attachedGruposCollectionNew = new ArrayList<Grupos>();
            for (Grupos gruposCollectionNewGruposToAttach : gruposCollectionNew) {
                gruposCollectionNewGruposToAttach = em.getReference(gruposCollectionNewGruposToAttach.getClass(), gruposCollectionNewGruposToAttach.getId());
                attachedGruposCollectionNew.add(gruposCollectionNewGruposToAttach);
            }
            gruposCollectionNew = attachedGruposCollectionNew;
            roles.setGruposCollection(gruposCollectionNew);
            Collection<Aplicaciones> attachedAplicacionesCollectionNew = new ArrayList<Aplicaciones>();
            for (Aplicaciones aplicacionesCollectionNewAplicacionesToAttach : aplicacionesCollectionNew) {
                aplicacionesCollectionNewAplicacionesToAttach = em.getReference(aplicacionesCollectionNewAplicacionesToAttach.getClass(), aplicacionesCollectionNewAplicacionesToAttach.getId());
                attachedAplicacionesCollectionNew.add(aplicacionesCollectionNewAplicacionesToAttach);
            }
            aplicacionesCollectionNew = attachedAplicacionesCollectionNew;
            roles.setAplicacionesCollection(aplicacionesCollectionNew);
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            roles.setUsuariosCollection(usuariosCollectionNew);
            roles = em.merge(roles);
            for (Grupos gruposCollectionOldGrupos : gruposCollectionOld) {
                if (!gruposCollectionNew.contains(gruposCollectionOldGrupos)) {
                    gruposCollectionOldGrupos.getRolesCollection().remove(roles);
                    gruposCollectionOldGrupos = em.merge(gruposCollectionOldGrupos);
                }
            }
            for (Grupos gruposCollectionNewGrupos : gruposCollectionNew) {
                if (!gruposCollectionOld.contains(gruposCollectionNewGrupos)) {
                    gruposCollectionNewGrupos.getRolesCollection().add(roles);
                    gruposCollectionNewGrupos = em.merge(gruposCollectionNewGrupos);
                }
            }
            for (Aplicaciones aplicacionesCollectionOldAplicaciones : aplicacionesCollectionOld) {
                if (!aplicacionesCollectionNew.contains(aplicacionesCollectionOldAplicaciones)) {
                    aplicacionesCollectionOldAplicaciones.getRolesCollection().remove(roles);
                    aplicacionesCollectionOldAplicaciones = em.merge(aplicacionesCollectionOldAplicaciones);
                }
            }
            for (Aplicaciones aplicacionesCollectionNewAplicaciones : aplicacionesCollectionNew) {
                if (!aplicacionesCollectionOld.contains(aplicacionesCollectionNewAplicaciones)) {
                    aplicacionesCollectionNewAplicaciones.getRolesCollection().add(roles);
                    aplicacionesCollectionNewAplicaciones = em.merge(aplicacionesCollectionNewAplicaciones);
                }
            }
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    usuariosCollectionOldUsuarios.getRolesCollection().remove(roles);
                    usuariosCollectionOldUsuarios = em.merge(usuariosCollectionOldUsuarios);
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    usuariosCollectionNewUsuarios.getRolesCollection().add(roles);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = roles.getId();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            Collection<Grupos> gruposCollection = roles.getGruposCollection();
            for (Grupos gruposCollectionGrupos : gruposCollection) {
                gruposCollectionGrupos.getRolesCollection().remove(roles);
                gruposCollectionGrupos = em.merge(gruposCollectionGrupos);
            }
            Collection<Aplicaciones> aplicacionesCollection = roles.getAplicacionesCollection();
            for (Aplicaciones aplicacionesCollectionAplicaciones : aplicacionesCollection) {
                aplicacionesCollectionAplicaciones.getRolesCollection().remove(roles);
                aplicacionesCollectionAplicaciones = em.merge(aplicacionesCollectionAplicaciones);
            }
            Collection<Usuarios> usuariosCollection = roles.getUsuariosCollection();
            for (Usuarios usuariosCollectionUsuarios : usuariosCollection) {
                usuariosCollectionUsuarios.getRolesCollection().remove(roles);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            em.remove(roles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Roles as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Roles findRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Roles as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
