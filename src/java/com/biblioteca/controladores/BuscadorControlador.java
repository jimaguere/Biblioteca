/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.controladores;

import com.biblioteca.dao.DocumentoFacade;
import com.biblioteca.dao.LogConsultaFacade;
import com.biblioteca.dao.LogDescargasFacade;
import com.biblioteca.dao.MetaDatoFacade;
import com.biblioteca.dao.TipoDocumentoFacade;
import com.biblioteca.entidad.Documento;
import com.biblioteca.entidad.LogConsulta;
import com.biblioteca.entidad.LogDescargas;
import com.biblioteca.entidad.MetaDato;
import com.biblioteca.entidad.TipoDocumento;
import com.biblioteca.entidad.TipoDocumentoMetaDato;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author mateo
 */
@ManagedBean(name = "buscadorControlador")
@ViewScoped
public class BuscadorControlador {


    private FSDirectory dir;
    private IndexSearcher searcher;
    private String tipoBusqueda;
    private String cadenaBusqueda;
    private String contenido;
    private List<Documento> listaDocuementos;
    private Documento documentoSeleccionado;
    private List<TipoDocumento> listaTipoDocumento;
    private TipoDocumento tipoDocumento;
    private MetaDato metaDato;
    private boolean btBusqueda;
    private boolean seleccionDocumento;
    private RepositorioControlador repositorio;
    private StreamedContent file;
    private InputStream stream;
    private LogDescargas logD;
    private LogConsulta logC;
    private UsuarioLoginControlador usuario;
    @EJB
    private TipoDocumentoFacade tipoDocumentoEjbFacade;
    @EJB
    private MetaDatoFacade metaDatoEjbFacade;
    @EJB
    private DocumentoFacade documentoEjbFacade;
    @EJB
    private LogDescargasFacade logDescargaEjbFacade;
    @EJB
    private LogConsultaFacade logConsultaEjbFacade;

    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public List<Documento> getListaDocuementos() {
        return listaDocuementos;
    }

    public void setListaDocuementos(List<Documento> listaDocuementos) {
        this.listaDocuementos = listaDocuementos;
    }

    public Documento getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public List<TipoDocumento> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public MetaDato getMetaDato() {
        return metaDato;
    }

    public void setMetaDato(MetaDato metaDato) {
        this.metaDato = metaDato;
    }

    public boolean isBtBusqueda() {
        return btBusqueda;
    }

    public void setBtBusqueda(boolean btBusqueda) {
        this.btBusqueda = btBusqueda;
    }

    public boolean isSeleccionDocumento() {
        return seleccionDocumento;
    }

    public void setSeleccionDocumento(boolean seleccionDocumento) {
        this.seleccionDocumento = seleccionDocumento;
    }

