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
@Table(name = "cf_cli_fac_custom_emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfCliFacCustomEmp.findAll", query = "SELECT c FROM CfCliFacCustomEmp c"),
    @NamedQuery(name = "CfCliFacCustomEmp.findById", query = "SELECT c FROM CfCliFacCustomEmp c WHERE c.id = :id"),
    @NamedQuery(name = "CfCliFacCustomEmp.findByCampo", query = "SELECT c FROM CfCliFacCustomEmp c WHERE c.campo = :campo"),
    @NamedQuery(name = "CfCliFacCustomEmp.findByDescripcion", query = "SELECT c FROM CfCliFacCustomEmp c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CfCliFacCustomEmp.findByTipoValor", query = "SELECT c FROM CfCliFacCustomEmp c WHERE c.tipoValor = :tipoValor")})
public class CfCliFacCustomEmp implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "TIPO_VALOR")
    private int tipoValor;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfCliFacCustomEmp")
    private Collection<CliFacCustom> cliFacCustomCollection;

    public CfCliFacCustomEmp() {
    }

    public CfCliFacCustomEmp(Integer id) {
        this.id = id;
    }

    public CfCliFacCustomEmp(Integer id, String campo, String descripcion, int tipoValor) {
        this.id = id;
        this.campo = campo;
        this.descripcion = descripcion;
        this.tipoValor = tipoValor;
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

    public int getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(int tipoValor) {
        this.tipoValor = tipoValor;
    }

    public Empresas getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Empresas codEmp) {
        this.codEmp = codEmp;
    }

    @XmlTransient
    public Collection<CliFacCustom> getCliFacCustomCollection() {
        return cliFacCustomCollection;
    }

    public void setCliFacCustomCollection(Collection<CliFacCustom> cliFacCustomCollection) {
        this.cliFacCustomCollection = cliFacCustomCollection;
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
        if (!(object instanceof CfCliFacCustomEmp)) {
            return false;
        }
        CfCliFacCustomEmp other = (CfCliFacCustomEmp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.CfCliFacCustomEmp[ id=" + id + " ]";
    }
    
}
