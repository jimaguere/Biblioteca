/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mateo
 */
@Embeddable
public class UsuarioRolSoftwarePK implements Serializable {
    @Basic(optional = false)
    @Size(min = 1, max = 2147483647)
    @Column(name = "id_usuario_rol_software", nullable = false, length = 2147483647)
    private String idUsuarioRolSoftware;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "usuario", nullable = false, length = 2147483647)
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_rol", nullable = false)
    private int idRol;

    public UsuarioRolSoftwarePK() {
    }

    public UsuarioRolSoftwarePK(String idUsuarioRolSoftware, String usuario, int idRol) {
        this.idUsuarioRolSoftware = idUsuarioRolSoftware;
        this.usuario = usuario;
        this.idRol = idRol;
    }

    public String getIdUsuarioRolSoftware() {
        return idUsuarioRolSoftware;
    }

    public void setIdUsuarioRolSoftware(String idUsuarioRolSoftware) {
        this.idUsuarioRolSoftware = idUsuarioRolSoftware;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioRolSoftware != null ? idUsuarioRolSoftware.hashCode() : 0);
        hash += (usuario != null ? usuario.hashCode() : 0);
        hash += (int) idRol;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioRolSoftwarePK)) {
            return false;
        }
        UsuarioRolSoftwarePK other = (UsuarioRolSoftwarePK) object;
        if ((this.idUsuarioRolSoftware == null && other.idUsuarioRolSoftware != null) || (this.idUsuarioRolSoftware != null && !this.idUsuarioRolSoftware.equals(other.idUsuarioRolSoftware))) {
            return false;
        }
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        if (this.idRol != other.idRol) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.biblioteca.entidad.UsuarioRolSoftwarePK[ idUsuarioRolSoftware=" + idUsuarioRolSoftware + ", usuario=" + usuario + ", idRol=" + idRol + " ]";
    }
    
}
