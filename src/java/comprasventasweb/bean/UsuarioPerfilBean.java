/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.ProductoService;
import comprasventasweb.service.UsuarioService;
import java.io.IOException;
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
@Named(value = "usuarioPerfilBean")
@RequestScoped
public class UsuarioPerfilBean {

    @Inject
    private UsuarioBean usuarioBean;
    
    @EJB
    private ProductoService productoService;
    
    @EJB
    private UsuarioService usuarioService;
    
    private static final Logger LOG = Logger.getLogger(UsuarioPerfilBean.class.getName());
    
    protected UsuarioDTO usuario;
    protected List<ProductoDTO> listaProductos;
    /**
     * Creates a new instance of UsuarioPerfilBean
     */
    public UsuarioPerfilBean() {
    }
    
    @PostConstruct
    public void init(){
        this.usuario = this.usuarioBean.getUsuario();
        if (this.usuario == null) { // Se ha llamado sin haberse autenticado
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }           
        } else {
            listaProductos = this.productoService.searchByUser(usuario);
        }
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public List<ProductoDTO> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoDTO> listaProductos) {
        this.listaProductos = listaProductos;
    }
    
}
