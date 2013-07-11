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
@Table(name = "cli_alb_custom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CliAlbCustom.findAll", query = "SELECT c FROM CliAlbCustom c"),
    @NamedQuery(name = "CliAlbCustom.findByCodCampo", query = "SELECT c FROM CliAlbCustom c WHERE c.cliAlbCustomPK.codCampo = :codCampo"),
    @NamedQuery(name = "CliAlbCustom.findByCodCab", query = "SELECT c FROM CliAlbCustom c WHERE c.cliAlbCustomPK.codCab = :codCab"),
    @NamedQuery(name = "CliAlbCustom.findByValor", query = "SELECT c FROM CliAlbCustom c WHERE c.valor = :valor")})
public class CliAlbCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CliAlbCustomPK cliAlbCustomPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "COD_CAMPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CfCliAlbCustomEmp cfCliAlbCustomEmp;
    @JoinColumn(name = "COD_CAB", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CliAlbCab cliAlbCab;

    public CliAlbCustom() {
    }

    public CliAlbCustom(CliAlbCustomPK cliAlbCustomPK) {
        this.cliAlbCustomPK = cliAlbCustomPK;
    }

    public CliAlbCustom(CliAlbCustomPK cliAlbCustomPK, String valor) {
        this.cliAlbCustomPK = cliAlbCustomPK;
        this.valor = valor;
    }

    public CliAlbCustom(int codCampo, int codCab) {
        this.cliAlbCustomPK = new CliAlbCustomPK(codCampo, codCab);
    }

    public CliAlbCustomPK getCliAlbCustomPK() {
        return cliAlbCustomPK;
    }

    public void setCliAlbCustomPK(CliAlbCustomPK cliAlbCustomPK) {
        this.cliAlbCustomPK = cliAlbCustomPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CfCliAlbCustomEmp getCfCliAlbCustomEmp() {
        return cfCliAlbCustomEmp;
    }

    public void setCfCliAlbCustomEmp(CfCliAlbCustomEmp cfCliAlbCustomEmp) {
        this.cfCliAlbCustomEmp = cfCliAlbCustomEmp;
    }

    public CliAlbCab getCliAlbCab() {
        return cliAlbCab;
    }

    public void setCliAlbCab(CliAlbCab cliAlbCab) {
        this.cliAlbCab = cliAlbCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliAlbCustomPK != null ? cliAlbCustomPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CliAlbCustom)) {
            return false;
        }
        CliAlbCustom other = (CliAlbCustom) object;
        if ((this.cliAlbCustomPK == null && other.cliAlbCustomPK != null) || (this.cliAlbCustomPK != null && !this.cliAlbCustomPK.equals(other.cliAlbCustomPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.CliAlbCustom[ cliAlbCustomPK=" + cliAlbCustomPK + " ]";
    }
    
}
