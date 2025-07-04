package com.arthur.tvshowtracker.app;

import java.util.Scanner;

public class PerfilUsuario {
    private String nomeCompleto;
    private String apelido;

    public PerfilUsuario(String nomeCompleto, String apelido) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            this.nomeCompleto = "Usuário Padrão";
            System.out.println("Atenção: Nome de usuário inválido. Definido como 'Usuário Padrão'.");
        } else {
            this.nomeCompleto = nomeCompleto.trim();
        }

        if (apelido == null || apelido.trim().isEmpty()) {
            this.apelido = this.nomeCompleto;
            System.out.println("Apelido não fornecido. Usando nome completo como apelido.");
        } else {
            this.apelido = apelido.trim();
        }
    }

    public PerfilUsuario(String nomeCompleto) {
        this(nomeCompleto, nomeCompleto);
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getApelido() {
        return apelido;
    }

    public void setNomeCompleto(String nomeCompleto) {
        if (nomeCompleto != null && !nomeCompleto.trim().isEmpty()) {
            this.nomeCompleto = nomeCompleto.trim();
        } else {
            System.out.println("Erro: O nome completo não pode ser vazio.");
        }
    }

    public void setApelido(String apelido) {
        if (apelido != null && !apelido.trim().isEmpty()) {
            this.apelido = apelido.trim();
        } else {
            System.out.println("Erro: O apelido não pode ser vazio. Apelido atual mantido.");
        }
    }

    public static PerfilUsuario registrarUsuario(Scanner scan) {
        System.out.println("Bem-vindo ao sistema de acompanhamento de séries!");
        System.out.println("Qual é o seu nome completo?");
        String nomeCompleto = scan.nextLine();

        System.out.println("Como você gostaria de ser chamado (apelido)? (Deixe em branco para usar seu nome)");
        String entradaApelido = scan.nextLine();

        String apelidoFinal = entradaApelido.trim().isEmpty() ? nomeCompleto : entradaApelido;

        System.out.println("Cadastro realizado com sucesso!");
        return new PerfilUsuario(nomeCompleto, apelidoFinal);
    }

    @Override
    public String toString() {
        return "Nome: " + nomeCompleto + " | Apelido: " + apelido;
    }
}