/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.servlet;

import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.dto.ProductoBasicoDTO;
import comprasventasweb.dto.ProductoDTO;
import comprasventasweb.dto.UsuarioDTO;
import comprasventasweb.service.CategoriaService;
import comprasventasweb.service.ProductoService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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



@WebServlet(name = "ProductosListar", urlPatterns = {"/ProductosListar"})
public class ProductosListar extends HttpServlet {

    @EJB
    private ProductoService productoServices;
    
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
        RequestDispatcher rd;
        
        usuario = (UsuarioDTO)session.getAttribute("usuario");
        if (usuario == null) { 
            response.sendRedirect("login.jsp");
            
        } else if (usuario.getAdministrador()){
            List<ProductoDTO> listaProductos = this.productoServices.searchAllInverso2();                       
            request.setAttribute("listaProductos", listaProductos);
            rd = request.getRequestDispatcher("productosAdmin.jsp");
            rd.forward(request, response);
            
        }else {
            
            String search = (String)request.getParameter("busqueda");
            //String searchEtiquetas = (String)request.getParameter("busquedaEtiquetas");
            String select = (String) request.getParameter("selectBuscar");
            String categoria = (String) request.getParameter("categoria");
            if(categoria == ""){
                categoria = null;
            }
            
            if(search == null || search.trim().equals("") || select == null){
                select = "";
                search = "";
            }
            
            List<ProductoBasicoDTO> listaProductos = new ArrayList<>();
            String[] words;
            String inicio;
            String end;
            String formato;
            
            if(categoria == null){
                switch(select) {
                    case "TituloDescripcion":
                        words = search.split(" ");
                        for(int i=0; i<words.length; i++){
                            List<ProductoBasicoDTO> listaWord = this.productoServices.searchByTituloDescripcion(words[i]);
                            listaProductos.removeAll(listaWord);
                            listaProductos.addAll(listaWord);
                        }
                        break;
                    
                    case "Titulo":
                        words = search.split(" ");
                        for(int i=0; i<words.length; i++){
                            List<ProductoBasicoDTO> listaWord = this.productoServices.searchByTitulo(words[i]);
                            listaProductos.removeAll(listaWord);
                            listaProductos.addAll(listaWord);
                        }
                        break;
                    
                    case "Descripcion":
                        words = search.split(" ");
                        for(int i=0; i<words.length; i++){
                            List<ProductoBasicoDTO> listaWord = this.productoServices.searchByDescripcion(words[i]);
                            listaProductos.removeAll(listaWord);
                            listaProductos.addAll(listaWord);
                        }
                        break;
                    
                    case "Etiqueta":
                        words = search.split(" ");
                        String pal;
                        for(int i=0; i<words.length; i++){
                            pal = words[i];
                            if(pal != null && pal.length() > 1 && pal.charAt(0) == '#'){
                                pal = pal.substring(1, pal.length());
                            }
                            List<ProductoBasicoDTO> listaWord = this.productoServices.searchByEtiquetas(pal);
                            listaProductos.removeAll(listaWord);
                            listaProductos.addAll(listaWord);
                        }
                        break;
                    
                    case "FechaHora":
                        words = search.split("-");
                        inicio = words[0].trim();
                        formato = "\\d{2}/\\d{2}/\\d{4}\\s{1,}\\d{2}:\\d{2}";
                        if(search.matches(formato)){ //Fecha concreta
                            listaProductos = this.productoServices.searchByFechaHora(inicio);
                        }else if(search.matches(formato +"\\s*-\\s*"+ formato)){ //Periodo entre dos fechas
                            end = words[1].trim();
                            listaProductos = this.productoServices.searchByFechaHoraEntre(inicio, end);
                        }else{ //Formato incorrecto
                            listaProductos = null;
                        }
                        break;
                    
                    case "Fecha":
                        words = search.split("-");
                        inicio = words[0].trim();
                        formato = "\\d{2}/\\d{2}/\\d{4}";
                        if(search.matches(formato)){ //Fecha concreta
                            listaProductos = this.productoServices.searchByFecha(inicio);
                        }else if(search.matches(formato +"\\s*-\\s*"+ formato)){ //Periodo entre dos fechas
                            end = words[1].trim();
                            listaProductos = this.productoServices.searchByFechaEntre(inicio, end);
                        }else{ //Formato incorrecto
                            listaProductos = null;
                        }
                        break;
                        
                    case "Hora":
                        words = search.split("-");
                        inicio = words[0].trim();
                        formato = "\\d{2}:\\d{2}";
                        if(search.matches(formato)){ //Hora concreta del dia actual
                            listaProductos = this.productoServices.searchByHora(inicio);
                        }else if(search.matches(formato + "\\s*-\\s*" + formato)){ //Periodo entre dos horas del dia actual
                            end = words[1].trim();
                            listaProductos = this.productoServices.searchByHoraEntre(inicio, end);
                        }else{ //Formato Incorrecto
                            listaProductos = null;
                        }
                        break;
                    default:
                        listaProductos = this.productoServices.searchAllInverso(); 
                }
            } else {
                int id = Integer.parseInt(categoria.substring(1, categoria.length()));
                if(categoria.charAt(0) == 'B'){
                    listaProductos = this.productoServices.searchBySubcategory(id);
                    request.setAttribute("subcategoria", id+"");
                }else{
                    listaProductos = this.productoServices.searchByCategory(id);
                    request.setAttribute("categoria", id+"");
                }
            }
            
            request.setAttribute("selectBusqueda", select);
            request.setAttribute("busqueda", search);
            
            List<CategoriaDTO> categorias = this.categoriaService.searchAll(); 
            request.setAttribute("listaCategorias", categorias);
            
            request.setAttribute("listaProductos", listaProductos);
            rd = request.getRequestDispatcher("paginaPrincipal.jsp");
            rd.forward(request, response);
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
