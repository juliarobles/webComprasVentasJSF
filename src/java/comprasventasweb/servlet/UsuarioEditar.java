/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.servlet;

import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.UsuarioService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "UsuarioEditar", urlPatterns = {"/UsuarioEditar"})
public class UsuarioEditar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(UsuarioEditar.class.getName());
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
        
        if (session.getAttribute("usuario")==null) { // Se ha llamado al servlet sin haberse autenticado
            response.sendRedirect("login.jsp");            
        } else {        
            String ID = (String) request.getParameter("id");
            if (ID == null) {
                LOG.log(Level.SEVERE, "No se ha recibido id");
                response.sendRedirect("principalAdmin.jsp");            
            } else {
                UsuarioDTO usuario = this.usuarioService.searchByUserId(Integer.parseInt(ID));
                if (usuario == null) { //Esta situación no debería darse
                    LOG.log(Level.SEVERE, "No se ha encontrado el usuario a editar");
                    response.sendRedirect("principalAdmin.jsp");
                }else{
                    request.setAttribute("usuario", usuario);
                    
                    RequestDispatcher rd = request.getRequestDispatcher("editarUsuario.jsp");
                    rd.forward(request, response);
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