    public StreamedContent getFile() {
        try {
            stream = new FileInputStream(repositorio.getRutaOntologia() + this.documentoSeleccionado.getIdDocumento() + ".pdf");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BuscadorControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        file = new DefaultStreamedContent(stream, "application/pdf", "doc.pdf");
        logDescargaEjbFacade.create(logD);
        return file;
    }

    public void actualizarTipoDocumento() {
        if (tipoDocumento.getIdTipoDoc().intValue() != -1) {
            tipoDocumento = tipoDocumentoEjbFacade.find(tipoDocumento.getIdTipoDoc());
        } else {
            tipoDocumento.setTipoDocumentoMetaDatoList(new ArrayList<TipoDocumentoMetaDato>());
        }
        metaDato = new MetaDato();
        metaDato.setIdMetaDato(Integer.parseInt("-1"));
    }

    /**
     * Creates a new instance of BuscadorControlador
     */
    public BuscadorControlador() {
        this.tipoBusqueda = "1";
    }    

    @PostConstruct
    public void iniciar() throws IOException {
        this.btBusqueda = false;
        this.seleccionDocumento = false;
        this.repositorio = (RepositorioControlador) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("repositorioControlador");
        this.usuario = (UsuarioLoginControlador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogin");
        if (this.repositorio == null) {
            return;
        }
        if (!repositorio.isRepositorio()) {
            return;
        }
        cadenaBusqueda = "";
        listaTipoDocumento = this.tipoDocumentoEjbFacade.findAll();
        tipoDocumento = new TipoDocumento();
        metaDato = new MetaDato();
        tipoDocumento.setTipoDocumentoMetaDatoList(new ArrayList<TipoDocumentoMetaDato>());
        metaDato.setIdMetaDato(Integer.parseInt("-1"));
        tipoDocumento.setIdTipoDoc(Integer.parseInt("-1"));
        dir = FSDirectory.open(new File(repositorio.getIndice()));
        searcher = new IndexSearcher(IndexReader.open(dir));
        this.tipoBusqueda = "1";
        this.cadenaBusqueda = "";
        this.contenido = "1";
    }

    public List<String> completeGeneral(String query) {
        /*   query = query.replaceAll("'", "");
         List<String> resul = new ArrayList<String>();
         List<Object[]> listaWords;
         String[] listaTitulo = query.split(" ");
         String res = "";
         if(listaTitulo.length==0){
         return resul;
         }
         for (int i = 0; i < listaTitulo.length - 1; i++) {
         if (i == 0) {
         res = listaTitulo[i];
         } else {
         res = res + " " + listaTitulo[i];
         }
         }
         listaWords = this.vocabularioFacadel.findAllJaroWordsComplet(listaTitulo[listaTitulo.length - 1]);
         if (listaWords.isEmpty()) {
         return resul;
         }
         for (int i = 0; i < listaTitulo.length; i++) {
         if (i > 5) {
         break;
         }
         if (res.equals("")) {
         resul.add(listaWords.get(i)[0].toString());
         } else {
         resul.add(res + " " + listaWords.get(i)[0].toString());
         }
         }
         return resul;*/
        return null;
    }

    public String depurarContenidoTodo() {
        String busquedaContenido = "(\"" + this.cadenaBusqueda + "\")";
        return busquedaContenido;
    }

    public String depurarContenidoAlguno() {
        String[] busquedaContenido = this.cadenaBusqueda.split(" ");
        String buscar = "";
        for (int i = 0; i < busquedaContenido.length; i++) {
            buscar = buscar + "+" + busquedaContenido[i] + " ";
        }
        return "(" + buscar.substring(0, buscar.length() - 1) + ")";
    }

    public String depurarContenidoAlgunoCompleto() {
        String[] busquedaContenido = this.cadenaBusqueda.split(" ");
        String buscar = "";
        for (int i = 0; i < busquedaContenido.length; i++) {
            buscar = buscar + "+" + busquedaContenido[i] + " ";
        }
        return buscar.substring(1, buscar.length() - 1);
    }

    public String depurarContenidoTodoCorrec() {
        String[] busquedaContenido = this.cadenaBusqueda.split(" ");
        String buscar = "";
        for (int i = 0; i < busquedaContenido.length; i++) {
            buscar = buscar + "+" + busquedaContenido[i] + "~ ";
        }
        return buscar.substring(0, buscar.length() - 1);
    }

    public String depurarContenidoAlgunoCorrec() {
        String[] busquedaContenido = this.cadenaBusqueda.split(" ");
        String buscar = "";
        for (int i = 0; i < busquedaContenido.length; i++) {
            buscar = buscar + "" + busquedaContenido[i] + "~ ";
        }
        return "(" + buscar.substring(0, buscar.length() - 1) + ")";
    }

    public void generarResultados(TopDocs hits) throws IOException {
        this.logC=new LogConsulta();
        this.btBusqueda = true;
        this.seleccionDocumento = false;
        this.listaDocuementos = new ArrayList<Documento>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            Documento documento = new Documento();

            documento.setIdDocumento(Integer.parseInt(doc.get("id")));
            documento.setMetaDatosDocumentos("");
            for (IndexableField index : doc.getFields()) {
                if (!doc.get("id").equals(index.stringValue())) {
                    documento.setMetaDatosDocumentos(documento.getMetaDatosDocumentos() + "\n" + index.stringValue());
                }
            }
            this.listaDocuementos.add(documento);
        }
        this.logC.setUsuario(this.usuario.getUsuarioSession());
        this.logC.setConsulta(this.cadenaBusqueda);
        this.logC.setFechaConsulta(new Date());
        this.logC.setLogDescargasList(new ArrayList<LogDescargas>());
        this.logConsultaEjbFacade.create(logC);       
    }

    @SuppressWarnings("UnusedAssignment")
    public void buscar() throws ParseException, IOException {
        String cont;
        if (metaDato.getIdMetaDato().intValue() != -1) {
            metaDato = metaDatoEjbFacade.find(metaDato.getIdMetaDato());
        }
        
        cont = this.cadenaBusqueda.trim();
        if (this.contenido.equals("1")) {
            cont = this.depurarContenidoTodo();
        } else {
            cont = this.depurarContenidoAlguno();
        }
        if (metaDato.getIdMetaDato().intValue() == -1) {
            cont = "contenido:" + cont;
        } else {
            cont = metaDato.getIdMetaDato() + ":" + cont;
        }
        if (tipoDocumento.getIdTipoDoc().intValue() != -1) {
            cont = cont + " AND tipoDocumento:" + tipoDocumento.getIdTipoDoc();
        }

        QueryParser parser;
        parser = new QueryParser(Version.LUCENE_46,
                "<default field>",
                new SpanishAnalyzer(
                Version.LUCENE_46));
        parser.setAllowLeadingWildcard(true);
        parser.setFuzzyMinSim(Float.parseFloat("2.0"));

        Query query = parser.parse(cont);
        System.out.println(cont);
        TopDocs hits = searcher.search(query, 30);
        if (hits.totalHits == 0) {
            if (!this.contenido.equals("1")) {
                cont = this.cadenaBusqueda.trim();
                cont = this.depurarContenidoAlgunoCorrec();
                if (metaDato.getIdMetaDato().intValue() == -1) {
                    cont = "contenido:" + cont;
                } else {
                    cont = metaDato.getIdMetaDato() + ":" + cont;
                }
                if (tipoDocumento.getIdTipoDoc().intValue() != -1) {
                    cont = cont + " AND tipoDocumento:" + tipoDocumento.getIdTipoDoc();
                }
                query = parser.parse(cont);
                System.out.println(cont);
                hits = searcher.search(query, 30);
            }
        }
        this.generarResultados(hits);
    }

    public void asignarDocumento(Documento doc) throws FileNotFoundException {
        this.documentoSeleccionado = this.documentoEjbFacade.find(doc.getIdDocumento());
        this.seleccionDocumento = true;
        stream = new FileInputStream(repositorio.getRutaOntologia() + this.documentoSeleccionado.getIdDocumento() + ".pdf");
        file = new DefaultStreamedContent(stream, "application/pdf", "doc.pdf");
        this.logD = new LogDescargas();
        logD.setIdDocumento(this.documentoSeleccionado);
        logD.setUsuario(this.usuario.getUsuarioSession());
        logD.setFechaDescarga(new Date());
        logD.setIdConsulta(logC);
    }

    public StreamedContent downloadPDF() throws IOException {
        return new DefaultStreamedContent(stream, "application/pdf", "documento.pdf");
    }
    
    public void regresar(){
        this.seleccionDocumento=false;
    }
}
