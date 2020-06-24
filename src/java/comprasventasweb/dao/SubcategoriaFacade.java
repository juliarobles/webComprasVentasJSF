/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.dao;

import comprasventasweb.entity.Subcategoria;
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
public class SubcategoriaFacade extends AbstractFacade<Subcategoria> {

    @PersistenceContext(unitName = "ProyWebComprasVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubcategoriaFacade() {
        super(Subcategoria.class);
    }

    public List<Subcategoria> findListSubcategory(Integer id) {
        Query q = this.getEntityManager().createNamedQuery("Subcategoria.findByCategory");
        q.setParameter("id", id);
        
        return q.getResultList();
    }
    
}
