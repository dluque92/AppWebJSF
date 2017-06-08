/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.entity.DatosUsuario;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author adri_
 */
@Named(value = "peticionBean")
@RequestScoped
public class PeticionBean {

    @EJB
    private DatosUsuarioFacade datosUsuarioFacade;

    @Inject
    private UsuarioBean usuarioBean;

    private DatosUsuario usuarioEnviarPeticion;

    /**
     * Creates a new instance of PeticionBean
     */
    public PeticionBean() {
    }

    public String enviarPeticion() {
        usuarioBean.getUsuario().getPeticionesEnviadas().add(usuarioBean.getUsuarioVisitado());
        usuarioBean.getUsuarioVisitado().getPeticionesRecibidas().add(usuarioBean.getUsuario());
        this.datosUsuarioFacade.edit(usuarioBean.getUsuarioVisitado());
        this.datosUsuarioFacade.edit(usuarioBean.getUsuario());
        return "index";
    }

    public String rechazarPeticion(DatosUsuario nuevoAmigo) {
        usuarioBean.cargarUsuario();
        usuarioBean.getUsuario().getPeticionesRecibidas().remove(nuevoAmigo);
        nuevoAmigo.getPeticionesEnviadas().remove(usuarioBean.getUsuario());
        this.datosUsuarioFacade.edit(usuarioBean.getUsuario());
        this.datosUsuarioFacade.edit(nuevoAmigo);
        return "peticiones";
    }

    public String aceptarPeticion(DatosUsuario nuevoAmigo) {
        usuarioBean.cargarUsuario();
        usuarioBean.getUsuario().getMisAmigos().add(nuevoAmigo);
        nuevoAmigo.getSoyAmigoDe().add(usuarioBean.getUsuario());
        usuarioBean.getUsuario().getSoyAmigoDe().add(nuevoAmigo);
        nuevoAmigo.getMisAmigos().add(usuarioBean.getUsuario());

        usuarioBean.getUsuario().getPeticionesRecibidas().remove(nuevoAmigo);
        nuevoAmigo.getPeticionesEnviadas().remove(usuarioBean.getUsuario());

        this.datosUsuarioFacade.edit(usuarioBean.getUsuario());
        this.datosUsuarioFacade.edit(nuevoAmigo);
        return "peticiones";
    }
    
    public String cancelarPeticion(){
        usuarioBean.getUsuario().getPeticionesEnviadas().remove(usuarioBean.getUsuarioVisitado());
        usuarioBean.getUsuarioVisitado().getPeticionesRecibidas().remove(usuarioBean.getUsuario());
        this.datosUsuarioFacade.edit(usuarioBean.getUsuario());
        this.datosUsuarioFacade.edit(usuarioBean.getUsuarioVisitado());
        return "index";
    }

}
