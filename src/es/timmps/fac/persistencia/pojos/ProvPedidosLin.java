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
@Table(name = "prov_pedidos_lin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvPedidosLin.findAll", query = "SELECT p FROM ProvPedidosLin p"),
    @NamedQuery(name = "ProvPedidosLin.findById", query = "SELECT p FROM ProvPedidosLin p WHERE p.id = :id"),
    @NamedQuery(name = "ProvPedidosLin.findByUnidades", query = "SELECT p FROM ProvPedidosLin p WHERE p.unidades = :unidades"),
    @NamedQuery(name = "ProvPedidosLin.findByPrecio", query = "SELECT p FROM ProvPedidosLin p WHERE p.precio = :precio"),
    @NamedQuery(name = "ProvPedidosLin.findByDescripcion", query = "SELECT p FROM ProvPedidosLin p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "ProvPedidosLin.findByBaseImp", query = "SELECT p FROM ProvPedidosLin p WHERE p.baseImp = :baseImp"),
    @NamedQuery(name = "ProvPedidosLin.findByIva", query = "SELECT p FROM ProvPedidosLin p WHERE p.iva = :iva"),
    @NamedQuery(name = "ProvPedidosLin.findByImporte", query = "SELECT p FROM ProvPedidosLin p WHERE p.importe = :importe")})
public class ProvPedidosLin implements Serializable {
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
    private ProvPedidosCab codCab;
    @JoinColumn(name = "TIPO_IVA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CfTipoImpuesto tipoIva;

    public ProvPedidosLin() {
    }

    public ProvPedidosLin(Integer id) {
        this.id = id;
    }

    public ProvPedidosLin(Integer id, int unidades, double precio, String descripcion, double baseImp, double iva, double importe) {
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

    public ProvPedidosCab getCodCab() {
        return codCab;
    }

    public void setCodCab(ProvPedidosCab codCab) {
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
        if (!(object instanceof ProvPedidosLin)) {
            return false;
        }
        ProvPedidosLin other = (ProvPedidosLin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.ProvPedidosLin[ id=" + id + " ]";
    }
    
}
