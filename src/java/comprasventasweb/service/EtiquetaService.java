/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.service;

import comprasventasweb.dao.EtiquetaFacade;
import comprasventasweb.dao.ProductoFacade;
import comprasventasweb.entity.Etiqueta;
import comprasventasweb.entity.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Usuario
 */

@Stateless
public class EtiquetaService {
    
    @EJB
    private EtiquetaFacade etiquetaFacade;
    
    @EJB
    private ProductoFacade productoFacade;
    
    private static final Logger LOG = Logger.getLogger(EtiquetaService.class.getName());
    
    public void createOrUpdate(String nombre, int productoId){
        if(productoId > 0){
            Producto p = this.productoFacade.find(productoId);
            if(p != null){
                this.etiquetaFacade.createOrUpdate(nombre, p);
                
            }
        }
    }
    
    public boolean removeProducto(String nombre, int productoId){
        Etiqueta et = this.etiquetaFacade.findByName(nombre);
        if (et == null) { //Esta situación no debería darse
            LOG.log(Level.SEVERE, "No se ha encontrado el cliente a borrar");
            return false;
        } else {
            if(productoId > 0){
                Producto p = this.productoFacade.find(productoId);
                if(p != null){
                  List<Producto> lista = et.getProductoList();
                  if(lista.contains(p) && lista.size() == 1){
                     return remove(et);
                  } else {
                     this.etiquetaFacade.removeProducto(et, p);
                     return true;
                  }
                }
            }
            return false;
        }
    }
    
    public boolean remove(String nombre){
        Etiqueta et = this.etiquetaFacade.findByName(nombre);
        return remove(et);
    }
    
    private boolean remove(Etiqueta et){
        if (et == null) { //Esta situación no debería darse
            LOG.log(Level.SEVERE, "No se ha encontrado el cliente a borrar");
            return false;
        } else {
            this.etiquetaFacade.remove(et);
            return true;
        }
    }
}
