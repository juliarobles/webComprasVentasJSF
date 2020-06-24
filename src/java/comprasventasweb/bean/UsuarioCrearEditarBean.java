/*
 *AUTOR:
 * Julia Robles Medina 
 * Daniel Ruiz Aswani
 */
package comprasventasweb.bean;

import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.UsuarioService;
import java.io.IOException;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Usuario
 */
@Named(value = "usuarioCrearEditarBean")
@RequestScoped
public class UsuarioCrearEditarBean {

    @Inject
    private UsuarioBean usuarioBean;
    
    @EJB
    private UsuarioService usuarioService;
    
    protected UsuarioDTO usuarioSeleccionado;
    
    private static final Logger LOG = Logger.getLogger(UsuarioCrearEditarBean.class.getName());
    /**
     * Creates a new instance of UsuarioCrearEditarBean
     */
    public UsuarioCrearEditarBean() {
    }

    @PostConstruct
    public void init(){
        if (this.usuarioBean.getUsuario() == null) { // Registrarse
            this.usuarioSeleccionado = new UsuarioDTO(0);
        } else { //Editar usuario desde admin       
            this.usuarioSeleccionado = this.usuarioBean.getUsuarioSeleccionado();
            if (this.usuarioSeleccionado == null || !this.usuarioBean.getUsuario().getAdministrador()) { // Es crear nuevo usuario (registrar)
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
                } catch (IOException ex) {
                    LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
                } 
            }
        }
    }

    public UsuarioDTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    public String doGuardar(){
        this.usuarioService.createOrUpdate(this.usuarioSeleccionado);
        if(this.usuarioBean.getUsuario() == null){
            this.usuarioBean.setUsuario(this.usuarioService.buscarPorUsuario(this.usuarioSeleccionado.getUsuario()));
            return "paginaPrincipal";
        } else if(this.usuarioBean.getUsuario().getAdministrador()){
            return "usuariosAdmin?faces-redirect=true";
        } else {
            return null;
        }
    }
    
    public String doVolver(){
        if(this.usuarioBean.getUsuario().getAdministrador()){
            return "usuariosAdmin?faces-redirect=true";
        } else {
            return "login?faces-redirect=true";
        }
    }
    
        public void validarCorreo(FacesContext context, UIComponent toValidate, Object value){
        context = FacesContext.getCurrentInstance();
        String texto = (String) value;
        
        UsuarioDTO res =this.usuarioService.buscarPorCorreo(texto);
        
        if(res != null){//Ya hay un usuario con ese correo
            ((UIInput)toValidate).setValid(false);
            context.addMessage(toValidate.getClientId(context), new FacesMessage("El correo ya está en uso"));
        }
    }
    
    public void validarUsuario(FacesContext context, UIComponent toValidate, Object value){
        context = FacesContext.getCurrentInstance();
        String texto = (String) value;
        
        UsuarioDTO res =this.usuarioService.buscarPorUsuario(texto);
        
        if(res != null){//Ya hay un usuario con ese correo
            ((UIInput)toValidate).setValid(false);
            context.addMessage(toValidate.getClientId(context), new FacesMessage("El usuario ya está en uso"));
        }
    }
    
    
}
