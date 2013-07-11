/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

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
@Table(name = "prov_fac_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvFacCab.findAll", query = "SELECT p FROM ProvFacCab p"),
    @NamedQuery(name = "ProvFacCab.findById", query = "SELECT p FROM ProvFacCab p WHERE p.id = :id"),
    @NamedQuery(name = "ProvFacCab.findByCodigo", query = "SELECT p FROM ProvFacCab p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "ProvFacCab.findByFechaExp", query = "SELECT p FROM ProvFacCab p WHERE p.fechaExp = :fechaExp"),
    @NamedQuery(name = "ProvFacCab.findByFechaVal", query = "SELECT p FROM ProvFacCab p WHERE p.fechaVal = :fechaVal"),
    @NamedQuery(name = "ProvFacCab.findByImporte", query = "SELECT p FROM ProvFacCab p WHERE p.importe = :importe"),
    @NamedQuery(name = "ProvFacCab.findByBase", query = "SELECT p FROM ProvFacCab p WHERE p.base = :base"),
    @NamedQuery(name = "ProvFacCab.findByIva", query = "SELECT p FROM ProvFacCab p WHERE p.iva = :iva"),
    @NamedQuery(name = "ProvFacCab.findByImpreso", query = "SELECT p FROM ProvFacCab p WHERE p.impreso = :impreso"),
    @NamedQuery(name = "ProvFacCab.findByServido", query = "SELECT p FROM ProvFacCab p WHERE p.servido = :servido"),
    @NamedQuery(name = "ProvFacCab.findByComentarios", query = "SELECT p FROM ProvFacCab p WHERE p.comentarios = :comentarios")})
public class ProvFacCab implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCab")
    private Collection<ProvFacLin> provFacLinCollection;
    @JoinColumn(name = "COD_PROV", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Proveedores codProv;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;
    @JoinColumn(name = "COD_ALB", referencedColumnName = "ID")
    @ManyToOne
    private ProvAlbCab codAlb;
    @JoinColumn(name = "COD_PEDIDO", referencedColumnName = "ID")
    @ManyToOne
    private ProvPedidosCab codPedido;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provFacCab")
    private Collection<ProvFacCustom> provFacCustomCollection;

    public ProvFacCab() {
    }

    public ProvFacCab(Integer id) {
        this.id = id;
    }

    public ProvFacCab(Integer id, String codigo, Date fechaExp, double importe, double base, double iva, int impreso, int servido, String comentarios) {
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
    public Collection<ProvFacLin> getProvFacLinCollection() {
        return provFacLinCollection;
    }

    public void setProvFacLinCollection(Collection<ProvFacLin> provFacLinCollection) {
        this.provFacLinCollection = provFacLinCollection;
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

    public ProvAlbCab getCodAlb() {
        return codAlb;
    }

    public void setCodAlb(ProvAlbCab codAlb) {
        this.codAlb = codAlb;
    }

    public ProvPedidosCab getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(ProvPedidosCab codPedido) {
        this.codPedido = codPedido;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<ProvFacCustom> getProvFacCustomCollection() {
        return provFacCustomCollection;
    }

    public void setProvFacCustomCollection(Collection<ProvFacCustom> provFacCustomCollection) {
        this.provFacCustomCollection = provFacCustomCollection;
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
        if (!(object instanceof ProvFacCab)) {
            return false;
        }
        ProvFacCab other = (ProvFacCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.ProvFacCab[ id=" + id + " ]";
    }
    
}
