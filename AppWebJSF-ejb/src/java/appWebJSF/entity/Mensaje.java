/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adrián Cardenas, Adrián Garcia, Daniel Lopez, David Luque, John Purihin
 */
@Entity
@Table(name = "MENSAJE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m")
    , @NamedQuery(name = "Mensaje.findByIdMensaje", query = "SELECT m FROM Mensaje m WHERE m.idMensaje = :idMensaje")
    , @NamedQuery(name = "Mensaje.findByMensaje", query = "SELECT m FROM Mensaje m WHERE m.mensaje = :mensaje")
    , @NamedQuery(name = "Mensaje.findByLeido", query = "SELECT m FROM Mensaje m WHERE m.leido = :leido")
    , @NamedQuery(name = "Mensaje.findByFecha", query = "SELECT m FROM Mensaje m WHERE m.fecha = :fecha")})
public class Mensaje implements Serializable, Comparable<Mensaje> {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MENSAJE")
    //--------Sentencias que habia que poner para hacer un atributo autoincrementable------
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_id_mensaje")
    @SequenceGenerator(name = "secuencia_id_mensaje", sequenceName = "SEQ_ID_MENSAJE", allocationSize = 1)
    //--------------------------------------------------------------------------
    private BigDecimal idMensaje;
    @Size(max = 300)
    @Column(name = "MENSAJE")
    private String mensaje;
    @Column(name = "LEIDO")
    private Character leido;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinTable(name = "COMUNICACION", joinColumns = {
        @JoinColumn(name = "MENSAJE_ID_MENSAJE", referencedColumnName = "ID_MENSAJE")}, inverseJoinColumns = {
        @JoinColumn(name = "USUARIO_ID_USUARIO", referencedColumnName = "ID_USUARIO")})
    @ManyToMany
    private Collection<DatosUsuario> datosUsuarioCollection;

    public Mensaje() {
    }

    public Mensaje(BigDecimal idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Mensaje(BigDecimal idMensaje, String texto, DatosUsuario usuario, DatosUsuario amigo) {
        this.idMensaje = idMensaje;
        this.mensaje = texto;
        List<DatosUsuario> datosUsuarioList = new ArrayList<>();
        datosUsuarioList.add(usuario);
        datosUsuarioList.add(amigo);
        Date f = new Date();
        this.fecha = f;
        this.datosUsuarioCollection = datosUsuarioList;
        //OJOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO CON ESTO
        //SI TENEMOS UNA COLECCIÓN TENEMOS QUE CREAR LA COLECCION, AÑADIR LOS ELEMENTOS Y ASIGNAR ESA COLECCION AL ATRIBUTO
    }

    public BigDecimal getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(BigDecimal idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Character getLeido() {
        return leido;
    }

    public void setLeido(Character leido) {
        this.leido = leido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public Collection<DatosUsuario> getDatosUsuarioCollection() {
        return datosUsuarioCollection;
    }

    public void setDatosUsuarioCollection(Collection<DatosUsuario> datosusuarioCollection) {
        this.datosUsuarioCollection = datosusuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensaje != null ? idMensaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensaje)) {
            return false;
        }
        Mensaje other = (Mensaje) object;
        if ((this.idMensaje == null && other.idMensaje != null) || (this.idMensaje != null && !this.idMensaje.equals(other.idMensaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "appweb.ejb.Mensaje[ idMensaje=" + idMensaje + " ]";
    }

    @Override
    public int compareTo(Mensaje m) {
        return fecha.compareTo(m.fecha);
    }

}
