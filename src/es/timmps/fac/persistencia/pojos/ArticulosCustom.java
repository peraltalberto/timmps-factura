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
@Table(name = "articulos_custom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticulosCustom.findAll", query = "SELECT a FROM ArticulosCustom a"),
    @NamedQuery(name = "ArticulosCustom.findByCodCampo", query = "SELECT a FROM ArticulosCustom a WHERE a.articulosCustomPK.codCampo = :codCampo"),
    @NamedQuery(name = "ArticulosCustom.findByCodProd", query = "SELECT a FROM ArticulosCustom a WHERE a.articulosCustomPK.codProd = :codProd"),
    @NamedQuery(name = "ArticulosCustom.findByValor", query = "SELECT a FROM ArticulosCustom a WHERE a.valor = :valor")})
public class ArticulosCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ArticulosCustomPK articulosCustomPK;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @JoinColumn(name = "COD_PROD", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Articulos articulos;
    @JoinColumn(name = "COD_CAMPO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CfProdCustomEmp cfProdCustomEmp;

    public ArticulosCustom() {
    }

    public ArticulosCustom(ArticulosCustomPK articulosCustomPK) {
        this.articulosCustomPK = articulosCustomPK;
    }

    public ArticulosCustom(ArticulosCustomPK articulosCustomPK, String valor) {
        this.articulosCustomPK = articulosCustomPK;
        this.valor = valor;
    }

    public ArticulosCustom(int codCampo, String codProd) {
        this.articulosCustomPK = new ArticulosCustomPK(codCampo, codProd);
    }

    public ArticulosCustomPK getArticulosCustomPK() {
        return articulosCustomPK;
    }

    public void setArticulosCustomPK(ArticulosCustomPK articulosCustomPK) {
        this.articulosCustomPK = articulosCustomPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Articulos getArticulos() {
        return articulos;
    }

    public void setArticulos(Articulos articulos) {
        this.articulos = articulos;
    }

    public CfProdCustomEmp getCfProdCustomEmp() {
        return cfProdCustomEmp;
    }

    public void setCfProdCustomEmp(CfProdCustomEmp cfProdCustomEmp) {
        this.cfProdCustomEmp = cfProdCustomEmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (articulosCustomPK != null ? articulosCustomPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticulosCustom)) {
            return false;
        }
        ArticulosCustom other = (ArticulosCustom) object;
        if ((this.articulosCustomPK == null && other.articulosCustomPK != null) || (this.articulosCustomPK != null && !this.articulosCustomPK.equals(other.articulosCustomPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.ArticulosCustom[ articulosCustomPK=" + articulosCustomPK + " ]";
    }
    
}
