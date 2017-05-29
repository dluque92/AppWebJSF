/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.ejb.MensajeFacade;
import appWebJSF.entity.DatosUsuario;
import appWebJSF.entity.Mensaje;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Daniel
 */
@Named(value = "mensajesBean")
@RequestScoped
public class MensajesBean {

    @EJB
    private MensajeFacade mensajeFacade;

    @EJB
    private DatosUsuarioFacade datosUsuarioFacade;

    @Inject
    private UsuarioBean usuarioBean;

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    private DatosUsuario usuario;
    //private DatosUsuario amigo;
    private List<Mensaje> listaMensajes;
    private List<DatosUsuario> listaAmigos;
    private String idAmigo;
    private List<Mensaje> listaMensajesAmigo = new ArrayList<Mensaje>();
    private Boolean mensajeDisponible = false;
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public MensajeFacade getMensajeFacade() {
        return mensajeFacade;
    }

    public void setMensajeFacade(MensajeFacade mensajeFacade) {
        this.mensajeFacade = mensajeFacade;
    }

    public DatosUsuarioFacade getDatosUsuarioFacade() {
        return datosUsuarioFacade;
    }

    public void setDatosUsuarioFacade(DatosUsuarioFacade datosUsuarioFacade) {
        this.datosUsuarioFacade = datosUsuarioFacade;
    }

    public Boolean getMensajeDisponible() {
        return mensajeDisponible;
    }

    public void setMensajeDisponible(Boolean mensajeDisponible) {
        this.mensajeDisponible = mensajeDisponible;
    }

    public DatosUsuario getAmigo() {
        return this.usuarioBean.getAmigoAListarMensajes();
    }

    public void setAmigo(DatosUsuario amigo) {
        this.usuarioBean.setAmigoAListarMensajes(amigo);
    }

    public List<Mensaje> getListaMensajesAmigo() {
        return listaMensajesAmigo;
    }

    public void setListaMensajesAmigo(List<Mensaje> listaMensajesAmigo) {
        this.listaMensajesAmigo = listaMensajesAmigo;
    }

    public String getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(String idAmigo) {
        this.idAmigo = idAmigo;
    }

    public List<DatosUsuario> getListaAmigos() {
        return listaAmigos;
    }

    public void setListaAmigos(List<DatosUsuario> listaAmigos) {
        this.listaAmigos = listaAmigos;
    }

    public DatosUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(DatosUsuario usuario) {
        this.usuario = usuario;
    }

    public List<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(List<Mensaje> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    /**
     * Creates a new instance of MensajesBean
     */
    public MensajesBean() {
    }

    @PostConstruct
    public void init() {
        usuario = usuarioBean.getUsuario();
        listaMensajes = (List<Mensaje>) usuario.getMensajeCollection();
        listaAmigos = (List<DatosUsuario>) usuario.getMisAmigos();
        if (this.getAmigo() == null && listaMensajes != null && !listaMensajes.isEmpty() && listaAmigos != null) {
            Collections.sort(listaMensajes);
            for (DatosUsuario u : listaMensajes.get(listaMensajes.size() - 1).getDatosUsuarioCollection()) {
                if (!u.equals(usuario)) {
                    idAmigo = u.getIdUsuario().toString();
                    this.setAmigo(this.datosUsuarioFacade.find(new BigDecimal(idAmigo))); 
                }
            }
        }
        if (listaMensajes.isEmpty() && this.getAmigo() == null && !listaAmigos.isEmpty()){
            //this.amigo = this.listaAmigos.get(0);
            this.setAmigo(this.listaAmigos.get(0));
        }
        
        this.obtenerMensajesAmigo(this.usuarioBean.getAmigoAListarMensajes());

        for (Mensaje mensaje : usuario.getMensajeCollection()) {
            if (!mensaje.getMensaje().startsWith(usuario.getEmail()) && mensaje.getLeido() == '0') {
                this.setMensajeDisponible(true);
            }
        }
    }

    private void obtenerMensajesAmigo(DatosUsuario amigo) {
        listaMensajesAmigo.clear();
        for (Mensaje mensaje : usuario.getMensajeCollection()) {
            Collection<DatosUsuario> coleccionParticipantes = mensaje.getDatosUsuarioCollection();
            if (coleccionParticipantes.contains(usuario) && coleccionParticipantes.contains(amigo)) {
                listaMensajesAmigo.add(mensaje);
            }
            if (!mensaje.getMensaje().startsWith(usuario.getEmail())
                    && amigo != null && mensaje.getMensaje().startsWith(amigo.getEmail())) {
                mensaje.setLeido('1');
                this.mensajeFacade.edit(mensaje);
            }
        }
        Collections.sort(listaMensajesAmigo);
    }

    public String cambiarAmigo(DatosUsuario amigo) {
        //this.amigo = amigo;
        this.setAmigo(amigo);
        this.init();
        return "bandejaentrada";
    }

    public Boolean esMensajeDeAmigo(Mensaje mensaje) {
       // return this.getAmigo().getEmail().equals(mensaje.getMensaje().substring(0, this.getAmigo().getEmail().length()));
        return mensaje.getMensaje().startsWith(this.getAmigo().getEmail());
    }

    public String doEnviarMensaje() {
        if (this.getAmigo() != null && this.getMensaje() != null && !mensaje.equals("") && usuario != null) {
            Mensaje mensajeAEnviar = this.mensajeFacade.crearMensaje(usuario.getEmail() + mensaje, usuario, this.getAmigo());
            mensajeAEnviar.setLeido('0');
            this.mensajeFacade.create(mensajeAEnviar);
            usuario.getMensajeCollection().add(mensajeAEnviar);
            this.getAmigo().getMensajeCollection().add(mensajeAEnviar);
            this.datosUsuarioFacade.edit(usuario);
            this.usuarioBean.setUsuario(usuario);
            this.datosUsuarioFacade.edit(this.getAmigo());
        }

        this.setMensaje("");
        this.init();

        return "bandejaentrada";
    }

    public Boolean tieneMensaje(DatosUsuario amigo) {
        boolean tieneMensajes = false;
        for (Mensaje m : usuario.getMensajeCollection()) {
            boolean empieza = !m.getMensaje().startsWith(usuario.getEmail());
            if (m.getLeido() == '0' && empieza && m.getDatosUsuarioCollection().contains(amigo)) {
                tieneMensajes = true;
                break;
            }
        }
        return tieneMensajes;
    }
}
