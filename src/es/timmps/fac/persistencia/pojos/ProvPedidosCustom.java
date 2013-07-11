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
@Table(name = "prov_pedidos_custom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvPedidosCustom.findAll", query = "SELECT p FROM ProvPedidosCustom p"),
    @NamedQuery(name = "ProvPedidosCustom.findByCodCampo", query = "SELECT p FROM ProvPedidosCustom p WHERE p.provPedidosCustomPK.codCampo = :codCampo"),
    @NamedQuery(name = "ProvPedidosCustom.findByCodCab", query = "SELECT p FROM ProvPedidosCustom p WHERE p.provPedidosCustomPK.codCab = :codCab"),
    @NamedQuery(name = "ProvPedidosCustom.findByValor", query = "SELECT p FROM ProvPedidosCustom p WHERE p.valor = :valor")})
public class ProvPedidosCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProvPedidosCustomPK provPedidosCustomPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "COD_CAB", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProvPedidosCab provPedidosCab;
    @JoinColumn(name = "COD_CAMPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CfProvPedCustomEmp cfProvPedCustomEmp;

    public ProvPedidosCustom() {
    }

    public ProvPedidosCustom(ProvPedidosCustomPK provPedidosCustomPK) {
        this.provPedidosCustomPK = provPedidosCustomPK;
    }

    public ProvPedidosCustom(ProvPedidosCustomPK provPedidosCustomPK, String valor) {
        this.provPedidosCustomPK = provPedidosCustomPK;
        this.valor = valor;
    }

    public ProvPedidosCustom(int codCampo, int codCab) {
        this.provPedidosCustomPK = new ProvPedidosCustomPK(codCampo, codCab);
    }

    public ProvPedidosCustomPK getProvPedidosCustomPK() {
        return provPedidosCustomPK;
    }

    public void setProvPedidosCustomPK(ProvPedidosCustomPK provPedidosCustomPK) {
        this.provPedidosCustomPK = provPedidosCustomPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public ProvPedidosCab getProvPedidosCab() {
        return provPedidosCab;
    }

    public void setProvPedidosCab(ProvPedidosCab provPedidosCab) {
        this.provPedidosCab = provPedidosCab;
    }

    public CfProvPedCustomEmp getCfProvPedCustomEmp() {
        return cfProvPedCustomEmp;
    }

    public void setCfProvPedCustomEmp(CfProvPedCustomEmp cfProvPedCustomEmp) {
        this.cfProvPedCustomEmp = cfProvPedCustomEmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provPedidosCustomPK != null ? provPedidosCustomPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvPedidosCustom)) {
            return false;
        }
        ProvPedidosCustom other = (ProvPedidosCustom) object;
        if ((this.provPedidosCustomPK == null && other.provPedidosCustomPK != null) || (this.provPedidosCustomPK != null && !this.provPedidosCustomPK.equals(other.provPedidosCustomPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.ProvPedidosCustom[ provPedidosCustomPK=" + provPedidosCustomPK + " ]";
    }
    
}
