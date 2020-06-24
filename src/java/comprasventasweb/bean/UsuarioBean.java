/*
 * AUTORES:
 * Julia Robles Medina (doCerrarSesion, doCrearProducto, doEditarProducto, doPerfil, doListarProductos)
 * Roberto Michán Sánchez (doLogin, comprobarAdmin, comprobarLogin)
 * Tomás (doBorrarProducto)
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
import java.util.Locale;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    @EJB
    private UsuarioService usuarioService;
    @EJB
    private ProductoService productoService;
    
    private static final Logger LOG = Logger.getLogger(UsuarioBean.class.getName());
    private final static Locale ENGLISH = new Locale("en");
    private final static Locale SPANISH = new Locale("es");
    
    private Locale currentLocale; 
    protected UsuarioDTO usuario;
    protected ProductoDTO productoSeleccionado;
    protected UsuarioDTO usuarioSeleccionado;
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
        this.usuarioSeleccionado = null;
        this.productoSeleccionado = null;
        this.listaSubcategorias = null;
        return "login";
    }
    
    public String doCrearProducto(){
        this.modoCrear = true;
        this.setProductoSeleccionado(null);
        this.listaSubcategorias = null;
        return "creacionProducto";
    }
    
    public String doEditarProducto(ProductoDTO producto){
        this.modoCrear = false;
        this.setProductoSeleccionado(producto);
        this.listaSubcategorias = null;
        return "creacionProducto";
    }
    
    public String doUsuarioEditar(UsuarioDTO usuario){
        if(this.usuario != null && this.usuario.getAdministrador()){
            this.setUsuarioSeleccionado(usuario);
            return "editarUsuario";
        } else {
            return "login";
        }
    }
    
    public String doVerProducto(int id){
        this.modoCrear = false;
        this.setProductoSeleccionado(this.productoService.searchById(id+""));
        //this.listaSubcategorias = null;
        return "verProducto";
    }
    public String doPerfil(){
        return "perfil";
    }
    
    public String doPerfil(UsuarioDTO usuario){
        if(this.usuario != null && this.usuario.getAdministrador()){
            this.setUsuarioSeleccionado(usuario);
        }
        return "perfil";
    }
    
    public String doListarProductos(){
        return "paginaPrincipal";
    }

    public String doBorrarProducto(ProductoDTO producto){
        if(this.usuario != null && (this.usuario.getId() == producto.getVendedor().getId() || this.usuario.getAdministrador())){
            this.productoService.remove(Integer.toString(producto.getId()));
            LOG.info("doBorrar(): " + this.hashCode());
        }
        if(usuario.getAdministrador()){
            return "productosAdmin?faces-redirect=true";
        } else {
            return "perfil?faces-redirect=true";
        }
    }
    
    public Locale getCurrentLocale() {
        return(currentLocale);
    }

    public String setEnglish() {
        currentLocale=ENGLISH;
        changeLocale();
        return null;
    }
  
    public String setSpanish() {
        currentLocale=SPANISH;
        changeLocale();    
        return null;
    }
  
    private void changeLocale () {
        UIViewRoot view = FacesContext.getCurrentInstance()
        			.getViewRoot();
        view.setLocale(currentLocale);
    }
    
    public boolean isSpanish(){
        return this.currentLocale == null || this.currentLocale.equals(SPANISH);
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
    
    public String comprobarAdmin() {
        if(usuario == null || !usuario.getAdministrador()){
            return "paginaPrincipal";
        } else {
            return null;
        }
    }
    
    public String comprobarLogin() {
        if(usuario != null){
            return null;
        } else {
            return "login";
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

    public UsuarioDTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }   
}
