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
@Table(name = "cli_pedidos_lin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CliPedidosLin.findAll", query = "SELECT c FROM CliPedidosLin c"),
    @NamedQuery(name = "CliPedidosLin.findById", query = "SELECT c FROM CliPedidosLin c WHERE c.id = :id"),
    @NamedQuery(name = "CliPedidosLin.findByUnidades", query = "SELECT c FROM CliPedidosLin c WHERE c.unidades = :unidades"),
    @NamedQuery(name = "CliPedidosLin.findByPrecio", query = "SELECT c FROM CliPedidosLin c WHERE c.precio = :precio"),
    @NamedQuery(name = "CliPedidosLin.findByDescripcion", query = "SELECT c FROM CliPedidosLin c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CliPedidosLin.findByBaseImp", query = "SELECT c FROM CliPedidosLin c WHERE c.baseImp = :baseImp"),
    @NamedQuery(name = "CliPedidosLin.findByIva", query = "SELECT c FROM CliPedidosLin c WHERE c.iva = :iva"),
    @NamedQuery(name = "CliPedidosLin.findByImporte", query = "SELECT c FROM CliPedidosLin c WHERE c.importe = :importe")})
public class CliPedidosLin implements Serializable {
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
    private CliPedidosCab codCab;
    @JoinColumn(name = "TIPO_IVA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CfTipoImpuesto tipoIva;

    public CliPedidosLin() {
    }

    public CliPedidosLin(Integer id) {
        this.id = id;
    }

    public CliPedidosLin(Integer id, int unidades, double precio, String descripcion, double baseImp, double iva, double importe) {
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

    public CliPedidosCab getCodCab() {
        return codCab;
    }

    public void setCodCab(CliPedidosCab codCab) {
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
        if (!(object instanceof CliPedidosLin)) {
            return false;
        }
        CliPedidosLin other = (CliPedidosLin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.CliPedidosLin[ id=" + id + " ]";
    }
    
}
