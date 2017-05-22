/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appWebJSF.ejb;

import appWebJSF.entity.Aficion;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Adrián Cardenas, Adrián Garcia, Daniel Lopez, David Luque, John Purihin
 */
@Stateless
public class AficionFacade extends AbstractFacade<Aficion> {

    @PersistenceContext(unitName = "AppWebJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AficionFacade() {
        super(Aficion.class);
    }

    public Aficion crearAficion(String nombre) {
        Query q;
        q = this.em.createNativeQuery("select SEQ_ID_AFICION.nextval from dual");
        BigDecimal num = (BigDecimal) q.getResultList().get(0);
        Aficion aficion = new Aficion(num, nombre);
        return aficion;
    }
    
}
