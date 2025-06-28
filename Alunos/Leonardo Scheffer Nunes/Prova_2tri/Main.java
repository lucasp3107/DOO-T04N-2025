import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SerieRepository repo = new SerieRepository();
        Usuario usuario = repo.carregarUsuario();

        if (usuario == null) {
            System.out.print("Digite seu nome ou apelido: ");
            String nome = sc.nextLine();
            usuario = new Usuario(nome);
        }

        SerieService service = new SerieService();

        boolean sair = false;
        while (!sair) {
            System.out.println("\nMenu:");
            System.out.println("1 - Buscar série por nome");
            System.out.println("2 - Ver listas");
            System.out.println("3 - Sair e salvar");
            System.out.print("Escolha: ");
            String opcao = sc.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Nome da série: ");
                    String nomeSerie = sc.nextLine();
                    Serie serie = service.buscarSeriePorNome(nomeSerie);
                    if (serie != null) {
                        System.out.println(serie);
                        System.out.println("Adicionar a qual lista? (1-Favoritos, 2-Assistidas, 3-Deseja assistir, outro-Não adicionar)");
                        String escolha = sc.nextLine();
                        if (escolha.equals("1")) usuario.adicionarFavorito(serie);
                        else if (escolha.equals("2")) usuario.adicionarAssistida(serie);
                        else if (escolha.equals("3")) usuario.adicionarDesejaAssistir(serie);
                    } else {
                        System.out.println("Série não encontrada.");
                    }
                    break;
                case "2":
                    usuario.mostrarListas();
                    break;
                case "3":
                    repo.salvarUsuario(usuario);
                    System.out.println("Dados salvos. Saindo...");
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }
}
