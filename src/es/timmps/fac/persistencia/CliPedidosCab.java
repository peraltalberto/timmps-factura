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
@Table(name = "cli_pedidos_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CliPedidosCab.findAll", query = "SELECT c FROM CliPedidosCab c"),
    @NamedQuery(name = "CliPedidosCab.findById", query = "SELECT c FROM CliPedidosCab c WHERE c.id = :id"),
    @NamedQuery(name = "CliPedidosCab.findByCodigo", query = "SELECT c FROM CliPedidosCab c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CliPedidosCab.findByFechaExp", query = "SELECT c FROM CliPedidosCab c WHERE c.fechaExp = :fechaExp"),
    @NamedQuery(name = "CliPedidosCab.findByFechaVal", query = "SELECT c FROM CliPedidosCab c WHERE c.fechaVal = :fechaVal"),
    @NamedQuery(name = "CliPedidosCab.findByImporte", query = "SELECT c FROM CliPedidosCab c WHERE c.importe = :importe"),
    @NamedQuery(name = "CliPedidosCab.findByBase", query = "SELECT c FROM CliPedidosCab c WHERE c.base = :base"),
    @NamedQuery(name = "CliPedidosCab.findByIva", query = "SELECT c FROM CliPedidosCab c WHERE c.iva = :iva"),
    @NamedQuery(name = "CliPedidosCab.findByImpreso", query = "SELECT c FROM CliPedidosCab c WHERE c.impreso = :impreso"),
    @NamedQuery(name = "CliPedidosCab.findByServido", query = "SELECT c FROM CliPedidosCab c WHERE c.servido = :servido"),
    @NamedQuery(name = "CliPedidosCab.findByComentarios", query = "SELECT c FROM CliPedidosCab c WHERE c.comentarios = :comentarios")})
public class CliPedidosCab implements Serializable {
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
    @OneToMany(mappedBy = "codPedido")
    private Collection<CliAlbCab> cliAlbCabCollection;
    @OneToMany(mappedBy = "codPedido")
    private Collection<CliFacCab> cliFacCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliPedidosCab")
    private Collection<CliPedidosCustom> cliPedidosCustomCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCab")
    private Collection<CliPedidosLin> cliPedidosLinCollection;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @JoinColumn(name = "COD_CLI", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Clientes codCli;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;

    public CliPedidosCab() {
    }

    public CliPedidosCab(Integer id) {
        this.id = id;
    }

    public CliPedidosCab(Integer id, String codigo, Date fechaExp, double importe, double base, double iva, int impreso, int servido, String comentarios) {
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
    public Collection<CliAlbCab> getCliAlbCabCollection() {
        return cliAlbCabCollection;
    }

    public void setCliAlbCabCollection(Collection<CliAlbCab> cliAlbCabCollection) {
        this.cliAlbCabCollection = cliAlbCabCollection;
    }

    @XmlTransient
    public Collection<CliFacCab> getCliFacCabCollection() {
        return cliFacCabCollection;
    }

    public void setCliFacCabCollection(Collection<CliFacCab> cliFacCabCollection) {
        this.cliFacCabCollection = cliFacCabCollection;
    }

    @XmlTransient
    public Collection<CliPedidosCustom> getCliPedidosCustomCollection() {
        return cliPedidosCustomCollection;
    }

    public void setCliPedidosCustomCollection(Collection<CliPedidosCustom> cliPedidosCustomCollection) {
        this.cliPedidosCustomCollection = cliPedidosCustomCollection;
    }

    @XmlTransient
    public Collection<CliPedidosLin> getCliPedidosLinCollection() {
        return cliPedidosLinCollection;
    }

    public void setCliPedidosLinCollection(Collection<CliPedidosLin> cliPedidosLinCollection) {
        this.cliPedidosLinCollection = cliPedidosLinCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CliPedidosCab)) {
            return false;
        }
        CliPedidosCab other = (CliPedidosCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.CliPedidosCab[ id=" + id + " ]";
    }
    
}
