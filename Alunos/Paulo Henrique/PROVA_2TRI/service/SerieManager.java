package service;

import model.Serie;
import model.Usuario;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SerieManager {
    private Usuario usuario;

    public SerieManager(Usuario usuario) {
        this.usuario = usuario;
    }

    public void adicionarFavorito(Serie serie) {
        usuario.getFavoritos().add(serie);
    }

    public void adicionarJaAssistida(Serie serie) {
        usuario.getJaAssistidas().add(serie);
    }

    public void adicionarDesejoAssistir(Serie serie) {
        usuario.getDesejoAssistir().add(serie);
    }

    public void removerFavorito(Serie serie) {
        usuario.getFavoritos().remove(serie);
    }

    public void removerJaAssistida(Serie serie) {
        usuario.getJaAssistidas().remove(serie);
    }

    public void removerDesejoAssistir(Serie serie) {
        usuario.getDesejoAssistir().remove(serie);
    }

    public List<Serie> getFavoritos() {
        return usuario.getFavoritos();
    }

    public List<Serie> getJaAssistidas() {
        return usuario.getJaAssistidas();
    }

    public List<Serie> getDesejoAssistir() {
        return usuario.getDesejoAssistir();
    }

    public void ordenarPorNome(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getName));
    }

    public void ordenarPorNota(List<Serie> lista) {
        lista.sort(Comparator.comparingDouble(Serie::getRatingAverage).reversed());
    }

    public void ordenarPorEstado(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getStatus));
    }

    public void ordenarPorEstreia(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getPremiered));
    }
}