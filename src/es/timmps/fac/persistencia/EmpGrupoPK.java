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
public class EmpGrupoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMP")
    private String codEmp;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO")
    private int codGrupo;

    public EmpGrupoPK() {
    }

    public EmpGrupoPK(String codEmp, int codGrupo) {
        this.codEmp = codEmp;
        this.codGrupo = codGrupo;
    }

    public String getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(String codEmp) {
        this.codEmp = codEmp;
    }

    public int getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(int codGrupo) {
        this.codGrupo = codGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmp != null ? codEmp.hashCode() : 0);
        hash += (int) codGrupo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpGrupoPK)) {
            return false;
        }
        EmpGrupoPK other = (EmpGrupoPK) object;
        if ((this.codEmp == null && other.codEmp != null) || (this.codEmp != null && !this.codEmp.equals(other.codEmp))) {
            return false;
        }
        if (this.codGrupo != other.codGrupo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.EmpGrupoPK[ codEmp=" + codEmp + ", codGrupo=" + codGrupo + " ]";
    }
    
}
