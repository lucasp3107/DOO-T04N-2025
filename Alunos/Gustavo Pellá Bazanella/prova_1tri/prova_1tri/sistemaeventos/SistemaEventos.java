package sistemaeventos;

import java.util.*;

public class SistemaEventos {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void cadastrarCliente() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Email do cliente: ");
        String email = scanner.nextLine();
        clientes.add(new Cliente(nome, email));
        System.out.println("Cliente cadastrado com sucesso!\n");
    }

    public void cadastrarEvento() {
        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();
        System.out.print("Tipo (1 - Show, 2 - Congresso): ");
        int tipo = Integer.parseInt(scanner.nextLine());
        System.out.print("Data de início (yyyy-mm-dd): ");
        Date inicio = java.sql.Date.valueOf(scanner.nextLine());
        System.out.print("Data de fim (yyyy-mm-dd): ");
        Date fim = java.sql.Date.valueOf(scanner.nextLine());
        System.out.print("Preço diário: ");
        double preco = Double.parseDouble(scanner.nextLine());
        System.out.print("Máximo de participantes: ");
        int max = Integer.parseInt(scanner.nextLine());

        Evento evento;
        if (tipo == 1) {
            evento = new Show(nome, inicio, fim, preco, max);
        } else {
            evento = new Congresso(nome, inicio, fim, preco, max);
        }

        eventos.add(evento);
        System.out.println("Evento cadastrado com sucesso!\n");
    }

    public void listarEventos() {
        for (Evento e : eventos) {
            System.out.println("Evento: " + e.nome + " | Tipo: " + e.getTipo() + " | Dias: " + e.getDuracaoDias());
        }
        System.out.println();
    }

    public void listarShows() {
        System.out.println("Shows cadastrados:");
        for (Evento e : eventos) {
            if (e instanceof Show) {
                System.out.println("Nome: " + e.nome + " | Tipo: Show | Dias: " + e.getDuracaoDias());
            }
        }
        System.out.println();
    }

    public void listarClientes() {
        System.out.println("Clientes cadastrados:");
        for (Cliente c : clientes) {
            System.out.println("Nome: " + c.nome + " | Email: " + c.email);
        }
        System.out.println();
    }

    public void listarParticipantesEvento() {
        System.out.print("Nome do evento: ");
        String nomeEvento = scanner.nextLine();
        for (Evento e : eventos) {
            if (e.nome.equals(nomeEvento)) {
                System.out.println("Participantes do evento " + nomeEvento + ":");
                for (Cliente c : e.participantes) {
                    System.out.println("Nome: " + c.nome + " | Email: " + c.email);
                }
                return;
            }
        }
        System.out.println("Evento não encontrado.\n");
    }

    public void comprarIngresso() {
        System.out.print("Nome do evento: ");
        String nomeEvento = scanner.nextLine();
        System.out.print("Email do cliente: ");
        String email = scanner.nextLine();
        Cliente cliente = buscarClientePorEmail(email);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.\n");
            return;
        }
        System.out.print("Ingresso VIP? (true/false): ");
        boolean vip = Boolean.parseBoolean(scanner.nextLine());
        if (comprarIngresso(nomeEvento, cliente, vip)) {
            System.out.println("Ingresso comprado com sucesso!\n");
        } else {
            System.out.println("Falha ao comprar ingresso.\n");
        }
    }

    public Cliente buscarClientePorEmail(String email) {
        for (Cliente c : clientes) {
            if (c.email.equals(email)) {
                return c;
            }
        }
        return null;
    }

    public boolean comprarIngresso(String nomeEvento, Cliente cliente, boolean vip) {
        for (Evento e : eventos) {
            if (e.nome.equals(nomeEvento)) {
                if (e instanceof Show && vip) {
                    return ((Show) e).adicionarVIP(cliente);
                } else {
                    return e.adicionarParticipante(cliente);
                }
            }
        }
        return false;
    }

    public void utilizarIngresso() {
        System.out.print("Nome do evento: ");
        String nomeEvento = scanner.nextLine();
        System.out.print("Email do cliente: ");
        String email = scanner.nextLine();
        Cliente cliente = buscarClientePorEmail(email);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.\n");
            return;
        }

        for (Evento e : eventos) {
            if (e.nome.equals(nomeEvento) && e.participantes.contains(cliente)) {
                System.out.println(cliente.nome + " está participando do evento: " + nomeEvento + "\n");
                return;
            }
        }
        System.out.println("Ingresso inválido ou não comprado.\n");
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("==== Menu ====");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Cadastrar evento");
            System.out.println("3. Listar eventos");
            System.out.println("4. Listar shows cadastrados");
            System.out.println("5. Listar clientes cadastrados");
            System.out.println("6. Listar participantes de um evento");
            System.out.println("7. Comprar ingresso");
            System.out.println("8. Utilizar ingresso");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarEvento();
                case 3 -> listarEventos();
                case 4 -> listarShows();
                case 5 -> listarClientes();
                case 6 -> listarParticipantesEvento();
                case 7 -> comprarIngresso();
                case 8 -> utilizarIngresso();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!\n");
            }
        } while (opcao != 0);
    }

    public static void main(String[] args) {
        SistemaEventos sistema = new SistemaEventos();
        sistema.menu();
    }
}
