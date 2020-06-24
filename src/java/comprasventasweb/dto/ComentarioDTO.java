/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.dto;

import java.util.Date;

/**
 *
 * @author rober
 */
public class ComentarioDTO {
    
    private Integer id;
    private String texto;
    private Date hora;
    private Date fecha;
    private ProductoDTO producto;
    private UsuarioDTO usuario;
    
    public ComentarioDTO(){
        
    }

    public ComentarioDTO(int i) {
        this.id = i;
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

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
