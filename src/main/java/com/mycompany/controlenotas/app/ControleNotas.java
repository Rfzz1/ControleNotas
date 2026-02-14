package com.mycompany.controlenotas.app;

import com.mycompany.controlenotas.exception.ApiException;
import com.mycompany.controlenotas.model.AlunoDTO;
import com.mycompany.controlenotas.service.AuthService;
import com.mycompany.controlenotas.util.Sessao;
import com.mycompany.controlenotas.view.CadastroAlunoView;
import com.mycompany.controlenotas.view.Trimestres;

import javax.swing.*;
import java.awt.*;

public class ControleNotas extends JFrame {

    public ControleNotas() {

        setTitle("Controle de Notas");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JLabel titulo = new JLabel("Controle de Notas");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel labelNome = new JLabel("Aluno:");
        JTextField campoNome = new JTextField(20);

        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField(20);

        JButton entrar = new JButton("Entrar");
        JButton cadastrar = new JButton("Cadastrar");

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

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        painel.add(entrar, gbc);
        
        gbc.gridy = 4;
        painel.add(cadastrar, gbc);

        entrar.addActionListener(e -> {
            String aluno = campoNome.getText().trim();
            String senha = new String(campoSenha.getPassword());
            

            if (aluno.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha aluno e senha");
                return;
            }

            try {
                AlunoDTO alunoLogado = AuthService.login(aluno, senha);

                Sessao.login(alunoLogado.getId(), alunoLogado.getAluno());

                new Trimestres().setVisible(true);
                dispose();

            } catch (ApiException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cadastrar.addActionListener(e -> {
            new CadastroAlunoView().setVisible(true);
            dispose();
        });

        add(painel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ControleNotas().setVisible(true));
    }
}
