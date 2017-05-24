/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.entity.DatosUsuario;
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
    
    private DatosUsuario usuarioEnSesion;
    
    
    /**
     * Creates a new instance of PeticionBean
     */
    public PeticionBean() {
    }
    
    public String enviarPeticion(){
        usuarioEnSesion = this.datosUsuarioFacade.obtenerUsuario(usuarioBean.getEmail(), usuarioBean.getPassword());
        usuarioEnSesion.getPeticionesEnviadas().add(usuarioBean.getUsuario());
        usuarioBean.getUsuario().getPeticionesRecibidas().add(usuarioEnSesion);
        this.datosUsuarioFacade.edit(usuarioEnSesion);
        this.datosUsuarioFacade.edit(usuarioBean.getUsuario());
        return "index";
    }
    
}
