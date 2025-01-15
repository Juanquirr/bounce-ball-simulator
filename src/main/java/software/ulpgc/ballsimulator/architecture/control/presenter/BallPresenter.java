package software.ulpgc.ballsimulator.architecture.control.presenter;

import software.ulpgc.ballsimulator.architecture.control.BallSimulator;
import software.ulpgc.ballsimulator.architecture.model.*;
import software.ulpgc.ballsimulator.architecture.view.BallAttributesDialog;
import software.ulpgc.ballsimulator.architecture.view.BallDisplay;

import java.util.*;

public class BallPresenter {
    private final BallDisplay display;
    private final BallRenderer renderer;
    private final BallSimulationHandler simulationHandler;
    private final BallInteractionHandler interactionHandler;
    private final Timer timer;

    public BallPresenter(BallDisplay display, BallAttributesDialog dialog) {
        BallCoordinateAdapter converter = BallCoordinateAdapter.with(2 / 0.1, display);
        Set<Ball> balls = new HashSet<>();
        BallHolder ballHolder = new BallHolder();
        BallSimulator simulator = new BallSimulator(0.001);
        this.display = display;
        this.renderer = new BallRenderer(display, converter);
        this.simulationHandler = new BallSimulationHandler(simulator, balls, ballHolder);
        this.interactionHandler = new BallInteractionHandler(dialog, ballHolder, balls, converter);
        this.timer = new Timer();
        setupDisplay();
    }

    private void setupDisplay() {
        display.on(interactionHandler.click());
        display.on(interactionHandler.pressed());
        display.on(interactionHandler.shift());
        display.on(interactionHandler.released());
    }

    public void execute() {
        timer.schedule(task(), 1, 1);
    }

    public void clear() {
        simulationHandler.getBalls().clear();
    }

    private TimerTask task() {
        return new TimerTask() {
            @Override
            public void run() {
                simulationHandler.simulate();
                renderer.render(simulationHandler.getBalls());
            }
        };
    }
}

