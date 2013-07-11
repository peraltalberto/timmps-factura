/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "cli_fac_custom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CliFacCustom.findAll", query = "SELECT c FROM CliFacCustom c"),
    @NamedQuery(name = "CliFacCustom.findByCodCampo", query = "SELECT c FROM CliFacCustom c WHERE c.cliFacCustomPK.codCampo = :codCampo"),
    @NamedQuery(name = "CliFacCustom.findByCodCab", query = "SELECT c FROM CliFacCustom c WHERE c.cliFacCustomPK.codCab = :codCab"),
    @NamedQuery(name = "CliFacCustom.findByValor", query = "SELECT c FROM CliFacCustom c WHERE c.valor = :valor")})
public class CliFacCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CliFacCustomPK cliFacCustomPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "COD_CAMPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CfCliFacCustomEmp cfCliFacCustomEmp;
    @JoinColumn(name = "COD_CAB", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CliFacCab cliFacCab;

    public CliFacCustom() {
    }

    public CliFacCustom(CliFacCustomPK cliFacCustomPK) {
        this.cliFacCustomPK = cliFacCustomPK;
    }

    public CliFacCustom(CliFacCustomPK cliFacCustomPK, String valor) {
        this.cliFacCustomPK = cliFacCustomPK;
        this.valor = valor;
    }

    public CliFacCustom(int codCampo, int codCab) {
        this.cliFacCustomPK = new CliFacCustomPK(codCampo, codCab);
    }

    public CliFacCustomPK getCliFacCustomPK() {
        return cliFacCustomPK;
    }

    public void setCliFacCustomPK(CliFacCustomPK cliFacCustomPK) {
        this.cliFacCustomPK = cliFacCustomPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CfCliFacCustomEmp getCfCliFacCustomEmp() {
        return cfCliFacCustomEmp;
    }

    public void setCfCliFacCustomEmp(CfCliFacCustomEmp cfCliFacCustomEmp) {
        this.cfCliFacCustomEmp = cfCliFacCustomEmp;
    }

    public CliFacCab getCliFacCab() {
        return cliFacCab;
    }

    public void setCliFacCab(CliFacCab cliFacCab) {
        this.cliFacCab = cliFacCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliFacCustomPK != null ? cliFacCustomPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CliFacCustom)) {
            return false;
        }
        CliFacCustom other = (CliFacCustom) object;
        if ((this.cliFacCustomPK == null && other.cliFacCustomPK != null) || (this.cliFacCustomPK != null && !this.cliFacCustomPK.equals(other.cliFacCustomPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.CliFacCustom[ cliFacCustomPK=" + cliFacCustomPK + " ]";
    }
    
}
