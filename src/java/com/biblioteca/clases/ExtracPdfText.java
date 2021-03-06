/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.clases;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ricardo Timaran Pereira Jimmy Mateo Guerrero
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.BadSecurityHandlerException;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 *
 * @author mateo
 */
public final class ExtracPdfText {

    private File archivoPdf;            
    private  PDDocument doc;

    public ExtracPdfText(InputStream archivo) throws IOException {
        doc = PDDocument.load(archivo,true);
        desencriptar();
    }

    public File getArchivoPdf() {
        return archivoPdf;
    }

    public void setArchivoPdf(File archivoPdf) {
        this.archivoPdf = archivoPdf;
    }

    public String extraerTexto() throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(1);
        stripper.setEndPage(stripper.getEndPage());
        stripper.setAddMoreFormatting(true);
        String docText = stripper.getText(doc);
        return docText.replaceAll("", "").replaceAll("~", "").replaceAll("", "").replaceAll(" ", "").replaceAll("`", "").replaceAll("", "").replaceAll("", "").replaceAll(" ", "").replaceAll("", "").replaceAll("", "").replaceAll("", "").trim();
    }

    public void cerrar() throws IOException {
        doc.close();
    }

    public void extarerUnaPagina() throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(3);
        stripper.setEndPage(3);
        System.out.println(stripper.getText(doc));
    }

    public String extraerTitulo() throws IOException {
        doc.setAllSecurityToBeRemoved(true);
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(1);
        stripper.setEndPage(1);
        stripper.setAddMoreFormatting(true);
        String titulo = stripper.getText(doc);
        String[] resultado = titulo.split("\n");
        String nuevoTitulo = "";
        boolean b = false;
        int contador = 0;
        for (int i = 0; i < resultado.length; i++) {
            if (resultado[i].trim().length() > 0) {
                b = true;
            }
            if (resultado[i].trim().length() == 0 && b) {
                contador++;
                if (contador > 10) {
                    break;
                }
            } else {
                nuevoTitulo = nuevoTitulo + " " + resultado[i];
            }
        }
        return nuevoTitulo.trim().replaceAll("", "").replaceAll("~", "").replaceAll("", "").replaceAll(" ", "").replaceAll("`", "").replaceAll("", "").replaceAll("", "").replaceAll("", "").replaceAll("", "").replaceAll("", "");
    }

    public void desencriptar() {
        if (doc.isEncrypted()) {
            try {
                doc.decrypt("");
                doc.setAllSecurityToBeRemoved(true);
            } catch (Exception e) {}
        }
    }

    public static void main(String arg[]) throws IOException, BadSecurityHandlerException, CryptographyException, InvalidPasswordException, COSVisitorException {
        InputStream archivoI = new FileInputStream("/opt/documentos pdfs/77666.pdf");
        ExtracPdfText texto = new ExtracPdfText(archivoI);
        System.out.println(texto.extraerTexto());
        texto.cerrar();
    }
}