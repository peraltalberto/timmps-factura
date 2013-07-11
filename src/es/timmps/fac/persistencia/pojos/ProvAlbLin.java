/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "prov_alb_lin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvAlbLin.findAll", query = "SELECT p FROM ProvAlbLin p"),
    @NamedQuery(name = "ProvAlbLin.findById", query = "SELECT p FROM ProvAlbLin p WHERE p.id = :id"),
    @NamedQuery(name = "ProvAlbLin.findByUnidades", query = "SELECT p FROM ProvAlbLin p WHERE p.unidades = :unidades"),
    @NamedQuery(name = "ProvAlbLin.findByPrecio", query = "SELECT p FROM ProvAlbLin p WHERE p.precio = :precio"),
    @NamedQuery(name = "ProvAlbLin.findByDescripcion", query = "SELECT p FROM ProvAlbLin p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "ProvAlbLin.findByBaseImp", query = "SELECT p FROM ProvAlbLin p WHERE p.baseImp = :baseImp"),
    @NamedQuery(name = "ProvAlbLin.findByIva", query = "SELECT p FROM ProvAlbLin p WHERE p.iva = :iva"),
    @NamedQuery(name = "ProvAlbLin.findByImporte", query = "SELECT p FROM ProvAlbLin p WHERE p.importe = :importe")})
public class ProvAlbLin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "UNIDADES")
    private int unidades;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private double precio;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "BASE_IMP")
    private double baseImp;
    @Basic(optional = false)
    @Column(name = "IVA")
    private double iva;
    @Basic(optional = false)
    @Column(name = "IMPORTE")
    private double importe;
    @OneToMany(mappedBy = "codAlbLinProv")
    private Collection<Stock> stockCollection;
    @JoinColumn(name = "COD_PROD", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Articulos codProd;
    @JoinColumn(name = "COD_CAB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ProvAlbCab codCab;
    @JoinColumn(name = "TIPO_IVA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CfTipoImpuesto tipoIva;

    public ProvAlbLin() {
    }

    public ProvAlbLin(Integer id) {
        this.id = id;
    }

    public ProvAlbLin(Integer id, int unidades, double precio, String descripcion, double baseImp, double iva, double importe) {
        this.id = id;
        this.unidades = unidades;
        this.precio = precio;
        this.descripcion = descripcion;
        this.baseImp = baseImp;
        this.iva = iva;
        this.importe = importe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getBaseImp() {
        return baseImp;
    }

    public void setBaseImp(double baseImp) {
        this.baseImp = baseImp;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    @XmlTransient
    public Collection<Stock> getStockCollection() {
        return stockCollection;
    }

    public void setStockCollection(Collection<Stock> stockCollection) {
        this.stockCollection = stockCollection;
    }

    public Articulos getCodProd() {
        return codProd;
    }

    public void setCodProd(Articulos codProd) {
        this.codProd = codProd;
    }

    public ProvAlbCab getCodCab() {
        return codCab;
    }

    public void setCodCab(ProvAlbCab codCab) {
        this.codCab = codCab;
    }

    public CfTipoImpuesto getTipoIva() {
        return tipoIva;
    }

    public void setTipoIva(CfTipoImpuesto tipoIva) {
        this.tipoIva = tipoIva;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvAlbLin)) {
            return false;
        }
        ProvAlbLin other = (ProvAlbLin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.ProvAlbLin[ id=" + id + " ]";
    }
    
}
