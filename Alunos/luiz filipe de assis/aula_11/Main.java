package aula_11;
import java.util.*;
import java.util.stream.*;
  
public class Main {

    static class Produto {
        String nome;
        double preco;

        Produto(String nome, double preco) {
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
            return nome + " - R$" + preco;
        }
    }

    public static void main(String[] args) {

        // ATV1 - Lista de inteiros, retorna só os pares
        List<Integer> numeros = Arrays.asList(10, 33, 46, 51, 28, 7, 2, 89, 100);
        List<Integer> pares = numeros.stream()
                                     .filter(n -> n % 2 == 0)
                                     .collect(Collectors.toList());
        System.out.println("//ATV1 - Números pares:");
        System.out.println(pares);
        System.out.println();

        // ATV2 - Converter lista de nomes para maiúsculas
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                                           .map(String::toUpperCase)
                                           .collect(Collectors.toList());
        System.out.println("//ATV2 - Nomes em maiúsculas:");
        System.out.println(nomesMaiusculos);
        System.out.println();

        // ATV3 - Contar quantas vezes cada palavra aparece
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagemPalavras = palavras.stream()
                                                    .collect(Collectors.groupingBy(
                                                        palavra -> palavra,
                                                        Collectors.counting()
                                                    ));
        System.out.println("//ATV3 - Contagem de palavras:");
        System.out.println(contagemPalavras);
        System.out.println();

        // ATV4 - Filtrar produtos com preço > 100
        List<Produto> produtos = Arrays.asList(
            new Produto("Notebook", 2500.00),
            new Produto("Mouse", 45.00),
            new Produto("Teclado", 150.00),
            new Produto("Monitor", 850.00)
        );
        List<Produto> produtosCaros = produtos.stream()
                                             .filter(p -> p.getPreco() > 100)
                                             .collect(Collectors.toList());
        System.out.println("//ATV4 - Produtos com preço maior que R$ 100,00:");
        produtosCaros.forEach(System.out::println);
        System.out.println();

        // ATV5 - Soma do preço total dos produtos
        double somaPrecos = produtos.stream()
                                   .mapToDouble(Produto::getPreco)
                                   .sum();
        System.out.println("//ATV5 - Soma total dos preços dos produtos:");
        System.out.println("R$ " + somaPrecos);
        System.out.println();

        // ATV6 - Ordenar lista por tamanho da palavra
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> linguagensOrdenadas = linguagens.stream()
                                                    .sorted(Comparator.comparingInt(String::length))
                                                    .collect(Collectors.toList());
        System.out.println("//ATV6 - Linguagens ordenadas pelo tamanho:");
        System.out.println(linguagensOrdenadas);
    }
}
