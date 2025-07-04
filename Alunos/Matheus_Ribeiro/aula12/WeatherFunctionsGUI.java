import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WeatherFunctionsGUI extends JFrame {
    private JTextField apiKeyField;
    private JTextField cityField;
    private JButton searchButton;
    private JTextArea resultArea;

    public WeatherFunctionsGUI() {
        super("Consulta de Tempo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        inputPanel.add(new JLabel("Chave da API:"));
        apiKeyField = new JTextField();
        inputPanel.add(apiKeyField);

        inputPanel.add(new JLabel("Cidade:"));
        cityField = new JTextField();
        inputPanel.add(cityField);

        inputPanel.add(new JLabel());
        searchButton = new JButton("Buscar Tempo");
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> fetchWeather());
    }

    private void fetchWeather() {
        String apiKey = apiKeyField.getText().trim();
        String cityName = cityField.getText().trim();

        if (apiKey.isEmpty() || cityName.isEmpty()) {
            resultArea.setText("Por favor, preencha a chave da API e o nome da cidade.");
            return;
        }

        new Thread(() -> {
            try {
                String encodedCity = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
                String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                        + encodedCity + "?key=" + apiKey + "&unitGroup=metric&include=days,hours";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                SwingUtilities.invokeLater(() -> {
                    switch (response.statusCode()) {
                        case 200 -> {
                            Gson gson = new GsonBuilder().create();
                            WeatherData data = gson.fromJson(response.body(), WeatherData.class);

                            Day today = data.days.get(0);
                            Hour currentHour = getClosestHour(today.hours);

                            StringBuilder sb = new StringBuilder();
                            sb.append("Local: ").append(data.resolvedAddress).append("\n");
                            sb.append("Data: ").append(today.datetime).append("\n");
                            sb.append("Temperatura atual: ").append(currentHour.temp).append("°C\n");
                            sb.append("Máxima do dia: ").append(today.tempmax).append("°C\n");
                            sb.append("Mínima do dia: ").append(today.tempmin).append("°C\n");
                            sb.append("Umidade do ar: ").append(currentHour.humidity).append("%\n");
                            sb.append("Condição do tempo: ").append(currentHour.conditions).append("\n");
                            sb.append("Precipitação: ").append(currentHour.precip).append(" mm\n");
                            sb.append("Velocidade do vento: ").append(currentHour.windspeed).append(" km/h\n");
                            sb.append("Direção do vento: ").append(currentHour.winddir).append("°\n");

                            resultArea.setText(sb.toString());
                        }
                        case 401 -> resultArea.setText("Chave da API inválida (401).");
                        case 400 -> resultArea.setText("Requisição malformada ou cidade inválida (400).");
                        case 404 -> resultArea.setText("Cidade não encontrada (404).");
                        default -> resultArea.setText("Erro: código de status HTTP " + response.statusCode());
                    }
                });

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> resultArea.setText("Erro inesperado: " + ex.getMessage()));
            }
        }).start();
    }

    private Hour getClosestHour(List<Hour> hours) {
        LocalTime now = LocalTime.now();
        Hour closest = hours.get(0);
        int minDiff = Integer.MAX_VALUE;

        for (Hour h : hours) {
            try {
                LocalTime hourTime = LocalTime.parse(h.datetime);
                int diff = Math.abs(hourTime.getHour() - now.getHour());
                if (diff < minDiff) {
                    minDiff = diff;
                    closest = h;
                }
            } catch (Exception ignored) {
            }
        }
        return closest;
    }
}
