<%-- 
    Document   : principalAdmin
    Created on : 30-mar-2020, 14:49:52
    Author     : Usuario
    Funcion    : Esta página simplemente debe enlazar a la lista de productos y la lista de usuarios.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menú de administración</title>
    </head>
    <body>
        <link rel="stylesheet" href="CSS/formularios.css">
        <link rel="stylesheet" href="CSS/menuAdmin.css">
        <img class="headerImagen" src="imagenes/logoblancoGRANDE.png">
        
        <div class="todo">
            <table>
                <tr><td><h2> Acceder a </h2></td></tr>
                <tr><td><a class="boton" href="ProductosListar">Lista de productos</a></td></tr>
                <tr><td><a class="boton" href="UsuarioListar">Lista de usuarios</a></td></tr>
                <tr><td><a class="cerrar" href="CerrarSesion">Cerrar sesión</a></td></tr>
            </table>
        </div>
    </body>
</html>
