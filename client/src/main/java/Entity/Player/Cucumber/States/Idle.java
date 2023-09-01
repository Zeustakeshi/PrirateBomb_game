package Entity.Player.Cucumber.States;


import Entity.Player.Player;
import Entity.Player.PlayerConstants;
import Entity.Player.PlayerStates;

import java.awt.event.KeyEvent;

public class Idle extends PlayerStates {
    public Idle(Player player) {
        super(PlayerConstants.States.IDLE, player);
    }

    @Override
    public void enter() {
        super.enter();
    }


    @Override
    public void update() {
        if (!this.player.onGround()) {
            this.player.setStateAndEmit(PlayerConstants.States.FALL, true);
        }
    }

    @Override
    public void handleInput(KeyEvent key, boolean isPress) {
        if (isPress) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_D -> {
                    this.player.flipAndEmit(1);
                    this.player.setStateAndEmit(PlayerConstants.States.RUN, true);
                }
                case KeyEvent.VK_A ->  {
                    this.player.flipAndEmit(0);
                    this.player.setStateAndEmit(PlayerConstants.States.RUN, true);
                }
                case KeyEvent.VK_K -> this.player.setStateAndEmit(PlayerConstants.States.JUMP, true);
                case KeyEvent.VK_J -> this.player.setStateAndEmit(PlayerConstants.States.ATTACK, true);
            }
        }
    }


}
