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
@Table(name = "prov_alb_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvAlbCab.findAll", query = "SELECT p FROM ProvAlbCab p"),
    @NamedQuery(name = "ProvAlbCab.findById", query = "SELECT p FROM ProvAlbCab p WHERE p.id = :id"),
    @NamedQuery(name = "ProvAlbCab.findByCodigo", query = "SELECT p FROM ProvAlbCab p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "ProvAlbCab.findByFechaExp", query = "SELECT p FROM ProvAlbCab p WHERE p.fechaExp = :fechaExp"),
    @NamedQuery(name = "ProvAlbCab.findByFechaVal", query = "SELECT p FROM ProvAlbCab p WHERE p.fechaVal = :fechaVal"),
    @NamedQuery(name = "ProvAlbCab.findByImporte", query = "SELECT p FROM ProvAlbCab p WHERE p.importe = :importe"),
    @NamedQuery(name = "ProvAlbCab.findByBase", query = "SELECT p FROM ProvAlbCab p WHERE p.base = :base"),
    @NamedQuery(name = "ProvAlbCab.findByIva", query = "SELECT p FROM ProvAlbCab p WHERE p.iva = :iva"),
    @NamedQuery(name = "ProvAlbCab.findByImpreso", query = "SELECT p FROM ProvAlbCab p WHERE p.impreso = :impreso"),
    @NamedQuery(name = "ProvAlbCab.findByServido", query = "SELECT p FROM ProvAlbCab p WHERE p.servido = :servido"),
    @NamedQuery(name = "ProvAlbCab.findByComentarios", query = "SELECT p FROM ProvAlbCab p WHERE p.comentarios = :comentarios")})
public class ProvAlbCab implements Serializable {
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
    @OneToMany(mappedBy = "codAlb")
    private Collection<ProvFacCab> provFacCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCab")
    private Collection<ProvAlbLin> provAlbLinCollection;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @JoinColumn(name = "COD_PROV", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Proveedores codProv;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;
    @JoinColumn(name = "COD_PEDIDO", referencedColumnName = "ID")
    @ManyToOne
    private ProvPedidosCab codPedido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provAlbCab")
    private Collection<ProvAlbCustom> provAlbCustomCollection;

    public ProvAlbCab() {
    }

    public ProvAlbCab(Integer id) {
        this.id = id;
    }

    public ProvAlbCab(Integer id, String codigo, Date fechaExp, double importe, double base, double iva, int impreso, int servido, String comentarios) {
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

    @XmlTransient
    public Collection<ProvFacCab> getProvFacCabCollection() {
        return provFacCabCollection;
    }

    public void setProvFacCabCollection(Collection<ProvFacCab> provFacCabCollection) {
        this.provFacCabCollection = provFacCabCollection;
    }

    @XmlTransient
    public Collection<ProvAlbLin> getProvAlbLinCollection() {
        return provAlbLinCollection;
    }

    public void setProvAlbLinCollection(Collection<ProvAlbLin> provAlbLinCollection) {
        this.provAlbLinCollection = provAlbLinCollection;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
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

    public ProvPedidosCab getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(ProvPedidosCab codPedido) {
        this.codPedido = codPedido;
    }

    @XmlTransient
    public Collection<ProvAlbCustom> getProvAlbCustomCollection() {
        return provAlbCustomCollection;
    }

    public void setProvAlbCustomCollection(Collection<ProvAlbCustom> provAlbCustomCollection) {
        this.provAlbCustomCollection = provAlbCustomCollection;
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
        if (!(object instanceof ProvAlbCab)) {
            return false;
        }
        ProvAlbCab other = (ProvAlbCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.ProvAlbCab[ id=" + id + " ]";
    }
    
}
