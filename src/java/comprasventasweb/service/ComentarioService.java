/*
Autor: clase reutilizada de la entrega anterior
Daniel Ruiz Aswani (funcion comentario)
*/
package comprasventasweb.service;

import comprasventasweb.dao.ComentarioFacade;
import comprasventasweb.dao.ProductoFacade;
import comprasventasweb.dto.ComentarioDTO;
import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.entity.Comentario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rober
 */
@Stateless
public class ComentarioService {
    
    @EJB
    private ComentarioFacade comentarioFacade;
    
    @EJB
    private ProductoFacade productoFacade;
    
    @EJB
    private UsuarioService usuarioService;
    
    @EJB 
    private ProductoService productoService;
    
    protected List<ComentarioDTO> convertToDTO (List<Comentario> listaComentarios) {
        List<ComentarioDTO> listaDTO = null;
        if (listaComentarios != null) {
            listaDTO = new ArrayList<>();
            for (Comentario c : listaComentarios) {
                listaDTO.add(c.getDTO());
            }
        }
        return listaDTO;
    } 
    
    public List<ComentarioDTO> searchByProducto(ProductoDTO producto) {
        List<Comentario> listaComentarios = this.comentarioFacade.findByProducto(this.productoFacade.findById(producto.getId()));
        return this.convertToDTO(listaComentarios);
    }
    
    public void comentario (ProductoDTO pr, UsuarioDTO usu, String contenido){
        //Suponemos que tanto el producto como el usuario existen.
       Date fecha = new Date();
        
        createOrUpdate(pr.getId(), usu.getId(), contenido, fecha);
        
    }

    private void createOrUpdate(Integer producto, Integer usuario, String contenido, Date fecha) {
        Comentario comentario = new Comentario(0);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        comentario.setFecha(new Date());
        comentario.setHora(new Date());
        comentario.setTexto(contenido);
        comentario.setUsuario(this.usuarioService.buscarPorID(usuario));
        comentario.setProducto(this.productoService.buscarPorId(producto+""));
        
        this.comentarioFacade.create(comentario);
    }

    public void eliminarComentario(int idComentario) {
        Comentario comentario = this.comentarioFacade.find(idComentario);
        if(comentario != null){
            this.comentarioFacade.remove(comentario);
        }
    }
}
