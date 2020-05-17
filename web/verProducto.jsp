<%-- 
    Document   : verProducto
    Created on : 30-mar-2020, 14:16:53
    Author     : Usuario
    Funcion    : En esta pantalla se podrá ver toda la información del producto con detalle, habrá un botón de comprar que no se implementará.
                 Se mantendrá el botón del perfil y el añadir producto. Además aqui se podrá comentar y valorar el producto.
--%>

<%@page import="comprasventasweb.dto.EtiquetaDTO"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="comprasventasweb.dto.UsuarioDTO"%>
<%@page import="comprasventasweb.dto.ComentarioDTO"%>
<%@page import="comprasventasweb.entity.Comentario"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="comprasventasweb.dto.ProductoDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
        <%
        HttpSession sesion = request.getSession();
        ProductoDTO pr = (ProductoDTO)sesion.getAttribute("producto");
        UsuarioDTO user = (UsuarioDTO)sesion.getAttribute("usuario");
        Integer valoracion = (Integer)request.getAttribute("valoracion");
        String destino = (String)request.getAttribute("editar");
        Boolean propio = false;
        //De primeras supondremos que producto no es null, si da error pues se arregla y listo (o ponemos una ventana 
        //de ha ocurrido un error)
        //Vamos a poner que ponga un producto aleatorio, luego descomentaremos las lineas de arriba
        
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
            
        if(user.getId() == pr.getVendedor().getId() || user.getAdministrador()) {
            propio = true;
        }
        
        String media = "", precio = "";
        if(pr.getValoracionmedia() > 0){
            media = df.format(pr.getValoracionmedia()) + "/5★";
        }
        
        Float p = pr.getPrecio();
        if(p % 1 == 0){
            precio = p.intValue() + "";
        } else {
            precio = p + "";
        }
        
        if(destino != null && destino.equals("si")){
            destino = "PerfilUsuario?id=" + user.getId();
        } else {
            destino = "ProductosListar";
        }

        %>
    
    <head>    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=pr.getTitulo()%></title>
    </head>
    
    <body>
       <link rel="stylesheet" href="CSS/formularios.css">
       <link rel="stylesheet" href="CSS/verProducto.css">
       <script type="text/javascript" src="javascript/comentario.js"></script>
       <script type="text/javascript" src="javascript/ponerEstrellas.js"></script>
       <a class="volver" href=<%= destino %>>&#8592 Volver atrás</a></br>
        <div class="todo">
            
            <div class="contenido">
                <img class="imagen" src="<%=pr.getFoto()%>" align="left">
                <h1><%=pr.getTitulo()%></h1>
                <p class="fila">
                    <categoria><a href="ProductosListar?categoria=<%= "B"+pr.getCategoria().getId() %>"><%=pr.getCategoria().getNombre()%></a></categoria>
                    <media><%= media %></media>
                </p>
                <p class="descripcion"><%=pr.getDescripcion()%></p>
                <p>
                <%  for(EtiquetaDTO et : pr.getEtiquetas()){
                %>
                <a href="ProductosListar?busqueda=<%= et.getNombre()%>&selectBuscar=Etiqueta">#<%= et.getNombre()%></a>
                <%
                }
                %>
                </p>
                <p class="fila">
                    <vendedor>Vendido por: @<%=pr.getVendedor().getUsuario() %></vendedor>
                    <fecha><%= fecha.format(pr.getFecha()) + " " + hora.format(pr.getHora()) %></fecha>
                </p>
                
                <h5>Precio</h5>
                <h5 class="precio"><%= precio %>€</h5>
                <%
                if(!propio){
                %>
                    <button>COMPRAR</button>
                <%
                }    
                %>
                
            </div>
            
            <div class="comentarios">
                
                <%
                  if(!propio){  
                %>    
                
                <h3>Queremos saber tu opinión</h3>
                <table>
                    <tr>
                        <td>
                            <input type="hidden" id="val" name="val" value="<%= valoracion %>" />
                            <p> ¿Que nota le pondrías a este producto? </p>
                            <form class="estre" method="post" action="ValoracionGuardar?id=<%= pr.getId() %>">
                                <p class="clasificacion"> 
                                    <input id="radio1" type="radio" name="estrellas" value="5" onclick="javascript:submit()">
                                    <label id="valor5" for="radio1">★</label>
                                    <input id="radio2" type="radio" name="estrellas" value="4"onclick="javascript:submit()">
                                    <label id="valor4" for="radio2">★</label>
                                    <input id="radio3" type="radio" name="estrellas" value="3"onclick="javascript:submit()">
                                    <label id="valor3" for="radio3">★</label>
                                    <input id="radio4" type="radio" name="estrellas" value="2"onclick="javascript:submit()">
                                    <label id="valor2" for="radio4">★</label>
                                    <input id="radio5" type="radio" name="estrellas" value="1"onclick="javascript:submit()">
                                    <label id="valor1" for="radio5">★</label>
                                </p>
                            </form>
                        </td>
                        <td>  
                            <p> ¿Te gustaría añadir un comentario? </p>
                            <button onclick="document.getElementById('id01').style.display='block'" class="botonComentario">Pulsa aquí para añadir un comentario</button>
                        </td>
                    </tr>
                </table>
                        
                <%
                  }
                %>
                    
                <div id="id01" class="modal">
                    <form class="modal-content animate" action="ComentarioGuardar" method="post">
                        <h3>Nuevo comentario</h3>
                        <textarea class="contenidoComentario"  maxlength="300"cols="60" rows="8" name="comentario" required ></textarea>
                        <p><button type="submit" class="comentarioSubmit">Enviar comentario</button></p>
                        <p><button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancelar</button></p>
                    </form>
                </div>

                <h3>Todos los comentarios</h3>
                <%
                List<ComentarioDTO> comentarios = (List)request.getAttribute("listaComentarios");
                if (comentarios == null || comentarios.isEmpty()) {
                %>          
                    <h4>Ninguna opinión ha sido publicada todavía</h4>
                <% 
                } else {
                    for(ComentarioDTO c : comentarios){%>
                        <p class="emisor"> @<%=c.getUsuario().getUsuario() %> - <%= fecha.format(c.getFecha()) + " - " + hora.format(c.getHora()) %></p>
                        <p class="comentarioContainer"><%= c.getTexto() %></p>
                        <% if(c.getUsuario().getId() == user.getId() || user.getAdministrador()){
                        %>
                            <p><a class="eliminar" href="ComentarioBorrar?id=<%= c.getId() %>">Eliminar comentario</a></p> 
                <%         }
                       
                    }
                }
                %>
            </div>
    </div> 
        
    </body>
</html>
