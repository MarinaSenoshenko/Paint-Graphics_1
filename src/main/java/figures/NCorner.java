package figures;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;


public class NCorner extends BorderedFigure {

    public NCorner(int startX, int startY, int endX, int endY, Color color, int width, BufferedImage img,
                   int radius, int corner, int turn) {
        super(startX, startY, endX, endY, color, width, img, radius, corner, turn);
    }

    public void DrawNFigure(Graphics g) {
        int[] x = new int[corner], y = new int[corner];
        for (int i = 0; i < corner; i++) {
            x[i] = (int)(startX + radius * cos(((2 * PI * i) / corner) + turn));
            y[i] = (int)(startY + radius * sin(((2 * PI * i) / corner) + turn));
        }
        g.drawPolygon(new Polygon(x, y, corner));
    }

    @Override
    public void draw(Graphics g) {
        g = image.getGraphics();
        g.setColor(color);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        DrawNFigure(g);
    }
}
