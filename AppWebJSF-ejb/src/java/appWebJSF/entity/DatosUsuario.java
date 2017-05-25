/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adrián Cardenas, Adrián Garcia, Daniel Lopez, David Luque, John Purihin
 */
@Entity
@Table(name = "DATOSUSUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosUsuario.findAll", query = "SELECT d FROM DatosUsuario d")
    , @NamedQuery(name = "DatosUsuario.findByIdUsuario", query = "SELECT d FROM DatosUsuario d WHERE d.idUsuario = :idUsuario")
    , @NamedQuery(name = "DatosUsuario.findByEmail", query = "SELECT d FROM DatosUsuario d WHERE d.email = :email")
    , @NamedQuery(name = "DatosUsuario.findByPassword", query = "SELECT d FROM DatosUsuario d WHERE d.password = :password")
    , @NamedQuery(name = "DatosUsuario.findByNombre", query = "SELECT d FROM DatosUsuario d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DatosUsuario.findByApellidos", query = "SELECT d FROM DatosUsuario d WHERE d.apellidos = :apellidos")
    , @NamedQuery(name = "DatosUsuario.findByTwitter", query = "SELECT d FROM DatosUsuario d WHERE d.twitter = :twitter")
    , @NamedQuery(name = "DatosUsuario.findByInstagram", query = "SELECT d FROM DatosUsuario d WHERE d.instagram = :instagram")
    , @NamedQuery(name = "DatosUsuario.findByWeb", query = "SELECT d FROM DatosUsuario d WHERE d.web = :web")
    , @NamedQuery(name = "DatosUsuario.findByFoto", query = "SELECT d FROM DatosUsuario d WHERE d.foto = :foto")
    , @NamedQuery(name = "DatosUsuario.findByBanner", query = "SELECT d FROM DatosUsuario d WHERE d.banner = :banner")
    , @NamedQuery(name = "DatosUsuario.findByVisitas", query = "SELECT d FROM DatosUsuario d WHERE d.visitas = :visitas")
    , @NamedQuery(name = "DatosUsuario.findByDescripcion", query = "SELECT d FROM DatosUsuario d WHERE d.descripcion = :descripcion")})
public class DatosUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USUARIO")
    //--------Sentencias que habia que poner para hacer un atributo autoincrementable------
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "secuencia_id_usuario")
    @SequenceGenerator(name="secuencia_id_usuario", sequenceName = "SEQ_ID_DATOSUSUARIO", allocationSize=1)
    //--------------------------------------------------------------------------
    private BigDecimal idUsuario;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 90)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Size(max = 16)
    @Column(name = "TWITTER")
    private String twitter;
    @Size(max = 30)
    @Column(name = "INSTAGRAM")
    private String instagram;
    @Size(max = 120)
    @Column(name = "WEB")
    private String web;
    @Size(max = 120)
    @Column(name = "FOTO")
    private String foto;
    @Size(max = 120)
    @Column(name = "BANNER")
    private String banner;
    @Column(name = "VISITAS")
    private BigInteger visitas;
    @Size(max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinTable(name = "PETICIONES", joinColumns = {
        @JoinColumn(name = "USUARIO_ID_USUARIO", referencedColumnName = "ID_USUARIO")}, inverseJoinColumns = {
        @JoinColumn(name = "USUARIO_ID_USUARIO1", referencedColumnName = "ID_USUARIO")})
    @ManyToMany
    private Collection<DatosUsuario> peticionesRecibidas;

    public Collection<DatosUsuario> getPeticionesRecibidas() {
        return peticionesRecibidas;
    }

    public void setPeticionesRecibidas(Collection<DatosUsuario> peticionesRecibidas) {
        this.peticionesRecibidas = peticionesRecibidas;
        }

    public Collection<DatosUsuario> getPeticionesEnviadas() {
        return peticionesEnviadas;
    }

    public void setPeticionesEnviadas(Collection<DatosUsuario> peticionesEnviadas) {
        this.peticionesEnviadas = peticionesEnviadas;
    }

    public Collection<DatosUsuario> getMisAmigos() {
        return misAmigos;
    }

    public void setMisAmigos(Collection<DatosUsuario> misAmigos) {
        this.misAmigos = misAmigos;
    }

    public Collection<DatosUsuario> getSoyAmigoDe() {
        return soyAmigoDe;
    }

    public void setSoyAmigoDe(Collection<DatosUsuario> soyAmigoDe) {
        this.soyAmigoDe = soyAmigoDe;
    }
    @ManyToMany(mappedBy = "peticionesRecibidas")
    private Collection<DatosUsuario> peticionesEnviadas;
    @JoinTable(name = "AMIGOS", joinColumns = {
        @JoinColumn(name = "USUARIO_ID_USUARIO", referencedColumnName = "ID_USUARIO")}, inverseJoinColumns = {
        @JoinColumn(name = "USUARIO_ID_USUARIO1", referencedColumnName = "ID_USUARIO")})
    @ManyToMany
    private Collection<DatosUsuario> misAmigos;
    @ManyToMany(mappedBy = "misAmigos")
    private Collection<DatosUsuario> soyAmigoDe;
    @ManyToMany(mappedBy = "datosUsuarioCollection")
    private Collection<Mensaje> mensajeCollection;
    @OneToMany(mappedBy = "datosUsuarioIdUsuario")
    private Collection<Aficion> aficionCollection;
    @OneToMany(mappedBy = "datosUsuarioIdUsuario")
    private Collection<Experiencia> experienciaCollection;
    @OneToMany(mappedBy = "datosUsuarioIdUsuario")
    private Collection<Estudio> estudioCollection;

    public DatosUsuario() {
    }

    public DatosUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public DatosUsuario(BigDecimal idUsuario, String email, String password, String nombre, String apellidos) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public BigInteger getVisitas() {
        return visitas;
    }

    public void setVisitas(BigInteger visitas) {
        this.visitas = visitas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    @XmlTransient
    public Collection<Mensaje> getMensajeCollection() {
        return mensajeCollection;
    }

    public void setMensajeCollection(Collection<Mensaje> mensajeCollection) {
        this.mensajeCollection = mensajeCollection;
    }

    @XmlTransient
    public Collection<Aficion> getAficionCollection() {
        return aficionCollection;
    }

    public void setAficionCollection(Collection<Aficion> aficionCollection) {
        this.aficionCollection = aficionCollection;
    }

    @XmlTransient
    public Collection<Experiencia> getExperienciaCollection() {
        return experienciaCollection;
    }

    public void setExperienciaCollection(Collection<Experiencia> experienciaCollection) {
        this.experienciaCollection = experienciaCollection;
    }

    @XmlTransient
    public Collection<Estudio> getEstudioCollection() {
        return estudioCollection;
    }

    public void setEstudioCollection(Collection<Estudio> estudioCollection) {
        this.estudioCollection = estudioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatosUsuario)) {
            return false;
        }
        DatosUsuario other = (DatosUsuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "appweb.ejb.DatosUsuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
