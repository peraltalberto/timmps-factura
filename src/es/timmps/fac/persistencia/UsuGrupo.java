/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "usu_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuGrupo.findAll", query = "SELECT u FROM UsuGrupo u"),
    @NamedQuery(name = "UsuGrupo.findByUsu", query = "SELECT u FROM UsuGrupo u WHERE u.usuGrupoPK.usu = :usu"),
    @NamedQuery(name = "UsuGrupo.findByGrupo", query = "SELECT u FROM UsuGrupo u WHERE u.usuGrupoPK.grupo = :grupo")})
public class UsuGrupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuGrupoPK usuGrupoPK;
    @JoinColumn(name = "GRUPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Grupos grupos;
    @JoinColumn(name = "USU", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public UsuGrupo() {
    }

    public UsuGrupo(UsuGrupoPK usuGrupoPK) {
        this.usuGrupoPK = usuGrupoPK;
    }

    public UsuGrupo(String usu, int grupo) {
        this.usuGrupoPK = new UsuGrupoPK(usu, grupo);
    }

    public UsuGrupoPK getUsuGrupoPK() {
        return usuGrupoPK;
    }

    public void setUsuGrupoPK(UsuGrupoPK usuGrupoPK) {
        this.usuGrupoPK = usuGrupoPK;
    }

    public Grupos getGrupos() {
        return grupos;
    }

    public void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuGrupoPK != null ? usuGrupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuGrupo)) {
            return false;
        }
        UsuGrupo other = (UsuGrupo) object;
        if ((this.usuGrupoPK == null && other.usuGrupoPK != null) || (this.usuGrupoPK != null && !this.usuGrupoPK.equals(other.usuGrupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.UsuGrupo[ usuGrupoPK=" + usuGrupoPK + " ]";
    }
    
}
