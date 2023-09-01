package Entity.Player.BigGuy.States;


import Entity.Player.Player;
import Entity.Player.PlayerConstants;
import Entity.Player.PlayerStates;

import java.awt.event.KeyEvent;

public class Fall extends PlayerStates {
    public Fall( Player player) {
        super(PlayerConstants.States.FALL, player);
    }

    @Override
    public void enter() {
        super.enter();
        if (!this.player.onGround()) this.player.setVy(this.player.getWeight());
        if (!this.player.canMoveLeft() || !this.player.canMoveRight()) this.player.setVx(0);
    }

    @Override
    public void update() {
        if (this.player.onGround()) this.player.removeStateAndEmit();
    }

    @Override
    public void handleInput(KeyEvent key, boolean isPress) {
        if (isPress) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_A -> {
                    this.player.flipAndEmit(0);
                }
                case KeyEvent.VK_D -> {
                    this.player.flipAndEmit(1);
                }
            }
        }
    }
}
