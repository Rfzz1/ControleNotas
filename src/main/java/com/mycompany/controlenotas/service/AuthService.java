package com.mycompany.controlenotas.service;

import com.google.gson.Gson;
import com.mycompany.controlenotas.exception.ApiException;
import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.model.AlunoDTO;

import java.util.Map;

public class AuthService {

    private static final Gson gson = new Gson();

    public static AlunoDTO login(String aluno, String senha) {
        try {
            String response = ApiClient.post(
                "/alunos/login",
                Map.of(
                    "aluno", aluno,
                    "senha", senha
                )
            );

            return gson.fromJson(response, AlunoDTO.class);

        } catch (Exception e) {
            throw new ApiException("Aluno ou senha inválidos", e);
        }
    }
    
    public static void cadastrar(String nome, String senha) {
        try {
            ApiClient.post("/alunos/registrar", Map.of(
            "aluno", nome,
            "senha", senha
            ));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Erro ao cadastrar aluno: ", e);
        }
    }
}
