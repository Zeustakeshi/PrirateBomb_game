package Entity.Player.Bomber.States;

import Entity.Player.Player;
import Entity.Player.PlayerConstants.States;
import Entity.Player.PlayerStates;



import java.awt.event.KeyEvent;

public class Run extends PlayerStates {

    public Run (Player player) {
        super(States.RUN, player);
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
                case KeyEvent.VK_J -> this.player.setStateAndEmit(States.ATTACK);
            }
        }else {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_K -> this.player.setStateAndEmit(States.JUMP);
            }
        }
    }
}
