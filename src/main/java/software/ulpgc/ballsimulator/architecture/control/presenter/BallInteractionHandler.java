package software.ulpgc.ballsimulator.architecture.control.presenter;

import software.ulpgc.ballsimulator.architecture.model.Ball;
import software.ulpgc.ballsimulator.architecture.model.BallBuilder;
import software.ulpgc.ballsimulator.architecture.view.BallAttributesDialog;
import software.ulpgc.ballsimulator.architecture.view.BallDisplay;

import java.util.Set;

public class BallInteractionHandler {
    private final BallAttributesDialog dialog;
    private final BallHolder ballHolder;
    private final Set<Ball> balls;
    private final BallCoordinateAdapter coordinateAdapter;

    public BallInteractionHandler(BallAttributesDialog dialog, BallHolder ballHolder, Set<Ball> balls, BallCoordinateAdapter adapter) {
        this.dialog = dialog;
        this.ballHolder =ballHolder;
        this.balls = balls;
        this.coordinateAdapter = adapter;
    }

    public BallDisplay.Click click() {
        return (button, xOffset, yOffset) -> {
            if (ballHolder.get() != null) return;
            if (button == 3) {
                findPressedBall(xOffset, yOffset);
            } else {
                add(BallBuilder.create()
                        .withX(coordinateAdapter.toMeters(coordinateAdapter.inBoundsXForBall(xOffset, dialog.radius())))
                        .withY(coordinateAdapter.toMeters(coordinateAdapter.inBoundsYForBall(yOffset, dialog.radius())))
                        .withRadius(dialog.radius())
                        .withVelocity(dialog.velocity())
                        .withGravity(dialog.gravity())
                        .withCr(dialog.cr())
                        .build()
                );
            }
        };
    }

    public BallDisplay.Pressed pressed() {
        return this::findPressedBall;
    }

    private void findPressedBall(int xOffset, int yOffset) {
        synchronized (this.balls) {
            balls.stream()
                    .filter(b -> isInX(xOffset, b) && isInY(yOffset, b))
                    .findAny()
                    .ifPresent(ballHolder::set);
        }
    }

    public BallDisplay.Shift shift() {
        return (xOffset, yOffset) -> {
            if (ballHolder.get() == null) return;
            add(BallBuilder.create()
                    .withId(ballHolder.get().id())
                    .withX(coordinateAdapter.toMeters(coordinateAdapter.inBoundsXForBall(xOffset, ballHolder.get().radius())))
                    .withY(coordinateAdapter.toMeters(coordinateAdapter.inBoundsYForBall(yOffset, ballHolder.get().radius())))
                    .withRadius(ballHolder.get().radius())
                    .withVelocity(0)
                    .withGravity(ballHolder.get().gravity())
                    .withCr(ballHolder.get().cr())
                    .build());
        };
    }

    public BallDisplay.Released released() {
        return (xOffset, yOffset) -> ballHolder.releaseBall();
    }

    public void add(Ball ball) {
        synchronized (this.balls) {
            this.balls.remove(ball);
            this.balls.add(ball);
        }
    }

    private boolean isInX(int xOffset, Ball b) {
        return coordinateAdapter.toMeters(xOffset) > b.x() - b.radius() && coordinateAdapter.toMeters(xOffset) < b.x() + b.radius();
    }

    private boolean isInY(int yOffset, Ball b) {
        return coordinateAdapter.toMeters(yOffset) > b.y() - b.radius() && coordinateAdapter.toMeters(yOffset) < b.y() + b.radius();
    }
}
