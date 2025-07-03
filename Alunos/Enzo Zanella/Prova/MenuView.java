import java.time.format.DateTimeFormatter;
import java.util.List;

import com.minhas_series_tv.model.Serie;
import com.minhas_series_tv.util.ConsoleInput;

public class MenuView {

    public void exibirBoasVindas(String nomeUsuario) {
        System.out.println("=========================================");
        System.out.println("  Bem-vindo(a) ao Minhas Séries TV, " + nomeUsuario + "!");
        System.out.println("=========================================");
    }

    public int exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Buscar séries");
        System.out.println("2. Ver Séries Favoritas");
        System.out.println("3. Ver Séries Já Assistidas");
        System.out.println("4. Ver Séries Que Desejo Assistir");
        System.out.println("5. Sair");
        System.out.println("==========================");
        return ConsoleInput.lerIntComLimite("Escolha uma opção: ", 1, 5);
    }

    public void exibirMensagemNenhumaSerieEncontrada() {
        System.out.println("Nenhuma série encontrada para sua busca.");
    }

    public int exibirResultadosBusca(List<Serie> series) {
        if (series == null || series.isEmpty()) {
            exibirMensagemNenhumaSerieEncontrada();
            return 0;
        }

        System.out.println("\n===== SÉRIES ENCONTRADAS =====");
        for (int i = 0; i < series.size(); i++) {
            Serie serie = series.get(i);
            System.out.printf("[%d] %s (%s, %s)\n", (i + 1), serie.getNome(), serie.getStatus(), serie.getNomeEmissora());
        }
        System.out.println("------------------------------");
        return ConsoleInput.lerIntComLimite("Digite o número da série para ver detalhes (ou 0 para voltar): ", 0, series.size());
    }

    public int exibirOpcoesSerieDetalhes() {
        System.out.println("\nO que deseja fazer com essa série?");
        System.out.println("1. Adicionar aos Favoritos");
        System.out.println("2. Marcar como Assistida");
        System.out.println("3. Adicionar à lista 'Desejo Assistir'");
        System.out.println("4. Voltar");
        return ConsoleInput.lerIntComLimite("Escolha uma opção: ", 1, 4);
    }

    public int exibirMenuOrdenacao() {
        System.out.println("\nOrdenar por:");
        System.out.println("1. Ordem alfabética do nome");
        System.out.println("2. Nota geral (maior para menor)");
        System.out.println("3. Estado da série (concluída, transmitindo, cancelada)");
        System.out.println("4. Data de estreia");
        System.out.println("0. Voltar (sem ordenação)");
        return ConsoleInput.lerIntComLimite("Escolha uma opção: ", 0, 4);
    }

    public int exibirListaSeries(String tituloLista, List<Serie> series) {
        if (series.isEmpty()) {
            System.out.println("\n" + tituloLista + ": (Vazia)");
            return 0;
        }

        System.out.println("\n===== " + tituloLista.toUpperCase() + " =====");
        for (int i = 0; i < series.size(); i++) {
            Serie serie = series.get(i);
            String dataEstreiaFormatada = serie.getDataEstreia() != null ?
                    serie.getDataEstreia().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";

            System.out.printf("[%d] %s (Nota: %.1f, Status: %s, Estreia: %s)\n",
                    (i + 1), serie.getNome(), serie.getNotaGeral(), serie.getStatus(), dataEstreiaFormatada);
        }
        System.out.println("------------------------------");

        System.out.println("Digite o número da série para remover (ou 0 para voltar): ");
        return ConsoleInput.lerIntComLimite("Escolha: ", 0, series.size());
    }

    public void exibirDetalhesSerie(Serie serie) {
        if (serie == null) {
            System.out.println("Detalhes da série não disponíveis.");
            return;
        }
        System.out.println("\n===== DETALHES DA SÉRIE =====");
        System.out.println("Nome: " + serie.getNome());
        System.out.println("Idioma: " + serie.getIdioma());
        System.out.println("Gêneros: " + (serie.getGeneros() != null ? String.join(", ", serie.getGeneros()) : "N/A"));
        System.out.println("Nota Geral: " + serie.getNotaGeral());
        System.out.println("Estado: " + serie.getStatus());
        System.out.println("Data de Estreia: " + (serie.getDataEstreia() != null ? serie.getDataEstreia().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A"));
        System.out.println("Data de Término: " + (serie.getDataTermino() != null ? serie.getDataTermino().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A"));
        System.out.println("Emissora/Streaming: " + serie.getNomeEmissora());
        System.out.println("=============================");
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public String pedirNomeUsuario() {
        return ConsoleInput.lerString("Digite seu nome ou apelido: ");
    }

    public String pedirTermoBusca() {
        return ConsoleInput.lerString("Digite o nome da série para buscar: ");
    }
}