/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.entity;

import comprasventasweb.dto.ComentarioDTO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "COMENTARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comentario.findAll", query = "SELECT c FROM Comentario c")
    , @NamedQuery(name = "Comentario.findById", query = "SELECT c FROM Comentario c WHERE c.id = :id")
    , @NamedQuery(name = "Comentario.findByTexto", query = "SELECT c FROM Comentario c WHERE c.texto = :texto")
    , @NamedQuery(name = "Comentario.findByHora", query = "SELECT c FROM Comentario c WHERE c.hora = :hora")
    , @NamedQuery(name = "Comentario.findByFecha", query = "SELECT c FROM Comentario c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Comentario.findByUser", query = "SELECT c FROM Comentario c WHERE c.usuario = :usuario")
    , @NamedQuery(name = "Comentario.findByProducto", query = "SELECT c FROM Comentario c WHERE c.producto = :producto")})
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "TEXTO")
    private String texto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HORA")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "PRODUCTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Comentario() {
    }

    public Comentario(Integer id) {
        this.id = id;
    }

    public Comentario(Integer id, String texto, Date hora, Date fecha) {
        this.id = id;
        this.texto = texto;
        this.hora = hora;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentario)) {
            return false;
        }
        Comentario other = (Comentario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "comprasventasweb.entity.Comentario[ id=" + id + " ]";
    }
    
    public ComentarioDTO getDTO(){
        ComentarioDTO c = new ComentarioDTO();
        c.setFecha(fecha);
        c.setHora(hora);
        c.setId(id);
        c.setTexto(texto);
        c.setProducto(producto.getDTO());
        c.setUsuario(usuario.getDTO());
        return c;
    }
    
}
