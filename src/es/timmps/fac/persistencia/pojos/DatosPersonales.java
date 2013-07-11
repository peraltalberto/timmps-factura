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
@Table(name = "datos_personales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosPersonales.findAll", query = "SELECT d FROM DatosPersonales d"),
    @NamedQuery(name = "DatosPersonales.findByCodigo", query = "SELECT d FROM DatosPersonales d WHERE d.codigo = :codigo"),
    @NamedQuery(name = "DatosPersonales.findByNombre", query = "SELECT d FROM DatosPersonales d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "DatosPersonales.findByApellido1", query = "SELECT d FROM DatosPersonales d WHERE d.apellido1 = :apellido1"),
    @NamedQuery(name = "DatosPersonales.findByApellido2", query = "SELECT d FROM DatosPersonales d WHERE d.apellido2 = :apellido2"),
    @NamedQuery(name = "DatosPersonales.findByDni", query = "SELECT d FROM DatosPersonales d WHERE d.dni = :dni"),
    @NamedQuery(name = "DatosPersonales.findByComentario", query = "SELECT d FROM DatosPersonales d WHERE d.comentario = :comentario"),
    @NamedQuery(name = "DatosPersonales.findByRazonSocial", query = "SELECT d FROM DatosPersonales d WHERE d.razonSocial = :razonSocial"),
    @NamedQuery(name = "DatosPersonales.findByNif", query = "SELECT d FROM DatosPersonales d WHERE d.nif = :nif"),
    @NamedQuery(name = "DatosPersonales.findByNombreComercial", query = "SELECT d FROM DatosPersonales d WHERE d.nombreComercial = :nombreComercial")})
public class DatosPersonales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO_1")
    private String apellido1;
    @Basic(optional = false)
    @Column(name = "APELLIDO_2")
    private String apellido2;
    @Basic(optional = false)
    @Column(name = "DNI")
    private String dni;
    @Basic(optional = false)
    @Column(name = "COMENTARIO")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Basic(optional = false)
    @Column(name = "NIF")
    private String nif;
    @Basic(optional = false)
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoPersona")
    private Collection<Direcciones> direccionesCollection;
    @JoinColumn(name = "TIPO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoPersona tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoPersona")
    private Collection<Mails> mailsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoPersona")
    private Collection<Clientes> clientesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoPersona")
    private Collection<Usuarios> usuariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoPersona")
    private Collection<Telefonos> telefonosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "datosPersonales")
    private Collection<DpCustom> dpCustomCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoPersona")
    private Collection<Proveedores> proveedoresCollection;

    public DatosPersonales() {
    }

    public DatosPersonales(Integer codigo) {
        this.codigo = codigo;
    }

    public DatosPersonales(Integer codigo, String nombre, String apellido1, String apellido2, String dni, String comentario, String razonSocial, String nif, String nombreComercial) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        this.comentario = comentario;
        this.razonSocial = razonSocial;
        this.nif = nif;
        this.nombreComercial = nombreComercial;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    @XmlTransient
    public Collection<Direcciones> getDireccionesCollection() {
        return direccionesCollection;
    }

    public void setDireccionesCollection(Collection<Direcciones> direccionesCollection) {
        this.direccionesCollection = direccionesCollection;
    }

    public TipoPersona getTipo() {
        return tipo;
    }

    public void setTipo(TipoPersona tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Mails> getMailsCollection() {
        return mailsCollection;
    }

    public void setMailsCollection(Collection<Mails> mailsCollection) {
        this.mailsCollection = mailsCollection;
    }

    @XmlTransient
    public Collection<Clientes> getClientesCollection() {
        return clientesCollection;
    }

    public void setClientesCollection(Collection<Clientes> clientesCollection) {
        this.clientesCollection = clientesCollection;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @XmlTransient
    public Collection<Telefonos> getTelefonosCollection() {
        return telefonosCollection;
    }

    public void setTelefonosCollection(Collection<Telefonos> telefonosCollection) {
        this.telefonosCollection = telefonosCollection;
    }

    @XmlTransient
    public Collection<DpCustom> getDpCustomCollection() {
        return dpCustomCollection;
    }

    public void setDpCustomCollection(Collection<DpCustom> dpCustomCollection) {
        this.dpCustomCollection = dpCustomCollection;
    }

    @XmlTransient
    public Collection<Proveedores> getProveedoresCollection() {
        return proveedoresCollection;
    }

    public void setProveedoresCollection(Collection<Proveedores> proveedoresCollection) {
        this.proveedoresCollection = proveedoresCollection;
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
        if (!(object instanceof DatosPersonales)) {
            return false;
        }
        DatosPersonales other = (DatosPersonales) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.DatosPersonales[ codigo=" + codigo + " ]";
    }
    
}
