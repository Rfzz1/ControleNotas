package com.mycompany.controlenotas.model;

public class MateriaDTO {
    private Long id;
    private String materia;

    public Long getId() { return id; }
    public String getMateria() { return materia; }
    
    @Override
    public String toString() {
        return materia;
    }
}
