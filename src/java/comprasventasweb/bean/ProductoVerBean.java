/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.dto.EtiquetaDTO;
import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.service.ProductoService;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;


/**
 *
 * @author danim
 */
@Named(value = "productoVerBean")
@RequestScoped
public class ProductoVerBean {
    
    @Inject
    private UsuarioBean usuarioBean;
    protected List<EtiquetaDTO> listaEtiquetas;
    protected ProductoDTO producto;
    protected int id;
    @EJB
    private ProductoService productoService;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.setProducto(this.productoService.searchById(id + ""));
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
        this.setListaEtiquetas(this.producto.getEtiquetas());
    }

    public List<EtiquetaDTO> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<EtiquetaDTO> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
    }
    /**
     * Creates a new instance of ProductoVerBean
     */
    public ProductoVerBean() {
    }
    
    @PostConstruct
    public void init(){
      
    }
    
    public String mediaPr(){
        String res = "";
        DecimalFormat df = new DecimalFormat("#.##");
        
        res= df.format(this.producto.getValoracionmedia())+ "/5★";
        return res;
    }
    
    public String formatearFecha(){
        String res = "";
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        res = fecha.format(this.producto.getFecha()) + " " + hora.format(producto.getHora());
        return res;
    }
    
    public boolean esPropio(){
        boolean propio = false;
        System.out.println(producto.getVendedor().getId() + " vendedor");
        System.out.println(this.usuarioBean.getUsuario().getId()+ " yo");
        if(producto.getVendedor().getId().equals(this.usuarioBean.getUsuario().getId())){
            propio = true;
        }
        return propio;
    }
}
