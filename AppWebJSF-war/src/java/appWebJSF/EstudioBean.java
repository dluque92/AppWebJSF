/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.ejb.EstudioFacade;
import appWebJSF.entity.Estudio;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Daniel
 */
@Named(value = "estudioBean")
@RequestScoped
public class EstudioBean {

    @EJB
    private EstudioFacade estudioFacade;
    
     @EJB
    private DatosUsuarioFacade datosUsuarioFacade;

    @Inject 
    private UsuarioBean usuarioBean;
    
    private Estudio nuevoEstudio;

    /**
     * Creates a new instance of EstudioBean
     */
    
    public EstudioBean() {
    }

    public Estudio getNuevoEstudio() {
        return nuevoEstudio;
    }

    public void setNuevoEstudio(Estudio nuevoEstudio) {
        this.nuevoEstudio = nuevoEstudio;
    }
    
    
    
    public String doBorrar(Estudio estudio){
        this.usuarioBean.getUsuario().getEstudioCollection().remove(estudio);
        
        this.estudioFacade.remove(estudio);
        
        this.datosUsuarioFacade.edit(this.usuarioBean.getUsuario());
        
        return "editar";
    }
    
    public String doCrear(){
        if(this.nuevoEstudio != null){
            this.estudioFacade.create(nuevoEstudio);
            this.usuarioBean.getUsuario().getEstudioCollection().add(nuevoEstudio);
            this.datosUsuarioFacade.edit(this.usuarioBean.getUsuario());
            init();
            usuarioBean.cargarUsuario();
        }
        
        return "editar";
    }
    
    @PostConstruct
    public void init(){
        this.nuevoEstudio = new Estudio();
        this.nuevoEstudio.setDatosUsuarioIdUsuario(usuarioBean.getUsuario());
    }
}
