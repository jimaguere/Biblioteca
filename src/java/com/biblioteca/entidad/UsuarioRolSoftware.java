/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 *
 * @author mateo
 */
@Entity
@Cacheable(false)
@Table(name = "usuario_rol_software", catalog = "bd_biblioteca", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_rol"})})
@NamedQueries({
    @NamedQuery(name = "UsuarioRolSoftware.findAll", query = "SELECT u FROM UsuarioRolSoftware u"),
    @NamedQuery(name = "UsuarioRolSoftware.findByIdUsuarioRolSoftware", query = "SELECT u FROM UsuarioRolSoftware u WHERE u.idUsuarioRolSoftware = :idUsuarioRolSoftware")})
public class UsuarioRolSoftware implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Size(min = 1, max = 2147483647)
    @Column(name = "id_usuario_rol_software", nullable = false, length = 2147483647)
    private String idUsuarioRolSoftware;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private RolSoftware idRol;

    public UsuarioRolSoftware() {
    }

    public UsuarioRolSoftware(String idUsuarioRolSoftware) {
        this.idUsuarioRolSoftware = idUsuarioRolSoftware;
    }

    public String getIdUsuarioRolSoftware() {
        return idUsuarioRolSoftware;
    }

    public void setIdUsuarioRolSoftware(String idUsuarioRolSoftware) {
        this.idUsuarioRolSoftware = idUsuarioRolSoftware;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public RolSoftware getIdRol() {
        return idRol;
    }

    public void setIdRol(RolSoftware idRol) {
        this.idRol = idRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioRolSoftware != null ? idUsuarioRolSoftware.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioRolSoftware)) {
            return false;
        }
        UsuarioRolSoftware other = (UsuarioRolSoftware) object;
        if ((this.idUsuarioRolSoftware == null && other.idUsuarioRolSoftware != null) || (this.idUsuarioRolSoftware != null && !this.idUsuarioRolSoftware.equals(other.idUsuarioRolSoftware))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.biblioteca.entidad.UsuarioRolSoftware[ idUsuarioRolSoftware=" + idUsuarioRolSoftware + " ]";
    }
    
}
