import java.util.*;
import java.util.stream.Collectors;

public class Sistema {
    private Usuario usuario;
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        usuario = Persistencia.carregarDados(nome);

        while (true) {
            System.out.println("\n1. Buscar série");
            System.out.println("2. Ver listas");
            System.out.println("3. Salvar e sair");
            int opcao = Utils.lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> buscarSerie();
                case 2 -> mostrarListas();
                case 3 -> {
                    Persistencia.salvarDados(usuario);
                    System.out.println("Salvo com sucesso. Até logo!");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void buscarSerie() {
        System.out.print("Digite o nome da série: ");
        String nomeBusca = scanner.nextLine();
        List<Serie> resultados = ConexaoAPI.buscarSeries(nomeBusca);
        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado encontrado.");
            return;
        }

        for (int i = 0; i < resultados.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, resultados.get(i).getNome());
        }

        int indice = Utils.lerInt("Escolha a série (0 para cancelar): ");
        if (indice <= 0 || indice > resultados.size()) return;

        Serie selecionada = resultados.get(indice - 1);
        System.out.println("\n" + selecionada);

        System.out.println("1. Adicionar aos favoritos");
        System.out.println("2. Marcar como assistida");
        System.out.println("3. Adicionar aos desejos");
        System.out.println("0. Voltar");
        int escolha = Utils.lerInt("Escolha: ");
        switch (escolha) {
            case 1 -> usuario.getFavoritos().add(selecionada);
            case 2 -> usuario.getAssistidas().add(selecionada);
            case 3 -> usuario.getDesejos().add(selecionada);
        }
    }

    private void mostrarListas() {
        System.out.println("1. Favoritos");
        System.out.println("2. Assistidas");
        System.out.println("3. Desejos");
        int op = Utils.lerInt("Escolha: ");

        List<Serie> lista = switch (op) {
            case 1 -> usuario.getFavoritos();
            case 2 -> usuario.getAssistidas();
            case 3 -> usuario.getDesejos();
            default -> null;
        };
        if (lista == null || lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }

        System.out.println("1. Ordenar por nome");
        System.out.println("2. Ordenar por nota");
        System.out.println("3. Ordenar por status");
        System.out.println("4. Ordenar por data de estreia");
        int ordenacao = Utils.lerInt("Escolha: ");
        lista = Utils.ordenarLista(lista, ordenacao);

        for (Serie s : lista) {
            System.out.println("\n" + s);
        }

        System.out.println("\nDeseja remover uma série da lista?");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        int rem = Utils.lerInt("Escolha: ");
        if (rem == 1) {
            for (int i = 0; i < lista.size(); i++) {
                System.out.printf("%d - %s\n", i + 1, lista.get(i).getNome());
            }
            int idx = Utils.lerInt("Digite o número da série a remover: ");
            if (idx > 0 && idx <= lista.size()) {
                lista.remove(idx - 1);
                System.out.println("Removido com sucesso!");
            }
        }

    }
}