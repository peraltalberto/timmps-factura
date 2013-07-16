/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.Aplicaciones;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Roles;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class AplicacionesJpaController implements Serializable {

    public AplicacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aplicaciones aplicaciones) throws PreexistingEntityException, Exception {
        if (aplicaciones.getRolesCollection() == null) {
            aplicaciones.setRolesCollection(new ArrayList<Roles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Roles> attachedRolesCollection = new ArrayList<Roles>();
            for (Roles rolesCollectionRolesToAttach : aplicaciones.getRolesCollection()) {
                rolesCollectionRolesToAttach = em.getReference(rolesCollectionRolesToAttach.getClass(), rolesCollectionRolesToAttach.getId());
                attachedRolesCollection.add(rolesCollectionRolesToAttach);
            }
            aplicaciones.setRolesCollection(attachedRolesCollection);
            em.persist(aplicaciones);
            for (Roles rolesCollectionRoles : aplicaciones.getRolesCollection()) {
                rolesCollectionRoles.getAplicacionesCollection().add(aplicaciones);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAplicaciones(aplicaciones.getId()) != null) {
                throw new PreexistingEntityException("Aplicaciones " + aplicaciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aplicaciones aplicaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aplicaciones persistentAplicaciones = em.find(Aplicaciones.class, aplicaciones.getId());
            Collection<Roles> rolesCollectionOld = persistentAplicaciones.getRolesCollection();
            Collection<Roles> rolesCollectionNew = aplicaciones.getRolesCollection();
            Collection<Roles> attachedRolesCollectionNew = new ArrayList<Roles>();
            for (Roles rolesCollectionNewRolesToAttach : rolesCollectionNew) {
                rolesCollectionNewRolesToAttach = em.getReference(rolesCollectionNewRolesToAttach.getClass(), rolesCollectionNewRolesToAttach.getId());
                attachedRolesCollectionNew.add(rolesCollectionNewRolesToAttach);
            }
            rolesCollectionNew = attachedRolesCollectionNew;
            aplicaciones.setRolesCollection(rolesCollectionNew);
            aplicaciones = em.merge(aplicaciones);
            for (Roles rolesCollectionOldRoles : rolesCollectionOld) {
                if (!rolesCollectionNew.contains(rolesCollectionOldRoles)) {
                    rolesCollectionOldRoles.getAplicacionesCollection().remove(aplicaciones);
                    rolesCollectionOldRoles = em.merge(rolesCollectionOldRoles);
                }
            }
            for (Roles rolesCollectionNewRoles : rolesCollectionNew) {
                if (!rolesCollectionOld.contains(rolesCollectionNewRoles)) {
                    rolesCollectionNewRoles.getAplicacionesCollection().add(aplicaciones);
                    rolesCollectionNewRoles = em.merge(rolesCollectionNewRoles);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = aplicaciones.getId();
                if (findAplicaciones(id) == null) {
                    throw new NonexistentEntityException("The aplicaciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aplicaciones aplicaciones;
            try {
                aplicaciones = em.getReference(Aplicaciones.class, id);
                aplicaciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aplicaciones with id " + id + " no longer exists.", enfe);
            }
            Collection<Roles> rolesCollection = aplicaciones.getRolesCollection();
            for (Roles rolesCollectionRoles : rolesCollection) {
                rolesCollectionRoles.getAplicacionesCollection().remove(aplicaciones);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
            }
            em.remove(aplicaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aplicaciones> findAplicacionesEntities() {
        return findAplicacionesEntities(true, -1, -1);
    }

    public List<Aplicaciones> findAplicacionesEntities(int maxResults, int firstResult) {
        return findAplicacionesEntities(false, maxResults, firstResult);
    }

    private List<Aplicaciones> findAplicacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Aplicaciones as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Aplicaciones findAplicaciones(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aplicaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getAplicacionesCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Aplicaciones as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
