/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.Contadores;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class ContadoresJpaController implements Serializable {

    public ContadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contadores contadores) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = contadores.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                contadores.setCodEmp(codEmp);
            }
            em.persist(contadores);
            if (codEmp != null) {
                codEmp.getContadoresCollection().add(contadores);
                codEmp = em.merge(codEmp);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContadores(contadores.getId()) != null) {
                throw new PreexistingEntityException("Contadores " + contadores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contadores contadores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contadores persistentContadores = em.find(Contadores.class, contadores.getId());
            Empresas codEmpOld = persistentContadores.getCodEmp();
            Empresas codEmpNew = contadores.getCodEmp();
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                contadores.setCodEmp(codEmpNew);
            }
            contadores = em.merge(contadores);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getContadoresCollection().remove(contadores);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getContadoresCollection().add(contadores);
                codEmpNew = em.merge(codEmpNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = contadores.getId();
                if (findContadores(id) == null) {
                    throw new NonexistentEntityException("The contadores with id " + id + " no longer exists.");
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
            Contadores contadores;
            try {
                contadores = em.getReference(Contadores.class, id);
                contadores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contadores with id " + id + " no longer exists.", enfe);
            }
            Empresas codEmp = contadores.getCodEmp();
            if (codEmp != null) {
                codEmp.getContadoresCollection().remove(contadores);
                codEmp = em.merge(codEmp);
            }
            em.remove(contadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contadores> findContadoresEntities() {
        return findContadoresEntities(true, -1, -1);
    }

    public List<Contadores> findContadoresEntities(int maxResults, int firstResult) {
        return findContadoresEntities(false, maxResults, firstResult);
    }

    private List<Contadores> findContadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Contadores as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Contadores findContadores(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getContadoresCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Contadores as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
