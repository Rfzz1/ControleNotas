package com.mycompany.controlenotas.model;

import java.time.LocalDate;

public class AvaliacaoDTO {
    private Long id;
    private int trimestre;
    private String titulo;
    private String descricao;
    private double valorMax;
    private double nota;
    private LocalDate data;
    
    private String materia;
    private String tipo;

    public Long getId() { return id; }
    public int getTrimestre() { return trimestre; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public double getValorMax() { return valorMax; }
    public double getNota() { return nota; }
    public LocalDate getData() { return data; }
    public String getMateria() { return materia; }
    public String getTipo() { return tipo; }
}
