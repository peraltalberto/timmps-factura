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
@Table(name = "tipo_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoLog.findAll", query = "SELECT t FROM TipoLog t"),
    @NamedQuery(name = "TipoLog.findById", query = "SELECT t FROM TipoLog t WHERE t.id = :id"),
    @NamedQuery(name = "TipoLog.findByNombreCorto", query = "SELECT t FROM TipoLog t WHERE t.nombreCorto = :nombreCorto"),
    @NamedQuery(name = "TipoLog.findByDescipcion", query = "SELECT t FROM TipoLog t WHERE t.descipcion = :descipcion")})
public class TipoLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE_CORTO")
    private String nombreCorto;
    @Basic(optional = false)
    @Column(name = "DESCIPCION")
    private String descipcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo")
    private Collection<RegistroLog> registroLogCollection;

    public TipoLog() {
    }

    public TipoLog(Integer id) {
        this.id = id;
    }

    public TipoLog(Integer id, String nombreCorto, String descipcion) {
        this.id = id;
        this.nombreCorto = nombreCorto;
        this.descipcion = descipcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    @XmlTransient
    public Collection<RegistroLog> getRegistroLogCollection() {
        return registroLogCollection;
    }

    public void setRegistroLogCollection(Collection<RegistroLog> registroLogCollection) {
        this.registroLogCollection = registroLogCollection;
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
        if (!(object instanceof TipoLog)) {
            return false;
        }
        TipoLog other = (TipoLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.TipoLog[ id=" + id + " ]";
    }
    
}
