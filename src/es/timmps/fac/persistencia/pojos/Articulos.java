/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "articulos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a"),
    @NamedQuery(name = "Articulos.findByCodigo", query = "SELECT a FROM Articulos a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "Articulos.findByNombre", query = "SELECT a FROM Articulos a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Articulos.findByDescripcion", query = "SELECT a FROM Articulos a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Articulos.findByPrecioDef", query = "SELECT a FROM Articulos a WHERE a.precioDef = :precioDef"),
    @NamedQuery(name = "Articulos.findByImagen", query = "SELECT a FROM Articulos a WHERE a.imagen = :imagen")})
public class Articulos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "PRECIO_DEF")
    private double precioDef;
    @Basic(optional = false)
    @Column(name = "IMAGEN")
    private String imagen;
    @OneToMany(mappedBy = "codProd")
    private Collection<ProvFacLin> provFacLinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProd")
    private Collection<Stock> stockCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProducto")
    private Collection<PreciosCompras> preciosComprasCollection;
    @OneToMany(mappedBy = "codProd")
    private Collection<ProvPedidosLin> provPedidosLinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProd")
    private Collection<ProvAlbLin> provAlbLinCollection;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProd")
    private Collection<Promociones> promocionesCollection;
    @OneToMany(mappedBy = "codProd")
    private Collection<CliPedidosLin> cliPedidosLinCollection;
    @OneToMany(mappedBy = "codProd")
    private Collection<CliAlbLin> cliAlbLinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articulos")
    private Collection<ArticulosCustom> articulosCustomCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProducto")
    private Collection<PreciosVentas> preciosVentasCollection;
    @OneToMany(mappedBy = "codProd")
    private Collection<CliFacLin> cliFacLinCollection;

    public Articulos() {
    }

    public Articulos(String codigo) {
        this.codigo = codigo;
    }

    public Articulos(String codigo, String nombre, String descripcion, double precioDef, String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioDef = precioDef;
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioDef() {
        return precioDef;
    }

    public void setPrecioDef(double precioDef) {
        this.precioDef = precioDef;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @XmlTransient
    public Collection<ProvFacLin> getProvFacLinCollection() {
        return provFacLinCollection;
    }

    public void setProvFacLinCollection(Collection<ProvFacLin> provFacLinCollection) {
        this.provFacLinCollection = provFacLinCollection;
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
    public Collection<ProvPedidosLin> getProvPedidosLinCollection() {
        return provPedidosLinCollection;
    }

    public void setProvPedidosLinCollection(Collection<ProvPedidosLin> provPedidosLinCollection) {
        this.provPedidosLinCollection = provPedidosLinCollection;
    }

    @XmlTransient
    public Collection<ProvAlbLin> getProvAlbLinCollection() {
        return provAlbLinCollection;
    }

    public void setProvAlbLinCollection(Collection<ProvAlbLin> provAlbLinCollection) {
        this.provAlbLinCollection = provAlbLinCollection;
    }

    public Empresas getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Empresas codEmp) {
        this.codEmp = codEmp;
    }

    @XmlTransient
    public Collection<Promociones> getPromocionesCollection() {
        return promocionesCollection;
    }

    public void setPromocionesCollection(Collection<Promociones> promocionesCollection) {
        this.promocionesCollection = promocionesCollection;
    }

    @XmlTransient
    public Collection<CliPedidosLin> getCliPedidosLinCollection() {
        return cliPedidosLinCollection;
    }

    public void setCliPedidosLinCollection(Collection<CliPedidosLin> cliPedidosLinCollection) {
        this.cliPedidosLinCollection = cliPedidosLinCollection;
    }

    @XmlTransient
    public Collection<CliAlbLin> getCliAlbLinCollection() {
        return cliAlbLinCollection;
    }

    public void setCliAlbLinCollection(Collection<CliAlbLin> cliAlbLinCollection) {
        this.cliAlbLinCollection = cliAlbLinCollection;
    }

    @XmlTransient
    public Collection<ArticulosCustom> getArticulosCustomCollection() {
        return articulosCustomCollection;
    }

    public void setArticulosCustomCollection(Collection<ArticulosCustom> articulosCustomCollection) {
        this.articulosCustomCollection = articulosCustomCollection;
    }

    @XmlTransient
    public Collection<PreciosVentas> getPreciosVentasCollection() {
        return preciosVentasCollection;
    }

    public void setPreciosVentasCollection(Collection<PreciosVentas> preciosVentasCollection) {
        this.preciosVentasCollection = preciosVentasCollection;
    }

    @XmlTransient
    public Collection<CliFacLin> getCliFacLinCollection() {
        return cliFacLinCollection;
    }

    public void setCliFacLinCollection(Collection<CliFacLin> cliFacLinCollection) {
        this.cliFacLinCollection = cliFacLinCollection;
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
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.Articulos[ codigo=" + codigo + " ]";
    }
    
}
