package PROVA_2TRI.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class InputUtilities {
    public static int GetId(Scanner sc)
    {
        String id = sc.nextLine();
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("Id inválido, por favor digite um número.");
            return GetId(sc);
        }
    }
    public static String GetSorN(Scanner sc)
    {
        String letter = sc.nextLine();
        if (letter.equalsIgnoreCase("S") || letter.equalsIgnoreCase("N")) {
            return letter.toUpperCase();
        } else {
            System.out.println("Entrada inválida, por favor digite 'S' ou 'N'.");
            return GetSorN(sc);
        }
    }
    public static LocalDate getLocalDate(Scanner sc, DateTimeFormatter formatter)
    {
        System.out.println("Digite a data da noticia no formato dd/MM/yyyy: ");
        String date = sc.nextLine();
        LocalDate localDate;
        if(date.isEmpty()){
            return getLocalDate(sc,formatter);
        }
        try {
            localDate = LocalDate.parse(date, formatter);
            return localDate;
        } catch (Exception e) {
            System.out.println("Data inválida, por favor digite novamente.");
            return getLocalDate(sc,formatter);
        }
    }
}
