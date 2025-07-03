package br.com.com.utilidades;

import br.com.com.calculadora.exception.EntradaInvalidaException;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraGrafica extends JFrame {

    private JTextField campoExibicao, campoValorA, campoValorB;
    private JButton botaoAdicao, botaoSubtracao, botaoMultiplicacao, botaoDivisao, botaoResetar;
    private JLabel rotuloExibicao;

    public CalculadoraGrafica() {
        setTitle("Calculadora Gráfica");
        setSize(320, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        campoValorA = new JTextField(10);
        campoValorB = new JTextField(10);
        campoExibicao = new JTextField(10);
        campoExibicao.setEditable(false);
        rotuloExibicao = new JLabel("Resultado:");

        botaoAdicao = new JButton("+");
        botaoSubtracao = new JButton("-");
        botaoMultiplicacao = new JButton("×");
        botaoDivisao = new JButton("÷");
        botaoResetar = new JButton("Limpar");

        add(new JLabel("Valor 1:"));
        add(campoValorA);
        add(new JLabel("Valor 2:"));
        add(campoValorB);
        add(botaoAdicao);
        add(botaoSubtracao);
        add(botaoMultiplicacao);
        add(botaoDivisao);
        add(botaoResetar);
        add(rotuloExibicao);
        add(campoExibicao);

        botaoAdicao.addActionListener(new OperacaoHandler("+"));
        botaoSubtracao.addActionListener(new OperacaoHandler("-"));
        botaoMultiplicacao.addActionListener(new OperacaoHandler("×"));
        botaoDivisao.addActionListener(new OperacaoHandler("÷"));
        botaoResetar.addActionListener(e -> {
            campoValorA.setText("");
            campoValorB.setText("");
            campoExibicao.setText("");
        });
    }

    private class OperacaoHandler implements ActionListener {
        private String tipoOperacao;

        public OperacaoHandler(String tipoOperacao) {
            this.tipoOperacao = tipoOperacao;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                double numero1 = Double.parseDouble(campoValorA.getText());
                double numero2 = Double.parseDouble(campoValorB.getText());
                double resultado = 0;

                switch (tipoOperacao) {
                    case "+":
                        resultado = numero1 + numero2;
                        break;
                    case "-":
                        resultado = numero1 - numero2;
                        break;
                    case "×":
                        resultado = numero1 * numero2;
                        break;
                    case "÷":
                        if (numero2 == 0) throw new EntradaInvalidaException("Divisão por zero não permitida");
                        resultado = numero1 / numero2;
                        break;
                }

                campoExibicao.setText(String.valueOf(resultado));
            } catch (EntradaInvalidaException ex) {
                campoExibicao.setText("Erro: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                campoExibicao.setText("Erro: Entrada inválida");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraGrafica().setVisible(true));
    }
}