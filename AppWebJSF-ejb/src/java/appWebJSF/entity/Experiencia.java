/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "EXPERIENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Experiencia.findAll", query = "SELECT e FROM Experiencia e")
    , @NamedQuery(name = "Experiencia.findByIdExperiencia", query = "SELECT e FROM Experiencia e WHERE e.idExperiencia = :idExperiencia")
    , @NamedQuery(name = "Experiencia.findByFechaComienzo", query = "SELECT e FROM Experiencia e WHERE e.fechaComienzo = :fechaComienzo")
    , @NamedQuery(name = "Experiencia.findByFechaFinalizacion", query = "SELECT e FROM Experiencia e WHERE e.fechaFinalizacion = :fechaFinalizacion")
    , @NamedQuery(name = "Experiencia.findByEmpresa", query = "SELECT e FROM Experiencia e WHERE e.empresa = :empresa")
    , @NamedQuery(name = "Experiencia.findByWebEmpresa", query = "SELECT e FROM Experiencia e WHERE e.webEmpresa = :webEmpresa")
    , @NamedQuery(name = "Experiencia.findByPuesto", query = "SELECT e FROM Experiencia e WHERE e.puesto = :puesto")})
public class Experiencia implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EXPERIENCIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_id_experiencia")
    @SequenceGenerator(name = "secuencia_id_experiencia", sequenceName = "SEQ_ID_EXPERIENCIA", allocationSize = 1)
    private BigDecimal idExperiencia;
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
    @Size(min = 1, max = 60)
    @Column(name = "EMPRESA")
    private String empresa;
    @Size(max = 120)
    @Column(name = "WEB_EMPRESA")
    private String webEmpresa;
    @Size(max = 80)
    @Column(name = "PUESTO")
    private String puesto;
    @JoinColumn(name = "DATOSUSUARIO_ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private DatosUsuario datosUsuarioIdUsuario;

    public Experiencia() {
    }
    
    @Transient// hace que no se lo traiga de la BD jjejeje
    private boolean canEdit=false;

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Experiencia(BigDecimal idExperiencia) {
        this.idExperiencia = idExperiencia;
    }

    public Experiencia(BigDecimal idExperiencia, Date fechaComienzo, String empresa) {
        this.idExperiencia = idExperiencia;
        this.fechaComienzo = fechaComienzo;
        this.empresa = empresa;
    }

    public BigDecimal getIdExperiencia() {
        return idExperiencia;
    }

    public void setIdExperiencia(BigDecimal idExperiencia) {
        this.idExperiencia = idExperiencia;
    }

    public Date getFechaComienzo() {
        return fechaComienzo;
    }

    public String getFechaComienzoString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fechaComienzo);
    }

    public void setFechaComienzo(Date fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public String getFechaFinalizacionString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fechaFinalizacion);
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getWebEmpresa() {
        return webEmpresa;
    }

    public void setWebEmpresa(String webEmpresa) {
        this.webEmpresa = webEmpresa;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public DatosUsuario getDatosUsuarioIdUsuario() {
        return datosUsuarioIdUsuario;
    }

    public void setDatosUsuarioIdUsuario(DatosUsuario datosusuarioIdUsuario) {
        this.datosUsuarioIdUsuario = datosusuarioIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExperiencia != null ? idExperiencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Experiencia)) {
            return false;
        }
        Experiencia other = (Experiencia) object;
        if ((this.idExperiencia == null && other.idExperiencia != null) || (this.idExperiencia != null && !this.idExperiencia.equals(other.idExperiencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "appweb.ejb.Experiencia[ idExperiencia=" + idExperiencia + " ]";
    }

}
