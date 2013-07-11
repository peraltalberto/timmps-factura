/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import es.timmps.fac.persistencia.Grupos;
import java.util.ArrayList;
import java.util.Collection;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.fac.persistencia.CfProdCustomEmp;
import es.timmps.fac.persistencia.ProvPedidosCab;
import es.timmps.fac.persistencia.CliAlbCab;
import es.timmps.fac.persistencia.Stock;
import es.timmps.fac.persistencia.PreciosCompras;
import es.timmps.fac.persistencia.Contadores;
import es.timmps.fac.persistencia.CfProvPedCustomEmp;
import es.timmps.fac.persistencia.Clientes;
import es.timmps.fac.persistencia.CfCliAlbCustomEmp;
import es.timmps.fac.persistencia.ProvFacCab;
import es.timmps.fac.persistencia.CfCliFacCustomEmp;
import es.timmps.fac.persistencia.ProvAlbCab;
import es.timmps.fac.persistencia.Articulos;
import es.timmps.fac.persistencia.CliFacCab;
import es.timmps.fac.persistencia.Promociones;
import es.timmps.fac.persistencia.CfProvAlbCustomEmp;
import es.timmps.fac.persistencia.Almacenes;
import es.timmps.fac.persistencia.CfDpCustomEmp;
import es.timmps.fac.persistencia.CfCliPedCustomEmp;
import es.timmps.fac.persistencia.CfProvFacCustomEmp;
import es.timmps.fac.persistencia.Proveedores;
import es.timmps.fac.persistencia.PreciosVentas;
import es.timmps.fac.persistencia.CliPedidosCab;
import es.timmps.fac.persistencia.Empresas;
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
public class EmpresasJpaController implements Serializable {

