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
@Table(name = "proveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p"),
    @NamedQuery(name = "Proveedores.findByCodigo", query = "SELECT p FROM Proveedores p WHERE p.codigo = :codigo")})
public class Proveedores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProv")
    private Collection<ProvPedidosCab> provPedidosCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProv")
    private Collection<PreciosCompras> preciosComprasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProv")
    private Collection<ProvFacCab> provFacCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProv")
    private Collection<ProvAlbCab> provAlbCabCollection;
    @JoinColumn(name = "CODIGO_PERSONA", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private DatosPersonales codigoPersona;
    @JoinColumn(name = "CODIGO_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codigoEmp;

    public Proveedores() {
    }

    public Proveedores(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<ProvPedidosCab> getProvPedidosCabCollection() {
        return provPedidosCabCollection;
    }

    public void setProvPedidosCabCollection(Collection<ProvPedidosCab> provPedidosCabCollection) {
        this.provPedidosCabCollection = provPedidosCabCollection;
    }

    @XmlTransient
    public Collection<PreciosCompras> getPreciosComprasCollection() {
        return preciosComprasCollection;
    }

    public void setPreciosComprasCollection(Collection<PreciosCompras> preciosComprasCollection) {
        this.preciosComprasCollection = preciosComprasCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.Proveedores[ codigo=" + codigo + " ]";
    }
    
}
