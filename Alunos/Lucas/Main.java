package com.serie;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SerieService service = new SerieService();
        Usuario usuario;

        try {
            usuario = Persistencia.carregarUsuario();
            if (usuario == null) {
                System.out.print("Digite seu nome ou apelido: ");
                String nomeUsuario = scanner.nextLine();
                usuario = new Usuario(nomeUsuario);
            } else {
                System.out.println("Bem-vindo de volta, " + usuario.getNome() + "!");
            }

            System.out.println("Olá, " + usuario.getNome() + "! Bem-vindo ao sistema de series do Lucas! ");

            boolean executando = true;

            while (executando) {
                System.out.println("\n===== MENU PRINCIPAL =====");
                System.out.println("1 - Buscar e listar séries");
                System.out.println("2 - Exibir minhas listas");
                System.out.println("3 - Sair");
                System.out.print("Escolha uma opção: ");
                String escolha = scanner.nextLine();

                switch (escolha) {
                    case "1":
                        System.out.print("\nDigite o nome da série para buscar: ");
                        String nomeBusca = scanner.nextLine();
                        List<Serie> resultados = service.buscarSeriePorNome(nomeBusca);

                        if (resultados.isEmpty()) {
                            System.out.println("Nenhuma série encontrada.");
                        } else {
                            System.out.println("\nSéries encontradas:");
                            for (int i = 0; i < resultados.size(); i++) {
                                System.out.println("\n[" + i + "]\n" + resultados.get(i));
                            }

                            System.out.print("\nEscolha o número da série para adicionar a uma lista ou digite -1 para cancelar: ");
                            try {
                                int escolhaSerie = Integer.parseInt(scanner.nextLine());

                                if (escolhaSerie >= 0 && escolhaSerie < resultados.size()) {
                                    Serie selecionada = resultados.get(escolhaSerie);

                                    System.out.println("\nAdicionar à:");
                                    System.out.println("1 - Favoritos");
                                    System.out.println("2 - Já assistidas");
                                    System.out.println("3 - Desejo assistir");
                                    System.out.print("Escolha a lista: ");
                                    String opcaoLista = scanner.nextLine();

                                    switch (opcaoLista) {
                                        case "1":
                                            usuario.adicionarFavorito(selecionada);
                                            System.out.println("Adicionada aos favoritos!");
                                            break;
                                        case "2":
                                            usuario.marcarComoAssistida(selecionada);
                                            System.out.println("Adicionada à lista de já assistidas!");
                                            break;
                                        case "3":
                                            usuario.adicionarDesejoAssistir(selecionada);
                                            System.out.println("Adicionada à lista de desejo assistir!");
                                            break;
                                        default:
                                            System.out.println("Opção inválida.");
                                    }
                                } else if (escolhaSerie != -1) {
                                    System.out.println("Índice inválido.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada inválida. Digite um número.");
                            }
                        }
                        break;

                    case "2":
                        System.out.println("\n===== Minhas Listas =====");
                        System.out.println("1 - Favoritos");
                        System.out.println("2 - Já assistidas");
                        System.out.println("3 - Desejo assistir");
                        System.out.print("Escolha uma lista para visualizar: ");
                        String verLista = scanner.nextLine();

                        List<Serie> listaExibida = switch (verLista) {
                            case "1" -> usuario.getFavoritos();
                            case "2" -> usuario.getJaAssistidas();
                            case "3" -> usuario.getDesejoAssistir();
                            default -> null;
                        };

                        if (listaExibida != null && !listaExibida.isEmpty()) {
                            System.out.println("\nDeseja ordenar a lista?");
                            System.out.println("1 - Nome alfabético");
                            System.out.println("2 - Nota geral");
                            System.out.println("3 - Status da série");
                            System.out.println("4 - Data de estreia");
                            System.out.print("Escolha o critério ou pressione Enter para pular: ");
                            String ordem = scanner.nextLine();

                            switch (ordem) {
                                case "1" -> usuario.ordenarLista(listaExibida, "nome");
                                case "2" -> usuario.ordenarLista(listaExibida, "nota");
                                case "3" -> usuario.ordenarLista(listaExibida, "status");
                                case "4" -> usuario.ordenarLista(listaExibida, "estreia");
                                default -> System.out.println("Sem ordenação aplicada.");
                            }

                            for (int i = 0; i < listaExibida.size(); i++) {
                                System.out.println("\n[" + i + "]\n" + listaExibida.get(i));
                            }

                            System.out.print("\nDeseja remover alguma? Digite o número ou -1 para sair: ");
                            try {
                                int remover = Integer.parseInt(scanner.nextLine());
                                if (remover >= 0 && remover < listaExibida.size()) {
                                    Serie serieRemovida = listaExibida.get(remover);
                                    if (verLista.equals("1")) usuario.removerFavorito(serieRemovida);
                                    if (verLista.equals("2")) usuario.removerAssistida(serieRemovida);
                                    if (verLista.equals("3")) usuario.removerDesejoAssistir(serieRemovida);
                                    System.out.println("Série removida da lista!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada inválida. Nenhuma série foi removida.");
                            }
                        } else if (listaExibida != null) {
                            System.out.println("Essa lista está vazia.");
                        } else {
                            System.out.println("Opção inválida.");
                        }
                        break;

                    case "3":
                        Persistencia.salvarUsuario(usuario);
                        System.out.println("Dados salvos com sucesso!");
                        System.out.println("Encerrando... Até logo, " + usuario.getNome() + "!");
                        executando = false;
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }

        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
