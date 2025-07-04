package util;

import model.Serie;
import model.Usuario;
import service.ApiService;
import service.PersistenciaService;
import service.SerieManager;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private ApiService apiService = new ApiService();
    private PersistenciaService persistenciaService = new PersistenciaService();

    private Usuario usuario;
    private SerieManager serieManager;

    public void iniciar() {
        usuario = persistenciaService.carregarUsuario();
        if (usuario == null) {
            System.out.print("Digite seu nome ou apelido: ");
            String nome = sc.nextLine();
            usuario = new Usuario(nome);
        }
        serieManager = new SerieManager(usuario);

        int opcao;
        do {
            System.out.println("\nOlá, " + usuario.getNome() + "!");
            System.out.println("1. Buscar série");
            System.out.println("2. Ver favoritos");
            System.out.println("3. Ver já assistidas");
            System.out.println("4. Ver desejo assistir");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1 -> buscarSerie();
                case 2 -> listarSeries(serieManager.getFavoritos(), "Favoritos");
                case 3 -> listarSeries(serieManager.getJaAssistidas(), "Já Assistidas");
                case 4 -> listarSeries(serieManager.getDesejoAssistir(), "Desejo Assistir");
            }
        } while (opcao != 0);

        persistenciaService.salvarUsuario(usuario);
    }

    private void buscarSerie() {
        System.out.print("Nome da série: ");
        String nome = sc.nextLine();
        try {
            List<Serie> resultados = apiService.buscarSeriePorNome(nome);
            if (resultados.isEmpty()) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println("[" + i + "] " + resultados.get(i).getName());
            }
            System.out.print("Escolha a série pelo número: ");
            int escolha = Integer.parseInt(sc.nextLine());
            Serie s = resultados.get(escolha);
            System.out.println(s);
            menuAdicao(s);
        } catch (Exception e) {
            System.out.println("Erro ao buscar série: " + e.getMessage());
        }
    }

    private void menuAdicao(Serie s) {
        System.out.println("Adicionar à:");
        System.out.println("1. Favoritos");
        System.out.println("2. Já assistidas");
        System.out.println("3. Desejo assistir");
        System.out.println("Outro. Cancelar");
        int opc = Integer.parseInt(sc.nextLine());
        switch (opc) {
            case 1 -> serieManager.adicionarFavorito(s);
            case 2 -> serieManager.adicionarJaAssistida(s);
            case 3 -> serieManager.adicionarDesejoAssistir(s);
        }
    }

    private void listarSeries(List<Serie> lista, String titulo) {
        System.out.println("--- " + titulo + " ---");
        if (lista.isEmpty()) {
            System.out.println("Nenhuma série.");
            return;
        }
        for (Serie s : lista) {
            System.out.println(s);
        }
    }
}