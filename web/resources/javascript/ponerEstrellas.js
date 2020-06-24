/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function() {
  var val = document.getElementById('valora:val').value;
  var radio = document.getElementById('valora:radio');
  var valoracion = val.valueOf();
  if(valoracion !== null && valoracion >= -1 && valoracion <= 5){
      for(var i = 0; i < valoracion; i++){
          var estrella = radio.rows[0].cells[4-i];
          estrella.style.color = 'orange';
      }
      if(valoracion !== 5){
         for(var i = valoracion; i < 5; i++){
            var estrella = radio.rows[0].cells[4-i];
            //estrella.style.color = 'black';
         } 
      }
      
  }
};
