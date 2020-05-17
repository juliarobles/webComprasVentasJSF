/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.servlet;

import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.CategoriaService;
import comprasventasweb.service.ProductoService;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "ProductoEditar", urlPatterns = {"/ProductoEditar"})
public class ProductoEditar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ProductoEditar.class.getName());
    
    @EJB
    private ProductoService productoService;
    
    @EJB
    private CategoriaService categoriaService;
    
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
        UsuarioDTO usuario;
        
        usuario = (UsuarioDTO)session.getAttribute("usuario");
        
        if (usuario==null) { // Se ha llamado al servlet sin haberse autenticado
            response.sendRedirect("login.jsp");            
        } else {        
            String str = request.getParameter("id"), destino;
            if(usuario.getAdministrador()){
                destino = "principalAdmin.jsp";
            } else {
                destino = "menuPrincipal.jsp";
            }
            if (str == null) {
                LOG.log(Level.SEVERE, "No se ha encontrado el producto a editar");
                response.sendRedirect(destino);            
            } else {
                ProductoDTO producto = this.productoService.searchById(str);
                if (producto == null) { //Esta situación no debería darse
                    LOG.log(Level.SEVERE, "No se ha encontrado el cliente a editar");
                    response.sendRedirect(destino);
                } else {
                    List<CategoriaDTO> categorias = this.categoriaService.searchAll();
                    
                    request.setAttribute("listaCategorias", categorias);
                    request.setAttribute("producto", producto);
                    if(!usuario.getAdministrador()){
                        request.setAttribute("destino", "PerfilUsuario");
                    } 
                    RequestDispatcher rd = request.getRequestDispatcher("creacionProducto.jsp");
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
