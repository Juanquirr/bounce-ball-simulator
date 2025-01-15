package software.ulpgc.ballsimulator.architecture.control.presenter;

import software.ulpgc.ballsimulator.architecture.view.BallDisplay;

public class BallCoordinateAdapter {
    private final double pixelsPerMeter;
    private final BallDisplay display;

    private BallCoordinateAdapter(double pixelsPerMeter, BallDisplay display) {
        this.pixelsPerMeter = pixelsPerMeter;
        this.display = display;
    }

    public static BallCoordinateAdapter with(double pixelsPerMeter, BallDisplay display) {
        return new BallCoordinateAdapter(pixelsPerMeter, display);
    }

    public int toPixels(double meters) {
        return (int) (meters * pixelsPerMeter);
    }

    public double toMeters(int pixels) {
        return pixels / pixelsPerMeter;
    }

    private int inBoundsForBall(int pixels, double radius) {
        return Math.max(pixels, toPixels(radius));
    }

    public int inBoundsXForBall(int pixels, double radius) {
        return inBoundsForBall(Math.min(pixels, display.width() - toPixels(radius)), radius);
    }

    public int inBoundsYForBall(int pixels, double radius) {
        return inBoundsForBall(Math.min(pixels, display.height() - toPixels(radius)), radius);
    }

    public int inBoundsXForDisplay(int pixels, double radius) {
        return inBoundsXForMaximumWidth(Math.max(pixels, 0), radius);
    }

    private int inBoundsXForMaximumWidth(int pixels, double radius) {
        return Math.min(display.width() - toPixels(radius) * 2, pixels);
    }

    public int inBoundsYForDisplay(int pixels, double radius) {
        return inBoundsForMaximumHeight(Math.max(pixels, toPixels(radius)));
    }

    private int inBoundsForMaximumHeight(int pixels) {
        return Math.min(display.height(), pixels);
    }
}
