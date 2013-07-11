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
@Table(name = "cli_pedidos_custom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CliPedidosCustom.findAll", query = "SELECT c FROM CliPedidosCustom c"),
    @NamedQuery(name = "CliPedidosCustom.findByCodCampo", query = "SELECT c FROM CliPedidosCustom c WHERE c.cliPedidosCustomPK.codCampo = :codCampo"),
    @NamedQuery(name = "CliPedidosCustom.findByCodCab", query = "SELECT c FROM CliPedidosCustom c WHERE c.cliPedidosCustomPK.codCab = :codCab"),
    @NamedQuery(name = "CliPedidosCustom.findByValor", query = "SELECT c FROM CliPedidosCustom c WHERE c.valor = :valor")})
public class CliPedidosCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CliPedidosCustomPK cliPedidosCustomPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "COD_CAMPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CfCliPedCustomEmp cfCliPedCustomEmp;
    @JoinColumn(name = "COD_CAB", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CliPedidosCab cliPedidosCab;

    public CliPedidosCustom() {
    }

    public CliPedidosCustom(CliPedidosCustomPK cliPedidosCustomPK) {
        this.cliPedidosCustomPK = cliPedidosCustomPK;
    }

    public CliPedidosCustom(CliPedidosCustomPK cliPedidosCustomPK, String valor) {
        this.cliPedidosCustomPK = cliPedidosCustomPK;
        this.valor = valor;
    }

    public CliPedidosCustom(int codCampo, int codCab) {
        this.cliPedidosCustomPK = new CliPedidosCustomPK(codCampo, codCab);
    }

    public CliPedidosCustomPK getCliPedidosCustomPK() {
        return cliPedidosCustomPK;
    }

    public void setCliPedidosCustomPK(CliPedidosCustomPK cliPedidosCustomPK) {
        this.cliPedidosCustomPK = cliPedidosCustomPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CfCliPedCustomEmp getCfCliPedCustomEmp() {
        return cfCliPedCustomEmp;
    }

    public void setCfCliPedCustomEmp(CfCliPedCustomEmp cfCliPedCustomEmp) {
        this.cfCliPedCustomEmp = cfCliPedCustomEmp;
    }

    public CliPedidosCab getCliPedidosCab() {
        return cliPedidosCab;
    }

    public void setCliPedidosCab(CliPedidosCab cliPedidosCab) {
        this.cliPedidosCab = cliPedidosCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliPedidosCustomPK != null ? cliPedidosCustomPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CliPedidosCustom)) {
            return false;
        }
        CliPedidosCustom other = (CliPedidosCustom) object;
        if ((this.cliPedidosCustomPK == null && other.cliPedidosCustomPK != null) || (this.cliPedidosCustomPK != null && !this.cliPedidosCustomPK.equals(other.cliPedidosCustomPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.CliPedidosCustom[ cliPedidosCustomPK=" + cliPedidosCustomPK + " ]";
    }
    
}
