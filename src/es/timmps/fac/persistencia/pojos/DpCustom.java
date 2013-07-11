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
@Table(name = "dp_custom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DpCustom.findAll", query = "SELECT d FROM DpCustom d"),
    @NamedQuery(name = "DpCustom.findByCodigoPersona", query = "SELECT d FROM DpCustom d WHERE d.dpCustomPK.codigoPersona = :codigoPersona"),
    @NamedQuery(name = "DpCustom.findByCampo", query = "SELECT d FROM DpCustom d WHERE d.dpCustomPK.campo = :campo"),
    @NamedQuery(name = "DpCustom.findByValor", query = "SELECT d FROM DpCustom d WHERE d.valor = :valor")})
public class DpCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DpCustomPK dpCustomPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "CAMPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CfDpCustomEmp cfDpCustomEmp;
    @JoinColumn(name = "CODIGO_PERSONA", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DatosPersonales datosPersonales;

    public DpCustom() {
    }

    public DpCustom(DpCustomPK dpCustomPK) {
        this.dpCustomPK = dpCustomPK;
    }

    public DpCustom(DpCustomPK dpCustomPK, String valor) {
        this.dpCustomPK = dpCustomPK;
        this.valor = valor;
    }

    public DpCustom(int codigoPersona, int campo) {
        this.dpCustomPK = new DpCustomPK(codigoPersona, campo);
    }

    public DpCustomPK getDpCustomPK() {
        return dpCustomPK;
    }

    public void setDpCustomPK(DpCustomPK dpCustomPK) {
        this.dpCustomPK = dpCustomPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CfDpCustomEmp getCfDpCustomEmp() {
        return cfDpCustomEmp;
    }

    public void setCfDpCustomEmp(CfDpCustomEmp cfDpCustomEmp) {
        this.cfDpCustomEmp = cfDpCustomEmp;
    }

    public DatosPersonales getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(DatosPersonales datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dpCustomPK != null ? dpCustomPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DpCustom)) {
            return false;
        }
        DpCustom other = (DpCustom) object;
        if ((this.dpCustomPK == null && other.dpCustomPK != null) || (this.dpCustomPK != null && !this.dpCustomPK.equals(other.dpCustomPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.DpCustom[ dpCustomPK=" + dpCustomPK + " ]";
    }
    
}
