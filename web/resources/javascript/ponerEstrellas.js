/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function() {
  var val = document.getElementById('val')
  console.log("Número de estrellas " + val)
  var valoracion = val.value
  
  if(valoracion !== null && valoracion >= -1 && valoracion <= 5){
      for(var i = 1; i <= valoracion; i++){
          var estrella = document.getElementById('valor'+i);
          estrella.style.color = 'orange';
      }
      if(valoracion !== 5){
         for(var i = valoracion+1; i <= 5; i++){
            var estrella = document.getElementById('valor'+i);
            estrella.style.color = 'grey';
         } 
      }
      
  }
};
