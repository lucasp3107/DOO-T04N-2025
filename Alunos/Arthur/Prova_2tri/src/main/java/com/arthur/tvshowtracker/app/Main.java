package com.arthur.tvshowtracker.app;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
    private static final String ARQUIVO_DADOS = "dados_app.json";
    private static PerfilUsuario usuario;
    private static GerenciadorSeries gerenciador;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        gerenciador = new GerenciadorSeries();

        try {
            carregarDadosApp();
        } catch (IOException | JSONException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            System.out.println("Iniciando com dados vazios. Por favor, cadastre um usuário.");
            usuario = PerfilUsuario.registrarUsuario(scanner);
        }

        int opcao = -1;
        do {
            exibirMenuPrincipal();
            opcao = obterOpcaoUsuario();
            processarOpcaoMenuPrincipal(opcao);

            if (opcao != 5) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        } while (opcao != 5);
        salvarDadosApp();
        System.out.println("Dados salvos. Saindo do sistema. Até logo, " + usuario.getApelido() + "!");
        scanner.close();
    }

    private static void carregarDadosApp() throws IOException, JSONException {
        JSONObject dadosCarregados = carregarDados();
        if (dadosCarregados != null) {
            if (dadosCarregados.has("usuario") && !dadosCarregados.isNull("usuario")) {
                JSONObject jsonUsuario = dadosCarregados.getJSONObject("usuario");
                usuario = new PerfilUsuario(jsonUsuario.optString("nomeCompleto", "Usuário Padrão"), jsonUsuario.optString("apelido", "Usuário Padrão"));
                System.out.println("Bem-vindo de volta, " + usuario.getApelido() + "!");
            } else {
                System.out.println("Nenhum usuário encontrado nos dados salvos ou dados de usuário inválidos. Por favor, cadastre-se.");
                usuario = PerfilUsuario.registrarUsuario(scanner);
            }

            if (dadosCarregados.has("favoritas") && !dadosCarregados.isNull("favoritas")) {
                gerenciador.setFavoritas(parseSeriesDeJSONArray(dadosCarregados.getJSONArray("favoritas")));
            }
            if (dadosCarregados.has("assistidas") && !dadosCarregados.isNull("assistidas")) {
                gerenciador.setAssistidas(parseSeriesDeJSONArray(dadosCarregados.getJSONArray("assistidas")));
            }
            if (dadosCarregados.has("paraAssistir") && !dadosCarregados.isNull("paraAssistir")) {
                gerenciador.setParaAssistir(parseSeriesDeJSONArray(dadosCarregados.getJSONArray("paraAssistir")));
            }
            System.out.println("Dados carregados com sucesso.");
        } else {
            System.out.println("Nenhum dado salvo encontrado. Iniciando novo sistema.");
            usuario = PerfilUsuario.registrarUsuario(scanner);
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== Menu Principal =====\n");
        System.out.println("1-  Gerenciar Usuário ");
        System.out.println("2-  Buscar série por nome e adicionar às listas");
        System.out.println("3-  Visualizar e Gerenciar minhas listas de séries");
        System.out.println("4-  Ordenar listas");
        System.out.println("5-  Salvar e sair do sistema");
        System.out.print("O que você deseja fazer? [1-5]: ");
    }

    private static int obterOpcaoUsuario() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            return -1;
        }
    }

    private static void processarOpcaoMenuPrincipal(int opcao) {
        switch (opcao) {
            case 1:
                gerenciarUsuario();
                break;
            case 2:
                buscarEAdicionarSerieMenu();
                break;
            case 3:
                visualizarEGerenciarListasSeries();
                break;
            case 4:
                ordenarListasSeries();
                break;
            case 5:
                break;
            default:
                System.out.println("Opção inválida. Por favor, escolha uma opção entre 1-5.");
                break;
        }
    }

    private static JSONObject carregarDados() throws IOException, JSONException {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists() || arquivo.length() == 0) {
            return null;
        }

        StringBuilder conteudo = new StringBuilder();
        try (FileReader leitor = new FileReader(arquivo)) {
            int caractere;
            while ((caractere = leitor.read()) != -1) {
                conteudo.append((char) caractere);
            }
        }
        return new JSONObject(conteudo.toString());
    }

    private static void salvarDadosApp() {
        JSONObject dadosParaSalvar = new JSONObject();
        try {
            JSONObject jsonUsuario = new JSONObject();
            jsonUsuario.put("nomeCompleto", usuario.getNomeCompleto());
            jsonUsuario.put("apelido", usuario.getApelido());
            dadosParaSalvar.put("usuario", jsonUsuario);

            dadosParaSalvar.put("favoritas", criarJSONArrayDeListaSeries(gerenciador.getFavoritas()));
            dadosParaSalvar.put("assistidas", criarJSONArrayDeListaSeries(gerenciador.getAssistidas()));
            dadosParaSalvar.put("paraAssistir", criarJSONArrayDeListaSeries(gerenciador.getParaAssistir()));

            try (FileWriter escritorArquivo = new FileWriter(ARQUIVO_DADOS)) {
                escritorArquivo.write(dadosParaSalvar.toString(4));
                System.out.println("Dados salvos com sucesso em " + ARQUIVO_DADOS);
            }
        } catch (IOException | JSONException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static JSONArray criarJSONArrayDeListaSeries(List<Serie> series) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Serie serie : series) {
            JSONObject jsonSerie = new JSONObject();
            jsonSerie.put("id", serie.getId());
            jsonSerie.put("nome", serie.getNome());
            jsonSerie.put("idioma", serie.getIdioma());
            jsonSerie.put("generos", new JSONArray(serie.getGeneros()));
            jsonSerie.putOpt("avaliacaoGeral", serie.getAvaliacaoGeral());
            jsonSerie.put("status", serie.getStatus());
            jsonSerie.putOpt("dataEstreia", serie.getDataEstreia() != null ? serie.getDataEstreia().toString() : null);
            jsonSerie.putOpt("dataFim", serie.getDataFim() != null ? serie.getDataFim().toString() : null);
            jsonSerie.putOpt("emissora", serie.getEmissora());
            jsonArray.put(jsonSerie);
        }
        return jsonArray;
    }

    private static List<Serie> parseSeriesDeJSONArray(JSONArray jsonArray) throws JSONException {
        List<Serie> series = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonSerie = jsonArray.getJSONObject(i);

            int id = jsonSerie.getInt("id");
            String nome = jsonSerie.getString("nome");
            String idioma = jsonSerie.getString("idioma");

            List<String> generos = new ArrayList<>();
            JSONArray jsonGeneros = jsonSerie.optJSONArray("generos");
            if (jsonGeneros != null) {
                for (int j = 0; j < jsonGeneros.length(); j++) {
                    generos.add(jsonGeneros.getString(j));
                }
            }

            Double avaliacaoGeral = jsonSerie.optDouble("avaliacaoGeral", Double.NaN);
            if (Double.isNaN(avaliacaoGeral) && !jsonSerie.has("avaliacaoGeral")) {
                avaliacaoGeral = null;
            } else if (Double.isNaN(avaliacaoGeral)) {
                avaliacaoGeral = null;
            }

            String status = jsonSerie.optString("status", "N/A");

            LocalDate dataEstreia = null;
            if (jsonSerie.has("dataEstreia") && !jsonSerie.isNull("dataEstreia")) {
                try {
                    dataEstreia = LocalDate.parse(jsonSerie.getString("dataEstreia"));
                } catch (DateTimeParseException e) {
                }
            }

            LocalDate dataFim = null;
            if (jsonSerie.has("dataFim") && !jsonSerie.isNull("dataFim")) {
                try {
                    dataFim = LocalDate.parse(jsonSerie.getString("dataFim"));
                } catch (DateTimeParseException e) {
                }
            }

            String emissora = null;
            if (jsonSerie.has("network") && !jsonSerie.isNull("network")) {
                JSONObject objEmissora = jsonSerie.getJSONObject("network");
                emissora = objEmissora.optString("name", "N/A");
            } else if (jsonSerie.has("webChannel") && !jsonSerie.isNull("webChannel")) {
                JSONObject canalWeb = jsonSerie.getJSONObject("webChannel");
                emissora = canalWeb.optString("name", "N/A (Canal Web)");
            }

            series.add(new Serie(id, nome, idioma, generos, avaliacaoGeral, status, dataEstreia, dataFim, emissora));
        }
        return series;
    }

    private static void gerenciarUsuario() {
        System.out.println("\n--- Gerenciar Usuário ---");
        System.out.println("Seu usuário atual é: " + usuario.toString());
        System.out.print("Deseja alterar seu nome ou apelido? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        if (resposta.equals("s")) {
            System.out.print("Digite o novo nome completo: ");
            usuario.setNomeCompleto(scanner.nextLine());
            System.out.print("Digite o novo apelido (deixe em branco para usar o nome completo): ");
            String novoApelido = scanner.nextLine();
            usuario.setApelido(novoApelido.isEmpty() ? usuario.getNomeCompleto() : novoApelido);
            System.out.println("Usuário atualizado para: " + usuario.toString());
        } else if (!resposta.equals("n")) {
            System.out.println("Resposta inválida. Nenhuma alteração foi feita no usuário.");
        }
    }

    private static void buscarEAdicionarSerieMenu() {
        System.out.print("Digite o nome da série que você quer buscar: ");
        String nomeBusca = scanner.nextLine();

        Optional<JSONArray> resultadosOpt = ApiSerieTv.buscarSeriesPorNome(nomeBusca);

        if (resultadosOpt.isEmpty() || resultadosOpt.get().length() == 0) {
            System.out.println("Nenhuma série encontrada para '" + nomeBusca + "'.");
            return;
        }

        JSONArray resultados = resultadosOpt.get();
        List<Serie> seriesEncontradas = new ArrayList<>();

        System.out.println("\n--- Resultados da Busca ---");
        for (int i = 0; i < resultados.length(); i++) {
            try {
                JSONObject item = resultados.getJSONObject(i);
                JSONObject dadosSerie = item.getJSONObject("show");
                Serie serie = parseSerieDeResultadoBusca(dadosSerie);
                seriesEncontradas.add(serie);
                System.out.println((i + 1) + ". " + serie.toStringCurto());

            } catch (JSONException e) {
                System.err.println("Erro ao analisar resultado da série: " + e.getMessage());
            }
        }

        if (!seriesEncontradas.isEmpty()) {
            System.out.print("\nDigite o número da série que você deseja gerenciar (detalhes/adicionar) ou '0' para voltar: ");
            try {
                int escolha = Integer.parseInt(scanner.nextLine());
                if (escolha > 0 && escolha <= seriesEncontradas.size()) {
                    Serie serieSelecionada = seriesEncontradas.get(escolha - 1);
                    exibirDetalhesSerieEOpcaoAdicionar(serieSelecionada);
                } else if (escolha != 0) {
                    System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
            }
        }
    }

    private static Serie parseSerieDeResultadoBusca(JSONObject dadosSerie) throws JSONException {
        int id = dadosSerie.getInt("id");
        String nome = dadosSerie.optString("name", "N/A");
        String idioma = dadosSerie.optString("language", "N/A");

        List<String> generos = new ArrayList<>();
        JSONArray jsonGeneros = dadosSerie.optJSONArray("genres");
        if (jsonGeneros != null) {
            for (int j = 0; j < jsonGeneros.length(); j++) {
                generos.add(jsonGeneros.getString(j));
            }
        }

        Double avaliacaoGeral = null;
        if (dadosSerie.has("rating") && !dadosSerie.isNull("rating")) {
            JSONObject avaliacao = dadosSerie.getJSONObject("rating");
            avaliacaoGeral = avaliacao.optDouble("average", Double.NaN);
            if (Double.isNaN(avaliacaoGeral)) avaliacaoGeral = null;
        }

        String status = dadosSerie.optString("status", "N/A");

        LocalDate dataEstreia = null;
        if (dadosSerie.has("premiered") && !dadosSerie.isNull("premiered")) {
            try {
                dataEstreia = LocalDate.parse(dadosSerie.getString("premiered"));
            } catch (DateTimeParseException e) {
            }
        }

        LocalDate dataFim = null;
        if (dadosSerie.has("ended") && !dadosSerie.isNull("ended")) {
            try {
                dataFim = LocalDate.parse(dadosSerie.getString("ended"));
            } catch (DateTimeParseException e) {
            }
        }

        String emissora = null;
        if (dadosSerie.has("network") && !dadosSerie.isNull("network")) {
            JSONObject objEmissora = dadosSerie.getJSONObject("network");
            emissora = objEmissora.optString("name", "N/A");
        } else if (dadosSerie.has("webChannel") && !dadosSerie.isNull("webChannel")) {
            JSONObject canalWeb = dadosSerie.getJSONObject("webChannel");
            emissora = canalWeb.optString("name", "N/A (Canal Web)");
        }

        return new Serie(id, nome, idioma, generos, avaliacaoGeral, status, dataEstreia, dataFim, emissora);
    }

    private static void exibirDetalhesSerieEOpcaoAdicionar(Serie serie) {
        System.out.println("\n--- Detalhes da Série Selecionada ---");
        System.out.println(serie.toString());
        System.out.println("\nO que deseja fazer com esta série?");
        System.out.println("1. Adicionar aos Favoritos");
        System.out.println("2. Adicionar às Séries Já Assistidas");
        System.out.println("3. Adicionar às Séries Que Deseja Assistir");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        int subOpcao = obterOpcaoUsuario();
        switch (subOpcao) {
            case 1:
                gerenciador.adicionarSerieFavorita(serie);
                break;
            case 2:
                gerenciador.adicionarSerieAssistida(serie);
                break;
            case 3:
                gerenciador.adicionarSerieParaAssistir(serie);
                break;
            case 0:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    private static void visualizarEGerenciarListasSeries() {
        int escolhaLista;
        do {
            System.out.println("\n--- Visualizar e Gerenciar Listas ---");
            System.out.println("1. Minhas Séries Favoritas");
            System.out.println("2. Minhas Séries Já Assistidas");
            System.out.println("3. Minhas Séries Que Deseja Assistir");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Qual lista você deseja ver? [0-3]: ");
            escolhaLista = obterOpcaoUsuario();

            List<Serie> listaAtual = null;
            String nomeLista = "";
            switch (escolhaLista) {
                case 1:
                    listaAtual = gerenciador.getFavoritas();
                    nomeLista = "Favoritas";
                    gerenciador.exibirListaFavoritas(false);
                    break;
                case 2:
                    listaAtual = gerenciador.getAssistidas();
                    nomeLista = "Já Assistidas";
                    gerenciador.exibirListaAssistidas(false);
                    break;
                case 3:
                    listaAtual = gerenciador.getParaAssistir();
                    nomeLista = "Que Deseja Assistir";
                    gerenciador.exibirListaParaAssistir(false);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            if (listaAtual != null && !listaAtual.isEmpty()) {
                gerenciarListaSelecionada(listaAtual, nomeLista);
            } else if (escolhaLista != 0) {
                System.out.println("Nenhuma série nesta lista para gerenciar.");
            }
            if (escolhaLista != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        } while (escolhaLista != 0);
    }

    private static void gerenciarListaSelecionada(List<Serie> lista, String nomeLista) {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Lista de " + nomeLista + " ---");
            System.out.println("1. Ver detalhes de uma série");
            System.out.println("2. Remover uma série");
            System.out.println("0. Voltar para seleção de listas");
            System.out.print("O que deseja fazer? [0-2]: ");
            opcao = obterOpcaoUsuario();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o número da série para ver detalhes: ");
                    int indiceSerie = obterOpcaoUsuario();
                    if (indiceSerie > 0 && indiceSerie <= lista.size()) {
                        System.out.println("\n--- Detalhes da Série ---");
                        System.out.println(lista.get(indiceSerie - 1).toString());
                    } else {
                        System.out.println("Número de série inválido.");
                    }
                    break;
                case 2:
                    System.out.print("Digite o número da série para remover: ");
                    int indiceRemover = obterOpcaoUsuario();
                    if (indiceRemover > 0 && indiceRemover <= lista.size()) {
                        Serie serieParaRemover = lista.get(indiceRemover - 1);
                        boolean removido = false;
                        if (nomeLista.equals("Favoritas")) {
                            removido = gerenciador.removerSerieFavorita(serieParaRemover);
                        } else if (nomeLista.equals("Já Assistidas")) {
                            removido = gerenciador.removerSerieAssistida(serieParaRemover);
                        } else if (nomeLista.equals("Que Deseja Assistir")) {
                            removido = gerenciador.removerSerieParaAssistir(serieParaRemover);
                        }
                        if (removido) {
                            System.out.println("Série '" + serieParaRemover.getNome() + "' removida da lista de " + nomeLista + ".");
                        } else {
                            System.out.println("Erro ao remover série.");
                        }
                    } else {
                        System.out.println("Número de série inválido.");
                    }
                    System.out.println("\n--- Lista de " + nomeLista + " Atualizada ---");
                    if (nomeLista.equals("Favoritas")) gerenciador.exibirListaFavoritas(false);
                    else if (nomeLista.equals("Já Assistidas")) gerenciador.exibirListaAssistidas(false);
                    else if (nomeLista.equals("Que Deseja Assistir")) gerenciador.exibirListaParaAssistir(false);
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        } while (opcao != 0);
    }

    private static void ordenarListasSeries() {
        int escolhaLista;
        do {
            System.out.println("\n--- Ordenar Listas ---");
            System.out.println("1. Minhas Séries Favoritas");
            System.out.println("2. Minhas Séries Já Assistidas");
            System.out.println("3. Minhas Séries Que Deseja Assistir");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Qual lista você deseja ordenar? [0-3]: ");
            escolhaLista = obterOpcaoUsuario();

            List<Serie> listaParaOrdenar = null;
            String nomeLista = "";
            switch (escolhaLista) {
                case 1:
                    listaParaOrdenar = gerenciador.getFavoritas();
                    nomeLista = "Favoritas";
                    break;
                case 2:
                    listaParaOrdenar = gerenciador.getAssistidas();
                    nomeLista = "Já Assistidas";
                    break;
                case 3:
                    listaParaOrdenar = gerenciador.getParaAssistir();
                    nomeLista = "Que Deseja Assistir";
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            if (listaParaOrdenar != null && !listaParaOrdenar.isEmpty()) {
                selecionarCriterioOrdenacao(listaParaOrdenar, nomeLista);
            } else if (escolhaLista != 0) {
                System.out.println("A lista de " + nomeLista + " está vazia ou não foi selecionada para ordenar.");
            }
            if (escolhaLista != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        } while (escolhaLista != 0);
    }

    private static void selecionarCriterioOrdenacao(List<Serie> lista, String nomeLista) {
        System.out.println("\n--- Critérios de Ordenação para " + nomeLista + " ---");
        System.out.println("1. Por Nome (A-Z)");
        System.out.println("2. Por Avaliação Geral (Maior para Menor)");
        System.out.println("3. Por Status (Em Exibição, Finalizada, etc.)");
        System.out.println("4. Por Data de Estreia (Mais Antiga para Mais Recente)");
        System.out.println("0. Voltar");
        System.out.print("Escolha um critério: [0-4]: ");
        int opcaoOrdenacao = obterOpcaoUsuario();

        switch (opcaoOrdenacao) {
            case 1:
                gerenciador.ordenarListaPorNome(lista);
                break;
            case 2:
                gerenciador.ordenarListaPorAvaliacaoGeral(lista);
                break;
            case 3:
                gerenciador.ordenarListaPorStatus(lista);
                break;
            case 4:
                gerenciador.ordenarListaPorDataEstreia(lista);
                break;
            case 0:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
        if (opcaoOrdenacao != 0) {
            System.out.println("\n--- Lista de " + nomeLista + " Após Ordenação ---");
            if (nomeLista.equals("Favoritas")) gerenciador.exibirListaFavoritas(false);
            else if (nomeLista.equals("Já Assistidas")) gerenciador.exibirListaAssistidas(false);
            else if (nomeLista.equals("Que Deseja Assistir")) gerenciador.exibirListaParaAssistir(false);
        }
    }
}