/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.AficionFacade;
import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.entity.Aficion;
import appWebJSF.entity.DatosUsuario;
import java.util.List;
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
    
    @Inject 
    private UsuarioBean usuarioBean;
    
    private Aficion nuevaAficion;
    /**
     * Creates a new instance of AficionBean
     */
    public AficionBean() {
    }
    
    

    public Aficion getNuevaAficion() {
        return nuevaAficion;
    }

    public void setNuevaAficion(Aficion nuevaAficion) {
        this.nuevaAficion = nuevaAficion;
    }
    
    public String doBorrar(Aficion aficion){
        this.usuarioBean.getUsuario().getAficionCollection().remove(aficion);
        
        this.aficionFacade.remove(aficion);
        
        this.datosUsuarioFacade.edit(this.usuarioBean.getUsuario());
        
        return "editar";
    }
    
    public String doCrear(){
        if(this.nuevaAficion != null){
            this.aficionFacade.create(nuevaAficion);
            this.usuarioBean.getUsuario().getAficionCollection().add(nuevaAficion);
            this.datosUsuarioFacade.edit(this.usuarioBean.getUsuario());
            init();
            usuarioBean.cargarUsuario();
        }
        
        return "editar";
    }
    
     public String doEditar(Aficion aficion) {

        // this.usuarioBean.getUsuario().getAficionCollection().remove(aficion);

        
        DatosUsuario usuario = this.usuarioBean.getUsuario();
        List<Aficion> listaAficiones = (List<Aficion>) usuario.getAficionCollection();
        int i=0;
        for (Aficion af : listaAficiones) {
            if (af.getIdAficion().equals(aficion.getIdAficion())) {
                listaAficiones.remove(aficion);
                break;
            }else{
                i++;
            }
        }
        aficion.setCanEdit(true);
        listaAficiones.add(i,aficion);
        usuario.setAficionCollection(listaAficiones);
        this.usuarioBean.setUsuario(usuario);
        return "editar";
    }
    
    public String doGuardar(Aficion aficion){
        //Moficamos la aficion en la base de datos
        this.aficionFacade.edit(aficion);
        //Modificamos la aficion en local
        DatosUsuario usuario = this.usuarioBean.getUsuario();
        List<Aficion> listaAficiones = (List<Aficion>) usuario.getAficionCollection();
        int i=0;
        for (Aficion af : listaAficiones) {
            if (af.getIdAficion().equals(aficion.getIdAficion())) {
                listaAficiones.remove(aficion);
                break;
            }else{
                i++;
            }
        }
        aficion.setCanEdit(false);
        listaAficiones.add(i,aficion);
        usuario.setAficionCollection(listaAficiones);
        this.usuarioBean.setUsuario(usuario);
        return "editar";
    }

    @PostConstruct
    public void init() {
        this.nuevaAficion = new Aficion();
        this.nuevaAficion.setDatosUsuarioIdUsuario(usuarioBean.getUsuario());
    }
}
