package app;

import controller.Sistema;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome ou apelido: ");
        String nomeUsuario = scanner.nextLine();

        Sistema sistema = new Sistema(nomeUsuario);

        while (true) {
            System.out.println("Usuário: " + nomeUsuario);
            System.out.println("1. Buscar série e adicionar à lista");
            System.out.println("2. Exibir listas");
            System.out.println("3. Ordenar listas");
            System.out.println("4. Remover série de uma lista");
            System.out.println("0. Sair");
            String opcao = scanner.nextLine();
            if (opcao.equals("1")) {
                sistema.buscarEAdicionarSerie();
            } else if (opcao.equals("2")) {
                sistema.exibirListas();
            } else if (opcao.equals("3")) {
                sistema.ordenarListas();
            } else if (opcao.equals("4")) {
                sistema.removerSerie();
            } else if (opcao.equals("0")) {
                break;
            }
        }
        scanner.close();
    }
}
