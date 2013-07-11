/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.DatosPersonales;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.CliAlbCab;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.pojos.CliFacCab;
import es.timmps.fac.persistencia.pojos.PreciosVentas;
import es.timmps.fac.persistencia.pojos.CliPedidosCab;
import es.timmps.fac.persistencia.pojos.Clientes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) throws PreexistingEntityException, Exception {
        if (clientes.getCliAlbCabCollection() == null) {
            clientes.setCliAlbCabCollection(new ArrayList<CliAlbCab>());
        }
        if (clientes.getCliFacCabCollection() == null) {
            clientes.setCliFacCabCollection(new ArrayList<CliFacCab>());
        }
        if (clientes.getPreciosVentasCollection() == null) {
            clientes.setPreciosVentasCollection(new ArrayList<PreciosVentas>());
        }
        if (clientes.getCliPedidosCabCollection() == null) {
            clientes.setCliPedidosCabCollection(new ArrayList<CliPedidosCab>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosPersonales codigoPersona = clientes.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona = em.getReference(codigoPersona.getClass(), codigoPersona.getCodigo());
                clientes.setCodigoPersona(codigoPersona);
            }
            Empresas codigoEmp = clientes.getCodigoEmp();
            if (codigoEmp != null) {
                codigoEmp = em.getReference(codigoEmp.getClass(), codigoEmp.getCodigo());
                clientes.setCodigoEmp(codigoEmp);
            }
            Collection<CliAlbCab> attachedCliAlbCabCollection = new ArrayList<CliAlbCab>();
            for (CliAlbCab cliAlbCabCollectionCliAlbCabToAttach : clientes.getCliAlbCabCollection()) {
                cliAlbCabCollectionCliAlbCabToAttach = em.getReference(cliAlbCabCollectionCliAlbCabToAttach.getClass(), cliAlbCabCollectionCliAlbCabToAttach.getId());
                attachedCliAlbCabCollection.add(cliAlbCabCollectionCliAlbCabToAttach);
            }
            clientes.setCliAlbCabCollection(attachedCliAlbCabCollection);
            Collection<CliFacCab> attachedCliFacCabCollection = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionCliFacCabToAttach : clientes.getCliFacCabCollection()) {
                cliFacCabCollectionCliFacCabToAttach = em.getReference(cliFacCabCollectionCliFacCabToAttach.getClass(), cliFacCabCollectionCliFacCabToAttach.getId());
                attachedCliFacCabCollection.add(cliFacCabCollectionCliFacCabToAttach);
            }
            clientes.setCliFacCabCollection(attachedCliFacCabCollection);
            Collection<PreciosVentas> attachedPreciosVentasCollection = new ArrayList<PreciosVentas>();
            for (PreciosVentas preciosVentasCollectionPreciosVentasToAttach : clientes.getPreciosVentasCollection()) {
                preciosVentasCollectionPreciosVentasToAttach = em.getReference(preciosVentasCollectionPreciosVentasToAttach.getClass(), preciosVentasCollectionPreciosVentasToAttach.getId());
                attachedPreciosVentasCollection.add(preciosVentasCollectionPreciosVentasToAttach);
            }
            clientes.setPreciosVentasCollection(attachedPreciosVentasCollection);
            Collection<CliPedidosCab> attachedCliPedidosCabCollection = new ArrayList<CliPedidosCab>();
            for (CliPedidosCab cliPedidosCabCollectionCliPedidosCabToAttach : clientes.getCliPedidosCabCollection()) {
                cliPedidosCabCollectionCliPedidosCabToAttach = em.getReference(cliPedidosCabCollectionCliPedidosCabToAttach.getClass(), cliPedidosCabCollectionCliPedidosCabToAttach.getId());
                attachedCliPedidosCabCollection.add(cliPedidosCabCollectionCliPedidosCabToAttach);
            }
            clientes.setCliPedidosCabCollection(attachedCliPedidosCabCollection);
            em.persist(clientes);
            if (codigoPersona != null) {
                codigoPersona.getClientesCollection().add(clientes);
                codigoPersona = em.merge(codigoPersona);
            }
            if (codigoEmp != null) {
                codigoEmp.getClientesCollection().add(clientes);
                codigoEmp = em.merge(codigoEmp);
            }
            for (CliAlbCab cliAlbCabCollectionCliAlbCab : clientes.getCliAlbCabCollection()) {
                Clientes oldCodCliOfCliAlbCabCollectionCliAlbCab = cliAlbCabCollectionCliAlbCab.getCodCli();
                cliAlbCabCollectionCliAlbCab.setCodCli(clientes);
                cliAlbCabCollectionCliAlbCab = em.merge(cliAlbCabCollectionCliAlbCab);
                if (oldCodCliOfCliAlbCabCollectionCliAlbCab != null) {
                    oldCodCliOfCliAlbCabCollectionCliAlbCab.getCliAlbCabCollection().remove(cliAlbCabCollectionCliAlbCab);
                    oldCodCliOfCliAlbCabCollectionCliAlbCab = em.merge(oldCodCliOfCliAlbCabCollectionCliAlbCab);
                }
            }
            for (CliFacCab cliFacCabCollectionCliFacCab : clientes.getCliFacCabCollection()) {
                Clientes oldCodCliOfCliFacCabCollectionCliFacCab = cliFacCabCollectionCliFacCab.getCodCli();
                cliFacCabCollectionCliFacCab.setCodCli(clientes);
                cliFacCabCollectionCliFacCab = em.merge(cliFacCabCollectionCliFacCab);
                if (oldCodCliOfCliFacCabCollectionCliFacCab != null) {
                    oldCodCliOfCliFacCabCollectionCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionCliFacCab);
                    oldCodCliOfCliFacCabCollectionCliFacCab = em.merge(oldCodCliOfCliFacCabCollectionCliFacCab);
                }
            }
            for (PreciosVentas preciosVentasCollectionPreciosVentas : clientes.getPreciosVentasCollection()) {
                Clientes oldCodCliOfPreciosVentasCollectionPreciosVentas = preciosVentasCollectionPreciosVentas.getCodCli();
                preciosVentasCollectionPreciosVentas.setCodCli(clientes);
                preciosVentasCollectionPreciosVentas = em.merge(preciosVentasCollectionPreciosVentas);
                if (oldCodCliOfPreciosVentasCollectionPreciosVentas != null) {
                    oldCodCliOfPreciosVentasCollectionPreciosVentas.getPreciosVentasCollection().remove(preciosVentasCollectionPreciosVentas);
                    oldCodCliOfPreciosVentasCollectionPreciosVentas = em.merge(oldCodCliOfPreciosVentasCollectionPreciosVentas);
                }
            }
            for (CliPedidosCab cliPedidosCabCollectionCliPedidosCab : clientes.getCliPedidosCabCollection()) {
                Clientes oldCodCliOfCliPedidosCabCollectionCliPedidosCab = cliPedidosCabCollectionCliPedidosCab.getCodCli();
                cliPedidosCabCollectionCliPedidosCab.setCodCli(clientes);
                cliPedidosCabCollectionCliPedidosCab = em.merge(cliPedidosCabCollectionCliPedidosCab);
                if (oldCodCliOfCliPedidosCabCollectionCliPedidosCab != null) {
                    oldCodCliOfCliPedidosCabCollectionCliPedidosCab.getCliPedidosCabCollection().remove(cliPedidosCabCollectionCliPedidosCab);
                    oldCodCliOfCliPedidosCabCollectionCliPedidosCab = em.merge(oldCodCliOfCliPedidosCabCollectionCliPedidosCab);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientes(clientes.getCodigo()) != null) {
                throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getCodigo());
            DatosPersonales codigoPersonaOld = persistentClientes.getCodigoPersona();
            DatosPersonales codigoPersonaNew = clientes.getCodigoPersona();
            Empresas codigoEmpOld = persistentClientes.getCodigoEmp();
            Empresas codigoEmpNew = clientes.getCodigoEmp();
            Collection<CliAlbCab> cliAlbCabCollectionOld = persistentClientes.getCliAlbCabCollection();
            Collection<CliAlbCab> cliAlbCabCollectionNew = clientes.getCliAlbCabCollection();
            Collection<CliFacCab> cliFacCabCollectionOld = persistentClientes.getCliFacCabCollection();
            Collection<CliFacCab> cliFacCabCollectionNew = clientes.getCliFacCabCollection();
            Collection<PreciosVentas> preciosVentasCollectionOld = persistentClientes.getPreciosVentasCollection();
            Collection<PreciosVentas> preciosVentasCollectionNew = clientes.getPreciosVentasCollection();
            Collection<CliPedidosCab> cliPedidosCabCollectionOld = persistentClientes.getCliPedidosCabCollection();
            Collection<CliPedidosCab> cliPedidosCabCollectionNew = clientes.getCliPedidosCabCollection();
            List<String> illegalOrphanMessages = null;
            for (CliAlbCab cliAlbCabCollectionOldCliAlbCab : cliAlbCabCollectionOld) {
                if (!cliAlbCabCollectionNew.contains(cliAlbCabCollectionOldCliAlbCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliAlbCab " + cliAlbCabCollectionOldCliAlbCab + " since its codCli field is not nullable.");
                }
            }
            for (CliFacCab cliFacCabCollectionOldCliFacCab : cliFacCabCollectionOld) {
                if (!cliFacCabCollectionNew.contains(cliFacCabCollectionOldCliFacCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliFacCab " + cliFacCabCollectionOldCliFacCab + " since its codCli field is not nullable.");
                }
            }
            for (PreciosVentas preciosVentasCollectionOldPreciosVentas : preciosVentasCollectionOld) {
                if (!preciosVentasCollectionNew.contains(preciosVentasCollectionOldPreciosVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreciosVentas " + preciosVentasCollectionOldPreciosVentas + " since its codCli field is not nullable.");
                }
            }
            for (CliPedidosCab cliPedidosCabCollectionOldCliPedidosCab : cliPedidosCabCollectionOld) {
                if (!cliPedidosCabCollectionNew.contains(cliPedidosCabCollectionOldCliPedidosCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliPedidosCab " + cliPedidosCabCollectionOldCliPedidosCab + " since its codCli field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoPersonaNew != null) {
                codigoPersonaNew = em.getReference(codigoPersonaNew.getClass(), codigoPersonaNew.getCodigo());
                clientes.setCodigoPersona(codigoPersonaNew);
            }
            if (codigoEmpNew != null) {
                codigoEmpNew = em.getReference(codigoEmpNew.getClass(), codigoEmpNew.getCodigo());
                clientes.setCodigoEmp(codigoEmpNew);
            }
            Collection<CliAlbCab> attachedCliAlbCabCollectionNew = new ArrayList<CliAlbCab>();
            for (CliAlbCab cliAlbCabCollectionNewCliAlbCabToAttach : cliAlbCabCollectionNew) {
                cliAlbCabCollectionNewCliAlbCabToAttach = em.getReference(cliAlbCabCollectionNewCliAlbCabToAttach.getClass(), cliAlbCabCollectionNewCliAlbCabToAttach.getId());
                attachedCliAlbCabCollectionNew.add(cliAlbCabCollectionNewCliAlbCabToAttach);
            }
            cliAlbCabCollectionNew = attachedCliAlbCabCollectionNew;
            clientes.setCliAlbCabCollection(cliAlbCabCollectionNew);
            Collection<CliFacCab> attachedCliFacCabCollectionNew = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionNewCliFacCabToAttach : cliFacCabCollectionNew) {
                cliFacCabCollectionNewCliFacCabToAttach = em.getReference(cliFacCabCollectionNewCliFacCabToAttach.getClass(), cliFacCabCollectionNewCliFacCabToAttach.getId());
                attachedCliFacCabCollectionNew.add(cliFacCabCollectionNewCliFacCabToAttach);
            }
            cliFacCabCollectionNew = attachedCliFacCabCollectionNew;
            clientes.setCliFacCabCollection(cliFacCabCollectionNew);
            Collection<PreciosVentas> attachedPreciosVentasCollectionNew = new ArrayList<PreciosVentas>();
            for (PreciosVentas preciosVentasCollectionNewPreciosVentasToAttach : preciosVentasCollectionNew) {
                preciosVentasCollectionNewPreciosVentasToAttach = em.getReference(preciosVentasCollectionNewPreciosVentasToAttach.getClass(), preciosVentasCollectionNewPreciosVentasToAttach.getId());
                attachedPreciosVentasCollectionNew.add(preciosVentasCollectionNewPreciosVentasToAttach);
            }
            preciosVentasCollectionNew = attachedPreciosVentasCollectionNew;
            clientes.setPreciosVentasCollection(preciosVentasCollectionNew);
            Collection<CliPedidosCab> attachedCliPedidosCabCollectionNew = new ArrayList<CliPedidosCab>();
            for (CliPedidosCab cliPedidosCabCollectionNewCliPedidosCabToAttach : cliPedidosCabCollectionNew) {
                cliPedidosCabCollectionNewCliPedidosCabToAttach = em.getReference(cliPedidosCabCollectionNewCliPedidosCabToAttach.getClass(), cliPedidosCabCollectionNewCliPedidosCabToAttach.getId());
                attachedCliPedidosCabCollectionNew.add(cliPedidosCabCollectionNewCliPedidosCabToAttach);
            }
            cliPedidosCabCollectionNew = attachedCliPedidosCabCollectionNew;
            clientes.setCliPedidosCabCollection(cliPedidosCabCollectionNew);
            clientes = em.merge(clientes);
            if (codigoPersonaOld != null && !codigoPersonaOld.equals(codigoPersonaNew)) {
                codigoPersonaOld.getClientesCollection().remove(clientes);
                codigoPersonaOld = em.merge(codigoPersonaOld);
            }
            if (codigoPersonaNew != null && !codigoPersonaNew.equals(codigoPersonaOld)) {
                codigoPersonaNew.getClientesCollection().add(clientes);
                codigoPersonaNew = em.merge(codigoPersonaNew);
            }
            if (codigoEmpOld != null && !codigoEmpOld.equals(codigoEmpNew)) {
                codigoEmpOld.getClientesCollection().remove(clientes);
                codigoEmpOld = em.merge(codigoEmpOld);
            }
            if (codigoEmpNew != null && !codigoEmpNew.equals(codigoEmpOld)) {
                codigoEmpNew.getClientesCollection().add(clientes);
                codigoEmpNew = em.merge(codigoEmpNew);
            }
            for (CliAlbCab cliAlbCabCollectionNewCliAlbCab : cliAlbCabCollectionNew) {
                if (!cliAlbCabCollectionOld.contains(cliAlbCabCollectionNewCliAlbCab)) {
                    Clientes oldCodCliOfCliAlbCabCollectionNewCliAlbCab = cliAlbCabCollectionNewCliAlbCab.getCodCli();
                    cliAlbCabCollectionNewCliAlbCab.setCodCli(clientes);
                    cliAlbCabCollectionNewCliAlbCab = em.merge(cliAlbCabCollectionNewCliAlbCab);
                    if (oldCodCliOfCliAlbCabCollectionNewCliAlbCab != null && !oldCodCliOfCliAlbCabCollectionNewCliAlbCab.equals(clientes)) {
                        oldCodCliOfCliAlbCabCollectionNewCliAlbCab.getCliAlbCabCollection().remove(cliAlbCabCollectionNewCliAlbCab);
                        oldCodCliOfCliAlbCabCollectionNewCliAlbCab = em.merge(oldCodCliOfCliAlbCabCollectionNewCliAlbCab);
                    }
                }
            }
            for (CliFacCab cliFacCabCollectionNewCliFacCab : cliFacCabCollectionNew) {
                if (!cliFacCabCollectionOld.contains(cliFacCabCollectionNewCliFacCab)) {
                    Clientes oldCodCliOfCliFacCabCollectionNewCliFacCab = cliFacCabCollectionNewCliFacCab.getCodCli();
                    cliFacCabCollectionNewCliFacCab.setCodCli(clientes);
                    cliFacCabCollectionNewCliFacCab = em.merge(cliFacCabCollectionNewCliFacCab);
                    if (oldCodCliOfCliFacCabCollectionNewCliFacCab != null && !oldCodCliOfCliFacCabCollectionNewCliFacCab.equals(clientes)) {
                        oldCodCliOfCliFacCabCollectionNewCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionNewCliFacCab);
                        oldCodCliOfCliFacCabCollectionNewCliFacCab = em.merge(oldCodCliOfCliFacCabCollectionNewCliFacCab);
                    }
                }
            }
            for (PreciosVentas preciosVentasCollectionNewPreciosVentas : preciosVentasCollectionNew) {
                if (!preciosVentasCollectionOld.contains(preciosVentasCollectionNewPreciosVentas)) {
                    Clientes oldCodCliOfPreciosVentasCollectionNewPreciosVentas = preciosVentasCollectionNewPreciosVentas.getCodCli();
                    preciosVentasCollectionNewPreciosVentas.setCodCli(clientes);
                    preciosVentasCollectionNewPreciosVentas = em.merge(preciosVentasCollectionNewPreciosVentas);
                    if (oldCodCliOfPreciosVentasCollectionNewPreciosVentas != null && !oldCodCliOfPreciosVentasCollectionNewPreciosVentas.equals(clientes)) {
                        oldCodCliOfPreciosVentasCollectionNewPreciosVentas.getPreciosVentasCollection().remove(preciosVentasCollectionNewPreciosVentas);
                        oldCodCliOfPreciosVentasCollectionNewPreciosVentas = em.merge(oldCodCliOfPreciosVentasCollectionNewPreciosVentas);
                    }
                }
            }
            for (CliPedidosCab cliPedidosCabCollectionNewCliPedidosCab : cliPedidosCabCollectionNew) {
                if (!cliPedidosCabCollectionOld.contains(cliPedidosCabCollectionNewCliPedidosCab)) {
                    Clientes oldCodCliOfCliPedidosCabCollectionNewCliPedidosCab = cliPedidosCabCollectionNewCliPedidosCab.getCodCli();
                    cliPedidosCabCollectionNewCliPedidosCab.setCodCli(clientes);
                    cliPedidosCabCollectionNewCliPedidosCab = em.merge(cliPedidosCabCollectionNewCliPedidosCab);
                    if (oldCodCliOfCliPedidosCabCollectionNewCliPedidosCab != null && !oldCodCliOfCliPedidosCabCollectionNewCliPedidosCab.equals(clientes)) {
                        oldCodCliOfCliPedidosCabCollectionNewCliPedidosCab.getCliPedidosCabCollection().remove(cliPedidosCabCollectionNewCliPedidosCab);
                        oldCodCliOfCliPedidosCabCollectionNewCliPedidosCab = em.merge(oldCodCliOfCliPedidosCabCollectionNewCliPedidosCab);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = clientes.getCodigo();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CliAlbCab> cliAlbCabCollectionOrphanCheck = clientes.getCliAlbCabCollection();
            for (CliAlbCab cliAlbCabCollectionOrphanCheckCliAlbCab : cliAlbCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the CliAlbCab " + cliAlbCabCollectionOrphanCheckCliAlbCab + " in its cliAlbCabCollection field has a non-nullable codCli field.");
            }
            Collection<CliFacCab> cliFacCabCollectionOrphanCheck = clientes.getCliFacCabCollection();
            for (CliFacCab cliFacCabCollectionOrphanCheckCliFacCab : cliFacCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the CliFacCab " + cliFacCabCollectionOrphanCheckCliFacCab + " in its cliFacCabCollection field has a non-nullable codCli field.");
            }
            Collection<PreciosVentas> preciosVentasCollectionOrphanCheck = clientes.getPreciosVentasCollection();
            for (PreciosVentas preciosVentasCollectionOrphanCheckPreciosVentas : preciosVentasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the PreciosVentas " + preciosVentasCollectionOrphanCheckPreciosVentas + " in its preciosVentasCollection field has a non-nullable codCli field.");
            }
            Collection<CliPedidosCab> cliPedidosCabCollectionOrphanCheck = clientes.getCliPedidosCabCollection();
            for (CliPedidosCab cliPedidosCabCollectionOrphanCheckCliPedidosCab : cliPedidosCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the CliPedidosCab " + cliPedidosCabCollectionOrphanCheckCliPedidosCab + " in its cliPedidosCabCollection field has a non-nullable codCli field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            DatosPersonales codigoPersona = clientes.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona.getClientesCollection().remove(clientes);
                codigoPersona = em.merge(codigoPersona);
            }
            Empresas codigoEmp = clientes.getCodigoEmp();
            if (codigoEmp != null) {
                codigoEmp.getClientesCollection().remove(clientes);
                codigoEmp = em.merge(codigoEmp);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
