/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ProductoDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private float precio;
    private Date hora;
    private Date fecha;
    private String foto;
    private Float valoracionmedia;
    private List<EtiquetaDTO> etiquetas;
    private SubcategoriaDTO categoria;
    private UsuarioDTO vendedor;

    public ProductoDTO() {
    }

    public ProductoDTO(Integer productoId) {
        this.id = productoId;
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

    public List<EtiquetaDTO> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<EtiquetaDTO> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public SubcategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(SubcategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public UsuarioDTO getVendedor() {
        return vendedor;
    }

    public void setVendedor(UsuarioDTO vendedor) {
        this.vendedor = vendedor;
    }
    
}
