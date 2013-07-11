/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "mails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mails.findAll", query = "SELECT m FROM Mails m"),
    @NamedQuery(name = "Mails.findById", query = "SELECT m FROM Mails m WHERE m.id = :id"),
    @NamedQuery(name = "Mails.findByDescripcion", query = "SELECT m FROM Mails m WHERE m.descripcion = :descripcion"),
    @NamedQuery(name = "Mails.findByEmail", query = "SELECT m FROM Mails m WHERE m.email = :email")})
public class Mails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @JoinColumn(name = "CODIGO_PERSONA", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private DatosPersonales codigoPersona;

    public Mails() {
    }

    public Mails(Integer id) {
        this.id = id;
    }

    public Mails(Integer id, String descripcion, String email) {
        this.id = id;
        this.descripcion = descripcion;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DatosPersonales getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(DatosPersonales codigoPersona) {
        this.codigoPersona = codigoPersona;
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
        if (!(object instanceof Mails)) {
            return false;
        }
        Mails other = (Mails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.Mails[ id=" + id + " ]";
    }
    
}
