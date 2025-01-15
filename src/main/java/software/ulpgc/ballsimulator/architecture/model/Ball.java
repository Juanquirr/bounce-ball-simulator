package software.ulpgc.ballsimulator.architecture.model;

import java.util.Objects;

public record Ball(int id, double x, double y, double radius, double velocity, double gravity, double cr) {
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Ball ball = (Ball) object;
        return id == ball.id();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
