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
import es.timmps.fac.persistencia.pojos.CliFacCab;
import es.timmps.fac.persistencia.pojos.CliPedidosCab;
import es.timmps.fac.persistencia.pojos.CliFacLin;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.pojos.CliFacCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class CliFacCabJpaController implements Serializable {

    public CliFacCabJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CliFacCab cliFacCab) {
        if (cliFacCab.getCliFacLinCollection() == null) {
            cliFacCab.setCliFacLinCollection(new ArrayList<CliFacLin>());
        }
        if (cliFacCab.getCliFacCustomCollection() == null) {
            cliFacCab.setCliFacCustomCollection(new ArrayList<CliFacCustom>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuario = cliFacCab.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                cliFacCab.setUsuario(usuario);
            }
            Clientes codCli = cliFacCab.getCodCli();
            if (codCli != null) {
                codCli = em.getReference(codCli.getClass(), codCli.getCodigo());
                cliFacCab.setCodCli(codCli);
            }
            Empresas codEmp = cliFacCab.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                cliFacCab.setCodEmp(codEmp);
            }
            CliAlbCab codAlb = cliFacCab.getCodAlb();
            if (codAlb != null) {
                codAlb = em.getReference(codAlb.getClass(), codAlb.getId());
                cliFacCab.setCodAlb(codAlb);
            }
            CliPedidosCab codPedido = cliFacCab.getCodPedido();
            if (codPedido != null) {
                codPedido = em.getReference(codPedido.getClass(), codPedido.getId());
                cliFacCab.setCodPedido(codPedido);
            }
            Collection<CliFacLin> attachedCliFacLinCollection = new ArrayList<CliFacLin>();
            for (CliFacLin cliFacLinCollectionCliFacLinToAttach : cliFacCab.getCliFacLinCollection()) {
                cliFacLinCollectionCliFacLinToAttach = em.getReference(cliFacLinCollectionCliFacLinToAttach.getClass(), cliFacLinCollectionCliFacLinToAttach.getId());
                attachedCliFacLinCollection.add(cliFacLinCollectionCliFacLinToAttach);
            }
            cliFacCab.setCliFacLinCollection(attachedCliFacLinCollection);
            Collection<CliFacCustom> attachedCliFacCustomCollection = new ArrayList<CliFacCustom>();
            for (CliFacCustom cliFacCustomCollectionCliFacCustomToAttach : cliFacCab.getCliFacCustomCollection()) {
                cliFacCustomCollectionCliFacCustomToAttach = em.getReference(cliFacCustomCollectionCliFacCustomToAttach.getClass(), cliFacCustomCollectionCliFacCustomToAttach.getCliFacCustomPK());
                attachedCliFacCustomCollection.add(cliFacCustomCollectionCliFacCustomToAttach);
            }
            cliFacCab.setCliFacCustomCollection(attachedCliFacCustomCollection);
            em.persist(cliFacCab);
            if (usuario != null) {
                usuario.getCliFacCabCollection().add(cliFacCab);
                usuario = em.merge(usuario);
            }
            if (codCli != null) {
                codCli.getCliFacCabCollection().add(cliFacCab);
                codCli = em.merge(codCli);
            }
            if (codEmp != null) {
                codEmp.getCliFacCabCollection().add(cliFacCab);
                codEmp = em.merge(codEmp);
            }
            if (codAlb != null) {
                codAlb.getCliFacCabCollection().add(cliFacCab);
                codAlb = em.merge(codAlb);
            }
            if (codPedido != null) {
                codPedido.getCliFacCabCollection().add(cliFacCab);
                codPedido = em.merge(codPedido);
            }
            for (CliFacLin cliFacLinCollectionCliFacLin : cliFacCab.getCliFacLinCollection()) {
                CliFacCab oldCodCabOfCliFacLinCollectionCliFacLin = cliFacLinCollectionCliFacLin.getCodCab();
                cliFacLinCollectionCliFacLin.setCodCab(cliFacCab);
                cliFacLinCollectionCliFacLin = em.merge(cliFacLinCollectionCliFacLin);
                if (oldCodCabOfCliFacLinCollectionCliFacLin != null) {
                    oldCodCabOfCliFacLinCollectionCliFacLin.getCliFacLinCollection().remove(cliFacLinCollectionCliFacLin);
                    oldCodCabOfCliFacLinCollectionCliFacLin = em.merge(oldCodCabOfCliFacLinCollectionCliFacLin);
                }
            }
            for (CliFacCustom cliFacCustomCollectionCliFacCustom : cliFacCab.getCliFacCustomCollection()) {
                CliFacCab oldCliFacCabOfCliFacCustomCollectionCliFacCustom = cliFacCustomCollectionCliFacCustom.getCliFacCab();
                cliFacCustomCollectionCliFacCustom.setCliFacCab(cliFacCab);
                cliFacCustomCollectionCliFacCustom = em.merge(cliFacCustomCollectionCliFacCustom);
                if (oldCliFacCabOfCliFacCustomCollectionCliFacCustom != null) {
                    oldCliFacCabOfCliFacCustomCollectionCliFacCustom.getCliFacCustomCollection().remove(cliFacCustomCollectionCliFacCustom);
                    oldCliFacCabOfCliFacCustomCollectionCliFacCustom = em.merge(oldCliFacCabOfCliFacCustomCollectionCliFacCustom);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CliFacCab cliFacCab) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CliFacCab persistentCliFacCab = em.find(CliFacCab.class, cliFacCab.getId());
            Usuarios usuarioOld = persistentCliFacCab.getUsuario();
            Usuarios usuarioNew = cliFacCab.getUsuario();
            Clientes codCliOld = persistentCliFacCab.getCodCli();
            Clientes codCliNew = cliFacCab.getCodCli();
            Empresas codEmpOld = persistentCliFacCab.getCodEmp();
            Empresas codEmpNew = cliFacCab.getCodEmp();
            CliAlbCab codAlbOld = persistentCliFacCab.getCodAlb();
            CliAlbCab codAlbNew = cliFacCab.getCodAlb();
            CliPedidosCab codPedidoOld = persistentCliFacCab.getCodPedido();
            CliPedidosCab codPedidoNew = cliFacCab.getCodPedido();
            Collection<CliFacLin> cliFacLinCollectionOld = persistentCliFacCab.getCliFacLinCollection();
            Collection<CliFacLin> cliFacLinCollectionNew = cliFacCab.getCliFacLinCollection();
            Collection<CliFacCustom> cliFacCustomCollectionOld = persistentCliFacCab.getCliFacCustomCollection();
            Collection<CliFacCustom> cliFacCustomCollectionNew = cliFacCab.getCliFacCustomCollection();
            List<String> illegalOrphanMessages = null;
            for (CliFacLin cliFacLinCollectionOldCliFacLin : cliFacLinCollectionOld) {
                if (!cliFacLinCollectionNew.contains(cliFacLinCollectionOldCliFacLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliFacLin " + cliFacLinCollectionOldCliFacLin + " since its codCab field is not nullable.");
                }
            }
            for (CliFacCustom cliFacCustomCollectionOldCliFacCustom : cliFacCustomCollectionOld) {
                if (!cliFacCustomCollectionNew.contains(cliFacCustomCollectionOldCliFacCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliFacCustom " + cliFacCustomCollectionOldCliFacCustom + " since its cliFacCab field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                cliFacCab.setUsuario(usuarioNew);
            }
            if (codCliNew != null) {
                codCliNew = em.getReference(codCliNew.getClass(), codCliNew.getCodigo());
                cliFacCab.setCodCli(codCliNew);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                cliFacCab.setCodEmp(codEmpNew);
            }
            if (codAlbNew != null) {
                codAlbNew = em.getReference(codAlbNew.getClass(), codAlbNew.getId());
                cliFacCab.setCodAlb(codAlbNew);
            }
            if (codPedidoNew != null) {
                codPedidoNew = em.getReference(codPedidoNew.getClass(), codPedidoNew.getId());
                cliFacCab.setCodPedido(codPedidoNew);
            }
            Collection<CliFacLin> attachedCliFacLinCollectionNew = new ArrayList<CliFacLin>();
            for (CliFacLin cliFacLinCollectionNewCliFacLinToAttach : cliFacLinCollectionNew) {
                cliFacLinCollectionNewCliFacLinToAttach = em.getReference(cliFacLinCollectionNewCliFacLinToAttach.getClass(), cliFacLinCollectionNewCliFacLinToAttach.getId());
                attachedCliFacLinCollectionNew.add(cliFacLinCollectionNewCliFacLinToAttach);
            }
            cliFacLinCollectionNew = attachedCliFacLinCollectionNew;
            cliFacCab.setCliFacLinCollection(cliFacLinCollectionNew);
            Collection<CliFacCustom> attachedCliFacCustomCollectionNew = new ArrayList<CliFacCustom>();
            for (CliFacCustom cliFacCustomCollectionNewCliFacCustomToAttach : cliFacCustomCollectionNew) {
                cliFacCustomCollectionNewCliFacCustomToAttach = em.getReference(cliFacCustomCollectionNewCliFacCustomToAttach.getClass(), cliFacCustomCollectionNewCliFacCustomToAttach.getCliFacCustomPK());
                attachedCliFacCustomCollectionNew.add(cliFacCustomCollectionNewCliFacCustomToAttach);
            }
            cliFacCustomCollectionNew = attachedCliFacCustomCollectionNew;
            cliFacCab.setCliFacCustomCollection(cliFacCustomCollectionNew);
            cliFacCab = em.merge(cliFacCab);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getCliFacCabCollection().remove(cliFacCab);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getCliFacCabCollection().add(cliFacCab);
                usuarioNew = em.merge(usuarioNew);
            }
            if (codCliOld != null && !codCliOld.equals(codCliNew)) {
                codCliOld.getCliFacCabCollection().remove(cliFacCab);
                codCliOld = em.merge(codCliOld);
            }
            if (codCliNew != null && !codCliNew.equals(codCliOld)) {
                codCliNew.getCliFacCabCollection().add(cliFacCab);
                codCliNew = em.merge(codCliNew);
            }
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getCliFacCabCollection().remove(cliFacCab);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getCliFacCabCollection().add(cliFacCab);
                codEmpNew = em.merge(codEmpNew);
            }
            if (codAlbOld != null && !codAlbOld.equals(codAlbNew)) {
                codAlbOld.getCliFacCabCollection().remove(cliFacCab);
                codAlbOld = em.merge(codAlbOld);
            }
            if (codAlbNew != null && !codAlbNew.equals(codAlbOld)) {
                codAlbNew.getCliFacCabCollection().add(cliFacCab);
                codAlbNew = em.merge(codAlbNew);
            }
            if (codPedidoOld != null && !codPedidoOld.equals(codPedidoNew)) {
                codPedidoOld.getCliFacCabCollection().remove(cliFacCab);
                codPedidoOld = em.merge(codPedidoOld);
            }
            if (codPedidoNew != null && !codPedidoNew.equals(codPedidoOld)) {
                codPedidoNew.getCliFacCabCollection().add(cliFacCab);
                codPedidoNew = em.merge(codPedidoNew);
            }
            for (CliFacLin cliFacLinCollectionNewCliFacLin : cliFacLinCollectionNew) {
                if (!cliFacLinCollectionOld.contains(cliFacLinCollectionNewCliFacLin)) {
                    CliFacCab oldCodCabOfCliFacLinCollectionNewCliFacLin = cliFacLinCollectionNewCliFacLin.getCodCab();
                    cliFacLinCollectionNewCliFacLin.setCodCab(cliFacCab);
                    cliFacLinCollectionNewCliFacLin = em.merge(cliFacLinCollectionNewCliFacLin);
                    if (oldCodCabOfCliFacLinCollectionNewCliFacLin != null && !oldCodCabOfCliFacLinCollectionNewCliFacLin.equals(cliFacCab)) {
                        oldCodCabOfCliFacLinCollectionNewCliFacLin.getCliFacLinCollection().remove(cliFacLinCollectionNewCliFacLin);
                        oldCodCabOfCliFacLinCollectionNewCliFacLin = em.merge(oldCodCabOfCliFacLinCollectionNewCliFacLin);
                    }
                }
            }
            for (CliFacCustom cliFacCustomCollectionNewCliFacCustom : cliFacCustomCollectionNew) {
                if (!cliFacCustomCollectionOld.contains(cliFacCustomCollectionNewCliFacCustom)) {
                    CliFacCab oldCliFacCabOfCliFacCustomCollectionNewCliFacCustom = cliFacCustomCollectionNewCliFacCustom.getCliFacCab();
                    cliFacCustomCollectionNewCliFacCustom.setCliFacCab(cliFacCab);
                    cliFacCustomCollectionNewCliFacCustom = em.merge(cliFacCustomCollectionNewCliFacCustom);
                    if (oldCliFacCabOfCliFacCustomCollectionNewCliFacCustom != null && !oldCliFacCabOfCliFacCustomCollectionNewCliFacCustom.equals(cliFacCab)) {
                        oldCliFacCabOfCliFacCustomCollectionNewCliFacCustom.getCliFacCustomCollection().remove(cliFacCustomCollectionNewCliFacCustom);
                        oldCliFacCabOfCliFacCustomCollectionNewCliFacCustom = em.merge(oldCliFacCabOfCliFacCustomCollectionNewCliFacCustom);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliFacCab.getId();
                if (findCliFacCab(id) == null) {
                    throw new NonexistentEntityException("The cliFacCab with id " + id + " no longer exists.");
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
            CliFacCab cliFacCab;
            try {
                cliFacCab = em.getReference(CliFacCab.class, id);
                cliFacCab.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliFacCab with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CliFacLin> cliFacLinCollectionOrphanCheck = cliFacCab.getCliFacLinCollection();
            for (CliFacLin cliFacLinCollectionOrphanCheckCliFacLin : cliFacLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CliFacCab (" + cliFacCab + ") cannot be destroyed since the CliFacLin " + cliFacLinCollectionOrphanCheckCliFacLin + " in its cliFacLinCollection field has a non-nullable codCab field.");
            }
            Collection<CliFacCustom> cliFacCustomCollectionOrphanCheck = cliFacCab.getCliFacCustomCollection();
            for (CliFacCustom cliFacCustomCollectionOrphanCheckCliFacCustom : cliFacCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CliFacCab (" + cliFacCab + ") cannot be destroyed since the CliFacCustom " + cliFacCustomCollectionOrphanCheckCliFacCustom + " in its cliFacCustomCollection field has a non-nullable cliFacCab field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios usuario = cliFacCab.getUsuario();
            if (usuario != null) {
                usuario.getCliFacCabCollection().remove(cliFacCab);
                usuario = em.merge(usuario);
            }
            Clientes codCli = cliFacCab.getCodCli();
            if (codCli != null) {
                codCli.getCliFacCabCollection().remove(cliFacCab);
                codCli = em.merge(codCli);
            }
            Empresas codEmp = cliFacCab.getCodEmp();
            if (codEmp != null) {
                codEmp.getCliFacCabCollection().remove(cliFacCab);
                codEmp = em.merge(codEmp);
            }
            CliAlbCab codAlb = cliFacCab.getCodAlb();
            if (codAlb != null) {
                codAlb.getCliFacCabCollection().remove(cliFacCab);
                codAlb = em.merge(codAlb);
            }
            CliPedidosCab codPedido = cliFacCab.getCodPedido();
            if (codPedido != null) {
                codPedido.getCliFacCabCollection().remove(cliFacCab);
                codPedido = em.merge(codPedido);
            }
            em.remove(cliFacCab);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CliFacCab> findCliFacCabEntities() {
        return findCliFacCabEntities(true, -1, -1);
    }

    public List<CliFacCab> findCliFacCabEntities(int maxResults, int firstResult) {
        return findCliFacCabEntities(false, maxResults, firstResult);
    }

    private List<CliFacCab> findCliFacCabEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CliFacCab.class));
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

    public CliFacCab findCliFacCab(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CliFacCab.class, id);
        } finally {
            em.close();
        }
    }

    public int getCliFacCabCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CliFacCab> rt = cq.from(CliFacCab.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
