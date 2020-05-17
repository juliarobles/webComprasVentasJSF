<%-- 
    Document   : editarUsuario
    Created on : 09-abr-2020, 18:19:26
    Author     : Usuario
--%>

<%@page import="comprasventasweb.dto.UsuarioDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar usuario</title>
    </head>
    <body>
        <%    
            UsuarioDTO Usuario = (UsuarioDTO)request.getAttribute("usuario");
            String usuario = Usuario.getUsuario() , email = Usuario.getEmail(), nombre = Usuario.getNombre(), foto = Usuario.getFoto(), password = Usuario.getPassword();
            Boolean administrador = Usuario.getAdministrador();
            Integer id = Usuario.getId();
            String editar = "1";
        %>
        <link rel="stylesheet" href="CSS/formularios.css">
        <link rel="stylesheet" href="CSS/producto.css">
        <a class="volver" href="UsuarioListar">&#8592 Volver atrás </a></br>
        <div class="todo">
            <form action="UsuarioGuardar">
                <input type="hidden" name="editar" value="<%= editar %>" />
                <input type="hidden" name="id" value="<%= id %>" />
                <table>
                    <tr>
                        <td>Usuario</td>
                        <td><input type="text" id="usuario" name="usuario" value="<%= usuario %>" size="30" minlength="1" maxlength="100" pattern="[A-Za-z0-9]+" required/></td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td><input type="email" name="email" value="<%= email %>" size="30" maxlength="100" /></td> 
                    </tr>
                    <tr>
                        <td>Nombre</td>
                        <td><input type="text" name="nombre" value="<%= nombre %>" maxlength ="100" pattern="[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,'-]{1,100}" required/></td> 
                    </tr>
                    <tr>
                        <td>Foto</td>
                        <td><input type="url" name="foto" value="<%= foto %>" size="30" maxlength="520" placeholder="URL de la imagen" /></td>
                    </tr>
                    <!--<tr>
                        <td>Administrador</td>
                        <%
                            String valorT = (administrador)?"checked":"";
                            String valorF = (!administrador)?"checked":"";
                        %>
                        <td>
                            <input type="radio" name="administrador" value="false" size="30" maxlength="30" <%=valorF%>/>NO<br/>
                            <input type="radio" name="administrador" value="true" size="30" maxlength="30" <%=valorT%> />SI
                        </td>   
                    </tr>
                    <tr>
                        <td></td>
                        <td><i>AVISO: Si haces administrador a un usuario no podrás quitarle el acceso admin posteriormente ni borrarlo</i></td>
                    </tr>-->
                    <tr>
                        <td>Contraseña</td>
                        <td><input type="text" name="password" value="<%= password %>" size="30" minlength="8" maxlength ="50" pattern="[A-Za-z0-9!?-]{8,50}" required/></td> 
                    </tr>
                </table>
                <button type="submit" onclick="return confirm('¿Estás seguro?');">Editar Usuario</button>
            </form>
        </div>
    </body>
</html>
