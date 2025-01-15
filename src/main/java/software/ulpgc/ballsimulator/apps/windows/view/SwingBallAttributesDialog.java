package software.ulpgc.ballsimulator.apps.windows.view;

import software.ulpgc.ballsimulator.architecture.view.BallAttributesDialog;

import javax.swing.*;
import java.awt.*;

public class SwingBallAttributesDialog extends JPanel implements BallAttributesDialog {
    private final JTextField radius;
    private final JTextField velocity;
    private final JTextField gravity;
    private final JTextField cr;

    public SwingBallAttributesDialog() {
        setLayout(new FlowLayout());
        add(createEntry("Radius: ", this.radius = new JTextField(), "1"));
        add(createEntry("Velocity: ", this.velocity = new JTextField(), "0"));
        add(createEntry("Gravity", this.gravity = new JTextField(), "-9.8"));
        add(createEntry("Cr", this.cr = new JTextField(), "0.75"));
    }

    private JPanel createEntry(String text, JTextField textField, String placeHolder) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(text));
        textField.setColumns(10);
        textField.setText(placeHolder);
        panel.add(textField);
        return panel;
    }

    @Override
    public double radius() {
        return Double.parseDouble(radius.getText());
    }

    @Override
    public double velocity() {
        return Double.parseDouble(velocity.getText());
    }

    @Override
    public double gravity() {
        return Double.parseDouble(gravity.getText());
    }

    @Override
    public double cr() {
        return Double.parseDouble(cr.getText());
    }
}
