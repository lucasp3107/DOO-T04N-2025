import com.minhas_series_tv.model.Serie;
import com.minhas_series_tv.model.Usuario;
import com.minhas_series_tv.service.SerieManager;
import com.minhas_series_tv.service.TVMazeApiService;
import com.minhas_series_tv.util.ConsoleInput;
import com.minhas_series_tv.util.ArquivoDataManager;
import com.minhas_series_tv.view.MenuView;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ArquivoDataManager dataManager = new ArquivoDataManager();
        MenuView menuView = new MenuView();

        Usuario usuario = dataManager.carregarUsuario();
        if (usuario == null) {
            String nome = menuView.pedirNomeUsuario();
            usuario = new Usuario(nome);
        }

        TVMazeApiService apiService = new TVMazeApiService();
        SerieManager serieManager = new SerieManager(usuario, apiService);

        menuView.exibirBoasVindas(usuario.getNomeOuApelido());

        int opcao = 0;
        do {
            try {
                opcao = menuView.exibirMenuPrincipal();

                switch (opcao) {
                    case 1:
                        String termoBusca = menuView.pedirTermoBusca();
                        if (termoBusca.isEmpty()) {
                            menuView.exibirMensagem("Termo de busca não pode ser vazio.");
                            break;
                        }
                        List<Serie> resultadosBusca = apiService.buscarSeriesPorNome(termoBusca);
                        if (resultadosBusca.isEmpty()) {
                            menuView.exibirMensagemNenhumaSerieEncontrada();
                            break;
                        }

                        int escolhaSerie;
                        do {
                            escolhaSerie = menuView.exibirResultadosBusca(resultadosBusca);
                            if (escolhaSerie > 0 && escolhaSerie <= resultadosBusca.size()) {
                                Serie serieSelecionada = resultadosBusca.get(escolhaSerie - 1);
                                menuView.exibirDetalhesSerie(serieSelecionada);
                                int opcaoDetalhes = menuView.exibirOpcoesSerieDetalhes();
                                boolean adicionado = false;
                                switch (opcaoDetalhes) {
                                    case 1:
                                        adicionado = serieManager.adicionarSerieFavoritos(serieSelecionada);
                                        menuView.exibirMensagem(adicionado ? "Série adicionada aos favoritos!" : "Série já está nos favoritos.");
                                        break;
                                    case 2:
                                        adicionado = serieManager.adicionarSerieAssistida(serieSelecionada);
                                        menuView.exibirMensagem(adicionado ? "Série marcada como assistida!" : "Série já está nas assistidas.");
                                        break;
                                    case 3:
                                        adicionado = serieManager.adicionarSerieDesejaAssistir(serieSelecionada);
                                        menuView.exibirMensagem(adicionado ? "Série adicionada à lista 'Desejo Assistir'!" : "Série já está na lista 'Desejo Assistir'.");
                                        break;
                                    case 4:
                                        break;
                                }
                            }
                        } while (escolhaSerie != 0);
                        break;

                    case 2:
                        gerenciarLista(serieManager.getListaFavoritos(), "Séries Favoritas", serieManager, "favoritos", menuView);
                        break;
                    case 3:
                        gerenciarLista(serieManager.getListaAssistidas(), "Séries Já Assistidas", serieManager, "assistidas", menuView);
                        break;
                    case 4:
                        gerenciarLista(serieManager.getListaDesejoAssistir(), "Séries Que Desejo Assistir", serieManager, "desejoAssistir", menuView);
                        break;
                    case 5:
                        menuView.exibirMensagem("Saindo do programa. Até mais, " + usuario.getNomeOuApelido() + "!");
                        break;
                    default:
                        menuView.exibirMensagem("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("\n*** Ocorreu um erro inesperado: " + e.getMessage());
                menuView.exibirMensagem("Por favor, tente novamente ou verifique sua conexão com a internet.");
            }
        } while (opcao != 5);

        dataManager.salvarUsuario(usuario);
        ConsoleInput.fecharScanner();
    }

    private static void gerenciarLista(List<Serie> lista, String titulo, SerieManager serieManager, String tipoLista, MenuView menuView) {
        if (lista.isEmpty()) {
            menuView.exibirMensagem("\n" + titulo + ": (Vazia)");
            return;
        }

        int opcaoOrdenacao = menuView.exibirMenuOrdenacao();
        String criterioOrdenacao = "";
        switch (opcaoOrdenacao) {
            case 1: criterioOrdenacao = "nome"; break;
            case 2: criterioOrdenacao = "nota"; break;
            case 3: criterioOrdenacao = "status"; break;
            case 4: criterioOrdenacao = "dataestreia"; break;
            case 0: break;
        }

        if (opcaoOrdenacao != 0) {
            serieManager.ordenarLista(lista, criterioOrdenacao);
            menuView.exibirMensagem("Lista ordenada por " + criterioOrdenacao + ".");
        } else {
            menuView.exibirMensagem("Exibindo lista sem ordenação específica.");
        }

        int escolhaRemocao;
        do {
            escolhaRemocao = menuView.exibirListaSeries(titulo, lista);
            if (escolhaRemocao > 0 && escolhaRemocao <= lista.size()) {
                Serie serieParaRemover = lista.get(escolhaRemocao - 1);
                boolean removido = false;
                switch (tipoLista) {
                    case "favoritos": removido = serieManager.removerSerieFavoritos(serieParaRemover.getId()); break;
                    case "assistidas": removido = serieManager.removerSerieAssistida(serieParaRemover.getId()); break;
                    case "desejoAssistir": removido = serieManager.removerSerieDesejaAssistir(serieParaRemover.getId()); break;
                }
                if (removido) {
                    menuView.exibirMensagem("Série '" + serieParaRemover.getNome() + "' removida de " + titulo + ".");
                    lista.remove(serieParaRemover);
                    if(lista.isEmpty()) {
                        menuView.exibirMensagem("\n" + titulo + ": (Vazia)");
                        break;
                    }
                } else {
                    menuView.exibirMensagem("Não foi possível remover a série.");
                }
            } else if (escolhaRemocao == 0) {
            } else {
                menuView.exibirMensagem("Opção inválida. Digite um número válido ou 0 para voltar.");
            }
        } while (escolhaRemocao != 0);
    }
}
