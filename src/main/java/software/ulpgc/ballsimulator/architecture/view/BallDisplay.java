package software.ulpgc.ballsimulator.architecture.view;

import java.util.List;

public interface BallDisplay {
    void draw(List<PaintOrder> paintOrders);

    void on(Shift shift);

    void on(Released released);

    void on(Click click);

    void on(Pressed pressed);

    int width();

    int height();

    interface Shift {
        Shift NULL = (xOffset, yOffset) -> {};

        void offset(int xOffset, int yOffset);
    }

    interface Released {
        Released NULL = (xOffset, yOffset) -> {};

        void offset(int xOffset, int yOffset);
    }

    interface Click {
        Click NULL = (button, xOffset, yOffset) -> {};

        void offset(int button, int xOffset, int yOffset);
    }

    interface Pressed {
        Pressed NULL = (xOffset, yOffset) -> {};

        void offset(int xOffset, int yOffset);
    }

    record PaintOrder(int x, int y, int radius) {}
}
