/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import es.timmps.fac.persistencia.Articulos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.ProvFacLin;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.Stock;
import es.timmps.fac.persistencia.PreciosCompras;
import es.timmps.fac.persistencia.ProvPedidosLin;
import es.timmps.fac.persistencia.ProvAlbLin;
import es.timmps.fac.persistencia.Promociones;
import es.timmps.fac.persistencia.CliPedidosLin;
import es.timmps.fac.persistencia.CliAlbLin;
import es.timmps.fac.persistencia.ArticulosCustom;
import es.timmps.fac.persistencia.PreciosVentas;
import es.timmps.fac.persistencia.CliFacLin;
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
public class ArticulosJpaController implements Serializable {

    public ArticulosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulos articulos) throws PreexistingEntityException, Exception {
        if (articulos.getProvFacLinCollection() == null) {
            articulos.setProvFacLinCollection(new ArrayList<ProvFacLin>());
        }
        if (articulos.getStockCollection() == null) {
            articulos.setStockCollection(new ArrayList<Stock>());
        }
        if (articulos.getPreciosComprasCollection() == null) {
            articulos.setPreciosComprasCollection(new ArrayList<PreciosCompras>());
        }
        if (articulos.getProvPedidosLinCollection() == null) {
            articulos.setProvPedidosLinCollection(new ArrayList<ProvPedidosLin>());
        }
        if (articulos.getProvAlbLinCollection() == null) {
            articulos.setProvAlbLinCollection(new ArrayList<ProvAlbLin>());
        }
        if (articulos.getPromocionesCollection() == null) {
            articulos.setPromocionesCollection(new ArrayList<Promociones>());
        }
        if (articulos.getCliPedidosLinCollection() == null) {
            articulos.setCliPedidosLinCollection(new ArrayList<CliPedidosLin>());
        }
        if (articulos.getCliAlbLinCollection() == null) {
            articulos.setCliAlbLinCollection(new ArrayList<CliAlbLin>());
        }
        if (articulos.getArticulosCustomCollection() == null) {
            articulos.setArticulosCustomCollection(new ArrayList<ArticulosCustom>());
        }
        if (articulos.getPreciosVentasCollection() == null) {
            articulos.setPreciosVentasCollection(new ArrayList<PreciosVentas>());
        }
        if (articulos.getCliFacLinCollection() == null) {
            articulos.setCliFacLinCollection(new ArrayList<CliFacLin>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas codEmp = articulos.getCodEmp();
            if (codEmp != null) {
                codEmp = em.getReference(codEmp.getClass(), codEmp.getCodigo());
                articulos.setCodEmp(codEmp);
            }
            Collection<ProvFacLin> attachedProvFacLinCollection = new ArrayList<ProvFacLin>();
            for (ProvFacLin provFacLinCollectionProvFacLinToAttach : articulos.getProvFacLinCollection()) {
                provFacLinCollectionProvFacLinToAttach = em.getReference(provFacLinCollectionProvFacLinToAttach.getClass(), provFacLinCollectionProvFacLinToAttach.getId());
                attachedProvFacLinCollection.add(provFacLinCollectionProvFacLinToAttach);
            }
            articulos.setProvFacLinCollection(attachedProvFacLinCollection);
            Collection<Stock> attachedStockCollection = new ArrayList<Stock>();
            for (Stock stockCollectionStockToAttach : articulos.getStockCollection()) {
                stockCollectionStockToAttach = em.getReference(stockCollectionStockToAttach.getClass(), stockCollectionStockToAttach.getId());
                attachedStockCollection.add(stockCollectionStockToAttach);
            }
            articulos.setStockCollection(attachedStockCollection);
            Collection<PreciosCompras> attachedPreciosComprasCollection = new ArrayList<PreciosCompras>();
            for (PreciosCompras preciosComprasCollectionPreciosComprasToAttach : articulos.getPreciosComprasCollection()) {
                preciosComprasCollectionPreciosComprasToAttach = em.getReference(preciosComprasCollectionPreciosComprasToAttach.getClass(), preciosComprasCollectionPreciosComprasToAttach.getId());
                attachedPreciosComprasCollection.add(preciosComprasCollectionPreciosComprasToAttach);
            }
            articulos.setPreciosComprasCollection(attachedPreciosComprasCollection);
            Collection<ProvPedidosLin> attachedProvPedidosLinCollection = new ArrayList<ProvPedidosLin>();
            for (ProvPedidosLin provPedidosLinCollectionProvPedidosLinToAttach : articulos.getProvPedidosLinCollection()) {
                provPedidosLinCollectionProvPedidosLinToAttach = em.getReference(provPedidosLinCollectionProvPedidosLinToAttach.getClass(), provPedidosLinCollectionProvPedidosLinToAttach.getId());
                attachedProvPedidosLinCollection.add(provPedidosLinCollectionProvPedidosLinToAttach);
            }
            articulos.setProvPedidosLinCollection(attachedProvPedidosLinCollection);
            Collection<ProvAlbLin> attachedProvAlbLinCollection = new ArrayList<ProvAlbLin>();
            for (ProvAlbLin provAlbLinCollectionProvAlbLinToAttach : articulos.getProvAlbLinCollection()) {
                provAlbLinCollectionProvAlbLinToAttach = em.getReference(provAlbLinCollectionProvAlbLinToAttach.getClass(), provAlbLinCollectionProvAlbLinToAttach.getId());
                attachedProvAlbLinCollection.add(provAlbLinCollectionProvAlbLinToAttach);
            }
            articulos.setProvAlbLinCollection(attachedProvAlbLinCollection);
            Collection<Promociones> attachedPromocionesCollection = new ArrayList<Promociones>();
            for (Promociones promocionesCollectionPromocionesToAttach : articulos.getPromocionesCollection()) {
                promocionesCollectionPromocionesToAttach = em.getReference(promocionesCollectionPromocionesToAttach.getClass(), promocionesCollectionPromocionesToAttach.getId());
                attachedPromocionesCollection.add(promocionesCollectionPromocionesToAttach);
            }
            articulos.setPromocionesCollection(attachedPromocionesCollection);
            Collection<CliPedidosLin> attachedCliPedidosLinCollection = new ArrayList<CliPedidosLin>();
            for (CliPedidosLin cliPedidosLinCollectionCliPedidosLinToAttach : articulos.getCliPedidosLinCollection()) {
                cliPedidosLinCollectionCliPedidosLinToAttach = em.getReference(cliPedidosLinCollectionCliPedidosLinToAttach.getClass(), cliPedidosLinCollectionCliPedidosLinToAttach.getId());
                attachedCliPedidosLinCollection.add(cliPedidosLinCollectionCliPedidosLinToAttach);
            }
            articulos.setCliPedidosLinCollection(attachedCliPedidosLinCollection);
            Collection<CliAlbLin> attachedCliAlbLinCollection = new ArrayList<CliAlbLin>();
            for (CliAlbLin cliAlbLinCollectionCliAlbLinToAttach : articulos.getCliAlbLinCollection()) {
                cliAlbLinCollectionCliAlbLinToAttach = em.getReference(cliAlbLinCollectionCliAlbLinToAttach.getClass(), cliAlbLinCollectionCliAlbLinToAttach.getId());
                attachedCliAlbLinCollection.add(cliAlbLinCollectionCliAlbLinToAttach);
            }
            articulos.setCliAlbLinCollection(attachedCliAlbLinCollection);
            Collection<ArticulosCustom> attachedArticulosCustomCollection = new ArrayList<ArticulosCustom>();
            for (ArticulosCustom articulosCustomCollectionArticulosCustomToAttach : articulos.getArticulosCustomCollection()) {
                articulosCustomCollectionArticulosCustomToAttach = em.getReference(articulosCustomCollectionArticulosCustomToAttach.getClass(), articulosCustomCollectionArticulosCustomToAttach.getArticulosCustomPK());
                attachedArticulosCustomCollection.add(articulosCustomCollectionArticulosCustomToAttach);
            }
            articulos.setArticulosCustomCollection(attachedArticulosCustomCollection);
            Collection<PreciosVentas> attachedPreciosVentasCollection = new ArrayList<PreciosVentas>();
            for (PreciosVentas preciosVentasCollectionPreciosVentasToAttach : articulos.getPreciosVentasCollection()) {
                preciosVentasCollectionPreciosVentasToAttach = em.getReference(preciosVentasCollectionPreciosVentasToAttach.getClass(), preciosVentasCollectionPreciosVentasToAttach.getId());
                attachedPreciosVentasCollection.add(preciosVentasCollectionPreciosVentasToAttach);
            }
            articulos.setPreciosVentasCollection(attachedPreciosVentasCollection);
            Collection<CliFacLin> attachedCliFacLinCollection = new ArrayList<CliFacLin>();
            for (CliFacLin cliFacLinCollectionCliFacLinToAttach : articulos.getCliFacLinCollection()) {
                cliFacLinCollectionCliFacLinToAttach = em.getReference(cliFacLinCollectionCliFacLinToAttach.getClass(), cliFacLinCollectionCliFacLinToAttach.getId());
                attachedCliFacLinCollection.add(cliFacLinCollectionCliFacLinToAttach);
            }
            articulos.setCliFacLinCollection(attachedCliFacLinCollection);
            em.persist(articulos);
            if (codEmp != null) {
                codEmp.getArticulosCollection().add(articulos);
                codEmp = em.merge(codEmp);
            }
            for (ProvFacLin provFacLinCollectionProvFacLin : articulos.getProvFacLinCollection()) {
                Articulos oldCodProdOfProvFacLinCollectionProvFacLin = provFacLinCollectionProvFacLin.getCodProd();
                provFacLinCollectionProvFacLin.setCodProd(articulos);
                provFacLinCollectionProvFacLin = em.merge(provFacLinCollectionProvFacLin);
                if (oldCodProdOfProvFacLinCollectionProvFacLin != null) {
                    oldCodProdOfProvFacLinCollectionProvFacLin.getProvFacLinCollection().remove(provFacLinCollectionProvFacLin);
                    oldCodProdOfProvFacLinCollectionProvFacLin = em.merge(oldCodProdOfProvFacLinCollectionProvFacLin);
                }
            }
            for (Stock stockCollectionStock : articulos.getStockCollection()) {
                Articulos oldCodProdOfStockCollectionStock = stockCollectionStock.getCodProd();
                stockCollectionStock.setCodProd(articulos);
                stockCollectionStock = em.merge(stockCollectionStock);
                if (oldCodProdOfStockCollectionStock != null) {
                    oldCodProdOfStockCollectionStock.getStockCollection().remove(stockCollectionStock);
                    oldCodProdOfStockCollectionStock = em.merge(oldCodProdOfStockCollectionStock);
                }
            }
            for (PreciosCompras preciosComprasCollectionPreciosCompras : articulos.getPreciosComprasCollection()) {
                Articulos oldCodProductoOfPreciosComprasCollectionPreciosCompras = preciosComprasCollectionPreciosCompras.getCodProducto();
                preciosComprasCollectionPreciosCompras.setCodProducto(articulos);
                preciosComprasCollectionPreciosCompras = em.merge(preciosComprasCollectionPreciosCompras);
                if (oldCodProductoOfPreciosComprasCollectionPreciosCompras != null) {
                    oldCodProductoOfPreciosComprasCollectionPreciosCompras.getPreciosComprasCollection().remove(preciosComprasCollectionPreciosCompras);
                    oldCodProductoOfPreciosComprasCollectionPreciosCompras = em.merge(oldCodProductoOfPreciosComprasCollectionPreciosCompras);
                }
            }
            for (ProvPedidosLin provPedidosLinCollectionProvPedidosLin : articulos.getProvPedidosLinCollection()) {
                Articulos oldCodProdOfProvPedidosLinCollectionProvPedidosLin = provPedidosLinCollectionProvPedidosLin.getCodProd();
                provPedidosLinCollectionProvPedidosLin.setCodProd(articulos);
                provPedidosLinCollectionProvPedidosLin = em.merge(provPedidosLinCollectionProvPedidosLin);
                if (oldCodProdOfProvPedidosLinCollectionProvPedidosLin != null) {
                    oldCodProdOfProvPedidosLinCollectionProvPedidosLin.getProvPedidosLinCollection().remove(provPedidosLinCollectionProvPedidosLin);
                    oldCodProdOfProvPedidosLinCollectionProvPedidosLin = em.merge(oldCodProdOfProvPedidosLinCollectionProvPedidosLin);
                }
            }
            for (ProvAlbLin provAlbLinCollectionProvAlbLin : articulos.getProvAlbLinCollection()) {
                Articulos oldCodProdOfProvAlbLinCollectionProvAlbLin = provAlbLinCollectionProvAlbLin.getCodProd();
                provAlbLinCollectionProvAlbLin.setCodProd(articulos);
                provAlbLinCollectionProvAlbLin = em.merge(provAlbLinCollectionProvAlbLin);
                if (oldCodProdOfProvAlbLinCollectionProvAlbLin != null) {
                    oldCodProdOfProvAlbLinCollectionProvAlbLin.getProvAlbLinCollection().remove(provAlbLinCollectionProvAlbLin);
                    oldCodProdOfProvAlbLinCollectionProvAlbLin = em.merge(oldCodProdOfProvAlbLinCollectionProvAlbLin);
                }
            }
            for (Promociones promocionesCollectionPromociones : articulos.getPromocionesCollection()) {
                Articulos oldCodProdOfPromocionesCollectionPromociones = promocionesCollectionPromociones.getCodProd();
                promocionesCollectionPromociones.setCodProd(articulos);
                promocionesCollectionPromociones = em.merge(promocionesCollectionPromociones);
                if (oldCodProdOfPromocionesCollectionPromociones != null) {
                    oldCodProdOfPromocionesCollectionPromociones.getPromocionesCollection().remove(promocionesCollectionPromociones);
                    oldCodProdOfPromocionesCollectionPromociones = em.merge(oldCodProdOfPromocionesCollectionPromociones);
                }
            }
            for (CliPedidosLin cliPedidosLinCollectionCliPedidosLin : articulos.getCliPedidosLinCollection()) {
                Articulos oldCodProdOfCliPedidosLinCollectionCliPedidosLin = cliPedidosLinCollectionCliPedidosLin.getCodProd();
                cliPedidosLinCollectionCliPedidosLin.setCodProd(articulos);
                cliPedidosLinCollectionCliPedidosLin = em.merge(cliPedidosLinCollectionCliPedidosLin);
                if (oldCodProdOfCliPedidosLinCollectionCliPedidosLin != null) {
                    oldCodProdOfCliPedidosLinCollectionCliPedidosLin.getCliPedidosLinCollection().remove(cliPedidosLinCollectionCliPedidosLin);
                    oldCodProdOfCliPedidosLinCollectionCliPedidosLin = em.merge(oldCodProdOfCliPedidosLinCollectionCliPedidosLin);
                }
            }
            for (CliAlbLin cliAlbLinCollectionCliAlbLin : articulos.getCliAlbLinCollection()) {
                Articulos oldCodProdOfCliAlbLinCollectionCliAlbLin = cliAlbLinCollectionCliAlbLin.getCodProd();
                cliAlbLinCollectionCliAlbLin.setCodProd(articulos);
                cliAlbLinCollectionCliAlbLin = em.merge(cliAlbLinCollectionCliAlbLin);
                if (oldCodProdOfCliAlbLinCollectionCliAlbLin != null) {
                    oldCodProdOfCliAlbLinCollectionCliAlbLin.getCliAlbLinCollection().remove(cliAlbLinCollectionCliAlbLin);
                    oldCodProdOfCliAlbLinCollectionCliAlbLin = em.merge(oldCodProdOfCliAlbLinCollectionCliAlbLin);
                }
            }
            for (ArticulosCustom articulosCustomCollectionArticulosCustom : articulos.getArticulosCustomCollection()) {
                Articulos oldArticulosOfArticulosCustomCollectionArticulosCustom = articulosCustomCollectionArticulosCustom.getArticulos();
                articulosCustomCollectionArticulosCustom.setArticulos(articulos);
                articulosCustomCollectionArticulosCustom = em.merge(articulosCustomCollectionArticulosCustom);
                if (oldArticulosOfArticulosCustomCollectionArticulosCustom != null) {
                    oldArticulosOfArticulosCustomCollectionArticulosCustom.getArticulosCustomCollection().remove(articulosCustomCollectionArticulosCustom);
                    oldArticulosOfArticulosCustomCollectionArticulosCustom = em.merge(oldArticulosOfArticulosCustomCollectionArticulosCustom);
                }
            }
            for (PreciosVentas preciosVentasCollectionPreciosVentas : articulos.getPreciosVentasCollection()) {
                Articulos oldCodProductoOfPreciosVentasCollectionPreciosVentas = preciosVentasCollectionPreciosVentas.getCodProducto();
                preciosVentasCollectionPreciosVentas.setCodProducto(articulos);
                preciosVentasCollectionPreciosVentas = em.merge(preciosVentasCollectionPreciosVentas);
                if (oldCodProductoOfPreciosVentasCollectionPreciosVentas != null) {
                    oldCodProductoOfPreciosVentasCollectionPreciosVentas.getPreciosVentasCollection().remove(preciosVentasCollectionPreciosVentas);
                    oldCodProductoOfPreciosVentasCollectionPreciosVentas = em.merge(oldCodProductoOfPreciosVentasCollectionPreciosVentas);
                }
            }
            for (CliFacLin cliFacLinCollectionCliFacLin : articulos.getCliFacLinCollection()) {
                Articulos oldCodProdOfCliFacLinCollectionCliFacLin = cliFacLinCollectionCliFacLin.getCodProd();
                cliFacLinCollectionCliFacLin.setCodProd(articulos);
                cliFacLinCollectionCliFacLin = em.merge(cliFacLinCollectionCliFacLin);
                if (oldCodProdOfCliFacLinCollectionCliFacLin != null) {
                    oldCodProdOfCliFacLinCollectionCliFacLin.getCliFacLinCollection().remove(cliFacLinCollectionCliFacLin);
                    oldCodProdOfCliFacLinCollectionCliFacLin = em.merge(oldCodProdOfCliFacLinCollectionCliFacLin);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArticulos(articulos.getCodigo()) != null) {
                throw new PreexistingEntityException("Articulos " + articulos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articulos articulos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos persistentArticulos = em.find(Articulos.class, articulos.getCodigo());
            Empresas codEmpOld = persistentArticulos.getCodEmp();
            Empresas codEmpNew = articulos.getCodEmp();
            Collection<ProvFacLin> provFacLinCollectionOld = persistentArticulos.getProvFacLinCollection();
            Collection<ProvFacLin> provFacLinCollectionNew = articulos.getProvFacLinCollection();
            Collection<Stock> stockCollectionOld = persistentArticulos.getStockCollection();
            Collection<Stock> stockCollectionNew = articulos.getStockCollection();
            Collection<PreciosCompras> preciosComprasCollectionOld = persistentArticulos.getPreciosComprasCollection();
            Collection<PreciosCompras> preciosComprasCollectionNew = articulos.getPreciosComprasCollection();
            Collection<ProvPedidosLin> provPedidosLinCollectionOld = persistentArticulos.getProvPedidosLinCollection();
            Collection<ProvPedidosLin> provPedidosLinCollectionNew = articulos.getProvPedidosLinCollection();
            Collection<ProvAlbLin> provAlbLinCollectionOld = persistentArticulos.getProvAlbLinCollection();
            Collection<ProvAlbLin> provAlbLinCollectionNew = articulos.getProvAlbLinCollection();
            Collection<Promociones> promocionesCollectionOld = persistentArticulos.getPromocionesCollection();
            Collection<Promociones> promocionesCollectionNew = articulos.getPromocionesCollection();
            Collection<CliPedidosLin> cliPedidosLinCollectionOld = persistentArticulos.getCliPedidosLinCollection();
            Collection<CliPedidosLin> cliPedidosLinCollectionNew = articulos.getCliPedidosLinCollection();
            Collection<CliAlbLin> cliAlbLinCollectionOld = persistentArticulos.getCliAlbLinCollection();
            Collection<CliAlbLin> cliAlbLinCollectionNew = articulos.getCliAlbLinCollection();
            Collection<ArticulosCustom> articulosCustomCollectionOld = persistentArticulos.getArticulosCustomCollection();
            Collection<ArticulosCustom> articulosCustomCollectionNew = articulos.getArticulosCustomCollection();
            Collection<PreciosVentas> preciosVentasCollectionOld = persistentArticulos.getPreciosVentasCollection();
            Collection<PreciosVentas> preciosVentasCollectionNew = articulos.getPreciosVentasCollection();
            Collection<CliFacLin> cliFacLinCollectionOld = persistentArticulos.getCliFacLinCollection();
            Collection<CliFacLin> cliFacLinCollectionNew = articulos.getCliFacLinCollection();
            List<String> illegalOrphanMessages = null;
            for (Stock stockCollectionOldStock : stockCollectionOld) {
                if (!stockCollectionNew.contains(stockCollectionOldStock)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Stock " + stockCollectionOldStock + " since its codProd field is not nullable.");
                }
            }
            for (PreciosCompras preciosComprasCollectionOldPreciosCompras : preciosComprasCollectionOld) {
                if (!preciosComprasCollectionNew.contains(preciosComprasCollectionOldPreciosCompras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreciosCompras " + preciosComprasCollectionOldPreciosCompras + " since its codProducto field is not nullable.");
                }
            }
            for (ProvAlbLin provAlbLinCollectionOldProvAlbLin : provAlbLinCollectionOld) {
                if (!provAlbLinCollectionNew.contains(provAlbLinCollectionOldProvAlbLin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvAlbLin " + provAlbLinCollectionOldProvAlbLin + " since its codProd field is not nullable.");
                }
            }
            for (Promociones promocionesCollectionOldPromociones : promocionesCollectionOld) {
                if (!promocionesCollectionNew.contains(promocionesCollectionOldPromociones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Promociones " + promocionesCollectionOldPromociones + " since its codProd field is not nullable.");
                }
            }
            for (ArticulosCustom articulosCustomCollectionOldArticulosCustom : articulosCustomCollectionOld) {
                if (!articulosCustomCollectionNew.contains(articulosCustomCollectionOldArticulosCustom)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ArticulosCustom " + articulosCustomCollectionOldArticulosCustom + " since its articulos field is not nullable.");
                }
            }
            for (PreciosVentas preciosVentasCollectionOldPreciosVentas : preciosVentasCollectionOld) {
                if (!preciosVentasCollectionNew.contains(preciosVentasCollectionOldPreciosVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreciosVentas " + preciosVentasCollectionOldPreciosVentas + " since its codProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codEmpNew != null) {
                codEmpNew = em.getReference(codEmpNew.getClass(), codEmpNew.getCodigo());
                articulos.setCodEmp(codEmpNew);
            }
            Collection<ProvFacLin> attachedProvFacLinCollectionNew = new ArrayList<ProvFacLin>();
            for (ProvFacLin provFacLinCollectionNewProvFacLinToAttach : provFacLinCollectionNew) {
                provFacLinCollectionNewProvFacLinToAttach = em.getReference(provFacLinCollectionNewProvFacLinToAttach.getClass(), provFacLinCollectionNewProvFacLinToAttach.getId());
                attachedProvFacLinCollectionNew.add(provFacLinCollectionNewProvFacLinToAttach);
            }
            provFacLinCollectionNew = attachedProvFacLinCollectionNew;
            articulos.setProvFacLinCollection(provFacLinCollectionNew);
            Collection<Stock> attachedStockCollectionNew = new ArrayList<Stock>();
            for (Stock stockCollectionNewStockToAttach : stockCollectionNew) {
                stockCollectionNewStockToAttach = em.getReference(stockCollectionNewStockToAttach.getClass(), stockCollectionNewStockToAttach.getId());
                attachedStockCollectionNew.add(stockCollectionNewStockToAttach);
            }
            stockCollectionNew = attachedStockCollectionNew;
            articulos.setStockCollection(stockCollectionNew);
            Collection<PreciosCompras> attachedPreciosComprasCollectionNew = new ArrayList<PreciosCompras>();
            for (PreciosCompras preciosComprasCollectionNewPreciosComprasToAttach : preciosComprasCollectionNew) {
                preciosComprasCollectionNewPreciosComprasToAttach = em.getReference(preciosComprasCollectionNewPreciosComprasToAttach.getClass(), preciosComprasCollectionNewPreciosComprasToAttach.getId());
                attachedPreciosComprasCollectionNew.add(preciosComprasCollectionNewPreciosComprasToAttach);
            }
            preciosComprasCollectionNew = attachedPreciosComprasCollectionNew;
            articulos.setPreciosComprasCollection(preciosComprasCollectionNew);
            Collection<ProvPedidosLin> attachedProvPedidosLinCollectionNew = new ArrayList<ProvPedidosLin>();
            for (ProvPedidosLin provPedidosLinCollectionNewProvPedidosLinToAttach : provPedidosLinCollectionNew) {
                provPedidosLinCollectionNewProvPedidosLinToAttach = em.getReference(provPedidosLinCollectionNewProvPedidosLinToAttach.getClass(), provPedidosLinCollectionNewProvPedidosLinToAttach.getId());
                attachedProvPedidosLinCollectionNew.add(provPedidosLinCollectionNewProvPedidosLinToAttach);
            }
            provPedidosLinCollectionNew = attachedProvPedidosLinCollectionNew;
            articulos.setProvPedidosLinCollection(provPedidosLinCollectionNew);
            Collection<ProvAlbLin> attachedProvAlbLinCollectionNew = new ArrayList<ProvAlbLin>();
            for (ProvAlbLin provAlbLinCollectionNewProvAlbLinToAttach : provAlbLinCollectionNew) {
                provAlbLinCollectionNewProvAlbLinToAttach = em.getReference(provAlbLinCollectionNewProvAlbLinToAttach.getClass(), provAlbLinCollectionNewProvAlbLinToAttach.getId());
                attachedProvAlbLinCollectionNew.add(provAlbLinCollectionNewProvAlbLinToAttach);
            }
            provAlbLinCollectionNew = attachedProvAlbLinCollectionNew;
            articulos.setProvAlbLinCollection(provAlbLinCollectionNew);
            Collection<Promociones> attachedPromocionesCollectionNew = new ArrayList<Promociones>();
            for (Promociones promocionesCollectionNewPromocionesToAttach : promocionesCollectionNew) {
                promocionesCollectionNewPromocionesToAttach = em.getReference(promocionesCollectionNewPromocionesToAttach.getClass(), promocionesCollectionNewPromocionesToAttach.getId());
                attachedPromocionesCollectionNew.add(promocionesCollectionNewPromocionesToAttach);
            }
            promocionesCollectionNew = attachedPromocionesCollectionNew;
            articulos.setPromocionesCollection(promocionesCollectionNew);
            Collection<CliPedidosLin> attachedCliPedidosLinCollectionNew = new ArrayList<CliPedidosLin>();
            for (CliPedidosLin cliPedidosLinCollectionNewCliPedidosLinToAttach : cliPedidosLinCollectionNew) {
                cliPedidosLinCollectionNewCliPedidosLinToAttach = em.getReference(cliPedidosLinCollectionNewCliPedidosLinToAttach.getClass(), cliPedidosLinCollectionNewCliPedidosLinToAttach.getId());
                attachedCliPedidosLinCollectionNew.add(cliPedidosLinCollectionNewCliPedidosLinToAttach);
            }
            cliPedidosLinCollectionNew = attachedCliPedidosLinCollectionNew;
            articulos.setCliPedidosLinCollection(cliPedidosLinCollectionNew);
            Collection<CliAlbLin> attachedCliAlbLinCollectionNew = new ArrayList<CliAlbLin>();
            for (CliAlbLin cliAlbLinCollectionNewCliAlbLinToAttach : cliAlbLinCollectionNew) {
                cliAlbLinCollectionNewCliAlbLinToAttach = em.getReference(cliAlbLinCollectionNewCliAlbLinToAttach.getClass(), cliAlbLinCollectionNewCliAlbLinToAttach.getId());
                attachedCliAlbLinCollectionNew.add(cliAlbLinCollectionNewCliAlbLinToAttach);
            }
            cliAlbLinCollectionNew = attachedCliAlbLinCollectionNew;
            articulos.setCliAlbLinCollection(cliAlbLinCollectionNew);
            Collection<ArticulosCustom> attachedArticulosCustomCollectionNew = new ArrayList<ArticulosCustom>();
            for (ArticulosCustom articulosCustomCollectionNewArticulosCustomToAttach : articulosCustomCollectionNew) {
                articulosCustomCollectionNewArticulosCustomToAttach = em.getReference(articulosCustomCollectionNewArticulosCustomToAttach.getClass(), articulosCustomCollectionNewArticulosCustomToAttach.getArticulosCustomPK());
                attachedArticulosCustomCollectionNew.add(articulosCustomCollectionNewArticulosCustomToAttach);
            }
            articulosCustomCollectionNew = attachedArticulosCustomCollectionNew;
            articulos.setArticulosCustomCollection(articulosCustomCollectionNew);
            Collection<PreciosVentas> attachedPreciosVentasCollectionNew = new ArrayList<PreciosVentas>();
            for (PreciosVentas preciosVentasCollectionNewPreciosVentasToAttach : preciosVentasCollectionNew) {
                preciosVentasCollectionNewPreciosVentasToAttach = em.getReference(preciosVentasCollectionNewPreciosVentasToAttach.getClass(), preciosVentasCollectionNewPreciosVentasToAttach.getId());
                attachedPreciosVentasCollectionNew.add(preciosVentasCollectionNewPreciosVentasToAttach);
            }
            preciosVentasCollectionNew = attachedPreciosVentasCollectionNew;
            articulos.setPreciosVentasCollection(preciosVentasCollectionNew);
            Collection<CliFacLin> attachedCliFacLinCollectionNew = new ArrayList<CliFacLin>();
            for (CliFacLin cliFacLinCollectionNewCliFacLinToAttach : cliFacLinCollectionNew) {
                cliFacLinCollectionNewCliFacLinToAttach = em.getReference(cliFacLinCollectionNewCliFacLinToAttach.getClass(), cliFacLinCollectionNewCliFacLinToAttach.getId());
                attachedCliFacLinCollectionNew.add(cliFacLinCollectionNewCliFacLinToAttach);
            }
            cliFacLinCollectionNew = attachedCliFacLinCollectionNew;
            articulos.setCliFacLinCollection(cliFacLinCollectionNew);
            articulos = em.merge(articulos);
            if (codEmpOld != null && !codEmpOld.equals(codEmpNew)) {
                codEmpOld.getArticulosCollection().remove(articulos);
                codEmpOld = em.merge(codEmpOld);
            }
            if (codEmpNew != null && !codEmpNew.equals(codEmpOld)) {
                codEmpNew.getArticulosCollection().add(articulos);
                codEmpNew = em.merge(codEmpNew);
            }
            for (ProvFacLin provFacLinCollectionOldProvFacLin : provFacLinCollectionOld) {
                if (!provFacLinCollectionNew.contains(provFacLinCollectionOldProvFacLin)) {
                    provFacLinCollectionOldProvFacLin.setCodProd(null);
                    provFacLinCollectionOldProvFacLin = em.merge(provFacLinCollectionOldProvFacLin);
                }
            }
            for (ProvFacLin provFacLinCollectionNewProvFacLin : provFacLinCollectionNew) {
                if (!provFacLinCollectionOld.contains(provFacLinCollectionNewProvFacLin)) {
                    Articulos oldCodProdOfProvFacLinCollectionNewProvFacLin = provFacLinCollectionNewProvFacLin.getCodProd();
                    provFacLinCollectionNewProvFacLin.setCodProd(articulos);
                    provFacLinCollectionNewProvFacLin = em.merge(provFacLinCollectionNewProvFacLin);
                    if (oldCodProdOfProvFacLinCollectionNewProvFacLin != null && !oldCodProdOfProvFacLinCollectionNewProvFacLin.equals(articulos)) {
                        oldCodProdOfProvFacLinCollectionNewProvFacLin.getProvFacLinCollection().remove(provFacLinCollectionNewProvFacLin);
                        oldCodProdOfProvFacLinCollectionNewProvFacLin = em.merge(oldCodProdOfProvFacLinCollectionNewProvFacLin);
                    }
                }
            }
            for (Stock stockCollectionNewStock : stockCollectionNew) {
                if (!stockCollectionOld.contains(stockCollectionNewStock)) {
                    Articulos oldCodProdOfStockCollectionNewStock = stockCollectionNewStock.getCodProd();
                    stockCollectionNewStock.setCodProd(articulos);
                    stockCollectionNewStock = em.merge(stockCollectionNewStock);
                    if (oldCodProdOfStockCollectionNewStock != null && !oldCodProdOfStockCollectionNewStock.equals(articulos)) {
                        oldCodProdOfStockCollectionNewStock.getStockCollection().remove(stockCollectionNewStock);
                        oldCodProdOfStockCollectionNewStock = em.merge(oldCodProdOfStockCollectionNewStock);
                    }
                }
            }
            for (PreciosCompras preciosComprasCollectionNewPreciosCompras : preciosComprasCollectionNew) {
                if (!preciosComprasCollectionOld.contains(preciosComprasCollectionNewPreciosCompras)) {
                    Articulos oldCodProductoOfPreciosComprasCollectionNewPreciosCompras = preciosComprasCollectionNewPreciosCompras.getCodProducto();
                    preciosComprasCollectionNewPreciosCompras.setCodProducto(articulos);
                    preciosComprasCollectionNewPreciosCompras = em.merge(preciosComprasCollectionNewPreciosCompras);
                    if (oldCodProductoOfPreciosComprasCollectionNewPreciosCompras != null && !oldCodProductoOfPreciosComprasCollectionNewPreciosCompras.equals(articulos)) {
                        oldCodProductoOfPreciosComprasCollectionNewPreciosCompras.getPreciosComprasCollection().remove(preciosComprasCollectionNewPreciosCompras);
                        oldCodProductoOfPreciosComprasCollectionNewPreciosCompras = em.merge(oldCodProductoOfPreciosComprasCollectionNewPreciosCompras);
                    }
                }
            }
            for (ProvPedidosLin provPedidosLinCollectionOldProvPedidosLin : provPedidosLinCollectionOld) {
                if (!provPedidosLinCollectionNew.contains(provPedidosLinCollectionOldProvPedidosLin)) {
                    provPedidosLinCollectionOldProvPedidosLin.setCodProd(null);
                    provPedidosLinCollectionOldProvPedidosLin = em.merge(provPedidosLinCollectionOldProvPedidosLin);
                }
            }
            for (ProvPedidosLin provPedidosLinCollectionNewProvPedidosLin : provPedidosLinCollectionNew) {
                if (!provPedidosLinCollectionOld.contains(provPedidosLinCollectionNewProvPedidosLin)) {
                    Articulos oldCodProdOfProvPedidosLinCollectionNewProvPedidosLin = provPedidosLinCollectionNewProvPedidosLin.getCodProd();
                    provPedidosLinCollectionNewProvPedidosLin.setCodProd(articulos);
                    provPedidosLinCollectionNewProvPedidosLin = em.merge(provPedidosLinCollectionNewProvPedidosLin);
                    if (oldCodProdOfProvPedidosLinCollectionNewProvPedidosLin != null && !oldCodProdOfProvPedidosLinCollectionNewProvPedidosLin.equals(articulos)) {
                        oldCodProdOfProvPedidosLinCollectionNewProvPedidosLin.getProvPedidosLinCollection().remove(provPedidosLinCollectionNewProvPedidosLin);
                        oldCodProdOfProvPedidosLinCollectionNewProvPedidosLin = em.merge(oldCodProdOfProvPedidosLinCollectionNewProvPedidosLin);
                    }
                }
            }
            for (ProvAlbLin provAlbLinCollectionNewProvAlbLin : provAlbLinCollectionNew) {
                if (!provAlbLinCollectionOld.contains(provAlbLinCollectionNewProvAlbLin)) {
                    Articulos oldCodProdOfProvAlbLinCollectionNewProvAlbLin = provAlbLinCollectionNewProvAlbLin.getCodProd();
                    provAlbLinCollectionNewProvAlbLin.setCodProd(articulos);
                    provAlbLinCollectionNewProvAlbLin = em.merge(provAlbLinCollectionNewProvAlbLin);
                    if (oldCodProdOfProvAlbLinCollectionNewProvAlbLin != null && !oldCodProdOfProvAlbLinCollectionNewProvAlbLin.equals(articulos)) {
                        oldCodProdOfProvAlbLinCollectionNewProvAlbLin.getProvAlbLinCollection().remove(provAlbLinCollectionNewProvAlbLin);
                        oldCodProdOfProvAlbLinCollectionNewProvAlbLin = em.merge(oldCodProdOfProvAlbLinCollectionNewProvAlbLin);
                    }
                }
            }
            for (Promociones promocionesCollectionNewPromociones : promocionesCollectionNew) {
                if (!promocionesCollectionOld.contains(promocionesCollectionNewPromociones)) {
                    Articulos oldCodProdOfPromocionesCollectionNewPromociones = promocionesCollectionNewPromociones.getCodProd();
                    promocionesCollectionNewPromociones.setCodProd(articulos);
                    promocionesCollectionNewPromociones = em.merge(promocionesCollectionNewPromociones);
                    if (oldCodProdOfPromocionesCollectionNewPromociones != null && !oldCodProdOfPromocionesCollectionNewPromociones.equals(articulos)) {
                        oldCodProdOfPromocionesCollectionNewPromociones.getPromocionesCollection().remove(promocionesCollectionNewPromociones);
                        oldCodProdOfPromocionesCollectionNewPromociones = em.merge(oldCodProdOfPromocionesCollectionNewPromociones);
                    }
                }
            }
            for (CliPedidosLin cliPedidosLinCollectionOldCliPedidosLin : cliPedidosLinCollectionOld) {
                if (!cliPedidosLinCollectionNew.contains(cliPedidosLinCollectionOldCliPedidosLin)) {
                    cliPedidosLinCollectionOldCliPedidosLin.setCodProd(null);
                    cliPedidosLinCollectionOldCliPedidosLin = em.merge(cliPedidosLinCollectionOldCliPedidosLin);
                }
            }
            for (CliPedidosLin cliPedidosLinCollectionNewCliPedidosLin : cliPedidosLinCollectionNew) {
                if (!cliPedidosLinCollectionOld.contains(cliPedidosLinCollectionNewCliPedidosLin)) {
                    Articulos oldCodProdOfCliPedidosLinCollectionNewCliPedidosLin = cliPedidosLinCollectionNewCliPedidosLin.getCodProd();
                    cliPedidosLinCollectionNewCliPedidosLin.setCodProd(articulos);
                    cliPedidosLinCollectionNewCliPedidosLin = em.merge(cliPedidosLinCollectionNewCliPedidosLin);
                    if (oldCodProdOfCliPedidosLinCollectionNewCliPedidosLin != null && !oldCodProdOfCliPedidosLinCollectionNewCliPedidosLin.equals(articulos)) {
                        oldCodProdOfCliPedidosLinCollectionNewCliPedidosLin.getCliPedidosLinCollection().remove(cliPedidosLinCollectionNewCliPedidosLin);
                        oldCodProdOfCliPedidosLinCollectionNewCliPedidosLin = em.merge(oldCodProdOfCliPedidosLinCollectionNewCliPedidosLin);
                    }
                }
            }
            for (CliAlbLin cliAlbLinCollectionOldCliAlbLin : cliAlbLinCollectionOld) {
                if (!cliAlbLinCollectionNew.contains(cliAlbLinCollectionOldCliAlbLin)) {
                    cliAlbLinCollectionOldCliAlbLin.setCodProd(null);
                    cliAlbLinCollectionOldCliAlbLin = em.merge(cliAlbLinCollectionOldCliAlbLin);
                }
            }
            for (CliAlbLin cliAlbLinCollectionNewCliAlbLin : cliAlbLinCollectionNew) {
                if (!cliAlbLinCollectionOld.contains(cliAlbLinCollectionNewCliAlbLin)) {
                    Articulos oldCodProdOfCliAlbLinCollectionNewCliAlbLin = cliAlbLinCollectionNewCliAlbLin.getCodProd();
                    cliAlbLinCollectionNewCliAlbLin.setCodProd(articulos);
                    cliAlbLinCollectionNewCliAlbLin = em.merge(cliAlbLinCollectionNewCliAlbLin);
                    if (oldCodProdOfCliAlbLinCollectionNewCliAlbLin != null && !oldCodProdOfCliAlbLinCollectionNewCliAlbLin.equals(articulos)) {
                        oldCodProdOfCliAlbLinCollectionNewCliAlbLin.getCliAlbLinCollection().remove(cliAlbLinCollectionNewCliAlbLin);
                        oldCodProdOfCliAlbLinCollectionNewCliAlbLin = em.merge(oldCodProdOfCliAlbLinCollectionNewCliAlbLin);
                    }
                }
            }
            for (ArticulosCustom articulosCustomCollectionNewArticulosCustom : articulosCustomCollectionNew) {
                if (!articulosCustomCollectionOld.contains(articulosCustomCollectionNewArticulosCustom)) {
                    Articulos oldArticulosOfArticulosCustomCollectionNewArticulosCustom = articulosCustomCollectionNewArticulosCustom.getArticulos();
                    articulosCustomCollectionNewArticulosCustom.setArticulos(articulos);
                    articulosCustomCollectionNewArticulosCustom = em.merge(articulosCustomCollectionNewArticulosCustom);
                    if (oldArticulosOfArticulosCustomCollectionNewArticulosCustom != null && !oldArticulosOfArticulosCustomCollectionNewArticulosCustom.equals(articulos)) {
                        oldArticulosOfArticulosCustomCollectionNewArticulosCustom.getArticulosCustomCollection().remove(articulosCustomCollectionNewArticulosCustom);
                        oldArticulosOfArticulosCustomCollectionNewArticulosCustom = em.merge(oldArticulosOfArticulosCustomCollectionNewArticulosCustom);
                    }
                }
            }
            for (PreciosVentas preciosVentasCollectionNewPreciosVentas : preciosVentasCollectionNew) {
                if (!preciosVentasCollectionOld.contains(preciosVentasCollectionNewPreciosVentas)) {
                    Articulos oldCodProductoOfPreciosVentasCollectionNewPreciosVentas = preciosVentasCollectionNewPreciosVentas.getCodProducto();
                    preciosVentasCollectionNewPreciosVentas.setCodProducto(articulos);
                    preciosVentasCollectionNewPreciosVentas = em.merge(preciosVentasCollectionNewPreciosVentas);
                    if (oldCodProductoOfPreciosVentasCollectionNewPreciosVentas != null && !oldCodProductoOfPreciosVentasCollectionNewPreciosVentas.equals(articulos)) {
                        oldCodProductoOfPreciosVentasCollectionNewPreciosVentas.getPreciosVentasCollection().remove(preciosVentasCollectionNewPreciosVentas);
                        oldCodProductoOfPreciosVentasCollectionNewPreciosVentas = em.merge(oldCodProductoOfPreciosVentasCollectionNewPreciosVentas);
                    }
                }
            }
            for (CliFacLin cliFacLinCollectionOldCliFacLin : cliFacLinCollectionOld) {
                if (!cliFacLinCollectionNew.contains(cliFacLinCollectionOldCliFacLin)) {
                    cliFacLinCollectionOldCliFacLin.setCodProd(null);
                    cliFacLinCollectionOldCliFacLin = em.merge(cliFacLinCollectionOldCliFacLin);
                }
            }
            for (CliFacLin cliFacLinCollectionNewCliFacLin : cliFacLinCollectionNew) {
                if (!cliFacLinCollectionOld.contains(cliFacLinCollectionNewCliFacLin)) {
                    Articulos oldCodProdOfCliFacLinCollectionNewCliFacLin = cliFacLinCollectionNewCliFacLin.getCodProd();
                    cliFacLinCollectionNewCliFacLin.setCodProd(articulos);
                    cliFacLinCollectionNewCliFacLin = em.merge(cliFacLinCollectionNewCliFacLin);
                    if (oldCodProdOfCliFacLinCollectionNewCliFacLin != null && !oldCodProdOfCliFacLinCollectionNewCliFacLin.equals(articulos)) {
                        oldCodProdOfCliFacLinCollectionNewCliFacLin.getCliFacLinCollection().remove(cliFacLinCollectionNewCliFacLin);
                        oldCodProdOfCliFacLinCollectionNewCliFacLin = em.merge(oldCodProdOfCliFacLinCollectionNewCliFacLin);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = articulos.getCodigo();
                if (findArticulos(id) == null) {
                    throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.");
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
            Articulos articulos;
            try {
                articulos = em.getReference(Articulos.class, id);
                articulos.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Stock> stockCollectionOrphanCheck = articulos.getStockCollection();
            for (Stock stockCollectionOrphanCheckStock : stockCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the Stock " + stockCollectionOrphanCheckStock + " in its stockCollection field has a non-nullable codProd field.");
            }
            Collection<PreciosCompras> preciosComprasCollectionOrphanCheck = articulos.getPreciosComprasCollection();
            for (PreciosCompras preciosComprasCollectionOrphanCheckPreciosCompras : preciosComprasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the PreciosCompras " + preciosComprasCollectionOrphanCheckPreciosCompras + " in its preciosComprasCollection field has a non-nullable codProducto field.");
            }
            Collection<ProvAlbLin> provAlbLinCollectionOrphanCheck = articulos.getProvAlbLinCollection();
            for (ProvAlbLin provAlbLinCollectionOrphanCheckProvAlbLin : provAlbLinCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the ProvAlbLin " + provAlbLinCollectionOrphanCheckProvAlbLin + " in its provAlbLinCollection field has a non-nullable codProd field.");
            }
            Collection<Promociones> promocionesCollectionOrphanCheck = articulos.getPromocionesCollection();
            for (Promociones promocionesCollectionOrphanCheckPromociones : promocionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the Promociones " + promocionesCollectionOrphanCheckPromociones + " in its promocionesCollection field has a non-nullable codProd field.");
            }
            Collection<ArticulosCustom> articulosCustomCollectionOrphanCheck = articulos.getArticulosCustomCollection();
            for (ArticulosCustom articulosCustomCollectionOrphanCheckArticulosCustom : articulosCustomCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the ArticulosCustom " + articulosCustomCollectionOrphanCheckArticulosCustom + " in its articulosCustomCollection field has a non-nullable articulos field.");
            }
            Collection<PreciosVentas> preciosVentasCollectionOrphanCheck = articulos.getPreciosVentasCollection();
            for (PreciosVentas preciosVentasCollectionOrphanCheckPreciosVentas : preciosVentasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the PreciosVentas " + preciosVentasCollectionOrphanCheckPreciosVentas + " in its preciosVentasCollection field has a non-nullable codProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresas codEmp = articulos.getCodEmp();
            if (codEmp != null) {
                codEmp.getArticulosCollection().remove(articulos);
                codEmp = em.merge(codEmp);
            }
            Collection<ProvFacLin> provFacLinCollection = articulos.getProvFacLinCollection();
            for (ProvFacLin provFacLinCollectionProvFacLin : provFacLinCollection) {
                provFacLinCollectionProvFacLin.setCodProd(null);
                provFacLinCollectionProvFacLin = em.merge(provFacLinCollectionProvFacLin);
            }
            Collection<ProvPedidosLin> provPedidosLinCollection = articulos.getProvPedidosLinCollection();
            for (ProvPedidosLin provPedidosLinCollectionProvPedidosLin : provPedidosLinCollection) {
                provPedidosLinCollectionProvPedidosLin.setCodProd(null);
                provPedidosLinCollectionProvPedidosLin = em.merge(provPedidosLinCollectionProvPedidosLin);
            }
            Collection<CliPedidosLin> cliPedidosLinCollection = articulos.getCliPedidosLinCollection();
            for (CliPedidosLin cliPedidosLinCollectionCliPedidosLin : cliPedidosLinCollection) {
                cliPedidosLinCollectionCliPedidosLin.setCodProd(null);
                cliPedidosLinCollectionCliPedidosLin = em.merge(cliPedidosLinCollectionCliPedidosLin);
            }
            Collection<CliAlbLin> cliAlbLinCollection = articulos.getCliAlbLinCollection();
            for (CliAlbLin cliAlbLinCollectionCliAlbLin : cliAlbLinCollection) {
                cliAlbLinCollectionCliAlbLin.setCodProd(null);
                cliAlbLinCollectionCliAlbLin = em.merge(cliAlbLinCollectionCliAlbLin);
            }
            Collection<CliFacLin> cliFacLinCollection = articulos.getCliFacLinCollection();
            for (CliFacLin cliFacLinCollectionCliFacLin : cliFacLinCollection) {
                cliFacLinCollectionCliFacLin.setCodProd(null);
                cliFacLinCollectionCliFacLin = em.merge(cliFacLinCollectionCliFacLin);
            }
            em.remove(articulos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulos> findArticulosEntities() {
        return findArticulosEntities(true, -1, -1);
    }

    public List<Articulos> findArticulosEntities(int maxResults, int firstResult) {
        return findArticulosEntities(false, maxResults, firstResult);
    }

    private List<Articulos> findArticulosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Articulos as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Articulos findArticulos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulos.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticulosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Articulos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
