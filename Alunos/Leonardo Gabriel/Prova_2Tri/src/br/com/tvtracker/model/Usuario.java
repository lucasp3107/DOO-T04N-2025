package br.com.tvtracker.model;

import java.util.HashSet;
import java.util.Set;

public class Usuario {

    private String nome;
    private Set<Integer> seriesFavoritasIds = new HashSet<>();
    private Set<Integer> seriesAssistidasIds = new HashSet<>();
    private Set<Integer> seriesDesejadasIds = new HashSet<>();

    public Usuario() {
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Set<Integer> getSeriesFavoritasIds() {
        return seriesFavoritasIds;
    }

    public Set<Integer> getSeriesAssistidasIds() {
        return seriesAssistidasIds;
    }

    public Set<Integer> getSeriesDesejadasIds() {
        return seriesDesejadasIds;
    }

    public void marcarFavorita(int serieId) {
        seriesFavoritasIds.add(serieId);
    }

    public void marcarAssistida(int serieId) {
        seriesAssistidasIds.add(serieId);
    }

    public void marcarDesejada(int serieId) {
        seriesDesejadasIds.add(serieId);
    }

    public void removerFavorita(int serieId) {
        seriesFavoritasIds.remove(serieId);
    }

    public void removerAssistida(int serieId) {
        seriesAssistidasIds.remove(serieId);
    }

    public void removerDesejada(int serieId) {
        seriesDesejadasIds.remove(serieId);
    }
}
