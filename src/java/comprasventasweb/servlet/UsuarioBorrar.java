/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.servlet;

import javax.ejb.EJB;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.ProductoService;
import comprasventasweb.service.UsuarioService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Usuario
 */
@WebServlet(name = "UsuarioBorrar", urlPatterns = {"/UsuarioBorrar"})
public class UsuarioBorrar extends HttpServlet {
private static final Logger LOG = Logger.getLogger(UsuarioBorrar.class.getName());
    
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
        HttpSession session = request.getSession();
        UsuarioDTO user = (UsuarioDTO)session.getAttribute("usuario");
        String str;   
        
        if (user==null) { // Se ha llamado al servlet sin haberse autenticado
            response.sendRedirect("login.jsp");            
        } else {        
            str = request.getParameter("id");
            if (str == null) {
                LOG.log(Level.SEVERE, "No se ha encontrado el usuario a borrar");
                response.sendRedirect("UsuarioListar");  
            } else {
                boolean ok = this.usuarioService.remove(str);
                System.out.println("Hasta aqui");
                if (ok) { 
                    if(user.getAdministrador()){
                        response.sendRedirect("UsuarioListar"); 
                    } else { //no deberia darse nunca
                        response.sendRedirect("PerfilUsuario"); 
                    }                      
                } else {
                    response.sendRedirect("UsuarioListar");                    
                }       
            }
        }
        
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
