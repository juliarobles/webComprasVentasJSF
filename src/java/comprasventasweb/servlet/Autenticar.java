/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.servlet;

import comprasventasweb.dto.UsuarioDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import comprasventasweb.service.UsuarioService;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
/**
 *
 * @author danim
 */
@WebServlet(name = "Autenticar", urlPatterns = {"/Autenticar"})
public class Autenticar extends HttpServlet {
    @EJB
    private UsuarioService usuarioService;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher rd;
        //Esto es para procesar la respuesta que hara nuestro servlet
        String user, contrasena, estado = null, destino;
        //El estado es la frase que se mostrará en el inicio de sesión si falla algo
        //En principio debería de ser que los datos introducidos no fueron correctos
        user = request.getParameter("usuario");
        contrasena = request.getParameter("clave");
        UsuarioDTO usu;//Aqui guardaremos la información del usuario obtenido por alguna función que lo busque según el nombre
        //o el correo
        //Comprobar si el usuario está en la base de datos
        usu = this.usuarioService.buscarPorCorreo(user);
        //No es una función abstracta o sea que tenemos que tener una variable de esta clase
        //al inicio de la clase para comprobar estas cosas 
        if(usu != null){//Si existe entonces comprobamos si la contraseña es igual a la introducida
        //Para obtener informacion del usuario solo tengo que coger usu
            if(contrasena.equals(usu.getPassword())){//Son iguales entonces abrir el menú principal 
                //Crearemos una sesion e introduciremos el usuario, cosa que nos permitirá acceder automaticamente
                //desde el login comprobando si hay usuario en la sesion
                HttpSession sesion = request.getSession();
                //Creamos la variable de la sesión en esta clase y la obtenemos de la request que nos llama
                sesion.setAttribute("usuario", usu);
                //A la sesion obtenida le añadimos el usuario para poder comprobar si ya hay uno la proxima vez que la llamemos
                if(usu.getAdministrador()){
                    destino = "PrincipalAdmin";
                } else {
                    destino = "ProductosListar";
                }
            }else{  //No son iguales entonces volver a inicio de sesión e informar de datos mal introducidos
                destino = "login.jsp";
                //En teoria creo que no tienen que decirte donde fallas al iniciar sesion
                //para que la gente no pueda sacarte la informacion pero asi queda más profesional
                estado = "Contraseña incorrecta";
                request.setAttribute("estado", estado);
            }
        }else{  //No existe el usuario, entonces error al introducir los datos (crear funcion(?))
            destino = "login.jsp";
            estado = "El usuario no existe";
            request.setAttribute("estado", estado);
        }
        
        
        rd = request.getRequestDispatcher(destino); 
        //aqui vamos metiendole el "destino" al request dispatcher
        rd.forward(request, response);
        //Aqui le decimos que vaya al destino al requestDispatcher
        //LE diremos que le pasamos los datos del request que nos llamó, donde hemos guardado los datos que hemos
        //Obtenido aqui
        
        //Completar autenticar 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
