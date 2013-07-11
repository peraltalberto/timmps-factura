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
public class ProvAlbCustomPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_CAMPO")
    private int codCampo;
    @Basic(optional = false)
    @Column(name = "COD_CAB")
    private int codCab;

    public ProvAlbCustomPK() {
    }

    public ProvAlbCustomPK(int codCampo, int codCab) {
        this.codCampo = codCampo;
        this.codCab = codCab;
    }

    public int getCodCampo() {
        return codCampo;
    }

    public void setCodCampo(int codCampo) {
        this.codCampo = codCampo;
    }

    public int getCodCab() {
        return codCab;
    }

    public void setCodCab(int codCab) {
        this.codCab = codCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codCampo;
        hash += (int) codCab;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvAlbCustomPK)) {
            return false;
        }
        ProvAlbCustomPK other = (ProvAlbCustomPK) object;
        if (this.codCampo != other.codCampo) {
            return false;
        }
        if (this.codCab != other.codCab) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.ProvAlbCustomPK[ codCampo=" + codCampo + ", codCab=" + codCab + " ]";
    }
    
}
