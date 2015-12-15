/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.controladores;

import com.biblioteca.aes.Aes;
import com.biblioteca.dao.UsuarioFacade;
import com.biblioteca.entidad.Usuario;
import com.biblioteca.mail.Correo;
import java.security.MessageDigest;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author mateo
 */
@ManagedBean(name = "registroController")
@ViewScoped
public class RegistroController {

    private Usuario selected;
    @EJB
    private com.biblioteca.dao.UsuarioFacade ejbFacade;
    private String confirmarClave;
    private boolean mensaje;

    public UsuarioFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(UsuarioFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    public boolean isMensaje() {
        return mensaje;
    }

    public void setMensaje(boolean mensaje) {
        this.mensaje = mensaje;
    }

    
    @PostConstruct
    public void prepareCreate() {
        selected = new Usuario();
        confirmarClave = "";
        mensaje=false;
    }
    

    public void create() {
        try {
            this.selected.setCorreoElectronico(this.selected.getCorreoElectronico().trim());
            String rutaServlet = ResourceBundle.getBundle("/Bundle").getString("rutaServlet");
            Correo c = new Correo();
            this.selected.setClave(md5(this.selected.getClave()));
            String usuarioEncrip=Aes.encriptar(this.selected.getCorreoElectronico()+"_"+this.selected.getClave());
            usuarioEncrip=usuarioEncrip.replaceAll("=", "comodin");
            c.addDestino(this.selected.getCorreoElectronico());
            c.setAsunto(ResourceBundle.getBundle("/Bundle").getString("asuntoRegistro"));
            c.setMensaje("<p align='justify'>"
                    + ResourceBundle.getBundle("/Bundle").getString("mensajeEstimado") + ":" + this.selected.getNombres() + " " + this.selected.getApellidos()
                    + "</p> <br/>"
                    + "<p align='justify'>"
                    + ResourceBundle.getBundle("/Bundle").getString("mensajeUsuario")
                    + "</p> <br/>"
                    + "<p align='justify'>"
                    + ResourceBundle.getBundle("/Bundle").getString("mensajeClick")
                    + "</p> <br/>"
                    + "<a href='"+rutaServlet+"?usuarioclave="+usuarioEncrip+"' target='ssssnew'>Verificar dirección de correo electrónico</a>"
                 //   + "<form action='" + rutaServlet + "' method='post'>"
                  //  + "<input type='hidden' value='" + Aes.encriptar(this.selected.getCorreoElectronico()) + "' name='usuario'/>"
                //    + "<input type='hidden' value='" + Aes.encriptar(this.selected.getClave()) + "' name='clave'/>"
                 //   + "<input type='submit' value='Verificar dirección de correo electrónico'/>"
                  //  + "</form>"
                    + "<br/><br/>"
                    + "<p align='justify'>"
                    + ResourceBundle.getBundle("/Bundle").getString("mensajeCaducar")
                    + "</p><br/>"
                    + "<p>"
                    + ResourceBundle.getBundle("/Bundle").getString("mensajeNoCrearCuenta")
                    + "</p><br/>"
                    + "<p align='justify'>"
                    + ResourceBundle.getBundle("/Bundle").getString("mensajeAgradecimientos")
                    + "</p>");

            selected.setUsuario(selected.getCorreoElectronico());
            selected.setFechaCreacion(new Date());
            selected.setFechaVigencia(new Date());
            selected.setEstado(Integer.parseInt(ResourceBundle.getBundle("/Bundle").getString("constanteUsuarioEstadoInavito")));
            ejbFacade.create(selected);
            c.enviar();
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", ResourceBundle.getBundle("/Bundle").getString("registroExitoso"));
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            prepareCreate();
            mensaje=true;
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ResourceBundle.getBundle("/Bundle").getString("registroError"));
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        }
    }

    private static String md5(String clear) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(clear.getBytes());
        int size = b.length;
        StringBuffer h = new StringBuffer(size);
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0" + Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }

        return h.toString();
    }

    /**
     * Creates a new instance of RegistroController
     */
    public RegistroController() {
    }
}
