package application;

import model.Usuario;
import service.SerieManager;
import util.JsonUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite seu nome ou apelido: ");
        String nome = sc.nextLine();

        Usuario usuario = JsonUtil.carregar();
        if (usuario == null) {
            usuario = new Usuario(nome);
        } else {
            System.out.println("Bem-vindo de volta, " + usuario.getNome() + "!");
        }

        SerieManager gerenciador = new SerieManager(usuario);
        gerenciador.menu();

        JsonUtil.salvar(usuario);
        sc.close();
    }
}
