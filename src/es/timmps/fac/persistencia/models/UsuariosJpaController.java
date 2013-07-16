/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.DatosPersonales;
import es.timmps.fac.persistencia.Grupos;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.ProvPedidosCab;
import es.timmps.fac.persistencia.CliAlbCab;
import es.timmps.fac.persistencia.RegistroLog;
import es.timmps.fac.persistencia.ProvFacCab;
import es.timmps.fac.persistencia.ProvAlbCab;
import es.timmps.fac.persistencia.CliFacCab;
import es.timmps.fac.persistencia.CliPedidosCab;
import es.timmps.fac.persistencia.Roles;
import es.timmps.fac.persistencia.UsuGrupo;
import es.timmps.fac.persistencia.EmpUsu;
import es.timmps.fac.persistencia.Usuarios;
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
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws PreexistingEntityException, Exception {
        if (usuarios.getGruposCollection() == null) {
            usuarios.setGruposCollection(new ArrayList<Grupos>());
        }
        if (usuarios.getEmpresasCollection() == null) {
            usuarios.setEmpresasCollection(new ArrayList<Empresas>());
        }
        if (usuarios.getProvPedidosCabCollection() == null) {
            usuarios.setProvPedidosCabCollection(new ArrayList<ProvPedidosCab>());
        }
        if (usuarios.getCliAlbCabCollection() == null) {
            usuarios.setCliAlbCabCollection(new ArrayList<CliAlbCab>());
        }
        if (usuarios.getRegistroLogCollection() == null) {
            usuarios.setRegistroLogCollection(new ArrayList<RegistroLog>());
        }
        if (usuarios.getProvFacCabCollection() == null) {
            usuarios.setProvFacCabCollection(new ArrayList<ProvFacCab>());
        }
        if (usuarios.getProvAlbCabCollection() == null) {
            usuarios.setProvAlbCabCollection(new ArrayList<ProvAlbCab>());
        }
        if (usuarios.getCliFacCabCollection() == null) {
            usuarios.setCliFacCabCollection(new ArrayList<CliFacCab>());
        }
        if (usuarios.getCliPedidosCabCollection() == null) {
            usuarios.setCliPedidosCabCollection(new ArrayList<CliPedidosCab>());
        }
        if (usuarios.getRolesCollection() == null) {
            usuarios.setRolesCollection(new ArrayList<Roles>());
        }
        if (usuarios.getUsuGrupoCollection() == null) {
            usuarios.setUsuGrupoCollection(new ArrayList<UsuGrupo>());
        }
        if (usuarios.getEmpUsuCollection() == null) {
            usuarios.setEmpUsuCollection(new ArrayList<EmpUsu>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosPersonales codigoPersona = usuarios.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona = em.getReference(codigoPersona.getClass(), codigoPersona.getCodigo());
                usuarios.setCodigoPersona(codigoPersona);
            }
            Collection<Grupos> attachedGruposCollection = new ArrayList<Grupos>();
            for (Grupos gruposCollectionGruposToAttach : usuarios.getGruposCollection()) {
                gruposCollectionGruposToAttach = em.getReference(gruposCollectionGruposToAttach.getClass(), gruposCollectionGruposToAttach.getId());
                attachedGruposCollection.add(gruposCollectionGruposToAttach);
            }
            usuarios.setGruposCollection(attachedGruposCollection);
            Collection<Empresas> attachedEmpresasCollection = new ArrayList<Empresas>();
            for (Empresas empresasCollectionEmpresasToAttach : usuarios.getEmpresasCollection()) {
                empresasCollectionEmpresasToAttach = em.getReference(empresasCollectionEmpresasToAttach.getClass(), empresasCollectionEmpresasToAttach.getCodigo());
                attachedEmpresasCollection.add(empresasCollectionEmpresasToAttach);
            }
            usuarios.setEmpresasCollection(attachedEmpresasCollection);
            Collection<ProvPedidosCab> attachedProvPedidosCabCollection = new ArrayList<ProvPedidosCab>();
            for (ProvPedidosCab provPedidosCabCollectionProvPedidosCabToAttach : usuarios.getProvPedidosCabCollection()) {
                provPedidosCabCollectionProvPedidosCabToAttach = em.getReference(provPedidosCabCollectionProvPedidosCabToAttach.getClass(), provPedidosCabCollectionProvPedidosCabToAttach.getId());
                attachedProvPedidosCabCollection.add(provPedidosCabCollectionProvPedidosCabToAttach);
            }
            usuarios.setProvPedidosCabCollection(attachedProvPedidosCabCollection);
            Collection<CliAlbCab> attachedCliAlbCabCollection = new ArrayList<CliAlbCab>();
            for (CliAlbCab cliAlbCabCollectionCliAlbCabToAttach : usuarios.getCliAlbCabCollection()) {
                cliAlbCabCollectionCliAlbCabToAttach = em.getReference(cliAlbCabCollectionCliAlbCabToAttach.getClass(), cliAlbCabCollectionCliAlbCabToAttach.getId());
                attachedCliAlbCabCollection.add(cliAlbCabCollectionCliAlbCabToAttach);
            }
            usuarios.setCliAlbCabCollection(attachedCliAlbCabCollection);
            Collection<RegistroLog> attachedRegistroLogCollection = new ArrayList<RegistroLog>();
            for (RegistroLog registroLogCollectionRegistroLogToAttach : usuarios.getRegistroLogCollection()) {
                registroLogCollectionRegistroLogToAttach = em.getReference(registroLogCollectionRegistroLogToAttach.getClass(), registroLogCollectionRegistroLogToAttach.getId());
                attachedRegistroLogCollection.add(registroLogCollectionRegistroLogToAttach);
            }
            usuarios.setRegistroLogCollection(attachedRegistroLogCollection);
            Collection<ProvFacCab> attachedProvFacCabCollection = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionProvFacCabToAttach : usuarios.getProvFacCabCollection()) {
                provFacCabCollectionProvFacCabToAttach = em.getReference(provFacCabCollectionProvFacCabToAttach.getClass(), provFacCabCollectionProvFacCabToAttach.getId());
                attachedProvFacCabCollection.add(provFacCabCollectionProvFacCabToAttach);
            }
            usuarios.setProvFacCabCollection(attachedProvFacCabCollection);
            Collection<ProvAlbCab> attachedProvAlbCabCollection = new ArrayList<ProvAlbCab>();
            for (ProvAlbCab provAlbCabCollectionProvAlbCabToAttach : usuarios.getProvAlbCabCollection()) {
                provAlbCabCollectionProvAlbCabToAttach = em.getReference(provAlbCabCollectionProvAlbCabToAttach.getClass(), provAlbCabCollectionProvAlbCabToAttach.getId());
                attachedProvAlbCabCollection.add(provAlbCabCollectionProvAlbCabToAttach);
            }
            usuarios.setProvAlbCabCollection(attachedProvAlbCabCollection);
            Collection<CliFacCab> attachedCliFacCabCollection = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionCliFacCabToAttach : usuarios.getCliFacCabCollection()) {
                cliFacCabCollectionCliFacCabToAttach = em.getReference(cliFacCabCollectionCliFacCabToAttach.getClass(), cliFacCabCollectionCliFacCabToAttach.getId());
                attachedCliFacCabCollection.add(cliFacCabCollectionCliFacCabToAttach);
            }
            usuarios.setCliFacCabCollection(attachedCliFacCabCollection);
            Collection<CliPedidosCab> attachedCliPedidosCabCollection = new ArrayList<CliPedidosCab>();
            for (CliPedidosCab cliPedidosCabCollectionCliPedidosCabToAttach : usuarios.getCliPedidosCabCollection()) {
                cliPedidosCabCollectionCliPedidosCabToAttach = em.getReference(cliPedidosCabCollectionCliPedidosCabToAttach.getClass(), cliPedidosCabCollectionCliPedidosCabToAttach.getId());
                attachedCliPedidosCabCollection.add(cliPedidosCabCollectionCliPedidosCabToAttach);
            }
            usuarios.setCliPedidosCabCollection(attachedCliPedidosCabCollection);
            Collection<Roles> attachedRolesCollection = new ArrayList<Roles>();
            for (Roles rolesCollectionRolesToAttach : usuarios.getRolesCollection()) {
                rolesCollectionRolesToAttach = em.getReference(rolesCollectionRolesToAttach.getClass(), rolesCollectionRolesToAttach.getId());
                attachedRolesCollection.add(rolesCollectionRolesToAttach);
            }
            usuarios.setRolesCollection(attachedRolesCollection);
            Collection<UsuGrupo> attachedUsuGrupoCollection = new ArrayList<UsuGrupo>();
            for (UsuGrupo usuGrupoCollectionUsuGrupoToAttach : usuarios.getUsuGrupoCollection()) {
                usuGrupoCollectionUsuGrupoToAttach = em.getReference(usuGrupoCollectionUsuGrupoToAttach.getClass(), usuGrupoCollectionUsuGrupoToAttach.getUsuGrupoPK());
                attachedUsuGrupoCollection.add(usuGrupoCollectionUsuGrupoToAttach);
            }
            usuarios.setUsuGrupoCollection(attachedUsuGrupoCollection);
            Collection<EmpUsu> attachedEmpUsuCollection = new ArrayList<EmpUsu>();
            for (EmpUsu empUsuCollectionEmpUsuToAttach : usuarios.getEmpUsuCollection()) {
                empUsuCollectionEmpUsuToAttach = em.getReference(empUsuCollectionEmpUsuToAttach.getClass(), empUsuCollectionEmpUsuToAttach.getEmpUsuPK());
                attachedEmpUsuCollection.add(empUsuCollectionEmpUsuToAttach);
            }
            usuarios.setEmpUsuCollection(attachedEmpUsuCollection);
            em.persist(usuarios);
            if (codigoPersona != null) {
                codigoPersona.getUsuariosCollection().add(usuarios);
                codigoPersona = em.merge(codigoPersona);
            }
            for (Grupos gruposCollectionGrupos : usuarios.getGruposCollection()) {
                gruposCollectionGrupos.getUsuariosCollection().add(usuarios);
                gruposCollectionGrupos = em.merge(gruposCollectionGrupos);
            }
            for (Empresas empresasCollectionEmpresas : usuarios.getEmpresasCollection()) {
                empresasCollectionEmpresas.getUsuariosCollection().add(usuarios);
                empresasCollectionEmpresas = em.merge(empresasCollectionEmpresas);
            }
            for (ProvPedidosCab provPedidosCabCollectionProvPedidosCab : usuarios.getProvPedidosCabCollection()) {
                Usuarios oldUsuarioOfProvPedidosCabCollectionProvPedidosCab = provPedidosCabCollectionProvPedidosCab.getUsuario();
                provPedidosCabCollectionProvPedidosCab.setUsuario(usuarios);
                provPedidosCabCollectionProvPedidosCab = em.merge(provPedidosCabCollectionProvPedidosCab);
                if (oldUsuarioOfProvPedidosCabCollectionProvPedidosCab != null) {
                    oldUsuarioOfProvPedidosCabCollectionProvPedidosCab.getProvPedidosCabCollection().remove(provPedidosCabCollectionProvPedidosCab);
                    oldUsuarioOfProvPedidosCabCollectionProvPedidosCab = em.merge(oldUsuarioOfProvPedidosCabCollectionProvPedidosCab);
                }
            }
            for (CliAlbCab cliAlbCabCollectionCliAlbCab : usuarios.getCliAlbCabCollection()) {
                Usuarios oldUsuarioOfCliAlbCabCollectionCliAlbCab = cliAlbCabCollectionCliAlbCab.getUsuario();
                cliAlbCabCollectionCliAlbCab.setUsuario(usuarios);
                cliAlbCabCollectionCliAlbCab = em.merge(cliAlbCabCollectionCliAlbCab);
                if (oldUsuarioOfCliAlbCabCollectionCliAlbCab != null) {
                    oldUsuarioOfCliAlbCabCollectionCliAlbCab.getCliAlbCabCollection().remove(cliAlbCabCollectionCliAlbCab);
                    oldUsuarioOfCliAlbCabCollectionCliAlbCab = em.merge(oldUsuarioOfCliAlbCabCollectionCliAlbCab);
                }
            }
            for (RegistroLog registroLogCollectionRegistroLog : usuarios.getRegistroLogCollection()) {
                Usuarios oldUsuarioOfRegistroLogCollectionRegistroLog = registroLogCollectionRegistroLog.getUsuario();
                registroLogCollectionRegistroLog.setUsuario(usuarios);
                registroLogCollectionRegistroLog = em.merge(registroLogCollectionRegistroLog);
                if (oldUsuarioOfRegistroLogCollectionRegistroLog != null) {
                    oldUsuarioOfRegistroLogCollectionRegistroLog.getRegistroLogCollection().remove(registroLogCollectionRegistroLog);
                    oldUsuarioOfRegistroLogCollectionRegistroLog = em.merge(oldUsuarioOfRegistroLogCollectionRegistroLog);
                }
            }
            for (ProvFacCab provFacCabCollectionProvFacCab : usuarios.getProvFacCabCollection()) {
                Usuarios oldUsuarioOfProvFacCabCollectionProvFacCab = provFacCabCollectionProvFacCab.getUsuario();
                provFacCabCollectionProvFacCab.setUsuario(usuarios);
                provFacCabCollectionProvFacCab = em.merge(provFacCabCollectionProvFacCab);
                if (oldUsuarioOfProvFacCabCollectionProvFacCab != null) {
                    oldUsuarioOfProvFacCabCollectionProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionProvFacCab);
                    oldUsuarioOfProvFacCabCollectionProvFacCab = em.merge(oldUsuarioOfProvFacCabCollectionProvFacCab);
                }
            }
            for (ProvAlbCab provAlbCabCollectionProvAlbCab : usuarios.getProvAlbCabCollection()) {
                Usuarios oldUsuarioOfProvAlbCabCollectionProvAlbCab = provAlbCabCollectionProvAlbCab.getUsuario();
                provAlbCabCollectionProvAlbCab.setUsuario(usuarios);
                provAlbCabCollectionProvAlbCab = em.merge(provAlbCabCollectionProvAlbCab);
                if (oldUsuarioOfProvAlbCabCollectionProvAlbCab != null) {
                    oldUsuarioOfProvAlbCabCollectionProvAlbCab.getProvAlbCabCollection().remove(provAlbCabCollectionProvAlbCab);
                    oldUsuarioOfProvAlbCabCollectionProvAlbCab = em.merge(oldUsuarioOfProvAlbCabCollectionProvAlbCab);
                }
            }
            for (CliFacCab cliFacCabCollectionCliFacCab : usuarios.getCliFacCabCollection()) {
                Usuarios oldUsuarioOfCliFacCabCollectionCliFacCab = cliFacCabCollectionCliFacCab.getUsuario();
                cliFacCabCollectionCliFacCab.setUsuario(usuarios);
                cliFacCabCollectionCliFacCab = em.merge(cliFacCabCollectionCliFacCab);
                if (oldUsuarioOfCliFacCabCollectionCliFacCab != null) {
                    oldUsuarioOfCliFacCabCollectionCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionCliFacCab);
                    oldUsuarioOfCliFacCabCollectionCliFacCab = em.merge(oldUsuarioOfCliFacCabCollectionCliFacCab);
                }
            }
            for (CliPedidosCab cliPedidosCabCollectionCliPedidosCab : usuarios.getCliPedidosCabCollection()) {
                Usuarios oldUsuarioOfCliPedidosCabCollectionCliPedidosCab = cliPedidosCabCollectionCliPedidosCab.getUsuario();
                cliPedidosCabCollectionCliPedidosCab.setUsuario(usuarios);
                cliPedidosCabCollectionCliPedidosCab = em.merge(cliPedidosCabCollectionCliPedidosCab);
                if (oldUsuarioOfCliPedidosCabCollectionCliPedidosCab != null) {
                    oldUsuarioOfCliPedidosCabCollectionCliPedidosCab.getCliPedidosCabCollection().remove(cliPedidosCabCollectionCliPedidosCab);
                    oldUsuarioOfCliPedidosCabCollectionCliPedidosCab = em.merge(oldUsuarioOfCliPedidosCabCollectionCliPedidosCab);
                }
            }
            for (Roles rolesCollectionRoles : usuarios.getRolesCollection()) {
                rolesCollectionRoles.getUsuariosCollection().add(usuarios);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
            }
            for (UsuGrupo usuGrupoCollectionUsuGrupo : usuarios.getUsuGrupoCollection()) {
                Usuarios oldUsuariosOfUsuGrupoCollectionUsuGrupo = usuGrupoCollectionUsuGrupo.getUsuarios();
                usuGrupoCollectionUsuGrupo.setUsuarios(usuarios);
                usuGrupoCollectionUsuGrupo = em.merge(usuGrupoCollectionUsuGrupo);
                if (oldUsuariosOfUsuGrupoCollectionUsuGrupo != null) {
                    oldUsuariosOfUsuGrupoCollectionUsuGrupo.getUsuGrupoCollection().remove(usuGrupoCollectionUsuGrupo);
                    oldUsuariosOfUsuGrupoCollectionUsuGrupo = em.merge(oldUsuariosOfUsuGrupoCollectionUsuGrupo);
                }
            }
            for (EmpUsu empUsuCollectionEmpUsu : usuarios.getEmpUsuCollection()) {
                Usuarios oldUsuariosOfEmpUsuCollectionEmpUsu = empUsuCollectionEmpUsu.getUsuarios();
                empUsuCollectionEmpUsu.setUsuarios(usuarios);
                empUsuCollectionEmpUsu = em.merge(empUsuCollectionEmpUsu);
                if (oldUsuariosOfEmpUsuCollectionEmpUsu != null) {
                    oldUsuariosOfEmpUsuCollectionEmpUsu.getEmpUsuCollection().remove(empUsuCollectionEmpUsu);
                    oldUsuariosOfEmpUsuCollectionEmpUsu = em.merge(oldUsuariosOfEmpUsuCollectionEmpUsu);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarios(usuarios.getId()) != null) {
                throw new PreexistingEntityException("Usuarios " + usuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            DatosPersonales codigoPersonaOld = persistentUsuarios.getCodigoPersona();
            DatosPersonales codigoPersonaNew = usuarios.getCodigoPersona();
            Collection<Grupos> gruposCollectionOld = persistentUsuarios.getGruposCollection();
            Collection<Grupos> gruposCollectionNew = usuarios.getGruposCollection();
            Collection<Empresas> empresasCollectionOld = persistentUsuarios.getEmpresasCollection();
            Collection<Empresas> empresasCollectionNew = usuarios.getEmpresasCollection();
            Collection<ProvPedidosCab> provPedidosCabCollectionOld = persistentUsuarios.getProvPedidosCabCollection();
            Collection<ProvPedidosCab> provPedidosCabCollectionNew = usuarios.getProvPedidosCabCollection();
            Collection<CliAlbCab> cliAlbCabCollectionOld = persistentUsuarios.getCliAlbCabCollection();
            Collection<CliAlbCab> cliAlbCabCollectionNew = usuarios.getCliAlbCabCollection();
            Collection<RegistroLog> registroLogCollectionOld = persistentUsuarios.getRegistroLogCollection();
            Collection<RegistroLog> registroLogCollectionNew = usuarios.getRegistroLogCollection();
            Collection<ProvFacCab> provFacCabCollectionOld = persistentUsuarios.getProvFacCabCollection();
            Collection<ProvFacCab> provFacCabCollectionNew = usuarios.getProvFacCabCollection();
            Collection<ProvAlbCab> provAlbCabCollectionOld = persistentUsuarios.getProvAlbCabCollection();
            Collection<ProvAlbCab> provAlbCabCollectionNew = usuarios.getProvAlbCabCollection();
            Collection<CliFacCab> cliFacCabCollectionOld = persistentUsuarios.getCliFacCabCollection();
            Collection<CliFacCab> cliFacCabCollectionNew = usuarios.getCliFacCabCollection();
            Collection<CliPedidosCab> cliPedidosCabCollectionOld = persistentUsuarios.getCliPedidosCabCollection();
            Collection<CliPedidosCab> cliPedidosCabCollectionNew = usuarios.getCliPedidosCabCollection();
            Collection<Roles> rolesCollectionOld = persistentUsuarios.getRolesCollection();
            Collection<Roles> rolesCollectionNew = usuarios.getRolesCollection();
            Collection<UsuGrupo> usuGrupoCollectionOld = persistentUsuarios.getUsuGrupoCollection();
            Collection<UsuGrupo> usuGrupoCollectionNew = usuarios.getUsuGrupoCollection();
            Collection<EmpUsu> empUsuCollectionOld = persistentUsuarios.getEmpUsuCollection();
            Collection<EmpUsu> empUsuCollectionNew = usuarios.getEmpUsuCollection();
            List<String> illegalOrphanMessages = null;
            for (ProvPedidosCab provPedidosCabCollectionOldProvPedidosCab : provPedidosCabCollectionOld) {
                if (!provPedidosCabCollectionNew.contains(provPedidosCabCollectionOldProvPedidosCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvPedidosCab " + provPedidosCabCollectionOldProvPedidosCab + " since its usuario field is not nullable.");
                }
            }
            for (CliAlbCab cliAlbCabCollectionOldCliAlbCab : cliAlbCabCollectionOld) {
                if (!cliAlbCabCollectionNew.contains(cliAlbCabCollectionOldCliAlbCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliAlbCab " + cliAlbCabCollectionOldCliAlbCab + " since its usuario field is not nullable.");
                }
            }
            for (RegistroLog registroLogCollectionOldRegistroLog : registroLogCollectionOld) {
                if (!registroLogCollectionNew.contains(registroLogCollectionOldRegistroLog)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegistroLog " + registroLogCollectionOldRegistroLog + " since its usuario field is not nullable.");
                }
            }
            for (ProvFacCab provFacCabCollectionOldProvFacCab : provFacCabCollectionOld) {
                if (!provFacCabCollectionNew.contains(provFacCabCollectionOldProvFacCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvFacCab " + provFacCabCollectionOldProvFacCab + " since its usuario field is not nullable.");
                }
            }
            for (ProvAlbCab provAlbCabCollectionOldProvAlbCab : provAlbCabCollectionOld) {
                if (!provAlbCabCollectionNew.contains(provAlbCabCollectionOldProvAlbCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvAlbCab " + provAlbCabCollectionOldProvAlbCab + " since its usuario field is not nullable.");
                }
            }
            for (CliFacCab cliFacCabCollectionOldCliFacCab : cliFacCabCollectionOld) {
                if (!cliFacCabCollectionNew.contains(cliFacCabCollectionOldCliFacCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliFacCab " + cliFacCabCollectionOldCliFacCab + " since its usuario field is not nullable.");
                }
            }
            for (CliPedidosCab cliPedidosCabCollectionOldCliPedidosCab : cliPedidosCabCollectionOld) {
                if (!cliPedidosCabCollectionNew.contains(cliPedidosCabCollectionOldCliPedidosCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliPedidosCab " + cliPedidosCabCollectionOldCliPedidosCab + " since its usuario field is not nullable.");
                }
            }
            for (UsuGrupo usuGrupoCollectionOldUsuGrupo : usuGrupoCollectionOld) {
                if (!usuGrupoCollectionNew.contains(usuGrupoCollectionOldUsuGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuGrupo " + usuGrupoCollectionOldUsuGrupo + " since its usuarios field is not nullable.");
                }
            }
            for (EmpUsu empUsuCollectionOldEmpUsu : empUsuCollectionOld) {
                if (!empUsuCollectionNew.contains(empUsuCollectionOldEmpUsu)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EmpUsu " + empUsuCollectionOldEmpUsu + " since its usuarios field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoPersonaNew != null) {
                codigoPersonaNew = em.getReference(codigoPersonaNew.getClass(), codigoPersonaNew.getCodigo());
                usuarios.setCodigoPersona(codigoPersonaNew);
            }
            Collection<Grupos> attachedGruposCollectionNew = new ArrayList<Grupos>();
            for (Grupos gruposCollectionNewGruposToAttach : gruposCollectionNew) {
                gruposCollectionNewGruposToAttach = em.getReference(gruposCollectionNewGruposToAttach.getClass(), gruposCollectionNewGruposToAttach.getId());
                attachedGruposCollectionNew.add(gruposCollectionNewGruposToAttach);
            }
            gruposCollectionNew = attachedGruposCollectionNew;
            usuarios.setGruposCollection(gruposCollectionNew);
            Collection<Empresas> attachedEmpresasCollectionNew = new ArrayList<Empresas>();
            for (Empresas empresasCollectionNewEmpresasToAttach : empresasCollectionNew) {
                empresasCollectionNewEmpresasToAttach = em.getReference(empresasCollectionNewEmpresasToAttach.getClass(), empresasCollectionNewEmpresasToAttach.getCodigo());
                attachedEmpresasCollectionNew.add(empresasCollectionNewEmpresasToAttach);
            }
            empresasCollectionNew = attachedEmpresasCollectionNew;
            usuarios.setEmpresasCollection(empresasCollectionNew);
            Collection<ProvPedidosCab> attachedProvPedidosCabCollectionNew = new ArrayList<ProvPedidosCab>();
            for (ProvPedidosCab provPedidosCabCollectionNewProvPedidosCabToAttach : provPedidosCabCollectionNew) {
                provPedidosCabCollectionNewProvPedidosCabToAttach = em.getReference(provPedidosCabCollectionNewProvPedidosCabToAttach.getClass(), provPedidosCabCollectionNewProvPedidosCabToAttach.getId());
                attachedProvPedidosCabCollectionNew.add(provPedidosCabCollectionNewProvPedidosCabToAttach);
            }
            provPedidosCabCollectionNew = attachedProvPedidosCabCollectionNew;
            usuarios.setProvPedidosCabCollection(provPedidosCabCollectionNew);
            Collection<CliAlbCab> attachedCliAlbCabCollectionNew = new ArrayList<CliAlbCab>();
            for (CliAlbCab cliAlbCabCollectionNewCliAlbCabToAttach : cliAlbCabCollectionNew) {
                cliAlbCabCollectionNewCliAlbCabToAttach = em.getReference(cliAlbCabCollectionNewCliAlbCabToAttach.getClass(), cliAlbCabCollectionNewCliAlbCabToAttach.getId());
                attachedCliAlbCabCollectionNew.add(cliAlbCabCollectionNewCliAlbCabToAttach);
            }
            cliAlbCabCollectionNew = attachedCliAlbCabCollectionNew;
            usuarios.setCliAlbCabCollection(cliAlbCabCollectionNew);
            Collection<RegistroLog> attachedRegistroLogCollectionNew = new ArrayList<RegistroLog>();
            for (RegistroLog registroLogCollectionNewRegistroLogToAttach : registroLogCollectionNew) {
                registroLogCollectionNewRegistroLogToAttach = em.getReference(registroLogCollectionNewRegistroLogToAttach.getClass(), registroLogCollectionNewRegistroLogToAttach.getId());
                attachedRegistroLogCollectionNew.add(registroLogCollectionNewRegistroLogToAttach);
            }
            registroLogCollectionNew = attachedRegistroLogCollectionNew;
            usuarios.setRegistroLogCollection(registroLogCollectionNew);
            Collection<ProvFacCab> attachedProvFacCabCollectionNew = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionNewProvFacCabToAttach : provFacCabCollectionNew) {
                provFacCabCollectionNewProvFacCabToAttach = em.getReference(provFacCabCollectionNewProvFacCabToAttach.getClass(), provFacCabCollectionNewProvFacCabToAttach.getId());
                attachedProvFacCabCollectionNew.add(provFacCabCollectionNewProvFacCabToAttach);
            }
            provFacCabCollectionNew = attachedProvFacCabCollectionNew;
            usuarios.setProvFacCabCollection(provFacCabCollectionNew);
            Collection<ProvAlbCab> attachedProvAlbCabCollectionNew = new ArrayList<ProvAlbCab>();
            for (ProvAlbCab provAlbCabCollectionNewProvAlbCabToAttach : provAlbCabCollectionNew) {
                provAlbCabCollectionNewProvAlbCabToAttach = em.getReference(provAlbCabCollectionNewProvAlbCabToAttach.getClass(), provAlbCabCollectionNewProvAlbCabToAttach.getId());
                attachedProvAlbCabCollectionNew.add(provAlbCabCollectionNewProvAlbCabToAttach);
            }
            provAlbCabCollectionNew = attachedProvAlbCabCollectionNew;
            usuarios.setProvAlbCabCollection(provAlbCabCollectionNew);
            Collection<CliFacCab> attachedCliFacCabCollectionNew = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionNewCliFacCabToAttach : cliFacCabCollectionNew) {
                cliFacCabCollectionNewCliFacCabToAttach = em.getReference(cliFacCabCollectionNewCliFacCabToAttach.getClass(), cliFacCabCollectionNewCliFacCabToAttach.getId());
                attachedCliFacCabCollectionNew.add(cliFacCabCollectionNewCliFacCabToAttach);
            }
            cliFacCabCollectionNew = attachedCliFacCabCollectionNew;
            usuarios.setCliFacCabCollection(cliFacCabCollectionNew);
            Collection<CliPedidosCab> attachedCliPedidosCabCollectionNew = new ArrayList<CliPedidosCab>();
            for (CliPedidosCab cliPedidosCabCollectionNewCliPedidosCabToAttach : cliPedidosCabCollectionNew) {
                cliPedidosCabCollectionNewCliPedidosCabToAttach = em.getReference(cliPedidosCabCollectionNewCliPedidosCabToAttach.getClass(), cliPedidosCabCollectionNewCliPedidosCabToAttach.getId());
                attachedCliPedidosCabCollectionNew.add(cliPedidosCabCollectionNewCliPedidosCabToAttach);
            }
            cliPedidosCabCollectionNew = attachedCliPedidosCabCollectionNew;
            usuarios.setCliPedidosCabCollection(cliPedidosCabCollectionNew);
            Collection<Roles> attachedRolesCollectionNew = new ArrayList<Roles>();
            for (Roles rolesCollectionNewRolesToAttach : rolesCollectionNew) {
                rolesCollectionNewRolesToAttach = em.getReference(rolesCollectionNewRolesToAttach.getClass(), rolesCollectionNewRolesToAttach.getId());
                attachedRolesCollectionNew.add(rolesCollectionNewRolesToAttach);
            }
            rolesCollectionNew = attachedRolesCollectionNew;
            usuarios.setRolesCollection(rolesCollectionNew);
            Collection<UsuGrupo> attachedUsuGrupoCollectionNew = new ArrayList<UsuGrupo>();
            for (UsuGrupo usuGrupoCollectionNewUsuGrupoToAttach : usuGrupoCollectionNew) {
                usuGrupoCollectionNewUsuGrupoToAttach = em.getReference(usuGrupoCollectionNewUsuGrupoToAttach.getClass(), usuGrupoCollectionNewUsuGrupoToAttach.getUsuGrupoPK());
                attachedUsuGrupoCollectionNew.add(usuGrupoCollectionNewUsuGrupoToAttach);
            }
            usuGrupoCollectionNew = attachedUsuGrupoCollectionNew;
            usuarios.setUsuGrupoCollection(usuGrupoCollectionNew);
            Collection<EmpUsu> attachedEmpUsuCollectionNew = new ArrayList<EmpUsu>();
            for (EmpUsu empUsuCollectionNewEmpUsuToAttach : empUsuCollectionNew) {
                empUsuCollectionNewEmpUsuToAttach = em.getReference(empUsuCollectionNewEmpUsuToAttach.getClass(), empUsuCollectionNewEmpUsuToAttach.getEmpUsuPK());
                attachedEmpUsuCollectionNew.add(empUsuCollectionNewEmpUsuToAttach);
            }
            empUsuCollectionNew = attachedEmpUsuCollectionNew;
            usuarios.setEmpUsuCollection(empUsuCollectionNew);
            usuarios = em.merge(usuarios);
            if (codigoPersonaOld != null && !codigoPersonaOld.equals(codigoPersonaNew)) {
                codigoPersonaOld.getUsuariosCollection().remove(usuarios);
                codigoPersonaOld = em.merge(codigoPersonaOld);
            }
            if (codigoPersonaNew != null && !codigoPersonaNew.equals(codigoPersonaOld)) {
                codigoPersonaNew.getUsuariosCollection().add(usuarios);
                codigoPersonaNew = em.merge(codigoPersonaNew);
            }
            for (Grupos gruposCollectionOldGrupos : gruposCollectionOld) {
                if (!gruposCollectionNew.contains(gruposCollectionOldGrupos)) {
                    gruposCollectionOldGrupos.getUsuariosCollection().remove(usuarios);
                    gruposCollectionOldGrupos = em.merge(gruposCollectionOldGrupos);
                }
            }
            for (Grupos gruposCollectionNewGrupos : gruposCollectionNew) {
                if (!gruposCollectionOld.contains(gruposCollectionNewGrupos)) {
                    gruposCollectionNewGrupos.getUsuariosCollection().add(usuarios);
                    gruposCollectionNewGrupos = em.merge(gruposCollectionNewGrupos);
                }
            }
            for (Empresas empresasCollectionOldEmpresas : empresasCollectionOld) {
                if (!empresasCollectionNew.contains(empresasCollectionOldEmpresas)) {
                    empresasCollectionOldEmpresas.getUsuariosCollection().remove(usuarios);
                    empresasCollectionOldEmpresas = em.merge(empresasCollectionOldEmpresas);
                }
            }
            for (Empresas empresasCollectionNewEmpresas : empresasCollectionNew) {
                if (!empresasCollectionOld.contains(empresasCollectionNewEmpresas)) {
                    empresasCollectionNewEmpresas.getUsuariosCollection().add(usuarios);
                    empresasCollectionNewEmpresas = em.merge(empresasCollectionNewEmpresas);
                }
            }
            for (ProvPedidosCab provPedidosCabCollectionNewProvPedidosCab : provPedidosCabCollectionNew) {
                if (!provPedidosCabCollectionOld.contains(provPedidosCabCollectionNewProvPedidosCab)) {
                    Usuarios oldUsuarioOfProvPedidosCabCollectionNewProvPedidosCab = provPedidosCabCollectionNewProvPedidosCab.getUsuario();
                    provPedidosCabCollectionNewProvPedidosCab.setUsuario(usuarios);
                    provPedidosCabCollectionNewProvPedidosCab = em.merge(provPedidosCabCollectionNewProvPedidosCab);
                    if (oldUsuarioOfProvPedidosCabCollectionNewProvPedidosCab != null && !oldUsuarioOfProvPedidosCabCollectionNewProvPedidosCab.equals(usuarios)) {
                        oldUsuarioOfProvPedidosCabCollectionNewProvPedidosCab.getProvPedidosCabCollection().remove(provPedidosCabCollectionNewProvPedidosCab);
                        oldUsuarioOfProvPedidosCabCollectionNewProvPedidosCab = em.merge(oldUsuarioOfProvPedidosCabCollectionNewProvPedidosCab);
                    }
                }
            }
            for (CliAlbCab cliAlbCabCollectionNewCliAlbCab : cliAlbCabCollectionNew) {
                if (!cliAlbCabCollectionOld.contains(cliAlbCabCollectionNewCliAlbCab)) {
                    Usuarios oldUsuarioOfCliAlbCabCollectionNewCliAlbCab = cliAlbCabCollectionNewCliAlbCab.getUsuario();
                    cliAlbCabCollectionNewCliAlbCab.setUsuario(usuarios);
                    cliAlbCabCollectionNewCliAlbCab = em.merge(cliAlbCabCollectionNewCliAlbCab);
                    if (oldUsuarioOfCliAlbCabCollectionNewCliAlbCab != null && !oldUsuarioOfCliAlbCabCollectionNewCliAlbCab.equals(usuarios)) {
                        oldUsuarioOfCliAlbCabCollectionNewCliAlbCab.getCliAlbCabCollection().remove(cliAlbCabCollectionNewCliAlbCab);
                        oldUsuarioOfCliAlbCabCollectionNewCliAlbCab = em.merge(oldUsuarioOfCliAlbCabCollectionNewCliAlbCab);
                    }
                }
            }
            for (RegistroLog registroLogCollectionNewRegistroLog : registroLogCollectionNew) {
                if (!registroLogCollectionOld.contains(registroLogCollectionNewRegistroLog)) {
                    Usuarios oldUsuarioOfRegistroLogCollectionNewRegistroLog = registroLogCollectionNewRegistroLog.getUsuario();
                    registroLogCollectionNewRegistroLog.setUsuario(usuarios);
                    registroLogCollectionNewRegistroLog = em.merge(registroLogCollectionNewRegistroLog);
                    if (oldUsuarioOfRegistroLogCollectionNewRegistroLog != null && !oldUsuarioOfRegistroLogCollectionNewRegistroLog.equals(usuarios)) {
                        oldUsuarioOfRegistroLogCollectionNewRegistroLog.getRegistroLogCollection().remove(registroLogCollectionNewRegistroLog);
                        oldUsuarioOfRegistroLogCollectionNewRegistroLog = em.merge(oldUsuarioOfRegistroLogCollectionNewRegistroLog);
                    }
                }
            }
            for (ProvFacCab provFacCabCollectionNewProvFacCab : provFacCabCollectionNew) {
                if (!provFacCabCollectionOld.contains(provFacCabCollectionNewProvFacCab)) {
                    Usuarios oldUsuarioOfProvFacCabCollectionNewProvFacCab = provFacCabCollectionNewProvFacCab.getUsuario();
                    provFacCabCollectionNewProvFacCab.setUsuario(usuarios);
                    provFacCabCollectionNewProvFacCab = em.merge(provFacCabCollectionNewProvFacCab);
                    if (oldUsuarioOfProvFacCabCollectionNewProvFacCab != null && !oldUsuarioOfProvFacCabCollectionNewProvFacCab.equals(usuarios)) {
                        oldUsuarioOfProvFacCabCollectionNewProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionNewProvFacCab);
                        oldUsuarioOfProvFacCabCollectionNewProvFacCab = em.merge(oldUsuarioOfProvFacCabCollectionNewProvFacCab);
                    }
                }
            }
            for (ProvAlbCab provAlbCabCollectionNewProvAlbCab : provAlbCabCollectionNew) {
                if (!provAlbCabCollectionOld.contains(provAlbCabCollectionNewProvAlbCab)) {
                    Usuarios oldUsuarioOfProvAlbCabCollectionNewProvAlbCab = provAlbCabCollectionNewProvAlbCab.getUsuario();
                    provAlbCabCollectionNewProvAlbCab.setUsuario(usuarios);
                    provAlbCabCollectionNewProvAlbCab = em.merge(provAlbCabCollectionNewProvAlbCab);
                    if (oldUsuarioOfProvAlbCabCollectionNewProvAlbCab != null && !oldUsuarioOfProvAlbCabCollectionNewProvAlbCab.equals(usuarios)) {
                        oldUsuarioOfProvAlbCabCollectionNewProvAlbCab.getProvAlbCabCollection().remove(provAlbCabCollectionNewProvAlbCab);
                        oldUsuarioOfProvAlbCabCollectionNewProvAlbCab = em.merge(oldUsuarioOfProvAlbCabCollectionNewProvAlbCab);
                    }
                }
            }
            for (CliFacCab cliFacCabCollectionNewCliFacCab : cliFacCabCollectionNew) {
                if (!cliFacCabCollectionOld.contains(cliFacCabCollectionNewCliFacCab)) {
                    Usuarios oldUsuarioOfCliFacCabCollectionNewCliFacCab = cliFacCabCollectionNewCliFacCab.getUsuario();
                    cliFacCabCollectionNewCliFacCab.setUsuario(usuarios);
                    cliFacCabCollectionNewCliFacCab = em.merge(cliFacCabCollectionNewCliFacCab);
                    if (oldUsuarioOfCliFacCabCollectionNewCliFacCab != null && !oldUsuarioOfCliFacCabCollectionNewCliFacCab.equals(usuarios)) {
                        oldUsuarioOfCliFacCabCollectionNewCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionNewCliFacCab);
                        oldUsuarioOfCliFacCabCollectionNewCliFacCab = em.merge(oldUsuarioOfCliFacCabCollectionNewCliFacCab);
                    }
                }
            }
            for (CliPedidosCab cliPedidosCabCollectionNewCliPedidosCab : cliPedidosCabCollectionNew) {
                if (!cliPedidosCabCollectionOld.contains(cliPedidosCabCollectionNewCliPedidosCab)) {
                    Usuarios oldUsuarioOfCliPedidosCabCollectionNewCliPedidosCab = cliPedidosCabCollectionNewCliPedidosCab.getUsuario();
                    cliPedidosCabCollectionNewCliPedidosCab.setUsuario(usuarios);
                    cliPedidosCabCollectionNewCliPedidosCab = em.merge(cliPedidosCabCollectionNewCliPedidosCab);
                    if (oldUsuarioOfCliPedidosCabCollectionNewCliPedidosCab != null && !oldUsuarioOfCliPedidosCabCollectionNewCliPedidosCab.equals(usuarios)) {
                        oldUsuarioOfCliPedidosCabCollectionNewCliPedidosCab.getCliPedidosCabCollection().remove(cliPedidosCabCollectionNewCliPedidosCab);
                        oldUsuarioOfCliPedidosCabCollectionNewCliPedidosCab = em.merge(oldUsuarioOfCliPedidosCabCollectionNewCliPedidosCab);
                    }
                }
            }
            for (Roles rolesCollectionOldRoles : rolesCollectionOld) {
                if (!rolesCollectionNew.contains(rolesCollectionOldRoles)) {
                    rolesCollectionOldRoles.getUsuariosCollection().remove(usuarios);
                    rolesCollectionOldRoles = em.merge(rolesCollectionOldRoles);
                }
            }
            for (Roles rolesCollectionNewRoles : rolesCollectionNew) {
                if (!rolesCollectionOld.contains(rolesCollectionNewRoles)) {
                    rolesCollectionNewRoles.getUsuariosCollection().add(usuarios);
                    rolesCollectionNewRoles = em.merge(rolesCollectionNewRoles);
                }
            }
            for (UsuGrupo usuGrupoCollectionNewUsuGrupo : usuGrupoCollectionNew) {
                if (!usuGrupoCollectionOld.contains(usuGrupoCollectionNewUsuGrupo)) {
                    Usuarios oldUsuariosOfUsuGrupoCollectionNewUsuGrupo = usuGrupoCollectionNewUsuGrupo.getUsuarios();
                    usuGrupoCollectionNewUsuGrupo.setUsuarios(usuarios);
                    usuGrupoCollectionNewUsuGrupo = em.merge(usuGrupoCollectionNewUsuGrupo);
                    if (oldUsuariosOfUsuGrupoCollectionNewUsuGrupo != null && !oldUsuariosOfUsuGrupoCollectionNewUsuGrupo.equals(usuarios)) {
                        oldUsuariosOfUsuGrupoCollectionNewUsuGrupo.getUsuGrupoCollection().remove(usuGrupoCollectionNewUsuGrupo);
                        oldUsuariosOfUsuGrupoCollectionNewUsuGrupo = em.merge(oldUsuariosOfUsuGrupoCollectionNewUsuGrupo);
                    }
                }
            }
            for (EmpUsu empUsuCollectionNewEmpUsu : empUsuCollectionNew) {
                if (!empUsuCollectionOld.contains(empUsuCollectionNewEmpUsu)) {
                    Usuarios oldUsuariosOfEmpUsuCollectionNewEmpUsu = empUsuCollectionNewEmpUsu.getUsuarios();
                    empUsuCollectionNewEmpUsu.setUsuarios(usuarios);
                    empUsuCollectionNewEmpUsu = em.merge(empUsuCollectionNewEmpUsu);
                    if (oldUsuariosOfEmpUsuCollectionNewEmpUsu != null && !oldUsuariosOfEmpUsuCollectionNewEmpUsu.equals(usuarios)) {
                        oldUsuariosOfEmpUsuCollectionNewEmpUsu.getEmpUsuCollection().remove(empUsuCollectionNewEmpUsu);
                        oldUsuariosOfEmpUsuCollectionNewEmpUsu = em.merge(oldUsuariosOfEmpUsuCollectionNewEmpUsu);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProvPedidosCab> provPedidosCabCollectionOrphanCheck = usuarios.getProvPedidosCabCollection();
            for (ProvPedidosCab provPedidosCabCollectionOrphanCheckProvPedidosCab : provPedidosCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the ProvPedidosCab " + provPedidosCabCollectionOrphanCheckProvPedidosCab + " in its provPedidosCabCollection field has a non-nullable usuario field.");
            }
            Collection<CliAlbCab> cliAlbCabCollectionOrphanCheck = usuarios.getCliAlbCabCollection();
            for (CliAlbCab cliAlbCabCollectionOrphanCheckCliAlbCab : cliAlbCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the CliAlbCab " + cliAlbCabCollectionOrphanCheckCliAlbCab + " in its cliAlbCabCollection field has a non-nullable usuario field.");
            }
            Collection<RegistroLog> registroLogCollectionOrphanCheck = usuarios.getRegistroLogCollection();
            for (RegistroLog registroLogCollectionOrphanCheckRegistroLog : registroLogCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the RegistroLog " + registroLogCollectionOrphanCheckRegistroLog + " in its registroLogCollection field has a non-nullable usuario field.");
            }
            Collection<ProvFacCab> provFacCabCollectionOrphanCheck = usuarios.getProvFacCabCollection();
            for (ProvFacCab provFacCabCollectionOrphanCheckProvFacCab : provFacCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the ProvFacCab " + provFacCabCollectionOrphanCheckProvFacCab + " in its provFacCabCollection field has a non-nullable usuario field.");
            }
            Collection<ProvAlbCab> provAlbCabCollectionOrphanCheck = usuarios.getProvAlbCabCollection();
            for (ProvAlbCab provAlbCabCollectionOrphanCheckProvAlbCab : provAlbCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the ProvAlbCab " + provAlbCabCollectionOrphanCheckProvAlbCab + " in its provAlbCabCollection field has a non-nullable usuario field.");
            }
            Collection<CliFacCab> cliFacCabCollectionOrphanCheck = usuarios.getCliFacCabCollection();
            for (CliFacCab cliFacCabCollectionOrphanCheckCliFacCab : cliFacCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the CliFacCab " + cliFacCabCollectionOrphanCheckCliFacCab + " in its cliFacCabCollection field has a non-nullable usuario field.");
            }
            Collection<CliPedidosCab> cliPedidosCabCollectionOrphanCheck = usuarios.getCliPedidosCabCollection();
            for (CliPedidosCab cliPedidosCabCollectionOrphanCheckCliPedidosCab : cliPedidosCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the CliPedidosCab " + cliPedidosCabCollectionOrphanCheckCliPedidosCab + " in its cliPedidosCabCollection field has a non-nullable usuario field.");
            }
            Collection<UsuGrupo> usuGrupoCollectionOrphanCheck = usuarios.getUsuGrupoCollection();
            for (UsuGrupo usuGrupoCollectionOrphanCheckUsuGrupo : usuGrupoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the UsuGrupo " + usuGrupoCollectionOrphanCheckUsuGrupo + " in its usuGrupoCollection field has a non-nullable usuarios field.");
            }
            Collection<EmpUsu> empUsuCollectionOrphanCheck = usuarios.getEmpUsuCollection();
            for (EmpUsu empUsuCollectionOrphanCheckEmpUsu : empUsuCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the EmpUsu " + empUsuCollectionOrphanCheckEmpUsu + " in its empUsuCollection field has a non-nullable usuarios field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            DatosPersonales codigoPersona = usuarios.getCodigoPersona();
            if (codigoPersona != null) {
                codigoPersona.getUsuariosCollection().remove(usuarios);
                codigoPersona = em.merge(codigoPersona);
            }
            Collection<Grupos> gruposCollection = usuarios.getGruposCollection();
            for (Grupos gruposCollectionGrupos : gruposCollection) {
                gruposCollectionGrupos.getUsuariosCollection().remove(usuarios);
                gruposCollectionGrupos = em.merge(gruposCollectionGrupos);
            }
            Collection<Empresas> empresasCollection = usuarios.getEmpresasCollection();
            for (Empresas empresasCollectionEmpresas : empresasCollection) {
                empresasCollectionEmpresas.getUsuariosCollection().remove(usuarios);
                empresasCollectionEmpresas = em.merge(empresasCollectionEmpresas);
            }
            Collection<Roles> rolesCollection = usuarios.getRolesCollection();
            for (Roles rolesCollectionRoles : rolesCollection) {
                rolesCollectionRoles.getUsuariosCollection().remove(usuarios);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Usuarios as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuarios findUsuarios(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Usuarios as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
