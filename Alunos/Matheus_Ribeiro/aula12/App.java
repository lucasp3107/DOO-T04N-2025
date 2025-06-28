import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherFunctionsGUI gui = new WeatherFunctionsGUI();
            gui.setVisible(true);
        });
    }
}
