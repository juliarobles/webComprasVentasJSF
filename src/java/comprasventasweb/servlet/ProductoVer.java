/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.servlet;

import comprasventasweb.dto.ComentarioDTO;
import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.ComentarioService;
import comprasventasweb.service.ProductoService;
import comprasventasweb.service.ValoracionService;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author danim
 */
@WebServlet(name = "ProductoVer", urlPatterns = {"/ProductoVer"})
public class ProductoVer extends HttpServlet {
    @EJB
    private ProductoService productoService;
    
    @EJB
    private ComentarioService comentarioService;
    
    @EJB
    private ValoracionService valoracionService;
        
    private static final Logger LOG = Logger.getLogger(ProductoVer.class.getName());
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
                String id = request.getParameter("id");
                if(id == null){
                    LOG.log(Level.SEVERE, "No se ha encontrado el producto");
                    response.sendRedirect("ProductosListar");  
                } else {
                    //Arreglar cuando se añada el acceso al producto
                    ProductoDTO pr = this.productoService.searchById(id);
                    if (pr == null) {
                        LOG.log(Level.SEVERE, "No se ha encontrado el producto");
                        response.sendRedirect("ProductosListar");  
                    } else {
                        HttpSession sesion = request.getSession();
                        sesion.setAttribute("producto", pr);
                        List<ComentarioDTO> listaComentarios = this.comentarioService.searchByProducto(pr); 
                        request.setAttribute("listaComentarios", listaComentarios);
                        int valoracion = this.valoracionService.searchValoracion(user.getId(), pr.getId());
                        request.setAttribute("valoracion", valoracion);
                        String d = request.getParameter("editar");
                        if(d != null && d.equals("si")) request.setAttribute("editar", "si");

                        RequestDispatcher rd = request.getRequestDispatcher("verProducto.jsp");
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
