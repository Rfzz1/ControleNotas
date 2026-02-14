package com.mycompany.controlenotas.view;

import com.mycompany.controlenotas.exception.ApiException;
import com.mycompany.controlenotas.service.AlunoService;
import com.mycompany.controlenotas.app.ControleNotas;

import javax.swing.*;
import java.awt.*;

public class CadastroAlunoView extends JFrame {

    public CadastroAlunoView() {
        setTitle("Cadastro de Aluno");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Cadastro de Aluno", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel labelNome = new JLabel("Aluno:");
        JTextField campoNome = new JTextField(20);

        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField(20);

        JLabel labelConfirmar = new JLabel("Confirmar senha:");
        JPasswordField campoConfirmar = new JPasswordField(20);

        JButton cadastrar = new JButton("Cadastrar");
        JButton voltar = new JButton("Voltar");

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        painel.add(labelNome, gbc);
        gbc.gridx = 1;
        painel.add(campoNome, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(labelSenha, gbc);
        gbc.gridx = 1;
        painel.add(campoSenha, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(labelConfirmar, gbc);
        gbc.gridx = 1;
        painel.add(campoConfirmar, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        painel.add(cadastrar, gbc);

        gbc.gridy = 5;
        painel.add(voltar, gbc);

        // AÇÕES
        cadastrar.addActionListener(e -> {
            String aluno = campoNome.getText().trim();
            String senha = new String(campoSenha.getPassword());
            String confirmar = new String(campoConfirmar.getPassword());

            if (aluno.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos");
                return;
            }

            if (!senha.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem");
                return;
            }

            try {
                AlunoService.cadastrar(aluno, senha);
                JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
                new ControleNotas().setVisible(true);
                dispose();

            } catch (ApiException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        voltar.addActionListener(e -> {
            new ControleNotas().setVisible(true);
            dispose();
        });

        add(painel);
    }
}
