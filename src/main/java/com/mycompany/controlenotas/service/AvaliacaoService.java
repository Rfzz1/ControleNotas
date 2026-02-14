package com.mycompany.controlenotas.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.model.AvaliacaoDTO;
import com.mycompany.controlenotas.util.Sessao;
import java.util.List;

public class AvaliacaoService extends ApiClient {

    private static final Gson gson = new Gson();

    public static List<AvaliacaoDTO> listarDoAluno(int trimestre) throws Exception {
        Long alunoId = Sessao.getAlunoId();
        String json = get("/avaliacoes/aluno/" + alunoId + "?trimestre=" + trimestre);
        return gson.fromJson(json, new TypeToken<List<AvaliacaoDTO>>(){}.getType());
    }
    
    public void deletar(Long id) throws Exception {
        delete("/avaliacoes/" + id);
    }
}
