/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.dto;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class CategoriaDTO {
    private Integer id;
    private String nombre;
    private List<SubcategoriaBasicaDTO> subcategoriaList;

    public CategoriaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<SubcategoriaBasicaDTO> getSubcategoriaList() {
        return subcategoriaList;
    }

    public void setSubcategoriaList(List<SubcategoriaBasicaDTO> subcategoriaList) {
        this.subcategoriaList = subcategoriaList;
    }

    
    
}
