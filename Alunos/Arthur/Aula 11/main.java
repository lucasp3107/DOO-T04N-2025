package aula11;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // ATV1 
        List<Integer> numeros = Arrays.asList(3, 8, 15, 22, 7, 10, 33, 40, 57, 64);
        List<Integer> pares = numeros.stream()
                                     .filter(n -> n % 2 == 0)
                                     .collect(Collectors.toList());
        System.out.println("ATV1 - Números pares: " + pares);

        // ATV2 
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                                            .map(String::toUpperCase)
                                            .collect(Collectors.toList());
        System.out.println("ATV2 - Nomes em MAIÚSCULO: " + nomesMaiusculos);

        // ATV3 
        List<String> palavras = Arrays.asList(
            "se", "talvez", "hoje", "sábado", "se", "quarta", "sábado"
        );
        Map<String, Long> frequencia = palavras.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("ATV3 - Frequência de palavras: " + frequencia);

        // ATV4 
        List<Produto> produtos = Arrays.asList(
            new Produto("Camiseta", 79.90),
            new Produto("Headset Gamer", 249.90),
            new Produto("Mouse", 129.90),
            new Produto("Caneca", 39.90)
        );
        List<Produto> caros = produtos.stream()
                                      .filter(p -> p.getPreco() > 100.0)
                                      .collect(Collectors.toList());
        System.out.println("ATV4 - Produtos acima de R$100: " + caros);

        // ATV5 

        double total = produtos.stream()
                               .mapToDouble(Produto::getPreco)
                               .sum();
        System.out.printf("ATV5 - Soma dos preços: R$ %.2f%n", total);

        // ATV6 

        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadasPorTamanho = linguagens.stream()
            .sorted(Comparator.comparingInt(String::length))
            .collect(Collectors.toList());
        System.out.println("ATV6 - Linguagens ordenadas por tamanho: " + ordenadasPorTamanho);
    }

    // Inner class Produto usada em ATV4 e ATV5
    public static class Produto {
        private final String nome;
        private final double preco;

        public Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }
        public String getNome() {
            return nome;
        }
        public double getPreco() {
            return preco;
        }
        @Override
        public String toString() {
            return String.format("%s (R$ %.2f)", nome, preco);
        }
    }
}
