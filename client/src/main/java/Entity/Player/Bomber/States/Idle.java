package Entity.Player.Bomber.States;

import Entity.Player.Player;
import Entity.Player.PlayerStates;
import static Entity.Player.PlayerConstants.*;

import java.awt.event.KeyEvent;

public class Idle extends PlayerStates {
    public Idle(Player player) {
        super(States.IDLE, player);
    }

    @Override
    public void enter() {
        super.enter();
    }


    @Override
    public void update() {
        if (!this.player.onGround()) {
            this.player.setStateAndEmit(States.FALL, true);
        }
    }

    @Override
    public void handleInput(KeyEvent key, boolean isPress) {
        if (isPress) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_D -> {
                    this.player.flipAndEmit(1);
                    this.player.setStateAndEmit(States.RUN, true);
                }
                case KeyEvent.VK_A ->  {
                    this.player.flipAndEmit(0);
                    this.player.setStateAndEmit(States.RUN, true);
                }
                case KeyEvent.VK_K -> this.player.setStateAndEmit(States.JUMP, true);
                case KeyEvent.VK_J -> this.player.setStateAndEmit(States.ATTACK, true);
            }
        }
    }


}
