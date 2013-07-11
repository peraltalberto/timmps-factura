/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.CliAlbCab;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.fac.persistencia.Clientes;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.CliPedidosCab;
import es.timmps.fac.persistencia.CliAlbCustom;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.CliFacCab;
import es.timmps.fac.persistencia.CliAlbLin;
import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class CliAlbCabJpaController implements Serializable {

    public CliAlbCabJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CliAlbCab cliAlbCab) {
        if (cliAlbCab.getCliAlbCustomCollection() == null) {
            cliAlbCab.setCliAlbCustomCollection(new ArrayList<CliAlbCustom>());
        }
        if (cliAlbCab.getCliFacCabCollection() == null) {
            cliAlbCab.setCliFacCabCollection(new ArrayList<CliFacCab>());
        }
        if (cliAlbCab.getCliAlbLinCollection() == null) {
            cliAlbCab.setCliAlbLinCollection(new ArrayList<CliAlbLin>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuario = cliAlbCab.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                cliAlbCab.setUsuario(usuario);
            }
            Clientes codCli = cliAlbCab.getCodCli();
            if (codCli != null) {
                codCli = em.getReference(codCli.getClass(), codCli.getCodigo());
                cliAlbCab.setCodCli(codCli);
            }
            Empresas codEmp = cliAlbCab.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cliAlbCab.setCodEmp(codEmp);
            }
            CliPedidosCab codPedido = cliAlbCab.getCodPedido();
            if (codPedido != null) {
                codPedido = em.getReference(codPedido.getClass(), codPedido.getId());
                cliAlbCab.setCodPedido(codPedido);
            }
            Collection<CliAlbCustom> attachedCliAlbCustomCollection = new ArrayList<CliAlbCustom>();
            for (CliAlbCustom cliAlbCustomCollectionCliAlbCustomToAttach : cliAlbCab.getCliAlbCustomCollection()) {
                cliAlbCustomCollectionCliAlbCustomToAttach = em.getReference(cliAlbCustomCollectionCliAlbCustomToAttach.getClass(), cliAlbCustomCollectionCliAlbCustomToAttach.getCliAlbCustomPK());
                attachedCliAlbCustomCollection.add(cliAlbCustomCollectionCliAlbCustomToAttach);
            }
            cliAlbCab.setCliAlbCustomCollection(attachedCliAlbCustomCollection);
            Collection<CliFacCab> attachedCliFacCabCollection = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionCliFacCabToAttach : cliAlbCab.getCliFacCabCollection()) {
                cliFacCabCollectionCliFacCabToAttach = em.getReference(cliFacCabCollectionCliFacCabToAttach.getClass(), cliFacCabCollectionCliFacCabToAttach.getId());
                attachedCliFacCabCollection.add(cliFacCabCollectionCliFacCabToAttach);
            }
            cliAlbCab.setCliFacCabCollection(attachedCliFacCabCollection);
            Collection<CliAlbLin> attachedCliAlbLinCollection = new ArrayList<CliAlbLin>();
            for (CliAlbLin cliAlbLinCollectionCliAlbLinToAttach : cliAlbCab.getCliAlbLinCollection()) {
                cliAlbLinCollectionCliAlbLinToAttach = em.getReference(cliAlbLinCollectionCliAlbLinToAttach.getClass(), cliAlbLinCollectionCliAlbLinToAttach.getId());
                attachedCliAlbLinCollection.add(cliAlbLinCollectionCliAlbLinToAttach);
            }
            cliAlbCab.setCliAlbLinCollection(attachedCliAlbLinCollection);
            em.persist(cliAlbCab);
            if (usuario != null) {
                usuario.getCliAlbCabCollection().add(cliAlbCab);
                usuario = em.merge(usuario);
            }
            if (codCli != null) {
                codCli.getCliAlbCabCollection().add(cliAlbCab);
                codCli = em.merge(codCli);
            }
            if (codEmp != null) {
                codEmp.getCliAlbCabCollection().add(cliAlbCab);
                codEmp = em.merge(codEmp);
            }
            if (codPedido != null) {
                codPedido.getCliAlbCabCollection().add(cliAlbCab);
                codPedido = em.merge(codPedido);
            }
            for (CliAlbCustom cliAlbCustomCollectionCliAlbCustom : cliAlbCab.getCliAlbCustomCollection()) {
                CliAlbCab oldCliAlbCabOfCliAlbCustomCollectionCliAlbCustom = cliAlbCustomCollectionCliAlbCustom.getCliAlbCab();
                cliAlbCustomCollectionCliAlbCustom.setCliAlbCab(cliAlbCab);
                cliAlbCustomCollectionCliAlbCustom = em.merge(cliAlbCustomCollectionCliAlbCustom);
                if (oldCliAlbCabOfCliAlbCustomCollectionCliAlbCustom != null) {
                    oldCliAlbCabOfCliAlbCustomCollectionCliAlbCustom.getCliAlbCustomCollection().remove(cliAlbCustomCollectionCliAlbCustom);
                    oldCliAlbCabOfCliAlbCustomCollectionCliAlbCustom = em.merge(oldCliAlbCabOfCliAlbCustomCollectionCliAlbCustom);
                }
            }
            for (CliFacCab cliFacCabCollectionCliFacCab : cliAlbCab.getCliFacCabCollection()) {
                CliAlbCab oldCodAlbOfCliFacCabCollectionCliFacCab = cliFacCabCollectionCliFacCab.getCodAlb();
                cliFacCabCollectionCliFacCab.setCodAlb(cliAlbCab);
                cliFacCabCollectionCliFacCab = em.merge(cliFacCabCollectionCliFacCab);
                if (oldCodAlbOfCliFacCabCollectionCliFacCab != null) {
                    oldCodAlbOfCliFacCabCollectionCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionCliFacCab);
                    oldCodAlbOfCliFacCabCollectionCliFacCab = em.merge(oldCodAlbOfCliFacCabCollectionCliFacCab);
                }
            }
            for (CliAlbLin cliAlbLinCollectionCliAlbLin : cliAlbCab.getCliAlbLinCollection()) {
                CliAlbCab oldCodCabOfCliAlbLinCollectionCliAlbLin = cliAlbLinCollectionCliAlbLin.getCodCab();
                cliAlbLinCollectionCliAlbLin.setCodCab(cliAlbCab);
                cliAlbLinCollectionCliAlbLin = em.merge(cliAlbLinCollectionCliAlbLin);
                if (oldCodCabOfCliAlbLinCollectionCliAlbLin != null) {
                    oldCodCabOfCliAlbLinCollectionCliAlbLin.getCliAlbLinCollection().remove(cliAlbLinCollectionCliAlbLin);
                    oldCodCabOfCliAlbLinCollectionCliAlbLin = em.merge(oldCodCabOfCliAlbLinCollectionCliAlbLin);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CliAlbCab cliAlbCab) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliAlbCab persistentCliAlbCab = em.find(CliAlbCab.class, cliAlbCab.getId());
            Usuarios usuarioOld = persistentCliAlbCab.getUsuario();
            Usuarios usuarioNew = cliAlbCab.getUsuario();
            Clientes codCliOld = persistentCliAlbCab.getCodCli();
            Clientes codCliNew = cliAlbCab.getCodCli();
            Empresas codEmpOld = persistentCliAlbCab.getCodEmp();
            Empresas codEmpNew = cliAlbCab.getCodEmp();
            CliPedidosCab codPedidoOld = persistentCliAlbCab.getCodPedido();
            CliPedidosCab codPedidoNew = cliAlbCab.getCodPedido();
            Collection<CliAlbCustom> cliAlbCustomCollectionOld = persistentCliAlbCab.getCliAlbCustomCollection();
            Collection<CliAlbCustom> cliAlbCustomCollectionNew = cliAlbCab.getCliAlbCustomCollection();
            Collection<CliFacCab> cliFacCabCollectionOld = persistentCliAlbCab.getCliFacCabCollection();
            Collection<CliFacCab> cliFacCabCollectionNew = cliAlbCab.getCliFacCabCollection();
            Collection<CliAlbLin> cliAlbLinCollectionOld = persistentCliAlbCab.getCliAlbLinCollection();
            Collection<CliAlbLin> cliAlbLinCollectionNew = cliAlbCab.getCliAlbLinCollection();
            List<String> illegalOrphanMessages = null;
            for (CliAlbCustom cliAlbCustomCollectionOldCliAlbCustom : cliAlbCustomCollectionOld) {
                if (!cliAlbCustomCollectionNew.contains(cliAlbCustomCollectionOldCliAlbCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliAlbCustom " + cliAlbCustomCollectionOldCliAlbCustom + " since its cliAlbCab field is not nullable.");
                }
            }
            for (CliAlbLin cliAlbLinCollectionOldCliAlbLin : cliAlbLinCollectionOld) {
                if (!cliAlbLinCollectionNew.contains(cliAlbLinCollectionOldCliAlbLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliAlbLin " + cliAlbLinCollectionOldCliAlbLin + " since its codCab field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                cliAlbCab.setUsuario(usuarioNew);
            }
            if (codCliNew != null) {
                codCliNew = em.getReference(codCliNew.getClass(), codCliNew.getCodigo());
                cliAlbCab.setCodCli(codCliNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cliAlbCab.setCodEmp(codEmpNew);
            }
            if (codPedidoNew != null) {
                codPedidoNew = em.getReference(codPedidoNew.getClass(), codPedidoNew.getId());
                cliAlbCab.setCodPedido(codPedidoNew);
            }
            Collection<CliAlbCustom> attachedCliAlbCustomCollectionNew = new ArrayList<CliAlbCustom>();
            for (CliAlbCustom cliAlbCustomCollectionNewCliAlbCustomToAttach : cliAlbCustomCollectionNew) {
                cliAlbCustomCollectionNewCliAlbCustomToAttach = em.getReference(cliAlbCustomCollectionNewCliAlbCustomToAttach.getClass(), cliAlbCustomCollectionNewCliAlbCustomToAttach.getCliAlbCustomPK());
                attachedCliAlbCustomCollectionNew.add(cliAlbCustomCollectionNewCliAlbCustomToAttach);
            }
            cliAlbCustomCollectionNew = attachedCliAlbCustomCollectionNew;
            cliAlbCab.setCliAlbCustomCollection(cliAlbCustomCollectionNew);
            Collection<CliFacCab> attachedCliFacCabCollectionNew = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionNewCliFacCabToAttach : cliFacCabCollectionNew) {
                cliFacCabCollectionNewCliFacCabToAttach = em.getReference(cliFacCabCollectionNewCliFacCabToAttach.getClass(), cliFacCabCollectionNewCliFacCabToAttach.getId());
                attachedCliFacCabCollectionNew.add(cliFacCabCollectionNewCliFacCabToAttach);
            }
            cliFacCabCollectionNew = attachedCliFacCabCollectionNew;
            cliAlbCab.setCliFacCabCollection(cliFacCabCollectionNew);
            Collection<CliAlbLin> attachedCliAlbLinCollectionNew = new ArrayList<CliAlbLin>();
            for (CliAlbLin cliAlbLinCollectionNewCliAlbLinToAttach : cliAlbLinCollectionNew) {
                cliAlbLinCollectionNewCliAlbLinToAttach = em.getReference(cliAlbLinCollectionNewCliAlbLinToAttach.getClass(), cliAlbLinCollectionNewCliAlbLinToAttach.getId());
                attachedCliAlbLinCollectionNew.add(cliAlbLinCollectionNewCliAlbLinToAttach);
            }
            cliAlbLinCollectionNew = attachedCliAlbLinCollectionNew;
            cliAlbCab.setCliAlbLinCollection(cliAlbLinCollectionNew);
            cliAlbCab = em.merge(cliAlbCab);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getCliAlbCabCollection().remove(cliAlbCab);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getCliAlbCabCollection().add(cliAlbCab);
                usuarioNew = em.merge(usuarioNew);
            }
            if (codCliOld != null && !codCliOld.equals(codCliNew)) {
                codCliOld.getCliAlbCabCollection().remove(cliAlbCab);
                codCliOld = em.merge(codCliOld);
            }
            if (codCliNew != null && !codCliNew.equals(codCliOld)) {
                codCliNew.getCliAlbCabCollection().add(cliAlbCab);
                codCliNew = em.merge(codCliNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCliAlbCabCollection().remove(cliAlbCab);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCliAlbCabCollection().add(cliAlbCab);
                codEmpNew = em.merge(codEmpNew);
            }
            if (codPedidoOld != null && !codPedidoOld.equals(codPedidoNew)) {
                codPedidoOld.getCliAlbCabCollection().remove(cliAlbCab);
                codPedidoOld = em.merge(codPedidoOld);
            }
            if (codPedidoNew != null && !codPedidoNew.equals(codPedidoOld)) {
                codPedidoNew.getCliAlbCabCollection().add(cliAlbCab);
                codPedidoNew = em.merge(codPedidoNew);
            }
            for (CliAlbCustom cliAlbCustomCollectionNewCliAlbCustom : cliAlbCustomCollectionNew) {
                if (!cliAlbCustomCollectionOld.contains(cliAlbCustomCollectionNewCliAlbCustom)) {
                    CliAlbCab oldCliAlbCabOfCliAlbCustomCollectionNewCliAlbCustom = cliAlbCustomCollectionNewCliAlbCustom.getCliAlbCab();
                    cliAlbCustomCollectionNewCliAlbCustom.setCliAlbCab(cliAlbCab);
                    cliAlbCustomCollectionNewCliAlbCustom = em.merge(cliAlbCustomCollectionNewCliAlbCustom);
                    if (oldCliAlbCabOfCliAlbCustomCollectionNewCliAlbCustom != null && !oldCliAlbCabOfCliAlbCustomCollectionNewCliAlbCustom.equals(cliAlbCab)) {
                        oldCliAlbCabOfCliAlbCustomCollectionNewCliAlbCustom.getCliAlbCustomCollection().remove(cliAlbCustomCollectionNewCliAlbCustom);
                        oldCliAlbCabOfCliAlbCustomCollectionNewCliAlbCustom = em.merge(oldCliAlbCabOfCliAlbCustomCollectionNewCliAlbCustom);
                    }
                }
            }
            for (CliFacCab cliFacCabCollectionOldCliFacCab : cliFacCabCollectionOld) {
                if (!cliFacCabCollectionNew.contains(cliFacCabCollectionOldCliFacCab)) {
                    cliFacCabCollectionOldCliFacCab.setCodAlb(null);
                    cliFacCabCollectionOldCliFacCab = em.merge(cliFacCabCollectionOldCliFacCab);
                }
            }
            for (CliFacCab cliFacCabCollectionNewCliFacCab : cliFacCabCollectionNew) {
                if (!cliFacCabCollectionOld.contains(cliFacCabCollectionNewCliFacCab)) {
                    CliAlbCab oldCodAlbOfCliFacCabCollectionNewCliFacCab = cliFacCabCollectionNewCliFacCab.getCodAlb();
                    cliFacCabCollectionNewCliFacCab.setCodAlb(cliAlbCab);
                    cliFacCabCollectionNewCliFacCab = em.merge(cliFacCabCollectionNewCliFacCab);
                    if (oldCodAlbOfCliFacCabCollectionNewCliFacCab != null && !oldCodAlbOfCliFacCabCollectionNewCliFacCab.equals(cliAlbCab)) {
                        oldCodAlbOfCliFacCabCollectionNewCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionNewCliFacCab);
                        oldCodAlbOfCliFacCabCollectionNewCliFacCab = em.merge(oldCodAlbOfCliFacCabCollectionNewCliFacCab);
                    }
                }
            }
            for (CliAlbLin cliAlbLinCollectionNewCliAlbLin : cliAlbLinCollectionNew) {
                if (!cliAlbLinCollectionOld.contains(cliAlbLinCollectionNewCliAlbLin)) {
                    CliAlbCab oldCodCabOfCliAlbLinCollectionNewCliAlbLin = cliAlbLinCollectionNewCliAlbLin.getCodCab();
                    cliAlbLinCollectionNewCliAlbLin.setCodCab(cliAlbCab);
                    cliAlbLinCollectionNewCliAlbLin = em.merge(cliAlbLinCollectionNewCliAlbLin);
                    if (oldCodCabOfCliAlbLinCollectionNewCliAlbLin != null && !oldCodCabOfCliAlbLinCollectionNewCliAlbLin.equals(cliAlbCab)) {
                        oldCodCabOfCliAlbLinCollectionNewCliAlbLin.getCliAlbLinCollection().remove(cliAlbLinCollectionNewCliAlbLin);
                        oldCodCabOfCliAlbLinCollectionNewCliAlbLin = em.merge(oldCodCabOfCliAlbLinCollectionNewCliAlbLin);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliAlbCab.getId();
                if (findCliAlbCab(id) == null) {
                    throw new NonexistentEntityException("The cliAlbCab with id " + id + " no longer exists.");
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
            CliAlbCab cliAlbCab;
            try {
                cliAlbCab = em.getReference(CliAlbCab.class, id);
                cliAlbCab.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliAlbCab with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CliAlbCustom> cliAlbCustomCollectionOrphanCheck = cliAlbCab.getCliAlbCustomCollection();
            for (CliAlbCustom cliAlbCustomCollectionOrphanCheckCliAlbCustom : cliAlbCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CliAlbCab (" + cliAlbCab + ") cannot be destroyed since the CliAlbCustom " + cliAlbCustomCollectionOrphanCheckCliAlbCustom + " in its cliAlbCustomCollection field has a non-nullable cliAlbCab field.");
            }
            Collection<CliAlbLin> cliAlbLinCollectionOrphanCheck = cliAlbCab.getCliAlbLinCollection();
            for (CliAlbLin cliAlbLinCollectionOrphanCheckCliAlbLin : cliAlbLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CliAlbCab (" + cliAlbCab + ") cannot be destroyed since the CliAlbLin " + cliAlbLinCollectionOrphanCheckCliAlbLin + " in its cliAlbLinCollection field has a non-nullable codCab field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios usuario = cliAlbCab.getUsuario();
            if (usuario != null) {
                usuario.getCliAlbCabCollection().remove(cliAlbCab);
                usuario = em.merge(usuario);
            }
            Clientes codCli = cliAlbCab.getCodCli();
            if (codCli != null) {
                codCli.getCliAlbCabCollection().remove(cliAlbCab);
                codCli = em.merge(codCli);
            }
            Empresas codEmp = cliAlbCab.getCodEmp();
            if (codEmp != null) {
                codEmp.getCliAlbCabCollection().remove(cliAlbCab);
                codEmp = em.merge(codEmp);
            }
            CliPedidosCab codPedido = cliAlbCab.getCodPedido();
            if (codPedido != null) {
                codPedido.getCliAlbCabCollection().remove(cliAlbCab);
                codPedido = em.merge(codPedido);
            }
            Collection<CliFacCab> cliFacCabCollection = cliAlbCab.getCliFacCabCollection();
            for (CliFacCab cliFacCabCollectionCliFacCab : cliFacCabCollection) {
                cliFacCabCollectionCliFacCab.setCodAlb(null);
                cliFacCabCollectionCliFacCab = em.merge(cliFacCabCollectionCliFacCab);
            }
            em.remove(cliAlbCab);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CliAlbCab> findCliAlbCabEntities() {
        return findCliAlbCabEntities(true, -1, -1);
    }

    public List<CliAlbCab> findCliAlbCabEntities(int maxResults, int firstResult) {
        return findCliAlbCabEntities(false, maxResults, firstResult);
    }

    private List<CliAlbCab> findCliAlbCabEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CliAlbCab as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CliAlbCab findCliAlbCab(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CliAlbCab.class, id);
        } finally {
            em.close();
        }
    }

    public int getCliAlbCabCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CliAlbCab as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
