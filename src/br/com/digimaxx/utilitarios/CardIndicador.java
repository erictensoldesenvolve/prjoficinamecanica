package br.com.digimaxx.utilitarios;

import javax.swing.*;
import java.awt.*;

public class CardIndicador extends JPanel {

    private JLabel titulo;
    private JLabel valor;

    public CardIndicador(String tituloCard) {
        setBackground(new Color(246, 31, 65));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 90));
        setOpaque(true);

        titulo = new JLabel(tituloCard);
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 14));

        valor = new JLabel("0");
        valor.setForeground(Color.white);
        valor.setFont(new Font("Tahoma", Font.BOLD, 22));
        valor.setHorizontalAlignment(SwingConstants.RIGHT);

        add(titulo, BorderLayout.NORTH);
        add(valor, BorderLayout.CENTER);
    }

    public void setValor(String v) {
        valor.setText(v);
    }
}
