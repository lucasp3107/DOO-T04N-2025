//WeatherPanel.java
import javax.swing.*;
import java.awt.*;

public class WeatherPanel extends JPanel {
 
 public WeatherPanel() {
     setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
     setBorder(BorderFactory.createTitledBorder("Informações do Clima"));
     setPreferredSize(new Dimension(550, 300));
 }
 
 public void displayWeatherData(WeatherData weather) {
     removeAll();
     
     // Criar painel com informações formatadas
     JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 5));
     infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     
     // Localização
     addInfoRow(infoPanel, "📍 Localização:", weather.getLocation());
     
     // Temperatura atual
     addInfoRow(infoPanel, "🌡️ Temperatura Atual:", 
         WeatherUtils.formatTemperature(weather.getCurrentTemp()));
     
     // Temperaturas máxima e mínima
     addInfoRow(infoPanel, "🔺 Temperatura Máxima:", 
         WeatherUtils.formatTemperature(weather.getMaxTemp()));
     addInfoRow(infoPanel, "🔻 Temperatura Mínima:", 
         WeatherUtils.formatTemperature(weather.getMinTemp()));
     
     // Umidade
     addInfoRow(infoPanel, "💧 Umidade:", 
         WeatherUtils.formatHumidity(weather.getHumidity()));
     
     // Condições
     addInfoRow(infoPanel, "☁️ Condições:", weather.getConditions());
     
     // Precipitação
     addInfoRow(infoPanel, "🌧️ Precipitação:", 
         WeatherUtils.formatPrecipitation(weather.getPrecipitation()));
     
     // Vento
     String windDir = WeatherUtils.getWindDirection(weather.getWindDirection());
     addInfoRow(infoPanel, "💨 Vento:", 
         WeatherUtils.formatWindSpeed(weather.getWindSpeed()) + " " + windDir);
     
     add(infoPanel);
     
     // Descrição
     if (weather.getDescription() != null && !weather.getDescription().isEmpty()) {
         JTextArea descArea = new JTextArea(weather.getDescription());
         descArea.setEditable(false);
         descArea.setWrapStyleWord(true);
         descArea.setLineWrap(true);
         descArea.setOpaque(false);
         descArea.setBorder(BorderFactory.createTitledBorder("Descrição"));
         add(descArea);
     }
     
     revalidate();
     repaint();
 }
 
 private void addInfoRow(JPanel panel, String label, String value) {
     JLabel labelComp = new JLabel(label);
     JLabel valueComp = new JLabel(value);
     
     labelComp.setFont(new Font("Arial", Font.BOLD, 12));
     valueComp.setFont(new Font("Arial", Font.PLAIN, 12));
     
     panel.add(labelComp);
     panel.add(valueComp);
 }
}