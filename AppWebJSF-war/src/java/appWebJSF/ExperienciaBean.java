/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.ejb.ExperienciaFacade;
import appWebJSF.entity.Experiencia;
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
@Named(value = "experienciaBean")
@RequestScoped
public class ExperienciaBean {

    @EJB
    private ExperienciaFacade experienciaFacade;
    
     @EJB
    private DatosUsuarioFacade datosUsuarioFacade;

    @Inject 
    private UsuarioBean usuarioBean;
    
    private Experiencia nuevaExperiencia;

    /**
     * Creates a new instance of EstudioBean
     */
    
    public ExperienciaBean() {
    }

    public Experiencia getNuevaExperiencia() {
        return nuevaExperiencia;
    }

    public void setNuevaExperiencia(Experiencia nuevaExperiencia) {
        this.nuevaExperiencia = nuevaExperiencia;
    }
    
    
    
    public String doBorrar(Experiencia experiencia){
        this.usuarioBean.getUsuario().getExperienciaCollection().remove(experiencia);
        
        this.experienciaFacade.remove(experiencia);
        
        this.datosUsuarioFacade.edit(this.usuarioBean.getUsuario());
        
        return "editar";
    }
    
    public String doCrear(){
        if(this.nuevaExperiencia != null){
            this.experienciaFacade.create(nuevaExperiencia);
            this.usuarioBean.getUsuario().getExperienciaCollection().add(nuevaExperiencia);
            this.datosUsuarioFacade.edit(this.usuarioBean.getUsuario());
            init();
            usuarioBean.cargarUsuario();
        }
        
        return "editar";
    }
    
    @PostConstruct
    public void init(){
        this.nuevaExperiencia = new Experiencia();
        this.nuevaExperiencia.setDatosUsuarioIdUsuario(usuarioBean.getUsuario());
    }
}
