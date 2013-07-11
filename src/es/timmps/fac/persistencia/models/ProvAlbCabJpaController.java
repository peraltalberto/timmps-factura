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
import es.timmps.fac.persistencia.pojos.Usuarios;
import es.timmps.fac.persistencia.pojos.Proveedores;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.ProvAlbCab;
import es.timmps.fac.persistencia.pojos.ProvPedidosCab;
import es.timmps.fac.persistencia.pojos.ProvFacCab;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.pojos.ProvAlbLin;
import es.timmps.fac.persistencia.pojos.ProvAlbCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class ProvAlbCabJpaController implements Serializable {

    public ProvAlbCabJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProvAlbCab provAlbCab) {
        if (provAlbCab.getProvFacCabCollection() == null) {
            provAlbCab.setProvFacCabCollection(new ArrayList<ProvFacCab>());
        }
        if (provAlbCab.getProvAlbLinCollection() == null) {
            provAlbCab.setProvAlbLinCollection(new ArrayList<ProvAlbLin>());
        }
        if (provAlbCab.getProvAlbCustomCollection() == null) {
            provAlbCab.setProvAlbCustomCollection(new ArrayList<ProvAlbCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuario = provAlbCab.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                provAlbCab.setUsuario(usuario);
            }
            Proveedores codProv = provAlbCab.getCodProv();
            if (codProv != null) {
                codProv = em.getReference(codProv.getClass(), codProv.getCodigo());
                provAlbCab.setCodProv(codProv);
            }
            Empresas codEmp = provAlbCab.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                provAlbCab.setCodEmp(codEmp);
            }
            ProvPedidosCab codPedido = provAlbCab.getCodPedido();
            if (codPedido != null) {
                codPedido = em.getReference(codPedido.getClass(), codPedido.getId());
                provAlbCab.setCodPedido(codPedido);
            }
            Collection<ProvFacCab> attachedProvFacCabCollection = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionProvFacCabToAttach : provAlbCab.getProvFacCabCollection()) {
                provFacCabCollectionProvFacCabToAttach = em.getReference(provFacCabCollectionProvFacCabToAttach.getClass(), provFacCabCollectionProvFacCabToAttach.getId());
                attachedProvFacCabCollection.add(provFacCabCollectionProvFacCabToAttach);
            }
            provAlbCab.setProvFacCabCollection(attachedProvFacCabCollection);
            Collection<ProvAlbLin> attachedProvAlbLinCollection = new ArrayList<ProvAlbLin>();
            for (ProvAlbLin provAlbLinCollectionProvAlbLinToAttach : provAlbCab.getProvAlbLinCollection()) {
                provAlbLinCollectionProvAlbLinToAttach = em.getReference(provAlbLinCollectionProvAlbLinToAttach.getClass(), provAlbLinCollectionProvAlbLinToAttach.getId());
                attachedProvAlbLinCollection.add(provAlbLinCollectionProvAlbLinToAttach);
            }
            provAlbCab.setProvAlbLinCollection(attachedProvAlbLinCollection);
            Collection<ProvAlbCustom> attachedProvAlbCustomCollection = new ArrayList<ProvAlbCustom>();
            for (ProvAlbCustom provAlbCustomCollectionProvAlbCustomToAttach : provAlbCab.getProvAlbCustomCollection()) {
                provAlbCustomCollectionProvAlbCustomToAttach = em.getReference(provAlbCustomCollectionProvAlbCustomToAttach.getClass(), provAlbCustomCollectionProvAlbCustomToAttach.getProvAlbCustomPK());
                attachedProvAlbCustomCollection.add(provAlbCustomCollectionProvAlbCustomToAttach);
            }
            provAlbCab.setProvAlbCustomCollection(attachedProvAlbCustomCollection);
            em.persist(provAlbCab);
            if (usuario != null) {
                usuario.getProvAlbCabCollection().add(provAlbCab);
                usuario = em.merge(usuario);
            }
            if (codProv != null) {
                codProv.getProvAlbCabCollection().add(provAlbCab);
                codProv = em.merge(codProv);
            }
            if (codEmp != null) {
                codEmp.getProvAlbCabCollection().add(provAlbCab);
                codEmp = em.merge(codEmp);
            }
            if (codPedido != null) {
                codPedido.getProvAlbCabCollection().add(provAlbCab);
                codPedido = em.merge(codPedido);
            }
            for (ProvFacCab provFacCabCollectionProvFacCab : provAlbCab.getProvFacCabCollection()) {
                ProvAlbCab oldCodAlbOfProvFacCabCollectionProvFacCab = provFacCabCollectionProvFacCab.getCodAlb();
                provFacCabCollectionProvFacCab.setCodAlb(provAlbCab);
                provFacCabCollectionProvFacCab = em.merge(provFacCabCollectionProvFacCab);
                if (oldCodAlbOfProvFacCabCollectionProvFacCab != null) {
                    oldCodAlbOfProvFacCabCollectionProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionProvFacCab);
                    oldCodAlbOfProvFacCabCollectionProvFacCab = em.merge(oldCodAlbOfProvFacCabCollectionProvFacCab);
                }
            }
            for (ProvAlbLin provAlbLinCollectionProvAlbLin : provAlbCab.getProvAlbLinCollection()) {
                ProvAlbCab oldCodCabOfProvAlbLinCollectionProvAlbLin = provAlbLinCollectionProvAlbLin.getCodCab();
                provAlbLinCollectionProvAlbLin.setCodCab(provAlbCab);
                provAlbLinCollectionProvAlbLin = em.merge(provAlbLinCollectionProvAlbLin);
                if (oldCodCabOfProvAlbLinCollectionProvAlbLin != null) {
                    oldCodCabOfProvAlbLinCollectionProvAlbLin.getProvAlbLinCollection().remove(provAlbLinCollectionProvAlbLin);
                    oldCodCabOfProvAlbLinCollectionProvAlbLin = em.merge(oldCodCabOfProvAlbLinCollectionProvAlbLin);
                }
            }
            for (ProvAlbCustom provAlbCustomCollectionProvAlbCustom : provAlbCab.getProvAlbCustomCollection()) {
                ProvAlbCab oldProvAlbCabOfProvAlbCustomCollectionProvAlbCustom = provAlbCustomCollectionProvAlbCustom.getProvAlbCab();
                provAlbCustomCollectionProvAlbCustom.setProvAlbCab(provAlbCab);
                provAlbCustomCollectionProvAlbCustom = em.merge(provAlbCustomCollectionProvAlbCustom);
                if (oldProvAlbCabOfProvAlbCustomCollectionProvAlbCustom != null) {
                    oldProvAlbCabOfProvAlbCustomCollectionProvAlbCustom.getProvAlbCustomCollection().remove(provAlbCustomCollectionProvAlbCustom);
                    oldProvAlbCabOfProvAlbCustomCollectionProvAlbCustom = em.merge(oldProvAlbCabOfProvAlbCustomCollectionProvAlbCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProvAlbCab provAlbCab) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvAlbCab persistentProvAlbCab = em.find(ProvAlbCab.class, provAlbCab.getId());
            Usuarios usuarioOld = persistentProvAlbCab.getUsuario();
            Usuarios usuarioNew = provAlbCab.getUsuario();
            Proveedores codProvOld = persistentProvAlbCab.getCodProv();
            Proveedores codProvNew = provAlbCab.getCodProv();
            Empresas codEmpOld = persistentProvAlbCab.getCodEmp();
            Empresas codEmpNew = provAlbCab.getCodEmp();
            ProvPedidosCab codPedidoOld = persistentProvAlbCab.getCodPedido();
            ProvPedidosCab codPedidoNew = provAlbCab.getCodPedido();
            Collection<ProvFacCab> provFacCabCollectionOld = persistentProvAlbCab.getProvFacCabCollection();
            Collection<ProvFacCab> provFacCabCollectionNew = provAlbCab.getProvFacCabCollection();
            Collection<ProvAlbLin> provAlbLinCollectionOld = persistentProvAlbCab.getProvAlbLinCollection();
            Collection<ProvAlbLin> provAlbLinCollectionNew = provAlbCab.getProvAlbLinCollection();
            Collection<ProvAlbCustom> provAlbCustomCollectionOld = persistentProvAlbCab.getProvAlbCustomCollection();
            Collection<ProvAlbCustom> provAlbCustomCollectionNew = provAlbCab.getProvAlbCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (ProvAlbLin provAlbLinCollectionOldProvAlbLin : provAlbLinCollectionOld) {
                if (!provAlbLinCollectionNew.contains(provAlbLinCollectionOldProvAlbLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvAlbLin " + provAlbLinCollectionOldProvAlbLin + " since its codCab field is not nullable.");
                }
            }
            for (ProvAlbCustom provAlbCustomCollectionOldProvAlbCustom : provAlbCustomCollectionOld) {
                if (!provAlbCustomCollectionNew.contains(provAlbCustomCollectionOldProvAlbCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvAlbCustom " + provAlbCustomCollectionOldProvAlbCustom + " since its provAlbCab field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                provAlbCab.setUsuario(usuarioNew);
            }
            if (codProvNew != null) {
                codProvNew = em.getReference(codProvNew.getClass(), codProvNew.getCodigo());
                provAlbCab.setCodProv(codProvNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                provAlbCab.setCodEmp(codEmpNew);
            }
            if (codPedidoNew != null) {
                codPedidoNew = em.getReference(codPedidoNew.getClass(), codPedidoNew.getId());
                provAlbCab.setCodPedido(codPedidoNew);
            }
            Collection<ProvFacCab> attachedProvFacCabCollectionNew = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionNewProvFacCabToAttach : provFacCabCollectionNew) {
                provFacCabCollectionNewProvFacCabToAttach = em.getReference(provFacCabCollectionNewProvFacCabToAttach.getClass(), provFacCabCollectionNewProvFacCabToAttach.getId());
                attachedProvFacCabCollectionNew.add(provFacCabCollectionNewProvFacCabToAttach);
            }
            provFacCabCollectionNew = attachedProvFacCabCollectionNew;
            provAlbCab.setProvFacCabCollection(provFacCabCollectionNew);
            Collection<ProvAlbLin> attachedProvAlbLinCollectionNew = new ArrayList<ProvAlbLin>();
            for (ProvAlbLin provAlbLinCollectionNewProvAlbLinToAttach : provAlbLinCollectionNew) {
                provAlbLinCollectionNewProvAlbLinToAttach = em.getReference(provAlbLinCollectionNewProvAlbLinToAttach.getClass(), provAlbLinCollectionNewProvAlbLinToAttach.getId());
                attachedProvAlbLinCollectionNew.add(provAlbLinCollectionNewProvAlbLinToAttach);
            }
            provAlbLinCollectionNew = attachedProvAlbLinCollectionNew;
            provAlbCab.setProvAlbLinCollection(provAlbLinCollectionNew);
            Collection<ProvAlbCustom> attachedProvAlbCustomCollectionNew = new ArrayList<ProvAlbCustom>();
            for (ProvAlbCustom provAlbCustomCollectionNewProvAlbCustomToAttach : provAlbCustomCollectionNew) {
                provAlbCustomCollectionNewProvAlbCustomToAttach = em.getReference(provAlbCustomCollectionNewProvAlbCustomToAttach.getClass(), provAlbCustomCollectionNewProvAlbCustomToAttach.getProvAlbCustomPK());
                attachedProvAlbCustomCollectionNew.add(provAlbCustomCollectionNewProvAlbCustomToAttach);
            }
            provAlbCustomCollectionNew = attachedProvAlbCustomCollectionNew;
            provAlbCab.setProvAlbCustomCollection(provAlbCustomCollectionNew);
            provAlbCab = em.merge(provAlbCab);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getProvAlbCabCollection().remove(provAlbCab);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getProvAlbCabCollection().add(provAlbCab);
                usuarioNew = em.merge(usuarioNew);
            }
            if (codProvOld != null && !codProvOld.equals(codProvNew)) {
                codProvOld.getProvAlbCabCollection().remove(provAlbCab);
                codProvOld = em.merge(codProvOld);
            }
            if (codProvNew != null && !codProvNew.equals(codProvOld)) {
                codProvNew.getProvAlbCabCollection().add(provAlbCab);
                codProvNew = em.merge(codProvNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getProvAlbCabCollection().remove(provAlbCab);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getProvAlbCabCollection().add(provAlbCab);
                codEmpNew = em.merge(codEmpNew);
            }
            if (codPedidoOld != null && !codPedidoOld.equals(codPedidoNew)) {
                codPedidoOld.getProvAlbCabCollection().remove(provAlbCab);
                codPedidoOld = em.merge(codPedidoOld);
            }
            if (codPedidoNew != null && !codPedidoNew.equals(codPedidoOld)) {
                codPedidoNew.getProvAlbCabCollection().add(provAlbCab);
                codPedidoNew = em.merge(codPedidoNew);
            }
            for (ProvFacCab provFacCabCollectionOldProvFacCab : provFacCabCollectionOld) {
                if (!provFacCabCollectionNew.contains(provFacCabCollectionOldProvFacCab)) {
                    provFacCabCollectionOldProvFacCab.setCodAlb(null);
                    provFacCabCollectionOldProvFacCab = em.merge(provFacCabCollectionOldProvFacCab);
                }
            }
            for (ProvFacCab provFacCabCollectionNewProvFacCab : provFacCabCollectionNew) {
                if (!provFacCabCollectionOld.contains(provFacCabCollectionNewProvFacCab)) {
                    ProvAlbCab oldCodAlbOfProvFacCabCollectionNewProvFacCab = provFacCabCollectionNewProvFacCab.getCodAlb();
                    provFacCabCollectionNewProvFacCab.setCodAlb(provAlbCab);
                    provFacCabCollectionNewProvFacCab = em.merge(provFacCabCollectionNewProvFacCab);
                    if (oldCodAlbOfProvFacCabCollectionNewProvFacCab != null && !oldCodAlbOfProvFacCabCollectionNewProvFacCab.equals(provAlbCab)) {
                        oldCodAlbOfProvFacCabCollectionNewProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionNewProvFacCab);
                        oldCodAlbOfProvFacCabCollectionNewProvFacCab = em.merge(oldCodAlbOfProvFacCabCollectionNewProvFacCab);
                    }
                }
            }
            for (ProvAlbLin provAlbLinCollectionNewProvAlbLin : provAlbLinCollectionNew) {
                if (!provAlbLinCollectionOld.contains(provAlbLinCollectionNewProvAlbLin)) {
                    ProvAlbCab oldCodCabOfProvAlbLinCollectionNewProvAlbLin = provAlbLinCollectionNewProvAlbLin.getCodCab();
                    provAlbLinCollectionNewProvAlbLin.setCodCab(provAlbCab);
                    provAlbLinCollectionNewProvAlbLin = em.merge(provAlbLinCollectionNewProvAlbLin);
                    if (oldCodCabOfProvAlbLinCollectionNewProvAlbLin != null && !oldCodCabOfProvAlbLinCollectionNewProvAlbLin.equals(provAlbCab)) {
                        oldCodCabOfProvAlbLinCollectionNewProvAlbLin.getProvAlbLinCollection().remove(provAlbLinCollectionNewProvAlbLin);
                        oldCodCabOfProvAlbLinCollectionNewProvAlbLin = em.merge(oldCodCabOfProvAlbLinCollectionNewProvAlbLin);
                    }
                }
            }
            for (ProvAlbCustom provAlbCustomCollectionNewProvAlbCustom : provAlbCustomCollectionNew) {
                if (!provAlbCustomCollectionOld.contains(provAlbCustomCollectionNewProvAlbCustom)) {
                    ProvAlbCab oldProvAlbCabOfProvAlbCustomCollectionNewProvAlbCustom = provAlbCustomCollectionNewProvAlbCustom.getProvAlbCab();
                    provAlbCustomCollectionNewProvAlbCustom.setProvAlbCab(provAlbCab);
                    provAlbCustomCollectionNewProvAlbCustom = em.merge(provAlbCustomCollectionNewProvAlbCustom);
                    if (oldProvAlbCabOfProvAlbCustomCollectionNewProvAlbCustom != null && !oldProvAlbCabOfProvAlbCustomCollectionNewProvAlbCustom.equals(provAlbCab)) {
                        oldProvAlbCabOfProvAlbCustomCollectionNewProvAlbCustom.getProvAlbCustomCollection().remove(provAlbCustomCollectionNewProvAlbCustom);
                        oldProvAlbCabOfProvAlbCustomCollectionNewProvAlbCustom = em.merge(oldProvAlbCabOfProvAlbCustomCollectionNewProvAlbCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provAlbCab.getId();
                if (findProvAlbCab(id) == null) {
                    throw new NonexistentEntityException("The provAlbCab with id " + id + " no longer exists.");
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
            ProvAlbCab provAlbCab;
            try {
                provAlbCab = em.getReference(ProvAlbCab.class, id);
                provAlbCab.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provAlbCab with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProvAlbLin> provAlbLinCollectionOrphanCheck = provAlbCab.getProvAlbLinCollection();
            for (ProvAlbLin provAlbLinCollectionOrphanCheckProvAlbLin : provAlbLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProvAlbCab (" + provAlbCab + ") cannot be destroyed since the ProvAlbLin " + provAlbLinCollectionOrphanCheckProvAlbLin + " in its provAlbLinCollection field has a non-nullable codCab field.");
            }
            Collection<ProvAlbCustom> provAlbCustomCollectionOrphanCheck = provAlbCab.getProvAlbCustomCollection();
            for (ProvAlbCustom provAlbCustomCollectionOrphanCheckProvAlbCustom : provAlbCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProvAlbCab (" + provAlbCab + ") cannot be destroyed since the ProvAlbCustom " + provAlbCustomCollectionOrphanCheckProvAlbCustom + " in its provAlbCustomCollection field has a non-nullable provAlbCab field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios usuario = provAlbCab.getUsuario();
            if (usuario != null) {
                usuario.getProvAlbCabCollection().remove(provAlbCab);
                usuario = em.merge(usuario);
            }
            Proveedores codProv = provAlbCab.getCodProv();
            if (codProv != null) {
                codProv.getProvAlbCabCollection().remove(provAlbCab);
                codProv = em.merge(codProv);
            }
            Empresas codEmp = provAlbCab.getCodEmp();
            if (codEmp != null) {
                codEmp.getProvAlbCabCollection().remove(provAlbCab);
                codEmp = em.merge(codEmp);
            }
            ProvPedidosCab codPedido = provAlbCab.getCodPedido();
            if (codPedido != null) {
                codPedido.getProvAlbCabCollection().remove(provAlbCab);
                codPedido = em.merge(codPedido);
            }
            Collection<ProvFacCab> provFacCabCollection = provAlbCab.getProvFacCabCollection();
            for (ProvFacCab provFacCabCollectionProvFacCab : provFacCabCollection) {
                provFacCabCollectionProvFacCab.setCodAlb(null);
                provFacCabCollectionProvFacCab = em.merge(provFacCabCollectionProvFacCab);
            }
            em.remove(provAlbCab);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProvAlbCab> findProvAlbCabEntities() {
        return findProvAlbCabEntities(true, -1, -1);
    }

    public List<ProvAlbCab> findProvAlbCabEntities(int maxResults, int firstResult) {
        return findProvAlbCabEntities(false, maxResults, firstResult);
    }

    private List<ProvAlbCab> findProvAlbCabEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProvAlbCab.class));
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

    public ProvAlbCab findProvAlbCab(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProvAlbCab.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvAlbCabCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProvAlbCab> rt = cq.from(ProvAlbCab.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
