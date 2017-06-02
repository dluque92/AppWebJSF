/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author adri_
 */
@Named(value = "passwordBean")
@RequestScoped
public class PasswordBean {
    private String email = "";
    @Inject
    private UsuarioBean usuarioBean;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Creates a new instance of passwordBean
     */
    public PasswordBean() {
    }
    
    public String resetPassword(){
        usuarioBean.setResetPassword(Boolean.TRUE);
        return "login";
    }
}
