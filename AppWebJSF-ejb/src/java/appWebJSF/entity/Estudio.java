/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrián Cardenas, Adrián Garcia, Daniel Lopez, David Luque, John Purihin
 */
@Entity
@Table(name = "ESTUDIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudio.findAll", query = "SELECT e FROM Estudio e")
    , @NamedQuery(name = "Estudio.findByIdEstudio", query = "SELECT e FROM Estudio e WHERE e.idEstudio = :idEstudio")
    , @NamedQuery(name = "Estudio.findByFechaComienzo", query = "SELECT e FROM Estudio e WHERE e.fechaComienzo = :fechaComienzo")
    , @NamedQuery(name = "Estudio.findByFechaFinalizacion", query = "SELECT e FROM Estudio e WHERE e.fechaFinalizacion = :fechaFinalizacion")
    , @NamedQuery(name = "Estudio.findByDescripcion", query = "SELECT e FROM Estudio e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Estudio.findByUbicacion", query = "SELECT e FROM Estudio e WHERE e.ubicacion = :ubicacion")
    , @NamedQuery(name = "Estudio.findByNombre", query = "SELECT e FROM Estudio e WHERE e.nombre = :nombre")})
public class Estudio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTUDIO")
    //--------Sentencias que habia que poner para hacer un atributo autoincrementable------
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "secuencia_id_estudio")
    @SequenceGenerator(name="secuencia_id_estudio", sequenceName = "SEQ_ID_ESTUDIO", allocationSize=1)
    //--------------------------------------------------------------------------
    private BigDecimal idEstudio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_COMIENZO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaComienzo;
    @Column(name = "FECHA_FINALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 80)
    @Column(name = "UBICACION")
    private String ubicacion;
    @Size(max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "DATOSUSUARIO_ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private DatosUsuario datosUsuarioIdUsuario;
    

    public Estudio() {
    }
    
    @Transient// hace que no se lo traiga de la BD jjejeje
    private boolean canEdit=false;

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Estudio(BigDecimal idEstudio) {
        this.idEstudio = idEstudio;
    }

    public Estudio(BigDecimal idEstudio, Date fechaComienzo, String descripcion) {
        this.idEstudio = idEstudio;
        this.fechaComienzo = fechaComienzo;
        this.descripcion = descripcion;
    }

    public BigDecimal getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(BigDecimal idEstudio) {
        this.idEstudio = idEstudio;
    }

    public Date getFechaComienzo() {
        return fechaComienzo;
    }

    public void setFechaComienzo(Date fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DatosUsuario getDatosUsuarioIdUsuario() {
        return datosUsuarioIdUsuario;
    }

    public void setDatosUsuarioIdUsuario(DatosUsuario datosusuarioIdUsuario) {
        this.datosUsuarioIdUsuario = datosusuarioIdUsuario;
    }
    
    public String getFechaComienzoString() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fechaComienzo);
    }
    
    public String getFechaFinString() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fechaFinalizacion);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstudio != null ? idEstudio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudio)) {
            return false;
        }
        Estudio other = (Estudio) object;
        if ((this.idEstudio == null && other.idEstudio != null) || (this.idEstudio != null && !this.idEstudio.equals(other.idEstudio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "appweb.ejb.Estudio[ idEstudio=" + idEstudio + " ]";
    }
    
}
