// WeatherApp.java (Classe principal)
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherApp extends JFrame {
    private WeatherService weatherService;
    private JTextField cityField;
    private JButton searchButton;
    private WeatherPanel weatherPanel;
    private JLabel statusLabel;

    public WeatherApp() {
        weatherService = new WeatherService();

        setTitle("Aplicativo de Clima - Visual Crossing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        initializeComponents();
        setupLayout();
        setupEventListeners();
    }

    private void initializeComponents() {
        cityField = new JTextField(20);
        searchButton = new JButton("Buscar Clima");
        weatherPanel = new WeatherPanel();
        statusLabel = new JLabel("Digite o nome de uma cidade e clique em Buscar");

        cityField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);

        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Cidade: "));
        searchPanel.add(cityField);
        searchPanel.add(searchButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(weatherPanel, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setupEventListeners() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchWeather();
            }
        });

        cityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchWeather();
            }
        });
    }

    private void searchWeather() {
        String city = cityField.getText().trim();
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite o nome de uma cidade.");
            return;
        }

        statusLabel.setText("Buscando informações do clima...");
        searchButton.setText("Buscando...");
        searchButton.setEnabled(false);

        SwingWorker<WeatherData, Void> worker = new SwingWorker<WeatherData, Void>() {
            @Override
            protected WeatherData doInBackground() throws Exception {
                return weatherService.getWeatherData(city);
            }

            @Override
            protected void done() {
                try {
                    WeatherData weather = get();
                    weatherPanel.displayWeatherData(weather);
                    statusLabel.setText("Última atualização: " + java.time.LocalDateTime.now());
                } catch (Exception e) {
                    statusLabel.setText("Erro na busca");
                    JOptionPane.showMessageDialog(WeatherApp.this,
                            "Erro ao buscar dados: " + e.getMessage());
                } finally {
                    searchButton.setText("Buscar Clima");
                    searchButton.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        // Corrigido aqui:
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new WeatherApp().setVisible(true);
        });
    }
}
