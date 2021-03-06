/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mateo
 */
@Entity
@Cacheable(false)
@Table(name = "usuario", catalog = "bd_biblioteca", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"correo_electronico"})})
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findByNombres", query = "SELECT u FROM Usuario u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuario.findByCorreoElectronico", query = "SELECT u FROM Usuario u WHERE u.correoElectronico = :correoElectronico"),
    @NamedQuery(name = "Usuario.findByInstitucion", query = "SELECT u FROM Usuario u WHERE u.institucion = :institucion"),
    @NamedQuery(name = "Usuario.findByProfesion", query = "SELECT u FROM Usuario u WHERE u.profesion = :profesion"),
    @NamedQuery(name = "Usuario.findByFechaCreacion", query = "SELECT u FROM Usuario u WHERE u.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Usuario.findByFechaVigencia", query = "SELECT u FROM Usuario u WHERE u.fechaVigencia = :fechaVigencia"),
    @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado"),
    @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM Usuario u WHERE u.clave = :clave"),
    @NamedQuery(name = "Usuario.findBySistema",query= "SELECT u FROM Usuario u WHERE u.usuario in(SELECT r.usuario.usuario FROM UsuarioRolSoftware r )")
})
public class Usuario implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<LogConsulta> logConsultaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<LogDescargas> logDescargasList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "usuario", nullable = false, length = 2147483647)
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombres", nullable = false, length = 2147483647)
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "apellidos", nullable = false, length = 2147483647)
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "correo_electronico", nullable = false, length = 2147483647)
    private String correoElectronico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "institucion", nullable = false, length = 2147483647)
    private String institucion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "profesion", nullable = false, length = 2147483647)
    private String profesion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_vigencia", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado", nullable = false)
    private int estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "clave", nullable = false, length = 2147483647)
    private String clave;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<UsuarioRolSoftware> usuarioRolSoftwareList;

    public Usuario() {
    }

    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    public Usuario(String usuario, String nombres, String apellidos, String correoElectronico, String institucion, String profesion, Date fechaCreacion, Date fechaVigencia, int estado, String clave) {
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.institucion = institucion;
        this.profesion = profesion;
        this.fechaCreacion = fechaCreacion;
        this.fechaVigencia = fechaVigencia;
        this.estado = estado;
        this.clave = clave;

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<UsuarioRolSoftware> getUsuarioRolSoftwareList() {
        return usuarioRolSoftwareList;
    }

    public void setUsuarioRolSoftwareList(List<UsuarioRolSoftware> usuarioRolSoftwareList) {
        this.usuarioRolSoftwareList = usuarioRolSoftwareList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.biblioteca.entidad.Usuario[ usuario=" + usuario + " ]";
    }

    public List<LogDescargas> getLogDescargasList() {
        return logDescargasList;
    }

    public void setLogDescargasList(List<LogDescargas> logDescargasList) {
        this.logDescargasList = logDescargasList;
    }

    public List<LogConsulta> getLogConsultaList() {
        return logConsultaList;
    }

    public void setLogConsultaList(List<LogConsulta> logConsultaList) {
        this.logConsultaList = logConsultaList;
    }
    
}
