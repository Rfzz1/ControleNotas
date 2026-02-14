
package com.mycompany.controlenotas.util;

public class Sessao {

    private static Long alunoId;
    private static String aluno;

    public static void login(Long id, String nome) {
        alunoId = id;
        aluno = nome;
    }

    public static Long getAlunoId() {
        return alunoId;
    }

    public static String getAluno() {
        return aluno;
    }

    public static void logout() {
        alunoId = null;
        aluno = null;
    }
}