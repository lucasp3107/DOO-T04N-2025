package com.arthur.tvshowtracker.app;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ApiSerieTv {

    private static final HttpClient cliente = HttpClient.newHttpClient();
    private static final String URL_BASE = "https://api.tvmaze.com";

    public static Optional<JSONArray> buscarSeriesPorNome(String nomeBusca) {
        if (nomeBusca == null || nomeBusca.trim().isEmpty()) {
            System.out.println("A busca requer um nome de série válido.");
            return Optional.empty();
        }

        try {
            String nomeFormatado = URLEncoder.encode(nomeBusca.trim(), StandardCharsets.UTF_8);
            URI url = URI.create(URL_BASE + "/search/shows?q=" + nomeFormatado);

            HttpRequest requisicao = HttpRequest.newBuilder(url)
                    .GET()
                    .build();

            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

            if (resposta.statusCode() == 200) {
                return Optional.of(new JSONArray(resposta.body()));
            } else {
                System.err.println("Erro na requisição para '" + nomeBusca + "'. Código de resposta: " + resposta.statusCode());
                if (resposta.body() != null && !resposta.body().isEmpty()) {
                    System.err.println("Mensagem de erro da API: " + resposta.body());
                }
                return Optional.empty();
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao buscar série por nome ('" + nomeBusca + "'): " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<JSONObject> buscarSeriePorId(int id) {
        try {
            URI url = URI.create(URL_BASE + "/shows/" + id);

            HttpRequest requisicao = HttpRequest.newBuilder(url)
                    .GET()
                    .build();

            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

            if (resposta.statusCode() == 200) {
                return Optional.of(new JSONObject(resposta.body()));
            } else if (resposta.statusCode() == 404) {
                System.out.println("Série com ID " + id + " não encontrada na API.");
                return Optional.empty();
            } else {
                System.err.println("Erro na requisição para ID " + id + ". Código de resposta: " + resposta.statusCode());
                if (resposta.body() != null && !resposta.body().isEmpty()) {
                    System.err.println("Mensagem de erro da API: " + resposta.body());
                }
                return Optional.empty();
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao buscar série por ID (" + id + "): " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}