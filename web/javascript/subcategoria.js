/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function cargarSubcategorias(subcategoria){
    var categoria = document.getElementById('categoria')
    var subcategoria = document.getElementById('subcategoria')
    var categoriaSeleccionada = categoria.value
    console.log(categoriaSeleccionada)
    subcategoria.innerHTML = '<option value="">Seleccione una subcategoria...</option>'
    if(categoriaSeleccionada != ""){
      var op = JSON.parse(categoriaSeleccionada)
    
       for (var i = 0; i < op.length; i++) {
            var option = document.createElement("option");
            option.text = op[i].nombre;
            option.value = op[i].id;
            subcategoria.add(option);
        }  
    }
    
    

  }
 
  

