/*
Autor: Tomás Goizueta Díaz

*/
package comprasventasweb.bean;

import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.UsuarioService;
import java.io.IOException;
import java.io.Serializable;
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
 * @author Tomy
 */
@Named(value = "usuariosListarBean")
@RequestScoped
public class UsuariosListarBean implements Serializable{

    /**
     * Creates a new instance of UsuariosListarBean
     */
    @Inject
    private UsuarioBean usuarioBean;
    
    @EJB
    private UsuarioService usuariosService;
    
    protected List<UsuarioDTO> listaUsuarios;
    
    private static final Logger LOG = Logger.getLogger(UsuariosListarBean.class.getName());        
    
    
    public UsuariosListarBean() {
    }
    
    @PostConstruct
    public void init () {
        if (this.usuarioBean.getUsuario() == null) { // Acceso no autorizado (sin autenticar)
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }
        } else {
            this.listaUsuarios = this.usuariosService.searchAll();
            
            LOG.info("init(): " + this.hashCode());
        }
    }
    
    public List<UsuarioDTO> getListaUsuarios() {
        return listaUsuarios;
    }
    
    public String doBorrarUsuario(UsuarioDTO usuario){
        
        this.usuariosService.remove(Integer.toString(usuario.getId()));
        LOG.info("doBorrar(): " + this.hashCode());
        return "usuariosAdmin?faces-redirect=true";
    }
    
}
