<%-- 
    Document   : productosAdmin
    Created on : 09-abr-2020, 17:23:03
    Author     : rober
    Funcion    : Lista de productos, con sus respectivas acciones de consultar, editar y borrar
                 Para ver producto se puede acceder a la misma pantalla de verProducto.jsp pero como admin, es decir, podrá borrar comentarios pero no
                 podrá valorar ni comentar. Lo mismo con el editar producto y el correspondiente servlet.
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="comprasventasweb.dto.EtiquetaDTO"%>
<%@page import="comprasventasweb.dto.ProductoDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de productos</title>
    </head><%
        List<ProductoDTO> productos = (List)request.getAttribute("listaProductos");

        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        %>
    <body>
        <link rel="stylesheet" href="CSS/menuAdmin.css">
        <a id="top"></a>
        <img class="headerImagen" src="imagenes/logo.png" width="800" height="100">
        <br>
        <a href="PrincipalAdmin">Menú de Administración</a> 
        <a href="CerrarSesion">Cerrar sesión</a>
        <h1>Lista de productos</h1>
        <%
        if (productos == null || productos.isEmpty()) {
        %>          
            <h2>Ningún producto ha sido publicado todavía</h2>
        <%
        } else {
        %>
        
        <table border="1">
            <tr>
              <th>Título</th>
              <th>Descripción</th>
              <th>Precio</th>
              <th>Hora</th>
              <th>Fecha</th>
              <th>Foto</th>
              <th>Valoracion</th>
              <th>Etiquetas</th>
              <th>Categoría</th>
              <th>Vendedor</th>
              <th></th>
              <th></th>
              <th></th>
            </tr>
            <% 
                for(ProductoDTO prod : productos){ 
            %>
            <tr>
              <td><%= prod.getTitulo()%></td>
              <td><%= prod.getDescripcion()%></td>
              <td><%= prod.getPrecio()%></td>
              <td><%= hora.format(prod.getHora())%></td>
              <td><%= fecha.format(prod.getFecha())%></td>
              <td><a href="<%= prod.getFoto() %>" target="_blank">Ver foto</td>
              <td><%= prod.getValoracionmedia()%></td>
              <% 
                List<String> etiquetas = new ArrayList<String>();
                for(EtiquetaDTO e : prod.getEtiquetas()){
                        etiquetas.add(e.getNombre());
              }%>
              <td><%= etiquetas.toString() %></td>
              <td><%= prod.getCategoria().getNombre() %></td>
              <td><a href="PerfilUsuario?id=<%= prod.getVendedor().getId() %>"><%=prod.getVendedor().getUsuario()%></a></td>
              <td><a href="ProductoVer?id=<%= prod.getId() %>">Consultar</td>
              <td><a href="ProductoEditar?id=<%= prod.getId() %>">Editar</a></td>
              <td><a href="ProductoBorrar?id=<%= prod.getId() %>" onclick="return confirm('¿Estás seguro?');">Borrar</a></td>
            </tr>
            <% 
                }
        }
        %>
        </table> 
        <a href="PrincipalAdmin">Menú de Administración</a> 
        <a href="CerrarSesion">Cerrar sesión</a>
        <a href="#top">Ir arriba</a>
    </body>
</html>
