package Entity.Player.Bomber.States;

import Entity.Player.Player;
import Entity.Player.PlayerConstants.*;
import Entity.Player.PlayerStates;

import java.awt.event.KeyEvent;

public class Attack extends PlayerStates {
    public Attack(Player player) {
        super(States.ATTACK, player);
    }

    @Override
    public void enter() {
        super.enter();
        this.player.bombManager.createNew((int)this.player.getHitBox().getX(), (int)this.player.getHitBox().getY());
        System.out.println("create bomb");
    }

    @Override
    public void update() {
        if (this.onAnimationEnd()) this.player.removeStateAndEmit();
    }

    @Override
    public void handleInput(KeyEvent key, boolean isPress) {

    }
}
