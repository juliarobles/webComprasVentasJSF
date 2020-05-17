<%-- 
    Document   : paginaPrincipal
    Created on : 30-mar-2020, 14:03:57
    Author     : Usuario
    Funcion    : Aqui se verá el listado de productos y se podrá pulsar sobre ellos para verlos con mayor detalle, tambien contará con
                 un boton para acceder a su perfil con los productos vendidos por el propio usuario y con otro para añadir un nuevo producto
--%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="comprasventasweb.dto.SubcategoriaBasicaDTO"%>
<%@page import="comprasventasweb.dto.CategoriaDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="comprasventasweb.dto.ProductoBasicoDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menú principal</title>
        <link rel="stylesheet" href="CSS/paginaPrincipal.css">
    </head>
    <%
        List<ProductoBasicoDTO> productos = (List)request.getAttribute("listaProductos");
        List<CategoriaDTO> categorias = (List) request.getAttribute("listaCategorias");

        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        
    %>
    <body>
        <% String busqueda = (String) request.getAttribute("busqueda");
            if(busqueda == null){
                busqueda = "";
            }
           String sel = (String) request.getAttribute("selectBusqueda");
           int categoria = -1;
           String cate = (String) request.getAttribute("categoria");
           int subcategoria = -1;
           String subcate = (String) request.getAttribute("subcategoria");
           if(subcate != null){
               subcategoria = Integer.parseInt(subcate);
           } else if (cate != null) {
               categoria = Integer.parseInt(cate);
           }
           request.removeAttribute("subcategoria");
           request.removeAttribute("categoria");
           
           String[][] buscar = {{"TituloDescripcion", "Título y descripción"}, {"Titulo","Título"}, {"Descripcion","Descripción"}, {"Etiqueta","Etiquetas"}, 
                            {"FechaHora", "Fecha y hora"}, {"Fecha","Fecha"}, {"Hora","Hora"}};

        %>

        <div class="navbar" id="navbar">
            <a href="ProductosListar?busqueda= &selectBuscar= ">
                <img class="headerImagen" src="imagenes/logoblanco.png" width="100" height="30"> </a>
        <script type="text/javascript" src="javascript/buscador.js"></script> 
        <form action="ProductosListar">
            <select class="selectBuscar" id="selectBuscar" name="selectBuscar" onchange="changePlaceholder()">
                <%
                    for(String[] m : buscar){
                        String seleccionado = "";
                        if(sel!= null && sel.equals(m[0])){
                            seleccionado = "selected";
                        }
                %>
                    <option <%= seleccionado %> value=<%= m[0] %>><%= m[1]%></option>
                <%
                    }
                %>
            </select>
            <input type="text" name="busqueda" id="busqueda" value="<%=busqueda%>" placeholder="" size="30" maxlength="300">
        </form>
        
        <form action="ProductosListar">
            <select class="categorias" id = "categoria" name="categoria" onchange="this.form.submit()">
                            <option value="">Todas las categorías</option>
                        <%
                            
                            for (CategoriaDTO cat : categorias) {
                                String seleccionado = "";
                                if (categoria != -1 && categoria == cat.getId()) {
                                    seleccionado = "selected";
                                } 
    %>
                            <option <%= seleccionado %> value=<%= "A" + cat.getId() %>><%= cat.getNombre() %></option>
    <%                          for(SubcategoriaBasicaDTO sub : cat.getSubcategoriaList()){
                                    seleccionado = "";
                                    if (subcategoria != -1 && subcategoria == sub.getId()) {
                                        seleccionado = "selected";
                                    } 
    %>
                            <option <%= seleccionado %> value=<%= "B" + sub.getId() %>><%= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + sub.getNombre() %></option>
    <%
                                }
                            }
    %>  
            </select>
        </form>
            <a class="link" href="CerrarSesion">Cerrar sesión</a>
            <a class="link" href="PerfilUsuario">Perfil</a>
            <a class="Crear" href="ProductoCrear">Nuevo producto</a>
        </div>    
            
        <div class="main">
        <%
        if (productos == null || productos.isEmpty()) {
        %>          
        <center>
            <%
                if(!busqueda.isEmpty()){
            %>
                <h2>No se han encontrado productos que coincidan con su búsqueda </h2>
            <%
               } else if (categoria != -1 || subcategoria != -1){
            %>
                <h2>No se han encontrado productos de la categoría seleccionada</h2>
            <%
                } else {
            %>
                <h2>Ningún producto ha sido publicado todavía</h2>
            <%
                }
            %>
        </center>
         <%
        } else {
        %>
        <div class="band">
        <%  
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            String precio = "", valoracion = "", descripcion = "";
            for (ProductoBasicoDTO producto : productos) {
                Float p = producto.getPrecio();
                if(p % 1 == 0){
                    precio = p.intValue() + "";
                } else {
                    precio = p + "";
                }
                if(producto.getValoracionmedia() < 0){
                    valoracion = "";
                } else {
                    valoracion = df.format(producto.getValoracionmedia()) + "★";
                }
                descripcion = producto.getDescripcion();
                int max = 115;
                if(producto.getTitulo().length() > 30) {
                    max = 80;
                }
                if(descripcion.length() > max){
                    descripcion = descripcion.substring(0, max-3) + "...";
                } 
        %>
        <div class="item">
           <a href="ProductoVer?id=<%= producto.getId() %>" class="card">
            <div class="imagen">
                <img src=<%= producto.getFoto() %>>
            </div>
            <article>
                <div class ="top">
                   <vendedor>@<%= producto.getVendedor().getUsuario() %></vendedor>
                   <valoracion><%= valoracion %></valoracion>
                </div>
                <h4 class="tituloCard"><%= producto.getTitulo()  %></h1>
                <p class = "descripcionCard"><%= descripcion %></p>
                <div class="bottom">
                    <precio><%= precio %>€</precio>
                    <fecha><%= fecha.format(producto.getFecha()) + " " + hora.format(producto.getHora()) %></fecha>
                </div>
            </article>
           </a>
        </div>
        
        <%
            }// for
        %>

        </div>
        <%
        }//if
        %>

        </div>
    </body>
</html>
