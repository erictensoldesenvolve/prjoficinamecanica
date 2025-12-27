package br.com.digimaxx.utilitarios;

import java.awt.*;
import javax.swing.*;

public class RoundedPanel extends JPanel {

    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // Necessário para permitir transparência
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension arcs = new Dimension(radius, radius);
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cor de fundo
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        // Borda
        g2.setColor(new Color(153, 153, 153)); // #999999
        g2.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        g2.dispose();
    }
}
