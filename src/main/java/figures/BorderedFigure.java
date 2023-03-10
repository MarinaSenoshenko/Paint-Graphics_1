package figures;

import lombok.Getter;
import java.awt.Color;
import java.awt.image.BufferedImage;


@Getter
abstract class BorderedFigure extends Figure {
    public BorderedFigure(int startX, int startY, int endX, int endY, Color color, int width, BufferedImage img,
                          int radius, int corner, int turn) {
        super(startX, startY, endX, endY, color, width, img, radius, corner, turn);
    }
}
