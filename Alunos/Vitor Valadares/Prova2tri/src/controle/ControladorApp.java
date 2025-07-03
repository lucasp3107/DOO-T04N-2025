package controle;

import modelo.*;
import servico.*;
import java.util.*;
import java.io.*;

public class ControladorApp {
    private Scanner scanner = new Scanner(System.in);
    private SerieServico serieServico = new SerieServico();
    private PersistenciaServico persistenciaServico = new PersistenciaServico();
    private Usuario usuario;

    public void iniciar() {
        usuario = persistenciaServico.carregarUsuario();
        if (usuario == null) {
            System.out.print("Digite seu nome ou apelido: ");
            usuario = new Usuario(scanner.nextLine());
        }
        menuPrincipal();
    }

    private void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n1 - Buscar série");
            System.out.println("2 - Ver listas");
            System.out.println("3 - Salvar e sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: buscarSerie(); break;
                case 2: verListas(); break;
                case 3: persistenciaServico.salvarUsuario(usuario); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 3);
    }

    private void buscarSerie() {
        try {
            System.out.print("Nome da série: ");
            String nome = scanner.nextLine();
            Serie serie = serieServico.buscarSeriePorNome(nome); // corrigido aqui
            System.out.println("Encontrada: " + serie);
            System.out.println("Adicionar em: 1-Favoritos 2-Assistidos 3-Desejados 0-Nenhum");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha >= 1 && escolha <= 3) {
                usuario.getLista(TipoLista.values()[escolha - 1]).add(serie);
                System.out.println("Adicionada!");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void verListas() {
        for (TipoLista tipo : TipoLista.values()) {
            System.out.println("\n-- " + tipo + " --");
            for (Serie s : usuario.getLista(tipo)) {
                System.out.println(s);
            }
        }
    }
}
