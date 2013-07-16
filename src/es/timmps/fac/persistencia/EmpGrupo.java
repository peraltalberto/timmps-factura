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
@Table(name = "emp_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpGrupo.findAll", query = "SELECT e FROM EmpGrupo e"),
    @NamedQuery(name = "EmpGrupo.findByCodEmp", query = "SELECT e FROM EmpGrupo e WHERE e.empGrupoPK.codEmp = :codEmp"),
    @NamedQuery(name = "EmpGrupo.findByCodGrupo", query = "SELECT e FROM EmpGrupo e WHERE e.empGrupoPK.codGrupo = :codGrupo")})
public class EmpGrupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpGrupoPK empGrupoPK;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empresas empresas;
    @JoinColumn(name = "COD_GRUPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Grupos grupos;

    public EmpGrupo() {
    }

    public EmpGrupo(EmpGrupoPK empGrupoPK) {
        this.empGrupoPK = empGrupoPK;
    }

    public EmpGrupo(String codEmp, int codGrupo) {
        this.empGrupoPK = new EmpGrupoPK(codEmp, codGrupo);
    }

    public EmpGrupoPK getEmpGrupoPK() {
        return empGrupoPK;
    }

    public void setEmpGrupoPK(EmpGrupoPK empGrupoPK) {
        this.empGrupoPK = empGrupoPK;
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    public Grupos getGrupos() {
        return grupos;
    }

    public void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empGrupoPK != null ? empGrupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpGrupo)) {
            return false;
        }
        EmpGrupo other = (EmpGrupo) object;
        if ((this.empGrupoPK == null && other.empGrupoPK != null) || (this.empGrupoPK != null && !this.empGrupoPK.equals(other.empGrupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.EmpGrupo[ empGrupoPK=" + empGrupoPK + " ]";
    }
    
}
