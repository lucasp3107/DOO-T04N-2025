import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Usuario u = JsonUtil.carregar();
        if (u == null) {
            System.out.print("Digite seu nome: ");
            u = new Usuario(sc.nextLine());
        }

        SerieManager m = new SerieManager(u);

        while (true) {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1 - Buscar serie por nome");
            System.out.println("2 - Gerenciar listas");
            System.out.println("3 - Sair");
            System.out.print("Escolha: ");
            int op = sc.nextInt(); sc.nextLine();

            try {
                if (op == 1) {
                    System.out.print("Nome da serie: ");
                    Serie s = TvMazeAPI.buscarSeriePorNome(sc.nextLine());
                    System.out.println(s);
                    System.out.println("Adicionar a: 1-Favoritas | 2-Ja assistidas | 3-Desejo assistir | Outro-Cancelar");
                    int escolha = sc.nextInt(); sc.nextLine();
                    switch (escolha) {
                        case 1 -> m.adicionar(s, u.favoritas);
                        case 2 -> m.adicionar(s, u.assistidas);
                        case 3 -> m.adicionar(s, u.desejoAssistir);
                    }
                } else if (op == 2) {
                    System.out.println("Lista: 1-Favoritas 2-Ja assistidas 3-Desejo assistir");
                    int escolha = sc.nextInt(); sc.nextLine();
                    List<Serie> listaSelecionada = switch (escolha) {
                        case 1 -> u.favoritas;
                        case 2 -> u.assistidas;
                        case 3 -> u.desejoAssistir;
                        default -> null;
                    };
                    if (listaSelecionada == null) continue;

                    boolean gerenciar = true;
                    while (gerenciar) {
                        System.out.println("\nAcoes:");
                        System.out.println("1 - Ver series");
                        System.out.println("2 - Adicionar serie");
                        System.out.println("3 - Remover serie");
                        System.out.println("4 - Ordenar por nome");
                        System.out.println("5 - Ordenar por nota");
                        System.out.println("6 - Ordenar por status");
                        System.out.println("7 - Ordenar por data de estreia");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        int acao = sc.nextInt(); sc.nextLine();
                        switch (acao) {
                            case 1 -> m.mostrarLista(listaSelecionada);
                            case 2 -> {
                                System.out.print("Nome da serie: ");
                                Serie nova = TvMazeAPI.buscarSeriePorNome(sc.nextLine());
                                m.adicionar(nova, listaSelecionada);
                            }
                            case 3 -> {
                                System.out.print("Nome da serie a remover: ");
                                String nome = sc.nextLine();
                                m.removerPorNome(nome, listaSelecionada);
                            }
                            case 4 -> m.ordenarPorNome(listaSelecionada);
                            case 5 -> m.ordenarPorNota(listaSelecionada);
                            case 6 -> m.ordenarPorStatus(listaSelecionada);
                            case 7 -> m.ordenarPorEstreia(listaSelecionada);
                            case 0 -> gerenciar = false;
                        }
                    }
                } else if (op == 3) {
                    JsonUtil.salvar(u);
                    System.out.println("Salvo!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}