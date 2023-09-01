package Objects.Bomb.States;

import Objects.Bomb.Bomb;
import Objects.Bomb.BombConstants.*;

public class Detonation extends BombStates {
    public Detonation(Bomb bomb) {
        super(States.DETONATION, bomb);
    }

    @Override
    public void enter() {
        super.enter();
    }

    @Override
    public void update() {
        super.update();
//        if (this.onAnimationEnd()) this.bomb.setStateAndEmit(States.IDLE);
    }
}
