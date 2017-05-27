/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.AficionFacade;
import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.entity.Aficion;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author adri_
 */
@Named(value = "aficionBean")
@RequestScoped
public class AficionBean {

    @EJB
    private DatosUsuarioFacade datosUsuarioFacade;

    @EJB
    private AficionFacade aficionFacade;
    
    /**
     * Creates a new instance of AficionBean
     */
    public AficionBean() {
    }
    
    @Inject 
    private UsuarioBean usuarioBean;
    
    private Aficion nuevaAficion;

    public Aficion getNuevaAficion() {
        return nuevaAficion;
    }

    public void setNuevaAficion(Aficion nuevaAficion) {
        this.nuevaAficion = nuevaAficion;
    }
    
    public String doBorrar(Aficion aficion){
        this.usuarioBean.getUsuario().getEstudioCollection().remove(aficion);
        
        this.aficionFacade.remove(aficion);
        
        this.datosUsuarioFacade.edit(this.usuarioBean.getUsuario());
        
        return "editar";
    }
    
    public String doCrear(){
        if(this.nuevaAficion!=null){
            this.aficionFacade.create(nuevaAficion);
            this.usuarioBean.getUsuario().getAficionCollection().add(nuevaAficion);
            this.datosUsuarioFacade.edit(this.usuarioBean.getUsuario());
            init();
            usuarioBean.cargarUsuario();
        }
        
        return "editar";
    }
    
    @PostConstruct
    public void init(){
        this.nuevaAficion = new Aficion();
        this.nuevaAficion.setDatosUsuarioIdUsuario(usuarioBean.getUsuario());
    }
    
    
    
}
