package figures;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class Star extends BorderedFigure {

    public Star(int startX, int startY, int endX, int endY, Color color, int width, BufferedImage img,
                int radius, int corner, int turn) {
        super(startX, startY, endX, endY, color, width, img, radius, corner, turn);
    }

    public Path2D drawStar() {
        Path2D p = new Path2D.Double();
        int startTurn = turn;
        double localCos, localSin, turn, x, y;
        for (int i = 0; i < corner * 2; i++) {
            turn = startTurn + i * PI / corner;
            localSin = sin(turn);
            localCos = cos(turn);
            x = ((i % 2) == 0) ? localCos * radius : localCos * (double)radius / 2;
            y = ((i % 2) == 0) ? localSin * radius : localSin * (double)radius / 2;
            if (i == 0) {
                p.moveTo(startX + x, startY + y);
            }
            else {
                p.lineTo(startX + x, startY + y);
            }
        }
        p.closePath();
        return p;
    }

    @Override
    public void draw(Graphics g) {
        g = image.getGraphics();
        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(drawStar());
    }
}
