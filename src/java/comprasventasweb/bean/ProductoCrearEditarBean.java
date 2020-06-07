/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.dto.EtiquetaDTO;
import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.dto.SubcategoriaBasicaDTO;
import comprasventasweb.service.CategoriaService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Usuario
 */
@Named(value = "productoCrearEditarBean")
@RequestScoped
public class ProductoCrearEditarBean {

    @Inject
    private UsuarioBean usuarioBean;
    
    @EJB
    private CategoriaService categoriaService;
    
    private static final Logger LOG = Logger.getLogger(ProductoCrearEditarBean.class.getName());
    
    protected ProductoDTO producto;
    protected boolean modoCrear;
    protected List<CategoriaDTO> listaCategorias;
    protected List<SubcategoriaBasicaDTO> listaSubcategorias;
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
                this.modoCrear = true;
                this.producto = new ProductoDTO(0);
                this.producto.setVendedor(usuarioBean.getUsuario());
                this.todasEtiquetas = "";
            } else { // Es editar producto
                this.modoCrear = false;
                this.categoriaSeleccionada = this.producto.getCategoria().getCategoriaPadre().getId();
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
            actualizarListaSubcategorias();
        }
        
    }

    public void actualizarListaSubcategorias(){
        if(this.categoriaSeleccionada != null){
            this.setListaSubcategorias(this.categoriaService.listSubcategory(categoriaSeleccionada));
        } else if(this.listaSubcategorias != null){
            this.listaSubcategorias.clear();
        } else {
            this.listaSubcategorias = new ArrayList<>();
        }
    }
    
    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public boolean isModoCrear() {
        return modoCrear;
    }

    public void setModoCrear(boolean modoCrear) {
        this.modoCrear = modoCrear;
    }

    public List<CategoriaDTO> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<CategoriaDTO> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<SubcategoriaBasicaDTO> getListaSubcategorias() {
        return listaSubcategorias;
    }

    public void setListaSubcategorias(List<SubcategoriaBasicaDTO> listaSubcategorias) {
        this.listaSubcategorias = listaSubcategorias;
    }

    public Integer getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(Integer categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
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
