package Entity.Player;

import Entity.Entity;
import Managers.States.StateManager;
import Entity.Player.PlayerConstants.States;

import java.awt.event.KeyEvent;

import static Entity.Player.PlayerConstants.*;

public abstract class PlayerStates extends StateManager.BaseState<States> {
    public States state;
    public Player player;
    public PlayerStates(States state, Player player) {
        super(state);
        this.player = player;
        this.state = state;

    }

    @Override
    public void enter() {
        this.player.setFrameY(GetSpriteIndex(this.player.type, this.state));
        this.player.setMaxFrame(GetSpriteAnimationFrame(this.player.type, this.state));
        this.player.setVx(GetSpriteSpeed(this.player.type, this.state));
        if (this.player.getDirector() == 0) this.player.setVx(this.player.getVx() * -1);
    }

    @Override
    public abstract  void update();

    @Override
    public boolean onAnimationEnd() {
        return this.player.getFrameX() >= this.player.getMaxFrame() - 1;
    }

    public abstract void handleInput (KeyEvent key, boolean isPress);

}
