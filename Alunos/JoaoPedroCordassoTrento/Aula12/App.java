import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        Funcoes funcao = new Funcoes();
        Boolean menu = true;
        while (menu) {
            try {
                System.out.println("\n[1] Buscar tempo por cidade \n[0] Sair \nSua escolha: ");
                int esc = scan.nextInt();
                if (esc == 0) {
                    menu = false;
                    System.out.println("Sistema encerrado.");
                } else if (esc == 1) {
                    funcao.buscarTempo();
                } else {
                    System.out.println("Opção invalida.");
                }
            } catch (Exception e) {
                System.out.println("Por favor, digite apenas números inteiros.");
            }
        }
    }
}
