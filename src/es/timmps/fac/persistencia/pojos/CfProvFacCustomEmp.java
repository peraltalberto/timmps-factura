/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aperalta
 */
@Entity
@Table(name = "cf_prov_fac_custom_emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfProvFacCustomEmp.findAll", query = "SELECT c FROM CfProvFacCustomEmp c"),
    @NamedQuery(name = "CfProvFacCustomEmp.findById", query = "SELECT c FROM CfProvFacCustomEmp c WHERE c.id = :id"),
    @NamedQuery(name = "CfProvFacCustomEmp.findByCampo", query = "SELECT c FROM CfProvFacCustomEmp c WHERE c.campo = :campo"),
    @NamedQuery(name = "CfProvFacCustomEmp.findByDescripcion", query = "SELECT c FROM CfProvFacCustomEmp c WHERE c.descripcion = :descripcion")})
public class CfProvFacCustomEmp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CAMPO")
    private String campo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfProvFacCustomEmp")
    private Collection<ProvFacCustom> provFacCustomCollection;
    @JoinColumn(name = "TIPO_VALOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CfTipoValores tipoValor;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;

    public CfProvFacCustomEmp() {
    }

    public CfProvFacCustomEmp(Integer id) {
        this.id = id;
    }

    public CfProvFacCustomEmp(Integer id, String campo, String descripcion) {
        this.id = id;
        this.campo = campo;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<ProvFacCustom> getProvFacCustomCollection() {
        return provFacCustomCollection;
    }

    public void setProvFacCustomCollection(Collection<ProvFacCustom> provFacCustomCollection) {
        this.provFacCustomCollection = provFacCustomCollection;
    }

    public CfTipoValores getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(CfTipoValores tipoValor) {
        this.tipoValor = tipoValor;
    }

    public Empresas getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Empresas codEmp) {
        this.codEmp = codEmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfProvFacCustomEmp)) {
            return false;
        }
        CfProvFacCustomEmp other = (CfProvFacCustomEmp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.CfProvFacCustomEmp[ id=" + id + " ]";
    }
    
}
