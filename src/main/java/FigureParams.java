import figures.Figure;
import lombok.*;
import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public class FigureParams {
    private String type;
    private int width;
    private Figure object;
    private Color color;
    private int radius, nForCorner, turn;
}