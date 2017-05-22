/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.entity.DatosUsuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author adri_
 */
@Named(value = "UsuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    @EJB
    private DatosUsuarioFacade datosUsuarioFacade;

    private String email = "";
    private String password = "";
    private DatosUsuario usuario;
    private Boolean error;

    @PostConstruct
    public void init(){
        error=false;
    }
    
    public DatosUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(DatosUsuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
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

    public UsuarioBean() {
    }

    public String doLogin() {
        if (email == null || password == null || email.equals("") || password.equals("")) {
            limpiarCampos();
            return "login";
        } else {
            this.setUsuario(this.datosUsuarioFacade.obtenerUsuario(email, password));
            if (usuario == null) {
                error=true;
                limpiarCampos();
                return "login";
            } else {
                return "index";
            }
        }
    }

    public void limpiarCampos() {
        email = "";
        password = "";
    }
    
    
}
