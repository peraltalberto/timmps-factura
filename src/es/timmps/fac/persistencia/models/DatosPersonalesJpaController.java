/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.TipoPersona;
import es.timmps.fac.persistencia.Direcciones;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.Mails;
import es.timmps.fac.persistencia.Clientes;
import es.timmps.fac.persistencia.DatosPersonales;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.fac.persistencia.Telefonos;
import es.timmps.fac.persistencia.DpCustom;
import es.timmps.fac.persistencia.Proveedores;
import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class DatosPersonalesJpaController implements Serializable {

    public DatosPersonalesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DatosPersonales datosPersonales) {
        if (datosPersonales.getDireccionesCollection() == null) {
            datosPersonales.setDireccionesCollection(new ArrayList<Direcciones>());
        }
        if (datosPersonales.getMailsCollection() == null) {
            datosPersonales.setMailsCollection(new ArrayList<Mails>());
        }
        if (datosPersonales.getClientesCollection() == null) {
            datosPersonales.setClientesCollection(new ArrayList<Clientes>());
        }
        if (datosPersonales.getUsuariosCollection() == null) {
            datosPersonales.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        if (datosPersonales.getTelefonosCollection() == null) {
            datosPersonales.setTelefonosCollection(new ArrayList<Telefonos>());
        }
        if (datosPersonales.getDpCustomCollection() == null) {
            datosPersonales.setDpCustomCollection(new ArrayList<DpCustom>());
        }
        if (datosPersonales.getProveedoresCollection() == null) {
            datosPersonales.setProveedoresCollection(new ArrayList<Proveedores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPersona tipo = datosPersonales.getTipo();
            if (tipo != null) {
                tipo = em.getReference(tipo.getClass(), tipo.getId());
                datosPersonales.setTipo(tipo);
            }
            Collection<Direcciones> attachedDireccionesCollection = new ArrayList<Direcciones>();
            for (Direcciones direccionesCollectionDireccionesToAttach : datosPersonales.getDireccionesCollection()) {
                direccionesCollectionDireccionesToAttach = em.getReference(direccionesCollectionDireccionesToAttach.getClass(), direccionesCollectionDireccionesToAttach.getId());
                attachedDireccionesCollection.add(direccionesCollectionDireccionesToAttach);
            }
            datosPersonales.setDireccionesCollection(attachedDireccionesCollection);
            Collection<Mails> attachedMailsCollection = new ArrayList<Mails>();
            for (Mails mailsCollectionMailsToAttach : datosPersonales.getMailsCollection()) {
                mailsCollectionMailsToAttach = em.getReference(mailsCollectionMailsToAttach.getClass(), mailsCollectionMailsToAttach.getId());
                attachedMailsCollection.add(mailsCollectionMailsToAttach);
            }
            datosPersonales.setMailsCollection(attachedMailsCollection);
            Collection<Clientes> attachedClientesCollection = new ArrayList<Clientes>();
            for (Clientes clientesCollectionClientesToAttach : datosPersonales.getClientesCollection()) {
                clientesCollectionClientesToAttach = em.getReference(clientesCollectionClientesToAttach.getClass(), clientesCollectionClientesToAttach.getCodigo());
                attachedClientesCollection.add(clientesCollectionClientesToAttach);
            }
            datosPersonales.setClientesCollection(attachedClientesCollection);
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : datosPersonales.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            datosPersonales.setUsuariosCollection(attachedUsuariosCollection);
            Collection<Telefonos> attachedTelefonosCollection = new ArrayList<Telefonos>();
            for (Telefonos telefonosCollectionTelefonosToAttach : datosPersonales.getTelefonosCollection()) {
                telefonosCollectionTelefonosToAttach = em.getReference(telefonosCollectionTelefonosToAttach.getClass(), telefonosCollectionTelefonosToAttach.getId());
                attachedTelefonosCollection.add(telefonosCollectionTelefonosToAttach);
            }
            datosPersonales.setTelefonosCollection(attachedTelefonosCollection);
            Collection<DpCustom> attachedDpCustomCollection = new ArrayList<DpCustom>();
            for (DpCustom dpCustomCollectionDpCustomToAttach : datosPersonales.getDpCustomCollection()) {
                dpCustomCollectionDpCustomToAttach = em.getReference(dpCustomCollectionDpCustomToAttach.getClass(), dpCustomCollectionDpCustomToAttach.getDpCustomPK());
                attachedDpCustomCollection.add(dpCustomCollectionDpCustomToAttach);
            }
            datosPersonales.setDpCustomCollection(attachedDpCustomCollection);
            Collection<Proveedores> attachedProveedoresCollection = new ArrayList<Proveedores>();
            for (Proveedores proveedoresCollectionProveedoresToAttach : datosPersonales.getProveedoresCollection()) {
                proveedoresCollectionProveedoresToAttach = em.getReference(proveedoresCollectionProveedoresToAttach.getClass(), proveedoresCollectionProveedoresToAttach.getCodigo());
                attachedProveedoresCollection.add(proveedoresCollectionProveedoresToAttach);
            }
            datosPersonales.setProveedoresCollection(attachedProveedoresCollection);
            em.persist(datosPersonales);
            if (tipo != null) {
                tipo.getDatosPersonalesCollection().add(datosPersonales);
                tipo = em.merge(tipo);
            }
            for (Direcciones direccionesCollectionDirecciones : datosPersonales.getDireccionesCollection()) {
                DatosPersonales oldCodigoPersonaOfDireccionesCollectionDirecciones = direccionesCollectionDirecciones.getCodigoPersona();
                direccionesCollectionDirecciones.setCodigoPersona(datosPersonales);
                direccionesCollectionDirecciones = em.merge(direccionesCollectionDirecciones);
                if (oldCodigoPersonaOfDireccionesCollectionDirecciones != null) {
                    oldCodigoPersonaOfDireccionesCollectionDirecciones.getDireccionesCollection().remove(direccionesCollectionDirecciones);
                    oldCodigoPersonaOfDireccionesCollectionDirecciones = em.merge(oldCodigoPersonaOfDireccionesCollectionDirecciones);
                }
            }
            for (Mails mailsCollectionMails : datosPersonales.getMailsCollection()) {
                DatosPersonales oldCodigoPersonaOfMailsCollectionMails = mailsCollectionMails.getCodigoPersona();
                mailsCollectionMails.setCodigoPersona(datosPersonales);
                mailsCollectionMails = em.merge(mailsCollectionMails);
                if (oldCodigoPersonaOfMailsCollectionMails != null) {
                    oldCodigoPersonaOfMailsCollectionMails.getMailsCollection().remove(mailsCollectionMails);
                    oldCodigoPersonaOfMailsCollectionMails = em.merge(oldCodigoPersonaOfMailsCollectionMails);
                }
            }
            for (Clientes clientesCollectionClientes : datosPersonales.getClientesCollection()) {
                DatosPersonales oldCodigoPersonaOfClientesCollectionClientes = clientesCollectionClientes.getCodigoPersona();
                clientesCollectionClientes.setCodigoPersona(datosPersonales);
                clientesCollectionClientes = em.merge(clientesCollectionClientes);
                if (oldCodigoPersonaOfClientesCollectionClientes != null) {
                    oldCodigoPersonaOfClientesCollectionClientes.getClientesCollection().remove(clientesCollectionClientes);
                    oldCodigoPersonaOfClientesCollectionClientes = em.merge(oldCodigoPersonaOfClientesCollectionClientes);
                }
            }
            for (Usuarios usuariosCollectionUsuarios : datosPersonales.getUsuariosCollection()) {
                DatosPersonales oldCodigoPersonaOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getCodigoPersona();
                usuariosCollectionUsuarios.setCodigoPersona(datosPersonales);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldCodigoPersonaOfUsuariosCollectionUsuarios != null) {
                    oldCodigoPersonaOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldCodigoPersonaOfUsuariosCollectionUsuarios = em.merge(oldCodigoPersonaOfUsuariosCollectionUsuarios);
                }
            }
            for (Telefonos telefonosCollectionTelefonos : datosPersonales.getTelefonosCollection()) {
                DatosPersonales oldCodigoPersonaOfTelefonosCollectionTelefonos = telefonosCollectionTelefonos.getCodigoPersona();
                telefonosCollectionTelefonos.setCodigoPersona(datosPersonales);
                telefonosCollectionTelefonos = em.merge(telefonosCollectionTelefonos);
                if (oldCodigoPersonaOfTelefonosCollectionTelefonos != null) {
                    oldCodigoPersonaOfTelefonosCollectionTelefonos.getTelefonosCollection().remove(telefonosCollectionTelefonos);
                    oldCodigoPersonaOfTelefonosCollectionTelefonos = em.merge(oldCodigoPersonaOfTelefonosCollectionTelefonos);
                }
            }
            for (DpCustom dpCustomCollectionDpCustom : datosPersonales.getDpCustomCollection()) {
                DatosPersonales oldDatosPersonalesOfDpCustomCollectionDpCustom = dpCustomCollectionDpCustom.getDatosPersonales();
                dpCustomCollectionDpCustom.setDatosPersonales(datosPersonales);
                dpCustomCollectionDpCustom = em.merge(dpCustomCollectionDpCustom);
                if (oldDatosPersonalesOfDpCustomCollectionDpCustom != null) {
                    oldDatosPersonalesOfDpCustomCollectionDpCustom.getDpCustomCollection().remove(dpCustomCollectionDpCustom);
                    oldDatosPersonalesOfDpCustomCollectionDpCustom = em.merge(oldDatosPersonalesOfDpCustomCollectionDpCustom);
                }
            }
            for (Proveedores proveedoresCollectionProveedores : datosPersonales.getProveedoresCollection()) {
                DatosPersonales oldCodigoPersonaOfProveedoresCollectionProveedores = proveedoresCollectionProveedores.getCodigoPersona();
                proveedoresCollectionProveedores.setCodigoPersona(datosPersonales);
                proveedoresCollectionProveedores = em.merge(proveedoresCollectionProveedores);
                if (oldCodigoPersonaOfProveedoresCollectionProveedores != null) {
                    oldCodigoPersonaOfProveedoresCollectionProveedores.getProveedoresCollection().remove(proveedoresCollectionProveedores);
                    oldCodigoPersonaOfProveedoresCollectionProveedores = em.merge(oldCodigoPersonaOfProveedoresCollectionProveedores);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DatosPersonales datosPersonales) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosPersonales persistentDatosPersonales = em.find(DatosPersonales.class, datosPersonales.getCodigo());
            TipoPersona tipoOld = persistentDatosPersonales.getTipo();
            TipoPersona tipoNew = datosPersonales.getTipo();
            Collection<Direcciones> direccionesCollectionOld = persistentDatosPersonales.getDireccionesCollection();
            Collection<Direcciones> direccionesCollectionNew = datosPersonales.getDireccionesCollection();
            Collection<Mails> mailsCollectionOld = persistentDatosPersonales.getMailsCollection();
            Collection<Mails> mailsCollectionNew = datosPersonales.getMailsCollection();
            Collection<Clientes> clientesCollectionOld = persistentDatosPersonales.getClientesCollection();
            Collection<Clientes> clientesCollectionNew = datosPersonales.getClientesCollection();
            Collection<Usuarios> usuariosCollectionOld = persistentDatosPersonales.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = datosPersonales.getUsuariosCollection();
            Collection<Telefonos> telefonosCollectionOld = persistentDatosPersonales.getTelefonosCollection();
            Collection<Telefonos> telefonosCollectionNew = datosPersonales.getTelefonosCollection();
            Collection<DpCustom> dpCustomCollectionOld = persistentDatosPersonales.getDpCustomCollection();
            Collection<DpCustom> dpCustomCollectionNew = datosPersonales.getDpCustomCollection();
            Collection<Proveedores> proveedoresCollectionOld = persistentDatosPersonales.getProveedoresCollection();
            Collection<Proveedores> proveedoresCollectionNew = datosPersonales.getProveedoresCollection();
            List<String> illegalOrphanMessages = null;
            for (Direcciones direccionesCollectionOldDirecciones : direccionesCollectionOld) {
                if (!direccionesCollectionNew.contains(direccionesCollectionOldDirecciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Direcciones " + direccionesCollectionOldDirecciones + " since its codigoPersona field is not nullable.");
                }
            }
            for (Mails mailsCollectionOldMails : mailsCollectionOld) {
                if (!mailsCollectionNew.contains(mailsCollectionOldMails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mails " + mailsCollectionOldMails + " since its codigoPersona field is not nullable.");
                }
            }
            for (Clientes clientesCollectionOldClientes : clientesCollectionOld) {
                if (!clientesCollectionNew.contains(clientesCollectionOldClientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clientes " + clientesCollectionOldClientes + " since its codigoPersona field is not nullable.");
                }
            }
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosCollectionOldUsuarios + " since its codigoPersona field is not nullable.");
                }
            }
            for (Telefonos telefonosCollectionOldTelefonos : telefonosCollectionOld) {
                if (!telefonosCollectionNew.contains(telefonosCollectionOldTelefonos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telefonos " + telefonosCollectionOldTelefonos + " since its codigoPersona field is not nullable.");
                }
            }
            for (DpCustom dpCustomCollectionOldDpCustom : dpCustomCollectionOld) {
                if (!dpCustomCollectionNew.contains(dpCustomCollectionOldDpCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DpCustom " + dpCustomCollectionOldDpCustom + " since its datosPersonales field is not nullable.");
                }
            }
            for (Proveedores proveedoresCollectionOldProveedores : proveedoresCollectionOld) {
                if (!proveedoresCollectionNew.contains(proveedoresCollectionOldProveedores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proveedores " + proveedoresCollectionOldProveedores + " since its codigoPersona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoNew != null) {
                tipoNew = em.getReference(tipoNew.getClass(), tipoNew.getId());
                datosPersonales.setTipo(tipoNew);
            }
            Collection<Direcciones> attachedDireccionesCollectionNew = new ArrayList<Direcciones>();
            for (Direcciones direccionesCollectionNewDireccionesToAttach : direccionesCollectionNew) {
                direccionesCollectionNewDireccionesToAttach = em.getReference(direccionesCollectionNewDireccionesToAttach.getClass(), direccionesCollectionNewDireccionesToAttach.getId());
                attachedDireccionesCollectionNew.add(direccionesCollectionNewDireccionesToAttach);
            }
            direccionesCollectionNew = attachedDireccionesCollectionNew;
            datosPersonales.setDireccionesCollection(direccionesCollectionNew);
            Collection<Mails> attachedMailsCollectionNew = new ArrayList<Mails>();
            for (Mails mailsCollectionNewMailsToAttach : mailsCollectionNew) {
                mailsCollectionNewMailsToAttach = em.getReference(mailsCollectionNewMailsToAttach.getClass(), mailsCollectionNewMailsToAttach.getId());
                attachedMailsCollectionNew.add(mailsCollectionNewMailsToAttach);
            }
            mailsCollectionNew = attachedMailsCollectionNew;
            datosPersonales.setMailsCollection(mailsCollectionNew);
            Collection<Clientes> attachedClientesCollectionNew = new ArrayList<Clientes>();
            for (Clientes clientesCollectionNewClientesToAttach : clientesCollectionNew) {
                clientesCollectionNewClientesToAttach = em.getReference(clientesCollectionNewClientesToAttach.getClass(), clientesCollectionNewClientesToAttach.getCodigo());
                attachedClientesCollectionNew.add(clientesCollectionNewClientesToAttach);
            }
            clientesCollectionNew = attachedClientesCollectionNew;
            datosPersonales.setClientesCollection(clientesCollectionNew);
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            datosPersonales.setUsuariosCollection(usuariosCollectionNew);
            Collection<Telefonos> attachedTelefonosCollectionNew = new ArrayList<Telefonos>();
            for (Telefonos telefonosCollectionNewTelefonosToAttach : telefonosCollectionNew) {
                telefonosCollectionNewTelefonosToAttach = em.getReference(telefonosCollectionNewTelefonosToAttach.getClass(), telefonosCollectionNewTelefonosToAttach.getId());
                attachedTelefonosCollectionNew.add(telefonosCollectionNewTelefonosToAttach);
            }
            telefonosCollectionNew = attachedTelefonosCollectionNew;
            datosPersonales.setTelefonosCollection(telefonosCollectionNew);
            Collection<DpCustom> attachedDpCustomCollectionNew = new ArrayList<DpCustom>();
            for (DpCustom dpCustomCollectionNewDpCustomToAttach : dpCustomCollectionNew) {
                dpCustomCollectionNewDpCustomToAttach = em.getReference(dpCustomCollectionNewDpCustomToAttach.getClass(), dpCustomCollectionNewDpCustomToAttach.getDpCustomPK());
                attachedDpCustomCollectionNew.add(dpCustomCollectionNewDpCustomToAttach);
            }
            dpCustomCollectionNew = attachedDpCustomCollectionNew;
            datosPersonales.setDpCustomCollection(dpCustomCollectionNew);
            Collection<Proveedores> attachedProveedoresCollectionNew = new ArrayList<Proveedores>();
            for (Proveedores proveedoresCollectionNewProveedoresToAttach : proveedoresCollectionNew) {
                proveedoresCollectionNewProveedoresToAttach = em.getReference(proveedoresCollectionNewProveedoresToAttach.getClass(), proveedoresCollectionNewProveedoresToAttach.getCodigo());
                attachedProveedoresCollectionNew.add(proveedoresCollectionNewProveedoresToAttach);
            }
            proveedoresCollectionNew = attachedProveedoresCollectionNew;
            datosPersonales.setProveedoresCollection(proveedoresCollectionNew);
            datosPersonales = em.merge(datosPersonales);
            if (tipoOld != null && !tipoOld.equals(tipoNew)) {
                tipoOld.getDatosPersonalesCollection().remove(datosPersonales);
                tipoOld = em.merge(tipoOld);
            }
            if (tipoNew != null && !tipoNew.equals(tipoOld)) {
                tipoNew.getDatosPersonalesCollection().add(datosPersonales);
                tipoNew = em.merge(tipoNew);
            }
            for (Direcciones direccionesCollectionNewDirecciones : direccionesCollectionNew) {
                if (!direccionesCollectionOld.contains(direccionesCollectionNewDirecciones)) {
                    DatosPersonales oldCodigoPersonaOfDireccionesCollectionNewDirecciones = direccionesCollectionNewDirecciones.getCodigoPersona();
                    direccionesCollectionNewDirecciones.setCodigoPersona(datosPersonales);
                    direccionesCollectionNewDirecciones = em.merge(direccionesCollectionNewDirecciones);
                    if (oldCodigoPersonaOfDireccionesCollectionNewDirecciones != null && !oldCodigoPersonaOfDireccionesCollectionNewDirecciones.equals(datosPersonales)) {
                        oldCodigoPersonaOfDireccionesCollectionNewDirecciones.getDireccionesCollection().remove(direccionesCollectionNewDirecciones);
                        oldCodigoPersonaOfDireccionesCollectionNewDirecciones = em.merge(oldCodigoPersonaOfDireccionesCollectionNewDirecciones);
                    }
                }
            }
            for (Mails mailsCollectionNewMails : mailsCollectionNew) {
                if (!mailsCollectionOld.contains(mailsCollectionNewMails)) {
                    DatosPersonales oldCodigoPersonaOfMailsCollectionNewMails = mailsCollectionNewMails.getCodigoPersona();
                    mailsCollectionNewMails.setCodigoPersona(datosPersonales);
                    mailsCollectionNewMails = em.merge(mailsCollectionNewMails);
                    if (oldCodigoPersonaOfMailsCollectionNewMails != null && !oldCodigoPersonaOfMailsCollectionNewMails.equals(datosPersonales)) {
                        oldCodigoPersonaOfMailsCollectionNewMails.getMailsCollection().remove(mailsCollectionNewMails);
                        oldCodigoPersonaOfMailsCollectionNewMails = em.merge(oldCodigoPersonaOfMailsCollectionNewMails);
                    }
                }
            }
            for (Clientes clientesCollectionNewClientes : clientesCollectionNew) {
                if (!clientesCollectionOld.contains(clientesCollectionNewClientes)) {
                    DatosPersonales oldCodigoPersonaOfClientesCollectionNewClientes = clientesCollectionNewClientes.getCodigoPersona();
                    clientesCollectionNewClientes.setCodigoPersona(datosPersonales);
                    clientesCollectionNewClientes = em.merge(clientesCollectionNewClientes);
                    if (oldCodigoPersonaOfClientesCollectionNewClientes != null && !oldCodigoPersonaOfClientesCollectionNewClientes.equals(datosPersonales)) {
                        oldCodigoPersonaOfClientesCollectionNewClientes.getClientesCollection().remove(clientesCollectionNewClientes);
                        oldCodigoPersonaOfClientesCollectionNewClientes = em.merge(oldCodigoPersonaOfClientesCollectionNewClientes);
                    }
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    DatosPersonales oldCodigoPersonaOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getCodigoPersona();
                    usuariosCollectionNewUsuarios.setCodigoPersona(datosPersonales);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldCodigoPersonaOfUsuariosCollectionNewUsuarios != null && !oldCodigoPersonaOfUsuariosCollectionNewUsuarios.equals(datosPersonales)) {
                        oldCodigoPersonaOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldCodigoPersonaOfUsuariosCollectionNewUsuarios = em.merge(oldCodigoPersonaOfUsuariosCollectionNewUsuarios);
                    }
                }
            }
            for (Telefonos telefonosCollectionNewTelefonos : telefonosCollectionNew) {
                if (!telefonosCollectionOld.contains(telefonosCollectionNewTelefonos)) {
                    DatosPersonales oldCodigoPersonaOfTelefonosCollectionNewTelefonos = telefonosCollectionNewTelefonos.getCodigoPersona();
                    telefonosCollectionNewTelefonos.setCodigoPersona(datosPersonales);
                    telefonosCollectionNewTelefonos = em.merge(telefonosCollectionNewTelefonos);
                    if (oldCodigoPersonaOfTelefonosCollectionNewTelefonos != null && !oldCodigoPersonaOfTelefonosCollectionNewTelefonos.equals(datosPersonales)) {
                        oldCodigoPersonaOfTelefonosCollectionNewTelefonos.getTelefonosCollection().remove(telefonosCollectionNewTelefonos);
                        oldCodigoPersonaOfTelefonosCollectionNewTelefonos = em.merge(oldCodigoPersonaOfTelefonosCollectionNewTelefonos);
                    }
                }
            }
            for (DpCustom dpCustomCollectionNewDpCustom : dpCustomCollectionNew) {
                if (!dpCustomCollectionOld.contains(dpCustomCollectionNewDpCustom)) {
                    DatosPersonales oldDatosPersonalesOfDpCustomCollectionNewDpCustom = dpCustomCollectionNewDpCustom.getDatosPersonales();
                    dpCustomCollectionNewDpCustom.setDatosPersonales(datosPersonales);
                    dpCustomCollectionNewDpCustom = em.merge(dpCustomCollectionNewDpCustom);
                    if (oldDatosPersonalesOfDpCustomCollectionNewDpCustom != null && !oldDatosPersonalesOfDpCustomCollectionNewDpCustom.equals(datosPersonales)) {
                        oldDatosPersonalesOfDpCustomCollectionNewDpCustom.getDpCustomCollection().remove(dpCustomCollectionNewDpCustom);
                        oldDatosPersonalesOfDpCustomCollectionNewDpCustom = em.merge(oldDatosPersonalesOfDpCustomCollectionNewDpCustom);
                    }
                }
            }
            for (Proveedores proveedoresCollectionNewProveedores : proveedoresCollectionNew) {
                if (!proveedoresCollectionOld.contains(proveedoresCollectionNewProveedores)) {
                    DatosPersonales oldCodigoPersonaOfProveedoresCollectionNewProveedores = proveedoresCollectionNewProveedores.getCodigoPersona();
                    proveedoresCollectionNewProveedores.setCodigoPersona(datosPersonales);
                    proveedoresCollectionNewProveedores = em.merge(proveedoresCollectionNewProveedores);
                    if (oldCodigoPersonaOfProveedoresCollectionNewProveedores != null && !oldCodigoPersonaOfProveedoresCollectionNewProveedores.equals(datosPersonales)) {
                        oldCodigoPersonaOfProveedoresCollectionNewProveedores.getProveedoresCollection().remove(proveedoresCollectionNewProveedores);
                        oldCodigoPersonaOfProveedoresCollectionNewProveedores = em.merge(oldCodigoPersonaOfProveedoresCollectionNewProveedores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datosPersonales.getCodigo();
                if (findDatosPersonales(id) == null) {
                    throw new NonexistentEntityException("The datosPersonales with id " + id + " no longer exists.");
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
            DatosPersonales datosPersonales;
            try {
                datosPersonales = em.getReference(DatosPersonales.class, id);
                datosPersonales.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datosPersonales with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Direcciones> direccionesCollectionOrphanCheck = datosPersonales.getDireccionesCollection();
            for (Direcciones direccionesCollectionOrphanCheckDirecciones : direccionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DatosPersonales (" + datosPersonales + ") cannot be destroyed since the Direcciones " + direccionesCollectionOrphanCheckDirecciones + " in its direccionesCollection field has a non-nullable codigoPersona field.");
            }
            Collection<Mails> mailsCollectionOrphanCheck = datosPersonales.getMailsCollection();
            for (Mails mailsCollectionOrphanCheckMails : mailsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DatosPersonales (" + datosPersonales + ") cannot be destroyed since the Mails " + mailsCollectionOrphanCheckMails + " in its mailsCollection field has a non-nullable codigoPersona field.");
            }
            Collection<Clientes> clientesCollectionOrphanCheck = datosPersonales.getClientesCollection();
            for (Clientes clientesCollectionOrphanCheckClientes : clientesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DatosPersonales (" + datosPersonales + ") cannot be destroyed since the Clientes " + clientesCollectionOrphanCheckClientes + " in its clientesCollection field has a non-nullable codigoPersona field.");
            }
            Collection<Usuarios> usuariosCollectionOrphanCheck = datosPersonales.getUsuariosCollection();
            for (Usuarios usuariosCollectionOrphanCheckUsuarios : usuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DatosPersonales (" + datosPersonales + ") cannot be destroyed since the Usuarios " + usuariosCollectionOrphanCheckUsuarios + " in its usuariosCollection field has a non-nullable codigoPersona field.");
            }
            Collection<Telefonos> telefonosCollectionOrphanCheck = datosPersonales.getTelefonosCollection();
            for (Telefonos telefonosCollectionOrphanCheckTelefonos : telefonosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DatosPersonales (" + datosPersonales + ") cannot be destroyed since the Telefonos " + telefonosCollectionOrphanCheckTelefonos + " in its telefonosCollection field has a non-nullable codigoPersona field.");
            }
            Collection<DpCustom> dpCustomCollectionOrphanCheck = datosPersonales.getDpCustomCollection();
            for (DpCustom dpCustomCollectionOrphanCheckDpCustom : dpCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DatosPersonales (" + datosPersonales + ") cannot be destroyed since the DpCustom " + dpCustomCollectionOrphanCheckDpCustom + " in its dpCustomCollection field has a non-nullable datosPersonales field.");
            }
            Collection<Proveedores> proveedoresCollectionOrphanCheck = datosPersonales.getProveedoresCollection();
            for (Proveedores proveedoresCollectionOrphanCheckProveedores : proveedoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DatosPersonales (" + datosPersonales + ") cannot be destroyed since the Proveedores " + proveedoresCollectionOrphanCheckProveedores + " in its proveedoresCollection field has a non-nullable codigoPersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoPersona tipo = datosPersonales.getTipo();
            if (tipo != null) {
                tipo.getDatosPersonalesCollection().remove(datosPersonales);
                tipo = em.merge(tipo);
            }
            em.remove(datosPersonales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatosPersonales> findDatosPersonalesEntities() {
        return findDatosPersonalesEntities(true, -1, -1);
    }

    public List<DatosPersonales> findDatosPersonalesEntities(int maxResults, int firstResult) {
        return findDatosPersonalesEntities(false, maxResults, firstResult);
    }

    private List<DatosPersonales> findDatosPersonalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from DatosPersonales as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DatosPersonales findDatosPersonales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatosPersonales.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatosPersonalesCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from DatosPersonales as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
