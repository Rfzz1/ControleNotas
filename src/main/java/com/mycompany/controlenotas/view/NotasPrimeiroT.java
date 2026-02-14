package com.mycompany.controlenotas.view;

import com.mycompany.controlenotas.RegistroTarPT;
import com.mycompany.controlenotas.Tarefa;
import com.mycompany.controlenotas.db;
import com.mycompany.controlenotas.util.Sessao;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
    listaTarefas.clear();
    modelo.setRowCount(0);

    String sql = """
        SELECT m.id AS materia_id,
               m.materia,
               COALESCE(SUM(a.nota), 0) AS soma
        FROM materias m
        LEFT JOIN avaliacoes a 
               ON a.materia_id = m.id
              AND a.trimestre = 1
              AND a.aluno_id = ?
        GROUP BY m.id, m.materia
        ORDER BY m.materia
    """;

    try (Connection conn = db.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, idAluno);
        var rs = stmt.executeQuery();

        while (rs.next()) {
            int materiaId = rs.getInt("materia_id");
            String materia = rs.getString("materia");
            double soma = rs.getDouble("soma");

            modelo.addRow(new Object[]{
                materiaId,
                materia,
                soma
            });
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "Erro ao carregar notas:\n" + ex.getMessage());
        Logger.getLogger(NotasPrimeiroT.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    
}