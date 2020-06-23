/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.dto.ComentarioDTO;
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
    
    protected boolean panelComentario;

    public boolean isPanelComentario() {
        return panelComentario;
    }

    public void setPanelComentario(boolean panelComentario) {
        this.panelComentario = panelComentario;
    }
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
    
    protected List<ComentarioDTO> listaComentarios;
    
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
        this.producto = this.usuarioBean.getProductoSeleccionado();
        this.listaComentarios = this.comentarioService.searchByProducto(producto);
        this.panelComentario = false;
    }

    public List<ComentarioDTO> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(List<ComentarioDTO> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }
    
    public void mostrarPanel(boolean val){
        System.out.println("Panel comentario cambiado a " + val);
        this.panelComentario = val;
        
    }
    
    public boolean mostrar(){
        return this.panelComentario;
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
        if(producto.getVendedor().getId().equals(this.usuarioBean.getUsuario().getId())){
            propio = true;
        }
        return propio;
    }
    
    public boolean comentarios(){
        boolean res = this.listaComentarios.size()>0;
        return res;
    }
    public void eliminarComentario(ComentarioDTO comentario){
                
                String idComentario = comentario.getId()+"";
                if(producto == null || idComentario == null || idComentario.isEmpty()){
                    LOG.log(Level.SEVERE, "No se ha encontrado el comentario a borrar"); 
                } else {
                    this.comentarioService.eliminarComentario(Integer.parseInt(idComentario));
                    actualizarListas();
                }
    }
    
    public boolean AdminOUsuario(ComentarioDTO comentario){
        boolean res = false;
 
        if(comentario.getUsuario().getId().equals(this.usuarioBean.getUsuario().getId()) 
                || this.usuarioBean.getUsuario().getAdministrador()){
            res = true;
        }
        return res;
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
                System.out.println("El contenido del comentario es5 " + contenido);
                this.comentarioService.comentario(pr, this.usuarioBean.getUsuario(), contenido);   
                actualizarListas();
            }
            
    }
    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    
    public void actualizarListas(){
        this.listaComentarios = this.comentarioService.searchByProducto(producto);
        System.out.println("Comentarios actuales "+this.listaComentarios.toString());
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
                this.valoracionService.valorar(valoracion, pr, this.usuarioBean.getUsuario());   
            }   
        }
    } 
    
    
}
