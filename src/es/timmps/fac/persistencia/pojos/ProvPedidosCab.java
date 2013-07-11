/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "prov_pedidos_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvPedidosCab.findAll", query = "SELECT p FROM ProvPedidosCab p"),
    @NamedQuery(name = "ProvPedidosCab.findById", query = "SELECT p FROM ProvPedidosCab p WHERE p.id = :id"),
    @NamedQuery(name = "ProvPedidosCab.findByCodigo", query = "SELECT p FROM ProvPedidosCab p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "ProvPedidosCab.findByFechaExp", query = "SELECT p FROM ProvPedidosCab p WHERE p.fechaExp = :fechaExp"),
    @NamedQuery(name = "ProvPedidosCab.findByFechaVal", query = "SELECT p FROM ProvPedidosCab p WHERE p.fechaVal = :fechaVal"),
    @NamedQuery(name = "ProvPedidosCab.findByImporte", query = "SELECT p FROM ProvPedidosCab p WHERE p.importe = :importe"),
    @NamedQuery(name = "ProvPedidosCab.findByBase", query = "SELECT p FROM ProvPedidosCab p WHERE p.base = :base"),
    @NamedQuery(name = "ProvPedidosCab.findByIva", query = "SELECT p FROM ProvPedidosCab p WHERE p.iva = :iva"),
    @NamedQuery(name = "ProvPedidosCab.findByImpreso", query = "SELECT p FROM ProvPedidosCab p WHERE p.impreso = :impreso"),
    @NamedQuery(name = "ProvPedidosCab.findByServido", query = "SELECT p FROM ProvPedidosCab p WHERE p.servido = :servido"),
    @NamedQuery(name = "ProvPedidosCab.findByComentarios", query = "SELECT p FROM ProvPedidosCab p WHERE p.comentarios = :comentarios")})
public class ProvPedidosCab implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "FECHA_EXP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExp;
    @Column(name = "FECHA_VAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVal;
    @Basic(optional = false)
    @Column(name = "IMPORTE")
    private double importe;
    @Basic(optional = false)
    @Column(name = "BASE")
    private double base;
    @Basic(optional = false)
    @Column(name = "IVA")
    private double iva;
    @Basic(optional = false)
    @Column(name = "IMPRESO")
    private int impreso;
    @Basic(optional = false)
    @Column(name = "SERVIDO")
    private int servido;
    @Basic(optional = false)
    @Column(name = "COMENTARIOS")
    private String comentarios;
    @JoinColumn(name = "COD_PROV", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Proveedores codProv;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provPedidosCab")
    private Collection<ProvPedidosCustom> provPedidosCustomCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCab")
    private Collection<ProvPedidosLin> provPedidosLinCollection;
    @OneToMany(mappedBy = "codPedido")
    private Collection<ProvFacCab> provFacCabCollection;
    @OneToMany(mappedBy = "codPedido")
    private Collection<ProvAlbCab> provAlbCabCollection;

    public ProvPedidosCab() {
    }

    public ProvPedidosCab(Integer id) {
        this.id = id;
    }

    public ProvPedidosCab(Integer id, String codigo, Date fechaExp, double importe, double base, double iva, int impreso, int servido, String comentarios) {
        this.id = id;
        this.codigo = codigo;
        this.fechaExp = fechaExp;
        this.importe = importe;
        this.base = base;
        this.iva = iva;
        this.impreso = impreso;
        this.servido = servido;
        this.comentarios = comentarios;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(Date fechaExp) {
        this.fechaExp = fechaExp;
    }

    public Date getFechaVal() {
        return fechaVal;
    }

    public void setFechaVal(Date fechaVal) {
        this.fechaVal = fechaVal;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public int getImpreso() {
        return impreso;
    }

    public void setImpreso(int impreso) {
        this.impreso = impreso;
    }

    public int getServido() {
        return servido;
    }

    public void setServido(int servido) {
        this.servido = servido;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Proveedores getCodProv() {
        return codProv;
    }

    public void setCodProv(Proveedores codProv) {
        this.codProv = codProv;
    }

    public Empresas getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Empresas codEmp) {
        this.codEmp = codEmp;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<ProvPedidosCustom> getProvPedidosCustomCollection() {
        return provPedidosCustomCollection;
    }

    public void setProvPedidosCustomCollection(Collection<ProvPedidosCustom> provPedidosCustomCollection) {
        this.provPedidosCustomCollection = provPedidosCustomCollection;
    }

    @XmlTransient
    public Collection<ProvPedidosLin> getProvPedidosLinCollection() {
        return provPedidosLinCollection;
    }

    public void setProvPedidosLinCollection(Collection<ProvPedidosLin> provPedidosLinCollection) {
        this.provPedidosLinCollection = provPedidosLinCollection;
    }

    @XmlTransient
    public Collection<ProvFacCab> getProvFacCabCollection() {
        return provFacCabCollection;
    }

    public void setProvFacCabCollection(Collection<ProvFacCab> provFacCabCollection) {
        this.provFacCabCollection = provFacCabCollection;
    }

    @XmlTransient
    public Collection<ProvAlbCab> getProvAlbCabCollection() {
        return provAlbCabCollection;
    }

    public void setProvAlbCabCollection(Collection<ProvAlbCab> provAlbCabCollection) {
        this.provAlbCabCollection = provAlbCabCollection;
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
        if (!(object instanceof ProvPedidosCab)) {
            return false;
        }
        ProvPedidosCab other = (ProvPedidosCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.ProvPedidosCab[ id=" + id + " ]";
    }
    
}
