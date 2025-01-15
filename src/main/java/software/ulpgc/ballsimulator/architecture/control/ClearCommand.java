package software.ulpgc.ballsimulator.architecture.control;

import software.ulpgc.ballsimulator.architecture.control.presenter.BallPresenter;

public class ClearCommand implements Command {
    private final BallPresenter presenter;

    public ClearCommand(BallPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        presenter.clear();
    }
}
