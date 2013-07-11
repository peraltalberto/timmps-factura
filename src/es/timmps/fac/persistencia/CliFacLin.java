/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

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
@Table(name = "cli_fac_lin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CliFacLin.findAll", query = "SELECT c FROM CliFacLin c"),
    @NamedQuery(name = "CliFacLin.findById", query = "SELECT c FROM CliFacLin c WHERE c.id = :id"),
    @NamedQuery(name = "CliFacLin.findByUnidades", query = "SELECT c FROM CliFacLin c WHERE c.unidades = :unidades"),
    @NamedQuery(name = "CliFacLin.findByPrecio", query = "SELECT c FROM CliFacLin c WHERE c.precio = :precio"),
    @NamedQuery(name = "CliFacLin.findByDescripcion", query = "SELECT c FROM CliFacLin c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CliFacLin.findByBaseImp", query = "SELECT c FROM CliFacLin c WHERE c.baseImp = :baseImp"),
    @NamedQuery(name = "CliFacLin.findByIva", query = "SELECT c FROM CliFacLin c WHERE c.iva = :iva"),
    @NamedQuery(name = "CliFacLin.findByImporte", query = "SELECT c FROM CliFacLin c WHERE c.importe = :importe")})
public class CliFacLin implements Serializable {
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
    @JoinColumn(name = "COD_CAB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CliFacCab codCab;
    @JoinColumn(name = "COD_PROD", referencedColumnName = "CODIGO")
    @ManyToOne
    private Articulos codProd;
    @JoinColumn(name = "TIPO_IVA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CfTipoImpuesto tipoIva;

    public CliFacLin() {
    }

    public CliFacLin(Integer id) {
        this.id = id;
    }

    public CliFacLin(Integer id, int unidades, double precio, String descripcion, double baseImp, double iva, double importe) {
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

    public CliFacCab getCodCab() {
        return codCab;
    }

    public void setCodCab(CliFacCab codCab) {
        this.codCab = codCab;
    }

    public Articulos getCodProd() {
        return codProd;
    }

    public void setCodProd(Articulos codProd) {
        this.codProd = codProd;
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
        if (!(object instanceof CliFacLin)) {
            return false;
        }
        CliFacLin other = (CliFacLin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.CliFacLin[ id=" + id + " ]";
    }
    
}
