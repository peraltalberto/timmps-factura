/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

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
@Table(name = "precios_compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreciosCompras.findAll", query = "SELECT p FROM PreciosCompras p"),
    @NamedQuery(name = "PreciosCompras.findById", query = "SELECT p FROM PreciosCompras p WHERE p.id = :id"),
    @NamedQuery(name = "PreciosCompras.findByFechaInicio", query = "SELECT p FROM PreciosCompras p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "PreciosCompras.findByFechaFin", query = "SELECT p FROM PreciosCompras p WHERE p.fechaFin = :fechaFin"),
    @NamedQuery(name = "PreciosCompras.findByActivo", query = "SELECT p FROM PreciosCompras p WHERE p.activo = :activo"),
    @NamedQuery(name = "PreciosCompras.findByPrecio", query = "SELECT p FROM PreciosCompras p WHERE p.precio = :precio")})
public class PreciosCompras implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Basic(optional = false)
    @Column(name = "ACTIVO")
    private int activo;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private double precio;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;
    @JoinColumn(name = "COD_PRODUCTO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Articulos codProducto;
    @JoinColumn(name = "COD_PROV", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Proveedores codProv;

    public PreciosCompras() {
    }

    public PreciosCompras(Integer id) {
        this.id = id;
    }

    public PreciosCompras(Integer id, Date fechaInicio, int activo, double precio) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.activo = activo;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Empresas getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Empresas codEmp) {
        this.codEmp = codEmp;
    }

    public Articulos getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Articulos codProducto) {
        this.codProducto = codProducto;
    }

    public Proveedores getCodProv() {
        return codProv;
    }

    public void setCodProv(Proveedores codProv) {
        this.codProv = codProv;
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
        if (!(object instanceof PreciosCompras)) {
            return false;
        }
        PreciosCompras other = (PreciosCompras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.PreciosCompras[ id=" + id + " ]";
    }
    
}
