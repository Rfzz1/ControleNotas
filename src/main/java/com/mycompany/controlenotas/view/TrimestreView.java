package com.mycompany.controlenotas.view;

import com.mycompany.controlenotas.RegistroTarefasView;
import com.mycompany.controlenotas.app.ControleNotas;
import com.mycompany.controlenotas.util.Sessao;

import javax.swing.*;
import java.awt.*;

public class TrimestreView extends JFrame {

    protected final Long idAluno;
    protected final int trimestre;

    public TrimestreView(String tituloJanela, int trimestre) {
        super(tituloJanela);
        this.trimestre = trimestre;
        this.idAluno = Sessao.getAlunoId();

        if (idAluno == null) {
            JOptionPane.showMessageDialog(this,
                    "Sessão inválida. Faça login novamente.");
            dispose();
            new ControleNotas().setVisible(true);
            return;
        }

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        montarTela();
    }

    private void montarTela() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JLabel titulo = new JLabel(
                "<html><h1>" + getTitulo() + "</h1></html>",
                SwingConstants.CENTER
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        painel.add(titulo, gbc);

        JButton registro = new JButton("Registro de Provas/Trabalhos");
        JButton notas = new JButton("Boletim");

        JPanel botoes = new JPanel();
        botoes.add(registro);
        botoes.add(notas);

        gbc.gridy = 1;
        painel.add(botoes, gbc);

        registro.addActionListener(e -> {
            new RegistroTarefasView(trimestre).setVisible(true);
            dispose();
        });

        notas.addActionListener(e -> abrirBoletim());

        JButton voltar = new JButton("Voltar");
        gbc.gridy = 2;
        painel.add(voltar, gbc);

        voltar.addActionListener(e -> {
            new Trimestres().setVisible(true);
            dispose();
        });

        add(painel);
    }

    protected void abrirBoletim() {
        switch (trimestre) {
            case 1 -> new NotasPrimeiroT().setVisible(true);
            case 2 -> new NotasSegundoT().setVisible(true);
            case 3 -> new NotasTerceiroT().setVisible(true);
        }
        dispose();
    }

    protected String getTitulo() {
        return switch (trimestre) {
            case 1 -> "Primeiro Trimestre";
            case 2 -> "Segundo Trimestre";
            case 3 -> "Terceiro Trimestre";
            default -> "Trimestre";
        };
    }
}
