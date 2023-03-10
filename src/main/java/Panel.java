import figures.*;
import lombok.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static constants.AppConstants.*;

@Setter
@Getter
public class Panel extends JPanel {
    private BufferedImage image;
    private ImageOperations imageOperations = new ImageOperations();
    private JScrollPane scrollPane;
    private FigureParams figureParams = new FigureParams("Line", INIT_WIDTH, null, Color.BLACK, INIT_RADIUS,
            INIT_N_FOR_CORNER, INIT_TURN);
    private Options options = new Options(figureParams);

    public Panel() {
        image = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, TYPE_INT_RGB);
        clearDrawing();

        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,null);

        if (figureParams.getObject() != null) {
            figureParams.getObject().draw(g);
            repaint();
        }
    }

    public void clearDrawing() {
        for (int i = 0; i < MAX_IMG_WIDTH; i++) {
            for (int j = 0; j <= MAX_IMG_HEIGHT; j++) {
                image.setRGB(i, j, Color.WHITE.getRGB());
            }
        }
        repaint();
    }


    public void ColorChooser() {
        figureParams.setColor(JColorChooser.showDialog(null, "ColorChooser", Color.MAGENTA));
    }

    private class MouseHandler extends MouseAdapter {
        int xPressed, yPressed;
        boolean isFirstClick = false;

        @Override
        public void mousePressed(MouseEvent event) {
            switch (figureParams.getType()) {
                case "Line" -> {
                    if (!isFirstClick) {
                        xPressed = event.getX();
                        yPressed = event.getY();
                    }
                    else {
                        figureParams.setObject(new Line(xPressed, yPressed, event.getX(), event.getY(),
                                figureParams.getColor(), figureParams.getWidth(), image, figureParams.getRadius(),
                                figureParams.getNForCorner(), figureParams.getTurn()));
                    }
                    isFirstClick = !isFirstClick;
                }
                case "NCorner" -> figureParams.setObject(new NCorner(event.getX(), event.getY(), event.getX(),
                        event.getY(), figureParams.getColor(), figureParams.getWidth(),
                        image, figureParams.getRadius(), figureParams.getNForCorner(), figureParams.getTurn()));

                case "Star" -> figureParams.setObject(new Star(event.getX(), event.getY(), event.getX(), event.getY(),
                        figureParams.getColor(), figureParams.getWidth(), image,
                        figureParams.getRadius(), figureParams.getNForCorner(), figureParams.getTurn()));
                case "Filler" -> figureParams.setObject(new Filler(event.getX(), event.getY(), event.getX(),
                        event.getY(), figureParams.getColor(), figureParams.getWidth(), image, figureParams.getRadius(),
                        figureParams.getNForCorner(), figureParams.getTurn()));
            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            figureParams.setObject(null);
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            repaint();
        }
    }
}



