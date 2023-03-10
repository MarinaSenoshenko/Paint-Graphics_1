import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;

import static constants.AppConstants.*;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.ScrollPaneConstants.*;

public class Frame extends JFrame {
    private final Panel panel = new Panel();
    private final JMenuBar menuBar = new JMenuBar();
    private final String about;
    private JRadioButtonMenuItem line, nCorner, star, filler;
    private JRadioButtonMenuItem red, green, blue, cyan, magenta, yellow, colorChooser;
    private final JToggleButton btnLine, btnNFigure, btnStar, btnFill;
    private final JToggleButton btnRed, btnRedGreen, btnGreen, btnGreenBlue, btnBlue, btnRedBlue, btnColor;

    public Frame(String about) {
        super("Paint");
        this.about = about;
        setIconImage(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("icon.png"))).getImage());
        setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar("Main toolbar");
        JScrollPane toolBarScrollPane = new JScrollPane(toolBar, VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        toolBarScrollPane.getViewport().setLayout(new ConstrainedViewPortLayout());
        toolBarScrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(toolBarScrollPane, BorderLayout.PAGE_START);

        JMenu fileMenu = new JMenu("File"), figureMenu = new JMenu("Figure"), sizeWidth = new JMenu("Options"),
                lastParamsMenu = new JMenu("Current params"), colorMenu = new JMenu("Colors");

        String[] colorOpt = {"Red", "Green", "Blue", "Cyan", "Magenta", "Yellow", "ColorChooser"},
                figureOpt = {"Line", "NCorner", "Star", "Filler"},
                fileOpt = {"Clear", "Save", "Upload", "About"},
                sizeOpt = {"Options For Radius", "Options For NCorner", "Options For Line", "Turn Option"},
                curParamsOpt = {"Show Current Radius", "Show Current Line Width", "Show Current Corner Number",
                        "Show Current Corner Turn"};

        addToJMenu(fileMenu, fileOpt);
        addToJMenu(figureMenu, figureOpt);
        addToJMenu(sizeWidth, sizeOpt);
        addToJMenu(colorMenu, colorOpt);
        addToJMenu(lastParamsMenu, curParamsOpt);

        JButton btnClear = new JButton((new ImageIcon(Objects.requireNonNull(Frame.class.getResource("clear.png")))));
        btnClear.addActionListener(e -> panel.clearDrawing());

        JButton btnAbout = new JButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("about.png"))));
        btnAbout.addActionListener(e  -> JOptionPane.showMessageDialog(null, about, "About", INFORMATION_MESSAGE));

        JButton btnExit = new JButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("door.png"))));
        btnExit.addActionListener(e -> System.exit(0));

        JButton btnExport = new JButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("export.png"))));
        btnExport.addActionListener(e -> panel.getImageOperations().saveImage(panel));

        JButton btnSave = new JButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("save.png"))));
        btnSave.addActionListener(e -> panel.getImageOperations().uploadImage(panel));

        btnLine = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("line.png"))));
        btnNFigure = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("rectangle.png"))));
        btnStar = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("star.png"))));
        btnFill = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("filler.png"))));
        btnLine.setSelected(true);
        line.setSelected(true);

        btnLine.addActionListener(e -> {
            setNotSelectedFigure(btnNFigure, btnFill, btnStar, btnLine);
            figureAction(true, false, false, false, "Line");
        });
        btnNFigure.addActionListener(e -> {
            setNotSelectedFigure(btnLine, btnFill, btnStar, btnNFigure);
            figureAction(false, true, false, false, "NCorner");
        });
        btnStar.addActionListener(e -> {
            setNotSelectedFigure(btnNFigure, btnFill, btnLine, btnStar);
            figureAction(false, false, false, true, "Star");
        });
        btnFill.addActionListener(e -> {
            setNotSelectedFigure(btnNFigure, btnLine, btnStar, btnFill);
            figureAction(false, false, true, false, "Filler");
        });

        JButton btnRadius = new JButton("Radius");
        btnRadius.addActionListener(e -> panel.getOptions().changeTurnOrRadius(panel.getFigureParams().getRadius(), "Radius"));
        JButton btnSizeLine = new JButton("Line width");
        btnSizeLine.addActionListener(e -> panel.getOptions().option("Options For Line"));
        JButton btnNCorner = new JButton("Corner number");
        btnNCorner.addActionListener(e -> panel.getOptions().option("Options For NCorner"));
        JButton btnCorner = new JButton("Corner turn");
        btnCorner.addActionListener(e -> panel.getOptions().changeTurnOrRadius(panel.getFigureParams().getTurn(), "Turn"));

        btnRed = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("color/red.png"))));
        btnRedGreen = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("color/yellow.png"))));
        btnGreen = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("color/green.png"))));
        btnGreenBlue = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("color/cyan.png"))));
        btnBlue = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("color/blue.png"))));
        btnRedBlue = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("color/magenta.png"))));
        btnColor = new JToggleButton(new ImageIcon(Objects.requireNonNull(Frame.class.getResource("rainbow.png"))));
        btnColor.setSelected(true);
        colorChooser.setSelected(true);

        btnColor.addActionListener(e -> {
            setNotSelectedColor(btnRedGreen, btnGreen, btnGreenBlue, btnBlue, btnRedBlue, btnRed, btnColor);
            colorAction(false, false, false, false, false, false, true, Color.RED);
            panel.ColorChooser();
        });
        btnRed.addActionListener(e -> {
            setNotSelectedColor(btnColor, btnRedGreen, btnGreen, btnGreenBlue, btnBlue, btnRedBlue, btnRed);
            colorAction(true, false, false, false, false, false, false, Color.RED);
        });
        btnRedGreen.addActionListener(e -> {
            setNotSelectedColor(btnColor, btnRed, btnGreen, btnGreenBlue, btnBlue, btnRedBlue, btnRedGreen);
            colorAction(false, false, false, false, false, true, false, Color.YELLOW);
        });
        btnGreen.addActionListener(e -> {
            setNotSelectedColor(btnColor, btnRedGreen, btnRed, btnGreenBlue, btnBlue, btnRedBlue, btnGreen);
            colorAction(false, true, false, false, false, false, false, Color.GREEN);
        });
        btnGreenBlue.addActionListener(e -> {
            setNotSelectedColor(btnColor, btnRedGreen, btnGreen, btnRed, btnBlue, btnRedBlue, btnGreenBlue);
            colorAction(false, false, false, true, false, false, false,Color.CYAN);
        });
        btnBlue.addActionListener(e -> {
            setNotSelectedColor(btnColor, btnRedGreen, btnGreen, btnGreenBlue, btnRed, btnRedBlue, btnBlue);
            colorAction(false, false, true, false, false, false, false,Color.BLUE);
        });
        btnRedBlue.addActionListener(e -> {
            setNotSelectedColor(btnColor, btnRedGreen, btnGreen, btnGreenBlue, btnBlue, btnRed, btnRedBlue);
            colorAction(false, false, false, false, true, false, false,Color.MAGENTA);
        });

        btnClear.setToolTipText("Clear the workspace");
        btnAbout.setToolTipText("Information about the program");
        btnExit.setToolTipText("Exit the program");
        btnColor.setToolTipText("Choose a color to draw");
        btnExport.setToolTipText("Save draw");
        btnSave.setToolTipText("Upload a draw");
        btnLine.setToolTipText("Draw lines");
        btnNFigure.setToolTipText("Draw correct N-gons");
        btnStar.setToolTipText("Draw stars");
        btnFill.setToolTipText("Fill the area");
        btnRadius.setToolTipText("Enter the radius");
        btnSizeLine.setToolTipText("Select the line thickness to draw the line");
        btnNCorner.setToolTipText("Enter the number of corners of a shape");
        btnCorner.setToolTipText("Enter the angle of rotation");
        btnRed.setToolTipText("Red color");
        btnRedGreen.setToolTipText("Yellow color");
        btnGreen.setToolTipText("Green color");
        btnGreenBlue.setToolTipText("Сyan color");
        btnBlue.setToolTipText("Blue color");
        btnRedBlue.setToolTipText("Magenta color");

        List.of(btnClear, btnAbout, btnExit, btnColor, btnExport, btnSave, btnLine, btnNFigure, btnStar, btnFill,
                btnRed, btnRedGreen, btnGreen, btnGreenBlue, btnBlue, btnRedBlue, btnRadius, btnSizeLine,
                btnNCorner, btnCorner).forEach(toolBar::add);

        panel.setScrollPane(new JScrollPane(panel, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED));
        panel.setPreferredSize(new Dimension(MAX_IMG_WIDTH, MAX_IMG_HEIGHT));
        panel.getScrollPane().setBorder(BorderFactory.createEmptyBorder());
        add(panel.getScrollPane(), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setVisible(true);
        pack();
    }

    private void setNotSelectedFigure(JToggleButton btn1, JToggleButton btn2, JToggleButton btn3,
                                      JToggleButton btnSelected) {
        btn1.setSelected(false);
        btn2.setSelected(false);
        btn3.setSelected(false);
        btnSelected.setSelected(true);
    }

    private void setNotSelectedColor(JToggleButton btn1, JToggleButton btn2, JToggleButton btn3, JToggleButton btn4,
                                     JToggleButton btn5, JToggleButton btn6, JToggleButton btnSelected) {
        btn1.setSelected(false);
        btn2.setSelected(false);
        btn3.setSelected(false);
        btn4.setSelected(false);
        btn5.setSelected(false);
        btn6.setSelected(false);
        btnSelected.setSelected(true);
    }

    private JRadioButtonMenuItem addRB(JMenu menu, String element) {
        JRadioButtonMenuItem obj = new JRadioButtonMenuItem(element);
        menu.add(obj);
        obj.addActionListener(new MenuHandler());
        return obj;
    }

    private void addToJMenu(JMenu menu, String[] arr) {
        for (String element : arr) {
            switch (element) {
                case "Line" -> line = addRB(menu, element);
                case "NCorner" -> nCorner = addRB(menu, element);
                case "Star" -> star = addRB(menu, element);
                case "Filler" -> filler = addRB(menu, element);
                case "Red" -> red = addRB(menu, element);
                case "Green" -> green = addRB(menu, element);
                case "Blue" -> blue = addRB(menu, element);
                case "Cyan" -> cyan = addRB(menu, element);
                case "Magenta" -> magenta = addRB(menu, element);
                case "Yellow" -> yellow = addRB(menu, element);
                case "ColorChooser" -> colorChooser = addRB(menu, element);
                default ->  {
                    JMenuItem menuItem  = new JMenuItem(element);
                    menu.add(menuItem);
                    menuItem.addActionListener(new MenuHandler());
                }
            }
        }
        menuBar.add(menu);
    }

    private void figureAction(boolean isLine, boolean isNCorner, boolean isFiller, boolean isStar,
                              String typeOfFigure) {
        star.setSelected(isStar);
        nCorner.setSelected(isNCorner);
        filler.setSelected(isFiller);
        line.setSelected(isLine);

        btnStar.setSelected(isStar);
        btnNFigure.setSelected(isNCorner);
        btnFill.setSelected(isFiller);
        btnLine.setSelected(isLine);

        panel.getFigureParams().setType(typeOfFigure);
    }

    private void colorAction(boolean isRed, boolean isGreen, boolean isBlue, boolean isCyan, boolean isMagenta,
                             boolean isYellow, boolean isColorChooser, Color color) {
        red.setSelected(isRed);
        green.setSelected(isGreen);
        blue.setSelected(isBlue);
        cyan.setSelected(isCyan);
        magenta.setSelected(isMagenta);
        yellow.setSelected(isYellow);
        colorChooser.setSelected(isColorChooser);
        if (!isColorChooser) {
            panel.getFigureParams().setColor(color);
        }

        btnRed.setSelected(isRed);
        btnGreen.setSelected(isGreen);
        btnBlue.setSelected(isBlue);
        btnRedBlue.setSelected(isMagenta);
        btnRedGreen.setSelected(isYellow);
        btnGreenBlue.setSelected(isCyan);
        btnColor.setSelected(isColorChooser);
    }

    private static class ConstrainedViewPortLayout extends ViewportLayout {
        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Dimension preferredViewSize = super.preferredLayoutSize(parent);
            Container viewportContainer = parent.getParent();

            if (viewportContainer != null) {
                Dimension parentSize = viewportContainer.getSize();
                preferredViewSize.width = parentSize.width;
            }
            return preferredViewSize;
        }
    }

    private class MenuHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            switch (event.getActionCommand()) {
                case "Turn Option" -> panel.getOptions().changeTurnOrRadius(panel.getFigureParams().getTurn(), "Turn");
                case "Options For Radius" -> panel.getOptions().changeTurnOrRadius(panel.getFigureParams().getRadius(), "Radius");
                case  "Options For NCorner", "Options For Line" -> panel.getOptions().option(event.getActionCommand());
                case "ColorChooser" -> {
                    colorAction(false, false, false, false, false, false, true, Color.RED);
                    panel.ColorChooser();
                }
                case "About" -> JOptionPane.showMessageDialog(null, about, "About", INFORMATION_MESSAGE);
                case "Clear" -> panel.clearDrawing();
                case "Save" -> panel.getImageOperations().saveImage(panel);
                case "Upload" -> panel.getImageOperations().uploadImage(panel);
                case "Line" -> figureAction(true, false, false, false, "Line");
                case "Filler" -> figureAction(false, false, true, false, "Filler");
                case "NCorner" -> figureAction(false, true, false, false, "NCorner");
                case "Star" -> figureAction(false, false, false, true, "Star");
                case "Red" -> colorAction(true, false, false, false, false, false, false, Color.RED);
                case "Green"-> colorAction(false, true, false, false, false, false, false, Color.GREEN);
                case "Blue"-> colorAction(false, false, true, false, false, false, false, Color.BLUE);
                case "Cyan" -> colorAction(false, false, false, true, false, false, false, Color.CYAN);
                case "Magenta" -> colorAction(false, false, false, false, true, false, false, Color.MAGENTA);
                case "Yellow" -> colorAction(false, false, false, false, false, true, false, Color.YELLOW);
                case "Show Current Radius" -> JOptionPane.showMessageDialog(null,
                        "radius = " + panel.getFigureParams().getRadius(), "About", INFORMATION_MESSAGE);
                case "Show Current Line Width" -> JOptionPane.showMessageDialog(null,
                        "line width = " + panel.getFigureParams().getWidth(), "About", INFORMATION_MESSAGE);
                case "Show Current Corner Number" -> JOptionPane.showMessageDialog(null,
                        "corner number = " + panel.getFigureParams().getNForCorner(), "About", INFORMATION_MESSAGE);
                case "Show Current Corner Turn" -> JOptionPane.showMessageDialog(null,
                        "corner turn = " + panel.getFigureParams().getTurn(), "About", INFORMATION_MESSAGE);
            }
        }
    }
}

