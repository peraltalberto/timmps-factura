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
import es.timmps.fac.persistencia.pojos.Proveedores;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.ProvAlbCab;
import es.timmps.fac.persistencia.pojos.ProvFacCab;
import es.timmps.fac.persistencia.pojos.ProvPedidosCab;
import es.timmps.fac.persistencia.pojos.Usuarios;
import es.timmps.fac.persistencia.pojos.ProvFacLin;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.pojos.ProvFacCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class ProvFacCabJpaController implements Serializable {

    public ProvFacCabJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProvFacCab provFacCab) {
        if (provFacCab.getProvFacLinCollection() == null) {
            provFacCab.setProvFacLinCollection(new ArrayList<ProvFacLin>());
        }
        if (provFacCab.getProvFacCustomCollection() == null) {
            provFacCab.setProvFacCustomCollection(new ArrayList<ProvFacCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedores codProv = provFacCab.getCodProv();
            if (codProv != null) {
                codProv = em.getReference(codProv.getClass(), codProv.getCodigo());
                provFacCab.setCodProv(codProv);
            }
            Empresas codEmp = provFacCab.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                provFacCab.setCodEmp(codEmp);
            }
            ProvAlbCab codAlb = provFacCab.getCodAlb();
            if (codAlb != null) {
                codAlb = em.getReference(codAlb.getClass(), codAlb.getId());
                provFacCab.setCodAlb(codAlb);
            }
            ProvPedidosCab codPedido = provFacCab.getCodPedido();
            if (codPedido != null) {
                codPedido = em.getReference(codPedido.getClass(), codPedido.getId());
                provFacCab.setCodPedido(codPedido);
            }
            Usuarios usuario = provFacCab.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                provFacCab.setUsuario(usuario);
            }
            Collection<ProvFacLin> attachedProvFacLinCollection = new ArrayList<ProvFacLin>();
            for (ProvFacLin provFacLinCollectionProvFacLinToAttach : provFacCab.getProvFacLinCollection()) {
                provFacLinCollectionProvFacLinToAttach = em.getReference(provFacLinCollectionProvFacLinToAttach.getClass(), provFacLinCollectionProvFacLinToAttach.getId());
                attachedProvFacLinCollection.add(provFacLinCollectionProvFacLinToAttach);
            }
            provFacCab.setProvFacLinCollection(attachedProvFacLinCollection);
            Collection<ProvFacCustom> attachedProvFacCustomCollection = new ArrayList<ProvFacCustom>();
            for (ProvFacCustom provFacCustomCollectionProvFacCustomToAttach : provFacCab.getProvFacCustomCollection()) {
                provFacCustomCollectionProvFacCustomToAttach = em.getReference(provFacCustomCollectionProvFacCustomToAttach.getClass(), provFacCustomCollectionProvFacCustomToAttach.getProvFacCustomPK());
                attachedProvFacCustomCollection.add(provFacCustomCollectionProvFacCustomToAttach);
            }
            provFacCab.setProvFacCustomCollection(attachedProvFacCustomCollection);
            em.persist(provFacCab);
            if (codProv != null) {
                codProv.getProvFacCabCollection().add(provFacCab);
                codProv = em.merge(codProv);
            }
            if (codEmp != null) {
                codEmp.getProvFacCabCollection().add(provFacCab);
                codEmp = em.merge(codEmp);
            }
            if (codAlb != null) {
                codAlb.getProvFacCabCollection().add(provFacCab);
                codAlb = em.merge(codAlb);
            }
            if (codPedido != null) {
                codPedido.getProvFacCabCollection().add(provFacCab);
                codPedido = em.merge(codPedido);
            }
            if (usuario != null) {
                usuario.getProvFacCabCollection().add(provFacCab);
                usuario = em.merge(usuario);
            }
            for (ProvFacLin provFacLinCollectionProvFacLin : provFacCab.getProvFacLinCollection()) {
                ProvFacCab oldCodCabOfProvFacLinCollectionProvFacLin = provFacLinCollectionProvFacLin.getCodCab();
                provFacLinCollectionProvFacLin.setCodCab(provFacCab);
                provFacLinCollectionProvFacLin = em.merge(provFacLinCollectionProvFacLin);
                if (oldCodCabOfProvFacLinCollectionProvFacLin != null) {
                    oldCodCabOfProvFacLinCollectionProvFacLin.getProvFacLinCollection().remove(provFacLinCollectionProvFacLin);
                    oldCodCabOfProvFacLinCollectionProvFacLin = em.merge(oldCodCabOfProvFacLinCollectionProvFacLin);
                }
            }
            for (ProvFacCustom provFacCustomCollectionProvFacCustom : provFacCab.getProvFacCustomCollection()) {
                ProvFacCab oldProvFacCabOfProvFacCustomCollectionProvFacCustom = provFacCustomCollectionProvFacCustom.getProvFacCab();
                provFacCustomCollectionProvFacCustom.setProvFacCab(provFacCab);
                provFacCustomCollectionProvFacCustom = em.merge(provFacCustomCollectionProvFacCustom);
                if (oldProvFacCabOfProvFacCustomCollectionProvFacCustom != null) {
                    oldProvFacCabOfProvFacCustomCollectionProvFacCustom.getProvFacCustomCollection().remove(provFacCustomCollectionProvFacCustom);
                    oldProvFacCabOfProvFacCustomCollectionProvFacCustom = em.merge(oldProvFacCabOfProvFacCustomCollectionProvFacCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProvFacCab provFacCab) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvFacCab persistentProvFacCab = em.find(ProvFacCab.class, provFacCab.getId());
            Proveedores codProvOld = persistentProvFacCab.getCodProv();
            Proveedores codProvNew = provFacCab.getCodProv();
            Empresas codEmpOld = persistentProvFacCab.getCodEmp();
            Empresas codEmpNew = provFacCab.getCodEmp();
            ProvAlbCab codAlbOld = persistentProvFacCab.getCodAlb();
            ProvAlbCab codAlbNew = provFacCab.getCodAlb();
            ProvPedidosCab codPedidoOld = persistentProvFacCab.getCodPedido();
            ProvPedidosCab codPedidoNew = provFacCab.getCodPedido();
            Usuarios usuarioOld = persistentProvFacCab.getUsuario();
            Usuarios usuarioNew = provFacCab.getUsuario();
            Collection<ProvFacLin> provFacLinCollectionOld = persistentProvFacCab.getProvFacLinCollection();
            Collection<ProvFacLin> provFacLinCollectionNew = provFacCab.getProvFacLinCollection();
            Collection<ProvFacCustom> provFacCustomCollectionOld = persistentProvFacCab.getProvFacCustomCollection();
            Collection<ProvFacCustom> provFacCustomCollectionNew = provFacCab.getProvFacCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (ProvFacLin provFacLinCollectionOldProvFacLin : provFacLinCollectionOld) {
                if (!provFacLinCollectionNew.contains(provFacLinCollectionOldProvFacLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvFacLin " + provFacLinCollectionOldProvFacLin + " since its codCab field is not nullable.");
                }
            }
            for (ProvFacCustom provFacCustomCollectionOldProvFacCustom : provFacCustomCollectionOld) {
                if (!provFacCustomCollectionNew.contains(provFacCustomCollectionOldProvFacCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvFacCustom " + provFacCustomCollectionOldProvFacCustom + " since its provFacCab field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codProvNew != null) {
                codProvNew = em.getReference(codProvNew.getClass(), codProvNew.getCodigo());
                provFacCab.setCodProv(codProvNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                provFacCab.setCodEmp(codEmpNew);
            }
            if (codAlbNew != null) {
                codAlbNew = em.getReference(codAlbNew.getClass(), codAlbNew.getId());
                provFacCab.setCodAlb(codAlbNew);
            }
            if (codPedidoNew != null) {
                codPedidoNew = em.getReference(codPedidoNew.getClass(), codPedidoNew.getId());
                provFacCab.setCodPedido(codPedidoNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                provFacCab.setUsuario(usuarioNew);
            }
            Collection<ProvFacLin> attachedProvFacLinCollectionNew = new ArrayList<ProvFacLin>();
            for (ProvFacLin provFacLinCollectionNewProvFacLinToAttach : provFacLinCollectionNew) {
                provFacLinCollectionNewProvFacLinToAttach = em.getReference(provFacLinCollectionNewProvFacLinToAttach.getClass(), provFacLinCollectionNewProvFacLinToAttach.getId());
                attachedProvFacLinCollectionNew.add(provFacLinCollectionNewProvFacLinToAttach);
            }
            provFacLinCollectionNew = attachedProvFacLinCollectionNew;
            provFacCab.setProvFacLinCollection(provFacLinCollectionNew);
            Collection<ProvFacCustom> attachedProvFacCustomCollectionNew = new ArrayList<ProvFacCustom>();
            for (ProvFacCustom provFacCustomCollectionNewProvFacCustomToAttach : provFacCustomCollectionNew) {
                provFacCustomCollectionNewProvFacCustomToAttach = em.getReference(provFacCustomCollectionNewProvFacCustomToAttach.getClass(), provFacCustomCollectionNewProvFacCustomToAttach.getProvFacCustomPK());
                attachedProvFacCustomCollectionNew.add(provFacCustomCollectionNewProvFacCustomToAttach);
            }
            provFacCustomCollectionNew = attachedProvFacCustomCollectionNew;
            provFacCab.setProvFacCustomCollection(provFacCustomCollectionNew);
            provFacCab = em.merge(provFacCab);
            if (codProvOld != null && !codProvOld.equals(codProvNew)) {
                codProvOld.getProvFacCabCollection().remove(provFacCab);
                codProvOld = em.merge(codProvOld);
            }
            if (codProvNew != null && !codProvNew.equals(codProvOld)) {
                codProvNew.getProvFacCabCollection().add(provFacCab);
                codProvNew = em.merge(codProvNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getProvFacCabCollection().remove(provFacCab);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getProvFacCabCollection().add(provFacCab);
                codEmpNew = em.merge(codEmpNew);
            }
            if (codAlbOld != null && !codAlbOld.equals(codAlbNew)) {
                codAlbOld.getProvFacCabCollection().remove(provFacCab);
                codAlbOld = em.merge(codAlbOld);
            }
            if (codAlbNew != null && !codAlbNew.equals(codAlbOld)) {
                codAlbNew.getProvFacCabCollection().add(provFacCab);
                codAlbNew = em.merge(codAlbNew);
            }
            if (codPedidoOld != null && !codPedidoOld.equals(codPedidoNew)) {
                codPedidoOld.getProvFacCabCollection().remove(provFacCab);
                codPedidoOld = em.merge(codPedidoOld);
            }
            if (codPedidoNew != null && !codPedidoNew.equals(codPedidoOld)) {
                codPedidoNew.getProvFacCabCollection().add(provFacCab);
                codPedidoNew = em.merge(codPedidoNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getProvFacCabCollection().remove(provFacCab);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getProvFacCabCollection().add(provFacCab);
                usuarioNew = em.merge(usuarioNew);
            }
            for (ProvFacLin provFacLinCollectionNewProvFacLin : provFacLinCollectionNew) {
                if (!provFacLinCollectionOld.contains(provFacLinCollectionNewProvFacLin)) {
                    ProvFacCab oldCodCabOfProvFacLinCollectionNewProvFacLin = provFacLinCollectionNewProvFacLin.getCodCab();
                    provFacLinCollectionNewProvFacLin.setCodCab(provFacCab);
                    provFacLinCollectionNewProvFacLin = em.merge(provFacLinCollectionNewProvFacLin);
                    if (oldCodCabOfProvFacLinCollectionNewProvFacLin != null && !oldCodCabOfProvFacLinCollectionNewProvFacLin.equals(provFacCab)) {
                        oldCodCabOfProvFacLinCollectionNewProvFacLin.getProvFacLinCollection().remove(provFacLinCollectionNewProvFacLin);
                        oldCodCabOfProvFacLinCollectionNewProvFacLin = em.merge(oldCodCabOfProvFacLinCollectionNewProvFacLin);
                    }
                }
            }
            for (ProvFacCustom provFacCustomCollectionNewProvFacCustom : provFacCustomCollectionNew) {
                if (!provFacCustomCollectionOld.contains(provFacCustomCollectionNewProvFacCustom)) {
                    ProvFacCab oldProvFacCabOfProvFacCustomCollectionNewProvFacCustom = provFacCustomCollectionNewProvFacCustom.getProvFacCab();
                    provFacCustomCollectionNewProvFacCustom.setProvFacCab(provFacCab);
                    provFacCustomCollectionNewProvFacCustom = em.merge(provFacCustomCollectionNewProvFacCustom);
                    if (oldProvFacCabOfProvFacCustomCollectionNewProvFacCustom != null && !oldProvFacCabOfProvFacCustomCollectionNewProvFacCustom.equals(provFacCab)) {
                        oldProvFacCabOfProvFacCustomCollectionNewProvFacCustom.getProvFacCustomCollection().remove(provFacCustomCollectionNewProvFacCustom);
                        oldProvFacCabOfProvFacCustomCollectionNewProvFacCustom = em.merge(oldProvFacCabOfProvFacCustomCollectionNewProvFacCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provFacCab.getId();
                if (findProvFacCab(id) == null) {
                    throw new NonexistentEntityException("The provFacCab with id " + id + " no longer exists.");
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
            ProvFacCab provFacCab;
            try {
                provFacCab = em.getReference(ProvFacCab.class, id);
                provFacCab.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provFacCab with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProvFacLin> provFacLinCollectionOrphanCheck = provFacCab.getProvFacLinCollection();
            for (ProvFacLin provFacLinCollectionOrphanCheckProvFacLin : provFacLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProvFacCab (" + provFacCab + ") cannot be destroyed since the ProvFacLin " + provFacLinCollectionOrphanCheckProvFacLin + " in its provFacLinCollection field has a non-nullable codCab field.");
            }
            Collection<ProvFacCustom> provFacCustomCollectionOrphanCheck = provFacCab.getProvFacCustomCollection();
            for (ProvFacCustom provFacCustomCollectionOrphanCheckProvFacCustom : provFacCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProvFacCab (" + provFacCab + ") cannot be destroyed since the ProvFacCustom " + provFacCustomCollectionOrphanCheckProvFacCustom + " in its provFacCustomCollection field has a non-nullable provFacCab field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proveedores codProv = provFacCab.getCodProv();
            if (codProv != null) {
                codProv.getProvFacCabCollection().remove(provFacCab);
                codProv = em.merge(codProv);
            }
            Empresas codEmp = provFacCab.getCodEmp();
            if (codEmp != null) {
                codEmp.getProvFacCabCollection().remove(provFacCab);
                codEmp = em.merge(codEmp);
            }
            ProvAlbCab codAlb = provFacCab.getCodAlb();
            if (codAlb != null) {
                codAlb.getProvFacCabCollection().remove(provFacCab);
                codAlb = em.merge(codAlb);
            }
            ProvPedidosCab codPedido = provFacCab.getCodPedido();
            if (codPedido != null) {
                codPedido.getProvFacCabCollection().remove(provFacCab);
                codPedido = em.merge(codPedido);
            }
            Usuarios usuario = provFacCab.getUsuario();
            if (usuario != null) {
                usuario.getProvFacCabCollection().remove(provFacCab);
                usuario = em.merge(usuario);
            }
            em.remove(provFacCab);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProvFacCab> findProvFacCabEntities() {
        return findProvFacCabEntities(true, -1, -1);
    }

    public List<ProvFacCab> findProvFacCabEntities(int maxResults, int firstResult) {
        return findProvFacCabEntities(false, maxResults, firstResult);
    }

    private List<ProvFacCab> findProvFacCabEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProvFacCab.class));
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

    public ProvFacCab findProvFacCab(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProvFacCab.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvFacCabCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProvFacCab> rt = cq.from(ProvFacCab.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
