package Entity.Player.BigGuy.States;

import Entity.Player.Player;
import Entity.Player.PlayerConstants;
import Entity.Player.PlayerStates;

import java.awt.event.KeyEvent;

public class Jump extends PlayerStates {
    public Jump(Player player) {
        super(PlayerConstants.States.JUMP, player);
    }

    @Override
    public void enter() {
        if (!this.player.onGround()) return;
        super.enter();
        this.player.setVy(-1 * this.player.getPower());
        if (!this.player.canMoveLeft() || !this.player.canMoveRight()) this.player.setVx(0);
    }

    @Override
    public void update() {
        if (this.player.getVy() >= 0) this.player.setStateAndEmit(PlayerConstants.States.FALL);
        else this.player.setVy(this.player.getVy() + 1);
    }

    @Override
    public void handleInput (KeyEvent key, boolean isPress) {
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
