package com.mycompany.controlenotas;

import com.mycompany.controlenotas.app.ControleNotas;
import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.model.NotaFinalDTO;
import com.mycompany.controlenotas.util.Sessao;
import com.mycompany.controlenotas.view.SegundoTri;
import com.mycompany.controlenotas.view.Trimestres;
import com.mycompany.controlenotas.view.PrimeiroTri;
import com.mycompany.controlenotas.view.TerceiroTri;
import com.mycompany.controlenotas.view.NotasPrimeiroT;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NotaFinal extends JFrame{
    
    private final Long idAluno;
    private JTable tabela;
    private DefaultTableModel modelo;
    private java.util.List<Tarefa> listaTarefas = new ArrayList<>();
    
    public NotaFinal() {
        super("Boletim Final");
        this.idAluno = Sessao.getAlunoId();
        
        if (idAluno == null) {
            JOptionPane.showMessageDialog(this, "Sessão Inválida");
            dispose();
            new ControleNotas().setVisible(true);
            return;
        }
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //--------------------------------------
        //           TOPO (Botões)
        //--------------------------------------
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton PT = new JButton("1º Trimestre");
        topo.add(PT);
        
        JButton ST = new JButton("2º Trimestre");
        topo.add(ST);
        
        JButton TT = new JButton("3º Trimestre");
        topo.add(TT);

        JButton voltar = new JButton("Voltar");
        topo.add(voltar);

        add(topo, BorderLayout.NORTH);
        
        //--------------------------------
        //          AÇÃO VOLTAR
        //--------------------------------
        voltar.addActionListener(e -> {
            new Trimestres().setVisible(true);
            dispose();
        });
        
        //--------------------------------
        //    AÇÃO Primeiro Trimestre
        //--------------------------------
        
        PT.addActionListener(e -> {
            new PrimeiroTri().setVisible(true);
            dispose();
        });
        
        //--------------------------------
        //     AÇÃO Segundo Trimestre
        //--------------------------------
        
        ST.addActionListener(e -> {
            new SegundoTri().setVisible(true);
            dispose();
        });

        //--------------------------------
        //    AÇÃO Terceiro Trimestre
        //--------------------------------
        
        TT.addActionListener(e -> {
            new TerceiroTri().setVisible(true);
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
        
        
        //----------------------------------------------------
        //  ADICIONA O PAINEL NA JANELA E TORNA ELA VISÍVEL
        //----------------------------------------------------
        setVisible(true);
}
    
    
    //---------------------
    //       FUNÇÕES
    //---------------------
    
    // -------------------------------------------
    // SomarNotas
    // -------------------------------------------
    
        private void SomarNotas() {
            modelo.setRowCount(0);
            
            try {
                String json = ApiClient.get("/avaliacoes/boletim-final/" + idAluno);
                
                NotaFinalDTO[] notas =
                    ApiClient.getGson().fromJson(json, NotaFinalDTO[].class);
                
                for (NotaFinalDTO n : notas) {
                    modelo.addRow(new Object[] {
                        n.getMateriaId(),
                        n.getMateria(),
                        n.getSoma()
                            
                    
                   });
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar notas finais:\n" + e.getMessage()
                );
            }
    
}
}
