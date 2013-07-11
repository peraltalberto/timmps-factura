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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "cf_tipo_impuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfTipoImpuesto.findAll", query = "SELECT c FROM CfTipoImpuesto c"),
    @NamedQuery(name = "CfTipoImpuesto.findById", query = "SELECT c FROM CfTipoImpuesto c WHERE c.id = :id"),
    @NamedQuery(name = "CfTipoImpuesto.findByNombre", query = "SELECT c FROM CfTipoImpuesto c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CfTipoImpuesto.findByValor", query = "SELECT c FROM CfTipoImpuesto c WHERE c.valor = :valor")})
public class CfTipoImpuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private double valor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoIva")
    private Collection<ProvFacLin> provFacLinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoIva")
    private Collection<ProvPedidosLin> provPedidosLinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoIva")
    private Collection<ProvAlbLin> provAlbLinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoIva")
    private Collection<CliPedidosLin> cliPedidosLinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoIva")
    private Collection<CliAlbLin> cliAlbLinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoIva")
    private Collection<CliFacLin> cliFacLinCollection;

    public CfTipoImpuesto() {
    }

    public CfTipoImpuesto(Integer id) {
        this.id = id;
    }

    public CfTipoImpuesto(Integer id, String nombre, double valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @XmlTransient
    public Collection<ProvFacLin> getProvFacLinCollection() {
        return provFacLinCollection;
    }

    public void setProvFacLinCollection(Collection<ProvFacLin> provFacLinCollection) {
        this.provFacLinCollection = provFacLinCollection;
    }

    @XmlTransient
    public Collection<ProvPedidosLin> getProvPedidosLinCollection() {
        return provPedidosLinCollection;
    }

    public void setProvPedidosLinCollection(Collection<ProvPedidosLin> provPedidosLinCollection) {
        this.provPedidosLinCollection = provPedidosLinCollection;
    }

    @XmlTransient
    public Collection<ProvAlbLin> getProvAlbLinCollection() {
        return provAlbLinCollection;
    }

    public void setProvAlbLinCollection(Collection<ProvAlbLin> provAlbLinCollection) {
        this.provAlbLinCollection = provAlbLinCollection;
    }

    @XmlTransient
    public Collection<CliPedidosLin> getCliPedidosLinCollection() {
        return cliPedidosLinCollection;
    }

    public void setCliPedidosLinCollection(Collection<CliPedidosLin> cliPedidosLinCollection) {
        this.cliPedidosLinCollection = cliPedidosLinCollection;
    }

    @XmlTransient
    public Collection<CliAlbLin> getCliAlbLinCollection() {
        return cliAlbLinCollection;
    }

    public void setCliAlbLinCollection(Collection<CliAlbLin> cliAlbLinCollection) {
        this.cliAlbLinCollection = cliAlbLinCollection;
    }

    @XmlTransient
    public Collection<CliFacLin> getCliFacLinCollection() {
        return cliFacLinCollection;
    }

    public void setCliFacLinCollection(Collection<CliFacLin> cliFacLinCollection) {
        this.cliFacLinCollection = cliFacLinCollection;
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
        if (!(object instanceof CfTipoImpuesto)) {
            return false;
        }
        CfTipoImpuesto other = (CfTipoImpuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.CfTipoImpuesto[ id=" + id + " ]";
    }
    
}
