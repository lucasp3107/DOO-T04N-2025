import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Functions funcao = new Functions();
        String nomeUsuario = funcao.getUsername();
        boolean menu = true;
        boolean subMenu = true;

        while (menu) {
            System.out.println("\n[1] Buscar Séries \n" +
                               "[2] Exibir Todas Séries Buscadas \n" +
                               "[3] Gerenciar \"Favoritas\" \n" +
                               "[4] Gerenciar \"Assistir Depois\" \n" +
                               "[5] Gerenciar \"Já Assistidas\" \n" +
                               "[0] Sair");

            try {
                int esc = scan.nextInt();
                scan.nextLine();

                if (esc == 0) {
                    menu = false;
                    System.out.println("Tchau " + nomeUsuario + "! Encerrando o Sistema...");
                } else if (esc == 1) {
                    funcao.searchSeries();
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
                            scan.nextLine();

                            switch(subEsc) {
                                case 0 -> subMenu = false;
                                case 1 -> funcao.showList("All");
                                case 2 -> funcao.sortByName(funcao.getList("All"));
                                case 3 -> funcao.sortByRating(funcao.getList("All"));
                                case 4 -> funcao.sortByStatus(funcao.getList("All"));
                                case 5 -> funcao.sortByDate(funcao.getList("All"));
                                default -> System.out.println(nomeUsuario + ", essa opção é inválida.");
                            }
                        } catch (Exception e) {
                            System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                            scan.nextLine();
                        }
                    }
                } else if (esc == 3) {
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("\n[1] Favoritar Série \n" +
                                           "[2] Desfavoritar Série \n" +
                                           "[3] Exibir Séries Favoritas \n" +
                                           "[4] Exibir Favoritas Ordenadas por Nome \n" +
                                           "[5] Exibir Favoritas Ordenadas por Nota \n" +
                                           "[6] Exibir Favoritas Ordenadas por Status \n" +
                                           "[7] Exibir Favoritas Ordenadas por Data de Estreia \n" +
                                           "[0] Sair");

                        try {
                            int subEsc = scan.nextInt();
                            scan.nextLine();

                            switch(subEsc) {
                                case 0 -> subMenu = false;
                                case 1 -> funcao.addToList("Favorites");
                                case 2 -> funcao.removeFromList("Favorites");
                                case 3 -> funcao.showList("Favorites");
                                case 4 -> funcao.sortByName(funcao.getList("Favorites"));
                                case 5 -> funcao.sortByRating(funcao.getList("Favorites"));
                                case 6 -> funcao.sortByStatus(funcao.getList("Favorites"));
                                case 7 -> funcao.sortByDate(funcao.getList("Favorites"));
                                default -> System.out.println(nomeUsuario + ", essa opção é inválida.");
                            }
                        } catch (Exception e) {
                            System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                            scan.nextLine();
                        }
                    }
                } else if (esc == 4) {
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("\n[1] Adicionar em \"Assistir Depois\" \n" +
                                           "[2] Remover de \"Assistir Depois\" \n" +
                                           "[3] Exibir \"Assistir Depois\" \n" +
                                           "[4] Exibir \"Assistir Depois\" Ordenadas por Nome \n" +
                                           "[5] Exibir \"Assistir Depois\" Ordenadas por Nota \n" +
                                           "[6] Exibir \"Assistir Depois\" Ordenadas por Status \n" +
                                           "[7] Exibir \"Assistir Depois\" Ordenadas por Data de Estreia \n" +
                                           "[0] Sair");
                        try {
                            int subEsc = scan.nextInt();
                            scan.nextLine();

                            switch(subEsc) {
                                case 0 -> subMenu = false;
                                case 1 -> funcao.addToList("WatchLater");
                                case 2 -> funcao.removeFromList("WatchLater");
                                case 3 -> funcao.showList("WatchLater");
                                case 4 -> funcao.sortByName(funcao.getList("WatchLater"));
                                case 5 -> funcao.sortByRating(funcao.getList("WatchLater"));
                                case 6 -> funcao.sortByStatus(funcao.getList("WatchLater"));
                                case 7 -> funcao.sortByDate(funcao.getList("WatchLater"));
                                default -> System.out.println(nomeUsuario + ", essa opção é inválida.");
                            }
                        } catch (Exception e) {
                            System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                            scan.nextLine();
                        }
                    }
                } else if (esc == 5) {
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("\n[1] Marcar como Já Assistida \n" +
                                           "[2] Desmarcar como Já Assistida \n" +
                                           "[3] Exibir Já Assistidas \n" +
                                           "[4] Exibir Já Assistidas Ordenadas por Nome \n" +
                                           "[5] Exibir Já Assistidas Ordenadas por Nota \n" +
                                           "[6] Exibir Já Assistidas Ordenadas por Status \n" +
                                           "[7] Exibir Já Assistidas Ordenadas por Data de Estreia \n" +
                                           "[0] Sair");

                        try {
                            int subEsc = scan.nextInt();
                            scan.nextLine();

                            switch(subEsc) {
                                case 0 -> subMenu = false;
                                case 1 -> funcao.addToList("Watched");
                                case 2 -> funcao.removeFromList("Watched");
                                case 3 -> funcao.showList("Watched");
                                case 4 -> funcao.sortByName(funcao.getList("Watched"));
                                case 5 -> funcao.sortByRating(funcao.getList("Watched"));
                                case 6 -> funcao.sortByStatus(funcao.getList("Watched"));
                                case 7 -> funcao.sortByDate(funcao.getList("Watched"));
                                default -> System.out.println(nomeUsuario + ", essa opção é inválida.");
                            }
                        } catch (Exception e) {
                            System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                            scan.nextLine();
                        }
                    }
                } else {
                    System.out.println(nomeUsuario + ", essa opção é inválida.");
                }
            } catch (Exception e) {
                System.out.println(nomeUsuario + ", por favor, insira apenas números inteiros.");
                scan.nextLine();
            }
        }

        scan.close();
    }
}
