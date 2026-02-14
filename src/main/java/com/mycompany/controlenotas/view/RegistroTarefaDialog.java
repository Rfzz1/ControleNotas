package com.mycompany.controlenotas.view;

import com.mycompany.controlenotas.RegistroTarTT;
import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.model.AvaliacaoCreateDTO;
import com.mycompany.controlenotas.model.MateriaDTO;
import com.mycompany.controlenotas.model.TipoAvaliacaoDTO;
import com.mycompany.controlenotas.util.Sessao;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level; //Log de erro
import java.util.logging.Logger;
import javax.swing.*;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

//-------------------------------------------
//          POPUP REGISTRO TAREFA
//-------------------------------------------

//Janela POP-UP que herda tudo do JDialog
public class RegistroTarefaDialog extends JDialog{

    //---------------------
    //      VARIÁVEIS
    //---------------------   
    
    private JComboBox<MateriaDTO> selectMateria; // Menu Suspenso com as matérias
    private JComboBox<TipoAvaliacaoDTO> selectTipo;    // Prova/Trabalho/Simulado/Seminário/Atividade Extra
    private JTextField inputTitulo;          // Título da Avaliação
    private JTextArea inputDescricao;        // Detalhamento/Descrição
    private JTextField inputValorMax;        // Nota máxima na prova
    private JTextField inputNota;            // Nota da prova
    private JTextField inputData;            // Data da Avaliação

    private final Long idAluno;      // O aluno que está sendo registrado
    private final int trimestre;    // Trimestre da tela atual
    
    
    //--------------------------------------------------------
    //        FUNÇÃO QUE FAZ A CONSTRUÇÃO DO POP UP
    //--------------------------------------------------------
    
    public RegistroTarefaDialog(JFrame parent, int trimestre) {
        //Parent - Janela Principal (Quem chamou a função)
        //APPLICATION_MODAL - Bloqueia a tela principal até salvar/fechar
        super(parent, "Registrar Avaliação", ModalityType.APPLICATION_MODAL);
        
        
        // Salva os dados
        this.idAluno = Sessao.getAlunoId();
        this.trimestre = trimestre;
        
        if (idAluno == null) {
            JOptionPane.showMessageDialog(this, "Sessão inválida");
            dispose();
            return;
        }
        
        //---------------------------------------------
        //        CONFIGURAÇÃO BÁSICA DO POPUP
        //---------------------------------------------
        
        setSize(500, 450); // Tamanho
        setLocationRelativeTo(parent); // Abre centralizado
        setLayout(new BorderLayout());
        
        //-------------------------------------
        //        CRIAÇÃO DE FORMULÁRIO
        //-------------------------------------
        
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 Colunas
        
        //---------------------
        //       CAMPOS
        //---------------------
        
        // Matérias
        selectMateria = new JComboBox<>();
        carregarMaterias();
        // Tipo de avaliação
        
        selectTipo = new JComboBox<>();
        carregarTipos();

       // Outros campos
       
       inputTitulo = new JTextField(); //Titulo
       inputDescricao = new JTextArea(4, 20); // Descrição
       inputValorMax = new JTextField(); //Valor máximo
       inputNota = new JTextField(); //Nota
       DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       inputData = new JTextField(LocalDate.now().format(fmt)); //Data
       
        //------------------------------------
        //       Adicionando ao painel
        //------------------------------------
        
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

        form.add(new JLabel("Nota obtida:"));
        form.add(inputNota);

        form.add(new JLabel("Data:"));
        form.add(inputData);

        add(form, BorderLayout.CENTER);
        
        //-------------------------------
        //      Botão SALVAR + AÇÃO
        //-------------------------------
        
        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(e -> salvarAvaliacao());
        
        add(salvar, BorderLayout.SOUTH);

    }
    
    //-------------------
    //       FUNÇÕES
    //-------------------

    //----------------------------
    //       SALVAR NO BANCO
    //----------------------------
    
private void salvarAvaliacao() {

    MateriaDTO materia = (MateriaDTO) selectMateria.getSelectedItem();
    TipoAvaliacaoDTO tipo = (TipoAvaliacaoDTO) selectTipo.getSelectedItem();

    AvaliacaoCreateDTO dto = new AvaliacaoCreateDTO(
        idAluno,
        trimestre,
        materia.getId(),
        tipo.getId(),
        inputTitulo.getText().trim(),
        inputDescricao.getText().trim(),
        Double.parseDouble(inputValorMax.getText()),
        Double.parseDouble(inputNota.getText()),
        LocalDate.parse(inputData.getText())
    );

    try {
        ApiClient.post("/avaliacoes", dto);

        JOptionPane.showMessageDialog(this, "Avaliação registrada com sucesso!");
        dispose();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Erro ao salvar avaliação:\n" + e.getMessage()
        );
    }
}

//---------------------
//       MATÉRIAS
//---------------------
    
private void carregarMaterias() {

    try {
        String json = ApiClient.get("/materias");
        MateriaDTO[] materias = ApiClient.getGson().fromJson(json, MateriaDTO[].class);
        
        selectMateria.removeAllItems();
            for (MateriaDTO m : materias) {
                selectMateria.addItem(m);
            }
               
    } catch (Exception e) {
        JOptionPane.showMessageDialog(
        this,
        "Erro ao carregar matérias:\n" + e.getMessage()
        );
    }
}

//-----------------------------
//        TIPO AVALIAÇÃO
//-----------------------------

private void carregarTipos() {
    try {
        String json = ApiClient.get("/tipos-avaliacao");
        
        TipoAvaliacaoDTO[] tipos = ApiClient.getGson().fromJson(json, TipoAvaliacaoDTO[].class);
        
        selectTipo.removeAllItems();
            for (TipoAvaliacaoDTO t : tipos) {
                selectTipo.addItem(t);
            }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(
        this,
        "Erro ao carregar tipos:\n" + e.getMessage()
        );
    }   
}
}