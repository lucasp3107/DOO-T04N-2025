package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;
import model.Serie;
import model.Usuario;
import service.PersistenciaService;
import service.TvMazeService;

public class CatalogoSeriesApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TvMazeService tvMazeService = new TvMazeService();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Usuario usuario;

    public static void main(String[] args) {
        carregarUsuario();
        menuPrincipal();
    }

    private static void carregarUsuario() {
        usuario = PersistenciaService.carregarUsuario();
        if (usuario == null) {
            System.out.println("--- Bem-vindo(a) ao Catálogo de Séries ---");
            System.out.print("Digite seu nome ou apelido: ");
            String nome = scanner.nextLine();
            usuario = new Usuario(nome);
        } else {
            System.out.println("Bem-vindo(a) de volta, " + usuario.getNome() + "!");
        }
    }

    private static void menuPrincipal() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Procurar nova série");
            System.out.println("2. Ver minhas listas");
            System.out.println("3. Ordenar listas");
            System.out.println("4. Salvar e Sair");
            System.out.println("5. Apagar dados e sair");

            int escolha = lerOpcaoNumerica("Escolha uma opção: ", 1, 5); 

            switch (escolha) {
                case 1 -> procurarSerie();
                case 2 -> gerenciarListas();
                case 3 -> ordenarListas();
                case 4 -> {
                    PersistenciaService.salvarUsuario(usuario);
                    continuar = false;
                }
                case 5 -> { // NOVO CASE
                    if (apagarDadosDoUsuario()) {
                        continuar = false;
                    }
                }
            }
        }
    }

   
    private static boolean apagarDadosDoUsuario() {
        System.out.println("\nATENÇÃO: Esta ação é irreversível e apagará seu perfil e todas as listas.");
        System.out.print("Tem certeza que deseja continuar? (Digite 'S' para confirmar): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            try {
                Files.deleteIfExists(Paths.get("usuario_series.json"));
                System.out.println("Dados apagados com sucesso. O programa será encerrado.");
                return true;
            } catch (IOException e) {
                System.err.println("ERRO: Falha ao apagar o arquivo de dados. Motivo: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Operação cancelada. Seus dados estão seguros.");
            return false;
        }
    }

    private static void procurarSerie() {
        System.out.print("\nDigite o nome da série para procurar: ");
        String nomeSerie = scanner.nextLine();
        try {
            List<Serie> resultados = tvMazeService.buscarSeries(nomeSerie);
            if (resultados.isEmpty()) {
                System.out.println("Nenhuma série encontrada com este nome.");
                return;
            }

            for (int i = 0; i < resultados.size(); i++) {
                System.out.println("\n[" + i + "] " + resultados.get(i).getNome());
            }

            int escolha = lerOpcaoNumerica("\nEscolha um número para ver os detalhes (ou -1 para voltar): ", -1, resultados.size() - 1);
            if (escolha != -1) {
                Serie serieEscolhida = resultados.get(escolha);
                exibirDetalhesSerie(serieEscolhida);
                adicionarSerieEmLista(serieEscolhida);
            }
        } catch (RuntimeException e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
    
    private static void adicionarSerieEmLista(Serie serie) {
        System.out.println("\nAdicionar '" + serie.getNome() + "' em qual lista?");
        System.out.println("1. Favoritas\n2. Já assistidas\n3. Desejo assistir\n0. Nenhuma");
        int escolha = lerOpcaoNumerica("Opção: ", 0, 3);
        switch (escolha) {
            case 1 -> usuario.adicionarFavorita(serie);
            case 2 -> usuario.adicionarAssistida(serie);
            case 3 -> usuario.adicionarDesejoAssistir(serie);
        }
        if (escolha != 0) System.out.println("Série adicionada com sucesso!");
    }

    private static void gerenciarListas() {
        System.out.println("\n--- MINHAS LISTAS ---");
        System.out.println("1. Favoritas\n2. Já assistidas\n3. Desejo assistir");
        int escolha = lerOpcaoNumerica("Qual lista deseja ver? ", 1, 3);

        List<Serie> lista;
        String nomeLista;
        if (escolha == 1) { lista = usuario.getFavoritas(); nomeLista = "Favoritas"; }
        else if (escolha == 2) { lista = usuario.getAssistidas(); nomeLista = "Já Assistidas"; }
        else { lista = usuario.getDesejoAssistir(); nomeLista = "Desejo Assistir"; }
        
        System.out.println("\n--- " + nomeLista.toUpperCase() + " ---");
        if (lista.isEmpty()) {
            System.out.println("(Lista vazia)");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println("[" + i + "] " + lista.get(i).getNome());
        }

        int idx = lerOpcaoNumerica("\nEscolha um número para ver detalhes/remover (ou -1 para voltar): ", -1, lista.size() - 1);
        if (idx != -1) {
            Serie serieEscolhida = new ArrayList<>(lista).get(idx);
            exibirDetalhesSerie(serieEscolhida);
            System.out.print("\nDeseja remover '" + serieEscolhida.getNome() + "' desta lista? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                if (escolha == 1) usuario.removerFavorita(serieEscolhida);
                else if (escolha == 2) usuario.removerAssistida(serieEscolhida);
                else usuario.removerDesejoAssistir(serieEscolhida);
                System.out.println("Série removida.");
            }
        }
    }
    
    private static void ordenarListas() {
        System.out.println("\nQual lista deseja ordenar?");
        System.out.println("1. Favoritas\n2. Já assistidas\n3. Desejo assistir");
        int listaEscolha = lerOpcaoNumerica("Opção: ", 1, 3);
        
        System.out.println("\nOrdenar por qual critério?");
        System.out.println("1. Ordem alfabética (nome)\n2. Nota (maior para menor)\n3. Status\n4. Data de estreia");
        int criterioEscolha = lerOpcaoNumerica("Opção: ", 1, 4);

        Comparator<Serie> comparador = null;
        switch(criterioEscolha) {
            case 1 -> comparador = Comparator.comparing(Serie::getNome);
            case 2 -> comparador = Comparator.comparing(Serie::getNota, Comparator.nullsLast(Comparator.reverseOrder()));
            case 3 -> comparador = Comparator.comparing(Serie::getStatus);
            case 4 -> comparador = Comparator.comparing(Serie::getDataEstreia, Comparator.nullsLast(Comparator.naturalOrder()));
        }

        if (listaEscolha == 1) usuario.ordenarFavoritas(comparador);
        else if (listaEscolha == 2) usuario.ordenarAssistidas(comparador);
        else usuario.ordenarDesejoAssistir(comparador);

        System.out.println("Lista ordenada com sucesso!");
    }
    
    private static void exibirDetalhesSerie(Serie serie) {
        System.out.println("\n--- DETALHES: " + serie.getNome().toUpperCase() + " ---");
        System.out.println("Idioma: " + serie.getIdioma());
        System.out.println("Gêneros: " + String.join(", ", serie.getGeneros()));
        System.out.println("Nota Geral: " + (serie.getNota() != null ? serie.getNota() : "N/A"));
        System.out.println("Status: " + serie.getStatus());
        String dataEstreia = serie.getDataEstreia() != null ? serie.getDataEstreia().format(formatter) : "N/A";
        String dataTermino = serie.getDataTermino() != null ? "até " + serie.getDataTermino().format(formatter) : "";
        System.out.println("Período: " + dataEstreia + " " + dataTermino);
        System.out.println("Emissora: " + serie.getEmissora());
    }

    private static int lerOpcaoNumerica(String mensagem, int min, int max) {
        int opcao;
        while (true) {
            System.out.print(mensagem);
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                if (opcao >= min && opcao <= max) return opcao;
                else System.out.println("Opção inválida. Tente novamente.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }
}
