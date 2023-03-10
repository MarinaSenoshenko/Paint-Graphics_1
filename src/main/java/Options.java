import lombok.AllArgsConstructor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;

import static constants.AppConstants.*;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;

@AllArgsConstructor
public class Options {
    private FigureParams figureParams;
    static JSlider slider;
    static JButton btnOk;
    static JTextField textField;

    public void option(String optionType) {
        boolean isCorrectly = false;
        String param = JOptionPane.showInputDialog(null,"Enter the parameter",
                "Choose the parameter");
        try {
            if (param.trim().equals("")) {
                isCorrectly = true;
            }
        } catch (NullPointerException ignored) {}

        if (param != null && param.length() > 0) {
            try {
                int current = Integer.parseInt(param);
                switch (optionType) {
                    case "Options For Line" -> {
                        if (current > MIN_LINE_WIDTH && current <= MAX_LINE_WIDTH) {
                            figureParams.setWidth(Integer.parseInt(param));
                            isCorrectly = true;
                        }
                    }
                    case "Options For NCorner" -> {
                        if (current > MIN_CORNERS && current <= MAX_CORNERS) {
                            figureParams.setNForCorner(Integer.parseInt(param));
                            isCorrectly = true;
                        }
                    }
                }
                if (!isCorrectly) {
                    incorrectData();
                }
            } catch (NumberFormatException e) {
                incorrectData();
            }
        }
    }

    public void changeTurnOrRadius(int param, String opt) {
        JFrame frame = new JFrame(opt);
        JPanel p = new JPanel();

        frame.setIconImage(new ImageIcon(Objects.requireNonNull(Frame.class.
                getResource("icon.png"))).getImage());

        slider = new JSlider(MIN_RADIUS_OR_TURN, MAX_RADIUS_OR_TURN, param);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(SPACE_BETWEEN_BIG_MARKS);
        slider.setMinorTickSpacing(SPACE_BETWEEN_SMALL_MARKS);

        btnOk = new JButton("OK");
        switch (opt) {
            case ("Radius") -> {
                btnOk.addActionListener(e -> {
                    int a;
                    try {
                        a = Integer.parseInt(textField.getText());
                    }
                    catch (NumberFormatException ex) {
                        errorData();
                    }

                    catch (Exception ex) {
                        errorData();
                        a = slider.getValue();
                        figureParams.setRadius(a);
                        slider.setValue(a);
                    }
                    frame.dispatchEvent(new WindowEvent(frame, WINDOW_CLOSING));
                });

                slider.addChangeListener(e -> {
                    figureParams.setRadius(slider.getValue());
                    textField.setText("" + figureParams.getRadius());
                });

                textField = new JTextField(TEXT_FIELD_LEN);
                textField.setToolTipText("Radius");
                textField.setPreferredSize(new Dimension(TEXT_FIELD_SIDE, TEXT_FIELD_SIDE));

                textField.setText("" + figureParams.getRadius());
                slider.setValue(figureParams.getRadius());
            }
            case ("Turn") -> {
                btnOk.addActionListener(e -> {
                    int a;
                    try {
                        a = Integer.parseInt(textField.getText());
                    }
                    catch (NumberFormatException ex) {
                        errorData();
                    }

                    catch (Exception ex) {
                        errorData();
                        a = slider.getValue();
                        figureParams.setTurn(a);
                        slider.setValue(a);
                    }
                    frame.dispatchEvent(new WindowEvent(frame, WINDOW_CLOSING));
                });

                slider.addChangeListener(e -> {
                    figureParams.setTurn(slider.getValue());
                    textField.setText("" + figureParams.getTurn());
                });

                textField = new JTextField(TEXT_FIELD_LEN);
                textField.setToolTipText("Turn");
                textField.setPreferredSize(new Dimension(TEXT_FIELD_SIDE, TEXT_FIELD_SIDE));

                slider.setValue(figureParams.getTurn());
                textField.setText("" + figureParams.getTurn());
            }
        }
        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                update();
            }
            public void removeUpdate(DocumentEvent e) {
                update();
            }
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void update() {
                try {
                    if (Integer.parseInt(textField.getText()) <= MAX_RADIUS_OR_TURN &&
                            Integer.parseInt(textField.getText()) >= MIN_RADIUS_OR_TURN) {
                        slider.setValue(Integer.parseInt(textField.getText()));
                        return;
                    }
                    incorrectData();
                }
                catch (Exception ignored) {}
            }
        });

        p.add(slider);
        p.add(textField);
        p.add(btnOk);

        frame.add(p);

        frame.setSize(EDIT_TURN_OR_RADIUS_WIDTH, EDIT_TURN_OR_RADIUS_HEIGHT);
        frame.show();
        frame.pack();
    }

    private void incorrectData() {
        JOptionPane.showMessageDialog(null, """
                                Incorrect data:
                                0<=radius<=100
                                0<line width<=100
                                2<NCorner<=16
                                0<=turn<=100
                                """,
                "About Init",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void errorData() {
        JOptionPane.showMessageDialog(null, "Syntax error:\nwe save last correct parameter",
                "About Init", JOptionPane.ERROR_MESSAGE);
    }
}
