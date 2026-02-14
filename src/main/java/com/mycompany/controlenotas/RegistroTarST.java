package com.mycompany.controlenotas;

import com.mycompany.controlenotas.app.ControleNotas;
import com.mycompany.controlenotas.util.Sessao;
import com.mycompany.controlenotas.view.SegundoTri;
import com.mycompany.controlenotas.view.RegistroTarBase;
import javax.swing.JOptionPane;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

//-------------------------------------------
//     REGISTRO DE TAREFAS 2 TRIMESTRE
//-------------------------------------------

public class RegistroTarST extends RegistroTarBase {

    public RegistroTarST() {
        super(Sessao.getAlunoId(), 2, "Registro de Tarefas - 2º TRI");
        
        if (Sessao.getAlunoId() == null) {
            JOptionPane.showMessageDialog(this, "Sessão Inválida");
            dispose();
            new ControleNotas().setVisible(true);
            return;
        }
    }

    @Override
    protected void voltar() {
        new SegundoTri().setVisible(true);
        dispose();
    }
}
