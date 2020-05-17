/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.dao;

import comprasventasweb.dto.ValoracionDTO;
import comprasventasweb.entity.Producto;
import comprasventasweb.entity.Usuario;
import comprasventasweb.entity.Valoracion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
@Stateless
public class ValoracionFacade extends AbstractFacade<Valoracion> {

    @PersistenceContext(unitName = "ProyWebComprasVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValoracionFacade() {
        super(Valoracion.class);
    }
    
     public List<Valoracion> searchByProductoYUser(int user, int pr) {        
        Query q = this.getEntityManager().createNamedQuery("Valoracion.findByProductoYUsuario");
        q.setParameter("usuario", user);
        q.setParameter("producto", pr);
        return q.getResultList();
    } 

    public List<Valoracion> obtenerListaValoraciones(int producto) {
       Query q = this.getEntityManager().createNamedQuery("Valoracion.findByProducto");
       q.setParameter("producto", producto);
       return q.getResultList();
    }
    
    public List<Valoracion> findByProducto(Producto producto){
       Query q = this.getEntityManager().createNamedQuery("Valoracion.findByProducto");
       q.setParameter("producto", producto.getId());
       return q.getResultList();
    }
    
    public List<Valoracion> findByUser(Usuario usuario){
       Query q = this.getEntityManager().createNamedQuery("Valoracion.findByUsuario");
       q.setParameter("usuario", usuario.getId());
       return q.getResultList();
    }
}
