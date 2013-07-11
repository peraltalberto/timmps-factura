/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "prov_fac_lin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvFacLin.findAll", query = "SELECT p FROM ProvFacLin p"),
    @NamedQuery(name = "ProvFacLin.findById", query = "SELECT p FROM ProvFacLin p WHERE p.id = :id"),
    @NamedQuery(name = "ProvFacLin.findByUnidades", query = "SELECT p FROM ProvFacLin p WHERE p.unidades = :unidades"),
    @NamedQuery(name = "ProvFacLin.findByPrecio", query = "SELECT p FROM ProvFacLin p WHERE p.precio = :precio"),
    @NamedQuery(name = "ProvFacLin.findByDescripcion", query = "SELECT p FROM ProvFacLin p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "ProvFacLin.findByBaseImp", query = "SELECT p FROM ProvFacLin p WHERE p.baseImp = :baseImp"),
    @NamedQuery(name = "ProvFacLin.findByIva", query = "SELECT p FROM ProvFacLin p WHERE p.iva = :iva"),
    @NamedQuery(name = "ProvFacLin.findByImporte", query = "SELECT p FROM ProvFacLin p WHERE p.importe = :importe")})
public class ProvFacLin implements Serializable {
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
    @JoinColumn(name = "COD_PROD", referencedColumnName = "CODIGO")
    @ManyToOne
    private Articulos codProd;
    @JoinColumn(name = "COD_CAB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ProvFacCab codCab;
    @JoinColumn(name = "TIPO_IVA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CfTipoImpuesto tipoIva;

    public ProvFacLin() {
    }

    public ProvFacLin(Integer id) {
        this.id = id;
    }

    public ProvFacLin(Integer id, int unidades, double precio, String descripcion, double baseImp, double iva, double importe) {
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

    public Articulos getCodProd() {
        return codProd;
    }

    public void setCodProd(Articulos codProd) {
        this.codProd = codProd;
    }

    public ProvFacCab getCodCab() {
        return codCab;
    }

    public void setCodCab(ProvFacCab codCab) {
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
        if (!(object instanceof ProvFacLin)) {
            return false;
        }
        ProvFacLin other = (ProvFacLin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.ProvFacLin[ id=" + id + " ]";
    }
    
}
