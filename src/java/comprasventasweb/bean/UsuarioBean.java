/*
 * Roberto Michán Sánchez (doLogin)
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
    protected String email;
    protected String clave;
    protected String status;
    
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    public String doCerrarSesion(){
        this.usuario = null;
        this.status = "";
        this.email = "";
        this.clave = "";
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
    
    public String doLogin () {        
        UsuarioDTO usuarioLog = this.usuarioService.buscarPorCorreo(this.email);
        if (usuarioLog == null) {
            this.status = "El usuario no se encuentra en la base de datos";
            this.email = "";
            this.clave = "";
            return null;
        } else if (!this.clave.equals(usuarioLog.getPassword())) {
           this.status = "La clave es incorrecta";            
            this.clave = "";           
            return null;           
        } else {
            this.usuario = usuarioLog;
            if(usuario.getAdministrador()){
                return "principalAdmin";
            } else {
                return "paginaPrincipal";
            }          
        }        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String correo) {
        this.email = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isModoCrear() {
        return modoCrear;
    }

    public void setModoCrear(boolean modoCrear) {
        this.modoCrear = modoCrear;
    }


    
    
    
    
}
