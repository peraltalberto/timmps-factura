/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.fac.persistencia.Aplicaciones;
import es.timmps.fac.persistencia.Sessiones;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class SessionesJpaController implements Serializable {

    public SessionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sessiones sessiones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas empresa = sessiones.getEmpresa();
            if (empresa != null) {
                empresa = em.getReference(empresa.getClass(), empresa.getCodigo());
                sessiones.setEmpresa(empresa);
            }
            Usuarios usuario = sessiones.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                sessiones.setUsuario(usuario);
            }
            Aplicaciones aplicacion = sessiones.getAplicacion();
            if (aplicacion != null) {
                aplicacion = em.getReference(aplicacion.getClass(), aplicacion.getId());
                sessiones.setAplicacion(aplicacion);
            }
            em.persist(sessiones);
            if (empresa != null) {
                empresa.getSessionesCollection().add(sessiones);
                empresa = em.merge(empresa);
            }
            if (usuario != null) {
                usuario.getSessionesCollection().add(sessiones);
                usuario = em.merge(usuario);
            }
            if (aplicacion != null) {
                aplicacion.getSessionesCollection().add(sessiones);
                aplicacion = em.merge(aplicacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sessiones sessiones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sessiones persistentSessiones = em.find(Sessiones.class, sessiones.getId());
            Empresas empresaOld = persistentSessiones.getEmpresa();
            Empresas empresaNew = sessiones.getEmpresa();
            Usuarios usuarioOld = persistentSessiones.getUsuario();
            Usuarios usuarioNew = sessiones.getUsuario();
            Aplicaciones aplicacionOld = persistentSessiones.getAplicacion();
            Aplicaciones aplicacionNew = sessiones.getAplicacion();
            if (empresaNew != null) {
                empresaNew = em.getReference(empresaNew.getClass(), empresaNew.getCodigo());
                sessiones.setEmpresa(empresaNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                sessiones.setUsuario(usuarioNew);
            }
            if (aplicacionNew != null) {
                aplicacionNew = em.getReference(aplicacionNew.getClass(), aplicacionNew.getId());
                sessiones.setAplicacion(aplicacionNew);
            }
            sessiones = em.merge(sessiones);
            if (empresaOld != null && !empresaOld.equals(empresaNew)) {
                empresaOld.getSessionesCollection().remove(sessiones);
                empresaOld = em.merge(empresaOld);
            }
            if (empresaNew != null && !empresaNew.equals(empresaOld)) {
                empresaNew.getSessionesCollection().add(sessiones);
                empresaNew = em.merge(empresaNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getSessionesCollection().remove(sessiones);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getSessionesCollection().add(sessiones);
                usuarioNew = em.merge(usuarioNew);
            }
            if (aplicacionOld != null && !aplicacionOld.equals(aplicacionNew)) {
                aplicacionOld.getSessionesCollection().remove(sessiones);
                aplicacionOld = em.merge(aplicacionOld);
            }
            if (aplicacionNew != null && !aplicacionNew.equals(aplicacionOld)) {
                aplicacionNew.getSessionesCollection().add(sessiones);
                aplicacionNew = em.merge(aplicacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sessiones.getId();
                if (findSessiones(id) == null) {
                    throw new NonexistentEntityException("The sessiones with id " + id + " no longer exists.");
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
            Sessiones sessiones;
            try {
                sessiones = em.getReference(Sessiones.class, id);
                sessiones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sessiones with id " + id + " no longer exists.", enfe);
            }
            Empresas empresa = sessiones.getEmpresa();
            if (empresa != null) {
                empresa.getSessionesCollection().remove(sessiones);
                empresa = em.merge(empresa);
            }
            Usuarios usuario = sessiones.getUsuario();
            if (usuario != null) {
                usuario.getSessionesCollection().remove(sessiones);
                usuario = em.merge(usuario);
            }
            Aplicaciones aplicacion = sessiones.getAplicacion();
            if (aplicacion != null) {
                aplicacion.getSessionesCollection().remove(sessiones);
                aplicacion = em.merge(aplicacion);
            }
            em.remove(sessiones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sessiones> findSessionesEntities() {
        return findSessionesEntities(true, -1, -1);
    }

    public List<Sessiones> findSessionesEntities(int maxResults, int firstResult) {
        return findSessionesEntities(false, maxResults, firstResult);
    }

    private List<Sessiones> findSessionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Sessiones as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Sessiones findSessiones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sessiones.class, id);
        } finally {
            em.close();
        }
    }

    public int getSessionesCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Sessiones as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
