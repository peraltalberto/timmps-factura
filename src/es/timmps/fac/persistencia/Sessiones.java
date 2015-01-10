/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "sessiones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sessiones.findAll", query = "SELECT s FROM Sessiones s"),
    @NamedQuery(name = "Sessiones.findById", query = "SELECT s FROM Sessiones s WHERE s.id = :id"),
    @NamedQuery(name = "Sessiones.findByClave", query = "SELECT s FROM Sessiones s WHERE s.clave = :clave"),
    @NamedQuery(name = "Sessiones.findByInicio", query = "SELECT s FROM Sessiones s WHERE s.inicio = :inicio"),
    @NamedQuery(name = "Sessiones.findByEstado", query = "SELECT s FROM Sessiones s WHERE s.estado = :estado")})
public class Sessiones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CLAVE")
    private String clave;
    @Basic(optional = false)
    @Column(name = "INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private int estado;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "CODIGO")
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(optional = false)
    private Empresas empresa;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @JoinColumn(name = "APLICACION", referencedColumnName = "ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(optional = true)
    private Aplicaciones aplicacion;

    public Sessiones() {
    }

    public Sessiones(Integer id) {
        this.id = id;
    }

    public Sessiones(Integer id, String key, Date inicio, int estado) {
        this.id = id;
        this.clave = key;
        this.inicio = inicio;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String key) {
        this.clave = key;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Aplicaciones getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicaciones aplicacion) {
        this.aplicacion = aplicacion;
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
        if (!(object instanceof Sessiones)) {
            return false;
        }
        Sessiones other = (Sessiones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.Sessiones[ id=" + id + " ]";
    }
    
}
