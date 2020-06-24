/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.dao;

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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "ProyWebComprasVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    
    public Usuario buscarPorCorreo(String user){
        //Para hacer la busqueda tendremos que hacer una query (consulta)
        Query q;
        //Ademas tendremos que crear la variable para el usuario que devolveremos en cuestion
        //si no hay usuario la devolveremos a null, por tanto tendremos que iniciarlizarla como tal
        Usuario usu = null;
        //Ademas la consulta nos devuelve una lista de usuarios que tendremos que comprobar
        //así que crearemos también la lista de usuarios donde almacenaremos esta informacion
        List<Usuario> lista;
        
        //Para acceder a la named query predefinida en la clase entidad correspondiente a, en este caso, usuario
        //tendremos que hacer lo siguiente
        q = this.getEntityManager().createNamedQuery("Usuario.findByEmail");
        //Hemos llamado al entity correspondiente a esta clase y hemos creado una consulta poniendo 
        //el nombre de la ya predefinida en la clase entidad como Usuario.findByEmail
        
         q.setParameter("email", user); //Con esto lo que hacemos es igualar el parámetro de la consulta
         //a el introducido por el usuario
         
         //Ahora tenemos que pasar los datos obtenidos de la consulta a la lista de usuarios correspondiente
         lista = q.getResultList();
         
         
         //Finalmente comprobaremos si lista tiene algun valor, si no lo tiene es que el email es incorrecto 
         //Si hubiese algún valor solo debería de haber uno, ya que el email es único
         
         if(lista != null && !lista.isEmpty()){ //Comprobamos si no peta y si está vacía o no
             usu = lista.get(0);                //devolvemos el primer resultado que deberia de ser el único
         }
        
        return usu; //Modificar
    }

    public Usuario buscarPorUsuario(String usuario) {
         //Para hacer la busqueda tendremos que hacer una query (consulta)
        Query q;
        //Ademas tendremos que crear la variable para el usuario que devolveremos en cuestion
        //si no hay usuario la devolveremos a null, por tanto tendremos que iniciarlizarla como tal
        Usuario usu = null;
        //Ademas la consulta nos devuelve una lista de usuarios que tendremos que comprobar
        //así que crearemos también la lista de usuarios donde almacenaremos esta informacion
        List<Usuario> lista;
        
        //Para acceder a la named query predefinida en la clase entidad correspondiente a, en este caso, usuario
        //tendremos que hacer lo siguiente
        q = this.getEntityManager().createNamedQuery("Usuario.findByUsuario");
        //Hemos llamado al entity correspondiente a esta clase y hemos creado una consulta poniendo 
        //el nombre de la ya predefinida en la clase entidad como Usuario.findByEmail
        
         q.setParameter("usuario", usuario); //Con esto lo que hacemos es igualar el parámetro de la consulta
         //a el introducido por el usuario
         
         //Ahora tenemos que pasar los datos obtenidos de la consulta a la lista de usuarios correspondiente
         lista = q.getResultList();
         
         
         //Finalmente comprobaremos si lista tiene algun valor, si no lo tiene es que el email es incorrecto 
         //Si hubiese algún valor solo debería de haber uno, ya que el email es único
         
         if(lista != null && !lista.isEmpty()){ //Comprobamos si no peta y si está vacía o no
             usu = lista.get(0);                //devolvemos el primer resultado que deberia de ser el único
         }
        
        return usu; //Modificar
    }
}
