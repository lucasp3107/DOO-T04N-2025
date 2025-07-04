public class ClimaParser {
    public static Clima parse(String json, String cidade) {
        try {
            String data = extract(json, "\"datetime\":\"", "\"");
            double tempAtual = Double.parseDouble(extract(json, "\"temp\":", ","));
            double tempMax = Double.parseDouble(extract(json, "\"tempmax\":", ","));
            double tempMin = Double.parseDouble(extract(json, "\"tempmin\":", ","));
            double umidade = Double.parseDouble(extract(json, "\"humidity\":", ","));
            String condicao = extract(json, "\"conditions\":\"", "\"");
            double precipitacao = Double.parseDouble(extract(json, "\"precip\":", ","));
            double vento = Double.parseDouble(extract(json, "\"windspeed\":", ","));
            String direcaoVento = extract(json, "\"winddir\":", ",");
            String direcao = getDirecaoVento(direcaoVento);

            return new Clima(cidade, data, tempAtual, tempMax, tempMin,
                             umidade, condicao, precipitacao, vento, direcao);
        } catch (Exception e) {
            System.out.println("Erro ao processar JSON: " + e.getMessage());
            return null;
        }
    }

    private static String extract(String json, String keyStart, String keyEnd) {
        int start = json.indexOf(keyStart);
        if (start == -1) return "";
        start += keyStart.length();
        int end = json.indexOf(keyEnd, start);
        return json.substring(start, end);
    }

    private static String getDirecaoVento(String grausStr) {
        try {
            double graus = Double.parseDouble(grausStr);
            String[] direcoes = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
            return direcoes[(int)Math.round(((graus % 360) / 45)) % 8];
        } catch (Exception e) {
            return "Desconhecida";
        }
    }
}
