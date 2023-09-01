package Entity.Player.BigGuy.States;

import Entity.Player.Player;
import Entity.Player.PlayerConstants.States;
import Entity.Player.PlayerStates;

import java.awt.event.KeyEvent;

public class Attack extends PlayerStates {
    public Attack(Player player) {
        super(States.ATTACK, player);
    }

    @Override
    public void enter() {
        super.enter();
    }

    @Override
    public void update() {
        if (this.onAnimationEnd()) this.player.removeStateAndEmit();
    }

    @Override
    public void handleInput(KeyEvent key, boolean isPress) {
        if (isPress){

        }else {

        }
    }
}
