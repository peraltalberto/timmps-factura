/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

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
@Table(name = "cf_tipo_valores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfTipoValores.findAll", query = "SELECT c FROM CfTipoValores c"),
    @NamedQuery(name = "CfTipoValores.findById", query = "SELECT c FROM CfTipoValores c WHERE c.id = :id"),
    @NamedQuery(name = "CfTipoValores.findByNombre", query = "SELECT c FROM CfTipoValores c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CfTipoValores.findByDescripcion", query = "SELECT c FROM CfTipoValores c WHERE c.descripcion = :descripcion")})
public class CfTipoValores implements Serializable {
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
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoValor")
    private Collection<CfProdCustomEmp> cfProdCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoValor")
    private Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDato")
    private Collection<CfGlobal> cfGlobalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoValor")
    private Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoValor")
    private Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollection;

    public CfTipoValores() {
    }

    public CfTipoValores(Integer id) {
        this.id = id;
    }

    public CfTipoValores(Integer id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<CfProdCustomEmp> getCfProdCustomEmpCollection() {
        return cfProdCustomEmpCollection;
    }

    public void setCfProdCustomEmpCollection(Collection<CfProdCustomEmp> cfProdCustomEmpCollection) {
        this.cfProdCustomEmpCollection = cfProdCustomEmpCollection;
    }

    @XmlTransient
    public Collection<CfProvPedCustomEmp> getCfProvPedCustomEmpCollection() {
        return cfProvPedCustomEmpCollection;
    }

    public void setCfProvPedCustomEmpCollection(Collection<CfProvPedCustomEmp> cfProvPedCustomEmpCollection) {
        this.cfProvPedCustomEmpCollection = cfProvPedCustomEmpCollection;
    }

    @XmlTransient
    public Collection<CfGlobal> getCfGlobalCollection() {
        return cfGlobalCollection;
    }

    public void setCfGlobalCollection(Collection<CfGlobal> cfGlobalCollection) {
        this.cfGlobalCollection = cfGlobalCollection;
    }

    @XmlTransient
    public Collection<CfProvAlbCustomEmp> getCfProvAlbCustomEmpCollection() {
        return cfProvAlbCustomEmpCollection;
    }

    public void setCfProvAlbCustomEmpCollection(Collection<CfProvAlbCustomEmp> cfProvAlbCustomEmpCollection) {
        this.cfProvAlbCustomEmpCollection = cfProvAlbCustomEmpCollection;
    }

    @XmlTransient
    public Collection<CfProvFacCustomEmp> getCfProvFacCustomEmpCollection() {
        return cfProvFacCustomEmpCollection;
    }

    public void setCfProvFacCustomEmpCollection(Collection<CfProvFacCustomEmp> cfProvFacCustomEmpCollection) {
        this.cfProvFacCustomEmpCollection = cfProvFacCustomEmpCollection;
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
        if (!(object instanceof CfTipoValores)) {
            return false;
        }
        CfTipoValores other = (CfTipoValores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.CfTipoValores[ id=" + id + " ]";
    }
    
}
