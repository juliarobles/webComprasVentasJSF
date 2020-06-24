/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.service;

import comprasventasweb.dao.ComentarioFacade;
import comprasventasweb.dao.ProductoFacade;
import comprasventasweb.dao.UsuarioFacade;
import comprasventasweb.dao.ValoracionFacade;
import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.dto.ValoracionDTO;
import comprasventasweb.entity.Producto;
import comprasventasweb.entity.Usuario;
import comprasventasweb.entity.Valoracion;
import comprasventasweb.entity.ValoracionPK;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author danim
 */

@Stateless
public class ValoracionService {
    
     private static final Logger LOG = Logger.getLogger(ValoracionService.class.getName());
     
     @EJB
     private ValoracionFacade valoracionFacade;
     
     @EJB 
     private ProductoFacade productoFacade;
     
     @EJB
     private UsuarioFacade usuarioFacade;
     
     @EJB
     private ComentarioFacade comentarioFacade;
 
     
      public void createOrUpdate (int nota, int usuario, int producto) {
        List<Valoracion> val= this.valoracionFacade.searchByProductoYUser(usuario, producto);
        
        boolean esCrearNuevo = false;
        Valoracion valoracion;
        if ( val == null || val.isEmpty()) { // Estamos en el caso de creación de un nuevo cliente
             valoracion = new Valoracion(new ValoracionPK(producto, usuario)); // Aunque el id es autoincremental, hay ocasiones en las que
                                       // si no se le da un valor por defecto, da un error al guardarlo.
            esCrearNuevo = true;
        } else{
            valoracion = this.valoracionFacade.searchByProductoYUser(usuario, producto).get(0);
        }

        
        valoracion.setNota(nota);
        System.out.println("Informacion valoracion: " +producto + " " + usuario);
        valoracion.setProducto1(this.productoFacade.find(producto));
        valoracion.setUsuario1(this.usuarioFacade.find(usuario));
       
        
        if (esCrearNuevo) {
            this.valoracionFacade.create(valoracion);
        } else {
            this.valoracionFacade.edit(valoracion);
        } 
    }
         
   
    
    public void valorar(int v, ProductoDTO pr, UsuarioDTO usu){
        //Primero tenemos que ver si el usuario ha valorado ya antes este producto
            this.createOrUpdate(v, usu.getId(), pr.getId());
        //Finalmente actualizamos la media de las valoraciones
            actualizarMedia(pr.getId());
    }
    
    public void actualizarMedia (int producto){
        List<Valoracion> lista=  this.valoracionFacade.obtenerListaValoraciones(producto);
        float media = 0;
        if(lista != null && !lista.isEmpty()){ 
            int tam = 0;
            for(Valoracion v:lista){
                if(v.getNota()<=5 && v.getNota()>=0){
                   media+=v.getNota();
                   tam++;
                }
             }
            media = media/tam;
        } else {
            media = -1;
        }
         
       Producto pr =this.productoFacade.find(producto);
       pr.setValoracionmedia(media);
       this.productoFacade.edit(pr);
    }

    public List<Valoracion> searchValoraciones(Integer idpr){
        return this.valoracionFacade.findByProductoID(idpr);
    }
    public int searchValoracion(Integer user, Integer producto) {
        List<Valoracion> val = this.valoracionFacade.searchByProductoYUser(user, producto);
        if(val == null || val.isEmpty()){
            return -1;
        } else {
            return val.get(0).getNota();
        }
    }

    public void eliminarTodasValoraciones(int usuario) { //Consulta que borra todos
        for(Valoracion v : this.valoracionFacade.findByUser(this.usuarioFacade.find(usuario))){
            int producto = v.getProducto1().getId();
            this.valoracionFacade.remove(v);
            this.actualizarMedia(producto);
        }
    }
}