package figures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


@AllArgsConstructor
@Getter
public abstract class Figure {
    final int startX, startY, endX, endY;
    final Color color;
    final int width;
    BufferedImage image;
    int radius, corner, turn;

    abstract public void draw(Graphics g);
}
