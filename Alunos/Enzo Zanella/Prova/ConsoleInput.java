import java.util.Scanner;

public class ConsoleInput {
    private static Scanner scanner = new Scanner(System.in);

    public static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int lerInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }

    public static int lerIntComLimite(String prompt, int min, int max) {
        while (true) {
            int valor = lerInt(prompt);
            if (valor >= min && valor <= max) {
                return valor;
            } else {
                System.out.println("Opção inválida. Por favor, digite um número entre " + min + " e " + max + ".");
            }
        }
    }

    public static void fecharScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}