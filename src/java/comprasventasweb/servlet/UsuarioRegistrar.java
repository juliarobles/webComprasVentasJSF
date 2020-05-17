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
 * @author danim
 */
@WebServlet(name = "UsuarioRegistrar", urlPatterns = {"/UsuarioRegistrar"})
public class UsuarioRegistrar extends HttpServlet {

    
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
        
        String statusTotal = null, usuario,nombre, correo, foto, pass, destino = "registro.jsp"; //Añadir foto y admin ver qué valor es el de usuario normla
        Boolean comprobar = true;
        //String [] status;
        //Si sobra tiempo hacerlo con arrays 
        
        
        
        UsuarioDTO usu; //Aqui guardaremos la información de usuario para guardarlo en la base de datos
        
        correo = request.getParameter("correo");
        usu = this.usuarioService.buscarPorCorreo(correo);
        String statusCorreo = null;
        if(usu != null || correo.length()<=0 ){
            statusCorreo = "Este correo ya está en uso";
            request.setAttribute("statusCorreo", statusCorreo);
            comprobar = false;
        }
        usu = null;
        usuario = request.getParameter("usuario");
        usu = this.usuarioService.buscarPorUsuario(usuario);//Hacer funcion buscar usuario
        String statusUsuario = null;
        if(usu != null || usuario.length()<=0){
            statusUsuario = "Este nombre está pillado amig@";
            request.setAttribute("statusUsuario", statusUsuario);
            comprobar = false;
        }
         pass = request.getParameter("pass");
         String statusContraseña = null;
         if(pass.length()<=0 ){
             statusContraseña = "Introduce una contraseña";
             request.setAttribute("statusContraseña", statusContraseña);
             comprobar = false;
         }
         
         nombre = request.getParameter("nombre");
         String statusNombre = null;
         if(nombre.length()<=0){    //Si el nombre tiene tamaño 0 o menos entonces no es válido
             statusNombre = "Introduce un nombre";
             request.setAttribute("statusNombre", statusNombre);
             comprobar = false;
         }
         
         foto = request.getParameter("foto");
         
         if(comprobar){
             this.usuarioService.create(usuario, correo, nombre, pass, false, foto);
             HttpSession sesion = request.getSession();
             usu = this.usuarioService.buscarPorCorreo(correo);
             sesion.setAttribute("usuario", usu);
             destino = "ProductosListar";
         }
         
         RequestDispatcher rd ;
         rd = request.getRequestDispatcher(destino);
         rd.forward(request, response);
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
