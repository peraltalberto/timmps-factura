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
@Table(name = "contadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contadores.findAll", query = "SELECT c FROM Contadores c"),
    @NamedQuery(name = "Contadores.findById", query = "SELECT c FROM Contadores c WHERE c.id = :id"),
    @NamedQuery(name = "Contadores.findByDescripcion", query = "SELECT c FROM Contadores c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Contadores.findByValor", query = "SELECT c FROM Contadores c WHERE c.valor = :valor"),
    @NamedQuery(name = "Contadores.findByInicio", query = "SELECT c FROM Contadores c WHERE c.inicio = :inicio"),
    @NamedQuery(name = "Contadores.findByReserva", query = "SELECT c FROM Contadores c WHERE c.reserva = :reserva"),
    @NamedQuery(name = "Contadores.findByCiclo", query = "SELECT c FROM Contadores c WHERE c.ciclo = :ciclo"),
    @NamedQuery(name = "Contadores.findByValorCiclo", query = "SELECT c FROM Contadores c WHERE c.valorCiclo = :valorCiclo"),
    @NamedQuery(name = "Contadores.findBySuma", query = "SELECT c FROM Contadores c WHERE c.suma = :suma")})
public class Contadores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private int valor;
    @Basic(optional = false)
    @Column(name = "INICIO")
    private int inicio;
    @Basic(optional = false)
    @Column(name = "RESERVA")
    private int reserva;
    @Basic(optional = false)
    @Column(name = "CICLO")
    private int ciclo;
    @Basic(optional = false)
    @Column(name = "VALOR_CICLO")
    private int valorCiclo;
    @Basic(optional = false)
    @Column(name = "SUMA")
    private int suma;
    @JoinColumn(name = "COD_EMP", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Empresas codEmp;

    public Contadores() {
    }

    public Contadores(String id) {
        this.id = id;
    }

    public Contadores(String id, String descripcion, int valor, int inicio, int reserva, int ciclo, int valorCiclo, int suma) {
        this.id = id;
        this.descripcion = descripcion;
        this.valor = valor;
        this.inicio = inicio;
        this.reserva = reserva;
        this.ciclo = ciclo;
        this.valorCiclo = valorCiclo;
        this.suma = suma;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getReserva() {
        return reserva;
    }

    public void setReserva(int reserva) {
        this.reserva = reserva;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public int getValorCiclo() {
        return valorCiclo;
    }

    public void setValorCiclo(int valorCiclo) {
        this.valorCiclo = valorCiclo;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
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
        if (!(object instanceof Contadores)) {
            return false;
        }
        Contadores other = (Contadores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.timmps.fac.persistencia.pojos.Contadores[ id=" + id + " ]";
    }
    
}
