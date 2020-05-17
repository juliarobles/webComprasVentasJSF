<%-- 
    Document   : usuarios
    Created on : 07-abr-2020, 18:15:20
    Author     : yisus
--%>

<%@page import="comprasventasweb.dto.UsuarioDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu Principal Usuarios</title>
    </head>
    <%
        List<UsuarioDTO> usuarios = (List)request.getAttribute("listaUsuario");
    %>
    <body>
        <link rel="stylesheet" href="CSS/menuAdmin.css">
        <a id="top"></a>
        <img class="headerImagen" src="imagenes/logo.png" width="800" height="100">
        <br>
        <a href="PrincipalAdmin">Menú de Administración</a> 
        <a href="CerrarSesion">Cerrar sesión</a>
        <h1>Lista de usuarios</h1>
        <%
        if (usuarios == null || usuarios.isEmpty()) {
        %>          
            <h2>Ningún usuario ha sido creado todavía</h2>
         <%
        } else {
        %>
        <table border="1">
        <tr>
            <th>Id</th>
            <th>Usuario</th>
            <th>Email</th>
            <th>Nombre</th>
            <th>Administrador</th>
            <th>Foto</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        <%    
            for (UsuarioDTO usuario : usuarios) {
        %>
        <tr>
            <td><%= usuario.getId() %></td>
            <td><%= usuario.getUsuario() %></td>
            <td><%= usuario.getEmail()  %></td>
            <td><%= usuario.getNombre() %></td>
            <% if (usuario.getAdministrador()){
            %>
            <td>Si</td>
            <% }else{%>
            <td>No</td>
            <%}%>
            <td><a href="<%= usuario.getFoto() %>" target="_blank">Ver foto</td>
            <%
                if(usuario.getAdministrador()){
            %>
            <td>No permitido</td>
            <%
                } else {
            %>
            <td><a href="PerfilUsuario?id=<%= usuario.getId() %>">Consultar</a></td>
            <%
                }
            %>
            <%
                if(usuario.getAdministrador()){
            %>
            <td>No permitido</td>
            <%
                } else {
            %>
            <td><a href="UsuarioEditar?id=<%= usuario.getId() %>">Editar</a></td>
            <%
                }
            %>
            <%
                if(usuario.getAdministrador()){
            %>
            <td>No permitido</td>
            <%
                } else {
            %>
            <td><a href="UsuarioBorrar?id=<%= usuario.getId() %>" onclick="return confirm('¿Estás seguro? Esta acción no se puede deshacer');">Borrar</a></td>
            <%
                }
            %>
            
<%
            }
%>
        </table>
        <a href="PrincipalAdmin">Menú de Administración</a> 
        <a href="CerrarSesion">Cerrar sesión</a>
        <a href="#top">Ir arriba</a>
<%
        }
%>                     
                     
    </body>
</html>
