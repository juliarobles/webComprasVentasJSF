/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.dao;

import comprasventasweb.entity.Comentario;
import comprasventasweb.entity.Producto;
import comprasventasweb.entity.Usuario;
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
public class ComentarioFacade extends AbstractFacade<Comentario> {

    @PersistenceContext(unitName = "ProyWebComprasVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComentarioFacade() {
        super(Comentario.class);
    }
    
    public List<Comentario> findByProducto(Producto producto) {        
        Query q = this.getEntityManager().createNamedQuery("Comentario.findByProducto");
        q.setParameter("producto", producto);
        
        return q.getResultList();
    }

    public Iterable<Comentario> findByUser(Usuario usuario) {
        Query q = this.getEntityManager().createNamedQuery("Comentario.findByUser");
        q.setParameter("usuario", usuario);
        
        return q.getResultList();
    }
    
}
