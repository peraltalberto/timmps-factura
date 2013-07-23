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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findById", query = "SELECT u FROM Usuarios u WHERE u.id = :id"),
    @NamedQuery(name = "Usuarios.findByPassword", query = "SELECT u FROM Usuarios u WHERE u.password = :password")})
public class Usuarios implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Sessiones> sessionesCollection;
    @ManyToMany(mappedBy = "usuariosCollection")
    private Collection<Roles> rolesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<UsuGrupo> usuGrupoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<EmpUsu> empUsuCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "usuariosCollection")
    private Collection<Grupos> gruposCollection;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "usuariosCollection")
    private Collection<Empresas> empresasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<ProvPedidosCab> provPedidosCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<CliAlbCab> cliAlbCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<RegistroLog> registroLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<ProvFacCab> provFacCabCollection;
    @JoinColumn(name = "CODIGO_PERSONA", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private DatosPersonales codigoPersona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<ProvAlbCab> provAlbCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<CliFacCab> cliFacCabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<CliPedidosCab> cliPedidosCabCollection;

    public Usuarios() {
    }

    public Usuarios(String id) {
        this.id = id;
    }

    public Usuarios(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<Grupos> getGruposCollection() {
        return gruposCollection;
    }

    public void setGruposCollection(Collection<Grupos> gruposCollection) {
        this.gruposCollection = gruposCollection;
    }

    @XmlTransient
    public Collection<Empresas> getEmpresasCollection() {
        return empresasCollection;
    }

    public void setEmpresasCollection(Collection<Empresas> empresasCollection) {
        this.empresasCollection = empresasCollection;
    }

    @XmlTransient
    public Collection<ProvPedidosCab> getProvPedidosCabCollection() {
        return provPedidosCabCollection;
    }

    public void setProvPedidosCabCollection(Collection<ProvPedidosCab> provPedidosCabCollection) {
        this.provPedidosCabCollection = provPedidosCabCollection;
    }

    @XmlTransient
    public Collection<CliAlbCab> getCliAlbCabCollection() {
        return cliAlbCabCollection;
    }

    public void setCliAlbCabCollection(Collection<CliAlbCab> cliAlbCabCollection) {
        this.cliAlbCabCollection = cliAlbCabCollection;
    }

    @XmlTransient
    public Collection<RegistroLog> getRegistroLogCollection() {
        return registroLogCollection;
    }

    public void setRegistroLogCollection(Collection<RegistroLog> registroLogCollection) {
        this.registroLogCollection = registroLogCollection;
    }

    @XmlTransient
    public Collection<ProvFacCab> getProvFacCabCollection() {
        return provFacCabCollection;
    }

    public void setProvFacCabCollection(Collection<ProvFacCab> provFacCabCollection) {
        this.provFacCabCollection = provFacCabCollection;
    }

    public DatosPersonales getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(DatosPersonales codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    @XmlTransient
    public Collection<ProvAlbCab> getProvAlbCabCollection() {
        return provAlbCabCollection;
    }

    public void setProvAlbCabCollection(Collection<ProvAlbCab> provAlbCabCollection) {
        this.provAlbCabCollection = provAlbCabCollection;
    }

    @XmlTransient
    public Collection<CliFacCab> getCliFacCabCollection() {
        return cliFacCabCollection;
    }

    public void setCliFacCabCollection(Collection<CliFacCab> cliFacCabCollection) {
        this.cliFacCabCollection = cliFacCabCollection;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.Usuarios[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Roles> getRolesCollection() {
        return rolesCollection;
    }

    public void setRolesCollection(Collection<Roles> rolesCollection) {
        this.rolesCollection = rolesCollection;
    }

    @XmlTransient
    public Collection<UsuGrupo> getUsuGrupoCollection() {
        return usuGrupoCollection;
    }

    public void setUsuGrupoCollection(Collection<UsuGrupo> usuGrupoCollection) {
        this.usuGrupoCollection = usuGrupoCollection;
    }

    @XmlTransient
    public Collection<EmpUsu> getEmpUsuCollection() {
        return empUsuCollection;
    }

    public void setEmpUsuCollection(Collection<EmpUsu> empUsuCollection) {
        this.empUsuCollection = empUsuCollection;
    }

    @XmlTransient
    public Collection<Sessiones> getSessionesCollection() {
        return sessionesCollection;
    }

    public void setSessionesCollection(Collection<Sessiones> sessionesCollection) {
        this.sessionesCollection = sessionesCollection;
    }
    
}
