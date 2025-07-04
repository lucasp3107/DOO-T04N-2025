
import java.util.List;
import java.util.Scanner;

public class SeriesApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GerenciadorSeries gs = new GerenciadorSeries();
        Usuario usuario = null;

        try {
            System.out.print("Digite seu nome/apelido: ");
            usuario = new Usuario(sc.nextLine());

            while (true) {
                System.out.println("\n1 - Buscar série");
                System.out.println("2 - Ver listas");
                System.out.println("3 - Salvar e sair");
                System.out.print("Escolha: ");
                String opcao = sc.nextLine();

                switch (opcao) {
                    case "1":
                        System.out.print("Nome da série: ");
                        String nomeSerie = sc.nextLine();
                        try {
                            Serie serie = Util.buscarSeriePorNome(nomeSerie);
                            System.out.println(serie);
                            System.out.println("Adicionar a:");
                            System.out.println("1 - Favoritas\n2 - Assistidas\n3 - Quero assistir");
                            String escolha = sc.nextLine();
                            switch (escolha) {
                                case "1":
                                    gs.adicionar(serie, "favoritas");
                                    break;
                                case "2":
                                    gs.adicionar(serie, "assistidas");
                                    break;
                                case "3":
                                    gs.adicionar(serie, "desejo");
                                    break;
                            }
                        } catch (Exception e) {
                            System.out.println("Erro ao buscar série.");
                        }
                        break;
                    case "2":
                        System.out.println("Qual lista? (favoritas, assistidas, desejo)");
                        String qualLista = sc.nextLine();
                        List<Serie> lista = gs.getListaPorNome(qualLista);
                        if (lista.isEmpty()) {
                            System.out.println("Lista vazia.");
                            break;
                        }
                        System.out.println("Ordenar por: nome, nota, status, estreia");
                        String criterio = sc.nextLine();
                        gs.ordenar(lista, criterio);
                        lista.forEach(System.out::println);
                        break;
                    case "3":
                        Persistencia.salvar("favoritas.json", gs.getListaPorNome("favoritas"));
                        Persistencia.salvar("assistidas.json", gs.getListaPorNome("assistidas"));
                        Persistencia.salvar("desejo.json", gs.getListaPorNome("desejo"));
                        System.out.println("Dados salvos. Até logo!");
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
