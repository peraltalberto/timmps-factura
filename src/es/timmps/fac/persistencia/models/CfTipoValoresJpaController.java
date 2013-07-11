/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.CfProdCustomEmp;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.CfProvPedCustomEmp;
import es.timmps.fac.persistencia.CfGlobal;
import es.timmps.fac.persistencia.CfProvAlbCustomEmp;
import es.timmps.fac.persistencia.CfProvFacCustomEmp;
import es.timmps.fac.persistencia.CfTipoValores;
import es.timmps.fac.persistencia.models.exceptions.IllegalOrphanException;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aperalta
 */
public class CfTipoValoresJpaController implements Serializable {

    public CfTipoValoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CfTipoValores cfTipoValores) {
        if (cfTipoValores.getCfProdCustomEmpCollection() == null) {
            cfTipoValores.setCfProdCustomEmpCollection(new ArrayList<CfProdCustomEmp>());
        }
        if (cfTipoValores.getCfProvPedCustomEmpCollection() == null) {
            cfTipoValores.setCfProvPedCustomEmpCollection(new ArrayList<CfProvPedCustomEmp>());
        }
        if (cfTipoValores.getCfGlobalCollection() == null) {
            cfTipoValores.setCfGlobalCollection(new ArrayList<CfGlobal>());
        }
        if (cfTipoValores.getCfProvAlbCustomEmpCollection() == null) {
            cfTipoValores.setCfProvAlbCustomEmpCollection(new ArrayList<CfProvAlbCustomEmp>());
        }
        if (cfTipoValores.getCfProvFacCustomEmpCollection() == null) {
            cfTipoValores.setCfProvFacCustomEmpCollection(new ArrayList<CfProvFacCustomEmp>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<CfProdCustomEmp> attachedCfProdCustomEmpCollection = new ArrayList<CfProdCustomEmp>();
            for (CfProdCustomEmp cfProdCustomEmpCollectionCfProdCustomEmpToAttach : cfTipoValores.getCfProdCustomEmpCollection()) {
                cfProdCustomEmpCollectionCfProdCustomEmpToAttach = em.getReference(cfProdCustomEmpCollectionCfProdCustomEmpToAttach.getClass(), cfProdCustomEmpCollectionCfProdCustomEmpToAttach.getId());
                attachedCfProdCustomEmpCollection.add(cfProdCustomEmpCollectionCfProdCustomEmpToAttach);
            }
            cfTipoValores.setCfProdCustomEmpCollection(attachedCfProdCustomEmpCollection);
            Collection<CfProvPedCustomEmp> attachedCfProvPedCustomEmpCollection = new ArrayList<CfProvPedCustomEmp>();
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach : cfTipoValores.getCfProvPedCustomEmpCollection()) {
                cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach = em.getReference(cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach.getClass(), cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach.getId());
                attachedCfProvPedCustomEmpCollection.add(cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach);
            }
            cfTipoValores.setCfProvPedCustomEmpCollection(attachedCfProvPedCustomEmpCollection);
            Collection<CfGlobal> attachedCfGlobalCollection = new ArrayList<CfGlobal>();
            for (CfGlobal cfGlobalCollectionCfGlobalToAttach : cfTipoValores.getCfGlobalCollection()) {
                cfGlobalCollectionCfGlobalToAttach = em.getReference(cfGlobalCollectionCfGlobalToAttach.getClass(), cfGlobalCollectionCfGlobalToAttach.getCodigo());
                attachedCfGlobalCollection.add(cfGlobalCollectionCfGlobalToAttach);
            }
            cfTipoValores.setCfGlobalCollection(attachedCfGlobalCollection);
            Collection<CfProvAlbCustomEmp> attachedCfProvAlbCustomEmpCollection = new ArrayList<CfProvAlbCustomEmp>();
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach : cfTipoValores.getCfProvAlbCustomEmpCollection()) {
                cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach = em.getReference(cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach.getClass(), cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach.getId());
                attachedCfProvAlbCustomEmpCollection.add(cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach);
            }
            cfTipoValores.setCfProvAlbCustomEmpCollection(attachedCfProvAlbCustomEmpCollection);
            Collection<CfProvFacCustomEmp> attachedCfProvFacCustomEmpCollection = new ArrayList<CfProvFacCustomEmp>();
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach : cfTipoValores.getCfProvFacCustomEmpCollection()) {
                cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach = em.getReference(cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach.getClass(), cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach.getId());
                attachedCfProvFacCustomEmpCollection.add(cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach);
            }
            cfTipoValores.setCfProvFacCustomEmpCollection(attachedCfProvFacCustomEmpCollection);
            em.persist(cfTipoValores);
            for (CfProdCustomEmp cfProdCustomEmpCollectionCfProdCustomEmp : cfTipoValores.getCfProdCustomEmpCollection()) {
                CfTipoValores oldTipoValorOfCfProdCustomEmpCollectionCfProdCustomEmp = cfProdCustomEmpCollectionCfProdCustomEmp.getTipoValor();
                cfProdCustomEmpCollectionCfProdCustomEmp.setTipoValor(cfTipoValores);
                cfProdCustomEmpCollectionCfProdCustomEmp = em.merge(cfProdCustomEmpCollectionCfProdCustomEmp);
                if (oldTipoValorOfCfProdCustomEmpCollectionCfProdCustomEmp != null) {
                    oldTipoValorOfCfProdCustomEmpCollectionCfProdCustomEmp.getCfProdCustomEmpCollection().remove(cfProdCustomEmpCollectionCfProdCustomEmp);
                    oldTipoValorOfCfProdCustomEmpCollectionCfProdCustomEmp = em.merge(oldTipoValorOfCfProdCustomEmpCollectionCfProdCustomEmp);
                }
            }
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionCfProvPedCustomEmp : cfTipoValores.getCfProvPedCustomEmpCollection()) {
                CfTipoValores oldTipoValorOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp = cfProvPedCustomEmpCollectionCfProvPedCustomEmp.getTipoValor();
                cfProvPedCustomEmpCollectionCfProvPedCustomEmp.setTipoValor(cfTipoValores);
                cfProvPedCustomEmpCollectionCfProvPedCustomEmp = em.merge(cfProvPedCustomEmpCollectionCfProvPedCustomEmp);
                if (oldTipoValorOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp != null) {
                    oldTipoValorOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp.getCfProvPedCustomEmpCollection().remove(cfProvPedCustomEmpCollectionCfProvPedCustomEmp);
                    oldTipoValorOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp = em.merge(oldTipoValorOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp);
                }
            }
            for (CfGlobal cfGlobalCollectionCfGlobal : cfTipoValores.getCfGlobalCollection()) {
                CfTipoValores oldTipoDatoOfCfGlobalCollectionCfGlobal = cfGlobalCollectionCfGlobal.getTipoDato();
                cfGlobalCollectionCfGlobal.setTipoDato(cfTipoValores);
                cfGlobalCollectionCfGlobal = em.merge(cfGlobalCollectionCfGlobal);
                if (oldTipoDatoOfCfGlobalCollectionCfGlobal != null) {
                    oldTipoDatoOfCfGlobalCollectionCfGlobal.getCfGlobalCollection().remove(cfGlobalCollectionCfGlobal);
                    oldTipoDatoOfCfGlobalCollectionCfGlobal = em.merge(oldTipoDatoOfCfGlobalCollectionCfGlobal);
                }
            }
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp : cfTipoValores.getCfProvAlbCustomEmpCollection()) {
                CfTipoValores oldTipoValorOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp = cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp.getTipoValor();
                cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp.setTipoValor(cfTipoValores);
                cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp = em.merge(cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp);
                if (oldTipoValorOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp != null) {
                    oldTipoValorOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp.getCfProvAlbCustomEmpCollection().remove(cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp);
                    oldTipoValorOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp = em.merge(oldTipoValorOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp);
                }
            }
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionCfProvFacCustomEmp : cfTipoValores.getCfProvFacCustomEmpCollection()) {
                CfTipoValores oldTipoValorOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp = cfProvFacCustomEmpCollectionCfProvFacCustomEmp.getTipoValor();
                cfProvFacCustomEmpCollectionCfProvFacCustomEmp.setTipoValor(cfTipoValores);
                cfProvFacCustomEmpCollectionCfProvFacCustomEmp = em.merge(cfProvFacCustomEmpCollectionCfProvFacCustomEmp);
                if (oldTipoValorOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp != null) {
                    oldTipoValorOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp.getCfProvFacCustomEmpCollection().remove(cfProvFacCustomEmpCollectionCfProvFacCustomEmp);
                    oldTipoValorOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp = em.merge(oldTipoValorOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CfTipoValores cfTipoValores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CfTipoValores persistentCfTipoValores = em.find(CfTipoValores.class, cfTipoValores.getId());
            Collection<CfProdCustomEmp> cfProdCustomEmpCollectionOld = persistentCfTipoValores.getCfProdCustomEmpCollection();
            Collection<CfProdCustomEmp> cfProdCustomEmpCollectionNew = cfTipoValores.getCfProdCustomEmpCollection();
            Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollectionOld = persistentCfTipoValores.getCfProvPedCustomEmpCollection();
            Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollectionNew = cfTipoValores.getCfProvPedCustomEmpCollection();
            Collection<CfGlobal> cfGlobalCollectionOld = persistentCfTipoValores.getCfGlobalCollection();
            Collection<CfGlobal> cfGlobalCollectionNew = cfTipoValores.getCfGlobalCollection();
            Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollectionOld = persistentCfTipoValores.getCfProvAlbCustomEmpCollection();
            Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollectionNew = cfTipoValores.getCfProvAlbCustomEmpCollection();
            Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollectionOld = persistentCfTipoValores.getCfProvFacCustomEmpCollection();
            Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollectionNew = cfTipoValores.getCfProvFacCustomEmpCollection();
            List<String> illegalOrphanMessages = null;
            for (CfProdCustomEmp cfProdCustomEmpCollectionOldCfProdCustomEmp : cfProdCustomEmpCollectionOld) {
                if (!cfProdCustomEmpCollectionNew.contains(cfProdCustomEmpCollectionOldCfProdCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfProdCustomEmp " + cfProdCustomEmpCollectionOldCfProdCustomEmp + " since its tipoValor field is not nullable.");
                }
            }
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionOldCfProvPedCustomEmp : cfProvPedCustomEmpCollectionOld) {
                if (!cfProvPedCustomEmpCollectionNew.contains(cfProvPedCustomEmpCollectionOldCfProvPedCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfProvPedCustomEmp " + cfProvPedCustomEmpCollectionOldCfProvPedCustomEmp + " since its tipoValor field is not nullable.");
                }
            }
            for (CfGlobal cfGlobalCollectionOldCfGlobal : cfGlobalCollectionOld) {
                if (!cfGlobalCollectionNew.contains(cfGlobalCollectionOldCfGlobal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfGlobal " + cfGlobalCollectionOldCfGlobal + " since its tipoDato field is not nullable.");
                }
            }
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionOldCfProvAlbCustomEmp : cfProvAlbCustomEmpCollectionOld) {
                if (!cfProvAlbCustomEmpCollectionNew.contains(cfProvAlbCustomEmpCollectionOldCfProvAlbCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfProvAlbCustomEmp " + cfProvAlbCustomEmpCollectionOldCfProvAlbCustomEmp + " since its tipoValor field is not nullable.");
                }
            }
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionOldCfProvFacCustomEmp : cfProvFacCustomEmpCollectionOld) {
                if (!cfProvFacCustomEmpCollectionNew.contains(cfProvFacCustomEmpCollectionOldCfProvFacCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfProvFacCustomEmp " + cfProvFacCustomEmpCollectionOldCfProvFacCustomEmp + " since its tipoValor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<CfProdCustomEmp> attachedCfProdCustomEmpCollectionNew = new ArrayList<CfProdCustomEmp>();
            for (CfProdCustomEmp cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach : cfProdCustomEmpCollectionNew) {
                cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach = em.getReference(cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach.getClass(), cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach.getId());
                attachedCfProdCustomEmpCollectionNew.add(cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach);
            }
            cfProdCustomEmpCollectionNew = attachedCfProdCustomEmpCollectionNew;
            cfTipoValores.setCfProdCustomEmpCollection(cfProdCustomEmpCollectionNew);
            Collection<CfProvPedCustomEmp> attachedCfProvPedCustomEmpCollectionNew = new ArrayList<CfProvPedCustomEmp>();
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach : cfProvPedCustomEmpCollectionNew) {
                cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach = em.getReference(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach.getClass(), cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach.getId());
                attachedCfProvPedCustomEmpCollectionNew.add(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach);
            }
            cfProvPedCustomEmpCollectionNew = attachedCfProvPedCustomEmpCollectionNew;
            cfTipoValores.setCfProvPedCustomEmpCollection(cfProvPedCustomEmpCollectionNew);
            Collection<CfGlobal> attachedCfGlobalCollectionNew = new ArrayList<CfGlobal>();
            for (CfGlobal cfGlobalCollectionNewCfGlobalToAttach : cfGlobalCollectionNew) {
                cfGlobalCollectionNewCfGlobalToAttach = em.getReference(cfGlobalCollectionNewCfGlobalToAttach.getClass(), cfGlobalCollectionNewCfGlobalToAttach.getCodigo());
                attachedCfGlobalCollectionNew.add(cfGlobalCollectionNewCfGlobalToAttach);
            }
            cfGlobalCollectionNew = attachedCfGlobalCollectionNew;
            cfTipoValores.setCfGlobalCollection(cfGlobalCollectionNew);
            Collection<CfProvAlbCustomEmp> attachedCfProvAlbCustomEmpCollectionNew = new ArrayList<CfProvAlbCustomEmp>();
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach : cfProvAlbCustomEmpCollectionNew) {
                cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach = em.getReference(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach.getClass(), cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach.getId());
                attachedCfProvAlbCustomEmpCollectionNew.add(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach);
            }
            cfProvAlbCustomEmpCollectionNew = attachedCfProvAlbCustomEmpCollectionNew;
            cfTipoValores.setCfProvAlbCustomEmpCollection(cfProvAlbCustomEmpCollectionNew);
            Collection<CfProvFacCustomEmp> attachedCfProvFacCustomEmpCollectionNew = new ArrayList<CfProvFacCustomEmp>();
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach : cfProvFacCustomEmpCollectionNew) {
                cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach = em.getReference(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach.getClass(), cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach.getId());
                attachedCfProvFacCustomEmpCollectionNew.add(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach);
            }
            cfProvFacCustomEmpCollectionNew = attachedCfProvFacCustomEmpCollectionNew;
            cfTipoValores.setCfProvFacCustomEmpCollection(cfProvFacCustomEmpCollectionNew);
            cfTipoValores = em.merge(cfTipoValores);
            for (CfProdCustomEmp cfProdCustomEmpCollectionNewCfProdCustomEmp : cfProdCustomEmpCollectionNew) {
                if (!cfProdCustomEmpCollectionOld.contains(cfProdCustomEmpCollectionNewCfProdCustomEmp)) {
                    CfTipoValores oldTipoValorOfCfProdCustomEmpCollectionNewCfProdCustomEmp = cfProdCustomEmpCollectionNewCfProdCustomEmp.getTipoValor();
                    cfProdCustomEmpCollectionNewCfProdCustomEmp.setTipoValor(cfTipoValores);
                    cfProdCustomEmpCollectionNewCfProdCustomEmp = em.merge(cfProdCustomEmpCollectionNewCfProdCustomEmp);
                    if (oldTipoValorOfCfProdCustomEmpCollectionNewCfProdCustomEmp != null && !oldTipoValorOfCfProdCustomEmpCollectionNewCfProdCustomEmp.equals(cfTipoValores)) {
                        oldTipoValorOfCfProdCustomEmpCollectionNewCfProdCustomEmp.getCfProdCustomEmpCollection().remove(cfProdCustomEmpCollectionNewCfProdCustomEmp);
                        oldTipoValorOfCfProdCustomEmpCollectionNewCfProdCustomEmp = em.merge(oldTipoValorOfCfProdCustomEmpCollectionNewCfProdCustomEmp);
                    }
                }
            }
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp : cfProvPedCustomEmpCollectionNew) {
                if (!cfProvPedCustomEmpCollectionOld.contains(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp)) {
                    CfTipoValores oldTipoValorOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp = cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp.getTipoValor();
                    cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp.setTipoValor(cfTipoValores);
                    cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp = em.merge(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp);
                    if (oldTipoValorOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp != null && !oldTipoValorOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp.equals(cfTipoValores)) {
                        oldTipoValorOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp.getCfProvPedCustomEmpCollection().remove(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp);
                        oldTipoValorOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp = em.merge(oldTipoValorOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp);
                    }
                }
            }
            for (CfGlobal cfGlobalCollectionNewCfGlobal : cfGlobalCollectionNew) {
                if (!cfGlobalCollectionOld.contains(cfGlobalCollectionNewCfGlobal)) {
                    CfTipoValores oldTipoDatoOfCfGlobalCollectionNewCfGlobal = cfGlobalCollectionNewCfGlobal.getTipoDato();
                    cfGlobalCollectionNewCfGlobal.setTipoDato(cfTipoValores);
                    cfGlobalCollectionNewCfGlobal = em.merge(cfGlobalCollectionNewCfGlobal);
                    if (oldTipoDatoOfCfGlobalCollectionNewCfGlobal != null && !oldTipoDatoOfCfGlobalCollectionNewCfGlobal.equals(cfTipoValores)) {
                        oldTipoDatoOfCfGlobalCollectionNewCfGlobal.getCfGlobalCollection().remove(cfGlobalCollectionNewCfGlobal);
                        oldTipoDatoOfCfGlobalCollectionNewCfGlobal = em.merge(oldTipoDatoOfCfGlobalCollectionNewCfGlobal);
                    }
                }
            }
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp : cfProvAlbCustomEmpCollectionNew) {
                if (!cfProvAlbCustomEmpCollectionOld.contains(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp)) {
                    CfTipoValores oldTipoValorOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp = cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp.getTipoValor();
                    cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp.setTipoValor(cfTipoValores);
                    cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp = em.merge(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp);
                    if (oldTipoValorOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp != null && !oldTipoValorOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp.equals(cfTipoValores)) {
                        oldTipoValorOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp.getCfProvAlbCustomEmpCollection().remove(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp);
                        oldTipoValorOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp = em.merge(oldTipoValorOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp);
                    }
                }
            }
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp : cfProvFacCustomEmpCollectionNew) {
                if (!cfProvFacCustomEmpCollectionOld.contains(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp)) {
                    CfTipoValores oldTipoValorOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp = cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp.getTipoValor();
                    cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp.setTipoValor(cfTipoValores);
                    cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp = em.merge(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp);
                    if (oldTipoValorOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp != null && !oldTipoValorOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp.equals(cfTipoValores)) {
                        oldTipoValorOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp.getCfProvFacCustomEmpCollection().remove(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp);
                        oldTipoValorOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp = em.merge(oldTipoValorOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cfTipoValores.getId();
                if (findCfTipoValores(id) == null) {
                    throw new NonexistentEntityException("The cfTipoValores with id " + id + " no longer exists.");
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
            CfTipoValores cfTipoValores;
            try {
                cfTipoValores = em.getReference(CfTipoValores.class, id);
                cfTipoValores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cfTipoValores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CfProdCustomEmp> cfProdCustomEmpCollectionOrphanCheck = cfTipoValores.getCfProdCustomEmpCollection();
            for (CfProdCustomEmp cfProdCustomEmpCollectionOrphanCheckCfProdCustomEmp : cfProdCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoValores (" + cfTipoValores + ") cannot be destroyed since the CfProdCustomEmp " + cfProdCustomEmpCollectionOrphanCheckCfProdCustomEmp + " in its cfProdCustomEmpCollection field has a non-nullable tipoValor field.");
            }
            Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollectionOrphanCheck = cfTipoValores.getCfProvPedCustomEmpCollection();
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionOrphanCheckCfProvPedCustomEmp : cfProvPedCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoValores (" + cfTipoValores + ") cannot be destroyed since the CfProvPedCustomEmp " + cfProvPedCustomEmpCollectionOrphanCheckCfProvPedCustomEmp + " in its cfProvPedCustomEmpCollection field has a non-nullable tipoValor field.");
            }
            Collection<CfGlobal> cfGlobalCollectionOrphanCheck = cfTipoValores.getCfGlobalCollection();
            for (CfGlobal cfGlobalCollectionOrphanCheckCfGlobal : cfGlobalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoValores (" + cfTipoValores + ") cannot be destroyed since the CfGlobal " + cfGlobalCollectionOrphanCheckCfGlobal + " in its cfGlobalCollection field has a non-nullable tipoDato field.");
            }
            Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollectionOrphanCheck = cfTipoValores.getCfProvAlbCustomEmpCollection();
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionOrphanCheckCfProvAlbCustomEmp : cfProvAlbCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoValores (" + cfTipoValores + ") cannot be destroyed since the CfProvAlbCustomEmp " + cfProvAlbCustomEmpCollectionOrphanCheckCfProvAlbCustomEmp + " in its cfProvAlbCustomEmpCollection field has a non-nullable tipoValor field.");
            }
            Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollectionOrphanCheck = cfTipoValores.getCfProvFacCustomEmpCollection();
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionOrphanCheckCfProvFacCustomEmp : cfProvFacCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CfTipoValores (" + cfTipoValores + ") cannot be destroyed since the CfProvFacCustomEmp " + cfProvFacCustomEmpCollectionOrphanCheckCfProvFacCustomEmp + " in its cfProvFacCustomEmpCollection field has a non-nullable tipoValor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cfTipoValores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CfTipoValores> findCfTipoValoresEntities() {
        return findCfTipoValoresEntities(true, -1, -1);
    }

    public List<CfTipoValores> findCfTipoValoresEntities(int maxResults, int firstResult) {
        return findCfTipoValoresEntities(false, maxResults, firstResult);
    }

    private List<CfTipoValores> findCfTipoValoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CfTipoValores as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CfTipoValores findCfTipoValores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CfTipoValores.class, id);
        } finally {
            em.close();
        }
    }

    public int getCfTipoValoresCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CfTipoValores as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
