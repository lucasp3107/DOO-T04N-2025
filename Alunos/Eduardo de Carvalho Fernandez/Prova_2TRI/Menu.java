


import java.util.*;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        Usuario usuario = carregarOuCriarUsuario();

        int opcao;
        do {
            System.out.println("\nOlá, " + usuario.getNome() + "! Escolha uma opção:");
            System.out.println("1. Buscar série");
            System.out.println("2. Ver listas");
            System.out.println("3. Salvar e sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> buscarSerie(usuario);
                case 2 -> mostrarListas(usuario);
                case 3 -> Persistencia.salvar(usuario);
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 3);
    }

    private Usuario carregarOuCriarUsuario() {
        Usuario usuario = Persistencia.carregar();
        if (usuario == null) {
            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();
            usuario = new Usuario(nome);
        }
        return usuario;
    }

    private void buscarSerie(Usuario usuario) {
        try {
            System.out.print("Digite o nome da série: ");
            String nomeBusca = scanner.nextLine();
            Serie serie = GerenciadorSeries.buscarSerie(nomeBusca);
            System.out.println(serie);

            System.out.println("Adicionar a:");
            System.out.println("1. Favoritos");
            System.out.println("2. Assistidas");
            System.out.println("3. Desejadas");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1 -> usuario.adicionar(usuario.getFavoritas(), serie);
                case 2 -> usuario.adicionar(usuario.getAssistidas(), serie);
                case 3 -> usuario.adicionar(usuario.getDesejadas(), serie);
                default -> System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void mostrarListas(Usuario usuario) {
        System.out.println("\n1. Favoritas\n2. Assistidas\n3. Desejadas");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        List<Serie> lista = switch (escolha) {
            case 1 -> usuario.getFavoritas();
            case 2 -> usuario.getAssistidas();
            case 3 -> usuario.getDesejadas();
            default -> null;
        };

        if (lista == null || lista.isEmpty()) {
            System.out.println("Lista vazia ou inválida.");
            return;
        }

        System.out.println("Ordenar por: 1. Nome  2. Nota  3. Status  4. Estreia");
        int ordem = scanner.nextInt();
        scanner.nextLine();

        lista.sort(switch (ordem) {
            case 1 -> Comparator.comparing(Serie::getNome);
            case 2 -> Comparator.comparingDouble(Serie::getNota).reversed();
            case 3 -> Comparator.comparing(Serie::getStatus);
            case 4 -> Comparator.comparing(Serie::getDataEstreia);
            default -> Comparator.comparing(Serie::getNome);
        });

        lista.forEach(System.out::println);
    }
}
