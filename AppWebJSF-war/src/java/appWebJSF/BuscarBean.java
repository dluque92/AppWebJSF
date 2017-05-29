/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.entity.DatosUsuario;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author adri_
 */
@Named(value = "buscarBean")
@RequestScoped
public class BuscarBean{
    

    @EJB
    private DatosUsuarioFacade datosUsuarioFacade;
    
    private String busqueda;
    
    @Inject
    private UsuarioBean usuarioBean;

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
    
    public BuscarBean() {
        busqueda = "";
    }
    
    public void visita(DatosUsuario usuario){
        usuario.setVisitas(usuario.getVisitas().add(BigInteger.ONE));
        this.datosUsuarioFacade.edit(usuario);
    }
    
    public String buscar(DatosUsuario usuario){
        usuarioBean.setUsuario(usuario);
        visita(usuario);
        return "index";
    }
    
}
