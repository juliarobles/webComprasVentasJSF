/*
    AUTOR: Julia Robles Medina
*/
package comprasventasweb.bean;

import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.dto.EtiquetaDTO;
import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.service.CategoriaService;
import comprasventasweb.service.ProductoService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Usuario
 */
@Named(value = "productoCrearEditarBean")
@RequestScoped
public class ProductoCrearEditarBean implements Serializable {

    @Inject
    private UsuarioBean usuarioBean;
    
    @EJB
    private CategoriaService categoriaService;
    
    @EJB
    private ProductoService productoService;
    
    private static final Logger LOG = Logger.getLogger(ProductoCrearEditarBean.class.getName());
    
    protected ProductoDTO producto;
    protected List<CategoriaDTO> listaCategorias;
    protected Integer categoriaSeleccionada;
    protected Integer subcategoriaSeleccionada;
    protected String todasEtiquetas;
    /**
     * Creates a new instance of ProductoCrearEditarBean
     */
    public ProductoCrearEditarBean() {
    }
    
    @PostConstruct
    public void init(){
        if (this.usuarioBean.getUsuario() == null) { // Acceso no autorizado (sin autenticar)
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }
        } else {        
            this.producto = this.usuarioBean.getProductoSeleccionado();
            if (this.producto == null) { // Es crear nuevo producto
                this.producto = new ProductoDTO(0);
                this.producto.setVendedor(usuarioBean.getUsuario());
                this.todasEtiquetas = "";
            } else { // Es editar producto
                this.setCategoriaSeleccionada(this.producto.getCategoria().getCategoriaPadre().getId());
                this.subcategoriaSeleccionada = this.producto.getCategoria().getId();
                this.usuarioBean.setProductoSeleccionado(null);
                StringBuilder sb = new StringBuilder();
                for(EtiquetaDTO et : producto.getEtiquetas()){
                    sb.append("#");
                    sb.append(et.getNombre());
                    sb.append(" ");
                }
                this.todasEtiquetas = sb.toString();
            }
            this.listaCategorias = this.categoriaService.searchAll();
        }
        
    }

    public void actualizarListaSubcategorias(){
        if(this.categoriaSeleccionada != null){
            this.usuarioBean.setListaSubcategorias(this.categoriaService.listSubcategory(categoriaSeleccionada));
        }
    }
    
    public String doGuardar(){
        this.productoService.createOrUpdate(this.producto, this.subcategoriaSeleccionada, this.todasEtiquetas);
        return doVolver();
    }
    
    public String doVolver(){
        if(this.usuarioBean.isModoCrear() == true){
            return "paginaPrincipal?faces-redirect=true";
        } else {
            return "perfil?faces-redirect=true";
        }
    }
    
    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public List<CategoriaDTO> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<CategoriaDTO> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public Integer getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(Integer categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
        actualizarListaSubcategorias();
    }

    public Integer getSubcategoriaSeleccionada() {
        return subcategoriaSeleccionada;
    }

    public void setSubcategoriaSeleccionada(Integer subcategoriaSeleccionada) {
        this.subcategoriaSeleccionada = subcategoriaSeleccionada;
    }

    public String getTodasEtiquetas() {
        return todasEtiquetas;
    }

    public void setTodasEtiquetas(String todasEtiquetas) {
        this.todasEtiquetas = todasEtiquetas;
    }
    
}
