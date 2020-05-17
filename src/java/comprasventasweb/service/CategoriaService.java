/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.service;

import comprasventasweb.dao.CategoriaFacade;
import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.entity.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Usuario
 */
@Stateless
public class CategoriaService {
    
    @EJB
    private CategoriaFacade categoriaFacade;
    
    /*
    @EJB
    private SubcategoriaFacade subcategoriaFacade;
    
    
    public List<SubcategoriaDTO> getSubcategorias(Integer id){
        List<SubcategoriaDTO> res = new ArrayList<>();
        for(Subcategoria s : this.subcategoriaFacade.findByCategory(id)){
            res.add(s.getDTO());
        }
        return res;
    } 
    */
    
    protected List<CategoriaDTO> convertToDTO (List<Categoria> lista) {
        List<CategoriaDTO> listaDTO = null;
        if (lista != null) {
            listaDTO = new ArrayList<>();
            for (Categoria c : lista) {
                listaDTO.add(c.getDTO());
            }
        }
        return listaDTO;
    }    
    
    public List<CategoriaDTO> searchAll () {
        List<Categoria> lista = this.categoriaFacade.findAll();
        return this.convertToDTO(lista);
    }
}
