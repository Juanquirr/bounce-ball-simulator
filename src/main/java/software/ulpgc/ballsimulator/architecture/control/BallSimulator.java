package software.ulpgc.ballsimulator.architecture.control;

import software.ulpgc.ballsimulator.architecture.model.Ball;

public class BallSimulator {
    private final double dt;

    public BallSimulator(double dt) {
        this.dt = dt;
    }

    public Ball simulate(Ball ball) {
        return new Ball(
                ball.id(),
                ball.x(),
                newHeightOf(ball),
                ball.radius(),
                newVelocityOf(ball),
                ball.gravity(),
                ball.cr()
        );
    }

    private double newHeightOf(Ball ball) {
        return willBounce(ball) ? newHeightAfterBounce(ball) : ball.y() + ball.velocity() * dt;
    }

    private double newVelocityOf(Ball ball) {
        return willBounce(ball) ? newVelocityAfterBounce(ball) : ball.velocity() + ball.gravity() * dt;
    }

    private double newHeightAfterBounce(Ball ball) {
        return newVelocityAfterBounce(ball) * (dt - timeToBounce(ball)) + ball.radius();
    }

    private double newVelocityAfterBounce(Ball ball) {
        return - ball.cr() * (ball.velocity() + ball.gravity() * timeToBounce(ball));
    }

    private boolean willBounce(Ball ball) {
        return isFalling(ball) && dt > timeToBounce(ball);
    }

    private double timeToBounce(Ball ball) {
        return - (ball.y() - ball.radius()) / ball.velocity();
    }

    private static boolean isFalling(Ball ball) {
        return ball.velocity() < 0;
    }
}
