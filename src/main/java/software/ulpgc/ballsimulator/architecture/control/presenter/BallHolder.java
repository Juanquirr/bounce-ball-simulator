package software.ulpgc.ballsimulator.architecture.control.presenter;

import software.ulpgc.ballsimulator.architecture.model.Ball;

public class BallHolder {
    private final Ball[] ball;

    public BallHolder() {
        this.ball = new Ball[]{null};
    }

    public Ball set(Ball ball) {
        this.ball[0] = ball;
        return ball;
    }

    public Ball get() {
        return ball[0];
    }

    public void releaseBall() {
        ball[0] = null;
    }
}
