/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

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

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "registro_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroLog.findAll", query = "SELECT r FROM RegistroLog r"),
    @NamedQuery(name = "RegistroLog.findById", query = "SELECT r FROM RegistroLog r WHERE r.id = :id"),
    @NamedQuery(name = "RegistroLog.findByFecha", query = "SELECT r FROM RegistroLog r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "RegistroLog.findByLog", query = "SELECT r FROM RegistroLog r WHERE r.log = :log")})
public class RegistroLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "LOG")
    private String log;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @JoinColumn(name = "TIPO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoLog tipo;

    public RegistroLog() {
    }

    public RegistroLog(Integer id) {
        this.id = id;
    }

    public RegistroLog(Integer id, Date fecha, String log) {
        this.id = id;
        this.fecha = fecha;
        this.log = log;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public TipoLog getTipo() {
        return tipo;
    }

    public void setTipo(TipoLog tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof RegistroLog)) {
            return false;
        }
        RegistroLog other = (RegistroLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.RegistroLog[ id=" + id + " ]";
    }
    
}
