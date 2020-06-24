/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Usuario
 */
@Embeddable
public class ValoracionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTO")
    private int producto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USUARIO")
    private int usuario;

    public ValoracionPK() {
    }

    public ValoracionPK(int producto, int usuario) {
        this.producto = producto;
        this.usuario = usuario;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) producto;
        hash += (int) usuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValoracionPK)) {
            return false;
        }
        ValoracionPK other = (ValoracionPK) object;
        if (this.producto != other.producto) {
            return false;
        }
        if (this.usuario != other.usuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "comprasventasweb.entity.ValoracionPK[ producto=" + producto + ", usuario=" + usuario + " ]";
    }
    
}
