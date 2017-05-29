/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrián Cardenas, Adrián Garcia, Daniel Lopez, David Luque, John Purihin
 */
@Entity
@Table(name = "AFICION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aficion.findAll", query = "SELECT a FROM Aficion a")
    , @NamedQuery(name = "Aficion.findByIdAficion", query = "SELECT a FROM Aficion a WHERE a.idAficion = :idAficion")
    , @NamedQuery(name = "Aficion.findByNombre", query = "SELECT a FROM Aficion a WHERE a.nombre = :nombre")})
public class Aficion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_AFICION")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "secuencia_id_aficion")
    @SequenceGenerator(name="secuencia_id_aficion", sequenceName = "SEQ_ID_AFICION", allocationSize=1)
    private BigDecimal idAficion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "DATOSUSUARIO_ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private DatosUsuario datosUsuarioIdUsuario;

    public Aficion() {
    }
    
    @Transient// hace que no se lo traiga de la BD jjejeje
    private boolean canEdit=false;

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    
    

    public Aficion(BigDecimal idAficion) {
        this.idAficion = idAficion;
    }

    public Aficion(BigDecimal idAficion, String nombre) {
        this.idAficion = idAficion;
        this.nombre = nombre;
    }

    public BigDecimal getIdAficion() {
        return idAficion;
    }

    public void setIdAficion(BigDecimal idAficion) {
        this.idAficion = idAficion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAficion != null ? idAficion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aficion)) {
            return false;
        }
        Aficion other = (Aficion) object;
        if ((this.idAficion == null && other.idAficion != null) || (this.idAficion != null && !this.idAficion.equals(other.idAficion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "appweb.ejb.Aficion[ idAficion=" + idAficion + " ]";
    }
    
}
