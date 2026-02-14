package com.mycompany.controlenotas.view;

import com.mycompany.controlenotas.RegistroTarPT;
import com.mycompany.controlenotas.Tarefa;
import com.mycompany.controlenotas.db;
import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.model.AvaliacaoResponseDTO;
import com.mycompany.controlenotas.util.Sessao;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

//-------------------------------------------
//          BOLETIM - 1 TRIMESTRE
//-------------------------------------------
public class NotasPrimeiroT extends JFrame {
    
    private final Long idAluno;
    private JTable tabela;
    private DefaultTableModel modelo;
    private java.util.List<Tarefa> listaTarefas = new ArrayList<>();
    
    public NotasPrimeiroT() {
        super("Boletim - 1º TRI");
        this.idAluno = Sessao.getAlunoId();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
        //--------------------------------------
        //           TOPO (Botões)
        //--------------------------------------
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton registrar = new JButton("Registrar Nova Tarefa");
        topo.add(registrar);

        JButton voltar = new JButton("Voltar");
        topo.add(voltar);

        add(topo, BorderLayout.NORTH);
        
        //--------------------------------
        //          AÇÃO VOLTAR
        //--------------------------------
        voltar.addActionListener(e -> {
            new PrimeiroTri().setVisible(true);
            dispose();
        });
        
        //--------------------------------
        //          AÇÃO REGISTRAR
        //--------------------------------
        
        registrar.addActionListener(e -> {
            new RegistroTarPT().setVisible(true);
            dispose();
        });
        
        //-----------------------------
        //           TABELA
        //-----------------------------
        modelo = new DefaultTableModel(
                new String[]{"ID","Disciplina", "Nota"}, 
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta
            }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(25);
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);



        add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        SomarNotas();
        setVisible(true); 
        
    }
    
    //---------------------
    //       FUNÇÕES
    //---------------------
    
    // -------------------------------------------
    // SomarNotas
    // -------------------------------------------
    
private void SomarNotas() {
    try {
        modelo.setRowCount(0);

        String json = ApiClient.get(
                "/avaliacoes/aluno/" + idAluno + "/trimestre/1"
        );

        AvaliacaoResponseDTO[] avaliacoes =
                ApiClient.getGson().fromJson(json, AvaliacaoResponseDTO[].class);

        Map<Long, Double> somaPorMateria = new HashMap<>();

        for (AvaliacaoResponseDTO a : avaliacoes) {
            somaPorMateria.merge(
                    a.getMateriaId(),
                    a.getNota(),
                    Double::sum
            );
        }

        for (Map.Entry<Long, Double> entry : somaPorMateria.entrySet()) {
            modelo.addRow(new Object[]{
                entry.getKey(),
                "Matéria " + entry.getKey(),
                entry.getValue()
            });
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Erro ao carregar notas:\n" + ex.getMessage());
        ex.printStackTrace();
    }
}



    
}