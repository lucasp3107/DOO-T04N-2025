package modelo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import modelo.Serie;
import modelo.TipoLista;
import modelo.Usuario;
import servico.SerieServico;
import servico.PersistenciaServico;
import java.util.Scanner;

public class Usuario {
    private String nome;
    private Map<TipoLista, List<Serie>> listas;

    public Usuario(String nome) {
        this.nome = nome;
        listas = new HashMap<>();
        for (TipoLista tipo : TipoLista.values()) {
            listas.put(tipo, new ArrayList<>());
        }
    }

    public List<Serie> getLista(TipoLista tipo) {
        return listas.get(tipo);
    }

    public String getNome() {
        return nome;
    }
}
