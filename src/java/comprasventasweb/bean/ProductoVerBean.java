/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.dto.EtiquetaDTO;
import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.service.ComentarioService;
import comprasventasweb.service.ProductoService;
import comprasventasweb.service.ValoracionService;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


/**
 *
 * @author danim
 */
@Named(value = "productoVerBean")
@RequestScoped
public class ProductoVerBean {
    
    
    protected int valoracion;

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        System.out.println("SetValoracion");
        this.valoracion = valoracion;
        guardarValoracion();
    }
    
    protected String contenido;
    
    @Inject
    private UsuarioBean usuarioBean;
    protected List<EtiquetaDTO> listaEtiquetas;
    protected ProductoDTO producto;
    protected int id;
    @EJB
    private ProductoService productoService;
     @EJB
    private ComentarioService comentarioService;
    @EJB
    private ValoracionService valoracionService;
    
    private static final Logger LOG = Logger.getLogger(ProductoVerBean.class.getName());

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.setProducto(this.productoService.searchById(this.id + ""));
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
        System.out.println(producto + " vendedor");
        System.out.println(this.usuarioBean.getUsuario().getId()+ " yo");
        if(producto.getVendedor().getId().equals(this.usuarioBean.getUsuario().getId())){
            propio = true;
        }
        return propio;
    }
    
    public void comentar(){
        ProductoDTO pr = this.producto;
            if(pr==null){
                LOG.log(Level.SEVERE, "No se ha encontrado el producto a valorar");
                 try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("paginaPrincipal.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }        
            } else {
               
                this.comentarioService.comentario(pr, this.usuarioBean.getUsuario(), contenido);
                try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("verProducto.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }  
    
            }
    }
    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public void guardarValoracion(){
        if (this.usuarioBean.getUsuario()==null) { // Se ha llamado al servlet sin haberse autenticado
             try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }        
        } else { 
            ProductoDTO pr = this.producto;
            if(pr == null){
                LOG.log(Level.SEVERE, "No se ha encontrado el producto a valorar");
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("productosListar.jsf");           
                } catch (IOException ex) {
                    LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
                }   
            } else {
                System.out.println("Guardando valoración de " + valoracion + " para el producto " + pr.getTitulo()
                + " del usuario " + this.usuarioBean.getUsuario());
                this.valoracionService.valorar(valoracion, pr, this.usuarioBean.getUsuario());   
            }   
        }
    }    
}
