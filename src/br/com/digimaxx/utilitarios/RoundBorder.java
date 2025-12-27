package br.com.digimaxx.utilitarios;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;


public class RoundBorder implements Border {
    private int arc;
    private Color color;
    private int thickness;
    private Insets insets;

    public RoundBorder(int arc, Color color, int thickness) {
        this.arc = arc;
        this.color = color;
        this.thickness = thickness;
        this.insets = new Insets(thickness+4, thickness+8, thickness+4, thickness+8);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public boolean isBorderOpaque() { return false; }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(color);
        RoundRectangle2D rr = new RoundRectangle2D.Float(x + thickness/2f, y + thickness/2f,
                width - thickness, height - thickness, arc, arc);
        g2.draw(rr);
        g2.dispose();
    }
}
