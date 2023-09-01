package Objects.Bomb.States;

import Managers.States.StateManager;
import Objects.Bomb.Bomb;
import Objects.Bomb.BombConstants;
import Objects.Bomb.BombConstants.*;

public class BombStates extends StateManager.BaseState<States> {
    Bomb bomb;
    public BombStates(States state, Bomb bomb) {
        super(state);
        this.bomb = bomb;
    }

    @Override
    public void enter() {
        this.bomb.setMaxFrame(BombConstants.getObjectAnimationFrame(this.state));
        this.bomb.setFrameY(BombConstants.getObjectIndex(this.state));
    }

    @Override
    public void update() {

    }

    @Override
    public boolean onAnimationEnd() {
        return this.bomb.getFrameX() >= this.bomb.getMaxFrame() - 1;
    }
}
