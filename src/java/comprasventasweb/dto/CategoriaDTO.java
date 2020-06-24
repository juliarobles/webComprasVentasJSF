/*
Autor: clase reutilizada de la entrega anterior
*/
package comprasventasweb.dto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final CategoriaDTO other = (CategoriaDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
}
