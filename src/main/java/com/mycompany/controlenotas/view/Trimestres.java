package com.mycompany.controlenotas.view;

import com.mycompany.controlenotas.NotaFinal;
import com.mycompany.controlenotas.app.ControleNotas;
import com.mycompany.controlenotas.util.Sessao;

import javax.swing.*;
import java.awt.*;

public class Trimestres extends JFrame {

    private final Long idAluno;

    public Trimestres() {
        super("Trimestres");

        // 🔐 pega da sessão
        this.idAluno = Sessao.getAlunoId();

        if (idAluno == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Sessão inválida. Faça login novamente.",
                    "Acesso negado",
                    JOptionPane.ERROR_MESSAGE
            );
            dispose();
            new ControleNotas().setVisible(true);
            return;
        }

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titulo = new JLabel(
                "<html><h1>Selecione a opção desejada</h1></html>",
                SwingConstants.CENTER
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        painel.add(titulo, gbc);

        JButton primeiroTri = new JButton("1º TRIMESTRE");
        JButton segundoTri = new JButton("2º TRIMESTRE");
        JButton terceiroTri = new JButton("3º TRIMESTRE");
        JButton notaFinal = new JButton("Nota Final");
        JButton logout = new JButton("Sair");

        gbc.gridwidth = 3;
        gbc.gridy = 1;
        painel.add(primeiroTri, gbc);

        gbc.gridy = 2;
        painel.add(segundoTri, gbc);

        gbc.gridy = 3;
        painel.add(terceiroTri, gbc);

        gbc.gridy = 4;
        painel.add(notaFinal, gbc);

        gbc.gridy = 5;
        painel.add(logout, gbc);

        // ações
        primeiroTri.addActionListener(e -> {
            new PrimeiroTri().setVisible(true);
            dispose();
        });

        segundoTri.addActionListener(e -> {
            new SegundoTri().setVisible(true);
            dispose();
        });

        terceiroTri.addActionListener(e -> {
            new TerceiroTri().setVisible(true);
            dispose();
        });

        notaFinal.addActionListener(e -> {
            new NotaFinal().setVisible(true);
            dispose();
        });

        logout.addActionListener(e -> logout());

        add(painel);
    }

    private void logout() {
        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja sair da conta?",
                "Confirmar logout",
                JOptionPane.YES_NO_OPTION
        );

        if (opcao == JOptionPane.YES_OPTION) {
            Sessao.logout();
            dispose();
            new ControleNotas().setVisible(true);
        }
    }
}
