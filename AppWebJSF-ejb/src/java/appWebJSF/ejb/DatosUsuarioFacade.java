/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF.ejb;

import appWebJSF.entity.DatosUsuario;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Adrián Cardenas, Adrián Garcia, Daniel Lopez, David Luque, John Purihin
 */
@Stateless
public class DatosUsuarioFacade extends AbstractFacade<DatosUsuario> {

    @PersistenceContext(unitName = "AppWebJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatosUsuarioFacade() {
        super(DatosUsuario.class);
    }
    public DatosUsuario obtenerUsuario(String email, String pass){
        Query q;
        q = this.em.createQuery("select d from DatosUsuario d where UPPER(d.email) = UPPER(:email) and d.password = :pass ");
        q.setParameter("email", email);
        q.setParameter("pass", pass);
        List <DatosUsuario> lista = (List)q.getResultList();
        if (!lista.isEmpty()){
            return lista.get(0);  
        }else{
            return null;
        }
    }
  
    public Boolean emailUsado(String email){
        Query q;
        q = this.em.createQuery("select d from DatosUsuario d where UPPER(d.email) = UPPER(:email) ");
        q.setParameter("email", email);
        List <DatosUsuario> lista = (List)q.getResultList();
        return !lista.isEmpty();
    }

    public DatosUsuario crearUsuario(String email, String password, String nombre, String apellidos) {
        Query q;
        q = this.em.createNativeQuery("select SEQ_ID_DATOSUSUARIO.nextval from dual");
        BigDecimal num = (BigDecimal) q.getResultList().get(0);
        DatosUsuario u = new DatosUsuario(num,email,password, nombre, apellidos);
        return u;
    }
   
    
    public List<DatosUsuario> findByName(String nombre, BigDecimal id) {
        Query q;
        q = this.em.createQuery("select d from DatosUsuario d where UPPER(d.nombre) LIKE UPPER(:nombre) and d.idUsuario <> :id");
        q.setParameter("nombre", "%"+nombre+"%");
        q.setParameter("id", id);
        List <DatosUsuario> lista = (List)q.getResultList();
        return lista;
    }

    public List<DatosUsuario> findByAficion(String nombreAficion, BigDecimal id) {
        Query q;
        
        q = this.em.createQuery("select distinct d from DatosUsuario d join fetch d.aficionCollection a "
                + "where UPPER(a.nombre) LIKE UPPER(:nombreAficion) and "
                + "d.idUsuario <> :id");
        q.setParameter("nombreAficion", "%"+nombreAficion+"%");
        q.setParameter("id", id);
        
        List <DatosUsuario> lista = (List)q.getResultList();
        return lista;
    }

    public List<DatosUsuario> findByEstudios(String nombreUbicacion, BigDecimal id) {
        Query q;
        q = this.em.createQuery("select distinct d from DatosUsuario d join fetch d.estudioCollection e "
                + "where UPPER(e.ubicacion) LIKE UPPER(:nombreUbicacion) and "
                + "d.idUsuario <> :id");
        q.setParameter("nombreUbicacion", "%"+nombreUbicacion+"%");
        q.setParameter("id", id);
        List <DatosUsuario> lista = (List)q.getResultList();
        return lista;
    }

    public List<DatosUsuario> findByExperiencia(String nombreEmpresa, BigDecimal id) {
        Query q;
        q = this.em.createQuery("select distinct d from DatosUsuario d join fetch d.experienciaCollection e "
                + "where UPPER(e.empresa) LIKE UPPER(:nombreEmpresa) and "
                + "d.idUsuario <> :id");
        q.setParameter("nombreEmpresa", "%"+nombreEmpresa+"%");
        q.setParameter("id", id);
        List <DatosUsuario> lista = (List)q.getResultList();
        return lista;
    }
}
