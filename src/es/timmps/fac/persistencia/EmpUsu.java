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
@Table(name = "emp_usu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpUsu.findAll", query = "SELECT e FROM EmpUsu e"),
    @NamedQuery(name = "EmpUsu.findByCodEmp", query = "SELECT e FROM EmpUsu e WHERE e.empUsuPK.codEmp = :codEmp"),
    @NamedQuery(name = "EmpUsu.findByUsu", query = "SELECT e FROM EmpUsu e WHERE e.empUsuPK.usu = :usu")})
public class EmpUsu implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpUsuPK empUsuPK;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empresas empresas;
    @JoinColumn(name = "USU", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public EmpUsu() {
    }

    public EmpUsu(EmpUsuPK empUsuPK) {
        this.empUsuPK = empUsuPK;
    }

    public EmpUsu(String codEmp, String usu) {
        this.empUsuPK = new EmpUsuPK(codEmp, usu);
    }

    public EmpUsuPK getEmpUsuPK() {
        return empUsuPK;
    }

    public void setEmpUsuPK(EmpUsuPK empUsuPK) {
        this.empUsuPK = empUsuPK;
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
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
        hash += (empUsuPK != null ? empUsuPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpUsu)) {
            return false;
        }
        EmpUsu other = (EmpUsu) object;
        if ((this.empUsuPK == null && other.empUsuPK != null) || (this.empUsuPK != null && !this.empUsuPK.equals(other.empUsuPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.EmpUsu[ empUsuPK=" + empUsuPK + " ]";
    }
    
}
