import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Funcoes funcao = new Funcoes();
        String nomeUsuario = funcao.getNomeUsuario();
        boolean menu = true;
        boolean subMenu = true;

        while (menu) {
            System.out.println("\n[1] Buscar Séries \n" + 
                                "[2] Exibir Todas Séries Buscadas \n" +
                                "[3] Gerenciar \"Favoritas\" \n" +
                                "[4] Gerenciar \"Assistir Depois\" \n" +
                                "[5] Gerenciar \"Ja Assistidas\" \n" +
                                "[0] Sair");

            try {
                int esc = scan.nextInt();
                scan.nextLine(); // limpa scan

                if (esc == 0) {
                    menu = false;
                    System.out.println("Tchau " + nomeUsuario + "! Encerrando o Sistema...");
                } else if (esc == 1) {
                    funcao.buscarSeries();
                } else if (esc == 2) {
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("\n[1] Exibir Séries \n" + 
                                "[2] Exibir Séries Ordenadas por Nome\n" +
                                "[3] Exibir Séries Ordenadas por Nota\n" +
                                "[4] Exibir Séries Ordenadas por Status\n" +
                                "[5] Exibir Séries Ordenadas por Data de Estreia\n" +
                                "[0] Sair");

                        try {
                            int subEsc = scan.nextInt();
                            scan.nextLine(); // limpa scan

                            if (subEsc == 0) {
                                subMenu = false;
                            } else if (subEsc == 1) {
                                funcao.exibirSeries();
                            } else if (subEsc == 2) {
                                funcao.OrdenarListaNome(funcao.getListaDeSeries());
                            } else if (subEsc == 3) {
                                funcao.OrdenarListaNota(funcao.getListaDeSeries());
                            } else if (subEsc == 4) {
                                funcao.OrdenarListaStatus(funcao.getListaDeSeries());
                            } else if (subEsc == 5) {
                                funcao.OrdenarPorData(funcao.getListaDeSeries());
                            } else {
                                System.out.println(nomeUsuario + ", essa opção é inválida.");
                            }
                        } catch (Exception e) {
                            System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                            scan.nextLine(); // limpa scan
                        }
                    }
                } else if (esc == 3) {
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("\n[1] Favoritar Notícia \n" + 
                                "[2] Desfavoritar Notícia \n" +
                                "[3] Exibir Séries Favoritas \n" +
                                "[4] Exibir Séries Favoritas Ordenadas Por Nome \n" +
                                "[5] Exibir Séries Favoritas Ordenadas Por Nota \n" +
                                "[6] Exibir Séries Favoritas Ordenadas Por Status \n" +
                                "[7] Exibir Séries Favoritas Ordenadas Por Data de Estreia \n" +
                                "[0] Sair");

                        try {
                            int subEsc = scan.nextInt();
                            scan.nextLine(); // limpa scan

                            if (subEsc == 0) {
                                subMenu = false;
                            } else if (subEsc == 1) {
                                funcao.favoritarSerie();
                            } else if (subEsc == 2) {
                                funcao.desfavoritarSerie();
                            } else if (subEsc == 3) {
                                funcao.exibirSeriesFavoritas();
                            } else if (subEsc == 4) {
                                funcao.OrdenarListaNome(funcao.getListaDeFavoritas());
                            } else if (subEsc == 5) {
                                funcao.OrdenarListaNota(funcao.getListaDeFavoritas());
                            } else if (subEsc == 6) {
                                funcao.OrdenarListaStatus(funcao.getListaDeFavoritas());
                            } else if (subEsc == 7) {
                                funcao.OrdenarPorData(funcao.getListaDeFavoritas());
                            } else {
                                System.out.println(nomeUsuario + ", essa opção é inválida.");
                            }
                        } catch (Exception e) {
                            System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                            scan.nextLine(); // limpa scan
                        }
                    }
                } else if (esc == 4) {
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("\n[1] Adicionar Notícia em \"Assistir Depois\" \n" + 
                                "[2] Remover Notícia em \"Assistir Depois\" \n" +
                                "[3] Exibir Séries em \"Assistir Depois\" \n" +
                                "[4] Exibir Séries em \"Assistir Depois\" Ordenadas Por Nome \n" +
                                "[5] Exibir Séries em \"Assistir Depois\" Ordenadas Por Nota \n" +
                                "[6] Exibir Séries em \"Assistir Depois\" Ordenadas Por Status \n" +
                                "[7] Exibir Séries em \"Assistir Depois\" Ordenadas Por Data de Estreia \n" +
                                "[0] Sair");
                        try {
                            int subEsc = scan.nextInt();
                            scan.nextLine(); // limpa scan

                            if (subEsc == 0) {
                                subMenu = false;
                            } else if (subEsc == 1) {
                                funcao.AdicionarAssistirDepois();
                            } else if (subEsc == 2) {
                                funcao.RemoverAssistirDepois();
                            } else if (subEsc == 3) {
                                funcao.exibirSeriesAssistirDepois();
                            } else if (subEsc == 4) {
                                funcao.OrdenarListaNome(funcao.getListaDeAssistirDepois());
                            } else if (subEsc == 5) {
                                funcao.OrdenarListaNota(funcao.getListaDeAssistirDepois());
                            } else if (subEsc == 6) {
                                funcao.OrdenarListaStatus(funcao.getListaDeAssistirDepois());
                            } else if (subEsc == 7) {
                                funcao.OrdenarPorData(funcao.getListaDeAssistirDepois());
                            } else {
                                System.out.println(nomeUsuario + ", essa opção é inválida.");
                            }
                        } catch (Exception e) {
                            System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                            scan.nextLine(); // limpa scan
                        }
                    }
                } else if (esc == 5) {
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("\n[1] Marcar Notícia como Ja Assistida \n" + 
                                "[2] Desmarcar Notícia como Ja Assistida \n" +
                                "[3] Exibir Séries Ja Assistidas \n" +
                                "[4] Exibir Séries Ja Assistidas Ordenadas Por Nome \n" +
                                "[5] Exibir Séries Ja Assistidas Ordenadas Por Nota \n" +
                                "[6] Exibir Séries Ja Assistidas Ordenadas Por Status \n" +
                                "[7] Exibir Séries Ja Assistidas Ordenadas Por Data de Estreia \n" +
                                "[0] Sair");

                        try {
                            int subEsc = scan.nextInt();
                            scan.nextLine(); // limpa scan

                            if (subEsc == 0) {
                                subMenu = false;
                            } else if (subEsc == 1) {
                                funcao.MarcarComoJaAssistida();
                            } else if (subEsc == 2) {
                                funcao.DesmarcarComoJaAssistida();
                            } else if (subEsc == 3) {
                                funcao.exibirSeriesJaAssistidas();
                            } else if (subEsc == 4) {
                                funcao.OrdenarListaNome(funcao.getListaDeJaAssistidas());
                            } else if (subEsc == 5) {
                                funcao.OrdenarListaNota(funcao.getListaDeJaAssistidas());
                            } else if (subEsc == 6) {
                                funcao.OrdenarListaStatus(funcao.getListaDeJaAssistidas());
                            } else if (subEsc == 7) {
                                funcao.OrdenarPorData(funcao.getListaDeJaAssistidas());
                            } else {
                                System.out.println(nomeUsuario + ", essa opção é inválida.");
                            }
                        } catch (Exception e) {
                            System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                            scan.nextLine(); // limpa scan
                        }
                    }
                } else {
                    System.out.println(nomeUsuario + ", essa opção é inválida.");
                }
            } catch (Exception e) {
                System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                scan.nextLine(); // limpa scan
            }
        }

        scan.close();
    }
}