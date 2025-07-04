import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
    private Scanner scanner;
    private GerenciadorDeDados gerenciadorDeDados;
    private Usuario usuario;
    private TvMazeApiClient apiClient;

    public static void main(String[] args) {
        while (true) {
            App app = new App();
            boolean deveReiniciar = app.executarSessao();

            if (!deveReiniciar) {
                break;
            }
            System.out.println("\nReiniciando o sistema...");
            System.out.println("------------------------------------------");
        }
    }

    public App() {
        this.scanner = new Scanner(System.in);
        this.gerenciadorDeDados = new GerenciadorDeDados();
        this.apiClient = new TvMazeApiClient();
    }

    public boolean executarSessao() {
        this.usuario = gerenciadorDeDados.carregarUsuario();

        if (usuario.getNome().equals("Novo Usuário")) {
            System.out.print("Bem-vindo! Para começar, digite seu nome ou apelido: ");
            String novoNome = scanner.nextLine();
            if (novoNome == null || novoNome.isBlank()) {
                novoNome = "Usuário Padrão";
            }
            usuario.setNome(novoNome);
            System.out.println("\nÓtimo, " + usuario.getNome() + "! Seu perfil foi criado.");
            gerenciadorDeDados.salvarUsuario(usuario);
        } else {
            System.out.println("\nBem-vindo(a) de volta, " + usuario.getNome() + "!");
        }

        return exibirMenuPrincipal();
    }

    private boolean exibirMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Procurar e Adicionar Série");
            System.out.println("2. Ver Minhas Listas");
            System.out.println("3. Remover Série de uma Lista");
            System.out.println("4. Alterar meu nome");
            System.out.println("5. Salvar e Sair");
            System.out.println("6. Limpar Dados e Reiniciar");
            System.out.println("7. Sair sem Salvar");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: procurarEAdicionarSerie(); break;
                    case 2: verListas(); break;
                    case 3: removerSerie(); break;
                    case 4: alterarNome(); break;
                    case 5:
                        gerenciadorDeDados.salvarUsuario(usuario);
                        System.out.println("Dados salvos com sucesso! Até logo!");
                        return false;
                    case 6:
                        if (confirmarLimpezaDeDados()) {
                            return true;
                        }
                        break;
                    case 7:
                        System.out.println("Saindo sem salvar. Até logo!");
                        return false;
                    default: System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        }
    }

    private boolean confirmarLimpezaDeDados() {
        System.out.print("\nATENÇÃO: Isso apagará todas as suas listas e seu nome de usuário.\nVocê tem certeza que deseja continuar? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            return gerenciadorDeDados.limparDados();
        } else {
            System.out.println("Operação cancelada. Seus dados estão seguros.");
            return false;
        }
    }

    private void procurarEAdicionarSerie() throws Exception {
        System.out.print("\nDigite o nome da série para procurar: ");
        String nomeSerie = scanner.nextLine();
        List<Serie> resultados = apiClient.buscarSeries(nomeSerie);

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma série encontrada com esse nome.");
            return;
        }

        System.out.println("\nResultados da busca:");
        for (int i = 0; i < resultados.size(); i++) {
            System.out.println((i + 1) + " - " + resultados.get(i).getName());
        }

        System.out.print("\nEscolha o número da série que deseja adicionar (ou 0 para cancelar): ");
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha > 0 && escolha <= resultados.size()) {
            Serie serieEscolhida = resultados.get(escolha - 1);
            adicionarSerieEmLista(serieEscolhida);
        }
    }

    private void adicionarSerieEmLista(Serie serie) {
        System.out.println("\nAdicionar '" + serie.getName() + "' em qual lista?");
        System.out.println("1. Favoritas");
        System.out.println("2. Já Assistidas");
        System.out.println("3. Desejo Assistir");
        System.out.print("Escolha uma opção: ");
        int escolhaLista = Integer.parseInt(scanner.nextLine());

        switch (escolhaLista) {
            case 1: usuario.getFavoritas().add(serie); break;
            case 2: usuario.getAssistidas().add(serie); break;
            case 3: usuario.getDesejoAssistir().add(serie); break;
            default: System.out.println("Opção inválida."); return;
        }
        System.out.println("Série adicionada com sucesso!");
        gerenciadorDeDados.salvarUsuario(usuario);
    }

    private void verListas() {
        System.out.println("\nQual lista você deseja ver?");
        System.out.println("1. Favoritas");
        System.out.println("2. Já Assistidas");
        System.out.println("3. Desejo Assistir");
        System.out.print("Escolha uma opção: ");
        int escolhaLista = Integer.parseInt(scanner.nextLine());

        List<Serie> lista;
        String nomeLista;

        switch (escolhaLista) {
            case 1: lista = usuario.getFavoritas(); nomeLista = "Favoritas"; break;
            case 2: lista = usuario.getAssistidas(); nomeLista = "Já Assistidas"; break;
            case 3: lista = usuario.getDesejoAssistir(); nomeLista = "Desejo Assistir"; break;
            default: System.out.println("Opção inválida."); return;
        }

        if (lista.isEmpty()) {
            System.out.println("\nA lista '" + nomeLista + "' está vazia.");
            return;
        }

        ordenarEExibirLista(lista, nomeLista);
    }

    private void ordenarEExibirLista(List<Serie> lista, String nomeLista) {
        System.out.println("\nComo deseja ordenar a lista?");
        System.out.println("1. Ordem Alfabética");
        System.out.println("2. Nota (maior para menor)");
        System.out.println("3. Status");
        System.out.println("4. Data de Estreia");
        System.out.print("Escolha uma opção de ordenação: ");
        int escolhaOrdem = Integer.parseInt(scanner.nextLine());

        switch (escolhaOrdem) {
            case 1: lista.sort(Comparator.comparing(Serie::getName, String.CASE_INSENSITIVE_ORDER)); break;
            case 2: lista.sort(Comparator.comparingDouble(Serie::getNota).reversed()); break;
            case 3: lista.sort(Comparator.comparing(Serie::getStatus, Comparator.nullsLast(String::compareTo))); break;
            case 4: lista.sort(Comparator.comparing(Serie::getDataEstreia, Comparator.nullsLast(String::compareTo))); break;
            default: System.out.println("Opção inválida. Exibindo na ordem padrão.");
        }

        System.out.println("\n--- LISTA: " + nomeLista.toUpperCase() + " ---");
        for (Serie serie : lista) {
            System.out.println(serie);
            System.out.println("----------------------------------------");
        }
    }

    private void removerSerie() {
        System.out.println("\nDe qual lista você quer remover uma série?");
        System.out.println("1. Favoritas");
        System.out.println("2. Já Assistidas");
        System.out.println("3. Desejo Assistir");
        System.out.print("Escolha uma opção: ");
        int escolhaLista = Integer.parseInt(scanner.nextLine());

        List<Serie> lista;
        switch (escolhaLista) {
            case 1: lista = usuario.getFavoritas(); break;
            case 2: lista = usuario.getAssistidas(); break;
            case 3: lista = usuario.getDesejoAssistir(); break;
            default: System.out.println("Opção inválida."); return;
        }

        if (lista.isEmpty()) {
            System.out.println("Esta lista já está vazia.");
            return;
        }

        System.out.println("\nSéries na lista:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getName());
        }

        System.out.print("\nDigite o número da série para remover (ou 0 para cancelar): ");
        int escolhaRemover = Integer.parseInt(scanner.nextLine());
        if (escolhaRemover > 0 && escolhaRemover <= lista.size()) {
            Serie removida = lista.remove(escolhaRemover - 1);
            System.out.println("Série '" + removida.getName() + "' removida com sucesso.");
            gerenciadorDeDados.salvarUsuario(usuario);
        }
    }

    private void alterarNome() {
        System.out.print("Digite seu novo nome ou apelido: ");
        String novoNome = scanner.nextLine();
        usuario.setNome(novoNome);
        gerenciadorDeDados.salvarUsuario(usuario);
        System.out.println("Nome alterado para: " + novoNome);
    }
}