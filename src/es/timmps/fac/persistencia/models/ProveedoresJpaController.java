/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.DatosPersonales;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.ProvPedidosCab;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.PreciosCompras;
import es.timmps.fac.persistencia.ProvFacCab;
import es.timmps.fac.persistencia.ProvAlbCab;
import es.timmps.fac.persistencia.Proveedores;
import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class ProveedoresJpaController implements Serializable {

    public ProveedoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedores proveedores) throws PreexistingEntityException, Exception {
        if (proveedores.getProvPedidosCabCollection() == null) {
            proveedores.setProvPedidosCabCollection(new ArrayList<ProvPedidosCab>());
        }
        if (proveedores.getPreciosComprasCollection() == null) {
            proveedores.setPreciosComprasCollection(new ArrayList<PreciosCompras>());
        }
        if (proveedores.getProvFacCabCollection() == null) {
            proveedores.setProvFacCabCollection(new ArrayList<ProvFacCab>());
        }
        if (proveedores.getProvAlbCabCollection() == null) {
            proveedores.setProvAlbCabCollection(new ArrayList<ProvAlbCab>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosPersonales codigoPersona = proveedores.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona = em.getReference(codigoPersona.getClass(), codigoPersona.getCodigo());
                proveedores.setCodigoPersona(codigoPersona);
            }
            Empresas codigoEmp = proveedores.getCodigoEmp();
            if (codigoEmp != null) {
                codigoEmp = em.getReference(codigoEmp.getClass(), codigoEmp.getCodigo());
                proveedores.setCodigoEmp(codigoEmp);
            }
            Collection<ProvPedidosCab> attachedProvPedidosCabCollection = new ArrayList<ProvPedidosCab>();
            for (ProvPedidosCab provPedidosCabCollectionProvPedidosCabToAttach : proveedores.getProvPedidosCabCollection()) {
                provPedidosCabCollectionProvPedidosCabToAttach = em.getReference(provPedidosCabCollectionProvPedidosCabToAttach.getClass(), provPedidosCabCollectionProvPedidosCabToAttach.getId());
                attachedProvPedidosCabCollection.add(provPedidosCabCollectionProvPedidosCabToAttach);
            }
            proveedores.setProvPedidosCabCollection(attachedProvPedidosCabCollection);
            Collection<PreciosCompras> attachedPreciosComprasCollection = new ArrayList<PreciosCompras>();
            for (PreciosCompras preciosComprasCollectionPreciosComprasToAttach : proveedores.getPreciosComprasCollection()) {
                preciosComprasCollectionPreciosComprasToAttach = em.getReference(preciosComprasCollectionPreciosComprasToAttach.getClass(), preciosComprasCollectionPreciosComprasToAttach.getId());
                attachedPreciosComprasCollection.add(preciosComprasCollectionPreciosComprasToAttach);
            }
            proveedores.setPreciosComprasCollection(attachedPreciosComprasCollection);
            Collection<ProvFacCab> attachedProvFacCabCollection = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionProvFacCabToAttach : proveedores.getProvFacCabCollection()) {
                provFacCabCollectionProvFacCabToAttach = em.getReference(provFacCabCollectionProvFacCabToAttach.getClass(), provFacCabCollectionProvFacCabToAttach.getId());
                attachedProvFacCabCollection.add(provFacCabCollectionProvFacCabToAttach);
            }
            proveedores.setProvFacCabCollection(attachedProvFacCabCollection);
            Collection<ProvAlbCab> attachedProvAlbCabCollection = new ArrayList<ProvAlbCab>();
            for (ProvAlbCab provAlbCabCollectionProvAlbCabToAttach : proveedores.getProvAlbCabCollection()) {
                provAlbCabCollectionProvAlbCabToAttach = em.getReference(provAlbCabCollectionProvAlbCabToAttach.getClass(), provAlbCabCollectionProvAlbCabToAttach.getId());
                attachedProvAlbCabCollection.add(provAlbCabCollectionProvAlbCabToAttach);
            }
            proveedores.setProvAlbCabCollection(attachedProvAlbCabCollection);
            em.persist(proveedores);
            if (codigoPersona != null) {
                codigoPersona.getProveedoresCollection().add(proveedores);
                codigoPersona = em.merge(codigoPersona);
            }
            if (codigoEmp != null) {
                codigoEmp.getProveedoresCollection().add(proveedores);
                codigoEmp = em.merge(codigoEmp);
            }
            for (ProvPedidosCab provPedidosCabCollectionProvPedidosCab : proveedores.getProvPedidosCabCollection()) {
                Proveedores oldCodProvOfProvPedidosCabCollectionProvPedidosCab = provPedidosCabCollectionProvPedidosCab.getCodProv();
                provPedidosCabCollectionProvPedidosCab.setCodProv(proveedores);
                provPedidosCabCollectionProvPedidosCab = em.merge(provPedidosCabCollectionProvPedidosCab);
                if (oldCodProvOfProvPedidosCabCollectionProvPedidosCab != null) {
                    oldCodProvOfProvPedidosCabCollectionProvPedidosCab.getProvPedidosCabCollection().remove(provPedidosCabCollectionProvPedidosCab);
                    oldCodProvOfProvPedidosCabCollectionProvPedidosCab = em.merge(oldCodProvOfProvPedidosCabCollectionProvPedidosCab);
                }
            }
            for (PreciosCompras preciosComprasCollectionPreciosCompras : proveedores.getPreciosComprasCollection()) {
                Proveedores oldCodProvOfPreciosComprasCollectionPreciosCompras = preciosComprasCollectionPreciosCompras.getCodProv();
                preciosComprasCollectionPreciosCompras.setCodProv(proveedores);
                preciosComprasCollectionPreciosCompras = em.merge(preciosComprasCollectionPreciosCompras);
                if (oldCodProvOfPreciosComprasCollectionPreciosCompras != null) {
                    oldCodProvOfPreciosComprasCollectionPreciosCompras.getPreciosComprasCollection().remove(preciosComprasCollectionPreciosCompras);
                    oldCodProvOfPreciosComprasCollectionPreciosCompras = em.merge(oldCodProvOfPreciosComprasCollectionPreciosCompras);
                }
            }
            for (ProvFacCab provFacCabCollectionProvFacCab : proveedores.getProvFacCabCollection()) {
                Proveedores oldCodProvOfProvFacCabCollectionProvFacCab = provFacCabCollectionProvFacCab.getCodProv();
                provFacCabCollectionProvFacCab.setCodProv(proveedores);
                provFacCabCollectionProvFacCab = em.merge(provFacCabCollectionProvFacCab);
                if (oldCodProvOfProvFacCabCollectionProvFacCab != null) {
                    oldCodProvOfProvFacCabCollectionProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionProvFacCab);
                    oldCodProvOfProvFacCabCollectionProvFacCab = em.merge(oldCodProvOfProvFacCabCollectionProvFacCab);
                }
            }
            for (ProvAlbCab provAlbCabCollectionProvAlbCab : proveedores.getProvAlbCabCollection()) {
                Proveedores oldCodProvOfProvAlbCabCollectionProvAlbCab = provAlbCabCollectionProvAlbCab.getCodProv();
                provAlbCabCollectionProvAlbCab.setCodProv(proveedores);
                provAlbCabCollectionProvAlbCab = em.merge(provAlbCabCollectionProvAlbCab);
                if (oldCodProvOfProvAlbCabCollectionProvAlbCab != null) {
                    oldCodProvOfProvAlbCabCollectionProvAlbCab.getProvAlbCabCollection().remove(provAlbCabCollectionProvAlbCab);
                    oldCodProvOfProvAlbCabCollectionProvAlbCab = em.merge(oldCodProvOfProvAlbCabCollectionProvAlbCab);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProveedores(proveedores.getCodigo()) != null) {
                throw new PreexistingEntityException("Proveedores " + proveedores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedores proveedores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedores persistentProveedores = em.find(Proveedores.class, proveedores.getCodigo());
            DatosPersonales codigoPersonaOld = persistentProveedores.getCodigoPersona();
            DatosPersonales codigoPersonaNew = proveedores.getCodigoPersona();
            Empresas codigoEmpOld = persistentProveedores.getCodigoEmp();
            Empresas codigoEmpNew = proveedores.getCodigoEmp();
            Collection<ProvPedidosCab> provPedidosCabCollectionOld = persistentProveedores.getProvPedidosCabCollection();
            Collection<ProvPedidosCab> provPedidosCabCollectionNew = proveedores.getProvPedidosCabCollection();
            Collection<PreciosCompras> preciosComprasCollectionOld = persistentProveedores.getPreciosComprasCollection();
            Collection<PreciosCompras> preciosComprasCollectionNew = proveedores.getPreciosComprasCollection();
            Collection<ProvFacCab> provFacCabCollectionOld = persistentProveedores.getProvFacCabCollection();
            Collection<ProvFacCab> provFacCabCollectionNew = proveedores.getProvFacCabCollection();
            Collection<ProvAlbCab> provAlbCabCollectionOld = persistentProveedores.getProvAlbCabCollection();
            Collection<ProvAlbCab> provAlbCabCollectionNew = proveedores.getProvAlbCabCollection();
            List<String> illegalOrphanMessages = null;
            for (ProvPedidosCab provPedidosCabCollectionOldProvPedidosCab : provPedidosCabCollectionOld) {
                if (!provPedidosCabCollectionNew.contains(provPedidosCabCollectionOldProvPedidosCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvPedidosCab " + provPedidosCabCollectionOldProvPedidosCab + " since its codProv field is not nullable.");
                }
            }
            for (PreciosCompras preciosComprasCollectionOldPreciosCompras : preciosComprasCollectionOld) {
                if (!preciosComprasCollectionNew.contains(preciosComprasCollectionOldPreciosCompras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreciosCompras " + preciosComprasCollectionOldPreciosCompras + " since its codProv field is not nullable.");
                }
            }
            for (ProvFacCab provFacCabCollectionOldProvFacCab : provFacCabCollectionOld) {
                if (!provFacCabCollectionNew.contains(provFacCabCollectionOldProvFacCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvFacCab " + provFacCabCollectionOldProvFacCab + " since its codProv field is not nullable.");
                }
            }
            for (ProvAlbCab provAlbCabCollectionOldProvAlbCab : provAlbCabCollectionOld) {
                if (!provAlbCabCollectionNew.contains(provAlbCabCollectionOldProvAlbCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvAlbCab " + provAlbCabCollectionOldProvAlbCab + " since its codProv field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoPersonaNew != null) {
                codigoPersonaNew = em.getReference(codigoPersonaNew.getClass(), codigoPersonaNew.getCodigo());
                proveedores.setCodigoPersona(codigoPersonaNew);
            }
            if (codigoEmpNew != null) {
                codigoEmpNew = em.getReference(codigoEmpNew.getClass(), codigoEmpNew.getCodigo());
                proveedores.setCodigoEmp(codigoEmpNew);
            }
            Collection<ProvPedidosCab> attachedProvPedidosCabCollectionNew = new ArrayList<ProvPedidosCab>();
            for (ProvPedidosCab provPedidosCabCollectionNewProvPedidosCabToAttach : provPedidosCabCollectionNew) {
                provPedidosCabCollectionNewProvPedidosCabToAttach = em.getReference(provPedidosCabCollectionNewProvPedidosCabToAttach.getClass(), provPedidosCabCollectionNewProvPedidosCabToAttach.getId());
                attachedProvPedidosCabCollectionNew.add(provPedidosCabCollectionNewProvPedidosCabToAttach);
            }
            provPedidosCabCollectionNew = attachedProvPedidosCabCollectionNew;
            proveedores.setProvPedidosCabCollection(provPedidosCabCollectionNew);
            Collection<PreciosCompras> attachedPreciosComprasCollectionNew = new ArrayList<PreciosCompras>();
            for (PreciosCompras preciosComprasCollectionNewPreciosComprasToAttach : preciosComprasCollectionNew) {
                preciosComprasCollectionNewPreciosComprasToAttach = em.getReference(preciosComprasCollectionNewPreciosComprasToAttach.getClass(), preciosComprasCollectionNewPreciosComprasToAttach.getId());
                attachedPreciosComprasCollectionNew.add(preciosComprasCollectionNewPreciosComprasToAttach);
            }
            preciosComprasCollectionNew = attachedPreciosComprasCollectionNew;
            proveedores.setPreciosComprasCollection(preciosComprasCollectionNew);
            Collection<ProvFacCab> attachedProvFacCabCollectionNew = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionNewProvFacCabToAttach : provFacCabCollectionNew) {
                provFacCabCollectionNewProvFacCabToAttach = em.getReference(provFacCabCollectionNewProvFacCabToAttach.getClass(), provFacCabCollectionNewProvFacCabToAttach.getId());
                attachedProvFacCabCollectionNew.add(provFacCabCollectionNewProvFacCabToAttach);
            }
            provFacCabCollectionNew = attachedProvFacCabCollectionNew;
            proveedores.setProvFacCabCollection(provFacCabCollectionNew);
            Collection<ProvAlbCab> attachedProvAlbCabCollectionNew = new ArrayList<ProvAlbCab>();
            for (ProvAlbCab provAlbCabCollectionNewProvAlbCabToAttach : provAlbCabCollectionNew) {
                provAlbCabCollectionNewProvAlbCabToAttach = em.getReference(provAlbCabCollectionNewProvAlbCabToAttach.getClass(), provAlbCabCollectionNewProvAlbCabToAttach.getId());
                attachedProvAlbCabCollectionNew.add(provAlbCabCollectionNewProvAlbCabToAttach);
            }
            provAlbCabCollectionNew = attachedProvAlbCabCollectionNew;
            proveedores.setProvAlbCabCollection(provAlbCabCollectionNew);
            proveedores = em.merge(proveedores);
            if (codigoPersonaOld != null && !codigoPersonaOld.equals(codigoPersonaNew)) {
                codigoPersonaOld.getProveedoresCollection().remove(proveedores);
                codigoPersonaOld = em.merge(codigoPersonaOld);
            }
            if (codigoPersonaNew != null && !codigoPersonaNew.equals(codigoPersonaOld)) {
                codigoPersonaNew.getProveedoresCollection().add(proveedores);
                codigoPersonaNew = em.merge(codigoPersonaNew);
            }
            if (codigoEmpOld != null && !codigoEmpOld.equals(codigoEmpNew)) {
                codigoEmpOld.getProveedoresCollection().remove(proveedores);
                codigoEmpOld = em.merge(codigoEmpOld);
            }
            if (codigoEmpNew != null && !codigoEmpNew.equals(codigoEmpOld)) {
                codigoEmpNew.getProveedoresCollection().add(proveedores);
                codigoEmpNew = em.merge(codigoEmpNew);
            }
            for (ProvPedidosCab provPedidosCabCollectionNewProvPedidosCab : provPedidosCabCollectionNew) {
                if (!provPedidosCabCollectionOld.contains(provPedidosCabCollectionNewProvPedidosCab)) {
                    Proveedores oldCodProvOfProvPedidosCabCollectionNewProvPedidosCab = provPedidosCabCollectionNewProvPedidosCab.getCodProv();
                    provPedidosCabCollectionNewProvPedidosCab.setCodProv(proveedores);
                    provPedidosCabCollectionNewProvPedidosCab = em.merge(provPedidosCabCollectionNewProvPedidosCab);
                    if (oldCodProvOfProvPedidosCabCollectionNewProvPedidosCab != null && !oldCodProvOfProvPedidosCabCollectionNewProvPedidosCab.equals(proveedores)) {
                        oldCodProvOfProvPedidosCabCollectionNewProvPedidosCab.getProvPedidosCabCollection().remove(provPedidosCabCollectionNewProvPedidosCab);
                        oldCodProvOfProvPedidosCabCollectionNewProvPedidosCab = em.merge(oldCodProvOfProvPedidosCabCollectionNewProvPedidosCab);
                    }
                }
            }
            for (PreciosCompras preciosComprasCollectionNewPreciosCompras : preciosComprasCollectionNew) {
                if (!preciosComprasCollectionOld.contains(preciosComprasCollectionNewPreciosCompras)) {
                    Proveedores oldCodProvOfPreciosComprasCollectionNewPreciosCompras = preciosComprasCollectionNewPreciosCompras.getCodProv();
                    preciosComprasCollectionNewPreciosCompras.setCodProv(proveedores);
                    preciosComprasCollectionNewPreciosCompras = em.merge(preciosComprasCollectionNewPreciosCompras);
                    if (oldCodProvOfPreciosComprasCollectionNewPreciosCompras != null && !oldCodProvOfPreciosComprasCollectionNewPreciosCompras.equals(proveedores)) {
                        oldCodProvOfPreciosComprasCollectionNewPreciosCompras.getPreciosComprasCollection().remove(preciosComprasCollectionNewPreciosCompras);
                        oldCodProvOfPreciosComprasCollectionNewPreciosCompras = em.merge(oldCodProvOfPreciosComprasCollectionNewPreciosCompras);
                    }
                }
            }
            for (ProvFacCab provFacCabCollectionNewProvFacCab : provFacCabCollectionNew) {
                if (!provFacCabCollectionOld.contains(provFacCabCollectionNewProvFacCab)) {
                    Proveedores oldCodProvOfProvFacCabCollectionNewProvFacCab = provFacCabCollectionNewProvFacCab.getCodProv();
                    provFacCabCollectionNewProvFacCab.setCodProv(proveedores);
                    provFacCabCollectionNewProvFacCab = em.merge(provFacCabCollectionNewProvFacCab);
                    if (oldCodProvOfProvFacCabCollectionNewProvFacCab != null && !oldCodProvOfProvFacCabCollectionNewProvFacCab.equals(proveedores)) {
                        oldCodProvOfProvFacCabCollectionNewProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionNewProvFacCab);
                        oldCodProvOfProvFacCabCollectionNewProvFacCab = em.merge(oldCodProvOfProvFacCabCollectionNewProvFacCab);
                    }
                }
            }
            for (ProvAlbCab provAlbCabCollectionNewProvAlbCab : provAlbCabCollectionNew) {
                if (!provAlbCabCollectionOld.contains(provAlbCabCollectionNewProvAlbCab)) {
                    Proveedores oldCodProvOfProvAlbCabCollectionNewProvAlbCab = provAlbCabCollectionNewProvAlbCab.getCodProv();
                    provAlbCabCollectionNewProvAlbCab.setCodProv(proveedores);
                    provAlbCabCollectionNewProvAlbCab = em.merge(provAlbCabCollectionNewProvAlbCab);
                    if (oldCodProvOfProvAlbCabCollectionNewProvAlbCab != null && !oldCodProvOfProvAlbCabCollectionNewProvAlbCab.equals(proveedores)) {
                        oldCodProvOfProvAlbCabCollectionNewProvAlbCab.getProvAlbCabCollection().remove(provAlbCabCollectionNewProvAlbCab);
                        oldCodProvOfProvAlbCabCollectionNewProvAlbCab = em.merge(oldCodProvOfProvAlbCabCollectionNewProvAlbCab);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = proveedores.getCodigo();
                if (findProveedores(id) == null) {
                    throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedores proveedores;
            try {
                proveedores = em.getReference(Proveedores.class, id);
                proveedores.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProvPedidosCab> provPedidosCabCollectionOrphanCheck = proveedores.getProvPedidosCabCollection();
            for (ProvPedidosCab provPedidosCabCollectionOrphanCheckProvPedidosCab : provPedidosCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedores (" + proveedores + ") cannot be destroyed since the ProvPedidosCab " + provPedidosCabCollectionOrphanCheckProvPedidosCab + " in its provPedidosCabCollection field has a non-nullable codProv field.");
            }
            Collection<PreciosCompras> preciosComprasCollectionOrphanCheck = proveedores.getPreciosComprasCollection();
            for (PreciosCompras preciosComprasCollectionOrphanCheckPreciosCompras : preciosComprasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedores (" + proveedores + ") cannot be destroyed since the PreciosCompras " + preciosComprasCollectionOrphanCheckPreciosCompras + " in its preciosComprasCollection field has a non-nullable codProv field.");
            }
            Collection<ProvFacCab> provFacCabCollectionOrphanCheck = proveedores.getProvFacCabCollection();
            for (ProvFacCab provFacCabCollectionOrphanCheckProvFacCab : provFacCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedores (" + proveedores + ") cannot be destroyed since the ProvFacCab " + provFacCabCollectionOrphanCheckProvFacCab + " in its provFacCabCollection field has a non-nullable codProv field.");
            }
            Collection<ProvAlbCab> provAlbCabCollectionOrphanCheck = proveedores.getProvAlbCabCollection();
            for (ProvAlbCab provAlbCabCollectionOrphanCheckProvAlbCab : provAlbCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedores (" + proveedores + ") cannot be destroyed since the ProvAlbCab " + provAlbCabCollectionOrphanCheckProvAlbCab + " in its provAlbCabCollection field has a non-nullable codProv field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            DatosPersonales codigoPersona = proveedores.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona.getProveedoresCollection().remove(proveedores);
                codigoPersona = em.merge(codigoPersona);
            }
            Empresas codigoEmp = proveedores.getCodigoEmp();
            if (codigoEmp != null) {
                codigoEmp.getProveedoresCollection().remove(proveedores);
                codigoEmp = em.merge(codigoEmp);
            }
            em.remove(proveedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedores> findProveedoresEntities() {
        return findProveedoresEntities(true, -1, -1);
    }

    public List<Proveedores> findProveedoresEntities(int maxResults, int firstResult) {
        return findProveedoresEntities(false, maxResults, firstResult);
    }

    private List<Proveedores> findProveedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Proveedores as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Proveedores findProveedores(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedoresCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Proveedores as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
