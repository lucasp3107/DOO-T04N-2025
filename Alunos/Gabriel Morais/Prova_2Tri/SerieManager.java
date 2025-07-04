import java.util.*;

public class SerieManager {
    private Usuario usuario;

    public SerieManager(Usuario usuario) {
        this.usuario = usuario;
    }

    public void adicionar(Serie s, List<Serie> lista) {
        if (lista.stream().noneMatch(x -> x.id == s.id)) {
            lista.add(s);
            System.out.println("Serie adicionada!");
        } else {
            System.out.println("Serie ja esta na lista.");
        }
    }

    public void removerPorNome(String nome, List<Serie> lista) {
        if (lista.removeIf(x -> x.nome.equalsIgnoreCase(nome))) {
            System.out.println("Removida com sucesso.");
        } else {
            System.out.println("Nao encontrada.");
        }
    }

    public void mostrarLista(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }
        for (Serie s : lista) {
            System.out.println(s);
        }
    }

    public void ordenarPorNome(List<Serie> lista) {
        lista.sort(Comparator.comparing(s -> s.nome.toLowerCase()));
    }

    public void ordenarPorNota(List<Serie> lista) {
        lista.sort(Comparator.comparingDouble((Serie s) -> -s.nota));
    }

    public void ordenarPorStatus(List<Serie> lista) {
        lista.sort(Comparator.comparing(s -> s.status));
    }

    public void ordenarPorEstreia(List<Serie> lista) {
        lista.sort(Comparator.comparing(s -> s.dataEstreia));
    }
}