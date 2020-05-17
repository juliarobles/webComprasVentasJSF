/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changePlaceholder() {
    var buscador = document.getElementById("busqueda");
    var select = document.getElementById("selectBuscar");
    var seleccionado = select.value;
    
    if(seleccionado == "FechaHora"){
        buscador.placeholder = "Ejemplo: 01/01/1990 20:54 o 01/01/1990 20:54 - 31/12/1990 23:54";
    } else if (seleccionado == "Fecha") {
        buscador.placeholder = "Ejemplo: 01/01/1990 o 01/01/1990 - 31/12/1990";
    } else if (seleccionado == "Hora") {
        buscador.placeholder = "Ejemplo: 20:54 o 20:54 - 21:54";
    } else if (seleccionado == "Etiqueta") {
        buscador.placeholder = "Separar por espacios";
    } else {
        buscador.placeholder = "";
    }
}
