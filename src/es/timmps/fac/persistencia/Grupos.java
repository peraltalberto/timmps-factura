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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "grupos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupos.findAll", query = "SELECT g FROM Grupos g"),
    @NamedQuery(name = "Grupos.findById", query = "SELECT g FROM Grupos g WHERE g.id = :id"),
    @NamedQuery(name = "Grupos.findByNombre", query = "SELECT g FROM Grupos g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Grupos.findByDescripcion", query = "SELECT g FROM Grupos g WHERE g.descripcion = :descripcion")})
public class Grupos implements Serializable {
    @Basic(optional = false)
    @Column(name = "ACTIVO")
    private int activo;
     @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "gruposCollection")
    private Collection<Roles> rolesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupos")
    private Collection<UsuGrupo> usuGrupoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupos")
    private Collection<EmpGrupo> empGrupoCollection;
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
    @JoinTable(name = "usu_grupo", joinColumns = {
        @JoinColumn(name = "GRUPO", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "USU", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Usuarios> usuariosCollection;
     @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "gruposCollection")
    private Collection<Empresas> empresasCollection;

    public Grupos() {
    }

    public Grupos(Integer id) {
        this.id = id;
    }

    public Grupos(Integer id, String nombre, String descripcion) {
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
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @XmlTransient
    public Collection<Empresas> getEmpresasCollection() {
        return empresasCollection;
    }

    public void setEmpresasCollection(Collection<Empresas> empresasCollection) {
        this.empresasCollection = empresasCollection;
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
        if (!(object instanceof Grupos)) {
            return false;
        }
        Grupos other = (Grupos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.Grupos[ id=" + id + " ]";
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
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
    public Collection<EmpGrupo> getEmpGrupoCollection() {
        return empGrupoCollection;
    }

    public void setEmpGrupoCollection(Collection<EmpGrupo> empGrupoCollection) {
        this.empGrupoCollection = empGrupoCollection;
    }
    
}
