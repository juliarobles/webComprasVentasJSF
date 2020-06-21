/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.dao;

import comprasventasweb.entity.Categoria;
import comprasventasweb.entity.Etiqueta;
import comprasventasweb.entity.Producto;
import comprasventasweb.entity.Subcategoria;
import comprasventasweb.entity.Usuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "ProyWebComprasVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }
    
    public Producto findById(Integer id){
        Query q = this.getEntityManager().createNamedQuery("Producto.findById");
        q.setParameter("id", id);
        Producto p = null;
        
        List<Producto> lista = q.getResultList();
        if(lista != null && !lista.isEmpty()){ //Comprobamos si no peta y si está vacía o no
             p = lista.get(0);                //devolvemos el primer resultado que deberia de ser el único
        }
        
        return p;
    }
    
    public List<Producto> findByUserId(Usuario user) {        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByVendedor");
        q.setParameter("user", user);
        
        return q.getResultList();
    }
    
    public List<Producto> findByTituloDescripcion(String search) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByTituloDescripcion");
        q.setParameter("titulo", "%"+search.toUpperCase()+"%");
            
        return q.getResultList();
    }
    
    public List<Producto> findByTitulo(String search) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByTitulo");
        q.setParameter("titulo", "%"+search.toUpperCase()+"%");
            
        return q.getResultList();
    }
    
    public List<Producto> findByDescripcion(String search) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByDescripcion");
        q.setParameter("descripcion", "%"+search.toUpperCase()+"%");
            
        return q.getResultList();
    }
    
    public List<Producto> findByFecha(String search) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByFecha");
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(search);
            q.setParameter("fecha", date);
        } catch (ParseException ex) {
            Logger.getLogger(ProductoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q.getResultList();
        
    }
    
    public List<Producto> findByHora(String search) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByHora");
        Date date1, date2; 
        try {
            String uno = search + ":00";
            String dos = search + ":59";
            date1 = new SimpleDateFormat("kk:mm:ss").parse(uno);
            date2 = new SimpleDateFormat("kk:mm:ss").parse(dos);
            q.setParameter("hora1", date1);
            q.setParameter("hora2", date2);
        } catch (ParseException ex) {
            Logger.getLogger(ProductoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q.getResultList();
        
    }
    
    public List<Producto> findByFechaHora(String search) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByFechaHora");
        String[] fh1 = search.split(" ");
        Date date, hora1, hora2; 
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(fh1[0]);
            hora1 = new SimpleDateFormat("kk:mm:ss").parse(fh1[1]+":00");
            hora2 = new SimpleDateFormat("kk:mm:ss").parse(fh1[1]+":59"); 
            q.setParameter("fecha", date);
            q.setParameter("hora1", hora1);
            q.setParameter("hora2", hora2);
        } catch (ParseException ex) {
            Logger.getLogger(ProductoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q.getResultList();
        
    }
    
    public List<Producto> findByFechaEntre(String inicio, String end) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByFechaEntre");
        Date date;
        Date date2; 
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(inicio);
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(end);
            q.setParameter("inicio", date);
            q.setParameter("end", date2);
            
        } catch (ParseException ex) {
            Logger.getLogger(ProductoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q.getResultList();
        
    }
    
    public List<Producto> findByHoraEntre(String inicio, String end) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByHora");
        Date date1;
        Date date2; 
        try {
            String uno = inicio+":00";
            String dos = end +":59";
            date1 = new SimpleDateFormat("kk:mm:ss").parse(uno);
            date2 = new SimpleDateFormat("kk:mm:ss").parse(dos);
            q.setParameter("hora1", date1);
            q.setParameter("hora2", date2);
            
        } catch (ParseException ex) {
            Logger.getLogger(ProductoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q.getResultList();
        
    }
    
    public List<Producto> findByFechaHoraEntre(String inicio, String end) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByFechaHoraEntre");
        String[] fh1 = inicio.split(" ");
        String[] fh2 = end.split(" ");
        Date date1, date2, hora1, hora2; 
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(fh1[0]);
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(fh2[0]);
            hora1 = new SimpleDateFormat("kk:mm:ss").parse(fh1[1]+":00");
            hora2 = new SimpleDateFormat("kk:mm:ss").parse(fh2[1]+":59"); 
            q.setParameter("fecha1", date1);
            q.setParameter("fecha2", date2);
            q.setParameter("hora1", hora1);
            q.setParameter("hora2", hora2);
        } catch (ParseException ex) {
            Logger.getLogger(ProductoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q.getResultList();
        
    }
    
    
    public List<Producto> findByEtiquetas(String search) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByEtiquetas");
        q.setParameter("etiqueta", search.toUpperCase());
        //System.out.println(q.getResultList()); //Para ver si devulve algo   
        return q.getResultList();
    }
    
    public List<Producto> findAllInverso(){
       Query q = this.getEntityManager().createNamedQuery("Producto.findAllInverso");
       return q.getResultList(); 
    }
    
    public void vaciarEtiquetas(Producto producto) {
         EntityManager em = this.getEntityManager();
         producto.setEtiquetaList(new ArrayList<>());
         this.edit(producto);
    }
    
    public List<Producto> findBySubcategory(Subcategoria id) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findBySubcategory");
        q.setParameter("id", id);
            
        return q.getResultList();
    }
    
    public List<Producto> findByCategory(Categoria id) {        
        
        Query q = this.getEntityManager().createNamedQuery("Producto.findByCategory");
        q.setParameter("id", id);
            
        return q.getResultList();
    }
    
}
