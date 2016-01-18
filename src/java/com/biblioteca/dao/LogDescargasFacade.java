/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.dao;

import com.biblioteca.entidad.LogDescargas;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mateo
 */
@Stateless
public class LogDescargasFacade extends AbstractFacade<LogDescargas> {
    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LogDescargasFacade() {
        super(LogDescargas.class);
    }
    
    public List<LogDescargas> findDescargasOrderBy(){
        List<LogDescargas> descargas=new ArrayList<LogDescargas>();
        try{
            
        }catch(Exception e){}
        return descargas;
    }
    
    public List<String[]> transaccionesDescargas(){
         List<String[]> logs=new ArrayList<String[]>();
         try{
             Query q=getEntityManager().createNativeQuery("select usuario,id_documento from log_descargas order by usuario");
             List<Object[]> object=q.getResultList();
             for(Object[] obj:object){
                 
             }
         }catch(Exception e){
             
         }
         return logs;
    }
    
}
