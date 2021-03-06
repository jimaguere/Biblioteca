/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mateo
 */
@Entity
@Cacheable(false)
@Table(name = "rol_software", catalog = "bd_biblioteca", schema = "public")
@NamedQueries({
    @NamedQuery(name = "RolSoftware.findAll", query = "SELECT r FROM RolSoftware r"),
    @NamedQuery(name = "RolSoftware.findByIdRol", query = "SELECT r FROM RolSoftware r WHERE r.idRol = :idRol"),
    @NamedQuery(name = "RolSoftware.findByDescripcion", query = "SELECT r FROM RolSoftware r WHERE r.descripcion = :descripcion")})
public class RolSoftware implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol", nullable = false)
    private Integer idRol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion", nullable = false, length = 2147483647)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRol", fetch = FetchType.LAZY)
    private List<RolSoftMenu> rolSoftMenuList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idRol", fetch = FetchType.LAZY)
    private UsuarioRolSoftware usuarioRolSoftware;

    public RolSoftware() {
    }

    public RolSoftware(Integer idRol) {
        this.idRol = idRol;
    }

    public RolSoftware(Integer idRol, String descripcion) {
        this.idRol = idRol;
        this.descripcion = descripcion;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<RolSoftMenu> getRolSoftMenuList() {
        return rolSoftMenuList;
    }

    public void setRolSoftMenuList(List<RolSoftMenu> rolSoftMenuList) {
        this.rolSoftMenuList = rolSoftMenuList;
    }

    public UsuarioRolSoftware getUsuarioRolSoftware() {
        return usuarioRolSoftware;
    }

    public void setUsuarioRolSoftware(UsuarioRolSoftware usuarioRolSoftware) {
        this.usuarioRolSoftware = usuarioRolSoftware;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolSoftware)) {
            return false;
        }
        RolSoftware other = (RolSoftware) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.biblioteca.entidad.RolSoftware[ idRol=" + idRol + " ]";
    }
    
}
