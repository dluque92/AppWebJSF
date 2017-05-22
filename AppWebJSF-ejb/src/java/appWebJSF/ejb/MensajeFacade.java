/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF.ejb;

import appWebJSF.entity.DatosUsuario;
import appWebJSF.entity.Mensaje;
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
public class MensajeFacade extends AbstractFacade<Mensaje> {

    @PersistenceContext(unitName = "AppWebJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MensajeFacade() {
        super(Mensaje.class);
    }
    
    public List<Mensaje> getMensajesAmigos( BigDecimal id, BigDecimal id2) {
        Query q;
        q = this.em.createQuery("select m from Mensaje m"
                + "where (:id = Mensaje.MensajePK.amigosDatousuarioId and  "
                + ":id2 = Mensaje.MensajePK.amigosDatousuarioId1)" +
                "or( :id = Mensaje.MensajePK.amigosDatousuarioId1 and"
                + ":id2 = Mensaje.MensajePK.amigosDatousuarioId)");
        
        q.setParameter("id", id);
        q.setParameter("id2", id2);
        List <Mensaje> lista = (List)q.getResultList();
        return lista;
    }
    
    public Mensaje crearMensaje(String texto,DatosUsuario usuario,DatosUsuario amigo) {
        Query q;
        q = this.em.createNativeQuery("select SEQ_ID_MENSAJE.nextval from dual");
        BigDecimal num = (BigDecimal) q.getResultList().get(0);
        Mensaje mensaje = new Mensaje(num,texto,usuario,amigo);
        return mensaje;
    }
    
    
}
