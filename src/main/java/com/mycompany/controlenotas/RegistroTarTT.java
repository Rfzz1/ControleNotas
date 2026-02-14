package com.mycompany.controlenotas;

import com.mycompany.controlenotas.app.ControleNotas;
import com.mycompany.controlenotas.util.Sessao;
import com.mycompany.controlenotas.view.TerceiroTri;
import com.mycompany.controlenotas.view.RegistroTarBase;
import javax.swing.JOptionPane;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

//-------------------------------------------
//     REGISTRO DE TAREFAS 3 TRIMESTRE
//-------------------------------------------

public class RegistroTarTT extends RegistroTarBase {

    public RegistroTarTT() {
        super(Sessao.getAlunoId(), 3, "Registro de Tarefas - 3º TRI");
        
        if (Sessao.getAlunoId() == null) {
            JOptionPane.showMessageDialog(this, "Sessão Inválida");
            dispose();
            new ControleNotas().setVisible(true);
            return;
        }
    }

    @Override
    protected void voltar() {
        new TerceiroTri().setVisible(true);
        dispose();
    }
}
