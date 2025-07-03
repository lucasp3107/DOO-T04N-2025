package com.arthur.tvshowtracker.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class GerenciadorSeries {
    private String nomeUsuario;
    private List<Serie> favoritas;
    private List<Serie> assistidas;
    private List<Serie> paraAssistir;

    public GerenciadorSeries() {
        this.favoritas = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.paraAssistir = new ArrayList<>();
        this.nomeUsuario = "Usuário Padrão";
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        if (nomeUsuario != null && !nomeUsuario.trim().isEmpty()) {
            this.nomeUsuario = nomeUsuario.trim();
            System.out.println("Nome de usuário atualizado para: " + this.nomeUsuario);
        } else {
            System.out.println("Erro: O nome de usuário não pode ser vazio.");
        }
    }

    public boolean adicionarSerieAssistida(Serie serie) {
        return adicionarALista(assistidas, serie, "assistidas");
    }

    public boolean adicionarSerieFavorita(Serie serie) {
        return adicionarALista(favoritas, serie, "favoritas");
    }

    public boolean adicionarSerieParaAssistir(Serie serie) {
        return adicionarALista(paraAssistir, serie, "para assistir");
    }

    private boolean adicionarALista(List<Serie> lista, Serie serie, String nomeLista) {
        if (serie == null) {
            System.out.println("Erro: Não é possível adicionar uma série nula.");
            return false;
        }
        if (!lista.contains(serie)) {
            lista.add(serie);
            System.out.println("Série '" + serie.getNome() + "' adicionada à lista " + nomeLista + ".");
            return true;
        } else {
            System.out.println("Série '" + serie.getNome() + "' já está na lista " + nomeLista + ".");
            return false;
        }
    }

    public boolean removerSerieAssistida(Serie serie) {
        return removerDaLista(assistidas, serie, "assistidas");
    }

    public boolean removerSerieFavorita(Serie serie) {
        return removerDaLista(favoritas, serie, "favoritas");
    }

    public boolean removerSerieParaAssistir(Serie serie) {
        return removerDaLista(paraAssistir, serie, "para assistir");
    }

    private boolean removerDaLista(List<Serie> lista, Serie serie, String nomeLista) {
        if (serie == null) {
            System.out.println("Erro: Não é possível remover uma série nula.");
            return false;
        }
        if (lista.remove(serie)) {
            System.out.println("Série '" + serie.getNome() + "' removida da lista " + nomeLista + ".");
            return true;
        } else {
            System.out.println("Série '" + serie.getNome() + "' não encontrada na lista " + nomeLista + ".");
            return false;
        }
    }

    public void exibirListaAssistidas(boolean exibirDetalhes) {
        exibirLista("\n--- Minhas Séries Assistidas ---", assistidas, exibirDetalhes);
    }

    public void exibirListaFavoritas(boolean exibirDetalhes) {
        exibirLista("\n--- Minhas Séries Favoritas ---", favoritas, exibirDetalhes);
    }

    public void exibirListaParaAssistir(boolean exibirDetalhes) {
        exibirLista("\n--- Minhas Séries Para Assistir ---", paraAssistir, exibirDetalhes);
    }

    private void exibirLista(String titulo, List<Serie> lista, boolean exibirDetalhes) {
        System.out.println(titulo);
        if (lista.isEmpty()) {
            System.out.println("Nenhuma série encontrada nesta lista.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + (exibirDetalhes ? lista.get(i).toString() : lista.get(i).toStringCurto()));
        }
    }

    public void ordenarListaPorNome(List<Serie> lista) {
        ordenarLista(lista, Comparator.comparing(Serie::getNome, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)), "alfabeticamente por nome");
    }

    public void ordenarListaPorAvaliacaoGeral(List<Serie> lista) {
        ordenarLista(lista, Comparator.comparing(Serie::getAvaliacaoGeral, Comparator.nullsLast(Comparator.reverseOrder())), "por avaliação geral (maior para menor)");
    }

    public void ordenarListaPorStatus(List<Serie> lista) {
        ordenarLista(lista, Comparator.comparing(this::obterOrdemStatus), "por status (Em Exibição, Finalizada, etc.)");
    }

    public void ordenarListaPorDataEstreia(List<Serie> lista) {
        ordenarLista(lista, Comparator.comparing(Serie::getDataEstreia, Comparator.nullsLast(Comparator.naturalOrder())), "por data de estreia (mais antiga para mais recente)");
    }

    private void ordenarLista(List<Serie> lista, Comparator<Serie> comparador, String criterio) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("Lista vazia ou nula para ordenação.");
            return;
        }
        lista.sort(comparador);
        System.out.println("Lista ordenada " + criterio + ".");
    }

    private int obterOrdemStatus(Serie serie) {
        String status = serie.getStatus();
        if (status == null) return 99;
        return switch (status) {
            case "Running" -> 1;
            case "Ended" -> 2;
            case "To Be Determined" -> 3;
            case "In Development" -> 4;
            case "Canceled" -> 5;
            default -> 10;
        };
    }

    public List<Serie> getFavoritas() {
        return favoritas;
    }
    public List<Serie> getAssistidas() {
        return assistidas;
    }
    public List<Serie> getParaAssistir() {
        return paraAssistir;
    }

    public void setFavoritas(List<Serie> favoritas) {
        this.favoritas = favoritas != null ? favoritas : new ArrayList<>();
    }
    public void setAssistidas(List<Serie> assistidas) {
        this.assistidas = assistidas != null ? assistidas : new ArrayList<>();
    }
    public void setParaAssistir(List<Serie> paraAssistir) {
        this.paraAssistir = paraAssistir != null ? paraAssistir : new ArrayList<>();
    }
}