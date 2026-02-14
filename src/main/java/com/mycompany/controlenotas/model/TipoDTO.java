
package com.mycompany.controlenotas.model;


public class TipoDTO {
    private Long id;
    private String avaliacao;
    
    public Long getId() {
        return id;
    }
    
    public String getAvaliacao() {
        return avaliacao;
    }
    
    
    @Override
    public String toString() {
        return avaliacao;
    }
}
