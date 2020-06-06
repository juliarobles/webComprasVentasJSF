/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.UsuarioDTO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Usuario
 */
@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    protected UsuarioDTO usuario;
    protected String volver;
    protected String actual;
    
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    public String doCerrarSesion(){
        return irA("login");
    }
    
    public String doCrearProducto(){
        return irA("producto");
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
    
}
