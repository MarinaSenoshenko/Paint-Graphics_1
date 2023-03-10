import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import static java.awt.FileDialog.*;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static constants.AppConstants.*;

public class ImageOperations {

    public void uploadImage(Panel panel) {
        try {
            FileDialog fd = new FileDialog((Dialog)null, "Upload image", LOAD);
            fd.setFile("*.png");
            fd.setVisible(true);
            panel.repaint();

            panel.setImage(ImageIO.read(fd.getFiles()[0]));
            panel.paintComponent(panel.getGraphics());

            BufferedImage img = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, TYPE_INT_RGB);
            panel.paint(img.createGraphics());
            panel.setImage(img);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}

    }

    public void saveImage(Panel panel) {
        try {
            FileDialog fd = new FileDialog((Dialog)null, "Save Image", SAVE);
            fd.setFile("*.png");
            fd.setVisible(true);

            File file = fd.getFiles()[0];

            if (file.getName().trim().equals("")) {
                throw new Exception("Please enter a file name!");
            }

            if (file.exists()) {
                throw new FileAlreadyExistsException("File already exists.");
            }

            BufferedImage img = new BufferedImage(panel.getScrollPane().getViewport().getSize().width,
                    panel.getScrollPane().getViewport().getSize().height, TYPE_INT_RGB);
            panel.paint(img.createGraphics());

            ImageIO.write(img, "PNG", file);
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
