/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.dto;

import comprasventasweb.entity.Comentario;
import comprasventasweb.entity.Etiqueta;
import comprasventasweb.entity.Subcategoria;
import comprasventasweb.entity.Usuario;
import comprasventasweb.entity.Valoracion;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Usuario
 */
public class ProductoBasicoDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private float precio;
    private Date hora;
    private Date fecha;
    private String foto;
    private Float valoracionmedia;
    private UsuarioDTO vendedor;

    public ProductoBasicoDTO() {
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

    public UsuarioDTO getVendedor() {
        return vendedor;
    }

    public void setVendedor(UsuarioDTO vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductoBasicoDTO other = (ProductoBasicoDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
