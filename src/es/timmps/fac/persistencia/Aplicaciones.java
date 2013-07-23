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
import javax.persistence.ManyToMany;
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
@Table(name = "aplicaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aplicaciones.findAll", query = "SELECT a FROM Aplicaciones a"),
    @NamedQuery(name = "Aplicaciones.findById", query = "SELECT a FROM Aplicaciones a WHERE a.id = :id"),
    @NamedQuery(name = "Aplicaciones.findByNombre", query = "SELECT a FROM Aplicaciones a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Aplicaciones.findByDescripcion", query = "SELECT a FROM Aplicaciones a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Aplicaciones.findByConstructor", query = "SELECT a FROM Aplicaciones a WHERE a.constructor = :constructor"),
    @NamedQuery(name = "Aplicaciones.findByDestino", query = "SELECT a FROM Aplicaciones a WHERE a.destino = :destino"),
    @NamedQuery(name = "Aplicaciones.findByTipo", query = "SELECT a FROM Aplicaciones a WHERE a.tipo = :tipo")})
public class Aplicaciones implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aplicacion")
    private Collection<Sessiones> sessionesCollection;
    @Basic(optional = false)
    @Column(name = "IMAGEN")
    private String imagen;
    @Basic(optional = false)
    @Column(name = "MENU")
    private String menu;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "CONSTRUCTOR")
    private String constructor;
    @Basic(optional = false)
    @Column(name = "DESTINO")
    private int destino;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private int tipo;
    @ManyToMany(mappedBy = "aplicacionesCollection")
    private Collection<Roles> rolesCollection;

    public Aplicaciones() {
    }

    public Aplicaciones(String id) {
        this.id = id;
    }

    public Aplicaciones(String id, String nombre, String descripcion, String constructor, int destino, int tipo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.constructor = constructor;
        this.destino = destino;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getConstructor() {
        return constructor;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Roles> getRolesCollection() {
        return rolesCollection;
    }

    public void setRolesCollection(Collection<Roles> rolesCollection) {
        this.rolesCollection = rolesCollection;
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
        if (!(object instanceof Aplicaciones)) {
            return false;
        }
        Aplicaciones other = (Aplicaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.Aplicaciones[ id=" + id + " ]";
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    @XmlTransient
    public Collection<Sessiones> getSessionesCollection() {
        return sessionesCollection;
    }

    public void setSessionesCollection(Collection<Sessiones> sessionesCollection) {
        this.sessionesCollection = sessionesCollection;
    }
    
}
