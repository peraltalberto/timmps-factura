/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "empresas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresas.findAll", query = "SELECT e FROM Empresas e"),
    @NamedQuery(name = "Empresas.findByCodigo", query = "SELECT e FROM Empresas e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Empresas.findByCodigoPersona", query = "SELECT e FROM Empresas e WHERE e.codigoPersona = :codigoPersona")})
public class Empresas implements Serializable {
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<Sessiones> sessionesCollection;
    @Basic(optional = false)
    @Column(name = "ACTIVO")
    private int activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresas")
    private Collection<EmpGrupo> empGrupoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresas")
    private Collection<EmpUsu> empUsuCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "CODIGO_PERSONA")
    private int codigoPersona;
    @JoinTable(name = "emp_grupo", joinColumns = {
        @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_GRUPO", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Grupos> gruposCollection;
    @JoinTable(name = "emp_usu", joinColumns = {
        @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")}, inverseJoinColumns = {
        @JoinColumn(name = "USU", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Usuarios> usuariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CfProdCustomEmp> cfProdCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<ProvPedidosCab> provPedidosCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CliAlbCab> cliAlbCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<Stock> stockCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<PreciosCompras> preciosComprasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<Contadores> contadoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoEmp")
    private Collection<Clientes> clientesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CfCliAlbCustomEmp> cfCliAlbCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<ProvFacCab> provFacCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CfCliFacCustomEmp> cfCliFacCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<ProvAlbCab> provAlbCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<Articulos> articulosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CliFacCab> cliFacCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<Promociones> promocionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<Almacenes> almacenesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CfDpCustomEmp> cfDpCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CfCliPedCustomEmp> cfCliPedCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoEmp")
    private Collection<Proveedores> proveedoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<PreciosVentas> preciosVentasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmp")
    private Collection<CliPedidosCab> cliPedidosCabCollection;

    public Empresas() {
    }

    public Empresas(String codigo) {
        this.codigo = codigo;
    }

    public Empresas(String codigo, int codigoPersona) {
        this.codigo = codigo;
        this.codigoPersona = codigoPersona;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(int codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    @XmlTransient
    public Collection<Grupos> getGruposCollection() {
        return gruposCollection;
    }

    public void setGruposCollection(Collection<Grupos> gruposCollection) {
        this.gruposCollection = gruposCollection;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @XmlTransient
    public Collection<CfProdCustomEmp> getCfProdCustomEmpCollection() {
        return cfProdCustomEmpCollection;
    }

    public void setCfProdCustomEmpCollection(Collection<CfProdCustomEmp> cfProdCustomEmpCollection) {
        this.cfProdCustomEmpCollection = cfProdCustomEmpCollection;
    }

    @XmlTransient
    public Collection<ProvPedidosCab> getProvPedidosCabCollection() {
        return provPedidosCabCollection;
    }

    public void setProvPedidosCabCollection(Collection<ProvPedidosCab> provPedidosCabCollection) {
        this.provPedidosCabCollection = provPedidosCabCollection;
    }

    @XmlTransient
    public Collection<CliAlbCab> getCliAlbCabCollection() {
        return cliAlbCabCollection;
    }

    public void setCliAlbCabCollection(Collection<CliAlbCab> cliAlbCabCollection) {
        this.cliAlbCabCollection = cliAlbCabCollection;
    }

    @XmlTransient
    public Collection<Stock> getStockCollection() {
        return stockCollection;
    }

    public void setStockCollection(Collection<Stock> stockCollection) {
        this.stockCollection = stockCollection;
    }

    @XmlTransient
    public Collection<PreciosCompras> getPreciosComprasCollection() {
        return preciosComprasCollection;
    }

    public void setPreciosComprasCollection(Collection<PreciosCompras> preciosComprasCollection) {
        this.preciosComprasCollection = preciosComprasCollection;
    }

    @XmlTransient
    public Collection<Contadores> getContadoresCollection() {
        return contadoresCollection;
    }

    public void setContadoresCollection(Collection<Contadores> contadoresCollection) {
        this.contadoresCollection = contadoresCollection;
    }

    @XmlTransient
    public Collection<CfProvPedCustomEmp> getCfProvPedCustomEmpCollection() {
        return cfProvPedCustomEmpCollection;
    }

    public void setCfProvPedCustomEmpCollection(Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollection) {
        this.cfProvPedCustomEmpCollection = cfProvPedCustomEmpCollection;
    }

    @XmlTransient
    public Collection<Clientes> getClientesCollection() {
        return clientesCollection;
    }

    public void setClientesCollection(Collection<Clientes> clientesCollection) {
        this.clientesCollection = clientesCollection;
    }

    @XmlTransient
    public Collection<CfCliAlbCustomEmp> getCfCliAlbCustomEmpCollection() {
        return cfCliAlbCustomEmpCollection;
    }

    public void setCfCliAlbCustomEmpCollection(Collection<CfCliAlbCustomEmp> cfCliAlbCustomEmpCollection) {
        this.cfCliAlbCustomEmpCollection = cfCliAlbCustomEmpCollection;
    }

    @XmlTransient
    public Collection<ProvFacCab> getProvFacCabCollection() {
        return provFacCabCollection;
    }

    public void setProvFacCabCollection(Collection<ProvFacCab> provFacCabCollection) {
        this.provFacCabCollection = provFacCabCollection;
    }

    @XmlTransient
    public Collection<CfCliFacCustomEmp> getCfCliFacCustomEmpCollection() {
        return cfCliFacCustomEmpCollection;
    }

    public void setCfCliFacCustomEmpCollection(Collection<CfCliFacCustomEmp> cfCliFacCustomEmpCollection) {
        this.cfCliFacCustomEmpCollection = cfCliFacCustomEmpCollection;
    }

    @XmlTransient
    public Collection<ProvAlbCab> getProvAlbCabCollection() {
        return provAlbCabCollection;
    }

    public void setProvAlbCabCollection(Collection<ProvAlbCab> provAlbCabCollection) {
        this.provAlbCabCollection = provAlbCabCollection;
    }

    @XmlTransient
    public Collection<Articulos> getArticulosCollection() {
        return articulosCollection;
    }

    public void setArticulosCollection(Collection<Articulos> articulosCollection) {
        this.articulosCollection = articulosCollection;
    }

    @XmlTransient
    public Collection<CliFacCab> getCliFacCabCollection() {
        return cliFacCabCollection;
    }

    public void setCliFacCabCollection(Collection<CliFacCab> cliFacCabCollection) {
        this.cliFacCabCollection = cliFacCabCollection;
    }

    @XmlTransient
    public Collection<Promociones> getPromocionesCollection() {
        return promocionesCollection;
    }

    public void setPromocionesCollection(Collection<Promociones> promocionesCollection) {
        this.promocionesCollection = promocionesCollection;
    }

    @XmlTransient
    public Collection<CfProvAlbCustomEmp> getCfProvAlbCustomEmpCollection() {
        return cfProvAlbCustomEmpCollection;
    }

    public void setCfProvAlbCustomEmpCollection(Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollection) {
        this.cfProvAlbCustomEmpCollection = cfProvAlbCustomEmpCollection;
    }

    @XmlTransient
    public Collection<Almacenes> getAlmacenesCollection() {
        return almacenesCollection;
    }

    public void setAlmacenesCollection(Collection<Almacenes> almacenesCollection) {
        this.almacenesCollection = almacenesCollection;
    }

    @XmlTransient
    public Collection<CfDpCustomEmp> getCfDpCustomEmpCollection() {
        return cfDpCustomEmpCollection;
    }

    public void setCfDpCustomEmpCollection(Collection<CfDpCustomEmp> cfDpCustomEmpCollection) {
        this.cfDpCustomEmpCollection = cfDpCustomEmpCollection;
    }

    @XmlTransient
    public Collection<CfCliPedCustomEmp> getCfCliPedCustomEmpCollection() {
        return cfCliPedCustomEmpCollection;
    }

    public void setCfCliPedCustomEmpCollection(Collection<CfCliPedCustomEmp> cfCliPedCustomEmpCollection) {
        this.cfCliPedCustomEmpCollection = cfCliPedCustomEmpCollection;
    }

    @XmlTransient
    public Collection<CfProvFacCustomEmp> getCfProvFacCustomEmpCollection() {
        return cfProvFacCustomEmpCollection;
    }

    public void setCfProvFacCustomEmpCollection(Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollection) {
        this.cfProvFacCustomEmpCollection = cfProvFacCustomEmpCollection;
    }

    @XmlTransient
    public Collection<Proveedores> getProveedoresCollection() {
        return proveedoresCollection;
    }

    public void setProveedoresCollection(Collection<Proveedores> proveedoresCollection) {
        this.proveedoresCollection = proveedoresCollection;
    }

    @XmlTransient
    public Collection<PreciosVentas> getPreciosVentasCollection() {
        return preciosVentasCollection;
    }

    public void setPreciosVentasCollection(Collection<PreciosVentas> preciosVentasCollection) {
        this.preciosVentasCollection = preciosVentasCollection;
    }

    @XmlTransient
    public Collection<CliPedidosCab> getCliPedidosCabCollection() {
        return cliPedidosCabCollection;
    }

    public void setCliPedidosCabCollection(Collection<CliPedidosCab> cliPedidosCabCollection) {
        this.cliPedidosCabCollection = cliPedidosCabCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresas)) {
            return false;
        }
        Empresas other = (Empresas) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  codigo;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    @XmlTransient
    public Collection<EmpGrupo> getEmpGrupoCollection() {
        return empGrupoCollection;
    }

    public void setEmpGrupoCollection(Collection<EmpGrupo> empGrupoCollection) {
        this.empGrupoCollection = empGrupoCollection;
    }

    @XmlTransient
    public Collection<EmpUsu> getEmpUsuCollection() {
        return empUsuCollection;
    }

    public void setEmpUsuCollection(Collection<EmpUsu> empUsuCollection) {
        this.empUsuCollection = empUsuCollection;
    }

    @XmlTransient
    public Collection<Sessiones> getSessionesCollection() {
        return sessionesCollection;
    }

    public void setSessionesCollection(Collection<Sessiones> sessionesCollection) {
        this.sessionesCollection = sessionesCollection;
    }
    
}
