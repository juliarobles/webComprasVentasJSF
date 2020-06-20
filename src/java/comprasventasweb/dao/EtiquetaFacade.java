/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.dao;

import comprasventasweb.entity.Etiqueta;
import comprasventasweb.entity.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
@Stateless
public class EtiquetaFacade extends AbstractFacade<Etiqueta> {
    
    @PersistenceContext(unitName = "ProyWebComprasVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @EJB
    protected ProductoFacade productoFacade;
    
    public EtiquetaFacade() {
        super(Etiqueta.class);
    }
    
    public Etiqueta findByName(String name){
        if(name != null){
            Etiqueta et = null;
            Query q = this.getEntityManager().createNamedQuery("Etiqueta.findByNombre");
            q.setParameter("nombre", name);

            List<Etiqueta> lista = q.getResultList();
            if(lista != null && !lista.isEmpty()){ //Comprobamos si no peta y si está vacía o no
                 et = lista.get(0);                //devolvemos el primer resultado que deberia de ser el único
            }
            return et;
        } else {
            return null;
        }
        
    }

    public void addProducto(Etiqueta et, Producto p) {
        if(et != null && p != null){
            List<Producto> lista = et.getProductoList();
            List<Etiqueta> lista2 = p.getEtiquetaList();
            if(lista == null){
               lista = new ArrayList<>();
            }
            if(lista2 == null){
                lista2 = new ArrayList<>();
            }
            if(!lista.contains(p) && !lista2.contains(et)){
                
                lista.add(p);
                et.setProductoList(lista);
                this.edit(et);
                
                lista2.add(et);
                p.setEtiquetaList(lista2);
                this.productoFacade.edit(p);
            }
        }
    }
    
    public void removeProducto(Etiqueta et, Producto p) {
        if(et != null && p != null){
            List<Producto> lista = et.getProductoList();
            //List<Etiqueta> lista2 = p.getEtiquetaList();
            //&& lista2 != null && lista2.contains(et)
            if(lista != null && lista.contains(p)){
                //EntityManager em = this.getEntityManager();
                
                lista.remove(p);
                et.setProductoList(lista);
                this.edit(et);
                //this.productoFacade.edit(p);
                
                /*
                lista2.remove(et);
                p.setEtiquetaList(lista2);
                em.persist(p);
                */
            }
            
        }
    }
    
    public void createOrUpdate(String nombre, Producto p){
        Etiqueta et = this.findByName(nombre);
        
        if (et == null) { 
            et = new Etiqueta(0);
            et.setNombre(nombre);
            this.create(et);
        }
        
        if(p != null){
            this.addProducto(et, p);     
        }

    }

    
}
