package software.ulpgc.ballsimulator.architecture.model;

import java.util.Objects;

public class BallBuilder implements Builder<Ball> {
    private double x;
    private double y;
    private double radius;
    private double velocity;
    private double gravity;
    private double cr;
    private Integer id;
    private static int ID;

    private BallBuilder() {
    }

    public static BallBuilder create() {
        return new BallBuilder();
    }

    public BallBuilder withX(double x) {
        this.x = x;
        return this;
    }

    public BallBuilder withY(double y) {
        this.y = y;
        return this;
    }

    public BallBuilder withRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public BallBuilder withVelocity(double velocity) {
        this.velocity = velocity;
        return this;
    }

    public BallBuilder withGravity(double gravity) {
        this.gravity = gravity;
        return this;
    }

    public BallBuilder withCr(double cr) {
        this.cr = cr;
        return this;
    }

    public BallBuilder withId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public Ball build() {
        return new Ball(
                Objects.isNull(id) ? BallBuilder.ID++ : id,
                x,
                y,
                radius,
                velocity,
                gravity,
                cr
        );
    }
}
