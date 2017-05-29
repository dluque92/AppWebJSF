/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF.ejb;

import appWebJSF.entity.DatosUsuario;
import appWebJSF.entity.Estudio;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Adrián Cardenas, Adrián Garcia, Daniel Lopez, David Luque, John Purihin
 */
@Stateless
public class EstudioFacade extends AbstractFacade<Estudio> {

    @PersistenceContext(unitName = "AppWebJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudioFacade() {
        super(Estudio.class);
    }

    public Estudio crearEstudio(Date fechaComienzo, String descripcion) {
        Query q;
        q = this.em.createNativeQuery("select SEQ_ID_ESTUDIO.nextval from dual");
        BigDecimal num = (BigDecimal) q.getResultList().get(0);
        Estudio estudio = new Estudio(num,fechaComienzo,descripcion);
        return estudio;
    }
    
    public BigDecimal crearID(){
        Query q;
        q = this.em.createNativeQuery("select SEQ_ID_ESTUDIO.nextval from dual");
        BigDecimal num = (BigDecimal) q.getResultList().get(0);
        return num;
    }
    
}
