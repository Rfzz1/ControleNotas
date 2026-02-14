package com.mycompany.controlenotas.service;

import com.google.gson.Gson;
import com.mycompany.controlenotas.exception.ApiException;
import com.mycompany.controlenotas.http.ApiClient;

import java.util.Map;

public class AlunoService {

    private static final Gson gson = new Gson();

    public static void cadastrar(String aluno, String senha) {
        try {

            ApiClient.post("/alunos/registrar", Map.of(
                    "aluno", aluno,
                    "senha", senha
            )
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Erro ao cadastrar aluno", e);
        }
    }
}
