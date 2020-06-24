/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.service;

import comprasventasweb.dao.CategoriaFacade;
import comprasventasweb.dao.SubcategoriaFacade;
import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.dto.SubcategoriaBasicaDTO;
import comprasventasweb.entity.Categoria;
import comprasventasweb.entity.Subcategoria;
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
    
    @EJB
    private SubcategoriaFacade subcategoriaFacade;
    
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
    
    protected List<SubcategoriaBasicaDTO> convertToDTOSub (List<Subcategoria> lista) {
        List<SubcategoriaBasicaDTO> listaDTO = null;
        if (lista != null) {
            listaDTO = new ArrayList<>();
            for (Subcategoria c : lista) {
                listaDTO.add(c.getDTOBasico());
            }
        }
        return listaDTO;
    }   
    
    public List<CategoriaDTO> searchAll () {
        List<Categoria> lista = this.categoriaFacade.findAll();
        return this.convertToDTO(lista);
    }
    
    public List<SubcategoriaBasicaDTO> listSubcategory(Integer id) {
        List<Subcategoria> lista = this.subcategoriaFacade.findListSubcategory(id);
        return this.convertToDTOSub(lista);
    }
}
