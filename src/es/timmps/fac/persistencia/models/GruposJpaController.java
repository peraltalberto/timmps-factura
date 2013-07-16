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
import es.timmps.fac.persistencia.Roles;
import es.timmps.fac.persistencia.UsuGrupo;
import es.timmps.fac.persistencia.EmpGrupo;
import es.timmps.fac.persistencia.Grupos;
import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
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
        if (grupos.getRolesCollection() == null) {
            grupos.setRolesCollection(new ArrayList<Roles>());
        }
        if (grupos.getUsuGrupoCollection() == null) {
            grupos.setUsuGrupoCollection(new ArrayList<UsuGrupo>());
        }
        if (grupos.getEmpGrupoCollection() == null) {
            grupos.setEmpGrupoCollection(new ArrayList<EmpGrupo>());
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
            Collection<Roles> attachedRolesCollection = new ArrayList<Roles>();
            for (Roles rolesCollectionRolesToAttach : grupos.getRolesCollection()) {
                rolesCollectionRolesToAttach = em.getReference(rolesCollectionRolesToAttach.getClass(), rolesCollectionRolesToAttach.getId());
                attachedRolesCollection.add(rolesCollectionRolesToAttach);
            }
            grupos.setRolesCollection(attachedRolesCollection);
            Collection<UsuGrupo> attachedUsuGrupoCollection = new ArrayList<UsuGrupo>();
            for (UsuGrupo usuGrupoCollectionUsuGrupoToAttach : grupos.getUsuGrupoCollection()) {
                usuGrupoCollectionUsuGrupoToAttach = em.getReference(usuGrupoCollectionUsuGrupoToAttach.getClass(), usuGrupoCollectionUsuGrupoToAttach.getUsuGrupoPK());
                attachedUsuGrupoCollection.add(usuGrupoCollectionUsuGrupoToAttach);
            }
            grupos.setUsuGrupoCollection(attachedUsuGrupoCollection);
            Collection<EmpGrupo> attachedEmpGrupoCollection = new ArrayList<EmpGrupo>();
            for (EmpGrupo empGrupoCollectionEmpGrupoToAttach : grupos.getEmpGrupoCollection()) {
                empGrupoCollectionEmpGrupoToAttach = em.getReference(empGrupoCollectionEmpGrupoToAttach.getClass(), empGrupoCollectionEmpGrupoToAttach.getEmpGrupoPK());
                attachedEmpGrupoCollection.add(empGrupoCollectionEmpGrupoToAttach);
            }
            grupos.setEmpGrupoCollection(attachedEmpGrupoCollection);
            em.persist(grupos);
            for (Usuarios usuariosCollectionUsuarios : grupos.getUsuariosCollection()) {
                usuariosCollectionUsuarios.getGruposCollection().add(grupos);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            for (Empresas empresasCollectionEmpresas : grupos.getEmpresasCollection()) {
                empresasCollectionEmpresas.getGruposCollection().add(grupos);
                empresasCollectionEmpresas = em.merge(empresasCollectionEmpresas);
            }
            for (Roles rolesCollectionRoles : grupos.getRolesCollection()) {
                rolesCollectionRoles.getGruposCollection().add(grupos);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
            }
            for (UsuGrupo usuGrupoCollectionUsuGrupo : grupos.getUsuGrupoCollection()) {
                Grupos oldGruposOfUsuGrupoCollectionUsuGrupo = usuGrupoCollectionUsuGrupo.getGrupos();
                usuGrupoCollectionUsuGrupo.setGrupos(grupos);
                usuGrupoCollectionUsuGrupo = em.merge(usuGrupoCollectionUsuGrupo);
                if (oldGruposOfUsuGrupoCollectionUsuGrupo != null) {
                    oldGruposOfUsuGrupoCollectionUsuGrupo.getUsuGrupoCollection().remove(usuGrupoCollectionUsuGrupo);
                    oldGruposOfUsuGrupoCollectionUsuGrupo = em.merge(oldGruposOfUsuGrupoCollectionUsuGrupo);
                }
            }
            for (EmpGrupo empGrupoCollectionEmpGrupo : grupos.getEmpGrupoCollection()) {
                Grupos oldGruposOfEmpGrupoCollectionEmpGrupo = empGrupoCollectionEmpGrupo.getGrupos();
                empGrupoCollectionEmpGrupo.setGrupos(grupos);
                empGrupoCollectionEmpGrupo = em.merge(empGrupoCollectionEmpGrupo);
                if (oldGruposOfEmpGrupoCollectionEmpGrupo != null) {
                    oldGruposOfEmpGrupoCollectionEmpGrupo.getEmpGrupoCollection().remove(empGrupoCollectionEmpGrupo);
                    oldGruposOfEmpGrupoCollectionEmpGrupo = em.merge(oldGruposOfEmpGrupoCollectionEmpGrupo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupos grupos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupos persistentGrupos = em.find(Grupos.class, grupos.getId());
            Collection<Usuarios> usuariosCollectionOld = persistentGrupos.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = grupos.getUsuariosCollection();
            Collection<Empresas> empresasCollectionOld = persistentGrupos.getEmpresasCollection();
            Collection<Empresas> empresasCollectionNew = grupos.getEmpresasCollection();
            Collection<Roles> rolesCollectionOld = persistentGrupos.getRolesCollection();
            Collection<Roles> rolesCollectionNew = grupos.getRolesCollection();
            Collection<UsuGrupo> usuGrupoCollectionOld = persistentGrupos.getUsuGrupoCollection();
            Collection<UsuGrupo> usuGrupoCollectionNew = grupos.getUsuGrupoCollection();
            Collection<EmpGrupo> empGrupoCollectionOld = persistentGrupos.getEmpGrupoCollection();
            Collection<EmpGrupo> empGrupoCollectionNew = grupos.getEmpGrupoCollection();
            List<String> illegalOrphanMessages = null;
            for (UsuGrupo usuGrupoCollectionOldUsuGrupo : usuGrupoCollectionOld) {
                if (!usuGrupoCollectionNew.contains(usuGrupoCollectionOldUsuGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuGrupo " + usuGrupoCollectionOldUsuGrupo + " since its grupos field is not nullable.");
                }
            }
            for (EmpGrupo empGrupoCollectionOldEmpGrupo : empGrupoCollectionOld) {
                if (!empGrupoCollectionNew.contains(empGrupoCollectionOldEmpGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EmpGrupo " + empGrupoCollectionOldEmpGrupo + " since its grupos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
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
            Collection<Roles> attachedRolesCollectionNew = new ArrayList<Roles>();
            for (Roles rolesCollectionNewRolesToAttach : rolesCollectionNew) {
                rolesCollectionNewRolesToAttach = em.getReference(rolesCollectionNewRolesToAttach.getClass(), rolesCollectionNewRolesToAttach.getId());
                attachedRolesCollectionNew.add(rolesCollectionNewRolesToAttach);
            }
            rolesCollectionNew = attachedRolesCollectionNew;
            grupos.setRolesCollection(rolesCollectionNew);
            Collection<UsuGrupo> attachedUsuGrupoCollectionNew = new ArrayList<UsuGrupo>();
            for (UsuGrupo usuGrupoCollectionNewUsuGrupoToAttach : usuGrupoCollectionNew) {
                usuGrupoCollectionNewUsuGrupoToAttach = em.getReference(usuGrupoCollectionNewUsuGrupoToAttach.getClass(), usuGrupoCollectionNewUsuGrupoToAttach.getUsuGrupoPK());
                attachedUsuGrupoCollectionNew.add(usuGrupoCollectionNewUsuGrupoToAttach);
            }
            usuGrupoCollectionNew = attachedUsuGrupoCollectionNew;
            grupos.setUsuGrupoCollection(usuGrupoCollectionNew);
            Collection<EmpGrupo> attachedEmpGrupoCollectionNew = new ArrayList<EmpGrupo>();
            for (EmpGrupo empGrupoCollectionNewEmpGrupoToAttach : empGrupoCollectionNew) {
                empGrupoCollectionNewEmpGrupoToAttach = em.getReference(empGrupoCollectionNewEmpGrupoToAttach.getClass(), empGrupoCollectionNewEmpGrupoToAttach.getEmpGrupoPK());
                attachedEmpGrupoCollectionNew.add(empGrupoCollectionNewEmpGrupoToAttach);
            }
            empGrupoCollectionNew = attachedEmpGrupoCollectionNew;
            grupos.setEmpGrupoCollection(empGrupoCollectionNew);
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
            for (Roles rolesCollectionOldRoles : rolesCollectionOld) {
                if (!rolesCollectionNew.contains(rolesCollectionOldRoles)) {
                    rolesCollectionOldRoles.getGruposCollection().remove(grupos);
                    rolesCollectionOldRoles = em.merge(rolesCollectionOldRoles);
                }
            }
            for (Roles rolesCollectionNewRoles : rolesCollectionNew) {
                if (!rolesCollectionOld.contains(rolesCollectionNewRoles)) {
                    rolesCollectionNewRoles.getGruposCollection().add(grupos);
                    rolesCollectionNewRoles = em.merge(rolesCollectionNewRoles);
                }
            }
            for (UsuGrupo usuGrupoCollectionNewUsuGrupo : usuGrupoCollectionNew) {
                if (!usuGrupoCollectionOld.contains(usuGrupoCollectionNewUsuGrupo)) {
                    Grupos oldGruposOfUsuGrupoCollectionNewUsuGrupo = usuGrupoCollectionNewUsuGrupo.getGrupos();
                    usuGrupoCollectionNewUsuGrupo.setGrupos(grupos);
                    usuGrupoCollectionNewUsuGrupo = em.merge(usuGrupoCollectionNewUsuGrupo);
                    if (oldGruposOfUsuGrupoCollectionNewUsuGrupo != null && !oldGruposOfUsuGrupoCollectionNewUsuGrupo.equals(grupos)) {
                        oldGruposOfUsuGrupoCollectionNewUsuGrupo.getUsuGrupoCollection().remove(usuGrupoCollectionNewUsuGrupo);
                        oldGruposOfUsuGrupoCollectionNewUsuGrupo = em.merge(oldGruposOfUsuGrupoCollectionNewUsuGrupo);
                    }
                }
            }
            for (EmpGrupo empGrupoCollectionNewEmpGrupo : empGrupoCollectionNew) {
                if (!empGrupoCollectionOld.contains(empGrupoCollectionNewEmpGrupo)) {
                    Grupos oldGruposOfEmpGrupoCollectionNewEmpGrupo = empGrupoCollectionNewEmpGrupo.getGrupos();
                    empGrupoCollectionNewEmpGrupo.setGrupos(grupos);
                    empGrupoCollectionNewEmpGrupo = em.merge(empGrupoCollectionNewEmpGrupo);
                    if (oldGruposOfEmpGrupoCollectionNewEmpGrupo != null && !oldGruposOfEmpGrupoCollectionNewEmpGrupo.equals(grupos)) {
                        oldGruposOfEmpGrupoCollectionNewEmpGrupo.getEmpGrupoCollection().remove(empGrupoCollectionNewEmpGrupo);
                        oldGruposOfEmpGrupoCollectionNewEmpGrupo = em.merge(oldGruposOfEmpGrupoCollectionNewEmpGrupo);
                    }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<UsuGrupo> usuGrupoCollectionOrphanCheck = grupos.getUsuGrupoCollection();
            for (UsuGrupo usuGrupoCollectionOrphanCheckUsuGrupo : usuGrupoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupos (" + grupos + ") cannot be destroyed since the UsuGrupo " + usuGrupoCollectionOrphanCheckUsuGrupo + " in its usuGrupoCollection field has a non-nullable grupos field.");
            }
            Collection<EmpGrupo> empGrupoCollectionOrphanCheck = grupos.getEmpGrupoCollection();
            for (EmpGrupo empGrupoCollectionOrphanCheckEmpGrupo : empGrupoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupos (" + grupos + ") cannot be destroyed since the EmpGrupo " + empGrupoCollectionOrphanCheckEmpGrupo + " in its empGrupoCollection field has a non-nullable grupos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
            Collection<Roles> rolesCollection = grupos.getRolesCollection();
            for (Roles rolesCollectionRoles : rolesCollection) {
                rolesCollectionRoles.getGruposCollection().remove(grupos);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
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
