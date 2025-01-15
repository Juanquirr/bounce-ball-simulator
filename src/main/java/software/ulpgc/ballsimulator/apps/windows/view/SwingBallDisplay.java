package software.ulpgc.ballsimulator.apps.windows.view;

import software.ulpgc.ballsimulator.architecture.view.BallDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class SwingBallDisplay extends JPanel implements BallDisplay {
    private Shift shift;
    private Released released;
    private Click click;
    private Pressed pressed;
    private final List<PaintOrder> paintOrders;
    private final static int WIDTH_GAP = 20;
    private final static int HEIGHT_GAP = 20;

    public SwingBallDisplay() {
        this.shift = Shift.NULL;
        this.released = Released.NULL;
        this.click = Click.NULL;
        this.pressed = Pressed.NULL;
        this.paintOrders = new ArrayList<>();
        this.addMouseListener(createMouseListener());
        this.addMouseMotionListener(createMouseMotionlistener());
    }

    private MouseMotionListener createMouseMotionlistener() {
        return new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                shift.offset(e.getX() - WIDTH_GAP / 2, (getHeight() - HEIGHT_GAP / 2) - e.getY());
            }
        };
    }

    private MouseListener createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click.offset(e.getButton(), e.getX() - WIDTH_GAP / 2, (getHeight() - HEIGHT_GAP / 2) - e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed.offset(e.getX() - WIDTH_GAP / 2, (getHeight() - HEIGHT_GAP / 2) - e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                released.offset(e.getX() - WIDTH_GAP / 2, (getHeight() - HEIGHT_GAP / 2) - e.getY());
            }
        };
    }

    @Override
    public void paint(Graphics g) {
        clearCanvas(g);
        synchronized (paintOrders) {
            paintOrders.forEach(p -> drawPaintOrder(g, p));
        }
    }

    private void clearCanvas(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(xCenterFor(width()), yCenterFor(height()), width(), height());
        darGrid(g);
    }

    private void darGrid(Graphics g) {
        for (int i = 0; i < height(); i += 10) {
            g.fillRect(WIDTH_GAP / 2, i + HEIGHT_GAP / 2, width(), 1);
            g.fillRect(i + WIDTH_GAP, HEIGHT_GAP / 2,1, height());
        }
    }

    private int yCenterFor(int height) {
        return (getHeight() - height) / 2;
    }

    private int xCenterFor(int width) {
        return (getWidth() - width) / 2;
    }

    private void drawPaintOrder(Graphics g, PaintOrder p) {
        g.setColor(Color.BLUE);
        g.fillOval(
                p.x() + (WIDTH_GAP / 2),
                (getHeight() - HEIGHT_GAP / 2) - p.y(),
                p.radius() * 2,
                p.radius() * 2
        );
    }

    @Override
    public void draw(List<PaintOrder> paintOrders) {
        synchronized (this.paintOrders) {
            this.paintOrders.clear();
            this.paintOrders.addAll(paintOrders);
        }
        repaint();
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift;
    }

    @Override
    public void on(Released released) {
        this.released = released;
    }

    @Override
    public void on(Click click) {
        this.click = click;
    }

    @Override
    public void on(Pressed pressed) {
        this.pressed = pressed;
    }

    @Override
    public int width() {
        return getWidth() - WIDTH_GAP;
    }

    @Override
    public int height() {
        return getHeight() - HEIGHT_GAP;
    }
}
