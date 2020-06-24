/*
Autor: clase reutilizada de la entrega anterior
Julia Robles Medina (constructor de DTO a producto)
*/
package comprasventasweb.entity;

import comprasventasweb.dto.EtiquetaDTO;
import comprasventasweb.dto.ProductoBasicoDTO;
import comprasventasweb.dto.ProductoDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 * 
 */
@Entity
@Table(name = "PRODUCTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findAllInverso", query = "SELECT p FROM Producto p ORDER BY p.fecha DESC, p.hora DESC")
    , @NamedQuery(name = "Producto.findBySubcategory", query = "SELECT p FROM Producto p WHERE p.categoria = :id ORDER BY p.fecha DESC, p.hora DESC")
    , @NamedQuery(name = "Producto.findByCategory", query = "SELECT p FROM Producto p, Subcategoria s WHERE p.categoria = s AND s.categoriaPadre = :id ORDER BY p.fecha DESC, p.hora DESC")
    , @NamedQuery(name = "Producto.findById", query = "SELECT p FROM Producto p WHERE p.id = :id")
    , @NamedQuery(name = "Producto.findByVendedor", query = "SELECT p FROM Producto p WHERE p.vendedor = :user")
    , @NamedQuery(name = "Producto.findByTitulo", query = "SELECT p FROM Producto p WHERE UPPER(p.titulo) LIKE :titulo ORDER BY p.fecha DESC, p.hora DESC")
    , @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE UPPER(p.descripcion) LIKE :descripcion ORDER BY p.fecha DESC, p.hora DESC")
    , @NamedQuery(name = "Producto.findByEtiquetas", query = "SELECT p FROM Producto p, Etiqueta e WHERE p.etiquetaList = e AND UPPER(e.nombre) = :etiqueta ORDER BY p.fecha DESC, p.hora DESC")
    , @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio")
    , @NamedQuery(name = "Producto.findByHora", query = "SELECT p FROM Producto p WHERE p.hora BETWEEN :hora1 AND :hora2 ORDER BY p.fecha DESC")
    , @NamedQuery(name = "Producto.findByFecha", query = "SELECT p FROM Producto p WHERE p.fecha = :fecha ORDER BY p.hora DESC")
    , @NamedQuery(name = "Producto.findByFechaHora", query = "SELECT p FROM Producto p WHERE p.fecha = :fecha AND p.hora BETWEEN :hora1 AND :hora2 ORDER BY p.hora DESC")
    , @NamedQuery(name = "Producto.findByFechaHoraEntre", query = "SELECT p FROM Producto p WHERE (p.fecha > :fecha1 AND p.fecha < :fecha2) OR (p.fecha = :fecha1 AND p.hora >= :hora1) OR (p.fecha = :fecha2 AND p.hora <= :hora2) ORDER BY p.fecha DESC, p.hora DESC")
    , @NamedQuery(name = "Producto.findByFechaEntre", query = "SELECT p FROM Producto p WHERE p.fecha >= :inicio AND p.fecha <= :end ORDER BY p.fecha DESC, p.hora DESC")
    , @NamedQuery(name = "Producto.findByFoto", query = "SELECT p FROM Producto p WHERE p.foto = :foto")
    , @NamedQuery(name = "Producto.findByTituloDescripcion", query = "SELECT p FROM Producto p WHERE UPPER(p.titulo) LIKE :titulo OR UPPER(p.descripcion) LIKE :titulo ORDER BY p.fecha DESC, p.hora DESC")
    , @NamedQuery(name = "Producto.findByValoracionmedia", query = "SELECT p FROM Producto p WHERE p.valoracionmedia = :valoracionmedia")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITULO")
    private String titulo;
    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRECIO")
    private float precio;
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
    @Size(max = 512)
    @Column(name = "FOTO")
    private String foto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALORACIONMEDIA")
    private Float valoracionmedia;
    @ManyToMany(mappedBy = "productoList")
    private List<Etiqueta> etiquetaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto1")
    private List<Valoracion> valoracionList;
    @JoinColumn(name = "CATEGORIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Subcategoria categoria;
    @JoinColumn(name = "VENDEDOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario vendedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<Comentario> comentarioList;

    public Producto() {
    }

    public Producto(Integer id) {
        this.id = id;
    }
    
    public Producto(ProductoDTO p){
        this.descripcion = p.getDescripcion();
        this.fecha = p.getFecha();
        this.hora = p.getHora();
        this.precio = p.getPrecio();
        this.id = p.getId();
        this.titulo = p.getTitulo();
        this.foto = p.getFoto();
        this.valoracionmedia = p.getValoracionmedia();
    }

    public Producto(Integer id, String titulo, float precio, Date hora, Date fecha) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.hora = hora;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Float getValoracionmedia() {
        return valoracionmedia;
    }

    public void setValoracionmedia(Float valoracionmedia) {
        this.valoracionmedia = valoracionmedia;
    }

    @XmlTransient
    public List<Etiqueta> getEtiquetaList() {
        return etiquetaList;
    }

    public void setEtiquetaList(List<Etiqueta> etiquetaList) {
        this.etiquetaList = etiquetaList;
    }

    @XmlTransient
    public List<Valoracion> getValoracionList() {
        return valoracionList;
    }

    public void setValoracionList(List<Valoracion> valoracionList) {
        this.valoracionList = valoracionList;
    }

    public Subcategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Subcategoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    @XmlTransient
    public List<Comentario> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
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
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "comprasventasweb.entity.Producto[ id=" + id + " ]";
    }
    
    public ProductoBasicoDTO getDTOBasico() {
        ProductoBasicoDTO productoDTO = new ProductoBasicoDTO();
        productoDTO.setId(id);
        productoDTO.setTitulo(titulo);
        productoDTO.setDescripcion(descripcion);
        productoDTO.setFecha(fecha);
        productoDTO.setHora(hora);
        productoDTO.setFoto(foto);
        productoDTO.setPrecio(precio);
        productoDTO.setValoracionmedia(valoracionmedia);
        productoDTO.setVendedor(vendedor.getDTO());
        return productoDTO;
    }
    
    public ProductoDTO getDTO() {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(id);
        productoDTO.setTitulo(titulo);
        productoDTO.setDescripcion(descripcion);
        productoDTO.setFecha(fecha);
        productoDTO.setHora(hora);
        productoDTO.setFoto(foto);
        productoDTO.setPrecio(precio);
        productoDTO.setValoracionmedia(valoracionmedia);
        productoDTO.setVendedor(vendedor.getDTO());
        productoDTO.setCategoria(categoria.getDTO());
        List<EtiquetaDTO> etiquetas = new ArrayList<>();
        for(Etiqueta e : etiquetaList){
            etiquetas.add(e.getDTO());
        }
        productoDTO.setEtiquetas(etiquetas);
        return productoDTO;
    }
    
}
