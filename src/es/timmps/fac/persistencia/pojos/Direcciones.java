/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.persistencia.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "direcciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direcciones.findAll", query = "SELECT d FROM Direcciones d"),
    @NamedQuery(name = "Direcciones.findById", query = "SELECT d FROM Direcciones d WHERE d.id = :id"),
    @NamedQuery(name = "Direcciones.findByTipoVia", query = "SELECT d FROM Direcciones d WHERE d.tipoVia = :tipoVia"),
    @NamedQuery(name = "Direcciones.findByNombreVia", query = "SELECT d FROM Direcciones d WHERE d.nombreVia = :nombreVia"),
    @NamedQuery(name = "Direcciones.findByNumero", query = "SELECT d FROM Direcciones d WHERE d.numero = :numero"),
    @NamedQuery(name = "Direcciones.findByEscalera", query = "SELECT d FROM Direcciones d WHERE d.escalera = :escalera"),
    @NamedQuery(name = "Direcciones.findByCodigoPostal", query = "SELECT d FROM Direcciones d WHERE d.codigoPostal = :codigoPostal"),
    @NamedQuery(name = "Direcciones.findByApCorreos", query = "SELECT d FROM Direcciones d WHERE d.apCorreos = :apCorreos"),
    @NamedQuery(name = "Direcciones.findByPredeterminada", query = "SELECT d FROM Direcciones d WHERE d.predeterminada = :predeterminada")})
public class Direcciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TIPO_VIA")
    private int tipoVia;
    @Basic(optional = false)
    @Column(name = "NOMBRE_VIA")
    private String nombreVia;
    @Basic(optional = false)
    @Column(name = "NUMERO")
    private int numero;
    @Basic(optional = false)
    @Column(name = "ESCALERA")
    private String escalera;
    @Basic(optional = false)
    @Column(name = "CODIGO_POSTAL")
    private String codigoPostal;
    @Basic(optional = false)
    @Column(name = "AP_CORREOS")
    private String apCorreos;
    @Basic(optional = false)
    @Column(name = "PREDETERMINADA")
    private int predeterminada;
    @JoinColumn(name = "CODIGO_PERSONA", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private DatosPersonales codigoPersona;

    public Direcciones() {
    }

    public Direcciones(Integer id) {
        this.id = id;
    }

    public Direcciones(Integer id, int tipoVia, String nombreVia, int numero, String escalera, String codigoPostal, String apCorreos, int predeterminada) {
        this.id = id;
        this.tipoVia = tipoVia;
        this.nombreVia = nombreVia;
        this.numero = numero;
        this.escalera = escalera;
        this.codigoPostal = codigoPostal;
        this.apCorreos = apCorreos;
        this.predeterminada = predeterminada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(int tipoVia) {
        this.tipoVia = tipoVia;
    }

    public String getNombreVia() {
        return nombreVia;
    }

    public void setNombreVia(String nombreVia) {
        this.nombreVia = nombreVia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEscalera() {
        return escalera;
    }

    public void setEscalera(String escalera) {
        this.escalera = escalera;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getApCorreos() {
        return apCorreos;
    }

    public void setApCorreos(String apCorreos) {
        this.apCorreos = apCorreos;
    }

    public int getPredeterminada() {
        return predeterminada;
    }

    public void setPredeterminada(int predeterminada) {
        this.predeterminada = predeterminada;
    }

    public DatosPersonales getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(DatosPersonales codigoPersona) {
        this.codigoPersona = codigoPersona;
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
        if (!(object instanceof Direcciones)) {
            return false;
        }
        Direcciones other = (Direcciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.Direcciones[ id=" + id + " ]";
    }
    
}
