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
 * @author johncarlo
 */
@Named(value = "usuarioModificarBean")
@RequestScoped
public class UsuarioModificarBean {

    @EJB
    private DatosUsuarioFacade datosUsuarioFacade;

    @Inject 
    private UsuarioBean usuarioBean;
    private String passAntigua;
    private boolean badPasswordAntigua=false;
    private boolean badPasswordRepetida=false;
    private String passNueva;
    private String passRepetida;
    
    protected DatosUsuario usuarioEnSesion;

    public DatosUsuario getUsuarioEnSesion() {
        return usuarioEnSesion;
    }

    public boolean isBadPasswordAntigua() {
        return badPasswordAntigua;
    }

    public void setBadPasswordAntigua(boolean badPasswordAntigua) {
        this.badPasswordAntigua = badPasswordAntigua;
    }

    public boolean isBadPasswordRepetida() {
        return badPasswordRepetida;
    }

    public void setBadPasswordRepetida(boolean badPasswordRepetida) {
        this.badPasswordRepetida = badPasswordRepetida;
    }

   

    public String getPassAntigua() {
        return passAntigua;
    }

    public void setPassAntigua(String passAntigua) {
        this.passAntigua = passAntigua;
    }

    public String getPassNueva() {
        return passNueva;
    }

    public void setPassNueva(String passNueva) {
        this.passNueva = passNueva;
    }

    public String getPassRepetida() {
        return passRepetida;
    }

    public void setPassRepetida(String passRepetida) {
        this.passRepetida = passRepetida;
    }

    public void setUsuarioEnSesion(DatosUsuario usuarioEnSesion) {
        this.usuarioEnSesion = usuarioEnSesion;
    }
    
    /**
     * Creates a new instance of UsuarioModificarBean
     */
    public UsuarioModificarBean() {
    }
    
    @PostConstruct
    public void init() {
        //Vamos a usar el atributo usuarioEnSesion Para mostrar los datos del usuario en editar.xhtml
        usuarioBean.cargarUsuario();
        //En el CustomerModificarBean
        this.usuarioEnSesion = usuarioBean.getUsuario();
    }
    
    public String doGuardar(){
        //Como las variables son bidireccionales, entonces 
        //La variable "usuarioEnSesion" tiene todos los datos que se han modificado
        if(!passAntigua.isEmpty()){
            if(passAntigua.equals(this.usuarioEnSesion.getPassword())){
                if(this.passNueva.equals(this.passRepetida)){
                    this.usuarioEnSesion.setPassword(passNueva);
                }else{
                    badPasswordRepetida=true;
                }
            }else{
                badPasswordAntigua=true;
            }
        }
        
        this.datosUsuarioFacade.edit(this.usuarioEnSesion);
        
        //En el CustomerBean se hace init() para ver que los datos se han actualizado
        //En cambio nosotros lo tenemos que hacer a mano
        //Puesto que en el init() de UsuarioBean no se actualiza el Usuario
        this.usuarioBean.setUsuario(usuarioEnSesion);
        //Después de guardar los cambios vuelve a index
        if(badPasswordAntigua || badPasswordRepetida){            
            return "editar";           
        }else{           
            return "index";
        }
        
    }
}
