/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.entity.DatosUsuario;
import appweb.security.hash;
import dropbox.DropboxController;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

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
    private boolean badPasswordAntigua = false;
    private boolean badPasswordRepetida = false;
    private String passNueva;
    private String passRepetida;
    private Part archivo;

    protected DatosUsuario usuarioEnSesion;

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

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
        //usuarioBean.cargarUsuario();
        //En el CustomerModificarBean
        this.usuarioEnSesion = usuarioBean.getUsuario();
    }

    public String doGuardar() {
        //Como las variables son bidireccionales, entonces 
        //La variable "usuarioEnSesion" tiene todos los datos que se han modificado
        if (!passAntigua.isEmpty()) {
            if (hash.stringToHash(passAntigua).equals(this.usuarioEnSesion.getPassword())) {
                if (this.passNueva.equals(this.passRepetida) && !this.passNueva.isEmpty()) {
                    this.usuarioEnSesion.setPassword(hash.stringToHash(passNueva));
                } else if(!this.passNueva.equals(this.passRepetida) && !this.passNueva.isEmpty()){
                    badPasswordRepetida = true;
                }
            } else {
                badPasswordAntigua = true;
            }
        }

        String nombreFoto;
        if (archivo != null) {
            nombreFoto = getUsuarioEnSesion().getEmail() + ".jpg";
            try {
                InputStream fileContent = archivo.getInputStream();
                DropboxController.overwriteFile(nombreFoto, fileContent);
            } catch (IOException ex) {
                //Fallo al subir la foto
            }
            //Fallo en Dropbox
        }

        if (badPasswordAntigua || badPasswordRepetida) {
            return "editar";
        } else {
            this.datosUsuarioFacade.edit(this.usuarioEnSesion);
            if (!usuarioEnSesion.getEmail().equals(usuarioBean.getUsuario().getEmail())) {
                this.usuarioBean.setEmail(this.usuarioEnSesion.getEmail());
            }
            //this.usuarioEnSesion = this.datosUsuarioFacade.find(this.usuarioEnSesion.getIdUsuario());
            //En el CustomerBean se hace init() para ver que los datos se han actualizado
            //En cambio nosotros lo tenemos que hacer a mano
            //Puesto que en el init() de UsuarioBean no se actualiza el Usuario
            this.usuarioBean.setUsuario(usuarioEnSesion);
            this.usuarioBean.setUsuarioVisitado(usuarioEnSesion);
            //Después de guardar los cambios vuelve a index
            return "index";
        }

    }
}
