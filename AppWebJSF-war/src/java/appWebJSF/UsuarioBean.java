/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.entity.DatosUsuario;
import appWebJSF.entity.Mensaje;
import appweb.security.hash;
import dropbox.DropboxController;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author adri_
 */
@Named(value = "UsuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    @EJB
    private DatosUsuarioFacade datosUsuarioFacade;

    private DatosUsuario amigoAListarMensajes;
    private String busqueda;
    private String email = "";
    private String password = "";
    private DatosUsuario usuarioVisitado;
    private DatosUsuario usuario;
    private Boolean error = false;
    private Boolean errorRegistrar = false;
    private List<DatosUsuario> amigosAmostrar;
    private List<DatosUsuario> usuariosPorNombre = null;
    private List<DatosUsuario> usuariosPorAficion = null;
    private List<DatosUsuario> usuariosPorExperiencia = null;
    private List<DatosUsuario> usuarioPorEstudio = null;
    private Map<BigDecimal, String> fotosUsuarios = new HashMap<>();
    private Boolean resetPassword = false;
    
    public Boolean getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(Boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    public Boolean getErrorRegistrar() {
        return errorRegistrar;
    }

    public void setErrorRegistrar(Boolean errorRegistrar) {
        this.errorRegistrar = errorRegistrar;
    }

    public List<DatosUsuario> getAmigosAmostrar() {
        List<DatosUsuario> misAmigos = new ArrayList();
        misAmigos.addAll(usuarioVisitado.getMisAmigos());
        if (usuarioVisitado.getMisAmigos().size() > 5) {
            Random alea = new Random();
            amigosAmostrar = new ArrayList<>(5);
            while (amigosAmostrar.size() < 5) {
                DatosUsuario u = misAmigos.get(alea.nextInt(usuarioVisitado.getMisAmigos().size()));
                if (!amigosAmostrar.contains(u)) {
                    amigosAmostrar.add(u);
                }
            }
            return amigosAmostrar;
        } else {
            return misAmigos;
        }
    }

    public DatosUsuario getAmigoAListarMensajes() {
        return amigoAListarMensajes;
    }

    public void setAmigoAListarMensajes(DatosUsuario amigoAListarMensajes) {
        this.amigoAListarMensajes = amigoAListarMensajes;
    }

    public String getFoto(DatosUsuario user) {
        if (!fotosUsuarios.containsKey(user.getIdUsuario())) {
            fotosUsuarios.put(user.getIdUsuario(), DropboxController.getUrl(user.getFoto()));
        }
        return fotosUsuarios.get(user.getIdUsuario());
    }

    public DatosUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(DatosUsuario usuario) {
        this.usuario = usuario;
    }

    public DatosUsuario getUsuarioVisitado() {
        return usuarioVisitado;
    }

    public void setUsuarioVisitado(DatosUsuario usuarioVisitado) {
        this.usuarioVisitado = usuarioVisitado;
    }
    
    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
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

    public UsuarioBean() {
    }

    public String doLogin() {
        if (email == null || password == null || email.equals("") || password.equals("")) {
            limpiarCampos();
            return "login";
        } else {
            this.password = hash.stringToHash(password);
            cargarUsuario();
            cargarUsuarioVisitado();
            if (usuario == null) {
                error = true;
                limpiarCampos();
                return "login";
            } else {
                getFoto(usuarioVisitado);
                error = false;
                return "index";
            }
        }
    }

    public String doLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public void limpiarCampos() {
        email = "";
        password = "";
    }

    public String volverAIndex() {
        cargarUsuario();
        this.usuarioVisitado = this.usuario;//??
        return "index";
    }

    public void cargarUsuario() {
        this.setUsuario(this.datosUsuarioFacade.obtenerUsuario(email, password));
    }
    
     public void cargarUsuarioVisitado() {
        this.setUsuarioVisitado(this.datosUsuarioFacade.obtenerUsuario(email, password));
    }
    
    

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<DatosUsuario> getUsuariosPorNombre() {
        return usuariosPorNombre;
    }

    public void setUsuariosPorNombre(List<DatosUsuario> usuariosPorNombre) {
        this.usuariosPorNombre = usuariosPorNombre;
    }

    public List<DatosUsuario> getUsuariosPorAficion() {
        return usuariosPorAficion;
    }

    public void setUsuariosPorAficion(List<DatosUsuario> usuariosPorAficion) {
        this.usuariosPorAficion = usuariosPorAficion;
    }

    public List<DatosUsuario> getUsuariosPorExperiencia() {
        return usuariosPorExperiencia;
    }

    public void setUsuariosPorExperiencia(List<DatosUsuario> usuariosPorExperiencia) {
        this.usuariosPorExperiencia = usuariosPorExperiencia;
    }

    public List<DatosUsuario> getUsuarioPorEstudio() {
        return usuarioPorEstudio;
    }

    public void setUsuarioPorEstudio(List<DatosUsuario> usuarioPorEstudio) {
        this.usuarioPorEstudio = usuarioPorEstudio;
    }

    public String getTwitter() {
        return "http://www.twitter.com/" + usuarioVisitado.getTwitter();
    }

    public String getInstagram() {
        return "http://www.instagram.com/" + usuarioVisitado.getInstagram();
    }

    public String getWeb() {
        return "http://www." + usuarioVisitado.getWeb();
    }

    public String hacerBusqueda() {
        cargarUsuario();
        setUsuarioPorEstudio(this.datosUsuarioFacade.findByEstudios(busqueda, getUsuario().getIdUsuario()));
        setUsuariosPorAficion(this.datosUsuarioFacade.findByAficion(busqueda, getUsuario().getIdUsuario()));
        setUsuariosPorNombre(this.datosUsuarioFacade.findByName(busqueda, getUsuario().getIdUsuario()));
        setUsuariosPorExperiencia(this.datosUsuarioFacade.findByExperiencia(busqueda, getUsuario().getIdUsuario()));
        busqueda = "";
        return "buscar";
    }
    
    public String irAPeticiones(){
        cargarUsuario();
        return "peticiones";
    }
    
    public String irABandejaEntrada(){
        cargarUsuario();
        return "bandejaentrada";
    }
    
    public Boolean NoCoincideEmailYEsAmigo(){
        return !coincideEmail() && usuario.getMisAmigos().contains(usuarioVisitado);
    }
    
    public Boolean coincideEmail(){
        return this.usuarioVisitado.getEmail().equals(this.usuario.getEmail());
    }
    
    public Boolean tienePeticionDeUsuario(){
        //DatosUsuario u = this.datosUsuarioFacade.obtenerUsuario(this.email, this.password);
        return !coincideEmail()&&usuario.getPeticionesEnviadas().contains(usuarioVisitado)&&!sonAmigos(usuario);
    }
    
    public Boolean sonAmigos(DatosUsuario u){
        return u.getMisAmigos().contains(this.usuario);
    }
    
    public Boolean noTienePeticionDeUsuario(){
        //DatosUsuario u = this.datosUsuarioFacade.obtenerUsuario(this.email, this.password);
        return !coincideEmail()&&!usuario.getPeticionesEnviadas().contains(usuarioVisitado)&& !sonAmigos(usuarioVisitado);
    }
    
    public String doEnviarMensaje(DatosUsuario amigo){
        this.amigoAListarMensajes = amigo;
        this.cargarUsuario();
        return "bandejaentrada";
    }
    
    public List<Mensaje> getMensajesAmigos(){
        List<Mensaje> mensajes = new ArrayList<>();
        for(Mensaje m : this.getUsuario().getMensajeCollection()) {
            boolean empieza = !m.getMensaje().startsWith(this.getUsuario().getIdUsuario().toString());
            if (m.getLeido() == '0' && empieza) {
                mensajes.add(m);
            }
        }
        return mensajes;
    }
}
