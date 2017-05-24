/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF;

import appWebJSF.ejb.DatosUsuarioFacade;
import appWebJSF.entity.DatosUsuario;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author adri_
 */
@Named(value = "buscarBean")
@RequestScoped
public class BuscarBean {

    @EJB
    private DatosUsuarioFacade datosUsuarioFacade;
    
    private String busqueda;
    private List<DatosUsuario> usuariosPorNombre = null;
    private List<DatosUsuario> usuariosPorAficion = null;
    private List<DatosUsuario> usuariosPorExperiencia = null;
    private List<DatosUsuario> usuarioPorEstudio = null;
    
    
    @Inject
    private UsuarioBean usuarioBean;

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<DatosUsuario> getUsuariosPorNombre() {
        return usuariosPorNombre;
    }

    public void setUsuariosPorNombre(List<DatosUsuario> usuariosPorNombre) {
        this.usuariosPorNombre = usuariosPorNombre;
    }

    public List<DatosUsuario> getUsuariosPorAficion() {
        return usuariosPorAficion;
    }

    public void setUsuariosPorAficion(List<DatosUsuario> usuariosPorAficion) {
        this.usuariosPorAficion = usuariosPorAficion;
    }

    public List<DatosUsuario> getUsuariosPorExperiencia() {
        return usuariosPorExperiencia;
    }

    public void setUsuariosPorExperiencia(List<DatosUsuario> usuariosPorExperiencia) {
        this.usuariosPorExperiencia = usuariosPorExperiencia;
    }

    public List<DatosUsuario> getUsuarioPorEstudio() {
        return usuarioPorEstudio;
    }

    public void setUsuarioPorEstudio(List<DatosUsuario> usuarioPorEstudio) {
        this.usuarioPorEstudio = usuarioPorEstudio;
    }
    
    public BuscarBean() {
    }
    
    public void visita(DatosUsuario usuario){
        usuario.setVisitas(usuario.getVisitas().add(BigInteger.ONE));
        this.datosUsuarioFacade.edit(usuario);
    }
    
    public String hacerBusqueda(){
        usuarioBean.cargarUsuario();
        setUsuarioPorEstudio(this.datosUsuarioFacade.findByEstudios(busqueda, usuarioBean.getUsuario().getIdUsuario()));
        setUsuariosPorAficion(this.datosUsuarioFacade.findByAficion(busqueda, usuarioBean.getUsuario().getIdUsuario()));
        setUsuariosPorNombre(this.datosUsuarioFacade.findByName(busqueda, usuarioBean.getUsuario().getIdUsuario()));
        setUsuariosPorExperiencia(this.datosUsuarioFacade.findByExperiencia(busqueda, usuarioBean.getUsuario().getIdUsuario()));
        busqueda="";
        return "buscar";
    }
}
