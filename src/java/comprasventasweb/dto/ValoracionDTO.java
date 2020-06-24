/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.dto;

import comprasventasweb.entity.Producto;
import comprasventasweb.entity.Usuario;

/**
 *
 * @author danim
 */
public class ValoracionDTO {
    private int valor;
    private UsuarioDTO usuario;
    private ProductoDTO producto;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

  
}
