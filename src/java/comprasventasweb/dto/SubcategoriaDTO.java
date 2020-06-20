/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.dto;

import java.util.Objects;

/**
 *
 * @author Usuario
 */
public class SubcategoriaDTO {
    private Integer id;
    private String nombre;
    private CategoriaDTO categoriaPadre;

    public SubcategoriaDTO() {
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

    public CategoriaDTO getCategoriaPadre() {
        return categoriaPadre;
    }

    public void setCategoriaPadre(CategoriaDTO categoriaPadre) {
        this.categoriaPadre = categoriaPadre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubcategoriaDTO other = (SubcategoriaDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
