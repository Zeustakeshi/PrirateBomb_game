package Entity.Player.Bomber;

import Entity.Player.Bomber.States.*;
import Entity.Player.Player;
import Main.Game;
import Managers.Bomb.BombManager;
import io.socket.client.Socket;
import Entity.Player.PlayerConstants.*;



public class Bomber extends Player{
    public Bomber (Socket socket, BombManager bombManager, String id, String roomId) {
        super(socket, bombManager, Types.BOMBER, id, roomId);
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
        this.initStates();
    }




    @Override
    protected void initStates () {
        this.stateManager.addState(States.IDLE, new Idle(this));
        this.stateManager.addState(States.RUN, new Run(this));
        this.stateManager.addState(States.JUMP, new Jump(this));
        this.stateManager.addState(States.FALL, new Fall(this));
        this.stateManager.addState(States.ATTACK, new Attack(this));
        this.setStateAndEmit(States.IDLE);
    }

}
