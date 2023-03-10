package figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static constants.AppConstants.*;

public class Filler extends Figure {
    private final Stack<Fill> spanStack = new Stack<>();

    public Filler(int startX, int startY, int endX, int endY, Color color, int width, BufferedImage img,
                  int radius, int corner, int turn) {
        super(startX, startY, endX, endY, color, width, img, radius, corner, turn);
    }
    public record Fill(int y, int x0, int x1, BufferedImage image) {
        public void fill(Color color) {
            int colorForFill = color.getRGB();
            for (int x = (x0 + 1); x < x1; x++) {
                image.setRGB(x, y, colorForFill);
            }
        }
    }

    public boolean pushSpan(int x, int y, int currentColor, Color color, boolean position) {
        if (image.getRGB(x, y) == currentColor && image.getRGB(x, y) != color.getRGB()) {
            if (!position) {
                spanStack.push(getSpan(image, x, y));
            }
            return true;
        }
        return false;
    }

    public void fillWithSpan(BufferedImage image, Color color, int X, int Y) {
        try {
            int currentColor = image.getRGB(X, Y);
            spanStack.push(getSpan(image, X, Y));

            while (!spanStack.isEmpty()) {
                Fill span = spanStack.pop();
                span.fill(color);

                boolean isAbove = false, isBelow = false;

                int y = span.y();
                for (int x = span.x0() + 1; x < span.x1(); x++) {
                    isAbove = pushSpan(x, y - 1, currentColor, color, isAbove);
                    isBelow = pushSpan(x, y + 1, currentColor, color, isBelow);
                }
            }
        } catch (Exception ignored) {}
    }

    private int findSpanX(BufferedImage image, int x, int y0, int sgn) {
        int curColor = image.getRGB(x, y0);
        if (y0 > 0 && y0 < MAX_IMG_HEIGHT) {
            while (image.getRGB(x, y0) == curColor && x > 0 && x < MAX_IMG_WIDTH) {
                x += sgn;
            }
        }
        return x;
    }

    public Fill getSpan(BufferedImage image, int x0, int y0) {
        return new Fill(y0, findSpanX(image, x0, y0, -1), findSpanX(image, x0, y0, 1), image);
    }

    @Override
    public void draw(Graphics g) {
        g = image.getGraphics();
        g.setColor(color);
        fillWithSpan(image, color, startX, startY);
    }
}

