/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.dto.SubcategoriaBasicaDTO;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.ProductoService;
import comprasventasweb.service.UsuarioService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Usuario
 */
@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    @EJB
    private UsuarioService usuarioService;
    @EJB
    private ProductoService productoService;
    
    private static final Logger LOG = Logger.getLogger(UsuarioBean.class.getName());
    
    protected UsuarioDTO usuario;
    protected ProductoDTO productoSeleccionado;
    protected String volver;
    protected String actual;
    protected boolean modoCrear;
    protected List<SubcategoriaBasicaDTO> listaSubcategorias;
    
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    //BORRAR ESTA FUNCION ENTERA CUANDO SE HAGA EL LOGIN DE VERDAD
    @PostConstruct
    public void init(){
        usuario = this.usuarioService.buscarPorCorreo("julia@gmail.com");
    }
    
    public String doCerrarSesion(){
        return irA("login");
    }
    
    public String doCrearProducto(){
        this.modoCrear = true;
        this.setProductoSeleccionado(null);
        this.listaSubcategorias = null;
        return irA("creacionProducto");
    }
    
    public String doEditarProducto(ProductoDTO producto){
        this.modoCrear = false;
        this.setProductoSeleccionado(producto);
        this.listaSubcategorias = null;
        return irA("creacionProducto");
    }
    
    public String doPerfil(){
        return irA("perfil");
    }
    
    public String doListarProductos(){
        return irA("paginaPrincipal");
    }
    
    private String irA(String ir){
        volver = actual;
        actual = ir;
        return actual;
    }

    public String doBorrar(ProductoDTO producto){
        
        this.productoService.remove(Integer.toString(producto.getId()));
        LOG.info("doBorrar(): " + this.hashCode());
        return "perfil?faces-redirect=true";
    }
    
    public String doBorrarUsuario(UsuarioDTO usuario){
        
        this.usuarioService.remove(Integer.toString(usuario.getId()));
        LOG.info("doBorrar(): " + this.hashCode());
        return "perfil?faces-redirect=true";
    }
    
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public ProductoDTO getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(ProductoDTO productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public List<SubcategoriaBasicaDTO> getListaSubcategorias() {
        return listaSubcategorias;
    }

    public void setListaSubcategorias(List<SubcategoriaBasicaDTO> listaSubcategorias) {
        this.listaSubcategorias = listaSubcategorias;
    }

    public boolean isModoCrear() {
        return modoCrear;
    }

    public void setModoCrear(boolean modoCrear) {
        this.modoCrear = modoCrear;
    }


    
    
    
    
}
