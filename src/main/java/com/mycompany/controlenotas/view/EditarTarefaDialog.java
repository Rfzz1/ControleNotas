package com.mycompany.controlenotas.view;

import com.google.gson.Gson;
import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.model.AvaliacaoDTO;
import com.mycompany.controlenotas.model.MateriaDTO;
import com.mycompany.controlenotas.model.TipoDTO;
import com.mycompany.controlenotas.util.Sessao;
import com.mycompany.controlenotas.view.RegistroTarefaDialog;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

//-------------------------------------------
//           POPUP EDITA TAREFA
//-------------------------------------------

public class EditarTarefaDialog extends JDialog {

    private final JTextField inputTitulo;
    private final JTextArea inputDescricao;
    private final JTextField inputData;
    private final JTextField inputValorMax;
    private final JTextField inputNota;
    private final JTextField inputTrimestre;

    private final JComboBox<MateriaDTO> selectMateria;
    private final JComboBox<TipoDTO> selectTipo;

    private AvaliacaoDTO tarefa;

    public EditarTarefaDialog(Window parent, AvaliacaoDTO tarefa) {
        super(parent, "Editar Tarefa", ModalityType.APPLICATION_MODAL);

        this.tarefa = tarefa;

        setSize(500, 480);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));

        // CAMPOS
        selectMateria = new JComboBox<>();
        carregarMaterias();

        selectTipo = new JComboBox<>();
        carregarTipos();
        
        inputTrimestre = new JTextField(String.valueOf(tarefa.getTrimestre()));
        inputTitulo = new JTextField(tarefa.getTitulo());
        inputDescricao = new JTextArea(tarefa.getDescricao(), 4, 20);
        inputData = new JTextField(tarefa.getData().toString());
        inputValorMax = new JTextField(String.valueOf(tarefa.getValorMax()));
        inputNota = new JTextField(String.valueOf(tarefa.getNota()));

        // selecionar matéria da tarefa
        selectMateria.setSelectedItem(tarefa.getMateria());

        // selecionar tipo da tarefa
        selectTipo.setSelectedItem(tarefa.getTipo());

        // ADICIONA NO FORM
        form.add(new JLabel("Matéria:"));
        form.add(selectMateria);

        form.add(new JLabel("Tipo:"));
        form.add(selectTipo);

        form.add(new JLabel("Título:"));
        form.add(inputTitulo);

        form.add(new JLabel("Descrição:"));
        form.add(new JScrollPane(inputDescricao));

        form.add(new JLabel("Valor máximo:"));
        form.add(inputValorMax);

        form.add(new JLabel("Nota:"));
        form.add(inputNota);

        form.add(new JLabel("Data:"));
        form.add(inputData);

        add(form, BorderLayout.CENTER);

        // BOTÃO SALVAR
        JButton salvar = new JButton("Salvar alterações");
        salvar.addActionListener(e -> salvarAlteracoes());

        add(salvar, BorderLayout.SOUTH);
    }

    // ---------------------------
    // SALVAR ALTERAÇÕES (UPDATE)
    // ---------------------------
private void salvarAlteracoes() {

    try {
        String titulo = inputTitulo.getText().trim();
        String descricao = inputDescricao.getText().trim();
        double valorMax = Double.parseDouble(inputValorMax.getText().trim());
        double nota = Double.parseDouble(inputNota.getText().trim());
        int trimestre = Integer.parseInt(inputTrimestre.getText().trim());
        LocalDate data = LocalDate.parse(inputData.getText().trim());
        MateriaDTO materiaSelecionada = (MateriaDTO)selectMateria.getSelectedItem();
        TipoDTO tipoSelecionado = (TipoDTO)selectTipo.getSelectedItem();
        
        Map<String, Object> body = new HashMap<>();
        
        body.put("alunoId", Sessao.getAlunoId());
        body.put("materiaId", materiaSelecionada.getId());
        body.put("tipoId", tipoSelecionado.getId());
        body.put("trimestre", trimestre);
        body.put("titulo", titulo);
        body.put("descricao", descricao);
        body.put("valorMax", valorMax);
        body.put("nota", nota);
        body.put("data", data.toString());

        ApiClient.put("/avaliacoes/" + tarefa.getId(), body);

        JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!");
        dispose();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Erro ao salvar alterações:\n" + ex.getMessage());
    }
}

    // -------------------------
    // CARREGAR MATERIAS
    // -------------------------
    private void carregarMaterias() {
        try {
            String json = ApiClient.get("/materias");
            MateriaDTO[] materias = new com.google.gson.Gson()
                    .fromJson(json, MateriaDTO[].class);

            for (MateriaDTO m : materias) {
                selectMateria.addItem(m);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar matérias:\n" + e.getMessage());
        }
    }


    // -------------------------
    // CARREGAR TIPOS
    // -------------------------
    private void carregarTipos() {
        try {
            String json = ApiClient.get("/tipos-avaliacao");
            TipoDTO[] tipos = new com.google.gson.Gson()
                    .fromJson(json, TipoDTO[].class);
               

            for (TipoDTO t : tipos) {
                selectTipo.addItem(t);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar tipos:\n" + e.getMessage());
        }
    }
}
