<%-- 
    Document   : perfil
    Created on : 30-mar-2020, 14:13:09
    Author     : Usuario
    Funcion    : Aqui el usuario podrá ver sus datos y los productos que vende, podrá editarlos o eliminarlos
                 Si nos sobra tiempo haremos un boton para que edite el perfil
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="comprasventasweb.dto.EtiquetaDTO"%>
<%@page import="comprasventasweb.dto.ProductoDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="comprasventasweb.dto.UsuarioDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfil</title>
    </head>
    <%
        UsuarioDTO usu = (UsuarioDTO)session.getAttribute("usuario");
         if (usu == null) {
            response.sendRedirect("login.jsp");  
            return;
        }
        
        UsuarioDTO user = (UsuarioDTO)request.getAttribute("usuario");
        if(user != null && !usu.getAdministrador()){
            response.sendRedirect("login.jsp");  
            return;
        } else if (!usu.getAdministrador()) {
            user = usu;
        }
         
        List<ProductoDTO> productos = (List)request.getAttribute("productosUsuario");
     %>
    <body>
        <link rel="stylesheet" href="CSS/formularios.css">
        <link rel="stylesheet" href="CSS/perfil.css">
        <%
            if(usu.getAdministrador()){
        %>
            <a class="volver" href="UsuarioListar">&#8592 Volver atrás </a></br>
        <%
            } else {
        %>
            <a class="volver" href="ProductosListar">&#8592 Volver al menú principal </a></br>
        <%
            }
        %>
        
        <div class="todo">
        <img class="avatar" id="avatar" src=<%= user.getFoto() %> width="200" height="200">
        <h1> <%= user.getNombre() %> </h1>
        <h2> @<%= user.getUsuario() %> </h2>
        <h3> Mis productos </h3>
        <%
        if (productos == null || productos.isEmpty()) {
        %>          
            <h2>Ningún producto ha sido publicado todavía</h2>
         <%
        } else {
            SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat hora = new SimpleDateFormat("HH:mm");

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
        %>
        <table>
        <tr>
            <th>FOTO</th>
            <th>TITULO</th>
            <th>DESCRIPCION</th>
            <th>SUBCATEGORIA</th>
            <th>ETIQUETAS</th>
            <th>PRECIO</th>         
            <th>MEDIA</th>
            <th>FECHA Y HORA</th>
            <th></th>
            <th></th>
        </tr>
        <%    
            for (ProductoDTO producto : productos) {
                String valoracion = "Sin valoraciones", precio = "";
                if(producto.getValoracionmedia() != -1){
                    valoracion = df.format(producto.getValoracionmedia())+"";
                }
                Float p = producto.getPrecio();
                if(p % 1 == 0){
                    precio = p.intValue() + "";
                } else {
                    precio = p + "";
                }
        %>
        <tr>
            <td width="100px" height="100px"><img class="imagen" src=<%= producto.getFoto() %>></td>
            <td><%= producto.getTitulo()  %></td>
            <td width="300px"><%= producto.getDescripcion() %></td>
            <td><%= producto.getCategoria().getNombre()  %></td>
            <td class="eti">
                <%  for(EtiquetaDTO et : producto.getEtiquetas()){
                %>
                <a href="ProductosListar?busqueda=<%= et.getNombre()%>&selectBuscar=Etiqueta">#<%= et.getNombre()%></a>
                <%
                }
                %>
            </td>
            <td><%= precio %>€</td>     
            <td><%= valoracion %></td> 
            <td><%= (fecha.format(producto.getFecha()) + " " + hora.format(producto.getHora()) )%></td>
            <td><a class="editar" href="ProductoVer?id=<%= producto.getId() %>&editar=si">Ver todo</a></td>
            <td><a class="editar" href="ProductoEditar?id=<%= producto.getId() %>">Editar</a></td>
            <td><a class="borrar" href="ProductoBorrar?id=<%= producto.getId() %>" onclick="return confirm('¿Estás seguro?');">Borrar</a></td>
        </tr>
        <%
            }// for
        %>
        </table>
        <%
        }//if
        %>
        </div>
    </body>
</html>
