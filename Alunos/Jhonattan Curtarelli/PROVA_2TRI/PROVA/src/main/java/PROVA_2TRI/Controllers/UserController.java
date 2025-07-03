package PROVA_2TRI.Controllers;

import PROVA_2TRI.Entities.User;

import java.util.Scanner;

public class UserController {
    private final java.util.Scanner sc;
    public UserController(Scanner sc) {
        this.sc = sc;
    }
    public User createUser() {
        System.out.print("Digite seu nome: ");
        String name = sc.nextLine();
        System.out.print("Digite seu apelido: ");
        String nickName = sc.nextLine();
        return CreateUser(name, nickName);
    }
    public User CreateUser(String name, String nickName)
    {
        return new User(name, nickName);
    }
}
