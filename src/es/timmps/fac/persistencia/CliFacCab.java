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
@Table(name = "cli_fac_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CliFacCab.findAll", query = "SELECT c FROM CliFacCab c"),
    @NamedQuery(name = "CliFacCab.findById", query = "SELECT c FROM CliFacCab c WHERE c.id = :id"),
    @NamedQuery(name = "CliFacCab.findByCodigo", query = "SELECT c FROM CliFacCab c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CliFacCab.findByFechaExp", query = "SELECT c FROM CliFacCab c WHERE c.fechaExp = :fechaExp"),
    @NamedQuery(name = "CliFacCab.findByFechaVal", query = "SELECT c FROM CliFacCab c WHERE c.fechaVal = :fechaVal"),
    @NamedQuery(name = "CliFacCab.findByImporte", query = "SELECT c FROM CliFacCab c WHERE c.importe = :importe"),
    @NamedQuery(name = "CliFacCab.findByBase", query = "SELECT c FROM CliFacCab c WHERE c.base = :base"),
    @NamedQuery(name = "CliFacCab.findByIva", query = "SELECT c FROM CliFacCab c WHERE c.iva = :iva"),
    @NamedQuery(name = "CliFacCab.findByImpreso", query = "SELECT c FROM CliFacCab c WHERE c.impreso = :impreso"),
    @NamedQuery(name = "CliFacCab.findByServido", query = "SELECT c FROM CliFacCab c WHERE c.servido = :servido"),
    @NamedQuery(name = "CliFacCab.findByComentarios", query = "SELECT c FROM CliFacCab c WHERE c.comentarios = :comentarios")})
public class CliFacCab implements Serializable {
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
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @JoinColumn(name = "COD_CLI", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Clientes codCli;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;
    @JoinColumn(name = "COD_ALB", referencedColumnName = "ID")
    @ManyToOne
    private CliAlbCab codAlb;
    @JoinColumn(name = "COD_PEDIDO", referencedColumnName = "ID")
    @ManyToOne
    private CliPedidosCab codPedido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCab")
    private Collection<CliFacLin> cliFacLinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliFacCab")
    private Collection<CliFacCustom> cliFacCustomCollection;

    public CliFacCab() {
    }

    public CliFacCab(Integer id) {
        this.id = id;
    }

    public CliFacCab(Integer id, String codigo, Date fechaExp, double importe, double base, double iva, int impreso, int servido, String comentarios) {
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

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Clientes getCodCli() {
        return codCli;
    }

    public void setCodCli(Clientes codCli) {
        this.codCli = codCli;
    }

    public Empresas getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Empresas codEmp) {
        this.codEmp = codEmp;
    }

    public CliAlbCab getCodAlb() {
        return codAlb;
    }

    public void setCodAlb(CliAlbCab codAlb) {
        this.codAlb = codAlb;
    }

    public CliPedidosCab getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(CliPedidosCab codPedido) {
        this.codPedido = codPedido;
    }

    @XmlTransient
    public Collection<CliFacLin> getCliFacLinCollection() {
        return cliFacLinCollection;
    }

    public void setCliFacLinCollection(Collection<CliFacLin> cliFacLinCollection) {
        this.cliFacLinCollection = cliFacLinCollection;
    }

    @XmlTransient
    public Collection<CliFacCustom> getCliFacCustomCollection() {
        return cliFacCustomCollection;
    }

    public void setCliFacCustomCollection(Collection<CliFacCustom> cliFacCustomCollection) {
        this.cliFacCustomCollection = cliFacCustomCollection;
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
        if (!(object instanceof CliFacCab)) {
            return false;
        }
        CliFacCab other = (CliFacCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.CliFacCab[ id=" + id + " ]";
    }
    
}
