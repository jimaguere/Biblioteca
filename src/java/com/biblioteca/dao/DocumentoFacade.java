/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.dao;

import com.biblioteca.entidad.Documento;
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
public class DocumentoFacade extends AbstractFacade<Documento> {
    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentoFacade() {
        super(Documento.class);
    }
    
    public  List<Documento> listaDocumentos(){
        return null;
    }
    
    public void editarDocumento(Documento doc){
        Query q=getEntityManager().createNativeQuery("delete from documento_valor_meta_dato where"
                + " id_documento=?");
        q.setParameter(1, doc.getIdDocumento());
        q.executeUpdate();
        getEntityManager().merge(doc);
    }
    
}
