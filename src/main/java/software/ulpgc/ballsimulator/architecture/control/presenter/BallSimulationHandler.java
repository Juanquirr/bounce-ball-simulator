package software.ulpgc.ballsimulator.architecture.control.presenter;

import software.ulpgc.ballsimulator.architecture.control.BallSimulator;
import software.ulpgc.ballsimulator.architecture.model.Ball;

import java.util.List;
import java.util.Set;

public class BallSimulationHandler {
    private final BallSimulator simulator;
    private final Set<Ball> balls;
    private final BallHolder ballHolder;

    public BallSimulationHandler(BallSimulator simulator, Set<Ball> balls, BallHolder ballHolder) {
        this.simulator = simulator;
        this.balls = balls;
        this.ballHolder = ballHolder;
    }

    public Set<Ball> getBalls() {
        synchronized (balls) {
            return balls;
        }
    }

    public void simulate() {
        synchronized (this.balls) {
            List<Ball> newBalls = balls.stream().map(this::simulate).toList();
            balls.clear();
            balls.addAll(newBalls);
        }
    }

    private Ball simulate(Ball ball) {
        if (ballHolder.get() != null && ball.id() == ballHolder.get().id()) return ball;
        return simulator.simulate(ball);
    }
}

