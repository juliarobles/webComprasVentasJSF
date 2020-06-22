/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        if (this.usuarioBean.getUsuario() == null) { // Acceso no autorizado (sin autenticar)
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }
        } else {        
            this.usuarioSeleccionado = this.usuarioBean.getUsuarioSeleccionado();
            if (this.usuarioSeleccionado == null) { // Es crear nuevo usuario (registrar)
                this.usuarioSeleccionado = new UsuarioDTO(0);
            } else { // Es editar usuario
                if(!this.usuarioBean.getUsuario().getAdministrador()){
                   try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
                    } catch (IOException ex) {
                        LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
                    } 
                } else {
                    this.usuarioBean.setUsuarioSeleccionado(null);
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
        if(this.usuarioBean.getUsuario().getAdministrador()){
            return "usuariosAdmin?faces-redirect=true";
        } else {
            this.usuarioBean.setUsuario(this.usuarioService.buscarPorUsuario(this.usuarioSeleccionado.getUsuario()));
            return "paginaPrincipal?faces-redirect=true";
        }
    }
    
    public String doVolver(){
        if(this.usuarioBean.getUsuario().getAdministrador()){
            return "usuariosAdmin?faces-redirect=true";
        } else {
            return "login?faces-redirect=true";
        }
    }
    
    
    
    
}
