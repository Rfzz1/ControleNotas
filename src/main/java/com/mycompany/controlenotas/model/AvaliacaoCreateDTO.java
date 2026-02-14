
package com.mycompany.controlenotas.model;

import java.time.LocalDate;

public class AvaliacaoCreateDTO {

    private Long alunoId;
    private int trimestre;
    private Long materiaId;
    private Long tipoId;

    private String titulo;
    private String descricao;
    private double valorMax;
    private double nota;
    private LocalDate data;

    public AvaliacaoCreateDTO(
            Long alunoId,
            int trimestre,
            Long materiaId,
            Long tipoId,
            String titulo,
            String descricao,
            double valorMax,
            double nota,
            LocalDate data
    ) {
        this.alunoId = alunoId;
        this.trimestre = trimestre;
        this.materiaId = materiaId;
        this.tipoId = tipoId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.valorMax = valorMax;
        this.nota = nota;
        this.data = data;
    }
}

