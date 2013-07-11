/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.DatosPersonales;
import es.timmps.fac.persistencia.pojos.TipoPersona;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class TipoPersonaJpaController implements Serializable {

    public TipoPersonaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPersona tipoPersona) {
        if (tipoPersona.getDatosPersonalesCollection() == null) {
            tipoPersona.setDatosPersonalesCollection(new ArrayList<DatosPersonales>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<DatosPersonales> attachedDatosPersonalesCollection = new ArrayList<DatosPersonales>();
            for (DatosPersonales datosPersonalesCollectionDatosPersonalesToAttach : tipoPersona.getDatosPersonalesCollection()) {
                datosPersonalesCollectionDatosPersonalesToAttach = em.getReference(datosPersonalesCollectionDatosPersonalesToAttach.getClass(), datosPersonalesCollectionDatosPersonalesToAttach.getCodigo());
                attachedDatosPersonalesCollection.add(datosPersonalesCollectionDatosPersonalesToAttach);
            }
            tipoPersona.setDatosPersonalesCollection(attachedDatosPersonalesCollection);
            em.persist(tipoPersona);
            for (DatosPersonales datosPersonalesCollectionDatosPersonales : tipoPersona.getDatosPersonalesCollection()) {
                TipoPersona oldTipoOfDatosPersonalesCollectionDatosPersonales = datosPersonalesCollectionDatosPersonales.getTipo();
                datosPersonalesCollectionDatosPersonales.setTipo(tipoPersona);
                datosPersonalesCollectionDatosPersonales = em.merge(datosPersonalesCollectionDatosPersonales);
                if (oldTipoOfDatosPersonalesCollectionDatosPersonales != null) {
                    oldTipoOfDatosPersonalesCollectionDatosPersonales.getDatosPersonalesCollection().remove(datosPersonalesCollectionDatosPersonales);
                    oldTipoOfDatosPersonalesCollectionDatosPersonales = em.merge(oldTipoOfDatosPersonalesCollectionDatosPersonales);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPersona tipoPersona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPersona persistentTipoPersona = em.find(TipoPersona.class, tipoPersona.getId());
            Collection<DatosPersonales> datosPersonalesCollectionOld = persistentTipoPersona.getDatosPersonalesCollection();
            Collection<DatosPersonales> datosPersonalesCollectionNew = tipoPersona.getDatosPersonalesCollection();
            List<String> illegalOrphanMessages = null;
            for (DatosPersonales datosPersonalesCollectionOldDatosPersonales : datosPersonalesCollectionOld) {
                if (!datosPersonalesCollectionNew.contains(datosPersonalesCollectionOldDatosPersonales)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DatosPersonales " + datosPersonalesCollectionOldDatosPersonales + " since its tipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<DatosPersonales> attachedDatosPersonalesCollectionNew = new ArrayList<DatosPersonales>();
            for (DatosPersonales datosPersonalesCollectionNewDatosPersonalesToAttach : datosPersonalesCollectionNew) {
                datosPersonalesCollectionNewDatosPersonalesToAttach = em.getReference(datosPersonalesCollectionNewDatosPersonalesToAttach.getClass(), datosPersonalesCollectionNewDatosPersonalesToAttach.getCodigo());
                attachedDatosPersonalesCollectionNew.add(datosPersonalesCollectionNewDatosPersonalesToAttach);
            }
            datosPersonalesCollectionNew = attachedDatosPersonalesCollectionNew;
            tipoPersona.setDatosPersonalesCollection(datosPersonalesCollectionNew);
            tipoPersona = em.merge(tipoPersona);
            for (DatosPersonales datosPersonalesCollectionNewDatosPersonales : datosPersonalesCollectionNew) {
                if (!datosPersonalesCollectionOld.contains(datosPersonalesCollectionNewDatosPersonales)) {
                    TipoPersona oldTipoOfDatosPersonalesCollectionNewDatosPersonales = datosPersonalesCollectionNewDatosPersonales.getTipo();
                    datosPersonalesCollectionNewDatosPersonales.setTipo(tipoPersona);
                    datosPersonalesCollectionNewDatosPersonales = em.merge(datosPersonalesCollectionNewDatosPersonales);
                    if (oldTipoOfDatosPersonalesCollectionNewDatosPersonales != null && !oldTipoOfDatosPersonalesCollectionNewDatosPersonales.equals(tipoPersona)) {
                        oldTipoOfDatosPersonalesCollectionNewDatosPersonales.getDatosPersonalesCollection().remove(datosPersonalesCollectionNewDatosPersonales);
                        oldTipoOfDatosPersonalesCollectionNewDatosPersonales = em.merge(oldTipoOfDatosPersonalesCollectionNewDatosPersonales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoPersona.getId();
                if (findTipoPersona(id) == null) {
                    throw new NonexistentEntityException("The tipoPersona with id " + id + " no longer exists.");
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
            TipoPersona tipoPersona;
            try {
                tipoPersona = em.getReference(TipoPersona.class, id);
                tipoPersona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPersona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DatosPersonales> datosPersonalesCollectionOrphanCheck = tipoPersona.getDatosPersonalesCollection();
            for (DatosPersonales datosPersonalesCollectionOrphanCheckDatosPersonales : datosPersonalesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPersona (" + tipoPersona + ") cannot be destroyed since the DatosPersonales " + datosPersonalesCollectionOrphanCheckDatosPersonales + " in its datosPersonalesCollection field has a non-nullable tipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoPersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoPersona> findTipoPersonaEntities() {
        return findTipoPersonaEntities(true, -1, -1);
    }

    public List<TipoPersona> findTipoPersonaEntities(int maxResults, int firstResult) {
        return findTipoPersonaEntities(false, maxResults, firstResult);
    }

    private List<TipoPersona> findTipoPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPersona.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TipoPersona findTipoPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPersona> rt = cq.from(TipoPersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
