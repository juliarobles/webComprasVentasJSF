/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.UsuarioService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
    
    private static final Logger LOG = Logger.getLogger(UsuarioBean.class.getName());
    
    protected UsuarioDTO usuario;
    protected ProductoDTO productoSeleccionado;
    protected String volver;
    protected String actual;
    
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
        this.setProductoSeleccionado(null);
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

    public void doBorrar(UsuarioDTO usuario){ //antes era public String doBorrar(ProductoDTO producto){
        //Aqui lo que sea
        this.usuarioService.remove(Integer.toString(usuario.getId()));
        LOG.info("doBorrar(): " + this.hashCode());
        // return "perfil?faces-redirect=true";
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
    
    
    
}
