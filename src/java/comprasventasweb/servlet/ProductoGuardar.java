/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.servlet;

import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.ProductoService;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ProductoGuardar", urlPatterns = {"/ProductoGuardar"})
public class ProductoGuardar extends HttpServlet {
    
    @EJB
    private ProductoService productoService;
    
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
        
        if (user==null) { // Se ha llamado al servlet sin haberse autenticado
            response.sendRedirect("login.jsp");            
        } else {
            String vendedor = request.getParameter("vendedor");
            if(vendedor.equals("")){
                vendedor = user.getId()+"";
            }
            
            this.productoService.createOrUpdate(
                request.getParameter("id"), vendedor, request.getParameter("titulo"),
                request.getParameter("descripcion"), request.getParameter("precio"), request.getParameter("subcategoria"),
                request.getParameter("foto"), request.getParameter("etiquetas")
            );

            String editar = request.getParameter("editar");
            if(editar != null && editar.equals("1")){
                if(user.getAdministrador()){
                    response.sendRedirect("ProductosListar");
                } else {
                    response.sendRedirect("PerfilUsuario"); 
                }
            } else {
                response.sendRedirect("ProductosListar");
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

    private boolean noTodoEspacios(String s){
        boolean otro = false;
        if(s != null){
            int i = 0;
            while(i < s.length() && !otro){
                if(s.charAt(i) != ' '){
                    otro = true;
                }
            }
        }
        return otro;
    }
    
    private boolean esUnFloat(String s){
        boolean esValido = true;
        try{
            Float.parseFloat(s);
        } catch(NumberFormatException e){
            esValido = false;
        }
        return esValido;
    }
}
