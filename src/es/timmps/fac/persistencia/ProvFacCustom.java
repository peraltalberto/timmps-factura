/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia;

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
@Table(name = "prov_fac_custom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvFacCustom.findAll", query = "SELECT p FROM ProvFacCustom p"),
    @NamedQuery(name = "ProvFacCustom.findByCodCampo", query = "SELECT p FROM ProvFacCustom p WHERE p.provFacCustomPK.codCampo = :codCampo"),
    @NamedQuery(name = "ProvFacCustom.findByCodCab", query = "SELECT p FROM ProvFacCustom p WHERE p.provFacCustomPK.codCab = :codCab"),
    @NamedQuery(name = "ProvFacCustom.findByValor", query = "SELECT p FROM ProvFacCustom p WHERE p.valor = :valor")})
public class ProvFacCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProvFacCustomPK provFacCustomPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "COD_CAMPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CfProvFacCustomEmp cfProvFacCustomEmp;
    @JoinColumn(name = "COD_CAB", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProvFacCab provFacCab;

    public ProvFacCustom() {
    }

    public ProvFacCustom(ProvFacCustomPK provFacCustomPK) {
        this.provFacCustomPK = provFacCustomPK;
    }

    public ProvFacCustom(ProvFacCustomPK provFacCustomPK, String valor) {
        this.provFacCustomPK = provFacCustomPK;
        this.valor = valor;
    }

    public ProvFacCustom(int codCampo, int codCab) {
        this.provFacCustomPK = new ProvFacCustomPK(codCampo, codCab);
    }

    public ProvFacCustomPK getProvFacCustomPK() {
        return provFacCustomPK;
    }

    public void setProvFacCustomPK(ProvFacCustomPK provFacCustomPK) {
        this.provFacCustomPK = provFacCustomPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CfProvFacCustomEmp getCfProvFacCustomEmp() {
        return cfProvFacCustomEmp;
    }

    public void setCfProvFacCustomEmp(CfProvFacCustomEmp cfProvFacCustomEmp) {
        this.cfProvFacCustomEmp = cfProvFacCustomEmp;
    }

    public ProvFacCab getProvFacCab() {
        return provFacCab;
    }

    public void setProvFacCab(ProvFacCab provFacCab) {
        this.provFacCab = provFacCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provFacCustomPK != null ? provFacCustomPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvFacCustom)) {
            return false;
        }
        ProvFacCustom other = (ProvFacCustom) object;
        if ((this.provFacCustomPK == null && other.provFacCustomPK != null) || (this.provFacCustomPK != null && !this.provFacCustomPK.equals(other.provFacCustomPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.ProvFacCustom[ provFacCustomPK=" + provFacCustomPK + " ]";
    }
    
}
