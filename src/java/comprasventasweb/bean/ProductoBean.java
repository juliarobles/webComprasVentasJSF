/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.service.ProductoService;
import java.beans.*;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tomy
 */
@Named(value = "productoBean")
@SessionScoped
public class ProductoBean implements Serializable {
    
    protected ProductoDTO producto;
    
    @EJB
    private ProductoService productoService;
    
    private static final Logger LOG = Logger.getLogger(ProductosListarBean.class.getName());
    
    public ProductoBean() {
    }
    
    public void doBorrar(ProductoDTO p){
        this.productoService.remove(Integer.toString(p.getId()));
        LOG.info("doBorrar(): " + this.hashCode());
        //return "clientesListado?faces-redirect=true";
        
    }
    
}
