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
import dropbox.DropboxControllerException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author adri_
 */
@Named(value = "registrarBean")
@RequestScoped
public class RegistrarBean {

    @EJB
    private DatosUsuarioFacade datosUsuarioFacade;

    @Inject
    UsuarioBean usuarioBean;

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
    
    private String nombre = "";
    private String apellidos = "";
    private String email = "";
    private String password = "";
    private String password2 = "";
    private String twitter = "";
    private String instagram = "";
    private String web = "";
    private Part archivo = null;
    
    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

    public RegistrarBean() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
    public Boolean getErrorPassword(){
        return usuarioBean.getErrorRegistrar() && password.length() > 0 && password2.length() > 0;
    }
    
    public String doRegistrar() {
        if (this.datosUsuarioFacade.emailUsado(email) || !password.equals(password2)) {
            System.out.println(this.datosUsuarioFacade.emailUsado(email));
            System.out.println(!password.equals(password2));
            this.usuarioBean.setErrorRegistrar(true);
            return "registrar";
        } else {
            this.usuarioBean.setErrorRegistrar(false);
            this.setPassword(hash.stringToHash(password));
            DatosUsuario usuario = this.datosUsuarioFacade.crearUsuario(email, password, nombre, apellidos);
            if (twitter != null && !twitter.isEmpty()) {
                usuario.setTwitter(twitter);
            }
            if (instagram != null && !instagram.isEmpty()) {
                usuario.setInstagram(instagram);
            }
            if (web != null && !web.isEmpty()) {
                usuario.setWeb(web);
            }
            //Visitas a 0
            usuario.setVisitas(BigInteger.ZERO);
            String nombreFoto;
            if (archivo != null) {
                nombreFoto = email + ".jpg";
                try {
                    InputStream fileContent = archivo.getInputStream();
                    DropboxController.uploadFile(nombreFoto, fileContent);
                } catch (IOException ex) {
                    //Fallo al subir la foto
                } catch (DropboxControllerException ex) {
                    //Fallo en Dropbox
                }
            } else {
                nombreFoto = "default.jpg";
            }
            usuario.setFoto(nombreFoto);
            this.datosUsuarioFacade.create(usuario);
            return "login";
        }
    }
}
