package br.com.seunome.prova2tri; 

public class Main {
    public static void main(String[] args) {
        SerieManager serieManager = new SerieManager();
        TerminalUI ui = new TerminalUI(serieManager);
        ui.iniciar();
    }
}