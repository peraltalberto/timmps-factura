/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Proveedores;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.fac.persistencia.ProvPedidosCustom;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.ProvPedidosLin;
import es.timmps.fac.persistencia.ProvFacCab;
import es.timmps.fac.persistencia.ProvAlbCab;
import es.timmps.fac.persistencia.ProvPedidosCab;
import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class ProvPedidosCabJpaController implements Serializable {

    public ProvPedidosCabJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProvPedidosCab provPedidosCab) {
        if (provPedidosCab.getProvPedidosCustomCollection() == null) {
            provPedidosCab.setProvPedidosCustomCollection(new ArrayList<ProvPedidosCustom>());
        }
        if (provPedidosCab.getProvPedidosLinCollection() == null) {
            provPedidosCab.setProvPedidosLinCollection(new ArrayList<ProvPedidosLin>());
        }
        if (provPedidosCab.getProvFacCabCollection() == null) {
            provPedidosCab.setProvFacCabCollection(new ArrayList<ProvFacCab>());
        }
        if (provPedidosCab.getProvAlbCabCollection() == null) {
            provPedidosCab.setProvAlbCabCollection(new ArrayList<ProvAlbCab>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedores codProv = provPedidosCab.getCodProv();
            if (codProv != null) {
                codProv = em.getReference(codProv.getClass(), codProv.getCodigo());
                provPedidosCab.setCodProv(codProv);
            }
            Empresas codEmp = provPedidosCab.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                provPedidosCab.setCodEmp(codEmp);
            }
            Usuarios usuario = provPedidosCab.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                provPedidosCab.setUsuario(usuario);
            }
            Collection<ProvPedidosCustom> attachedProvPedidosCustomCollection = new ArrayList<ProvPedidosCustom>();
            for (ProvPedidosCustom provPedidosCustomCollectionProvPedidosCustomToAttach : provPedidosCab.getProvPedidosCustomCollection()) {
                provPedidosCustomCollectionProvPedidosCustomToAttach = em.getReference(provPedidosCustomCollectionProvPedidosCustomToAttach.getClass(), provPedidosCustomCollectionProvPedidosCustomToAttach.getProvPedidosCustomPK());
                attachedProvPedidosCustomCollection.add(provPedidosCustomCollectionProvPedidosCustomToAttach);
            }
            provPedidosCab.setProvPedidosCustomCollection(attachedProvPedidosCustomCollection);
            Collection<ProvPedidosLin> attachedProvPedidosLinCollection = new ArrayList<ProvPedidosLin>();
            for (ProvPedidosLin provPedidosLinCollectionProvPedidosLinToAttach : provPedidosCab.getProvPedidosLinCollection()) {
                provPedidosLinCollectionProvPedidosLinToAttach = em.getReference(provPedidosLinCollectionProvPedidosLinToAttach.getClass(), provPedidosLinCollectionProvPedidosLinToAttach.getId());
                attachedProvPedidosLinCollection.add(provPedidosLinCollectionProvPedidosLinToAttach);
            }
            provPedidosCab.setProvPedidosLinCollection(attachedProvPedidosLinCollection);
            Collection<ProvFacCab> attachedProvFacCabCollection = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionProvFacCabToAttach : provPedidosCab.getProvFacCabCollection()) {
                provFacCabCollectionProvFacCabToAttach = em.getReference(provFacCabCollectionProvFacCabToAttach.getClass(), provFacCabCollectionProvFacCabToAttach.getId());
                attachedProvFacCabCollection.add(provFacCabCollectionProvFacCabToAttach);
            }
            provPedidosCab.setProvFacCabCollection(attachedProvFacCabCollection);
            Collection<ProvAlbCab> attachedProvAlbCabCollection = new ArrayList<ProvAlbCab>();
            for (ProvAlbCab provAlbCabCollectionProvAlbCabToAttach : provPedidosCab.getProvAlbCabCollection()) {
                provAlbCabCollectionProvAlbCabToAttach = em.getReference(provAlbCabCollectionProvAlbCabToAttach.getClass(), provAlbCabCollectionProvAlbCabToAttach.getId());
                attachedProvAlbCabCollection.add(provAlbCabCollectionProvAlbCabToAttach);
            }
            provPedidosCab.setProvAlbCabCollection(attachedProvAlbCabCollection);
            em.persist(provPedidosCab);
            if (codProv != null) {
                codProv.getProvPedidosCabCollection().add(provPedidosCab);
                codProv = em.merge(codProv);
            }
            if (codEmp != null) {
                codEmp.getProvPedidosCabCollection().add(provPedidosCab);
                codEmp = em.merge(codEmp);
            }
            if (usuario != null) {
                usuario.getProvPedidosCabCollection().add(provPedidosCab);
                usuario = em.merge(usuario);
            }
            for (ProvPedidosCustom provPedidosCustomCollectionProvPedidosCustom : provPedidosCab.getProvPedidosCustomCollection()) {
                ProvPedidosCab oldProvPedidosCabOfProvPedidosCustomCollectionProvPedidosCustom = provPedidosCustomCollectionProvPedidosCustom.getProvPedidosCab();
                provPedidosCustomCollectionProvPedidosCustom.setProvPedidosCab(provPedidosCab);
                provPedidosCustomCollectionProvPedidosCustom = em.merge(provPedidosCustomCollectionProvPedidosCustom);
                if (oldProvPedidosCabOfProvPedidosCustomCollectionProvPedidosCustom != null) {
                    oldProvPedidosCabOfProvPedidosCustomCollectionProvPedidosCustom.getProvPedidosCustomCollection().remove(provPedidosCustomCollectionProvPedidosCustom);
                    oldProvPedidosCabOfProvPedidosCustomCollectionProvPedidosCustom = em.merge(oldProvPedidosCabOfProvPedidosCustomCollectionProvPedidosCustom);
                }
            }
            for (ProvPedidosLin provPedidosLinCollectionProvPedidosLin : provPedidosCab.getProvPedidosLinCollection()) {
                ProvPedidosCab oldCodCabOfProvPedidosLinCollectionProvPedidosLin = provPedidosLinCollectionProvPedidosLin.getCodCab();
                provPedidosLinCollectionProvPedidosLin.setCodCab(provPedidosCab);
                provPedidosLinCollectionProvPedidosLin = em.merge(provPedidosLinCollectionProvPedidosLin);
                if (oldCodCabOfProvPedidosLinCollectionProvPedidosLin != null) {
                    oldCodCabOfProvPedidosLinCollectionProvPedidosLin.getProvPedidosLinCollection().remove(provPedidosLinCollectionProvPedidosLin);
                    oldCodCabOfProvPedidosLinCollectionProvPedidosLin = em.merge(oldCodCabOfProvPedidosLinCollectionProvPedidosLin);
                }
            }
            for (ProvFacCab provFacCabCollectionProvFacCab : provPedidosCab.getProvFacCabCollection()) {
                ProvPedidosCab oldCodPedidoOfProvFacCabCollectionProvFacCab = provFacCabCollectionProvFacCab.getCodPedido();
                provFacCabCollectionProvFacCab.setCodPedido(provPedidosCab);
                provFacCabCollectionProvFacCab = em.merge(provFacCabCollectionProvFacCab);
                if (oldCodPedidoOfProvFacCabCollectionProvFacCab != null) {
                    oldCodPedidoOfProvFacCabCollectionProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionProvFacCab);
                    oldCodPedidoOfProvFacCabCollectionProvFacCab = em.merge(oldCodPedidoOfProvFacCabCollectionProvFacCab);
                }
            }
            for (ProvAlbCab provAlbCabCollectionProvAlbCab : provPedidosCab.getProvAlbCabCollection()) {
                ProvPedidosCab oldCodPedidoOfProvAlbCabCollectionProvAlbCab = provAlbCabCollectionProvAlbCab.getCodPedido();
                provAlbCabCollectionProvAlbCab.setCodPedido(provPedidosCab);
                provAlbCabCollectionProvAlbCab = em.merge(provAlbCabCollectionProvAlbCab);
                if (oldCodPedidoOfProvAlbCabCollectionProvAlbCab != null) {
                    oldCodPedidoOfProvAlbCabCollectionProvAlbCab.getProvAlbCabCollection().remove(provAlbCabCollectionProvAlbCab);
                    oldCodPedidoOfProvAlbCabCollectionProvAlbCab = em.merge(oldCodPedidoOfProvAlbCabCollectionProvAlbCab);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProvPedidosCab provPedidosCab) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProvPedidosCab persistentProvPedidosCab = em.find(ProvPedidosCab.class, provPedidosCab.getId());
            Proveedores codProvOld = persistentProvPedidosCab.getCodProv();
            Proveedores codProvNew = provPedidosCab.getCodProv();
            Empresas codEmpOld = persistentProvPedidosCab.getCodEmp();
            Empresas codEmpNew = provPedidosCab.getCodEmp();
            Usuarios usuarioOld = persistentProvPedidosCab.getUsuario();
            Usuarios usuarioNew = provPedidosCab.getUsuario();
            Collection<ProvPedidosCustom> provPedidosCustomCollectionOld = persistentProvPedidosCab.getProvPedidosCustomCollection();
            Collection<ProvPedidosCustom> provPedidosCustomCollectionNew = provPedidosCab.getProvPedidosCustomCollection();
            Collection<ProvPedidosLin> provPedidosLinCollectionOld = persistentProvPedidosCab.getProvPedidosLinCollection();
            Collection<ProvPedidosLin> provPedidosLinCollectionNew = provPedidosCab.getProvPedidosLinCollection();
            Collection<ProvFacCab> provFacCabCollectionOld = persistentProvPedidosCab.getProvFacCabCollection();
            Collection<ProvFacCab> provFacCabCollectionNew = provPedidosCab.getProvFacCabCollection();
            Collection<ProvAlbCab> provAlbCabCollectionOld = persistentProvPedidosCab.getProvAlbCabCollection();
            Collection<ProvAlbCab> provAlbCabCollectionNew = provPedidosCab.getProvAlbCabCollection();
            List<String> illegalOrphanMessages = null;
            for (ProvPedidosCustom provPedidosCustomCollectionOldProvPedidosCustom : provPedidosCustomCollectionOld) {
                if (!provPedidosCustomCollectionNew.contains(provPedidosCustomCollectionOldProvPedidosCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvPedidosCustom " + provPedidosCustomCollectionOldProvPedidosCustom + " since its provPedidosCab field is not nullable.");
                }
            }
            for (ProvPedidosLin provPedidosLinCollectionOldProvPedidosLin : provPedidosLinCollectionOld) {
                if (!provPedidosLinCollectionNew.contains(provPedidosLinCollectionOldProvPedidosLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvPedidosLin " + provPedidosLinCollectionOldProvPedidosLin + " since its codCab field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codProvNew != null) {
                codProvNew = em.getReference(codProvNew.getClass(), codProvNew.getCodigo());
                provPedidosCab.setCodProv(codProvNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                provPedidosCab.setCodEmp(codEmpNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                provPedidosCab.setUsuario(usuarioNew);
            }
            Collection<ProvPedidosCustom> attachedProvPedidosCustomCollectionNew = new ArrayList<ProvPedidosCustom>();
            for (ProvPedidosCustom provPedidosCustomCollectionNewProvPedidosCustomToAttach : provPedidosCustomCollectionNew) {
                provPedidosCustomCollectionNewProvPedidosCustomToAttach = em.getReference(provPedidosCustomCollectionNewProvPedidosCustomToAttach.getClass(), provPedidosCustomCollectionNewProvPedidosCustomToAttach.getProvPedidosCustomPK());
                attachedProvPedidosCustomCollectionNew.add(provPedidosCustomCollectionNewProvPedidosCustomToAttach);
            }
            provPedidosCustomCollectionNew = attachedProvPedidosCustomCollectionNew;
            provPedidosCab.setProvPedidosCustomCollection(provPedidosCustomCollectionNew);
            Collection<ProvPedidosLin> attachedProvPedidosLinCollectionNew = new ArrayList<ProvPedidosLin>();
            for (ProvPedidosLin provPedidosLinCollectionNewProvPedidosLinToAttach : provPedidosLinCollectionNew) {
                provPedidosLinCollectionNewProvPedidosLinToAttach = em.getReference(provPedidosLinCollectionNewProvPedidosLinToAttach.getClass(), provPedidosLinCollectionNewProvPedidosLinToAttach.getId());
                attachedProvPedidosLinCollectionNew.add(provPedidosLinCollectionNewProvPedidosLinToAttach);
            }
            provPedidosLinCollectionNew = attachedProvPedidosLinCollectionNew;
            provPedidosCab.setProvPedidosLinCollection(provPedidosLinCollectionNew);
            Collection<ProvFacCab> attachedProvFacCabCollectionNew = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionNewProvFacCabToAttach : provFacCabCollectionNew) {
                provFacCabCollectionNewProvFacCabToAttach = em.getReference(provFacCabCollectionNewProvFacCabToAttach.getClass(), provFacCabCollectionNewProvFacCabToAttach.getId());
                attachedProvFacCabCollectionNew.add(provFacCabCollectionNewProvFacCabToAttach);
            }
            provFacCabCollectionNew = attachedProvFacCabCollectionNew;
            provPedidosCab.setProvFacCabCollection(provFacCabCollectionNew);
            Collection<ProvAlbCab> attachedProvAlbCabCollectionNew = new ArrayList<ProvAlbCab>();
            for (ProvAlbCab provAlbCabCollectionNewProvAlbCabToAttach : provAlbCabCollectionNew) {
                provAlbCabCollectionNewProvAlbCabToAttach = em.getReference(provAlbCabCollectionNewProvAlbCabToAttach.getClass(), provAlbCabCollectionNewProvAlbCabToAttach.getId());
                attachedProvAlbCabCollectionNew.add(provAlbCabCollectionNewProvAlbCabToAttach);
            }
            provAlbCabCollectionNew = attachedProvAlbCabCollectionNew;
            provPedidosCab.setProvAlbCabCollection(provAlbCabCollectionNew);
            provPedidosCab = em.merge(provPedidosCab);
            if (codProvOld != null && !codProvOld.equals(codProvNew)) {
                codProvOld.getProvPedidosCabCollection().remove(provPedidosCab);
                codProvOld = em.merge(codProvOld);
            }
            if (codProvNew != null && !codProvNew.equals(codProvOld)) {
                codProvNew.getProvPedidosCabCollection().add(provPedidosCab);
                codProvNew = em.merge(codProvNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getProvPedidosCabCollection().remove(provPedidosCab);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getProvPedidosCabCollection().add(provPedidosCab);
                codEmpNew = em.merge(codEmpNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getProvPedidosCabCollection().remove(provPedidosCab);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getProvPedidosCabCollection().add(provPedidosCab);
                usuarioNew = em.merge(usuarioNew);
            }
            for (ProvPedidosCustom provPedidosCustomCollectionNewProvPedidosCustom : provPedidosCustomCollectionNew) {
                if (!provPedidosCustomCollectionOld.contains(provPedidosCustomCollectionNewProvPedidosCustom)) {
                    ProvPedidosCab oldProvPedidosCabOfProvPedidosCustomCollectionNewProvPedidosCustom = provPedidosCustomCollectionNewProvPedidosCustom.getProvPedidosCab();
                    provPedidosCustomCollectionNewProvPedidosCustom.setProvPedidosCab(provPedidosCab);
                    provPedidosCustomCollectionNewProvPedidosCustom = em.merge(provPedidosCustomCollectionNewProvPedidosCustom);
                    if (oldProvPedidosCabOfProvPedidosCustomCollectionNewProvPedidosCustom != null && !oldProvPedidosCabOfProvPedidosCustomCollectionNewProvPedidosCustom.equals(provPedidosCab)) {
                        oldProvPedidosCabOfProvPedidosCustomCollectionNewProvPedidosCustom.getProvPedidosCustomCollection().remove(provPedidosCustomCollectionNewProvPedidosCustom);
                        oldProvPedidosCabOfProvPedidosCustomCollectionNewProvPedidosCustom = em.merge(oldProvPedidosCabOfProvPedidosCustomCollectionNewProvPedidosCustom);
                    }
                }
            }
            for (ProvPedidosLin provPedidosLinCollectionNewProvPedidosLin : provPedidosLinCollectionNew) {
                if (!provPedidosLinCollectionOld.contains(provPedidosLinCollectionNewProvPedidosLin)) {
                    ProvPedidosCab oldCodCabOfProvPedidosLinCollectionNewProvPedidosLin = provPedidosLinCollectionNewProvPedidosLin.getCodCab();
                    provPedidosLinCollectionNewProvPedidosLin.setCodCab(provPedidosCab);
                    provPedidosLinCollectionNewProvPedidosLin = em.merge(provPedidosLinCollectionNewProvPedidosLin);
                    if (oldCodCabOfProvPedidosLinCollectionNewProvPedidosLin != null && !oldCodCabOfProvPedidosLinCollectionNewProvPedidosLin.equals(provPedidosCab)) {
                        oldCodCabOfProvPedidosLinCollectionNewProvPedidosLin.getProvPedidosLinCollection().remove(provPedidosLinCollectionNewProvPedidosLin);
                        oldCodCabOfProvPedidosLinCollectionNewProvPedidosLin = em.merge(oldCodCabOfProvPedidosLinCollectionNewProvPedidosLin);
                    }
                }
            }
            for (ProvFacCab provFacCabCollectionOldProvFacCab : provFacCabCollectionOld) {
                if (!provFacCabCollectionNew.contains(provFacCabCollectionOldProvFacCab)) {
                    provFacCabCollectionOldProvFacCab.setCodPedido(null);
                    provFacCabCollectionOldProvFacCab = em.merge(provFacCabCollectionOldProvFacCab);
                }
            }
            for (ProvFacCab provFacCabCollectionNewProvFacCab : provFacCabCollectionNew) {
                if (!provFacCabCollectionOld.contains(provFacCabCollectionNewProvFacCab)) {
                    ProvPedidosCab oldCodPedidoOfProvFacCabCollectionNewProvFacCab = provFacCabCollectionNewProvFacCab.getCodPedido();
                    provFacCabCollectionNewProvFacCab.setCodPedido(provPedidosCab);
                    provFacCabCollectionNewProvFacCab = em.merge(provFacCabCollectionNewProvFacCab);
                    if (oldCodPedidoOfProvFacCabCollectionNewProvFacCab != null && !oldCodPedidoOfProvFacCabCollectionNewProvFacCab.equals(provPedidosCab)) {
                        oldCodPedidoOfProvFacCabCollectionNewProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionNewProvFacCab);
                        oldCodPedidoOfProvFacCabCollectionNewProvFacCab = em.merge(oldCodPedidoOfProvFacCabCollectionNewProvFacCab);
                    }
                }
            }
            for (ProvAlbCab provAlbCabCollectionOldProvAlbCab : provAlbCabCollectionOld) {
                if (!provAlbCabCollectionNew.contains(provAlbCabCollectionOldProvAlbCab)) {
                    provAlbCabCollectionOldProvAlbCab.setCodPedido(null);
                    provAlbCabCollectionOldProvAlbCab = em.merge(provAlbCabCollectionOldProvAlbCab);
                }
            }
            for (ProvAlbCab provAlbCabCollectionNewProvAlbCab : provAlbCabCollectionNew) {
                if (!provAlbCabCollectionOld.contains(provAlbCabCollectionNewProvAlbCab)) {
                    ProvPedidosCab oldCodPedidoOfProvAlbCabCollectionNewProvAlbCab = provAlbCabCollectionNewProvAlbCab.getCodPedido();
                    provAlbCabCollectionNewProvAlbCab.setCodPedido(provPedidosCab);
                    provAlbCabCollectionNewProvAlbCab = em.merge(provAlbCabCollectionNewProvAlbCab);
                    if (oldCodPedidoOfProvAlbCabCollectionNewProvAlbCab != null && !oldCodPedidoOfProvAlbCabCollectionNewProvAlbCab.equals(provPedidosCab)) {
                        oldCodPedidoOfProvAlbCabCollectionNewProvAlbCab.getProvAlbCabCollection().remove(provAlbCabCollectionNewProvAlbCab);
                        oldCodPedidoOfProvAlbCabCollectionNewProvAlbCab = em.merge(oldCodPedidoOfProvAlbCabCollectionNewProvAlbCab);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provPedidosCab.getId();
                if (findProvPedidosCab(id) == null) {
                    throw new NonexistentEntityException("The provPedidosCab with id " + id + " no longer exists.");
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
            ProvPedidosCab provPedidosCab;
            try {
                provPedidosCab = em.getReference(ProvPedidosCab.class, id);
                provPedidosCab.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provPedidosCab with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProvPedidosCustom> provPedidosCustomCollectionOrphanCheck = provPedidosCab.getProvPedidosCustomCollection();
            for (ProvPedidosCustom provPedidosCustomCollectionOrphanCheckProvPedidosCustom : provPedidosCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProvPedidosCab (" + provPedidosCab + ") cannot be destroyed since the ProvPedidosCustom " + provPedidosCustomCollectionOrphanCheckProvPedidosCustom + " in its provPedidosCustomCollection field has a non-nullable provPedidosCab field.");
            }
            Collection<ProvPedidosLin> provPedidosLinCollectionOrphanCheck = provPedidosCab.getProvPedidosLinCollection();
            for (ProvPedidosLin provPedidosLinCollectionOrphanCheckProvPedidosLin : provPedidosLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProvPedidosCab (" + provPedidosCab + ") cannot be destroyed since the ProvPedidosLin " + provPedidosLinCollectionOrphanCheckProvPedidosLin + " in its provPedidosLinCollection field has a non-nullable codCab field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proveedores codProv = provPedidosCab.getCodProv();
            if (codProv != null) {
                codProv.getProvPedidosCabCollection().remove(provPedidosCab);
                codProv = em.merge(codProv);
            }
            Empresas codEmp = provPedidosCab.getCodEmp();
            if (codEmp != null) {
                codEmp.getProvPedidosCabCollection().remove(provPedidosCab);
                codEmp = em.merge(codEmp);
            }
            Usuarios usuario = provPedidosCab.getUsuario();
            if (usuario != null) {
                usuario.getProvPedidosCabCollection().remove(provPedidosCab);
                usuario = em.merge(usuario);
            }
            Collection<ProvFacCab> provFacCabCollection = provPedidosCab.getProvFacCabCollection();
            for (ProvFacCab provFacCabCollectionProvFacCab : provFacCabCollection) {
                provFacCabCollectionProvFacCab.setCodPedido(null);
                provFacCabCollectionProvFacCab = em.merge(provFacCabCollectionProvFacCab);
            }
            Collection<ProvAlbCab> provAlbCabCollection = provPedidosCab.getProvAlbCabCollection();
            for (ProvAlbCab provAlbCabCollectionProvAlbCab : provAlbCabCollection) {
                provAlbCabCollectionProvAlbCab.setCodPedido(null);
                provAlbCabCollectionProvAlbCab = em.merge(provAlbCabCollectionProvAlbCab);
            }
            em.remove(provPedidosCab);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProvPedidosCab> findProvPedidosCabEntities() {
        return findProvPedidosCabEntities(true, -1, -1);
    }

    public List<ProvPedidosCab> findProvPedidosCabEntities(int maxResults, int firstResult) {
        return findProvPedidosCabEntities(false, maxResults, firstResult);
    }

    private List<ProvPedidosCab> findProvPedidosCabEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ProvPedidosCab as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ProvPedidosCab findProvPedidosCab(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProvPedidosCab.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvPedidosCabCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from ProvPedidosCab as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
