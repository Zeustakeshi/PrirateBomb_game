package Entity.Player.Cucumber;


import ColliderBox.*;
import Entity.Player.Cucumber.States.*;
import Entity.Player.Player;
import Entity.Player.PlayerConstants.*;
import Main.Game;
import Managers.Bomb.BombManager;
import io.socket.client.Socket;

import java.awt.*;


public class Cucumber extends Player {
    private final int ATTACK_WIDTH = 50;
    private final int ATTACK_HEIGHT = 50;
    private ColliderBox<RectangleColliderBox>  attackBox;
    public Cucumber(Socket socket, BombManager bombManager, String id, String roomId) {
        super(socket, bombManager,  Types.CUCUMBER, id, roomId);
        this.scale = Game.SCALE;
        this.width = (int)(this.spriteWidth * this.scale);
        this.height = (int)(this.spriteHeight * this.scale);

        this.hitBox.x = 100;
        this.hitBox.y = Game.GAME_HEIGHT - this.height - 300;
        this.hitBox.width = (float) (this.width * 0.5);
        this.hitBox.height = (float) (this.height * 0.8);

        this.maxFrame = 5;
        this.vx = 0;
        this.vy = 0;
        this.weight = (int)(this.height * 0.08);
        this.power = (int)(this.height * 0.2);
        this.health = 100;
        this.maxHealth = 100;
        this.director = 1;
        this.initStates();

        this.attackBox = new RectangleColliderBox();
    }


    @Override
    protected void initStates() {
        this.stateManager.addState(States.IDLE, new Idle(this));
        this.stateManager.addState(States.RUN, new Run(this));
        this.stateManager.addState(States.JUMP, new Jump(this));
        this.stateManager.addState(States.FALL, new Fall(this));
        this.stateManager.addState(States.ATTACK, new Attack(this));
        this.stateManager.setState(States.IDLE);
    }


    @Override
    public void draw(Graphics g) {
        super.draw(g);
        this.attackBox.draw(g, 0, Color.YELLOW);
    }


    @Override
    public void update() {
        super.update();
        int offsetX = this.director == 1 ? 30 : -30;
        this.attackBox.update(
                this.hitBox.x + this.hitBox.width * 0.5 - ATTACK_WIDTH * 0.5 + offsetX,
                this.hitBox.y,
                ATTACK_WIDTH,
                ATTACK_HEIGHT
        );
    }
}
