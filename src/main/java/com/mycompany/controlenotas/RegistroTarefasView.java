package com.mycompany.controlenotas;

import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.model.AvaliacaoResponseDTO;
import com.mycompany.controlenotas.service.AvaliacaoService;
import com.mycompany.controlenotas.util.Sessao;
import com.mycompany.controlenotas.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroTarefasView extends JFrame {

    private final Long idAluno;
    private final int trimestre;

    private JTable tabela;
    private DefaultTableModel modelo;
    private final List<AvaliacaoResponseDTO> listaTarefas = new ArrayList<>();

    public RegistroTarefasView(int trimestre) {
        super("Registro de Tarefas - " + trimestre + "º Trimestre");
        this.trimestre = trimestre;
        this.idAluno = Sessao.getAlunoId();

        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        criarTopo();
        criarTabela();
        listarTarefas();
    }

    private void criarTopo() {
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton adicionar = new JButton("+");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");
        JButton voltar = new JButton("Voltar");

        topo.add(adicionar);
        topo.add(editar);
        topo.add(excluir);
        topo.add(voltar);

        add(topo, BorderLayout.NORTH);

        adicionar.addActionListener(e -> {
            new RegistroTarefaDialog(this, trimestre).setVisible(true);
            listarTarefas();
        });

        editar.addActionListener(e -> editarTarefa());
        excluir.addActionListener(e -> excluirTarefa());

        voltar.addActionListener(e -> {
            switch (trimestre) {
                case 1 -> new PrimeiroTri().setVisible(true);
                case 2 -> new SegundoTri().setVisible(true);
                case 3 -> new TerceiroTri().setVisible(true);
            }
            dispose();
        });
    }

    private void criarTabela() {
        modelo = new DefaultTableModel(
                new String[]{
                        "ID", "Matéria", "Tipo", "Título",
                        "Descrição", "Valor Máx.", "Nota", "Data"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private void listarTarefas() {
        listaTarefas.clear();
        modelo.setRowCount(0);

        try {
            String json = ApiClient.get(
                    "/avaliacoes/aluno/" + idAluno + "?trimestre=" + trimestre
            );

            AvaliacaoResponseDTO[] tarefas =
                    ApiClient.getGson().fromJson(json, AvaliacaoResponseDTO[].class);

            for (AvaliacaoResponseDTO dto : tarefas) {
                listaTarefas.add(dto);

                modelo.addRow(new Object[]{
                        dto.getId(),
                        dto.getMateriaNome(),
                        dto.getTipoNome(),
                        dto.getTitulo(),
                        dto.getDescricao(),
                        dto.getValorMax(),
                        dto.getNota(),
                        dto.getData()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar tarefas:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void editarTarefa() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa!");
            return;
        }

        Long id = (Long) tabela.getValueAt(row, 0);

        listaTarefas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .ifPresent(t -> {
                    new EditarTarefaDialog(this, t).setVisible(true);
                    listarTarefas();
                });
    }

    private void excluirTarefa() {
        int row = tabela.getSelectedRow();
        if (row == -1) return;

        Long id = (Long) tabela.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja excluir a tarefa?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            AvaliacaoService service = new AvaliacaoService();
            service.deletar((long) id);
            listarTarefas();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao excluir tarefa:\n" + e.getMessage()
            );
        }
    }
}
