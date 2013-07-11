/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findByCodigo", query = "SELECT c FROM Clientes c WHERE c.codigo = :codigo")})
public class Clientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCli")
    private Collection<CliAlbCab> cliAlbCabCollection;
    @JoinColumn(name = "CODIGO_PERSONA", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private DatosPersonales codigoPersona;
    @JoinColumn(name = "CODIGO_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codigoEmp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCli")
    private Collection<CliFacCab> cliFacCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCli")
    private Collection<PreciosVentas> preciosVentasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCli")
    private Collection<CliPedidosCab> cliPedidosCabCollection;

    public Clientes() {
    }

    public Clientes(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<CliAlbCab> getCliAlbCabCollection() {
        return cliAlbCabCollection;
    }

    public void setCliAlbCabCollection(Collection<CliAlbCab> cliAlbCabCollection) {
        this.cliAlbCabCollection = cliAlbCabCollection;
    }

    public DatosPersonales getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(DatosPersonales codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    public Empresas getCodigoEmp() {
        return codigoEmp;
    }

    public void setCodigoEmp(Empresas codigoEmp) {
        this.codigoEmp = codigoEmp;
    }

    @XmlTransient
    public Collection<CliFacCab> getCliFacCabCollection() {
        return cliFacCabCollection;
    }

    public void setCliFacCabCollection(Collection<CliFacCab> cliFacCabCollection) {
        this.cliFacCabCollection = cliFacCabCollection;
    }

    @XmlTransient
    public Collection<PreciosVentas> getPreciosVentasCollection() {
        return preciosVentasCollection;
    }

    public void setPreciosVentasCollection(Collection<PreciosVentas> preciosVentasCollection) {
        this.preciosVentasCollection = preciosVentasCollection;
    }

    @XmlTransient
    public Collection<CliPedidosCab> getCliPedidosCabCollection() {
        return cliPedidosCabCollection;
    }

    public void setCliPedidosCabCollection(Collection<CliPedidosCab> cliPedidosCabCollection) {
        this.cliPedidosCabCollection = cliPedidosCabCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.Clientes[ codigo=" + codigo + " ]";
    }
    
}
