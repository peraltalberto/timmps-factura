/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Grupos;
import es.timmps.fac.persistencia.UsuGrupo;
import es.timmps.fac.persistencia.UsuGrupoPK;
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
public class UsuGrupoJpaController implements Serializable {

    public UsuGrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuGrupo usuGrupo) throws PreexistingEntityException, Exception {
        if (usuGrupo.getUsuGrupoPK() == null) {
            usuGrupo.setUsuGrupoPK(new UsuGrupoPK());
        }
        usuGrupo.getUsuGrupoPK().setGrupo(usuGrupo.getGrupos().getId());
        usuGrupo.getUsuGrupoPK().setUsu(usuGrupo.getUsuarios().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupos grupos = usuGrupo.getGrupos();
            if (grupos != null) {
                grupos = em.getReference(grupos.getClass(), grupos.getId());
                usuGrupo.setGrupos(grupos);
            }
            Usuarios usuarios = usuGrupo.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getId());
                usuGrupo.setUsuarios(usuarios);
            }
            em.persist(usuGrupo);
            if (grupos != null) {
                grupos.getUsuGrupoCollection().add(usuGrupo);
                grupos = em.merge(grupos);
            }
            if (usuarios != null) {
                usuarios.getUsuGrupoCollection().add(usuGrupo);
                usuarios = em.merge(usuarios);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuGrupo(usuGrupo.getUsuGrupoPK()) != null) {
                throw new PreexistingEntityException("UsuGrupo " + usuGrupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuGrupo usuGrupo) throws NonexistentEntityException, Exception {
        usuGrupo.getUsuGrupoPK().setGrupo(usuGrupo.getGrupos().getId());
        usuGrupo.getUsuGrupoPK().setUsu(usuGrupo.getUsuarios().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuGrupo persistentUsuGrupo = em.find(UsuGrupo.class, usuGrupo.getUsuGrupoPK());
            Grupos gruposOld = persistentUsuGrupo.getGrupos();
            Grupos gruposNew = usuGrupo.getGrupos();
            Usuarios usuariosOld = persistentUsuGrupo.getUsuarios();
            Usuarios usuariosNew = usuGrupo.getUsuarios();
            if (gruposNew != null) {
                gruposNew = em.getReference(gruposNew.getClass(), gruposNew.getId());
                usuGrupo.setGrupos(gruposNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getId());
                usuGrupo.setUsuarios(usuariosNew);
            }
            usuGrupo = em.merge(usuGrupo);
            if (gruposOld != null && !gruposOld.equals(gruposNew)) {
                gruposOld.getUsuGrupoCollection().remove(usuGrupo);
                gruposOld = em.merge(gruposOld);
            }
            if (gruposNew != null && !gruposNew.equals(gruposOld)) {
                gruposNew.getUsuGrupoCollection().add(usuGrupo);
                gruposNew = em.merge(gruposNew);
            }
            if (usuariosOld != null && !usuariosOld.equals(usuariosNew)) {
                usuariosOld.getUsuGrupoCollection().remove(usuGrupo);
                usuariosOld = em.merge(usuariosOld);
            }
            if (usuariosNew != null && !usuariosNew.equals(usuariosOld)) {
                usuariosNew.getUsuGrupoCollection().add(usuGrupo);
                usuariosNew = em.merge(usuariosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UsuGrupoPK id = usuGrupo.getUsuGrupoPK();
                if (findUsuGrupo(id) == null) {
                    throw new NonexistentEntityException("The usuGrupo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsuGrupoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuGrupo usuGrupo;
            try {
                usuGrupo = em.getReference(UsuGrupo.class, id);
                usuGrupo.getUsuGrupoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuGrupo with id " + id + " no longer exists.", enfe);
            }
            Grupos grupos = usuGrupo.getGrupos();
            if (grupos != null) {
                grupos.getUsuGrupoCollection().remove(usuGrupo);
                grupos = em.merge(grupos);
            }
            Usuarios usuarios = usuGrupo.getUsuarios();
            if (usuarios != null) {
                usuarios.getUsuGrupoCollection().remove(usuGrupo);
                usuarios = em.merge(usuarios);
            }
            em.remove(usuGrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuGrupo> findUsuGrupoEntities() {
        return findUsuGrupoEntities(true, -1, -1);
    }

    public List<UsuGrupo> findUsuGrupoEntities(int maxResults, int firstResult) {
        return findUsuGrupoEntities(false, maxResults, firstResult);
    }

    private List<UsuGrupo> findUsuGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from UsuGrupo as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public UsuGrupo findUsuGrupo(UsuGrupoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuGrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from UsuGrupo as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
