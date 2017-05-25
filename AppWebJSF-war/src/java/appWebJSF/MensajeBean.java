
package appWebJSF;

import appWebJSF.entity.DatosUsuario;
import appWebJSF.entity.Mensaje;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Daniel
 */
@Named(value = "mensajeBean")
@RequestScoped
public class MensajeBean {

    @Inject
    private UsuarioBean usuarioBean;

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
    
    private DatosUsuario usuario;
    private List<Mensaje> listaMensajes;
    private List<DatosUsuario> listaAmigos;
    private String idAmigo;

    public String getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(String idAmigo) {
        this.idAmigo = idAmigo;
    }
    
    public List<DatosUsuario> getListaAmigos() {
        return listaAmigos;
    }

    public void setListaAmigos(List<DatosUsuario> listaAmigos) {
        this.listaAmigos = listaAmigos;
    }

    public DatosUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(DatosUsuario usuario) {
        this.usuario = usuario;
    }

    public List<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(List<Mensaje> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }
    /**
     * Creates a new instance of MensajesBean
     */
    public MensajeBean() {
    }
    
    @PostConstruct
    public void init(){
        usuario = usuarioBean.getUsuario();
        listaMensajes = (List<Mensaje>) usuario.getMensajeCollection();
        listaAmigos = (List<DatosUsuario>) usuario.getMisAmigos();
        if (idAmigo == null && listaMensajes != null && !listaMensajes.isEmpty() && listaAmigos != null){
            Collections.sort(listaMensajes);
            for (DatosUsuario u: listaMensajes.get(listaMensajes.size() - 1).getDatosUsuarioCollection()){
                if (!u.equals(usuario)){
                    idAmigo = u.getIdUsuario().toString();
                }
            }
        }
    }
    
    public void cambiarAmigo(DatosUsuario amigo){
        
    }
}

