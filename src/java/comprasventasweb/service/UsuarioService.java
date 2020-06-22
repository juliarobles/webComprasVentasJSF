/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.service;

import comprasventasweb.dao.ComentarioFacade;
import comprasventasweb.dao.ProductoFacade;
import comprasventasweb.dao.UsuarioFacade;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.entity.Comentario;
import comprasventasweb.entity.Producto;
import comprasventasweb.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * Autor: Julia Robles Medina (createOrUpdate) - todo lo demás es reutilizado de la otra entrega
 */

@Stateless
public class UsuarioService {
    
    private static final Logger LOG = Logger.getLogger(UsuarioService.class.getName());
    
    @EJB
    private UsuarioFacade usuarioFacade; 
    
    @EJB
    private ProductoFacade productoFacade;
    
    @EJB
    private ProductoService productoService;
    
    @EJB
    private ValoracionService valoracionService;
    
    @EJB
    private ComentarioFacade comentarioFacade;
   
    
    protected List<UsuarioDTO> convertToDTO (List<Usuario> listaUsuarios){
       List<UsuarioDTO> listaDTO = null;
        if (listaUsuarios != null) {
            listaDTO = new ArrayList<>();
            for (Usuario a : listaUsuarios) {
                listaDTO.add(a.getDTO());
            }
        }
        return listaDTO; 
    }
    
    public List<UsuarioDTO> searchAll() {
        List<Usuario> listaUsuarios = this.usuarioFacade.findAll();
        return this.convertToDTO(listaUsuarios);//To change body of generated methods, choose Tools | Templates.
    }
    
    public UsuarioDTO searchByUserId(Integer id){
        return this.usuarioFacade.find(id).getDTO();
    }
    
    public UsuarioDTO buscarPorCorreo(String user){
        
        Usuario usu = this.usuarioFacade.buscarPorCorreo(user);
        if(usu != null){
            return usu.getDTO();
        }else{
            return null;
        }
    }

    public UsuarioDTO buscarPorUsuario(String usuario) {//To do
         
        Usuario usu = this.usuarioFacade.buscarPorUsuario(usuario);
        if(usu != null){
            return usu.getDTO();
        }else{
            return null;
        }
    }

    public void createOrUpdate(UsuarioDTO usuario) {
        boolean esCrearNuevo = false;
        Integer id = usuario.getId();
        if (id == null || id == 0) { // Estamos en el caso de creación de un nuevo cliente
            esCrearNuevo = true;
        }
        usuario.setAdministrador(false);
        Usuario u = new Usuario(usuario);
        
        if(esCrearNuevo){
            this.usuarioFacade.create(u);
        } else {
            this.usuarioFacade.edit(u);
        }
    }  
    
    public boolean remove (String userId) {     
        
        // Busco al cliente a través de su clave primaria.
        // Como la clave primaria de la entidad Customer es de tipo Integer, 
        // tengo que hacer la transformación en la llamada a "find".        
        Usuario usuario = this.usuarioFacade.find(new Integer(userId));
        if (usuario == null) { //Esta situación no debería darse
            LOG.log(Level.SEVERE, "No se ha encontrado el cliente a borrar");
            return false;
        } else {
            for(Producto p : this.productoFacade.findByUserId(usuario)){
                this.productoService.remove(p.getId()+"");
            }
            this.valoracionService.eliminarTodasValoraciones(usuario.getId());
            eliminarComentarios(usuario);
            this.usuarioFacade.remove(usuario);
            return true;
        }
    }

    private void eliminarComentarios(Usuario usuario) {
        for(Comentario c : this.comentarioFacade.findByUser(usuario)){
            this.comentarioFacade.remove(c);
        }
    }

    public Usuario buscarPorID(Integer usuario) {
       return this.usuarioFacade.find(usuario);
    }

    
  
}
