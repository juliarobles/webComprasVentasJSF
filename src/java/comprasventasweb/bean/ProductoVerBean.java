/*
 * AUTOR:
 *Daniel Ruiz Aswani
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
import java.util.Date;
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
        System.out.println("Se ha establecido al valoración a " + this.valoracion);
        this.valoracion = valoracion;
        guardarValoracion();
    }
    
    protected String contenido;
    
    protected List<ComentarioDTO> listaComentarios;
    
    @Inject
    private UsuarioBean usuarioBean;
    protected List<EtiquetaDTO> listaEtiquetas;
    protected ProductoDTO producto;
    protected Integer id;
    @EJB
    private ProductoService productoService;
     @EJB
    private ComentarioService comentarioService;
    @EJB
    private ValoracionService valoracionService;
    
    private static final Logger LOG = Logger.getLogger(ProductoVerBean.class.getName());

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if(id != null && id !=0 ){
          this.id = id;
          this.setProducto(this.productoService.searchById(this.id + ""));
          this.usuarioBean.setProductoSeleccionado(producto);

        }else{
            this.id = this.usuarioBean.getProductoSeleccionado().getId();
            this.producto = this.usuarioBean.getProductoSeleccionado();
        }
        this.listaComentarios = this.comentarioService.searchByProducto(producto);
         valoracion = this.valoracionService.searchValoracion(this.usuarioBean.getUsuario().getId(), this.producto.getId()); 
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
        //this.usuarioBean.productoSeleccionado = this.productoService.searchById(this.id+"");
        //this.producto = this.usuarioBean.getProductoSeleccionado();
        
       
    
        if(this.usuarioBean.getProductoSeleccionado()!= null ){
            System.out.println("Se ha cogido el producto de usuario bean");
           // this.producto = this.usuarioBean.getProductoSeleccionado();
            this.setId(id);
            actualizarListas();
        }
    }

    public List<ComentarioDTO> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(List<ComentarioDTO> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }
    /*
    public void mostrarPanel(boolean val){
        System.out.println("Panel comentario cambiado a " + val);
        this.usuarioBean.setMostrarNuevoComentario(val);
        
    }*/
    
    public String mediaPr(){
        String res = "";
        DecimalFormat df = new DecimalFormat("#.##");
        if(this.producto.getValoracionmedia() > 0){
            res= df.format(this.producto.getValoracionmedia())+ "/5★ (" + this.valoracionService.searchValoraciones(id).size() + ")" ;
        }
        return res;
    }
    /*
    public String formatearFecha(Date f, Date h){
        
        String res = "";
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        res = fecha.format(f) + " " + hora.format(h);
        return res;
    }*/
    
    public boolean esPropio(){
        return this.usuarioBean.getUsuario().getAdministrador() || producto.getVendedor().getId().equals(this.usuarioBean.getUsuario().getId());
    }
    
    public boolean comentarios(){
        boolean res = this.listaComentarios.size()>0;
        return res;
    }
    public void eliminarComentario(ComentarioDTO comentario){
                System.out.println("He entrado a eliminar comentario");
                String idComentario = comentario.getId()+"";
                if(producto == null || idComentario == null || idComentario.isEmpty()){
                    LOG.log(Level.SEVERE, "No se ha encontrado el comentario a borrar"); 
                } else {
                    System.out.println("Eliminar comentario " + comentario.getTexto());
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
        this.setListaEtiquetas(this.producto.getEtiquetas());
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
                this.producto = this.productoService.searchById(this.producto.getId().toString());
                this.mediaPr();
                 try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("verProducto.jsf");           
                } catch (IOException ex) {
                    LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
                }   
            }   
        }
    } 
    
    
}
