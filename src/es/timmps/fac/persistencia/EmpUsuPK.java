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
public class EmpUsuPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMP")
    private String codEmp;
    @Basic(optional = false)
    @Column(name = "USU")
    private String usu;

    public EmpUsuPK() {
    }

    public EmpUsuPK(String codEmp, String usu) {
        this.codEmp = codEmp;
        this.usu = usu;
    }

    public String getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(String codEmp) {
        this.codEmp = codEmp;
    }

    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmp != null ? codEmp.hashCode() : 0);
        hash += (usu != null ? usu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpUsuPK)) {
            return false;
        }
        EmpUsuPK other = (EmpUsuPK) object;
        if ((this.codEmp == null && other.codEmp != null) || (this.codEmp != null && !this.codEmp.equals(other.codEmp))) {
            return false;
        }
        if ((this.usu == null && other.usu != null) || (this.usu != null && !this.usu.equals(other.usu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.EmpUsuPK[ codEmp=" + codEmp + ", usu=" + usu + " ]";
    }
    
}
