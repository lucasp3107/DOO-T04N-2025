package com.serie;

import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private String dataEstreia;
    private String dataFim;
    private String emissora;

    public Serie(JsonObject json) {
        try {
            this.nome = json.has("name") && !json.get("name").isJsonNull()
                ? json.get("name").getAsString()
                : "Sem nome";

            this.idioma = json.has("language") && !json.get("language").isJsonNull()
                ? json.get("language").getAsString()
                : "Desconhecido";

            this.generos = new Gson().fromJson(
                json.getAsJsonArray("genres"),
                new TypeToken<List<String>>() {}.getType()
            );

            JsonObject rating = json.has("rating") ? json.getAsJsonObject("rating") : null;
            this.nota = (rating != null && rating.has("average") && !rating.get("average").isJsonNull())
                ? rating.get("average").getAsDouble()
                : 0.0;

            this.status = json.has("status") && !json.get("status").isJsonNull()
                ? json.get("status").getAsString()
                : "Desconhecido";

            this.dataEstreia = json.has("premiered") && !json.get("premiered").isJsonNull()
                ? json.get("premiered").getAsString()
                : "Indefinida";

            this.dataFim = json.has("ended") && !json.get("ended").isJsonNull()
                ? json.get("ended").getAsString()
                : "Ainda em exibição";

            JsonObject network = json.has("network") && !json.get("network").isJsonNull()
                ? json.getAsJsonObject("network")
                : null;

            this.emissora = (network != null && network.has("name"))
                ? network.get("name").getAsString()
                : "Desconhecida";

        } catch (Exception e) {
            System.out.println("Erro ao processar uma série: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format(
            "Nome: %s\nIdioma: %s\nGêneros: %s\nNota geral: %.1f\nStatus: %s\nEstreia: %s\nFim: %s\nEmissora: %s\n",
            nome, idioma, generos, nota, status, dataEstreia, dataFim, emissora
        );
    }

    // Getters para ordenação e uso em outras partes do sistema
    public String getNome() { return nome; }
    public double getNota() { return nota; }
    public String getStatus() { return status; }
    public String getDataEstreia() { return dataEstreia != null ? dataEstreia : ""; }
}
