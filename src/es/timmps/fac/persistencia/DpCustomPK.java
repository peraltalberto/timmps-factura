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
public class DpCustomPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "CODIGO_PERSONA")
    private int codigoPersona;
    @Basic(optional = false)
    @Column(name = "CAMPO")
    private int campo;

    public DpCustomPK() {
    }

    public DpCustomPK(int codigoPersona, int campo) {
        this.codigoPersona = codigoPersona;
        this.campo = campo;
    }

    public int getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(int codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    public int getCampo() {
        return campo;
    }

    public void setCampo(int campo) {
        this.campo = campo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoPersona;
        hash += (int) campo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DpCustomPK)) {
            return false;
        }
        DpCustomPK other = (DpCustomPK) object;
        if (this.codigoPersona != other.codigoPersona) {
            return false;
        }
        if (this.campo != other.campo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.DpCustomPK[ codigoPersona=" + codigoPersona + ", campo=" + campo + " ]";
    }
    
}
