/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.entity;

import comprasventasweb.dto.ValoracionDTO;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "VALORACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Valoracion.findAll", query = "SELECT v FROM Valoracion v")
    , @NamedQuery(name = "Valoracion.findByNota", query = "SELECT v FROM Valoracion v WHERE v.nota = :nota")
    , @NamedQuery(name = "Valoracion.findByProducto", query = "SELECT v FROM Valoracion v WHERE v.valoracionPK.producto = :producto")
    , @NamedQuery(name = "Valoracion.findByUsuario", query = "SELECT v FROM Valoracion v WHERE v.valoracionPK.usuario = :usuario")
    , @NamedQuery(name = "Valoracion.findByProductoYUsuario",
            query = "SELECT v FROM Valoracion v WHERE v.valoracionPK.usuario = :usuario and v.valoracionPK.producto = :producto")})
public class Valoracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ValoracionPK valoracionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOTA")
    private int nota;
    @JoinColumn(name = "PRODUCTO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto1;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;

    public Valoracion() {
    }

    public Valoracion(ValoracionPK valoracionPK) {
        this.valoracionPK = valoracionPK;
    }

    public Valoracion(ValoracionPK valoracionPK, int nota) {
        this.valoracionPK = valoracionPK;
        this.nota = nota;
    }

    public Valoracion(int producto, int usuario) {
        this.valoracionPK = new ValoracionPK(producto, usuario);
    }

    public ValoracionPK getValoracionPK() {
        return valoracionPK;
    }

    public void setValoracionPK(ValoracionPK valoracionPK) {
        this.valoracionPK = valoracionPK;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Producto getProducto1() {
        return producto1;
    }

    public void setProducto1(Producto producto1) {
        this.producto1 = producto1;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valoracionPK != null ? valoracionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoracion)) {
            return false;
        }
        Valoracion other = (Valoracion) object;
        if ((this.valoracionPK == null && other.valoracionPK != null) || (this.valoracionPK != null && !this.valoracionPK.equals(other.valoracionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "comprasventasweb.entity.Valoracion[ valoracionPK=" + valoracionPK + " ]";
    }

    public ValoracionDTO getDTO() {
       ValoracionDTO res = new ValoracionDTO();
       res.setProducto(producto1.getDTO());
       res.setUsuario(usuario1.getDTO());
       res.setValor(nota);
       return res;
    }
    
}
