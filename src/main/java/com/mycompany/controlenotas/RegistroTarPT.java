package com.mycompany.controlenotas;

import com.mycompany.controlenotas.app.ControleNotas;
import com.mycompany.controlenotas.util.Sessao;
import com.mycompany.controlenotas.view.PrimeiroTri;
import com.mycompany.controlenotas.view.RegistroTarBase;
import javax.swing.JOptionPane;

//-------------------------------------------
//     REGISTRO DE TAREFAS 1 TRIMESTRE
//-------------------------------------------

public class RegistroTarPT extends RegistroTarBase {
    public RegistroTarPT() {
        super(Sessao.getAlunoId(), 1, "Registro de Tarefas - 1º TRI");
        
        if (Sessao.getAlunoId() == null) {
            JOptionPane.showMessageDialog(this, "Sessão Inválida");
            dispose();
            new ControleNotas().setVisible(true);
            return;
        }
    }
    
    @Override
    protected void voltar(){
        new PrimeiroTri().setVisible(true);
        dispose();
    }

}