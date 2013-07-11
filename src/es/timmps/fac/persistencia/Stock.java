/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"),
    @NamedQuery(name = "Stock.findById", query = "SELECT s FROM Stock s WHERE s.id = :id"),
    @NamedQuery(name = "Stock.findByCantidad", query = "SELECT s FROM Stock s WHERE s.cantidad = :cantidad"),
    @NamedQuery(name = "Stock.findBySaldo", query = "SELECT s FROM Stock s WHERE s.saldo = :saldo"),
    @NamedQuery(name = "Stock.findByFecha", query = "SELECT s FROM Stock s WHERE s.fecha = :fecha")})
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private double cantidad;
    @Basic(optional = false)
    @Column(name = "SALDO")
    private double saldo;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "COD_ALB_LIN_CLI", referencedColumnName = "ID")
    @ManyToOne
    private CliAlbLin codAlbLinCli;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;
    @JoinColumn(name = "COD_PROD", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Articulos codProd;
    @JoinColumn(name = "COD_ALMACEN", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Almacenes codAlmacen;
    @JoinColumn(name = "COD_ALB_LIN_PROV", referencedColumnName = "ID")
    @ManyToOne
    private ProvAlbLin codAlbLinProv;

    public Stock() {
    }

    public Stock(Integer id) {
        this.id = id;
    }

    public Stock(Integer id, double cantidad, double saldo, Date fecha) {
        this.id = id;
        this.cantidad = cantidad;
        this.saldo = saldo;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public CliAlbLin getCodAlbLinCli() {
        return codAlbLinCli;
    }

    public void setCodAlbLinCli(CliAlbLin codAlbLinCli) {
        this.codAlbLinCli = codAlbLinCli;
    }

    public Empresas getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Empresas codEmp) {
        this.codEmp = codEmp;
    }

    public Articulos getCodProd() {
        return codProd;
    }

    public void setCodProd(Articulos codProd) {
        this.codProd = codProd;
    }

    public Almacenes getCodAlmacen() {
        return codAlmacen;
    }

    public void setCodAlmacen(Almacenes codAlmacen) {
        this.codAlmacen = codAlmacen;
    }

    public ProvAlbLin getCodAlbLinProv() {
        return codAlbLinProv;
    }

    public void setCodAlbLinProv(ProvAlbLin codAlbLinProv) {
        this.codAlbLinProv = codAlbLinProv;
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
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.Stock[ id=" + id + " ]";
    }
    
}
