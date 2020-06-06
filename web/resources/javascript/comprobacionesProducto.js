/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function comprobar(){
    if (document.getElementById("titulo").value.trim() == "") {
        alert("El campo correo es obligatorio");
        return false;
    }
}

