/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.ejb.EstudioFacade;
import appWebJSF.entity.Aficion;
import appWebJSF.entity.DatosUsuario;
import appWebJSF.entity.Estudio;
import java.util.List;
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
    
    public String doEditar(Estudio estudio) {

        // this.usuarioBean.getUsuario().getAficionCollection().remove(estudio);

        
        DatosUsuario usuario = this.usuarioBean.getUsuario();
        List<Estudio> listaEstudios = (List<Estudio>) usuario.getEstudioCollection();
        int i=0;
        for (Estudio es : listaEstudios) {
            if (es.getIdEstudio().equals(estudio.getIdEstudio())) {
                listaEstudios.remove(estudio);
                break;
            }else{
                i++;
            }
        }
        estudio.setCanEdit(true);
        listaEstudios.add(i,estudio);
        usuario.setEstudioCollection(listaEstudios);
        this.usuarioBean.setUsuario(usuario);
        return "editar";
    }
    
    
    public String doGuardar(Estudio estudio){
        //Moficamos la estudio en la base de datos
        this.estudioFacade.edit(estudio);
        //Modificamos la estudio en local
        DatosUsuario usuario = this.usuarioBean.getUsuario();
        List<Estudio> listaEstudios = (List<Estudio>) usuario.getEstudioCollection();
        int i=0;
        for (Estudio es : listaEstudios) {
            if (es.getIdEstudio().equals(estudio.getIdEstudio())) {
                listaEstudios.remove(estudio);
                break;
            }else{
                i++;
            }
        }
        estudio.setCanEdit(false);
        listaEstudios.add(i,estudio);
        usuario.setEstudioCollection(listaEstudios);
        this.usuarioBean.setUsuario(usuario);
        return "editar";
    }
    
    @PostConstruct
    public void init(){
        this.nuevoEstudio = new Estudio();
        this.nuevoEstudio.setDatosUsuarioIdUsuario(usuarioBean.getUsuario());
    }
}
