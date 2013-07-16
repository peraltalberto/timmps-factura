/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author aperalta
 */
@Embeddable
public class UsuGrupoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "USU")
    private String usu;
    @Basic(optional = false)
    @Column(name = "GRUPO")
    private int grupo;

    public UsuGrupoPK() {
    }

    public UsuGrupoPK(String usu, int grupo) {
        this.usu = usu;
        this.grupo = grupo;
    }

    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usu != null ? usu.hashCode() : 0);
        hash += (int) grupo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuGrupoPK)) {
            return false;
        }
        UsuGrupoPK other = (UsuGrupoPK) object;
        if ((this.usu == null && other.usu != null) || (this.usu != null && !this.usu.equals(other.usu))) {
            return false;
        }
        if (this.grupo != other.grupo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.UsuGrupoPK[ usu=" + usu + ", grupo=" + grupo + " ]";
    }
    
}
