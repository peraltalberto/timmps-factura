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
import es.timmps.fac.persistencia.pojos.Clientes;
import es.timmps.fac.persistencia.pojos.Empresas;
import es.timmps.fac.persistencia.pojos.CliAlbCab;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.pojos.CliFacCab;
import es.timmps.fac.persistencia.pojos.CliPedidosCab;
import es.timmps.fac.persistencia.pojos.CliPedidosCustom;
import es.timmps.fac.persistencia.pojos.CliPedidosLin;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class CliPedidosCabJpaController implements Serializable {

    public CliPedidosCabJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CliPedidosCab cliPedidosCab) {
        if (cliPedidosCab.getCliAlbCabCollection() == null) {
            cliPedidosCab.setCliAlbCabCollection(new ArrayList<CliAlbCab>());
        }
        if (cliPedidosCab.getCliFacCabCollection() == null) {
            cliPedidosCab.setCliFacCabCollection(new ArrayList<CliFacCab>());
        }
        if (cliPedidosCab.getCliPedidosCustomCollection() == null) {
            cliPedidosCab.setCliPedidosCustomCollection(new ArrayList<CliPedidosCustom>());
        }
        if (cliPedidosCab.getCliPedidosLinCollection() == null) {
            cliPedidosCab.setCliPedidosLinCollection(new ArrayList<CliPedidosLin>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuario = cliPedidosCab.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                cliPedidosCab.setUsuario(usuario);
            }
            Clientes codCli = cliPedidosCab.getCodCli();
            if (codCli != null) {
                codCli = em.getReference(codCli.getClass(), codCli.getCodigo());
                cliPedidosCab.setCodCli(codCli);
            }
            Empresas codEmp = cliPedidosCab.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cliPedidosCab.setCodEmp(codEmp);
            }
            Collection<CliAlbCab> attachedCliAlbCabCollection = new ArrayList<CliAlbCab>();
            for (CliAlbCab cliAlbCabCollectionCliAlbCabToAttach : cliPedidosCab.getCliAlbCabCollection()) {
                cliAlbCabCollectionCliAlbCabToAttach = em.getReference(cliAlbCabCollectionCliAlbCabToAttach.getClass(), cliAlbCabCollectionCliAlbCabToAttach.getId());
                attachedCliAlbCabCollection.add(cliAlbCabCollectionCliAlbCabToAttach);
            }
            cliPedidosCab.setCliAlbCabCollection(attachedCliAlbCabCollection);
            Collection<CliFacCab> attachedCliFacCabCollection = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionCliFacCabToAttach : cliPedidosCab.getCliFacCabCollection()) {
                cliFacCabCollectionCliFacCabToAttach = em.getReference(cliFacCabCollectionCliFacCabToAttach.getClass(), cliFacCabCollectionCliFacCabToAttach.getId());
                attachedCliFacCabCollection.add(cliFacCabCollectionCliFacCabToAttach);
            }
            cliPedidosCab.setCliFacCabCollection(attachedCliFacCabCollection);
            Collection<CliPedidosCustom> attachedCliPedidosCustomCollection = new ArrayList<CliPedidosCustom>();
            for (CliPedidosCustom cliPedidosCustomCollectionCliPedidosCustomToAttach : cliPedidosCab.getCliPedidosCustomCollection()) {
                cliPedidosCustomCollectionCliPedidosCustomToAttach = em.getReference(cliPedidosCustomCollectionCliPedidosCustomToAttach.getClass(), cliPedidosCustomCollectionCliPedidosCustomToAttach.getCliPedidosCustomPK());
                attachedCliPedidosCustomCollection.add(cliPedidosCustomCollectionCliPedidosCustomToAttach);
            }
            cliPedidosCab.setCliPedidosCustomCollection(attachedCliPedidosCustomCollection);
            Collection<CliPedidosLin> attachedCliPedidosLinCollection = new ArrayList<CliPedidosLin>();
            for (CliPedidosLin cliPedidosLinCollectionCliPedidosLinToAttach : cliPedidosCab.getCliPedidosLinCollection()) {
                cliPedidosLinCollectionCliPedidosLinToAttach = em.getReference(cliPedidosLinCollectionCliPedidosLinToAttach.getClass(), cliPedidosLinCollectionCliPedidosLinToAttach.getId());
                attachedCliPedidosLinCollection.add(cliPedidosLinCollectionCliPedidosLinToAttach);
            }
            cliPedidosCab.setCliPedidosLinCollection(attachedCliPedidosLinCollection);
            em.persist(cliPedidosCab);
            if (usuario != null) {
                usuario.getCliPedidosCabCollection().add(cliPedidosCab);
                usuario = em.merge(usuario);
            }
            if (codCli != null) {
                codCli.getCliPedidosCabCollection().add(cliPedidosCab);
                codCli = em.merge(codCli);
            }
            if (codEmp != null) {
                codEmp.getCliPedidosCabCollection().add(cliPedidosCab);
                codEmp = em.merge(codEmp);
            }
            for (CliAlbCab cliAlbCabCollectionCliAlbCab : cliPedidosCab.getCliAlbCabCollection()) {
                CliPedidosCab oldCodPedidoOfCliAlbCabCollectionCliAlbCab = cliAlbCabCollectionCliAlbCab.getCodPedido();
                cliAlbCabCollectionCliAlbCab.setCodPedido(cliPedidosCab);
                cliAlbCabCollectionCliAlbCab = em.merge(cliAlbCabCollectionCliAlbCab);
                if (oldCodPedidoOfCliAlbCabCollectionCliAlbCab != null) {
                    oldCodPedidoOfCliAlbCabCollectionCliAlbCab.getCliAlbCabCollection().remove(cliAlbCabCollectionCliAlbCab);
                    oldCodPedidoOfCliAlbCabCollectionCliAlbCab = em.merge(oldCodPedidoOfCliAlbCabCollectionCliAlbCab);
                }
            }
            for (CliFacCab cliFacCabCollectionCliFacCab : cliPedidosCab.getCliFacCabCollection()) {
                CliPedidosCab oldCodPedidoOfCliFacCabCollectionCliFacCab = cliFacCabCollectionCliFacCab.getCodPedido();
                cliFacCabCollectionCliFacCab.setCodPedido(cliPedidosCab);
                cliFacCabCollectionCliFacCab = em.merge(cliFacCabCollectionCliFacCab);
                if (oldCodPedidoOfCliFacCabCollectionCliFacCab != null) {
                    oldCodPedidoOfCliFacCabCollectionCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionCliFacCab);
                    oldCodPedidoOfCliFacCabCollectionCliFacCab = em.merge(oldCodPedidoOfCliFacCabCollectionCliFacCab);
                }
            }
            for (CliPedidosCustom cliPedidosCustomCollectionCliPedidosCustom : cliPedidosCab.getCliPedidosCustomCollection()) {
                CliPedidosCab oldCliPedidosCabOfCliPedidosCustomCollectionCliPedidosCustom = cliPedidosCustomCollectionCliPedidosCustom.getCliPedidosCab();
                cliPedidosCustomCollectionCliPedidosCustom.setCliPedidosCab(cliPedidosCab);
                cliPedidosCustomCollectionCliPedidosCustom = em.merge(cliPedidosCustomCollectionCliPedidosCustom);
                if (oldCliPedidosCabOfCliPedidosCustomCollectionCliPedidosCustom != null) {
                    oldCliPedidosCabOfCliPedidosCustomCollectionCliPedidosCustom.getCliPedidosCustomCollection().remove(cliPedidosCustomCollectionCliPedidosCustom);
                    oldCliPedidosCabOfCliPedidosCustomCollectionCliPedidosCustom = em.merge(oldCliPedidosCabOfCliPedidosCustomCollectionCliPedidosCustom);
                }
            }
            for (CliPedidosLin cliPedidosLinCollectionCliPedidosLin : cliPedidosCab.getCliPedidosLinCollection()) {
                CliPedidosCab oldCodCabOfCliPedidosLinCollectionCliPedidosLin = cliPedidosLinCollectionCliPedidosLin.getCodCab();
                cliPedidosLinCollectionCliPedidosLin.setCodCab(cliPedidosCab);
                cliPedidosLinCollectionCliPedidosLin = em.merge(cliPedidosLinCollectionCliPedidosLin);
                if (oldCodCabOfCliPedidosLinCollectionCliPedidosLin != null) {
                    oldCodCabOfCliPedidosLinCollectionCliPedidosLin.getCliPedidosLinCollection().remove(cliPedidosLinCollectionCliPedidosLin);
                    oldCodCabOfCliPedidosLinCollectionCliPedidosLin = em.merge(oldCodCabOfCliPedidosLinCollectionCliPedidosLin);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CliPedidosCab cliPedidosCab) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliPedidosCab persistentCliPedidosCab = em.find(CliPedidosCab.class, cliPedidosCab.getId());
            Usuarios usuarioOld = persistentCliPedidosCab.getUsuario();
            Usuarios usuarioNew = cliPedidosCab.getUsuario();
            Clientes codCliOld = persistentCliPedidosCab.getCodCli();
            Clientes codCliNew = cliPedidosCab.getCodCli();
            Empresas codEmpOld = persistentCliPedidosCab.getCodEmp();
            Empresas codEmpNew = cliPedidosCab.getCodEmp();
            Collection<CliAlbCab> cliAlbCabCollectionOld = persistentCliPedidosCab.getCliAlbCabCollection();
            Collection<CliAlbCab> cliAlbCabCollectionNew = cliPedidosCab.getCliAlbCabCollection();
            Collection<CliFacCab> cliFacCabCollectionOld = persistentCliPedidosCab.getCliFacCabCollection();
            Collection<CliFacCab> cliFacCabCollectionNew = cliPedidosCab.getCliFacCabCollection();
            Collection<CliPedidosCustom> cliPedidosCustomCollectionOld = persistentCliPedidosCab.getCliPedidosCustomCollection();
            Collection<CliPedidosCustom> cliPedidosCustomCollectionNew = cliPedidosCab.getCliPedidosCustomCollection();
            Collection<CliPedidosLin> cliPedidosLinCollectionOld = persistentCliPedidosCab.getCliPedidosLinCollection();
            Collection<CliPedidosLin> cliPedidosLinCollectionNew = cliPedidosCab.getCliPedidosLinCollection();
            List<String> illegalOrphanMessages = null;
            for (CliPedidosCustom cliPedidosCustomCollectionOldCliPedidosCustom : cliPedidosCustomCollectionOld) {
                if (!cliPedidosCustomCollectionNew.contains(cliPedidosCustomCollectionOldCliPedidosCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliPedidosCustom " + cliPedidosCustomCollectionOldCliPedidosCustom + " since its cliPedidosCab field is not nullable.");
                }
            }
            for (CliPedidosLin cliPedidosLinCollectionOldCliPedidosLin : cliPedidosLinCollectionOld) {
                if (!cliPedidosLinCollectionNew.contains(cliPedidosLinCollectionOldCliPedidosLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliPedidosLin " + cliPedidosLinCollectionOldCliPedidosLin + " since its codCab field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                cliPedidosCab.setUsuario(usuarioNew);
            }
            if (codCliNew != null) {
                codCliNew = em.getReference(codCliNew.getClass(), codCliNew.getCodigo());
                cliPedidosCab.setCodCli(codCliNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cliPedidosCab.setCodEmp(codEmpNew);
            }
            Collection<CliAlbCab> attachedCliAlbCabCollectionNew = new ArrayList<CliAlbCab>();
            for (CliAlbCab cliAlbCabCollectionNewCliAlbCabToAttach : cliAlbCabCollectionNew) {
                cliAlbCabCollectionNewCliAlbCabToAttach = em.getReference(cliAlbCabCollectionNewCliAlbCabToAttach.getClass(), cliAlbCabCollectionNewCliAlbCabToAttach.getId());
                attachedCliAlbCabCollectionNew.add(cliAlbCabCollectionNewCliAlbCabToAttach);
            }
            cliAlbCabCollectionNew = attachedCliAlbCabCollectionNew;
            cliPedidosCab.setCliAlbCabCollection(cliAlbCabCollectionNew);
            Collection<CliFacCab> attachedCliFacCabCollectionNew = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionNewCliFacCabToAttach : cliFacCabCollectionNew) {
                cliFacCabCollectionNewCliFacCabToAttach = em.getReference(cliFacCabCollectionNewCliFacCabToAttach.getClass(), cliFacCabCollectionNewCliFacCabToAttach.getId());
                attachedCliFacCabCollectionNew.add(cliFacCabCollectionNewCliFacCabToAttach);
            }
            cliFacCabCollectionNew = attachedCliFacCabCollectionNew;
            cliPedidosCab.setCliFacCabCollection(cliFacCabCollectionNew);
            Collection<CliPedidosCustom> attachedCliPedidosCustomCollectionNew = new ArrayList<CliPedidosCustom>();
            for (CliPedidosCustom cliPedidosCustomCollectionNewCliPedidosCustomToAttach : cliPedidosCustomCollectionNew) {
                cliPedidosCustomCollectionNewCliPedidosCustomToAttach = em.getReference(cliPedidosCustomCollectionNewCliPedidosCustomToAttach.getClass(), cliPedidosCustomCollectionNewCliPedidosCustomToAttach.getCliPedidosCustomPK());
                attachedCliPedidosCustomCollectionNew.add(cliPedidosCustomCollectionNewCliPedidosCustomToAttach);
            }
            cliPedidosCustomCollectionNew = attachedCliPedidosCustomCollectionNew;
            cliPedidosCab.setCliPedidosCustomCollection(cliPedidosCustomCollectionNew);
            Collection<CliPedidosLin> attachedCliPedidosLinCollectionNew = new ArrayList<CliPedidosLin>();
            for (CliPedidosLin cliPedidosLinCollectionNewCliPedidosLinToAttach : cliPedidosLinCollectionNew) {
                cliPedidosLinCollectionNewCliPedidosLinToAttach = em.getReference(cliPedidosLinCollectionNewCliPedidosLinToAttach.getClass(), cliPedidosLinCollectionNewCliPedidosLinToAttach.getId());
                attachedCliPedidosLinCollectionNew.add(cliPedidosLinCollectionNewCliPedidosLinToAttach);
            }
            cliPedidosLinCollectionNew = attachedCliPedidosLinCollectionNew;
            cliPedidosCab.setCliPedidosLinCollection(cliPedidosLinCollectionNew);
            cliPedidosCab = em.merge(cliPedidosCab);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getCliPedidosCabCollection().remove(cliPedidosCab);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getCliPedidosCabCollection().add(cliPedidosCab);
                usuarioNew = em.merge(usuarioNew);
            }
            if (codCliOld != null && !codCliOld.equals(codCliNew)) {
                codCliOld.getCliPedidosCabCollection().remove(cliPedidosCab);
                codCliOld = em.merge(codCliOld);
            }
            if (codCliNew != null && !codCliNew.equals(codCliOld)) {
                codCliNew.getCliPedidosCabCollection().add(cliPedidosCab);
                codCliNew = em.merge(codCliNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCliPedidosCabCollection().remove(cliPedidosCab);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCliPedidosCabCollection().add(cliPedidosCab);
                codEmpNew = em.merge(codEmpNew);
            }
            for (CliAlbCab cliAlbCabCollectionOldCliAlbCab : cliAlbCabCollectionOld) {
                if (!cliAlbCabCollectionNew.contains(cliAlbCabCollectionOldCliAlbCab)) {
                    cliAlbCabCollectionOldCliAlbCab.setCodPedido(null);
                    cliAlbCabCollectionOldCliAlbCab = em.merge(cliAlbCabCollectionOldCliAlbCab);
                }
            }
            for (CliAlbCab cliAlbCabCollectionNewCliAlbCab : cliAlbCabCollectionNew) {
                if (!cliAlbCabCollectionOld.contains(cliAlbCabCollectionNewCliAlbCab)) {
                    CliPedidosCab oldCodPedidoOfCliAlbCabCollectionNewCliAlbCab = cliAlbCabCollectionNewCliAlbCab.getCodPedido();
                    cliAlbCabCollectionNewCliAlbCab.setCodPedido(cliPedidosCab);
                    cliAlbCabCollectionNewCliAlbCab = em.merge(cliAlbCabCollectionNewCliAlbCab);
                    if (oldCodPedidoOfCliAlbCabCollectionNewCliAlbCab != null && !oldCodPedidoOfCliAlbCabCollectionNewCliAlbCab.equals(cliPedidosCab)) {
                        oldCodPedidoOfCliAlbCabCollectionNewCliAlbCab.getCliAlbCabCollection().remove(cliAlbCabCollectionNewCliAlbCab);
                        oldCodPedidoOfCliAlbCabCollectionNewCliAlbCab = em.merge(oldCodPedidoOfCliAlbCabCollectionNewCliAlbCab);
                    }
                }
            }
            for (CliFacCab cliFacCabCollectionOldCliFacCab : cliFacCabCollectionOld) {
                if (!cliFacCabCollectionNew.contains(cliFacCabCollectionOldCliFacCab)) {
                    cliFacCabCollectionOldCliFacCab.setCodPedido(null);
                    cliFacCabCollectionOldCliFacCab = em.merge(cliFacCabCollectionOldCliFacCab);
                }
            }
            for (CliFacCab cliFacCabCollectionNewCliFacCab : cliFacCabCollectionNew) {
                if (!cliFacCabCollectionOld.contains(cliFacCabCollectionNewCliFacCab)) {
                    CliPedidosCab oldCodPedidoOfCliFacCabCollectionNewCliFacCab = cliFacCabCollectionNewCliFacCab.getCodPedido();
                    cliFacCabCollectionNewCliFacCab.setCodPedido(cliPedidosCab);
                    cliFacCabCollectionNewCliFacCab = em.merge(cliFacCabCollectionNewCliFacCab);
                    if (oldCodPedidoOfCliFacCabCollectionNewCliFacCab != null && !oldCodPedidoOfCliFacCabCollectionNewCliFacCab.equals(cliPedidosCab)) {
                        oldCodPedidoOfCliFacCabCollectionNewCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionNewCliFacCab);
                        oldCodPedidoOfCliFacCabCollectionNewCliFacCab = em.merge(oldCodPedidoOfCliFacCabCollectionNewCliFacCab);
                    }
                }
            }
            for (CliPedidosCustom cliPedidosCustomCollectionNewCliPedidosCustom : cliPedidosCustomCollectionNew) {
                if (!cliPedidosCustomCollectionOld.contains(cliPedidosCustomCollectionNewCliPedidosCustom)) {
                    CliPedidosCab oldCliPedidosCabOfCliPedidosCustomCollectionNewCliPedidosCustom = cliPedidosCustomCollectionNewCliPedidosCustom.getCliPedidosCab();
                    cliPedidosCustomCollectionNewCliPedidosCustom.setCliPedidosCab(cliPedidosCab);
                    cliPedidosCustomCollectionNewCliPedidosCustom = em.merge(cliPedidosCustomCollectionNewCliPedidosCustom);
                    if (oldCliPedidosCabOfCliPedidosCustomCollectionNewCliPedidosCustom != null && !oldCliPedidosCabOfCliPedidosCustomCollectionNewCliPedidosCustom.equals(cliPedidosCab)) {
                        oldCliPedidosCabOfCliPedidosCustomCollectionNewCliPedidosCustom.getCliPedidosCustomCollection().remove(cliPedidosCustomCollectionNewCliPedidosCustom);
                        oldCliPedidosCabOfCliPedidosCustomCollectionNewCliPedidosCustom = em.merge(oldCliPedidosCabOfCliPedidosCustomCollectionNewCliPedidosCustom);
                    }
                }
            }
            for (CliPedidosLin cliPedidosLinCollectionNewCliPedidosLin : cliPedidosLinCollectionNew) {
                if (!cliPedidosLinCollectionOld.contains(cliPedidosLinCollectionNewCliPedidosLin)) {
                    CliPedidosCab oldCodCabOfCliPedidosLinCollectionNewCliPedidosLin = cliPedidosLinCollectionNewCliPedidosLin.getCodCab();
                    cliPedidosLinCollectionNewCliPedidosLin.setCodCab(cliPedidosCab);
                    cliPedidosLinCollectionNewCliPedidosLin = em.merge(cliPedidosLinCollectionNewCliPedidosLin);
                    if (oldCodCabOfCliPedidosLinCollectionNewCliPedidosLin != null && !oldCodCabOfCliPedidosLinCollectionNewCliPedidosLin.equals(cliPedidosCab)) {
                        oldCodCabOfCliPedidosLinCollectionNewCliPedidosLin.getCliPedidosLinCollection().remove(cliPedidosLinCollectionNewCliPedidosLin);
                        oldCodCabOfCliPedidosLinCollectionNewCliPedidosLin = em.merge(oldCodCabOfCliPedidosLinCollectionNewCliPedidosLin);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliPedidosCab.getId();
                if (findCliPedidosCab(id) == null) {
                    throw new NonexistentEntityException("The cliPedidosCab with id " + id + " no longer exists.");
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
            CliPedidosCab cliPedidosCab;
            try {
                cliPedidosCab = em.getReference(CliPedidosCab.class, id);
                cliPedidosCab.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliPedidosCab with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CliPedidosCustom> cliPedidosCustomCollectionOrphanCheck = cliPedidosCab.getCliPedidosCustomCollection();
            for (CliPedidosCustom cliPedidosCustomCollectionOrphanCheckCliPedidosCustom : cliPedidosCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CliPedidosCab (" + cliPedidosCab + ") cannot be destroyed since the CliPedidosCustom " + cliPedidosCustomCollectionOrphanCheckCliPedidosCustom + " in its cliPedidosCustomCollection field has a non-nullable cliPedidosCab field.");
            }
            Collection<CliPedidosLin> cliPedidosLinCollectionOrphanCheck = cliPedidosCab.getCliPedidosLinCollection();
            for (CliPedidosLin cliPedidosLinCollectionOrphanCheckCliPedidosLin : cliPedidosLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CliPedidosCab (" + cliPedidosCab + ") cannot be destroyed since the CliPedidosLin " + cliPedidosLinCollectionOrphanCheckCliPedidosLin + " in its cliPedidosLinCollection field has a non-nullable codCab field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios usuario = cliPedidosCab.getUsuario();
            if (usuario != null) {
                usuario.getCliPedidosCabCollection().remove(cliPedidosCab);
                usuario = em.merge(usuario);
            }
            Clientes codCli = cliPedidosCab.getCodCli();
            if (codCli != null) {
                codCli.getCliPedidosCabCollection().remove(cliPedidosCab);
                codCli = em.merge(codCli);
            }
            Empresas codEmp = cliPedidosCab.getCodEmp();
            if (codEmp != null) {
                codEmp.getCliPedidosCabCollection().remove(cliPedidosCab);
                codEmp = em.merge(codEmp);
            }
            Collection<CliAlbCab> cliAlbCabCollection = cliPedidosCab.getCliAlbCabCollection();
            for (CliAlbCab cliAlbCabCollectionCliAlbCab : cliAlbCabCollection) {
                cliAlbCabCollectionCliAlbCab.setCodPedido(null);
                cliAlbCabCollectionCliAlbCab = em.merge(cliAlbCabCollectionCliAlbCab);
            }
            Collection<CliFacCab> cliFacCabCollection = cliPedidosCab.getCliFacCabCollection();
            for (CliFacCab cliFacCabCollectionCliFacCab : cliFacCabCollection) {
                cliFacCabCollectionCliFacCab.setCodPedido(null);
                cliFacCabCollectionCliFacCab = em.merge(cliFacCabCollectionCliFacCab);
            }
            em.remove(cliPedidosCab);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CliPedidosCab> findCliPedidosCabEntities() {
        return findCliPedidosCabEntities(true, -1, -1);
    }

    public List<CliPedidosCab> findCliPedidosCabEntities(int maxResults, int firstResult) {
        return findCliPedidosCabEntities(false, maxResults, firstResult);
    }

    private List<CliPedidosCab> findCliPedidosCabEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CliPedidosCab.class));
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

    public CliPedidosCab findCliPedidosCab(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CliPedidosCab.class, id);
        } finally {
            em.close();
        }
    }

    public int getCliPedidosCabCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CliPedidosCab> rt = cq.from(CliPedidosCab.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
