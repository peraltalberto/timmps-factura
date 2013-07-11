/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "cf_global")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfGlobal.findAll", query = "SELECT c FROM CfGlobal c"),
    @NamedQuery(name = "CfGlobal.findByCodigo", query = "SELECT c FROM CfGlobal c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CfGlobal.findByDescripcion", query = "SELECT c FROM CfGlobal c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CfGlobal.findByValor", query = "SELECT c FROM CfGlobal c WHERE c.valor = :valor")})
public class CfGlobal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "TIPO_DATO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CfTipoValores tipoDato;

    public CfGlobal() {
    }

    public CfGlobal(String codigo) {
        this.codigo = codigo;
    }

    public CfGlobal(String codigo, String descripcion, String valor) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CfTipoValores getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(CfTipoValores tipoDato) {
        this.tipoDato = tipoDato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfGlobal)) {
            return false;
        }
        CfGlobal other = (CfGlobal) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.CfGlobal[ codigo=" + codigo + " ]";
    }
    
}
