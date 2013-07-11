/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.pojos.CfTipoImpuesto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.timmps.fac.persistencia.pojos.ProvFacLin;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.pojos.ProvPedidosLin;
import es.timmps.fac.persistencia.pojos.ProvAlbLin;
import es.timmps.fac.persistencia.pojos.CliPedidosLin;
import es.timmps.fac.persistencia.pojos.CliAlbLin;
import es.timmps.fac.persistencia.pojos.CliFacLin;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aperalta
 */
public class CfTipoImpuestoJpaController implements Serializable {

    public CfTipoImpuestoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfTipoImpuesto cfTipoImpuesto) {
        if (cfTipoImpuesto.getProvFacLinCollection() == null) {
            cfTipoImpuesto.setProvFacLinCollection(new ArrayList<ProvFacLin>());
        }
        if (cfTipoImpuesto.getProvPedidosLinCollection() == null) {
            cfTipoImpuesto.setProvPedidosLinCollection(new ArrayList<ProvPedidosLin>());
        }
        if (cfTipoImpuesto.getProvAlbLinCollection() == null) {
            cfTipoImpuesto.setProvAlbLinCollection(new ArrayList<ProvAlbLin>());
        }
        if (cfTipoImpuesto.getCliPedidosLinCollection() == null) {
            cfTipoImpuesto.setCliPedidosLinCollection(new ArrayList<CliPedidosLin>());
        }
        if (cfTipoImpuesto.getCliAlbLinCollection() == null) {
            cfTipoImpuesto.setCliAlbLinCollection(new ArrayList<CliAlbLin>());
        }
        if (cfTipoImpuesto.getCliFacLinCollection() == null) {
            cfTipoImpuesto.setCliFacLinCollection(new ArrayList<CliFacLin>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ProvFacLin> attachedProvFacLinCollection = new ArrayList<ProvFacLin>();
            for (ProvFacLin provFacLinCollectionProvFacLinToAttach : cfTipoImpuesto.getProvFacLinCollection()) {
                provFacLinCollectionProvFacLinToAttach = em.getReference(provFacLinCollectionProvFacLinToAttach.getClass(), provFacLinCollectionProvFacLinToAttach.getId());
                attachedProvFacLinCollection.add(provFacLinCollectionProvFacLinToAttach);
            }
            cfTipoImpuesto.setProvFacLinCollection(attachedProvFacLinCollection);
            Collection<ProvPedidosLin> attachedProvPedidosLinCollection = new ArrayList<ProvPedidosLin>();
            for (ProvPedidosLin provPedidosLinCollectionProvPedidosLinToAttach : cfTipoImpuesto.getProvPedidosLinCollection()) {
                provPedidosLinCollectionProvPedidosLinToAttach = em.getReference(provPedidosLinCollectionProvPedidosLinToAttach.getClass(), provPedidosLinCollectionProvPedidosLinToAttach.getId());
                attachedProvPedidosLinCollection.add(provPedidosLinCollectionProvPedidosLinToAttach);
            }
            cfTipoImpuesto.setProvPedidosLinCollection(attachedProvPedidosLinCollection);
            Collection<ProvAlbLin> attachedProvAlbLinCollection = new ArrayList<ProvAlbLin>();
            for (ProvAlbLin provAlbLinCollectionProvAlbLinToAttach : cfTipoImpuesto.getProvAlbLinCollection()) {
                provAlbLinCollectionProvAlbLinToAttach = em.getReference(provAlbLinCollectionProvAlbLinToAttach.getClass(), provAlbLinCollectionProvAlbLinToAttach.getId());
                attachedProvAlbLinCollection.add(provAlbLinCollectionProvAlbLinToAttach);
            }
            cfTipoImpuesto.setProvAlbLinCollection(attachedProvAlbLinCollection);
            Collection<CliPedidosLin> attachedCliPedidosLinCollection = new ArrayList<CliPedidosLin>();
            for (CliPedidosLin cliPedidosLinCollectionCliPedidosLinToAttach : cfTipoImpuesto.getCliPedidosLinCollection()) {
                cliPedidosLinCollectionCliPedidosLinToAttach = em.getReference(cliPedidosLinCollectionCliPedidosLinToAttach.getClass(), cliPedidosLinCollectionCliPedidosLinToAttach.getId());
                attachedCliPedidosLinCollection.add(cliPedidosLinCollectionCliPedidosLinToAttach);
            }
            cfTipoImpuesto.setCliPedidosLinCollection(attachedCliPedidosLinCollection);
            Collection<CliAlbLin> attachedCliAlbLinCollection = new ArrayList<CliAlbLin>();
            for (CliAlbLin cliAlbLinCollectionCliAlbLinToAttach : cfTipoImpuesto.getCliAlbLinCollection()) {
                cliAlbLinCollectionCliAlbLinToAttach = em.getReference(cliAlbLinCollectionCliAlbLinToAttach.getClass(), cliAlbLinCollectionCliAlbLinToAttach.getId());
                attachedCliAlbLinCollection.add(cliAlbLinCollectionCliAlbLinToAttach);
            }
            cfTipoImpuesto.setCliAlbLinCollection(attachedCliAlbLinCollection);
            Collection<CliFacLin> attachedCliFacLinCollection = new ArrayList<CliFacLin>();
            for (CliFacLin cliFacLinCollectionCliFacLinToAttach : cfTipoImpuesto.getCliFacLinCollection()) {
                cliFacLinCollectionCliFacLinToAttach = em.getReference(cliFacLinCollectionCliFacLinToAttach.getClass(), cliFacLinCollectionCliFacLinToAttach.getId());
                attachedCliFacLinCollection.add(cliFacLinCollectionCliFacLinToAttach);
            }
            cfTipoImpuesto.setCliFacLinCollection(attachedCliFacLinCollection);
            em.persist(cfTipoImpuesto);
            for (ProvFacLin provFacLinCollectionProvFacLin : cfTipoImpuesto.getProvFacLinCollection()) {
                CfTipoImpuesto oldTipoIvaOfProvFacLinCollectionProvFacLin = provFacLinCollectionProvFacLin.getTipoIva();
                provFacLinCollectionProvFacLin.setTipoIva(cfTipoImpuesto);
                provFacLinCollectionProvFacLin = em.merge(provFacLinCollectionProvFacLin);
                if (oldTipoIvaOfProvFacLinCollectionProvFacLin != null) {
                    oldTipoIvaOfProvFacLinCollectionProvFacLin.getProvFacLinCollection().remove(provFacLinCollectionProvFacLin);
                    oldTipoIvaOfProvFacLinCollectionProvFacLin = em.merge(oldTipoIvaOfProvFacLinCollectionProvFacLin);
                }
            }
            for (ProvPedidosLin provPedidosLinCollectionProvPedidosLin : cfTipoImpuesto.getProvPedidosLinCollection()) {
                CfTipoImpuesto oldTipoIvaOfProvPedidosLinCollectionProvPedidosLin = provPedidosLinCollectionProvPedidosLin.getTipoIva();
                provPedidosLinCollectionProvPedidosLin.setTipoIva(cfTipoImpuesto);
                provPedidosLinCollectionProvPedidosLin = em.merge(provPedidosLinCollectionProvPedidosLin);
                if (oldTipoIvaOfProvPedidosLinCollectionProvPedidosLin != null) {
                    oldTipoIvaOfProvPedidosLinCollectionProvPedidosLin.getProvPedidosLinCollection().remove(provPedidosLinCollectionProvPedidosLin);
                    oldTipoIvaOfProvPedidosLinCollectionProvPedidosLin = em.merge(oldTipoIvaOfProvPedidosLinCollectionProvPedidosLin);
                }
            }
            for (ProvAlbLin provAlbLinCollectionProvAlbLin : cfTipoImpuesto.getProvAlbLinCollection()) {
                CfTipoImpuesto oldTipoIvaOfProvAlbLinCollectionProvAlbLin = provAlbLinCollectionProvAlbLin.getTipoIva();
                provAlbLinCollectionProvAlbLin.setTipoIva(cfTipoImpuesto);
                provAlbLinCollectionProvAlbLin = em.merge(provAlbLinCollectionProvAlbLin);
                if (oldTipoIvaOfProvAlbLinCollectionProvAlbLin != null) {
                    oldTipoIvaOfProvAlbLinCollectionProvAlbLin.getProvAlbLinCollection().remove(provAlbLinCollectionProvAlbLin);
                    oldTipoIvaOfProvAlbLinCollectionProvAlbLin = em.merge(oldTipoIvaOfProvAlbLinCollectionProvAlbLin);
                }
            }
            for (CliPedidosLin cliPedidosLinCollectionCliPedidosLin : cfTipoImpuesto.getCliPedidosLinCollection()) {
                CfTipoImpuesto oldTipoIvaOfCliPedidosLinCollectionCliPedidosLin = cliPedidosLinCollectionCliPedidosLin.getTipoIva();
                cliPedidosLinCollectionCliPedidosLin.setTipoIva(cfTipoImpuesto);
                cliPedidosLinCollectionCliPedidosLin = em.merge(cliPedidosLinCollectionCliPedidosLin);
                if (oldTipoIvaOfCliPedidosLinCollectionCliPedidosLin != null) {
                    oldTipoIvaOfCliPedidosLinCollectionCliPedidosLin.getCliPedidosLinCollection().remove(cliPedidosLinCollectionCliPedidosLin);
                    oldTipoIvaOfCliPedidosLinCollectionCliPedidosLin = em.merge(oldTipoIvaOfCliPedidosLinCollectionCliPedidosLin);
                }
            }
            for (CliAlbLin cliAlbLinCollectionCliAlbLin : cfTipoImpuesto.getCliAlbLinCollection()) {
                CfTipoImpuesto oldTipoIvaOfCliAlbLinCollectionCliAlbLin = cliAlbLinCollectionCliAlbLin.getTipoIva();
                cliAlbLinCollectionCliAlbLin.setTipoIva(cfTipoImpuesto);
                cliAlbLinCollectionCliAlbLin = em.merge(cliAlbLinCollectionCliAlbLin);
                if (oldTipoIvaOfCliAlbLinCollectionCliAlbLin != null) {
                    oldTipoIvaOfCliAlbLinCollectionCliAlbLin.getCliAlbLinCollection().remove(cliAlbLinCollectionCliAlbLin);
                    oldTipoIvaOfCliAlbLinCollectionCliAlbLin = em.merge(oldTipoIvaOfCliAlbLinCollectionCliAlbLin);
                }
            }
            for (CliFacLin cliFacLinCollectionCliFacLin : cfTipoImpuesto.getCliFacLinCollection()) {
                CfTipoImpuesto oldTipoIvaOfCliFacLinCollectionCliFacLin = cliFacLinCollectionCliFacLin.getTipoIva();
                cliFacLinCollectionCliFacLin.setTipoIva(cfTipoImpuesto);
                cliFacLinCollectionCliFacLin = em.merge(cliFacLinCollectionCliFacLin);
                if (oldTipoIvaOfCliFacLinCollectionCliFacLin != null) {
                    oldTipoIvaOfCliFacLinCollectionCliFacLin.getCliFacLinCollection().remove(cliFacLinCollectionCliFacLin);
                    oldTipoIvaOfCliFacLinCollectionCliFacLin = em.merge(oldTipoIvaOfCliFacLinCollectionCliFacLin);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfTipoImpuesto cfTipoImpuesto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfTipoImpuesto persistentCfTipoImpuesto = em.find(CfTipoImpuesto.class, cfTipoImpuesto.getId());
            Collection<ProvFacLin> provFacLinCollectionOld = persistentCfTipoImpuesto.getProvFacLinCollection();
            Collection<ProvFacLin> provFacLinCollectionNew = cfTipoImpuesto.getProvFacLinCollection();
            Collection<ProvPedidosLin> provPedidosLinCollectionOld = persistentCfTipoImpuesto.getProvPedidosLinCollection();
            Collection<ProvPedidosLin> provPedidosLinCollectionNew = cfTipoImpuesto.getProvPedidosLinCollection();
            Collection<ProvAlbLin> provAlbLinCollectionOld = persistentCfTipoImpuesto.getProvAlbLinCollection();
            Collection<ProvAlbLin> provAlbLinCollectionNew = cfTipoImpuesto.getProvAlbLinCollection();
            Collection<CliPedidosLin> cliPedidosLinCollectionOld = persistentCfTipoImpuesto.getCliPedidosLinCollection();
            Collection<CliPedidosLin> cliPedidosLinCollectionNew = cfTipoImpuesto.getCliPedidosLinCollection();
            Collection<CliAlbLin> cliAlbLinCollectionOld = persistentCfTipoImpuesto.getCliAlbLinCollection();
            Collection<CliAlbLin> cliAlbLinCollectionNew = cfTipoImpuesto.getCliAlbLinCollection();
            Collection<CliFacLin> cliFacLinCollectionOld = persistentCfTipoImpuesto.getCliFacLinCollection();
            Collection<CliFacLin> cliFacLinCollectionNew = cfTipoImpuesto.getCliFacLinCollection();
            List<String> illegalOrphanMessages = null;
            for (ProvFacLin provFacLinCollectionOldProvFacLin : provFacLinCollectionOld) {
                if (!provFacLinCollectionNew.contains(provFacLinCollectionOldProvFacLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvFacLin " + provFacLinCollectionOldProvFacLin + " since its tipoIva field is not nullable.");
                }
            }
            for (ProvPedidosLin provPedidosLinCollectionOldProvPedidosLin : provPedidosLinCollectionOld) {
                if (!provPedidosLinCollectionNew.contains(provPedidosLinCollectionOldProvPedidosLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvPedidosLin " + provPedidosLinCollectionOldProvPedidosLin + " since its tipoIva field is not nullable.");
                }
            }
            for (ProvAlbLin provAlbLinCollectionOldProvAlbLin : provAlbLinCollectionOld) {
                if (!provAlbLinCollectionNew.contains(provAlbLinCollectionOldProvAlbLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvAlbLin " + provAlbLinCollectionOldProvAlbLin + " since its tipoIva field is not nullable.");
                }
            }
            for (CliPedidosLin cliPedidosLinCollectionOldCliPedidosLin : cliPedidosLinCollectionOld) {
                if (!cliPedidosLinCollectionNew.contains(cliPedidosLinCollectionOldCliPedidosLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliPedidosLin " + cliPedidosLinCollectionOldCliPedidosLin + " since its tipoIva field is not nullable.");
                }
            }
            for (CliAlbLin cliAlbLinCollectionOldCliAlbLin : cliAlbLinCollectionOld) {
                if (!cliAlbLinCollectionNew.contains(cliAlbLinCollectionOldCliAlbLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliAlbLin " + cliAlbLinCollectionOldCliAlbLin + " since its tipoIva field is not nullable.");
                }
            }
            for (CliFacLin cliFacLinCollectionOldCliFacLin : cliFacLinCollectionOld) {
                if (!cliFacLinCollectionNew.contains(cliFacLinCollectionOldCliFacLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliFacLin " + cliFacLinCollectionOldCliFacLin + " since its tipoIva field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ProvFacLin> attachedProvFacLinCollectionNew = new ArrayList<ProvFacLin>();
            for (ProvFacLin provFacLinCollectionNewProvFacLinToAttach : provFacLinCollectionNew) {
                provFacLinCollectionNewProvFacLinToAttach = em.getReference(provFacLinCollectionNewProvFacLinToAttach.getClass(), provFacLinCollectionNewProvFacLinToAttach.getId());
                attachedProvFacLinCollectionNew.add(provFacLinCollectionNewProvFacLinToAttach);
            }
            provFacLinCollectionNew = attachedProvFacLinCollectionNew;
            cfTipoImpuesto.setProvFacLinCollection(provFacLinCollectionNew);
            Collection<ProvPedidosLin> attachedProvPedidosLinCollectionNew = new ArrayList<ProvPedidosLin>();
            for (ProvPedidosLin provPedidosLinCollectionNewProvPedidosLinToAttach : provPedidosLinCollectionNew) {
                provPedidosLinCollectionNewProvPedidosLinToAttach = em.getReference(provPedidosLinCollectionNewProvPedidosLinToAttach.getClass(), provPedidosLinCollectionNewProvPedidosLinToAttach.getId());
                attachedProvPedidosLinCollectionNew.add(provPedidosLinCollectionNewProvPedidosLinToAttach);
            }
            provPedidosLinCollectionNew = attachedProvPedidosLinCollectionNew;
            cfTipoImpuesto.setProvPedidosLinCollection(provPedidosLinCollectionNew);
            Collection<ProvAlbLin> attachedProvAlbLinCollectionNew = new ArrayList<ProvAlbLin>();
            for (ProvAlbLin provAlbLinCollectionNewProvAlbLinToAttach : provAlbLinCollectionNew) {
                provAlbLinCollectionNewProvAlbLinToAttach = em.getReference(provAlbLinCollectionNewProvAlbLinToAttach.getClass(), provAlbLinCollectionNewProvAlbLinToAttach.getId());
                attachedProvAlbLinCollectionNew.add(provAlbLinCollectionNewProvAlbLinToAttach);
            }
            provAlbLinCollectionNew = attachedProvAlbLinCollectionNew;
            cfTipoImpuesto.setProvAlbLinCollection(provAlbLinCollectionNew);
            Collection<CliPedidosLin> attachedCliPedidosLinCollectionNew = new ArrayList<CliPedidosLin>();
            for (CliPedidosLin cliPedidosLinCollectionNewCliPedidosLinToAttach : cliPedidosLinCollectionNew) {
                cliPedidosLinCollectionNewCliPedidosLinToAttach = em.getReference(cliPedidosLinCollectionNewCliPedidosLinToAttach.getClass(), cliPedidosLinCollectionNewCliPedidosLinToAttach.getId());
                attachedCliPedidosLinCollectionNew.add(cliPedidosLinCollectionNewCliPedidosLinToAttach);
            }
            cliPedidosLinCollectionNew = attachedCliPedidosLinCollectionNew;
            cfTipoImpuesto.setCliPedidosLinCollection(cliPedidosLinCollectionNew);
            Collection<CliAlbLin> attachedCliAlbLinCollectionNew = new ArrayList<CliAlbLin>();
            for (CliAlbLin cliAlbLinCollectionNewCliAlbLinToAttach : cliAlbLinCollectionNew) {
                cliAlbLinCollectionNewCliAlbLinToAttach = em.getReference(cliAlbLinCollectionNewCliAlbLinToAttach.getClass(), cliAlbLinCollectionNewCliAlbLinToAttach.getId());
                attachedCliAlbLinCollectionNew.add(cliAlbLinCollectionNewCliAlbLinToAttach);
            }
            cliAlbLinCollectionNew = attachedCliAlbLinCollectionNew;
            cfTipoImpuesto.setCliAlbLinCollection(cliAlbLinCollectionNew);
            Collection<CliFacLin> attachedCliFacLinCollectionNew = new ArrayList<CliFacLin>();
            for (CliFacLin cliFacLinCollectionNewCliFacLinToAttach : cliFacLinCollectionNew) {
                cliFacLinCollectionNewCliFacLinToAttach = em.getReference(cliFacLinCollectionNewCliFacLinToAttach.getClass(), cliFacLinCollectionNewCliFacLinToAttach.getId());
                attachedCliFacLinCollectionNew.add(cliFacLinCollectionNewCliFacLinToAttach);
            }
            cliFacLinCollectionNew = attachedCliFacLinCollectionNew;
            cfTipoImpuesto.setCliFacLinCollection(cliFacLinCollectionNew);
            cfTipoImpuesto = em.merge(cfTipoImpuesto);
            for (ProvFacLin provFacLinCollectionNewProvFacLin : provFacLinCollectionNew) {
                if (!provFacLinCollectionOld.contains(provFacLinCollectionNewProvFacLin)) {
                    CfTipoImpuesto oldTipoIvaOfProvFacLinCollectionNewProvFacLin = provFacLinCollectionNewProvFacLin.getTipoIva();
                    provFacLinCollectionNewProvFacLin.setTipoIva(cfTipoImpuesto);
                    provFacLinCollectionNewProvFacLin = em.merge(provFacLinCollectionNewProvFacLin);
                    if (oldTipoIvaOfProvFacLinCollectionNewProvFacLin != null && !oldTipoIvaOfProvFacLinCollectionNewProvFacLin.equals(cfTipoImpuesto)) {
                        oldTipoIvaOfProvFacLinCollectionNewProvFacLin.getProvFacLinCollection().remove(provFacLinCollectionNewProvFacLin);
                        oldTipoIvaOfProvFacLinCollectionNewProvFacLin = em.merge(oldTipoIvaOfProvFacLinCollectionNewProvFacLin);
                    }
                }
            }
            for (ProvPedidosLin provPedidosLinCollectionNewProvPedidosLin : provPedidosLinCollectionNew) {
                if (!provPedidosLinCollectionOld.contains(provPedidosLinCollectionNewProvPedidosLin)) {
                    CfTipoImpuesto oldTipoIvaOfProvPedidosLinCollectionNewProvPedidosLin = provPedidosLinCollectionNewProvPedidosLin.getTipoIva();
                    provPedidosLinCollectionNewProvPedidosLin.setTipoIva(cfTipoImpuesto);
                    provPedidosLinCollectionNewProvPedidosLin = em.merge(provPedidosLinCollectionNewProvPedidosLin);
                    if (oldTipoIvaOfProvPedidosLinCollectionNewProvPedidosLin != null && !oldTipoIvaOfProvPedidosLinCollectionNewProvPedidosLin.equals(cfTipoImpuesto)) {
                        oldTipoIvaOfProvPedidosLinCollectionNewProvPedidosLin.getProvPedidosLinCollection().remove(provPedidosLinCollectionNewProvPedidosLin);
                        oldTipoIvaOfProvPedidosLinCollectionNewProvPedidosLin = em.merge(oldTipoIvaOfProvPedidosLinCollectionNewProvPedidosLin);
                    }
                }
            }
            for (ProvAlbLin provAlbLinCollectionNewProvAlbLin : provAlbLinCollectionNew) {
                if (!provAlbLinCollectionOld.contains(provAlbLinCollectionNewProvAlbLin)) {
                    CfTipoImpuesto oldTipoIvaOfProvAlbLinCollectionNewProvAlbLin = provAlbLinCollectionNewProvAlbLin.getTipoIva();
                    provAlbLinCollectionNewProvAlbLin.setTipoIva(cfTipoImpuesto);
                    provAlbLinCollectionNewProvAlbLin = em.merge(provAlbLinCollectionNewProvAlbLin);
                    if (oldTipoIvaOfProvAlbLinCollectionNewProvAlbLin != null && !oldTipoIvaOfProvAlbLinCollectionNewProvAlbLin.equals(cfTipoImpuesto)) {
                        oldTipoIvaOfProvAlbLinCollectionNewProvAlbLin.getProvAlbLinCollection().remove(provAlbLinCollectionNewProvAlbLin);
                        oldTipoIvaOfProvAlbLinCollectionNewProvAlbLin = em.merge(oldTipoIvaOfProvAlbLinCollectionNewProvAlbLin);
                    }
                }
            }
            for (CliPedidosLin cliPedidosLinCollectionNewCliPedidosLin : cliPedidosLinCollectionNew) {
                if (!cliPedidosLinCollectionOld.contains(cliPedidosLinCollectionNewCliPedidosLin)) {
                    CfTipoImpuesto oldTipoIvaOfCliPedidosLinCollectionNewCliPedidosLin = cliPedidosLinCollectionNewCliPedidosLin.getTipoIva();
                    cliPedidosLinCollectionNewCliPedidosLin.setTipoIva(cfTipoImpuesto);
                    cliPedidosLinCollectionNewCliPedidosLin = em.merge(cliPedidosLinCollectionNewCliPedidosLin);
                    if (oldTipoIvaOfCliPedidosLinCollectionNewCliPedidosLin != null && !oldTipoIvaOfCliPedidosLinCollectionNewCliPedidosLin.equals(cfTipoImpuesto)) {
                        oldTipoIvaOfCliPedidosLinCollectionNewCliPedidosLin.getCliPedidosLinCollection().remove(cliPedidosLinCollectionNewCliPedidosLin);
                        oldTipoIvaOfCliPedidosLinCollectionNewCliPedidosLin = em.merge(oldTipoIvaOfCliPedidosLinCollectionNewCliPedidosLin);
                    }
                }
            }
            for (CliAlbLin cliAlbLinCollectionNewCliAlbLin : cliAlbLinCollectionNew) {
                if (!cliAlbLinCollectionOld.contains(cliAlbLinCollectionNewCliAlbLin)) {
                    CfTipoImpuesto oldTipoIvaOfCliAlbLinCollectionNewCliAlbLin = cliAlbLinCollectionNewCliAlbLin.getTipoIva();
                    cliAlbLinCollectionNewCliAlbLin.setTipoIva(cfTipoImpuesto);
                    cliAlbLinCollectionNewCliAlbLin = em.merge(cliAlbLinCollectionNewCliAlbLin);
                    if (oldTipoIvaOfCliAlbLinCollectionNewCliAlbLin != null && !oldTipoIvaOfCliAlbLinCollectionNewCliAlbLin.equals(cfTipoImpuesto)) {
                        oldTipoIvaOfCliAlbLinCollectionNewCliAlbLin.getCliAlbLinCollection().remove(cliAlbLinCollectionNewCliAlbLin);
                        oldTipoIvaOfCliAlbLinCollectionNewCliAlbLin = em.merge(oldTipoIvaOfCliAlbLinCollectionNewCliAlbLin);
                    }
                }
            }
            for (CliFacLin cliFacLinCollectionNewCliFacLin : cliFacLinCollectionNew) {
                if (!cliFacLinCollectionOld.contains(cliFacLinCollectionNewCliFacLin)) {
                    CfTipoImpuesto oldTipoIvaOfCliFacLinCollectionNewCliFacLin = cliFacLinCollectionNewCliFacLin.getTipoIva();
                    cliFacLinCollectionNewCliFacLin.setTipoIva(cfTipoImpuesto);
                    cliFacLinCollectionNewCliFacLin = em.merge(cliFacLinCollectionNewCliFacLin);
                    if (oldTipoIvaOfCliFacLinCollectionNewCliFacLin != null && !oldTipoIvaOfCliFacLinCollectionNewCliFacLin.equals(cfTipoImpuesto)) {
                        oldTipoIvaOfCliFacLinCollectionNewCliFacLin.getCliFacLinCollection().remove(cliFacLinCollectionNewCliFacLin);
                        oldTipoIvaOfCliFacLinCollectionNewCliFacLin = em.merge(oldTipoIvaOfCliFacLinCollectionNewCliFacLin);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfTipoImpuesto.getId();
                if (findCfTipoImpuesto(id) == null) {
                    throw new NonexistentEntityException("The cfTipoImpuesto with id " + id + " no longer exists.");
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
            CfTipoImpuesto cfTipoImpuesto;
            try {
                cfTipoImpuesto = em.getReference(CfTipoImpuesto.class, id);
                cfTipoImpuesto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfTipoImpuesto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProvFacLin> provFacLinCollectionOrphanCheck = cfTipoImpuesto.getProvFacLinCollection();
            for (ProvFacLin provFacLinCollectionOrphanCheckProvFacLin : provFacLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoImpuesto (" + cfTipoImpuesto + ") cannot be destroyed since the ProvFacLin " + provFacLinCollectionOrphanCheckProvFacLin + " in its provFacLinCollection field has a non-nullable tipoIva field.");
            }
            Collection<ProvPedidosLin> provPedidosLinCollectionOrphanCheck = cfTipoImpuesto.getProvPedidosLinCollection();
            for (ProvPedidosLin provPedidosLinCollectionOrphanCheckProvPedidosLin : provPedidosLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoImpuesto (" + cfTipoImpuesto + ") cannot be destroyed since the ProvPedidosLin " + provPedidosLinCollectionOrphanCheckProvPedidosLin + " in its provPedidosLinCollection field has a non-nullable tipoIva field.");
            }
            Collection<ProvAlbLin> provAlbLinCollectionOrphanCheck = cfTipoImpuesto.getProvAlbLinCollection();
            for (ProvAlbLin provAlbLinCollectionOrphanCheckProvAlbLin : provAlbLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoImpuesto (" + cfTipoImpuesto + ") cannot be destroyed since the ProvAlbLin " + provAlbLinCollectionOrphanCheckProvAlbLin + " in its provAlbLinCollection field has a non-nullable tipoIva field.");
            }
            Collection<CliPedidosLin> cliPedidosLinCollectionOrphanCheck = cfTipoImpuesto.getCliPedidosLinCollection();
            for (CliPedidosLin cliPedidosLinCollectionOrphanCheckCliPedidosLin : cliPedidosLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoImpuesto (" + cfTipoImpuesto + ") cannot be destroyed since the CliPedidosLin " + cliPedidosLinCollectionOrphanCheckCliPedidosLin + " in its cliPedidosLinCollection field has a non-nullable tipoIva field.");
            }
            Collection<CliAlbLin> cliAlbLinCollectionOrphanCheck = cfTipoImpuesto.getCliAlbLinCollection();
            for (CliAlbLin cliAlbLinCollectionOrphanCheckCliAlbLin : cliAlbLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoImpuesto (" + cfTipoImpuesto + ") cannot be destroyed since the CliAlbLin " + cliAlbLinCollectionOrphanCheckCliAlbLin + " in its cliAlbLinCollection field has a non-nullable tipoIva field.");
            }
            Collection<CliFacLin> cliFacLinCollectionOrphanCheck = cfTipoImpuesto.getCliFacLinCollection();
            for (CliFacLin cliFacLinCollectionOrphanCheckCliFacLin : cliFacLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoImpuesto (" + cfTipoImpuesto + ") cannot be destroyed since the CliFacLin " + cliFacLinCollectionOrphanCheckCliFacLin + " in its cliFacLinCollection field has a non-nullable tipoIva field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cfTipoImpuesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfTipoImpuesto> findCfTipoImpuestoEntities() {
        return findCfTipoImpuestoEntities(true, -1, -1);
    }

    public List<CfTipoImpuesto> findCfTipoImpuestoEntities(int maxResults, int firstResult) {
        return findCfTipoImpuestoEntities(false, maxResults, firstResult);
    }

    private List<CfTipoImpuesto> findCfTipoImpuestoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CfTipoImpuesto.class));
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

    public CfTipoImpuesto findCfTipoImpuesto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfTipoImpuesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfTipoImpuestoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CfTipoImpuesto> rt = cq.from(CfTipoImpuesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
