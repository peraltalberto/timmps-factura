/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author aperalta
 */
@Embeddable
public class ArticulosCustomPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_CAMPO")
    private int codCampo;
    @Basic(optional = false)
    @Column(name = "COD_PROD")
    private String codProd;

    public ArticulosCustomPK() {
    }

    public ArticulosCustomPK(int codCampo, String codProd) {
        this.codCampo = codCampo;
        this.codProd = codProd;
    }

    public int getCodCampo() {
        return codCampo;
    }

    public void setCodCampo(int codCampo) {
        this.codCampo = codCampo;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codCampo;
        hash += (codProd != null ? codProd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticulosCustomPK)) {
            return false;
        }
        ArticulosCustomPK other = (ArticulosCustomPK) object;
        if (this.codCampo != other.codCampo) {
            return false;
        }
        if ((this.codProd == null && other.codProd != null) || (this.codProd != null && !this.codProd.equals(other.codProd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.ArticulosCustomPK[ codCampo=" + codCampo + ", codProd=" + codProd + " ]";
    }
    
}
