package Entity.Player.Cucumber.States;


import Entity.Player.Player;
import Entity.Player.PlayerConstants;
import Entity.Player.PlayerStates;

import java.awt.event.KeyEvent;

public class Run extends PlayerStates {

    public Run (Player player) {
        super(PlayerConstants.States.RUN, player);
    }


    @Override
    public void enter() {
        super.enter();
    }

    @Override
    public void update() {

    }

    @Override
    public void handleInput(KeyEvent key, boolean isPress) {
        if (!isPress) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_D, KeyEvent.VK_A -> this.player.removeStateAndEmit();
                case KeyEvent.VK_J -> this.player.setStateAndEmit(PlayerConstants.States.ATTACK);
            }
        }else {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_K -> this.player.setStateAndEmit(PlayerConstants.States.JUMP);
            }
        }
    }
}

