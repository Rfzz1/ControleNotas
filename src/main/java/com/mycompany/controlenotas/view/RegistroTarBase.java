package com.mycompany.controlenotas.view;

import com.mycompany.controlenotas.Tarefa;
import com.mycompany.controlenotas.db;
import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.model.AvaliacaoDTO;
import com.mycompany.controlenotas.util.Sessao;
import com.mycompany.controlenotas.view.RegistroTarefaDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public abstract class RegistroTarBase extends JFrame {

    protected final Long idAluno;
    protected final int trimestre;

    protected JTable tabela;
    protected DefaultTableModel modelo;
    protected java.util.List<AvaliacaoDTO> listaTarefas = new ArrayList<>();

    protected RegistroTarBase(Long idAluno, int trimestre, String tituloJanela) {
        super(tituloJanela);
        this.idAluno = Sessao.getAlunoId();
        this.trimestre = trimestre;

        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        criarTopo();
        criarTabela();
        listarTarefas();

        setVisible(true);
    }

    // ---------- TOPO ----------
    private void criarTopo() {
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton adicionar = new JButton("+");
        JButton editar = new JButton("Editar");
        JButton voltar = new JButton("Voltar");

        topo.add(adicionar);
        topo.add(editar);

        adicionar.addActionListener(e -> {
            new RegistroTarefaDialog(this, trimestre).setVisible(true);
            listarTarefas();
        });

        editar.addActionListener(e -> editarTarefa());
        voltar.addActionListener(e -> voltar());

        add(topo, BorderLayout.NORTH);
    }

    // ---------- TABELA ----------
    private void criarTabela() {
        modelo = new DefaultTableModel(
            new String[]{"ID","Matéria","Tipo","Título","Descrição","Valor Máximo","Nota","Data"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(25);

        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    // ---------- LISTAR ----------
    protected void listarTarefas() {

        listaTarefas.clear();
        modelo.setRowCount(0);

        try {

            String json = ApiClient.get("/avaliacoes/aluno/" 
                            + idAluno + "?trimestre" + trimestre);

            AvaliacaoDTO[] avaliacoes =
                    ApiClient.getGson().fromJson(json, AvaliacaoDTO[].class);

            for (AvaliacaoDTO a : avaliacoes) {

                listaTarefas.add(a);

                modelo.addRow(new Object[]{
                    a.getId(),
                    a.getMateria(),
                    a.getTipo(),
                    a.getTitulo(),
                    a.getDescricao(),
                    a.getValorMax(),
                    a.getNota(),
                    a.getData()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar tarefas:\n" + e.getMessage());
        }
    }

    // ---------- EDITAR ----------
    protected void editarTarefa() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa!");
            return;
        }

        Long id = (Long) tabela.getValueAt(row, 0);

        AvaliacaoDTO tarefa = listaTarefas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (tarefa == null) return;

        new EditarTarefaDialog(this, tarefa).setVisible(true);
        listarTarefas();
    }

    // ---------- VOLTAR (cada TRI define) ----------
    protected abstract void voltar();
}
