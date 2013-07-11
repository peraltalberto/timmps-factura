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
@Table(name = "prov_alb_custom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvAlbCustom.findAll", query = "SELECT p FROM ProvAlbCustom p"),
    @NamedQuery(name = "ProvAlbCustom.findByCodCampo", query = "SELECT p FROM ProvAlbCustom p WHERE p.provAlbCustomPK.codCampo = :codCampo"),
    @NamedQuery(name = "ProvAlbCustom.findByCodCab", query = "SELECT p FROM ProvAlbCustom p WHERE p.provAlbCustomPK.codCab = :codCab"),
    @NamedQuery(name = "ProvAlbCustom.findByValor", query = "SELECT p FROM ProvAlbCustom p WHERE p.valor = :valor")})
public class ProvAlbCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProvAlbCustomPK provAlbCustomPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "COD_CAMPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CfProvAlbCustomEmp cfProvAlbCustomEmp;
    @JoinColumn(name = "COD_CAB", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProvAlbCab provAlbCab;

    public ProvAlbCustom() {
    }

    public ProvAlbCustom(ProvAlbCustomPK provAlbCustomPK) {
        this.provAlbCustomPK = provAlbCustomPK;
    }

    public ProvAlbCustom(ProvAlbCustomPK provAlbCustomPK, String valor) {
        this.provAlbCustomPK = provAlbCustomPK;
        this.valor = valor;
    }

    public ProvAlbCustom(int codCampo, int codCab) {
        this.provAlbCustomPK = new ProvAlbCustomPK(codCampo, codCab);
    }

    public ProvAlbCustomPK getProvAlbCustomPK() {
        return provAlbCustomPK;
    }

    public void setProvAlbCustomPK(ProvAlbCustomPK provAlbCustomPK) {
        this.provAlbCustomPK = provAlbCustomPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CfProvAlbCustomEmp getCfProvAlbCustomEmp() {
        return cfProvAlbCustomEmp;
    }

    public void setCfProvAlbCustomEmp(CfProvAlbCustomEmp cfProvAlbCustomEmp) {
        this.cfProvAlbCustomEmp = cfProvAlbCustomEmp;
    }

    public ProvAlbCab getProvAlbCab() {
        return provAlbCab;
    }

    public void setProvAlbCab(ProvAlbCab provAlbCab) {
        this.provAlbCab = provAlbCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provAlbCustomPK != null ? provAlbCustomPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvAlbCustom)) {
            return false;
        }
        ProvAlbCustom other = (ProvAlbCustom) object;
        if ((this.provAlbCustomPK == null && other.provAlbCustomPK != null) || (this.provAlbCustomPK != null && !this.provAlbCustomPK.equals(other.provAlbCustomPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.ProvAlbCustom[ provAlbCustomPK=" + provAlbCustomPK + " ]";
    }
    
}