    public EmpresasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresas empresas) throws PreexistingEntityException, Exception {
        if (empresas.getGruposCollection() == null) {
            empresas.setGruposCollection(new ArrayList<Grupos>());
        }
        if (empresas.getUsuariosCollection() == null) {
            empresas.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        if (empresas.getCfProdCustomEmpCollection() == null) {
            empresas.setCfProdCustomEmpCollection(new ArrayList<CfProdCustomEmp>());
        }
        if (empresas.getProvPedidosCabCollection() == null) {
            empresas.setProvPedidosCabCollection(new ArrayList<ProvPedidosCab>());
        }
        if (empresas.getCliAlbCabCollection() == null) {
            empresas.setCliAlbCabCollection(new ArrayList<CliAlbCab>());
        }
        if (empresas.getStockCollection() == null) {
            empresas.setStockCollection(new ArrayList<Stock>());
        }
        if (empresas.getPreciosComprasCollection() == null) {
            empresas.setPreciosComprasCollection(new ArrayList<PreciosCompras>());
        }
        if (empresas.getContadoresCollection() == null) {
            empresas.setContadoresCollection(new ArrayList<Contadores>());
        }
        if (empresas.getCfProvPedCustomEmpCollection() == null) {
            empresas.setCfProvPedCustomEmpCollection(new ArrayList<CfProvPedCustomEmp>());
        }
        if (empresas.getClientesCollection() == null) {
            empresas.setClientesCollection(new ArrayList<Clientes>());
        }
        if (empresas.getCfCliAlbCustomEmpCollection() == null) {
            empresas.setCfCliAlbCustomEmpCollection(new ArrayList<CfCliAlbCustomEmp>());
        }
        if (empresas.getProvFacCabCollection() == null) {
            empresas.setProvFacCabCollection(new ArrayList<ProvFacCab>());
        }
        if (empresas.getCfCliFacCustomEmpCollection() == null) {
            empresas.setCfCliFacCustomEmpCollection(new ArrayList<CfCliFacCustomEmp>());
        }
        if (empresas.getProvAlbCabCollection() == null) {
            empresas.setProvAlbCabCollection(new ArrayList<ProvAlbCab>());
        }
        if (empresas.getArticulosCollection() == null) {
            empresas.setArticulosCollection(new ArrayList<Articulos>());
        }
        if (empresas.getCliFacCabCollection() == null) {
            empresas.setCliFacCabCollection(new ArrayList<CliFacCab>());
        }
        if (empresas.getPromocionesCollection() == null) {
            empresas.setPromocionesCollection(new ArrayList<Promociones>());
        }
        if (empresas.getCfProvAlbCustomEmpCollection() == null) {
            empresas.setCfProvAlbCustomEmpCollection(new ArrayList<CfProvAlbCustomEmp>());
        }
        if (empresas.getAlmacenesCollection() == null) {
            empresas.setAlmacenesCollection(new ArrayList<Almacenes>());
        }
        if (empresas.getCfDpCustomEmpCollection() == null) {
            empresas.setCfDpCustomEmpCollection(new ArrayList<CfDpCustomEmp>());
        }
        if (empresas.getCfCliPedCustomEmpCollection() == null) {
            empresas.setCfCliPedCustomEmpCollection(new ArrayList<CfCliPedCustomEmp>());
        }
        if (empresas.getCfProvFacCustomEmpCollection() == null) {
            empresas.setCfProvFacCustomEmpCollection(new ArrayList<CfProvFacCustomEmp>());
        }
        if (empresas.getProveedoresCollection() == null) {
            empresas.setProveedoresCollection(new ArrayList<Proveedores>());
        }
        if (empresas.getPreciosVentasCollection() == null) {
            empresas.setPreciosVentasCollection(new ArrayList<PreciosVentas>());
        }
        if (empresas.getCliPedidosCabCollection() == null) {
            empresas.setCliPedidosCabCollection(new ArrayList<CliPedidosCab>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Grupos> attachedGruposCollection = new ArrayList<Grupos>();
            for (Grupos gruposCollectionGruposToAttach : empresas.getGruposCollection()) {
                gruposCollectionGruposToAttach = em.getReference(gruposCollectionGruposToAttach.getClass(), gruposCollectionGruposToAttach.getId());
                attachedGruposCollection.add(gruposCollectionGruposToAttach);
            }
            empresas.setGruposCollection(attachedGruposCollection);
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : empresas.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            empresas.setUsuariosCollection(attachedUsuariosCollection);
            Collection<CfProdCustomEmp> attachedCfProdCustomEmpCollection = new ArrayList<CfProdCustomEmp>();
            for (CfProdCustomEmp cfProdCustomEmpCollectionCfProdCustomEmpToAttach : empresas.getCfProdCustomEmpCollection()) {
                cfProdCustomEmpCollectionCfProdCustomEmpToAttach = em.getReference(cfProdCustomEmpCollectionCfProdCustomEmpToAttach.getClass(), cfProdCustomEmpCollectionCfProdCustomEmpToAttach.getId());
                attachedCfProdCustomEmpCollection.add(cfProdCustomEmpCollectionCfProdCustomEmpToAttach);
            }
            empresas.setCfProdCustomEmpCollection(attachedCfProdCustomEmpCollection);
            Collection<ProvPedidosCab> attachedProvPedidosCabCollection = new ArrayList<ProvPedidosCab>();
            for (ProvPedidosCab provPedidosCabCollectionProvPedidosCabToAttach : empresas.getProvPedidosCabCollection()) {
                provPedidosCabCollectionProvPedidosCabToAttach = em.getReference(provPedidosCabCollectionProvPedidosCabToAttach.getClass(), provPedidosCabCollectionProvPedidosCabToAttach.getId());
                attachedProvPedidosCabCollection.add(provPedidosCabCollectionProvPedidosCabToAttach);
            }
            empresas.setProvPedidosCabCollection(attachedProvPedidosCabCollection);
            Collection<CliAlbCab> attachedCliAlbCabCollection = new ArrayList<CliAlbCab>();
            for (CliAlbCab cliAlbCabCollectionCliAlbCabToAttach : empresas.getCliAlbCabCollection()) {
                cliAlbCabCollectionCliAlbCabToAttach = em.getReference(cliAlbCabCollectionCliAlbCabToAttach.getClass(), cliAlbCabCollectionCliAlbCabToAttach.getId());
                attachedCliAlbCabCollection.add(cliAlbCabCollectionCliAlbCabToAttach);
            }
            empresas.setCliAlbCabCollection(attachedCliAlbCabCollection);
            Collection<Stock> attachedStockCollection = new ArrayList<Stock>();
            for (Stock stockCollectionStockToAttach : empresas.getStockCollection()) {
                stockCollectionStockToAttach = em.getReference(stockCollectionStockToAttach.getClass(), stockCollectionStockToAttach.getId());
                attachedStockCollection.add(stockCollectionStockToAttach);
            }
            empresas.setStockCollection(attachedStockCollection);
            Collection<PreciosCompras> attachedPreciosComprasCollection = new ArrayList<PreciosCompras>();
            for (PreciosCompras preciosComprasCollectionPreciosComprasToAttach : empresas.getPreciosComprasCollection()) {
                preciosComprasCollectionPreciosComprasToAttach = em.getReference(preciosComprasCollectionPreciosComprasToAttach.getClass(), preciosComprasCollectionPreciosComprasToAttach.getId());
                attachedPreciosComprasCollection.add(preciosComprasCollectionPreciosComprasToAttach);
            }
            empresas.setPreciosComprasCollection(attachedPreciosComprasCollection);
            Collection<Contadores> attachedContadoresCollection = new ArrayList<Contadores>();
            for (Contadores contadoresCollectionContadoresToAttach : empresas.getContadoresCollection()) {
                contadoresCollectionContadoresToAttach = em.getReference(contadoresCollectionContadoresToAttach.getClass(), contadoresCollectionContadoresToAttach.getId());
                attachedContadoresCollection.add(contadoresCollectionContadoresToAttach);
            }
            empresas.setContadoresCollection(attachedContadoresCollection);
            Collection<CfProvPedCustomEmp> attachedCfProvPedCustomEmpCollection = new ArrayList<CfProvPedCustomEmp>();
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach : empresas.getCfProvPedCustomEmpCollection()) {
                cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach = em.getReference(cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach.getClass(), cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach.getId());
                attachedCfProvPedCustomEmpCollection.add(cfProvPedCustomEmpCollectionCfProvPedCustomEmpToAttach);
            }
            empresas.setCfProvPedCustomEmpCollection(attachedCfProvPedCustomEmpCollection);
            Collection<Clientes> attachedClientesCollection = new ArrayList<Clientes>();
            for (Clientes clientesCollectionClientesToAttach : empresas.getClientesCollection()) {
                clientesCollectionClientesToAttach = em.getReference(clientesCollectionClientesToAttach.getClass(), clientesCollectionClientesToAttach.getCodigo());
                attachedClientesCollection.add(clientesCollectionClientesToAttach);
            }
            empresas.setClientesCollection(attachedClientesCollection);
            Collection<CfCliAlbCustomEmp> attachedCfCliAlbCustomEmpCollection = new ArrayList<CfCliAlbCustomEmp>();
            for (CfCliAlbCustomEmp cfCliAlbCustomEmpCollectionCfCliAlbCustomEmpToAttach : empresas.getCfCliAlbCustomEmpCollection()) {
                cfCliAlbCustomEmpCollectionCfCliAlbCustomEmpToAttach = em.getReference(cfCliAlbCustomEmpCollectionCfCliAlbCustomEmpToAttach.getClass(), cfCliAlbCustomEmpCollectionCfCliAlbCustomEmpToAttach.getId());
                attachedCfCliAlbCustomEmpCollection.add(cfCliAlbCustomEmpCollectionCfCliAlbCustomEmpToAttach);
            }
            empresas.setCfCliAlbCustomEmpCollection(attachedCfCliAlbCustomEmpCollection);
            Collection<ProvFacCab> attachedProvFacCabCollection = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionProvFacCabToAttach : empresas.getProvFacCabCollection()) {
                provFacCabCollectionProvFacCabToAttach = em.getReference(provFacCabCollectionProvFacCabToAttach.getClass(), provFacCabCollectionProvFacCabToAttach.getId());
                attachedProvFacCabCollection.add(provFacCabCollectionProvFacCabToAttach);
            }
            empresas.setProvFacCabCollection(attachedProvFacCabCollection);
            Collection<CfCliFacCustomEmp> attachedCfCliFacCustomEmpCollection = new ArrayList<CfCliFacCustomEmp>();
            for (CfCliFacCustomEmp cfCliFacCustomEmpCollectionCfCliFacCustomEmpToAttach : empresas.getCfCliFacCustomEmpCollection()) {
                cfCliFacCustomEmpCollectionCfCliFacCustomEmpToAttach = em.getReference(cfCliFacCustomEmpCollectionCfCliFacCustomEmpToAttach.getClass(), cfCliFacCustomEmpCollectionCfCliFacCustomEmpToAttach.getId());
                attachedCfCliFacCustomEmpCollection.add(cfCliFacCustomEmpCollectionCfCliFacCustomEmpToAttach);
            }
            empresas.setCfCliFacCustomEmpCollection(attachedCfCliFacCustomEmpCollection);
            Collection<ProvAlbCab> attachedProvAlbCabCollection = new ArrayList<ProvAlbCab>();
            for (ProvAlbCab provAlbCabCollectionProvAlbCabToAttach : empresas.getProvAlbCabCollection()) {
                provAlbCabCollectionProvAlbCabToAttach = em.getReference(provAlbCabCollectionProvAlbCabToAttach.getClass(), provAlbCabCollectionProvAlbCabToAttach.getId());
                attachedProvAlbCabCollection.add(provAlbCabCollectionProvAlbCabToAttach);
            }
            empresas.setProvAlbCabCollection(attachedProvAlbCabCollection);
            Collection<Articulos> attachedArticulosCollection = new ArrayList<Articulos>();
            for (Articulos articulosCollectionArticulosToAttach : empresas.getArticulosCollection()) {
                articulosCollectionArticulosToAttach = em.getReference(articulosCollectionArticulosToAttach.getClass(), articulosCollectionArticulosToAttach.getCodigo());
                attachedArticulosCollection.add(articulosCollectionArticulosToAttach);
            }
            empresas.setArticulosCollection(attachedArticulosCollection);
            Collection<CliFacCab> attachedCliFacCabCollection = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionCliFacCabToAttach : empresas.getCliFacCabCollection()) {
                cliFacCabCollectionCliFacCabToAttach = em.getReference(cliFacCabCollectionCliFacCabToAttach.getClass(), cliFacCabCollectionCliFacCabToAttach.getId());
                attachedCliFacCabCollection.add(cliFacCabCollectionCliFacCabToAttach);
            }
            empresas.setCliFacCabCollection(attachedCliFacCabCollection);
            Collection<Promociones> attachedPromocionesCollection = new ArrayList<Promociones>();
            for (Promociones promocionesCollectionPromocionesToAttach : empresas.getPromocionesCollection()) {
                promocionesCollectionPromocionesToAttach = em.getReference(promocionesCollectionPromocionesToAttach.getClass(), promocionesCollectionPromocionesToAttach.getId());
                attachedPromocionesCollection.add(promocionesCollectionPromocionesToAttach);
            }
            empresas.setPromocionesCollection(attachedPromocionesCollection);
            Collection<CfProvAlbCustomEmp> attachedCfProvAlbCustomEmpCollection = new ArrayList<CfProvAlbCustomEmp>();
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach : empresas.getCfProvAlbCustomEmpCollection()) {
                cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach = em.getReference(cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach.getClass(), cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach.getId());
                attachedCfProvAlbCustomEmpCollection.add(cfProvAlbCustomEmpCollectionCfProvAlbCustomEmpToAttach);
            }
            empresas.setCfProvAlbCustomEmpCollection(attachedCfProvAlbCustomEmpCollection);
            Collection<Almacenes> attachedAlmacenesCollection = new ArrayList<Almacenes>();
            for (Almacenes almacenesCollectionAlmacenesToAttach : empresas.getAlmacenesCollection()) {
                almacenesCollectionAlmacenesToAttach = em.getReference(almacenesCollectionAlmacenesToAttach.getClass(), almacenesCollectionAlmacenesToAttach.getCodigo());
                attachedAlmacenesCollection.add(almacenesCollectionAlmacenesToAttach);
            }
            empresas.setAlmacenesCollection(attachedAlmacenesCollection);
            Collection<CfDpCustomEmp> attachedCfDpCustomEmpCollection = new ArrayList<CfDpCustomEmp>();
            for (CfDpCustomEmp cfDpCustomEmpCollectionCfDpCustomEmpToAttach : empresas.getCfDpCustomEmpCollection()) {
                cfDpCustomEmpCollectionCfDpCustomEmpToAttach = em.getReference(cfDpCustomEmpCollectionCfDpCustomEmpToAttach.getClass(), cfDpCustomEmpCollectionCfDpCustomEmpToAttach.getId());
                attachedCfDpCustomEmpCollection.add(cfDpCustomEmpCollectionCfDpCustomEmpToAttach);
            }
            empresas.setCfDpCustomEmpCollection(attachedCfDpCustomEmpCollection);
            Collection<CfCliPedCustomEmp> attachedCfCliPedCustomEmpCollection = new ArrayList<CfCliPedCustomEmp>();
            for (CfCliPedCustomEmp cfCliPedCustomEmpCollectionCfCliPedCustomEmpToAttach : empresas.getCfCliPedCustomEmpCollection()) {
                cfCliPedCustomEmpCollectionCfCliPedCustomEmpToAttach = em.getReference(cfCliPedCustomEmpCollectionCfCliPedCustomEmpToAttach.getClass(), cfCliPedCustomEmpCollectionCfCliPedCustomEmpToAttach.getId());
                attachedCfCliPedCustomEmpCollection.add(cfCliPedCustomEmpCollectionCfCliPedCustomEmpToAttach);
            }
            empresas.setCfCliPedCustomEmpCollection(attachedCfCliPedCustomEmpCollection);
            Collection<CfProvFacCustomEmp> attachedCfProvFacCustomEmpCollection = new ArrayList<CfProvFacCustomEmp>();
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach : empresas.getCfProvFacCustomEmpCollection()) {
                cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach = em.getReference(cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach.getClass(), cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach.getId());
                attachedCfProvFacCustomEmpCollection.add(cfProvFacCustomEmpCollectionCfProvFacCustomEmpToAttach);
            }
            empresas.setCfProvFacCustomEmpCollection(attachedCfProvFacCustomEmpCollection);
            Collection<Proveedores> attachedProveedoresCollection = new ArrayList<Proveedores>();
            for (Proveedores proveedoresCollectionProveedoresToAttach : empresas.getProveedoresCollection()) {
                proveedoresCollectionProveedoresToAttach = em.getReference(proveedoresCollectionProveedoresToAttach.getClass(), proveedoresCollectionProveedoresToAttach.getCodigo());
                attachedProveedoresCollection.add(proveedoresCollectionProveedoresToAttach);
            }
            empresas.setProveedoresCollection(attachedProveedoresCollection);
            Collection<PreciosVentas> attachedPreciosVentasCollection = new ArrayList<PreciosVentas>();
            for (PreciosVentas preciosVentasCollectionPreciosVentasToAttach : empresas.getPreciosVentasCollection()) {
                preciosVentasCollectionPreciosVentasToAttach = em.getReference(preciosVentasCollectionPreciosVentasToAttach.getClass(), preciosVentasCollectionPreciosVentasToAttach.getId());
                attachedPreciosVentasCollection.add(preciosVentasCollectionPreciosVentasToAttach);
            }
            empresas.setPreciosVentasCollection(attachedPreciosVentasCollection);
            Collection<CliPedidosCab> attachedCliPedidosCabCollection = new ArrayList<CliPedidosCab>();
            for (CliPedidosCab cliPedidosCabCollectionCliPedidosCabToAttach : empresas.getCliPedidosCabCollection()) {
                cliPedidosCabCollectionCliPedidosCabToAttach = em.getReference(cliPedidosCabCollectionCliPedidosCabToAttach.getClass(), cliPedidosCabCollectionCliPedidosCabToAttach.getId());
                attachedCliPedidosCabCollection.add(cliPedidosCabCollectionCliPedidosCabToAttach);
            }
            empresas.setCliPedidosCabCollection(attachedCliPedidosCabCollection);
            em.persist(empresas);
            for (Grupos gruposCollectionGrupos : empresas.getGruposCollection()) {
                gruposCollectionGrupos.getEmpresasCollection().add(empresas);
                gruposCollectionGrupos = em.merge(gruposCollectionGrupos);
            }
            for (Usuarios usuariosCollectionUsuarios : empresas.getUsuariosCollection()) {
                usuariosCollectionUsuarios.getEmpresasCollection().add(empresas);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            for (CfProdCustomEmp cfProdCustomEmpCollectionCfProdCustomEmp : empresas.getCfProdCustomEmpCollection()) {
                Empresas oldCodEmpOfCfProdCustomEmpCollectionCfProdCustomEmp = cfProdCustomEmpCollectionCfProdCustomEmp.getCodEmp();
                cfProdCustomEmpCollectionCfProdCustomEmp.setCodEmp(empresas);
                cfProdCustomEmpCollectionCfProdCustomEmp = em.merge(cfProdCustomEmpCollectionCfProdCustomEmp);
                if (oldCodEmpOfCfProdCustomEmpCollectionCfProdCustomEmp != null) {
                    oldCodEmpOfCfProdCustomEmpCollectionCfProdCustomEmp.getCfProdCustomEmpCollection().remove(cfProdCustomEmpCollectionCfProdCustomEmp);
                    oldCodEmpOfCfProdCustomEmpCollectionCfProdCustomEmp = em.merge(oldCodEmpOfCfProdCustomEmpCollectionCfProdCustomEmp);
                }
            }
            for (ProvPedidosCab provPedidosCabCollectionProvPedidosCab : empresas.getProvPedidosCabCollection()) {
                Empresas oldCodEmpOfProvPedidosCabCollectionProvPedidosCab = provPedidosCabCollectionProvPedidosCab.getCodEmp();
                provPedidosCabCollectionProvPedidosCab.setCodEmp(empresas);
                provPedidosCabCollectionProvPedidosCab = em.merge(provPedidosCabCollectionProvPedidosCab);
                if (oldCodEmpOfProvPedidosCabCollectionProvPedidosCab != null) {
                    oldCodEmpOfProvPedidosCabCollectionProvPedidosCab.getProvPedidosCabCollection().remove(provPedidosCabCollectionProvPedidosCab);
                    oldCodEmpOfProvPedidosCabCollectionProvPedidosCab = em.merge(oldCodEmpOfProvPedidosCabCollectionProvPedidosCab);
                }
            }
            for (CliAlbCab cliAlbCabCollectionCliAlbCab : empresas.getCliAlbCabCollection()) {
                Empresas oldCodEmpOfCliAlbCabCollectionCliAlbCab = cliAlbCabCollectionCliAlbCab.getCodEmp();
                cliAlbCabCollectionCliAlbCab.setCodEmp(empresas);
                cliAlbCabCollectionCliAlbCab = em.merge(cliAlbCabCollectionCliAlbCab);
                if (oldCodEmpOfCliAlbCabCollectionCliAlbCab != null) {
                    oldCodEmpOfCliAlbCabCollectionCliAlbCab.getCliAlbCabCollection().remove(cliAlbCabCollectionCliAlbCab);
                    oldCodEmpOfCliAlbCabCollectionCliAlbCab = em.merge(oldCodEmpOfCliAlbCabCollectionCliAlbCab);
                }
            }
            for (Stock stockCollectionStock : empresas.getStockCollection()) {
                Empresas oldCodEmpOfStockCollectionStock = stockCollectionStock.getCodEmp();
                stockCollectionStock.setCodEmp(empresas);
                stockCollectionStock = em.merge(stockCollectionStock);
                if (oldCodEmpOfStockCollectionStock != null) {
                    oldCodEmpOfStockCollectionStock.getStockCollection().remove(stockCollectionStock);
                    oldCodEmpOfStockCollectionStock = em.merge(oldCodEmpOfStockCollectionStock);
                }
            }
            for (PreciosCompras preciosComprasCollectionPreciosCompras : empresas.getPreciosComprasCollection()) {
                Empresas oldCodEmpOfPreciosComprasCollectionPreciosCompras = preciosComprasCollectionPreciosCompras.getCodEmp();
                preciosComprasCollectionPreciosCompras.setCodEmp(empresas);
                preciosComprasCollectionPreciosCompras = em.merge(preciosComprasCollectionPreciosCompras);
                if (oldCodEmpOfPreciosComprasCollectionPreciosCompras != null) {
                    oldCodEmpOfPreciosComprasCollectionPreciosCompras.getPreciosComprasCollection().remove(preciosComprasCollectionPreciosCompras);
                    oldCodEmpOfPreciosComprasCollectionPreciosCompras = em.merge(oldCodEmpOfPreciosComprasCollectionPreciosCompras);
                }
            }
            for (Contadores contadoresCollectionContadores : empresas.getContadoresCollection()) {
                Empresas oldCodEmpOfContadoresCollectionContadores = contadoresCollectionContadores.getCodEmp();
                contadoresCollectionContadores.setCodEmp(empresas);
                contadoresCollectionContadores = em.merge(contadoresCollectionContadores);
                if (oldCodEmpOfContadoresCollectionContadores != null) {
                    oldCodEmpOfContadoresCollectionContadores.getContadoresCollection().remove(contadoresCollectionContadores);
                    oldCodEmpOfContadoresCollectionContadores = em.merge(oldCodEmpOfContadoresCollectionContadores);
                }
            }
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionCfProvPedCustomEmp : empresas.getCfProvPedCustomEmpCollection()) {
                Empresas oldCodEmpOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp = cfProvPedCustomEmpCollectionCfProvPedCustomEmp.getCodEmp();
                cfProvPedCustomEmpCollectionCfProvPedCustomEmp.setCodEmp(empresas);
                cfProvPedCustomEmpCollectionCfProvPedCustomEmp = em.merge(cfProvPedCustomEmpCollectionCfProvPedCustomEmp);
                if (oldCodEmpOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp != null) {
                    oldCodEmpOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp.getCfProvPedCustomEmpCollection().remove(cfProvPedCustomEmpCollectionCfProvPedCustomEmp);
                    oldCodEmpOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp = em.merge(oldCodEmpOfCfProvPedCustomEmpCollectionCfProvPedCustomEmp);
                }
            }
            for (Clientes clientesCollectionClientes : empresas.getClientesCollection()) {
                Empresas oldCodigoEmpOfClientesCollectionClientes = clientesCollectionClientes.getCodigoEmp();
                clientesCollectionClientes.setCodigoEmp(empresas);
                clientesCollectionClientes = em.merge(clientesCollectionClientes);
                if (oldCodigoEmpOfClientesCollectionClientes != null) {
                    oldCodigoEmpOfClientesCollectionClientes.getClientesCollection().remove(clientesCollectionClientes);
                    oldCodigoEmpOfClientesCollectionClientes = em.merge(oldCodigoEmpOfClientesCollectionClientes);
                }
            }
            for (CfCliAlbCustomEmp cfCliAlbCustomEmpCollectionCfCliAlbCustomEmp : empresas.getCfCliAlbCustomEmpCollection()) {
                Empresas oldCodEmpOfCfCliAlbCustomEmpCollectionCfCliAlbCustomEmp = cfCliAlbCustomEmpCollectionCfCliAlbCustomEmp.getCodEmp();
                cfCliAlbCustomEmpCollectionCfCliAlbCustomEmp.setCodEmp(empresas);
                cfCliAlbCustomEmpCollectionCfCliAlbCustomEmp = em.merge(cfCliAlbCustomEmpCollectionCfCliAlbCustomEmp);
                if (oldCodEmpOfCfCliAlbCustomEmpCollectionCfCliAlbCustomEmp != null) {
                    oldCodEmpOfCfCliAlbCustomEmpCollectionCfCliAlbCustomEmp.getCfCliAlbCustomEmpCollection().remove(cfCliAlbCustomEmpCollectionCfCliAlbCustomEmp);
                    oldCodEmpOfCfCliAlbCustomEmpCollectionCfCliAlbCustomEmp = em.merge(oldCodEmpOfCfCliAlbCustomEmpCollectionCfCliAlbCustomEmp);
                }
            }
            for (ProvFacCab provFacCabCollectionProvFacCab : empresas.getProvFacCabCollection()) {
                Empresas oldCodEmpOfProvFacCabCollectionProvFacCab = provFacCabCollectionProvFacCab.getCodEmp();
                provFacCabCollectionProvFacCab.setCodEmp(empresas);
                provFacCabCollectionProvFacCab = em.merge(provFacCabCollectionProvFacCab);
                if (oldCodEmpOfProvFacCabCollectionProvFacCab != null) {
                    oldCodEmpOfProvFacCabCollectionProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionProvFacCab);
                    oldCodEmpOfProvFacCabCollectionProvFacCab = em.merge(oldCodEmpOfProvFacCabCollectionProvFacCab);
                }
            }
            for (CfCliFacCustomEmp cfCliFacCustomEmpCollectionCfCliFacCustomEmp : empresas.getCfCliFacCustomEmpCollection()) {
                Empresas oldCodEmpOfCfCliFacCustomEmpCollectionCfCliFacCustomEmp = cfCliFacCustomEmpCollectionCfCliFacCustomEmp.getCodEmp();
                cfCliFacCustomEmpCollectionCfCliFacCustomEmp.setCodEmp(empresas);
                cfCliFacCustomEmpCollectionCfCliFacCustomEmp = em.merge(cfCliFacCustomEmpCollectionCfCliFacCustomEmp);
                if (oldCodEmpOfCfCliFacCustomEmpCollectionCfCliFacCustomEmp != null) {
                    oldCodEmpOfCfCliFacCustomEmpCollectionCfCliFacCustomEmp.getCfCliFacCustomEmpCollection().remove(cfCliFacCustomEmpCollectionCfCliFacCustomEmp);
                    oldCodEmpOfCfCliFacCustomEmpCollectionCfCliFacCustomEmp = em.merge(oldCodEmpOfCfCliFacCustomEmpCollectionCfCliFacCustomEmp);
                }
            }
            for (ProvAlbCab provAlbCabCollectionProvAlbCab : empresas.getProvAlbCabCollection()) {
                Empresas oldCodEmpOfProvAlbCabCollectionProvAlbCab = provAlbCabCollectionProvAlbCab.getCodEmp();
                provAlbCabCollectionProvAlbCab.setCodEmp(empresas);
                provAlbCabCollectionProvAlbCab = em.merge(provAlbCabCollectionProvAlbCab);
                if (oldCodEmpOfProvAlbCabCollectionProvAlbCab != null) {
                    oldCodEmpOfProvAlbCabCollectionProvAlbCab.getProvAlbCabCollection().remove(provAlbCabCollectionProvAlbCab);
                    oldCodEmpOfProvAlbCabCollectionProvAlbCab = em.merge(oldCodEmpOfProvAlbCabCollectionProvAlbCab);
                }
            }
            for (Articulos articulosCollectionArticulos : empresas.getArticulosCollection()) {
                Empresas oldCodEmpOfArticulosCollectionArticulos = articulosCollectionArticulos.getCodEmp();
                articulosCollectionArticulos.setCodEmp(empresas);
                articulosCollectionArticulos = em.merge(articulosCollectionArticulos);
                if (oldCodEmpOfArticulosCollectionArticulos != null) {
                    oldCodEmpOfArticulosCollectionArticulos.getArticulosCollection().remove(articulosCollectionArticulos);
                    oldCodEmpOfArticulosCollectionArticulos = em.merge(oldCodEmpOfArticulosCollectionArticulos);
                }
            }
            for (CliFacCab cliFacCabCollectionCliFacCab : empresas.getCliFacCabCollection()) {
                Empresas oldCodEmpOfCliFacCabCollectionCliFacCab = cliFacCabCollectionCliFacCab.getCodEmp();
                cliFacCabCollectionCliFacCab.setCodEmp(empresas);
                cliFacCabCollectionCliFacCab = em.merge(cliFacCabCollectionCliFacCab);
                if (oldCodEmpOfCliFacCabCollectionCliFacCab != null) {
                    oldCodEmpOfCliFacCabCollectionCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionCliFacCab);
                    oldCodEmpOfCliFacCabCollectionCliFacCab = em.merge(oldCodEmpOfCliFacCabCollectionCliFacCab);
                }
            }
            for (Promociones promocionesCollectionPromociones : empresas.getPromocionesCollection()) {
                Empresas oldCodEmpOfPromocionesCollectionPromociones = promocionesCollectionPromociones.getCodEmp();
                promocionesCollectionPromociones.setCodEmp(empresas);
                promocionesCollectionPromociones = em.merge(promocionesCollectionPromociones);
                if (oldCodEmpOfPromocionesCollectionPromociones != null) {
                    oldCodEmpOfPromocionesCollectionPromociones.getPromocionesCollection().remove(promocionesCollectionPromociones);
                    oldCodEmpOfPromocionesCollectionPromociones = em.merge(oldCodEmpOfPromocionesCollectionPromociones);
                }
            }
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp : empresas.getCfProvAlbCustomEmpCollection()) {
                Empresas oldCodEmpOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp = cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp.getCodEmp();
                cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp.setCodEmp(empresas);
                cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp = em.merge(cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp);
                if (oldCodEmpOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp != null) {
                    oldCodEmpOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp.getCfProvAlbCustomEmpCollection().remove(cfProvAlbCustomEmpCollectionCfProvAlbCustomEmp);
                    oldCodEmpOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp = em.merge(oldCodEmpOfCfProvAlbCustomEmpCollectionCfProvAlbCustomEmp);
                }
            }
            for (Almacenes almacenesCollectionAlmacenes : empresas.getAlmacenesCollection()) {
                Empresas oldCodEmpOfAlmacenesCollectionAlmacenes = almacenesCollectionAlmacenes.getCodEmp();
                almacenesCollectionAlmacenes.setCodEmp(empresas);
                almacenesCollectionAlmacenes = em.merge(almacenesCollectionAlmacenes);
                if (oldCodEmpOfAlmacenesCollectionAlmacenes != null) {
                    oldCodEmpOfAlmacenesCollectionAlmacenes.getAlmacenesCollection().remove(almacenesCollectionAlmacenes);
                    oldCodEmpOfAlmacenesCollectionAlmacenes = em.merge(oldCodEmpOfAlmacenesCollectionAlmacenes);
                }
            }
            for (CfDpCustomEmp cfDpCustomEmpCollectionCfDpCustomEmp : empresas.getCfDpCustomEmpCollection()) {
                Empresas oldCodEmpOfCfDpCustomEmpCollectionCfDpCustomEmp = cfDpCustomEmpCollectionCfDpCustomEmp.getCodEmp();
                cfDpCustomEmpCollectionCfDpCustomEmp.setCodEmp(empresas);
                cfDpCustomEmpCollectionCfDpCustomEmp = em.merge(cfDpCustomEmpCollectionCfDpCustomEmp);
                if (oldCodEmpOfCfDpCustomEmpCollectionCfDpCustomEmp != null) {
                    oldCodEmpOfCfDpCustomEmpCollectionCfDpCustomEmp.getCfDpCustomEmpCollection().remove(cfDpCustomEmpCollectionCfDpCustomEmp);
                    oldCodEmpOfCfDpCustomEmpCollectionCfDpCustomEmp = em.merge(oldCodEmpOfCfDpCustomEmpCollectionCfDpCustomEmp);
                }
            }
            for (CfCliPedCustomEmp cfCliPedCustomEmpCollectionCfCliPedCustomEmp : empresas.getCfCliPedCustomEmpCollection()) {
                Empresas oldCodEmpOfCfCliPedCustomEmpCollectionCfCliPedCustomEmp = cfCliPedCustomEmpCollectionCfCliPedCustomEmp.getCodEmp();
                cfCliPedCustomEmpCollectionCfCliPedCustomEmp.setCodEmp(empresas);
                cfCliPedCustomEmpCollectionCfCliPedCustomEmp = em.merge(cfCliPedCustomEmpCollectionCfCliPedCustomEmp);
                if (oldCodEmpOfCfCliPedCustomEmpCollectionCfCliPedCustomEmp != null) {
                    oldCodEmpOfCfCliPedCustomEmpCollectionCfCliPedCustomEmp.getCfCliPedCustomEmpCollection().remove(cfCliPedCustomEmpCollectionCfCliPedCustomEmp);
                    oldCodEmpOfCfCliPedCustomEmpCollectionCfCliPedCustomEmp = em.merge(oldCodEmpOfCfCliPedCustomEmpCollectionCfCliPedCustomEmp);
                }
            }
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionCfProvFacCustomEmp : empresas.getCfProvFacCustomEmpCollection()) {
                Empresas oldCodEmpOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp = cfProvFacCustomEmpCollectionCfProvFacCustomEmp.getCodEmp();
                cfProvFacCustomEmpCollectionCfProvFacCustomEmp.setCodEmp(empresas);
                cfProvFacCustomEmpCollectionCfProvFacCustomEmp = em.merge(cfProvFacCustomEmpCollectionCfProvFacCustomEmp);
                if (oldCodEmpOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp != null) {
                    oldCodEmpOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp.getCfProvFacCustomEmpCollection().remove(cfProvFacCustomEmpCollectionCfProvFacCustomEmp);
                    oldCodEmpOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp = em.merge(oldCodEmpOfCfProvFacCustomEmpCollectionCfProvFacCustomEmp);
                }
            }
            for (Proveedores proveedoresCollectionProveedores : empresas.getProveedoresCollection()) {
                Empresas oldCodigoEmpOfProveedoresCollectionProveedores = proveedoresCollectionProveedores.getCodigoEmp();
                proveedoresCollectionProveedores.setCodigoEmp(empresas);
                proveedoresCollectionProveedores = em.merge(proveedoresCollectionProveedores);
                if (oldCodigoEmpOfProveedoresCollectionProveedores != null) {
                    oldCodigoEmpOfProveedoresCollectionProveedores.getProveedoresCollection().remove(proveedoresCollectionProveedores);
                    oldCodigoEmpOfProveedoresCollectionProveedores = em.merge(oldCodigoEmpOfProveedoresCollectionProveedores);
                }
            }
            for (PreciosVentas preciosVentasCollectionPreciosVentas : empresas.getPreciosVentasCollection()) {
                Empresas oldCodEmpOfPreciosVentasCollectionPreciosVentas = preciosVentasCollectionPreciosVentas.getCodEmp();
                preciosVentasCollectionPreciosVentas.setCodEmp(empresas);
                preciosVentasCollectionPreciosVentas = em.merge(preciosVentasCollectionPreciosVentas);
                if (oldCodEmpOfPreciosVentasCollectionPreciosVentas != null) {
                    oldCodEmpOfPreciosVentasCollectionPreciosVentas.getPreciosVentasCollection().remove(preciosVentasCollectionPreciosVentas);
                    oldCodEmpOfPreciosVentasCollectionPreciosVentas = em.merge(oldCodEmpOfPreciosVentasCollectionPreciosVentas);
                }
            }
            for (CliPedidosCab cliPedidosCabCollectionCliPedidosCab : empresas.getCliPedidosCabCollection()) {
                Empresas oldCodEmpOfCliPedidosCabCollectionCliPedidosCab = cliPedidosCabCollectionCliPedidosCab.getCodEmp();
                cliPedidosCabCollectionCliPedidosCab.setCodEmp(empresas);
                cliPedidosCabCollectionCliPedidosCab = em.merge(cliPedidosCabCollectionCliPedidosCab);
                if (oldCodEmpOfCliPedidosCabCollectionCliPedidosCab != null) {
                    oldCodEmpOfCliPedidosCabCollectionCliPedidosCab.getCliPedidosCabCollection().remove(cliPedidosCabCollectionCliPedidosCab);
                    oldCodEmpOfCliPedidosCabCollectionCliPedidosCab = em.merge(oldCodEmpOfCliPedidosCabCollectionCliPedidosCab);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresas(empresas.getCodigo()) != null) {
                throw new PreexistingEntityException("Empresas " + empresas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresas empresas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas persistentEmpresas = em.find(Empresas.class, empresas.getCodigo());
            Collection<Grupos> gruposCollectionOld = persistentEmpresas.getGruposCollection();
            Collection<Grupos> gruposCollectionNew = empresas.getGruposCollection();
            Collection<Usuarios> usuariosCollectionOld = persistentEmpresas.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = empresas.getUsuariosCollection();
            Collection<CfProdCustomEmp> cfProdCustomEmpCollectionOld = persistentEmpresas.getCfProdCustomEmpCollection();
            Collection<CfProdCustomEmp> cfProdCustomEmpCollectionNew = empresas.getCfProdCustomEmpCollection();
            Collection<ProvPedidosCab> provPedidosCabCollectionOld = persistentEmpresas.getProvPedidosCabCollection();
            Collection<ProvPedidosCab> provPedidosCabCollectionNew = empresas.getProvPedidosCabCollection();
            Collection<CliAlbCab> cliAlbCabCollectionOld = persistentEmpresas.getCliAlbCabCollection();
            Collection<CliAlbCab> cliAlbCabCollectionNew = empresas.getCliAlbCabCollection();
            Collection<Stock> stockCollectionOld = persistentEmpresas.getStockCollection();
            Collection<Stock> stockCollectionNew = empresas.getStockCollection();
            Collection<PreciosCompras> preciosComprasCollectionOld = persistentEmpresas.getPreciosComprasCollection();
            Collection<PreciosCompras> preciosComprasCollectionNew = empresas.getPreciosComprasCollection();
            Collection<Contadores> contadoresCollectionOld = persistentEmpresas.getContadoresCollection();
            Collection<Contadores> contadoresCollectionNew = empresas.getContadoresCollection();
            Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollectionOld = persistentEmpresas.getCfProvPedCustomEmpCollection();
            Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollectionNew = empresas.getCfProvPedCustomEmpCollection();
            Collection<Clientes> clientesCollectionOld = persistentEmpresas.getClientesCollection();
            Collection<Clientes> clientesCollectionNew = empresas.getClientesCollection();
            Collection<CfCliAlbCustomEmp> cfCliAlbCustomEmpCollectionOld = persistentEmpresas.getCfCliAlbCustomEmpCollection();
            Collection<CfCliAlbCustomEmp> cfCliAlbCustomEmpCollectionNew = empresas.getCfCliAlbCustomEmpCollection();
            Collection<ProvFacCab> provFacCabCollectionOld = persistentEmpresas.getProvFacCabCollection();
            Collection<ProvFacCab> provFacCabCollectionNew = empresas.getProvFacCabCollection();
            Collection<CfCliFacCustomEmp> cfCliFacCustomEmpCollectionOld = persistentEmpresas.getCfCliFacCustomEmpCollection();
            Collection<CfCliFacCustomEmp> cfCliFacCustomEmpCollectionNew = empresas.getCfCliFacCustomEmpCollection();
            Collection<ProvAlbCab> provAlbCabCollectionOld = persistentEmpresas.getProvAlbCabCollection();
            Collection<ProvAlbCab> provAlbCabCollectionNew = empresas.getProvAlbCabCollection();
            Collection<Articulos> articulosCollectionOld = persistentEmpresas.getArticulosCollection();
            Collection<Articulos> articulosCollectionNew = empresas.getArticulosCollection();
            Collection<CliFacCab> cliFacCabCollectionOld = persistentEmpresas.getCliFacCabCollection();
            Collection<CliFacCab> cliFacCabCollectionNew = empresas.getCliFacCabCollection();
            Collection<Promociones> promocionesCollectionOld = persistentEmpresas.getPromocionesCollection();
            Collection<Promociones> promocionesCollectionNew = empresas.getPromocionesCollection();
            Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollectionOld = persistentEmpresas.getCfProvAlbCustomEmpCollection();
            Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollectionNew = empresas.getCfProvAlbCustomEmpCollection();
            Collection<Almacenes> almacenesCollectionOld = persistentEmpresas.getAlmacenesCollection();
            Collection<Almacenes> almacenesCollectionNew = empresas.getAlmacenesCollection();
            Collection<CfDpCustomEmp> cfDpCustomEmpCollectionOld = persistentEmpresas.getCfDpCustomEmpCollection();
            Collection<CfDpCustomEmp> cfDpCustomEmpCollectionNew = empresas.getCfDpCustomEmpCollection();
            Collection<CfCliPedCustomEmp> cfCliPedCustomEmpCollectionOld = persistentEmpresas.getCfCliPedCustomEmpCollection();
            Collection<CfCliPedCustomEmp> cfCliPedCustomEmpCollectionNew = empresas.getCfCliPedCustomEmpCollection();
            Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollectionOld = persistentEmpresas.getCfProvFacCustomEmpCollection();
            Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollectionNew = empresas.getCfProvFacCustomEmpCollection();
            Collection<Proveedores> proveedoresCollectionOld = persistentEmpresas.getProveedoresCollection();
            Collection<Proveedores> proveedoresCollectionNew = empresas.getProveedoresCollection();
            Collection<PreciosVentas> preciosVentasCollectionOld = persistentEmpresas.getPreciosVentasCollection();
            Collection<PreciosVentas> preciosVentasCollectionNew = empresas.getPreciosVentasCollection();
            Collection<CliPedidosCab> cliPedidosCabCollectionOld = persistentEmpresas.getCliPedidosCabCollection();
            Collection<CliPedidosCab> cliPedidosCabCollectionNew = empresas.getCliPedidosCabCollection();
            List<String> illegalOrphanMessages = null;
            for (CfProdCustomEmp cfProdCustomEmpCollectionOldCfProdCustomEmp : cfProdCustomEmpCollectionOld) {
                if (!cfProdCustomEmpCollectionNew.contains(cfProdCustomEmpCollectionOldCfProdCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfProdCustomEmp " + cfProdCustomEmpCollectionOldCfProdCustomEmp + " since its codEmp field is not nullable.");
                }
            }
            for (ProvPedidosCab provPedidosCabCollectionOldProvPedidosCab : provPedidosCabCollectionOld) {
                if (!provPedidosCabCollectionNew.contains(provPedidosCabCollectionOldProvPedidosCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvPedidosCab " + provPedidosCabCollectionOldProvPedidosCab + " since its codEmp field is not nullable.");
                }
            }
            for (CliAlbCab cliAlbCabCollectionOldCliAlbCab : cliAlbCabCollectionOld) {
                if (!cliAlbCabCollectionNew.contains(cliAlbCabCollectionOldCliAlbCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliAlbCab " + cliAlbCabCollectionOldCliAlbCab + " since its codEmp field is not nullable.");
                }
            }
            for (Stock stockCollectionOldStock : stockCollectionOld) {
                if (!stockCollectionNew.contains(stockCollectionOldStock)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Stock " + stockCollectionOldStock + " since its codEmp field is not nullable.");
                }
            }
            for (PreciosCompras preciosComprasCollectionOldPreciosCompras : preciosComprasCollectionOld) {
                if (!preciosComprasCollectionNew.contains(preciosComprasCollectionOldPreciosCompras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreciosCompras " + preciosComprasCollectionOldPreciosCompras + " since its codEmp field is not nullable.");
                }
            }
            for (Contadores contadoresCollectionOldContadores : contadoresCollectionOld) {
                if (!contadoresCollectionNew.contains(contadoresCollectionOldContadores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contadores " + contadoresCollectionOldContadores + " since its codEmp field is not nullable.");
                }
            }
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionOldCfProvPedCustomEmp : cfProvPedCustomEmpCollectionOld) {
                if (!cfProvPedCustomEmpCollectionNew.contains(cfProvPedCustomEmpCollectionOldCfProvPedCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfProvPedCustomEmp " + cfProvPedCustomEmpCollectionOldCfProvPedCustomEmp + " since its codEmp field is not nullable.");
                }
            }
            for (Clientes clientesCollectionOldClientes : clientesCollectionOld) {
                if (!clientesCollectionNew.contains(clientesCollectionOldClientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clientes " + clientesCollectionOldClientes + " since its codigoEmp field is not nullable.");
                }
            }
            for (CfCliAlbCustomEmp cfCliAlbCustomEmpCollectionOldCfCliAlbCustomEmp : cfCliAlbCustomEmpCollectionOld) {
                if (!cfCliAlbCustomEmpCollectionNew.contains(cfCliAlbCustomEmpCollectionOldCfCliAlbCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfCliAlbCustomEmp " + cfCliAlbCustomEmpCollectionOldCfCliAlbCustomEmp + " since its codEmp field is not nullable.");
                }
            }
            for (ProvFacCab provFacCabCollectionOldProvFacCab : provFacCabCollectionOld) {
                if (!provFacCabCollectionNew.contains(provFacCabCollectionOldProvFacCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvFacCab " + provFacCabCollectionOldProvFacCab + " since its codEmp field is not nullable.");
                }
            }
            for (CfCliFacCustomEmp cfCliFacCustomEmpCollectionOldCfCliFacCustomEmp : cfCliFacCustomEmpCollectionOld) {
                if (!cfCliFacCustomEmpCollectionNew.contains(cfCliFacCustomEmpCollectionOldCfCliFacCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfCliFacCustomEmp " + cfCliFacCustomEmpCollectionOldCfCliFacCustomEmp + " since its codEmp field is not nullable.");
                }
            }
            for (ProvAlbCab provAlbCabCollectionOldProvAlbCab : provAlbCabCollectionOld) {
                if (!provAlbCabCollectionNew.contains(provAlbCabCollectionOldProvAlbCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProvAlbCab " + provAlbCabCollectionOldProvAlbCab + " since its codEmp field is not nullable.");
                }
            }
            for (Articulos articulosCollectionOldArticulos : articulosCollectionOld) {
                if (!articulosCollectionNew.contains(articulosCollectionOldArticulos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Articulos " + articulosCollectionOldArticulos + " since its codEmp field is not nullable.");
                }
            }
            for (CliFacCab cliFacCabCollectionOldCliFacCab : cliFacCabCollectionOld) {
                if (!cliFacCabCollectionNew.contains(cliFacCabCollectionOldCliFacCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliFacCab " + cliFacCabCollectionOldCliFacCab + " since its codEmp field is not nullable.");
                }
            }
            for (Promociones promocionesCollectionOldPromociones : promocionesCollectionOld) {
                if (!promocionesCollectionNew.contains(promocionesCollectionOldPromociones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Promociones " + promocionesCollectionOldPromociones + " since its codEmp field is not nullable.");
                }
            }
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionOldCfProvAlbCustomEmp : cfProvAlbCustomEmpCollectionOld) {
                if (!cfProvAlbCustomEmpCollectionNew.contains(cfProvAlbCustomEmpCollectionOldCfProvAlbCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfProvAlbCustomEmp " + cfProvAlbCustomEmpCollectionOldCfProvAlbCustomEmp + " since its codEmp field is not nullable.");
                }
            }
            for (Almacenes almacenesCollectionOldAlmacenes : almacenesCollectionOld) {
                if (!almacenesCollectionNew.contains(almacenesCollectionOldAlmacenes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Almacenes " + almacenesCollectionOldAlmacenes + " since its codEmp field is not nullable.");
                }
            }
            for (CfDpCustomEmp cfDpCustomEmpCollectionOldCfDpCustomEmp : cfDpCustomEmpCollectionOld) {
                if (!cfDpCustomEmpCollectionNew.contains(cfDpCustomEmpCollectionOldCfDpCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfDpCustomEmp " + cfDpCustomEmpCollectionOldCfDpCustomEmp + " since its codEmp field is not nullable.");
                }
            }
            for (CfCliPedCustomEmp cfCliPedCustomEmpCollectionOldCfCliPedCustomEmp : cfCliPedCustomEmpCollectionOld) {
                if (!cfCliPedCustomEmpCollectionNew.contains(cfCliPedCustomEmpCollectionOldCfCliPedCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfCliPedCustomEmp " + cfCliPedCustomEmpCollectionOldCfCliPedCustomEmp + " since its codEmp field is not nullable.");
                }
            }
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionOldCfProvFacCustomEmp : cfProvFacCustomEmpCollectionOld) {
                if (!cfProvFacCustomEmpCollectionNew.contains(cfProvFacCustomEmpCollectionOldCfProvFacCustomEmp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CfProvFacCustomEmp " + cfProvFacCustomEmpCollectionOldCfProvFacCustomEmp + " since its codEmp field is not nullable.");
                }
            }
            for (Proveedores proveedoresCollectionOldProveedores : proveedoresCollectionOld) {
                if (!proveedoresCollectionNew.contains(proveedoresCollectionOldProveedores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proveedores " + proveedoresCollectionOldProveedores + " since its codigoEmp field is not nullable.");
                }
            }
            for (PreciosVentas preciosVentasCollectionOldPreciosVentas : preciosVentasCollectionOld) {
                if (!preciosVentasCollectionNew.contains(preciosVentasCollectionOldPreciosVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreciosVentas " + preciosVentasCollectionOldPreciosVentas + " since its codEmp field is not nullable.");
                }
            }
            for (CliPedidosCab cliPedidosCabCollectionOldCliPedidosCab : cliPedidosCabCollectionOld) {
                if (!cliPedidosCabCollectionNew.contains(cliPedidosCabCollectionOldCliPedidosCab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CliPedidosCab " + cliPedidosCabCollectionOldCliPedidosCab + " since its codEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Grupos> attachedGruposCollectionNew = new ArrayList<Grupos>();
            for (Grupos gruposCollectionNewGruposToAttach : gruposCollectionNew) {
                gruposCollectionNewGruposToAttach = em.getReference(gruposCollectionNewGruposToAttach.getClass(), gruposCollectionNewGruposToAttach.getId());
                attachedGruposCollectionNew.add(gruposCollectionNewGruposToAttach);
            }
            gruposCollectionNew = attachedGruposCollectionNew;
            empresas.setGruposCollection(gruposCollectionNew);
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            empresas.setUsuariosCollection(usuariosCollectionNew);
            Collection<CfProdCustomEmp> attachedCfProdCustomEmpCollectionNew = new ArrayList<CfProdCustomEmp>();
            for (CfProdCustomEmp cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach : cfProdCustomEmpCollectionNew) {
                cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach = em.getReference(cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach.getClass(), cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach.getId());
                attachedCfProdCustomEmpCollectionNew.add(cfProdCustomEmpCollectionNewCfProdCustomEmpToAttach);
            }
            cfProdCustomEmpCollectionNew = attachedCfProdCustomEmpCollectionNew;
            empresas.setCfProdCustomEmpCollection(cfProdCustomEmpCollectionNew);
            Collection<ProvPedidosCab> attachedProvPedidosCabCollectionNew = new ArrayList<ProvPedidosCab>();
            for (ProvPedidosCab provPedidosCabCollectionNewProvPedidosCabToAttach : provPedidosCabCollectionNew) {
                provPedidosCabCollectionNewProvPedidosCabToAttach = em.getReference(provPedidosCabCollectionNewProvPedidosCabToAttach.getClass(), provPedidosCabCollectionNewProvPedidosCabToAttach.getId());
                attachedProvPedidosCabCollectionNew.add(provPedidosCabCollectionNewProvPedidosCabToAttach);
            }
            provPedidosCabCollectionNew = attachedProvPedidosCabCollectionNew;
            empresas.setProvPedidosCabCollection(provPedidosCabCollectionNew);
            Collection<CliAlbCab> attachedCliAlbCabCollectionNew = new ArrayList<CliAlbCab>();
            for (CliAlbCab cliAlbCabCollectionNewCliAlbCabToAttach : cliAlbCabCollectionNew) {
                cliAlbCabCollectionNewCliAlbCabToAttach = em.getReference(cliAlbCabCollectionNewCliAlbCabToAttach.getClass(), cliAlbCabCollectionNewCliAlbCabToAttach.getId());
                attachedCliAlbCabCollectionNew.add(cliAlbCabCollectionNewCliAlbCabToAttach);
            }
            cliAlbCabCollectionNew = attachedCliAlbCabCollectionNew;
            empresas.setCliAlbCabCollection(cliAlbCabCollectionNew);
            Collection<Stock> attachedStockCollectionNew = new ArrayList<Stock>();
            for (Stock stockCollectionNewStockToAttach : stockCollectionNew) {
                stockCollectionNewStockToAttach = em.getReference(stockCollectionNewStockToAttach.getClass(), stockCollectionNewStockToAttach.getId());
                attachedStockCollectionNew.add(stockCollectionNewStockToAttach);
            }
            stockCollectionNew = attachedStockCollectionNew;
            empresas.setStockCollection(stockCollectionNew);
            Collection<PreciosCompras> attachedPreciosComprasCollectionNew = new ArrayList<PreciosCompras>();
            for (PreciosCompras preciosComprasCollectionNewPreciosComprasToAttach : preciosComprasCollectionNew) {
                preciosComprasCollectionNewPreciosComprasToAttach = em.getReference(preciosComprasCollectionNewPreciosComprasToAttach.getClass(), preciosComprasCollectionNewPreciosComprasToAttach.getId());
                attachedPreciosComprasCollectionNew.add(preciosComprasCollectionNewPreciosComprasToAttach);
            }
            preciosComprasCollectionNew = attachedPreciosComprasCollectionNew;
            empresas.setPreciosComprasCollection(preciosComprasCollectionNew);
            Collection<Contadores> attachedContadoresCollectionNew = new ArrayList<Contadores>();
            for (Contadores contadoresCollectionNewContadoresToAttach : contadoresCollectionNew) {
                contadoresCollectionNewContadoresToAttach = em.getReference(contadoresCollectionNewContadoresToAttach.getClass(), contadoresCollectionNewContadoresToAttach.getId());
                attachedContadoresCollectionNew.add(contadoresCollectionNewContadoresToAttach);
            }
            contadoresCollectionNew = attachedContadoresCollectionNew;
            empresas.setContadoresCollection(contadoresCollectionNew);
            Collection<CfProvPedCustomEmp> attachedCfProvPedCustomEmpCollectionNew = new ArrayList<CfProvPedCustomEmp>();
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach : cfProvPedCustomEmpCollectionNew) {
                cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach = em.getReference(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach.getClass(), cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach.getId());
                attachedCfProvPedCustomEmpCollectionNew.add(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmpToAttach);
            }
            cfProvPedCustomEmpCollectionNew = attachedCfProvPedCustomEmpCollectionNew;
            empresas.setCfProvPedCustomEmpCollection(cfProvPedCustomEmpCollectionNew);
            Collection<Clientes> attachedClientesCollectionNew = new ArrayList<Clientes>();
            for (Clientes clientesCollectionNewClientesToAttach : clientesCollectionNew) {
                clientesCollectionNewClientesToAttach = em.getReference(clientesCollectionNewClientesToAttach.getClass(), clientesCollectionNewClientesToAttach.getCodigo());
                attachedClientesCollectionNew.add(clientesCollectionNewClientesToAttach);
            }
            clientesCollectionNew = attachedClientesCollectionNew;
            empresas.setClientesCollection(clientesCollectionNew);
            Collection<CfCliAlbCustomEmp> attachedCfCliAlbCustomEmpCollectionNew = new ArrayList<CfCliAlbCustomEmp>();
            for (CfCliAlbCustomEmp cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmpToAttach : cfCliAlbCustomEmpCollectionNew) {
                cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmpToAttach = em.getReference(cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmpToAttach.getClass(), cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmpToAttach.getId());
                attachedCfCliAlbCustomEmpCollectionNew.add(cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmpToAttach);
            }
            cfCliAlbCustomEmpCollectionNew = attachedCfCliAlbCustomEmpCollectionNew;
            empresas.setCfCliAlbCustomEmpCollection(cfCliAlbCustomEmpCollectionNew);
            Collection<ProvFacCab> attachedProvFacCabCollectionNew = new ArrayList<ProvFacCab>();
            for (ProvFacCab provFacCabCollectionNewProvFacCabToAttach : provFacCabCollectionNew) {
                provFacCabCollectionNewProvFacCabToAttach = em.getReference(provFacCabCollectionNewProvFacCabToAttach.getClass(), provFacCabCollectionNewProvFacCabToAttach.getId());
                attachedProvFacCabCollectionNew.add(provFacCabCollectionNewProvFacCabToAttach);
            }
            provFacCabCollectionNew = attachedProvFacCabCollectionNew;
            empresas.setProvFacCabCollection(provFacCabCollectionNew);
            Collection<CfCliFacCustomEmp> attachedCfCliFacCustomEmpCollectionNew = new ArrayList<CfCliFacCustomEmp>();
            for (CfCliFacCustomEmp cfCliFacCustomEmpCollectionNewCfCliFacCustomEmpToAttach : cfCliFacCustomEmpCollectionNew) {
                cfCliFacCustomEmpCollectionNewCfCliFacCustomEmpToAttach = em.getReference(cfCliFacCustomEmpCollectionNewCfCliFacCustomEmpToAttach.getClass(), cfCliFacCustomEmpCollectionNewCfCliFacCustomEmpToAttach.getId());
                attachedCfCliFacCustomEmpCollectionNew.add(cfCliFacCustomEmpCollectionNewCfCliFacCustomEmpToAttach);
            }
            cfCliFacCustomEmpCollectionNew = attachedCfCliFacCustomEmpCollectionNew;
            empresas.setCfCliFacCustomEmpCollection(cfCliFacCustomEmpCollectionNew);
            Collection<ProvAlbCab> attachedProvAlbCabCollectionNew = new ArrayList<ProvAlbCab>();
            for (ProvAlbCab provAlbCabCollectionNewProvAlbCabToAttach : provAlbCabCollectionNew) {
                provAlbCabCollectionNewProvAlbCabToAttach = em.getReference(provAlbCabCollectionNewProvAlbCabToAttach.getClass(), provAlbCabCollectionNewProvAlbCabToAttach.getId());
                attachedProvAlbCabCollectionNew.add(provAlbCabCollectionNewProvAlbCabToAttach);
            }
            provAlbCabCollectionNew = attachedProvAlbCabCollectionNew;
            empresas.setProvAlbCabCollection(provAlbCabCollectionNew);
            Collection<Articulos> attachedArticulosCollectionNew = new ArrayList<Articulos>();
            for (Articulos articulosCollectionNewArticulosToAttach : articulosCollectionNew) {
                articulosCollectionNewArticulosToAttach = em.getReference(articulosCollectionNewArticulosToAttach.getClass(), articulosCollectionNewArticulosToAttach.getCodigo());
                attachedArticulosCollectionNew.add(articulosCollectionNewArticulosToAttach);
            }
            articulosCollectionNew = attachedArticulosCollectionNew;
            empresas.setArticulosCollection(articulosCollectionNew);
            Collection<CliFacCab> attachedCliFacCabCollectionNew = new ArrayList<CliFacCab>();
            for (CliFacCab cliFacCabCollectionNewCliFacCabToAttach : cliFacCabCollectionNew) {
                cliFacCabCollectionNewCliFacCabToAttach = em.getReference(cliFacCabCollectionNewCliFacCabToAttach.getClass(), cliFacCabCollectionNewCliFacCabToAttach.getId());
                attachedCliFacCabCollectionNew.add(cliFacCabCollectionNewCliFacCabToAttach);
            }
            cliFacCabCollectionNew = attachedCliFacCabCollectionNew;
            empresas.setCliFacCabCollection(cliFacCabCollectionNew);
            Collection<Promociones> attachedPromocionesCollectionNew = new ArrayList<Promociones>();
            for (Promociones promocionesCollectionNewPromocionesToAttach : promocionesCollectionNew) {
                promocionesCollectionNewPromocionesToAttach = em.getReference(promocionesCollectionNewPromocionesToAttach.getClass(), promocionesCollectionNewPromocionesToAttach.getId());
                attachedPromocionesCollectionNew.add(promocionesCollectionNewPromocionesToAttach);
            }
            promocionesCollectionNew = attachedPromocionesCollectionNew;
            empresas.setPromocionesCollection(promocionesCollectionNew);
            Collection<CfProvAlbCustomEmp> attachedCfProvAlbCustomEmpCollectionNew = new ArrayList<CfProvAlbCustomEmp>();
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach : cfProvAlbCustomEmpCollectionNew) {
                cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach = em.getReference(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach.getClass(), cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach.getId());
                attachedCfProvAlbCustomEmpCollectionNew.add(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmpToAttach);
            }
            cfProvAlbCustomEmpCollectionNew = attachedCfProvAlbCustomEmpCollectionNew;
            empresas.setCfProvAlbCustomEmpCollection(cfProvAlbCustomEmpCollectionNew);
            Collection<Almacenes> attachedAlmacenesCollectionNew = new ArrayList<Almacenes>();
            for (Almacenes almacenesCollectionNewAlmacenesToAttach : almacenesCollectionNew) {
                almacenesCollectionNewAlmacenesToAttach = em.getReference(almacenesCollectionNewAlmacenesToAttach.getClass(), almacenesCollectionNewAlmacenesToAttach.getCodigo());
                attachedAlmacenesCollectionNew.add(almacenesCollectionNewAlmacenesToAttach);
            }
            almacenesCollectionNew = attachedAlmacenesCollectionNew;
            empresas.setAlmacenesCollection(almacenesCollectionNew);
            Collection<CfDpCustomEmp> attachedCfDpCustomEmpCollectionNew = new ArrayList<CfDpCustomEmp>();
            for (CfDpCustomEmp cfDpCustomEmpCollectionNewCfDpCustomEmpToAttach : cfDpCustomEmpCollectionNew) {
                cfDpCustomEmpCollectionNewCfDpCustomEmpToAttach = em.getReference(cfDpCustomEmpCollectionNewCfDpCustomEmpToAttach.getClass(), cfDpCustomEmpCollectionNewCfDpCustomEmpToAttach.getId());
                attachedCfDpCustomEmpCollectionNew.add(cfDpCustomEmpCollectionNewCfDpCustomEmpToAttach);
            }
            cfDpCustomEmpCollectionNew = attachedCfDpCustomEmpCollectionNew;
            empresas.setCfDpCustomEmpCollection(cfDpCustomEmpCollectionNew);
            Collection<CfCliPedCustomEmp> attachedCfCliPedCustomEmpCollectionNew = new ArrayList<CfCliPedCustomEmp>();
            for (CfCliPedCustomEmp cfCliPedCustomEmpCollectionNewCfCliPedCustomEmpToAttach : cfCliPedCustomEmpCollectionNew) {
                cfCliPedCustomEmpCollectionNewCfCliPedCustomEmpToAttach = em.getReference(cfCliPedCustomEmpCollectionNewCfCliPedCustomEmpToAttach.getClass(), cfCliPedCustomEmpCollectionNewCfCliPedCustomEmpToAttach.getId());
                attachedCfCliPedCustomEmpCollectionNew.add(cfCliPedCustomEmpCollectionNewCfCliPedCustomEmpToAttach);
            }
            cfCliPedCustomEmpCollectionNew = attachedCfCliPedCustomEmpCollectionNew;
            empresas.setCfCliPedCustomEmpCollection(cfCliPedCustomEmpCollectionNew);
            Collection<CfProvFacCustomEmp> attachedCfProvFacCustomEmpCollectionNew = new ArrayList<CfProvFacCustomEmp>();
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach : cfProvFacCustomEmpCollectionNew) {
                cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach = em.getReference(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach.getClass(), cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach.getId());
                attachedCfProvFacCustomEmpCollectionNew.add(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmpToAttach);
            }
            cfProvFacCustomEmpCollectionNew = attachedCfProvFacCustomEmpCollectionNew;
            empresas.setCfProvFacCustomEmpCollection(cfProvFacCustomEmpCollectionNew);
            Collection<Proveedores> attachedProveedoresCollectionNew = new ArrayList<Proveedores>();
            for (Proveedores proveedoresCollectionNewProveedoresToAttach : proveedoresCollectionNew) {
                proveedoresCollectionNewProveedoresToAttach = em.getReference(proveedoresCollectionNewProveedoresToAttach.getClass(), proveedoresCollectionNewProveedoresToAttach.getCodigo());
                attachedProveedoresCollectionNew.add(proveedoresCollectionNewProveedoresToAttach);
            }
            proveedoresCollectionNew = attachedProveedoresCollectionNew;
            empresas.setProveedoresCollection(proveedoresCollectionNew);
            Collection<PreciosVentas> attachedPreciosVentasCollectionNew = new ArrayList<PreciosVentas>();
            for (PreciosVentas preciosVentasCollectionNewPreciosVentasToAttach : preciosVentasCollectionNew) {
                preciosVentasCollectionNewPreciosVentasToAttach = em.getReference(preciosVentasCollectionNewPreciosVentasToAttach.getClass(), preciosVentasCollectionNewPreciosVentasToAttach.getId());
                attachedPreciosVentasCollectionNew.add(preciosVentasCollectionNewPreciosVentasToAttach);
            }
            preciosVentasCollectionNew = attachedPreciosVentasCollectionNew;
            empresas.setPreciosVentasCollection(preciosVentasCollectionNew);
            Collection<CliPedidosCab> attachedCliPedidosCabCollectionNew = new ArrayList<CliPedidosCab>();
            for (CliPedidosCab cliPedidosCabCollectionNewCliPedidosCabToAttach : cliPedidosCabCollectionNew) {
                cliPedidosCabCollectionNewCliPedidosCabToAttach = em.getReference(cliPedidosCabCollectionNewCliPedidosCabToAttach.getClass(), cliPedidosCabCollectionNewCliPedidosCabToAttach.getId());
                attachedCliPedidosCabCollectionNew.add(cliPedidosCabCollectionNewCliPedidosCabToAttach);
            }
            cliPedidosCabCollectionNew = attachedCliPedidosCabCollectionNew;
            empresas.setCliPedidosCabCollection(cliPedidosCabCollectionNew);
            empresas = em.merge(empresas);
            for (Grupos gruposCollectionOldGrupos : gruposCollectionOld) {
                if (!gruposCollectionNew.contains(gruposCollectionOldGrupos)) {
                    gruposCollectionOldGrupos.getEmpresasCollection().remove(empresas);
                    gruposCollectionOldGrupos = em.merge(gruposCollectionOldGrupos);
                }
            }
            for (Grupos gruposCollectionNewGrupos : gruposCollectionNew) {
                if (!gruposCollectionOld.contains(gruposCollectionNewGrupos)) {
                    gruposCollectionNewGrupos.getEmpresasCollection().add(empresas);
                    gruposCollectionNewGrupos = em.merge(gruposCollectionNewGrupos);
                }
            }
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    usuariosCollectionOldUsuarios.getEmpresasCollection().remove(empresas);
                    usuariosCollectionOldUsuarios = em.merge(usuariosCollectionOldUsuarios);
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    usuariosCollectionNewUsuarios.getEmpresasCollection().add(empresas);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                }
            }
            for (CfProdCustomEmp cfProdCustomEmpCollectionNewCfProdCustomEmp : cfProdCustomEmpCollectionNew) {
                if (!cfProdCustomEmpCollectionOld.contains(cfProdCustomEmpCollectionNewCfProdCustomEmp)) {
                    Empresas oldCodEmpOfCfProdCustomEmpCollectionNewCfProdCustomEmp = cfProdCustomEmpCollectionNewCfProdCustomEmp.getCodEmp();
                    cfProdCustomEmpCollectionNewCfProdCustomEmp.setCodEmp(empresas);
                    cfProdCustomEmpCollectionNewCfProdCustomEmp = em.merge(cfProdCustomEmpCollectionNewCfProdCustomEmp);
                    if (oldCodEmpOfCfProdCustomEmpCollectionNewCfProdCustomEmp != null && !oldCodEmpOfCfProdCustomEmpCollectionNewCfProdCustomEmp.equals(empresas)) {
                        oldCodEmpOfCfProdCustomEmpCollectionNewCfProdCustomEmp.getCfProdCustomEmpCollection().remove(cfProdCustomEmpCollectionNewCfProdCustomEmp);
                        oldCodEmpOfCfProdCustomEmpCollectionNewCfProdCustomEmp = em.merge(oldCodEmpOfCfProdCustomEmpCollectionNewCfProdCustomEmp);
                    }
                }
            }
            for (ProvPedidosCab provPedidosCabCollectionNewProvPedidosCab : provPedidosCabCollectionNew) {
                if (!provPedidosCabCollectionOld.contains(provPedidosCabCollectionNewProvPedidosCab)) {
                    Empresas oldCodEmpOfProvPedidosCabCollectionNewProvPedidosCab = provPedidosCabCollectionNewProvPedidosCab.getCodEmp();
                    provPedidosCabCollectionNewProvPedidosCab.setCodEmp(empresas);
                    provPedidosCabCollectionNewProvPedidosCab = em.merge(provPedidosCabCollectionNewProvPedidosCab);
                    if (oldCodEmpOfProvPedidosCabCollectionNewProvPedidosCab != null && !oldCodEmpOfProvPedidosCabCollectionNewProvPedidosCab.equals(empresas)) {
                        oldCodEmpOfProvPedidosCabCollectionNewProvPedidosCab.getProvPedidosCabCollection().remove(provPedidosCabCollectionNewProvPedidosCab);
                        oldCodEmpOfProvPedidosCabCollectionNewProvPedidosCab = em.merge(oldCodEmpOfProvPedidosCabCollectionNewProvPedidosCab);
                    }
                }
            }
            for (CliAlbCab cliAlbCabCollectionNewCliAlbCab : cliAlbCabCollectionNew) {
                if (!cliAlbCabCollectionOld.contains(cliAlbCabCollectionNewCliAlbCab)) {
                    Empresas oldCodEmpOfCliAlbCabCollectionNewCliAlbCab = cliAlbCabCollectionNewCliAlbCab.getCodEmp();
                    cliAlbCabCollectionNewCliAlbCab.setCodEmp(empresas);
                    cliAlbCabCollectionNewCliAlbCab = em.merge(cliAlbCabCollectionNewCliAlbCab);
                    if (oldCodEmpOfCliAlbCabCollectionNewCliAlbCab != null && !oldCodEmpOfCliAlbCabCollectionNewCliAlbCab.equals(empresas)) {
                        oldCodEmpOfCliAlbCabCollectionNewCliAlbCab.getCliAlbCabCollection().remove(cliAlbCabCollectionNewCliAlbCab);
                        oldCodEmpOfCliAlbCabCollectionNewCliAlbCab = em.merge(oldCodEmpOfCliAlbCabCollectionNewCliAlbCab);
                    }
                }
            }
            for (Stock stockCollectionNewStock : stockCollectionNew) {
                if (!stockCollectionOld.contains(stockCollectionNewStock)) {
                    Empresas oldCodEmpOfStockCollectionNewStock = stockCollectionNewStock.getCodEmp();
                    stockCollectionNewStock.setCodEmp(empresas);
                    stockCollectionNewStock = em.merge(stockCollectionNewStock);
                    if (oldCodEmpOfStockCollectionNewStock != null && !oldCodEmpOfStockCollectionNewStock.equals(empresas)) {
                        oldCodEmpOfStockCollectionNewStock.getStockCollection().remove(stockCollectionNewStock);
                        oldCodEmpOfStockCollectionNewStock = em.merge(oldCodEmpOfStockCollectionNewStock);
                    }
                }
            }
            for (PreciosCompras preciosComprasCollectionNewPreciosCompras : preciosComprasCollectionNew) {
                if (!preciosComprasCollectionOld.contains(preciosComprasCollectionNewPreciosCompras)) {
                    Empresas oldCodEmpOfPreciosComprasCollectionNewPreciosCompras = preciosComprasCollectionNewPreciosCompras.getCodEmp();
                    preciosComprasCollectionNewPreciosCompras.setCodEmp(empresas);
                    preciosComprasCollectionNewPreciosCompras = em.merge(preciosComprasCollectionNewPreciosCompras);
                    if (oldCodEmpOfPreciosComprasCollectionNewPreciosCompras != null && !oldCodEmpOfPreciosComprasCollectionNewPreciosCompras.equals(empresas)) {
                        oldCodEmpOfPreciosComprasCollectionNewPreciosCompras.getPreciosComprasCollection().remove(preciosComprasCollectionNewPreciosCompras);
                        oldCodEmpOfPreciosComprasCollectionNewPreciosCompras = em.merge(oldCodEmpOfPreciosComprasCollectionNewPreciosCompras);
                    }
                }
            }
            for (Contadores contadoresCollectionNewContadores : contadoresCollectionNew) {
                if (!contadoresCollectionOld.contains(contadoresCollectionNewContadores)) {
                    Empresas oldCodEmpOfContadoresCollectionNewContadores = contadoresCollectionNewContadores.getCodEmp();
                    contadoresCollectionNewContadores.setCodEmp(empresas);
                    contadoresCollectionNewContadores = em.merge(contadoresCollectionNewContadores);
                    if (oldCodEmpOfContadoresCollectionNewContadores != null && !oldCodEmpOfContadoresCollectionNewContadores.equals(empresas)) {
                        oldCodEmpOfContadoresCollectionNewContadores.getContadoresCollection().remove(contadoresCollectionNewContadores);
                        oldCodEmpOfContadoresCollectionNewContadores = em.merge(oldCodEmpOfContadoresCollectionNewContadores);
                    }
                }
            }
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp : cfProvPedCustomEmpCollectionNew) {
                if (!cfProvPedCustomEmpCollectionOld.contains(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp)) {
                    Empresas oldCodEmpOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp = cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp.getCodEmp();
                    cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp.setCodEmp(empresas);
                    cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp = em.merge(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp);
                    if (oldCodEmpOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp != null && !oldCodEmpOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp.equals(empresas)) {
                        oldCodEmpOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp.getCfProvPedCustomEmpCollection().remove(cfProvPedCustomEmpCollectionNewCfProvPedCustomEmp);
                        oldCodEmpOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp = em.merge(oldCodEmpOfCfProvPedCustomEmpCollectionNewCfProvPedCustomEmp);
                    }
                }
            }
            for (Clientes clientesCollectionNewClientes : clientesCollectionNew) {
                if (!clientesCollectionOld.contains(clientesCollectionNewClientes)) {
                    Empresas oldCodigoEmpOfClientesCollectionNewClientes = clientesCollectionNewClientes.getCodigoEmp();
                    clientesCollectionNewClientes.setCodigoEmp(empresas);
                    clientesCollectionNewClientes = em.merge(clientesCollectionNewClientes);
                    if (oldCodigoEmpOfClientesCollectionNewClientes != null && !oldCodigoEmpOfClientesCollectionNewClientes.equals(empresas)) {
                        oldCodigoEmpOfClientesCollectionNewClientes.getClientesCollection().remove(clientesCollectionNewClientes);
                        oldCodigoEmpOfClientesCollectionNewClientes = em.merge(oldCodigoEmpOfClientesCollectionNewClientes);
                    }
                }
            }
            for (CfCliAlbCustomEmp cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp : cfCliAlbCustomEmpCollectionNew) {
                if (!cfCliAlbCustomEmpCollectionOld.contains(cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp)) {
                    Empresas oldCodEmpOfCfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp = cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp.getCodEmp();
                    cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp.setCodEmp(empresas);
                    cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp = em.merge(cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp);
                    if (oldCodEmpOfCfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp != null && !oldCodEmpOfCfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp.equals(empresas)) {
                        oldCodEmpOfCfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp.getCfCliAlbCustomEmpCollection().remove(cfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp);
                        oldCodEmpOfCfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp = em.merge(oldCodEmpOfCfCliAlbCustomEmpCollectionNewCfCliAlbCustomEmp);
                    }
                }
            }
            for (ProvFacCab provFacCabCollectionNewProvFacCab : provFacCabCollectionNew) {
                if (!provFacCabCollectionOld.contains(provFacCabCollectionNewProvFacCab)) {
                    Empresas oldCodEmpOfProvFacCabCollectionNewProvFacCab = provFacCabCollectionNewProvFacCab.getCodEmp();
                    provFacCabCollectionNewProvFacCab.setCodEmp(empresas);
                    provFacCabCollectionNewProvFacCab = em.merge(provFacCabCollectionNewProvFacCab);
                    if (oldCodEmpOfProvFacCabCollectionNewProvFacCab != null && !oldCodEmpOfProvFacCabCollectionNewProvFacCab.equals(empresas)) {
                        oldCodEmpOfProvFacCabCollectionNewProvFacCab.getProvFacCabCollection().remove(provFacCabCollectionNewProvFacCab);
                        oldCodEmpOfProvFacCabCollectionNewProvFacCab = em.merge(oldCodEmpOfProvFacCabCollectionNewProvFacCab);
                    }
                }
            }
            for (CfCliFacCustomEmp cfCliFacCustomEmpCollectionNewCfCliFacCustomEmp : cfCliFacCustomEmpCollectionNew) {
                if (!cfCliFacCustomEmpCollectionOld.contains(cfCliFacCustomEmpCollectionNewCfCliFacCustomEmp)) {
                    Empresas oldCodEmpOfCfCliFacCustomEmpCollectionNewCfCliFacCustomEmp = cfCliFacCustomEmpCollectionNewCfCliFacCustomEmp.getCodEmp();
                    cfCliFacCustomEmpCollectionNewCfCliFacCustomEmp.setCodEmp(empresas);
                    cfCliFacCustomEmpCollectionNewCfCliFacCustomEmp = em.merge(cfCliFacCustomEmpCollectionNewCfCliFacCustomEmp);
                    if (oldCodEmpOfCfCliFacCustomEmpCollectionNewCfCliFacCustomEmp != null && !oldCodEmpOfCfCliFacCustomEmpCollectionNewCfCliFacCustomEmp.equals(empresas)) {
                        oldCodEmpOfCfCliFacCustomEmpCollectionNewCfCliFacCustomEmp.getCfCliFacCustomEmpCollection().remove(cfCliFacCustomEmpCollectionNewCfCliFacCustomEmp);
                        oldCodEmpOfCfCliFacCustomEmpCollectionNewCfCliFacCustomEmp = em.merge(oldCodEmpOfCfCliFacCustomEmpCollectionNewCfCliFacCustomEmp);
                    }
                }
            }
            for (ProvAlbCab provAlbCabCollectionNewProvAlbCab : provAlbCabCollectionNew) {
                if (!provAlbCabCollectionOld.contains(provAlbCabCollectionNewProvAlbCab)) {
                    Empresas oldCodEmpOfProvAlbCabCollectionNewProvAlbCab = provAlbCabCollectionNewProvAlbCab.getCodEmp();
                    provAlbCabCollectionNewProvAlbCab.setCodEmp(empresas);
                    provAlbCabCollectionNewProvAlbCab = em.merge(provAlbCabCollectionNewProvAlbCab);
                    if (oldCodEmpOfProvAlbCabCollectionNewProvAlbCab != null && !oldCodEmpOfProvAlbCabCollectionNewProvAlbCab.equals(empresas)) {
                        oldCodEmpOfProvAlbCabCollectionNewProvAlbCab.getProvAlbCabCollection().remove(provAlbCabCollectionNewProvAlbCab);
                        oldCodEmpOfProvAlbCabCollectionNewProvAlbCab = em.merge(oldCodEmpOfProvAlbCabCollectionNewProvAlbCab);
                    }
                }
            }
            for (Articulos articulosCollectionNewArticulos : articulosCollectionNew) {
                if (!articulosCollectionOld.contains(articulosCollectionNewArticulos)) {
                    Empresas oldCodEmpOfArticulosCollectionNewArticulos = articulosCollectionNewArticulos.getCodEmp();
                    articulosCollectionNewArticulos.setCodEmp(empresas);
                    articulosCollectionNewArticulos = em.merge(articulosCollectionNewArticulos);
                    if (oldCodEmpOfArticulosCollectionNewArticulos != null && !oldCodEmpOfArticulosCollectionNewArticulos.equals(empresas)) {
                        oldCodEmpOfArticulosCollectionNewArticulos.getArticulosCollection().remove(articulosCollectionNewArticulos);
                        oldCodEmpOfArticulosCollectionNewArticulos = em.merge(oldCodEmpOfArticulosCollectionNewArticulos);
                    }
                }
            }
            for (CliFacCab cliFacCabCollectionNewCliFacCab : cliFacCabCollectionNew) {
                if (!cliFacCabCollectionOld.contains(cliFacCabCollectionNewCliFacCab)) {
                    Empresas oldCodEmpOfCliFacCabCollectionNewCliFacCab = cliFacCabCollectionNewCliFacCab.getCodEmp();
                    cliFacCabCollectionNewCliFacCab.setCodEmp(empresas);
                    cliFacCabCollectionNewCliFacCab = em.merge(cliFacCabCollectionNewCliFacCab);
                    if (oldCodEmpOfCliFacCabCollectionNewCliFacCab != null && !oldCodEmpOfCliFacCabCollectionNewCliFacCab.equals(empresas)) {
                        oldCodEmpOfCliFacCabCollectionNewCliFacCab.getCliFacCabCollection().remove(cliFacCabCollectionNewCliFacCab);
                        oldCodEmpOfCliFacCabCollectionNewCliFacCab = em.merge(oldCodEmpOfCliFacCabCollectionNewCliFacCab);
                    }
                }
            }
            for (Promociones promocionesCollectionNewPromociones : promocionesCollectionNew) {
                if (!promocionesCollectionOld.contains(promocionesCollectionNewPromociones)) {
                    Empresas oldCodEmpOfPromocionesCollectionNewPromociones = promocionesCollectionNewPromociones.getCodEmp();
                    promocionesCollectionNewPromociones.setCodEmp(empresas);
                    promocionesCollectionNewPromociones = em.merge(promocionesCollectionNewPromociones);
                    if (oldCodEmpOfPromocionesCollectionNewPromociones != null && !oldCodEmpOfPromocionesCollectionNewPromociones.equals(empresas)) {
                        oldCodEmpOfPromocionesCollectionNewPromociones.getPromocionesCollection().remove(promocionesCollectionNewPromociones);
                        oldCodEmpOfPromocionesCollectionNewPromociones = em.merge(oldCodEmpOfPromocionesCollectionNewPromociones);
                    }
                }
            }
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp : cfProvAlbCustomEmpCollectionNew) {
                if (!cfProvAlbCustomEmpCollectionOld.contains(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp)) {
                    Empresas oldCodEmpOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp = cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp.getCodEmp();
                    cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp.setCodEmp(empresas);
                    cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp = em.merge(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp);
                    if (oldCodEmpOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp != null && !oldCodEmpOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp.equals(empresas)) {
                        oldCodEmpOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp.getCfProvAlbCustomEmpCollection().remove(cfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp);
                        oldCodEmpOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp = em.merge(oldCodEmpOfCfProvAlbCustomEmpCollectionNewCfProvAlbCustomEmp);
                    }
                }
            }
            for (Almacenes almacenesCollectionNewAlmacenes : almacenesCollectionNew) {
                if (!almacenesCollectionOld.contains(almacenesCollectionNewAlmacenes)) {
                    Empresas oldCodEmpOfAlmacenesCollectionNewAlmacenes = almacenesCollectionNewAlmacenes.getCodEmp();
                    almacenesCollectionNewAlmacenes.setCodEmp(empresas);
                    almacenesCollectionNewAlmacenes = em.merge(almacenesCollectionNewAlmacenes);
                    if (oldCodEmpOfAlmacenesCollectionNewAlmacenes != null && !oldCodEmpOfAlmacenesCollectionNewAlmacenes.equals(empresas)) {
                        oldCodEmpOfAlmacenesCollectionNewAlmacenes.getAlmacenesCollection().remove(almacenesCollectionNewAlmacenes);
                        oldCodEmpOfAlmacenesCollectionNewAlmacenes = em.merge(oldCodEmpOfAlmacenesCollectionNewAlmacenes);
                    }
                }
            }
            for (CfDpCustomEmp cfDpCustomEmpCollectionNewCfDpCustomEmp : cfDpCustomEmpCollectionNew) {
                if (!cfDpCustomEmpCollectionOld.contains(cfDpCustomEmpCollectionNewCfDpCustomEmp)) {
                    Empresas oldCodEmpOfCfDpCustomEmpCollectionNewCfDpCustomEmp = cfDpCustomEmpCollectionNewCfDpCustomEmp.getCodEmp();
                    cfDpCustomEmpCollectionNewCfDpCustomEmp.setCodEmp(empresas);
                    cfDpCustomEmpCollectionNewCfDpCustomEmp = em.merge(cfDpCustomEmpCollectionNewCfDpCustomEmp);
                    if (oldCodEmpOfCfDpCustomEmpCollectionNewCfDpCustomEmp != null && !oldCodEmpOfCfDpCustomEmpCollectionNewCfDpCustomEmp.equals(empresas)) {
                        oldCodEmpOfCfDpCustomEmpCollectionNewCfDpCustomEmp.getCfDpCustomEmpCollection().remove(cfDpCustomEmpCollectionNewCfDpCustomEmp);
                        oldCodEmpOfCfDpCustomEmpCollectionNewCfDpCustomEmp = em.merge(oldCodEmpOfCfDpCustomEmpCollectionNewCfDpCustomEmp);
                    }
                }
            }
            for (CfCliPedCustomEmp cfCliPedCustomEmpCollectionNewCfCliPedCustomEmp : cfCliPedCustomEmpCollectionNew) {
                if (!cfCliPedCustomEmpCollectionOld.contains(cfCliPedCustomEmpCollectionNewCfCliPedCustomEmp)) {
                    Empresas oldCodEmpOfCfCliPedCustomEmpCollectionNewCfCliPedCustomEmp = cfCliPedCustomEmpCollectionNewCfCliPedCustomEmp.getCodEmp();
                    cfCliPedCustomEmpCollectionNewCfCliPedCustomEmp.setCodEmp(empresas);
                    cfCliPedCustomEmpCollectionNewCfCliPedCustomEmp = em.merge(cfCliPedCustomEmpCollectionNewCfCliPedCustomEmp);
                    if (oldCodEmpOfCfCliPedCustomEmpCollectionNewCfCliPedCustomEmp != null && !oldCodEmpOfCfCliPedCustomEmpCollectionNewCfCliPedCustomEmp.equals(empresas)) {
                        oldCodEmpOfCfCliPedCustomEmpCollectionNewCfCliPedCustomEmp.getCfCliPedCustomEmpCollection().remove(cfCliPedCustomEmpCollectionNewCfCliPedCustomEmp);
                        oldCodEmpOfCfCliPedCustomEmpCollectionNewCfCliPedCustomEmp = em.merge(oldCodEmpOfCfCliPedCustomEmpCollectionNewCfCliPedCustomEmp);
                    }
                }
            }
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp : cfProvFacCustomEmpCollectionNew) {
                if (!cfProvFacCustomEmpCollectionOld.contains(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp)) {
                    Empresas oldCodEmpOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp = cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp.getCodEmp();
                    cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp.setCodEmp(empresas);
                    cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp = em.merge(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp);
                    if (oldCodEmpOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp != null && !oldCodEmpOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp.equals(empresas)) {
                        oldCodEmpOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp.getCfProvFacCustomEmpCollection().remove(cfProvFacCustomEmpCollectionNewCfProvFacCustomEmp);
                        oldCodEmpOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp = em.merge(oldCodEmpOfCfProvFacCustomEmpCollectionNewCfProvFacCustomEmp);
                    }
                }
            }
            for (Proveedores proveedoresCollectionNewProveedores : proveedoresCollectionNew) {
                if (!proveedoresCollectionOld.contains(proveedoresCollectionNewProveedores)) {
                    Empresas oldCodigoEmpOfProveedoresCollectionNewProveedores = proveedoresCollectionNewProveedores.getCodigoEmp();
                    proveedoresCollectionNewProveedores.setCodigoEmp(empresas);
                    proveedoresCollectionNewProveedores = em.merge(proveedoresCollectionNewProveedores);
                    if (oldCodigoEmpOfProveedoresCollectionNewProveedores != null && !oldCodigoEmpOfProveedoresCollectionNewProveedores.equals(empresas)) {
                        oldCodigoEmpOfProveedoresCollectionNewProveedores.getProveedoresCollection().remove(proveedoresCollectionNewProveedores);
                        oldCodigoEmpOfProveedoresCollectionNewProveedores = em.merge(oldCodigoEmpOfProveedoresCollectionNewProveedores);
                    }
                }
            }
            for (PreciosVentas preciosVentasCollectionNewPreciosVentas : preciosVentasCollectionNew) {
                if (!preciosVentasCollectionOld.contains(preciosVentasCollectionNewPreciosVentas)) {
                    Empresas oldCodEmpOfPreciosVentasCollectionNewPreciosVentas = preciosVentasCollectionNewPreciosVentas.getCodEmp();
                    preciosVentasCollectionNewPreciosVentas.setCodEmp(empresas);
                    preciosVentasCollectionNewPreciosVentas = em.merge(preciosVentasCollectionNewPreciosVentas);
                    if (oldCodEmpOfPreciosVentasCollectionNewPreciosVentas != null && !oldCodEmpOfPreciosVentasCollectionNewPreciosVentas.equals(empresas)) {
                        oldCodEmpOfPreciosVentasCollectionNewPreciosVentas.getPreciosVentasCollection().remove(preciosVentasCollectionNewPreciosVentas);
                        oldCodEmpOfPreciosVentasCollectionNewPreciosVentas = em.merge(oldCodEmpOfPreciosVentasCollectionNewPreciosVentas);
                    }
                }
            }
            for (CliPedidosCab cliPedidosCabCollectionNewCliPedidosCab : cliPedidosCabCollectionNew) {
                if (!cliPedidosCabCollectionOld.contains(cliPedidosCabCollectionNewCliPedidosCab)) {
                    Empresas oldCodEmpOfCliPedidosCabCollectionNewCliPedidosCab = cliPedidosCabCollectionNewCliPedidosCab.getCodEmp();
                    cliPedidosCabCollectionNewCliPedidosCab.setCodEmp(empresas);
                    cliPedidosCabCollectionNewCliPedidosCab = em.merge(cliPedidosCabCollectionNewCliPedidosCab);
                    if (oldCodEmpOfCliPedidosCabCollectionNewCliPedidosCab != null && !oldCodEmpOfCliPedidosCabCollectionNewCliPedidosCab.equals(empresas)) {
                        oldCodEmpOfCliPedidosCabCollectionNewCliPedidosCab.getCliPedidosCabCollection().remove(cliPedidosCabCollectionNewCliPedidosCab);
                        oldCodEmpOfCliPedidosCabCollectionNewCliPedidosCab = em.merge(oldCodEmpOfCliPedidosCabCollectionNewCliPedidosCab);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empresas.getCodigo();
                if (findEmpresas(id) == null) {
                    throw new NonexistentEntityException("The empresas with id " + id + " no longer exists.");
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
            Empresas empresas;
            try {
                empresas = em.getReference(Empresas.class, id);
                empresas.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CfProdCustomEmp> cfProdCustomEmpCollectionOrphanCheck = empresas.getCfProdCustomEmpCollection();
            for (CfProdCustomEmp cfProdCustomEmpCollectionOrphanCheckCfProdCustomEmp : cfProdCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CfProdCustomEmp " + cfProdCustomEmpCollectionOrphanCheckCfProdCustomEmp + " in its cfProdCustomEmpCollection field has a non-nullable codEmp field.");
            }
            Collection<ProvPedidosCab> provPedidosCabCollectionOrphanCheck = empresas.getProvPedidosCabCollection();
            for (ProvPedidosCab provPedidosCabCollectionOrphanCheckProvPedidosCab : provPedidosCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the ProvPedidosCab " + provPedidosCabCollectionOrphanCheckProvPedidosCab + " in its provPedidosCabCollection field has a non-nullable codEmp field.");
            }
            Collection<CliAlbCab> cliAlbCabCollectionOrphanCheck = empresas.getCliAlbCabCollection();
            for (CliAlbCab cliAlbCabCollectionOrphanCheckCliAlbCab : cliAlbCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CliAlbCab " + cliAlbCabCollectionOrphanCheckCliAlbCab + " in its cliAlbCabCollection field has a non-nullable codEmp field.");
            }
            Collection<Stock> stockCollectionOrphanCheck = empresas.getStockCollection();
            for (Stock stockCollectionOrphanCheckStock : stockCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the Stock " + stockCollectionOrphanCheckStock + " in its stockCollection field has a non-nullable codEmp field.");
            }
            Collection<PreciosCompras> preciosComprasCollectionOrphanCheck = empresas.getPreciosComprasCollection();
            for (PreciosCompras preciosComprasCollectionOrphanCheckPreciosCompras : preciosComprasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the PreciosCompras " + preciosComprasCollectionOrphanCheckPreciosCompras + " in its preciosComprasCollection field has a non-nullable codEmp field.");
            }
            Collection<Contadores> contadoresCollectionOrphanCheck = empresas.getContadoresCollection();
            for (Contadores contadoresCollectionOrphanCheckContadores : contadoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the Contadores " + contadoresCollectionOrphanCheckContadores + " in its contadoresCollection field has a non-nullable codEmp field.");
            }
            Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollectionOrphanCheck = empresas.getCfProvPedCustomEmpCollection();
            for (CfProvPedCustomEmp cfProvPedCustomEmpCollectionOrphanCheckCfProvPedCustomEmp : cfProvPedCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CfProvPedCustomEmp " + cfProvPedCustomEmpCollectionOrphanCheckCfProvPedCustomEmp + " in its cfProvPedCustomEmpCollection field has a non-nullable codEmp field.");
            }
            Collection<Clientes> clientesCollectionOrphanCheck = empresas.getClientesCollection();
            for (Clientes clientesCollectionOrphanCheckClientes : clientesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the Clientes " + clientesCollectionOrphanCheckClientes + " in its clientesCollection field has a non-nullable codigoEmp field.");
            }
            Collection<CfCliAlbCustomEmp> cfCliAlbCustomEmpCollectionOrphanCheck = empresas.getCfCliAlbCustomEmpCollection();
            for (CfCliAlbCustomEmp cfCliAlbCustomEmpCollectionOrphanCheckCfCliAlbCustomEmp : cfCliAlbCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CfCliAlbCustomEmp " + cfCliAlbCustomEmpCollectionOrphanCheckCfCliAlbCustomEmp + " in its cfCliAlbCustomEmpCollection field has a non-nullable codEmp field.");
            }
            Collection<ProvFacCab> provFacCabCollectionOrphanCheck = empresas.getProvFacCabCollection();
            for (ProvFacCab provFacCabCollectionOrphanCheckProvFacCab : provFacCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the ProvFacCab " + provFacCabCollectionOrphanCheckProvFacCab + " in its provFacCabCollection field has a non-nullable codEmp field.");
            }
            Collection<CfCliFacCustomEmp> cfCliFacCustomEmpCollectionOrphanCheck = empresas.getCfCliFacCustomEmpCollection();
            for (CfCliFacCustomEmp cfCliFacCustomEmpCollectionOrphanCheckCfCliFacCustomEmp : cfCliFacCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CfCliFacCustomEmp " + cfCliFacCustomEmpCollectionOrphanCheckCfCliFacCustomEmp + " in its cfCliFacCustomEmpCollection field has a non-nullable codEmp field.");
            }
            Collection<ProvAlbCab> provAlbCabCollectionOrphanCheck = empresas.getProvAlbCabCollection();
            for (ProvAlbCab provAlbCabCollectionOrphanCheckProvAlbCab : provAlbCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the ProvAlbCab " + provAlbCabCollectionOrphanCheckProvAlbCab + " in its provAlbCabCollection field has a non-nullable codEmp field.");
            }
            Collection<Articulos> articulosCollectionOrphanCheck = empresas.getArticulosCollection();
            for (Articulos articulosCollectionOrphanCheckArticulos : articulosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the Articulos " + articulosCollectionOrphanCheckArticulos + " in its articulosCollection field has a non-nullable codEmp field.");
            }
            Collection<CliFacCab> cliFacCabCollectionOrphanCheck = empresas.getCliFacCabCollection();
            for (CliFacCab cliFacCabCollectionOrphanCheckCliFacCab : cliFacCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CliFacCab " + cliFacCabCollectionOrphanCheckCliFacCab + " in its cliFacCabCollection field has a non-nullable codEmp field.");
            }
            Collection<Promociones> promocionesCollectionOrphanCheck = empresas.getPromocionesCollection();
            for (Promociones promocionesCollectionOrphanCheckPromociones : promocionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the Promociones " + promocionesCollectionOrphanCheckPromociones + " in its promocionesCollection field has a non-nullable codEmp field.");
            }
            Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollectionOrphanCheck = empresas.getCfProvAlbCustomEmpCollection();
            for (CfProvAlbCustomEmp cfProvAlbCustomEmpCollectionOrphanCheckCfProvAlbCustomEmp : cfProvAlbCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CfProvAlbCustomEmp " + cfProvAlbCustomEmpCollectionOrphanCheckCfProvAlbCustomEmp + " in its cfProvAlbCustomEmpCollection field has a non-nullable codEmp field.");
            }
            Collection<Almacenes> almacenesCollectionOrphanCheck = empresas.getAlmacenesCollection();
            for (Almacenes almacenesCollectionOrphanCheckAlmacenes : almacenesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the Almacenes " + almacenesCollectionOrphanCheckAlmacenes + " in its almacenesCollection field has a non-nullable codEmp field.");
            }
            Collection<CfDpCustomEmp> cfDpCustomEmpCollectionOrphanCheck = empresas.getCfDpCustomEmpCollection();
            for (CfDpCustomEmp cfDpCustomEmpCollectionOrphanCheckCfDpCustomEmp : cfDpCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CfDpCustomEmp " + cfDpCustomEmpCollectionOrphanCheckCfDpCustomEmp + " in its cfDpCustomEmpCollection field has a non-nullable codEmp field.");
            }
            Collection<CfCliPedCustomEmp> cfCliPedCustomEmpCollectionOrphanCheck = empresas.getCfCliPedCustomEmpCollection();
            for (CfCliPedCustomEmp cfCliPedCustomEmpCollectionOrphanCheckCfCliPedCustomEmp : cfCliPedCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CfCliPedCustomEmp " + cfCliPedCustomEmpCollectionOrphanCheckCfCliPedCustomEmp + " in its cfCliPedCustomEmpCollection field has a non-nullable codEmp field.");
            }
            Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollectionOrphanCheck = empresas.getCfProvFacCustomEmpCollection();
            for (CfProvFacCustomEmp cfProvFacCustomEmpCollectionOrphanCheckCfProvFacCustomEmp : cfProvFacCustomEmpCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CfProvFacCustomEmp " + cfProvFacCustomEmpCollectionOrphanCheckCfProvFacCustomEmp + " in its cfProvFacCustomEmpCollection field has a non-nullable codEmp field.");
            }
            Collection<Proveedores> proveedoresCollectionOrphanCheck = empresas.getProveedoresCollection();
            for (Proveedores proveedoresCollectionOrphanCheckProveedores : proveedoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the Proveedores " + proveedoresCollectionOrphanCheckProveedores + " in its proveedoresCollection field has a non-nullable codigoEmp field.");
            }
            Collection<PreciosVentas> preciosVentasCollectionOrphanCheck = empresas.getPreciosVentasCollection();
            for (PreciosVentas preciosVentasCollectionOrphanCheckPreciosVentas : preciosVentasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the PreciosVentas " + preciosVentasCollectionOrphanCheckPreciosVentas + " in its preciosVentasCollection field has a non-nullable codEmp field.");
            }
            Collection<CliPedidosCab> cliPedidosCabCollectionOrphanCheck = empresas.getCliPedidosCabCollection();
            for (CliPedidosCab cliPedidosCabCollectionOrphanCheckCliPedidosCab : cliPedidosCabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the CliPedidosCab " + cliPedidosCabCollectionOrphanCheckCliPedidosCab + " in its cliPedidosCabCollection field has a non-nullable codEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Grupos> gruposCollection = empresas.getGruposCollection();
            for (Grupos gruposCollectionGrupos : gruposCollection) {
                gruposCollectionGrupos.getEmpresasCollection().remove(empresas);
                gruposCollectionGrupos = em.merge(gruposCollectionGrupos);
            }
            Collection<Usuarios> usuariosCollection = empresas.getUsuariosCollection();
            for (Usuarios usuariosCollectionUsuarios : usuariosCollection) {
                usuariosCollectionUsuarios.getEmpresasCollection().remove(empresas);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            em.remove(empresas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresas> findEmpresasEntities() {
        return findEmpresasEntities(true, -1, -1);
    }

    public List<Empresas> findEmpresasEntities(int maxResults, int firstResult) {
        return findEmpresasEntities(false, maxResults, firstResult);
    }

    private List<Empresas> findEmpresasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Empresas as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Empresas findEmpresas(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresas.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresasCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Empresas as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
