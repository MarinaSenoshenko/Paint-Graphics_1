package figures;

import lombok.Getter;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

@Getter
public class Line extends Figure {

    public Line(int startX, int startY, int endX, int endY, Color color, int width, BufferedImage img,
                int radius, int corner, int turn) {
        super(startX, startY, endX, endY, color, width, img, radius, corner, turn);
    }

    public void drawBresenhamLine(int x0, int y0, int x1, int y1) {
        int x = x0, y = y0, deltaX, deltaY, offsetX, offsetY, error;
        int lineColor = color.getRGB();

        deltaX = abs(x1 - x0);
        deltaY = abs(y1 - y0);

        offsetX = Integer.compare(x1 - x0, 0);
        offsetY = Integer.compare(y1 - y0, 0);

        error = max(deltaX, deltaY) / 2;

        int maxDelta = max(deltaX, deltaY);
        int minDelta = min(deltaX, deltaY);

        try {
            for (int i = 0; i < maxDelta; i++) {
                if (i == 0) {
                    image.setRGB(x, y, lineColor);
                }
                error -= minDelta;
                if (error < 0) {
                    error += maxDelta;
                    x += offsetX;
                    y += offsetY;
                }
                else {
                    x = (deltaX > deltaY) ? x + offsetX : x;
                    y = (deltaX < deltaY) ? y + offsetY : y;
                }
                image.setRGB(x, y, lineColor);
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {}
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        if (width == 1) {
            drawBresenhamLine(startX, startY, endX, endY);
        }
        else {
            Graphics2D g2d = (Graphics2D)image.getGraphics();
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(width));
            g2d.drawLine(startX, startY, endX, endY);
        }
    }
}
