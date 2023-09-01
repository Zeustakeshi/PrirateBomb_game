package Entity.Player;

import Entity.Player.PlayerConstants.*;
import Entity.Entity;
import GameStates.GameStates;
import Managers.Bomb.BombManager;
import Managers.States.StateManager;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import javax.print.attribute.standard.JobName;
import java.awt.event.KeyEvent;

public abstract class Player extends Entity {
    protected StateManager<States, PlayerStates> stateManager;

    public Types type;
    private String id;
    private String roomId;
    public BombManager bombManager;

    public Player(Socket socket, BombManager bombManager, Types type, String id, String roomId) {
        super(socket);
        this.stateManager = new StateManager<>();
        this.bombManager = bombManager;
        this.type = type;
        this.id = id;
        this.roomId = roomId;
        this.spriteWidth = PlayerConstants.getSpriteSize(this.type)[0];
        this.spriteHeight = PlayerConstants.getSpriteSize(this.type)[1];
        this.img = PlayerConstants.getSpriteImage(this.type);
    }

    public States getState () {
        return this.stateManager.getCurrentState().state;
    }

    public void handleInput(KeyEvent key, boolean isPress) {
        this.stateManager.getCurrentState().handleInput(key, isPress);
    }

    protected abstract void initStates();

    public void emitState (States state) {
        if (GameStates.state != GameStates.PLAYING) return;
        JSONObject data = new JSONObject();
        JSONObject payload = new JSONObject();

        try {
            payload.put("x", this.hitBox.x);
            payload.put("y", this.hitBox.y);
            payload.put("director", this.director);
            data.put("roomId", this.roomId);
            data.put("playerId", this.id);
            data.put("state", state);
            data.put("payload", payload);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        this.socket.emit("update-state", data.toString());
    }

    public void setState(States state) {
        if (state == States.TURN_LEFT) {
            this.flip(0);
            return;
        }else if (state == States.TURN_RIGHT) {
            this.flip(1);
            return;
        }

        PlayerStates currentState = this.stateManager.getCurrentState();
        if (currentState != null && currentState.state != state)  {
            this.animationTick = 0;
            this.frameX = 0;

        }
        this.stateManager.setState(state, false);
        this.stateManager.getCurrentState().enter();
    }

    public void setState(States state, boolean savePreviousState) {
        if (state == States.TURN_LEFT) {
            this.flip(0);
            return;
        }else if (state == States.TURN_RIGHT) {
            this.flip(1);
            return;
        }

        PlayerStates currentState = this.stateManager.getCurrentState();
        if (currentState != null && currentState.state != state)  {
            this.animationTick = 0;
            this.frameX = 0;

        }
        this.stateManager.setState(state, savePreviousState);
        this.stateManager.getCurrentState().enter();
    }

    public void setStateAndEmit(States state, boolean savePreviousState) {
        this.setState(state, savePreviousState);
        this.emitState (this.stateManager.getCurrentState().state);
    }

    public void setStateAndEmit(States state) {
        this.setState(state);
        this.emitState (this.stateManager.getCurrentState().state);
    }

    public void removeStateAndEmit() {
        this.stateManager.removeState();
        if (this.stateManager.getCurrentState() == null) return;
        this.stateManager.getCurrentState().enter();
        this.emitState (this.stateManager.getCurrentState().state);
    }


    public void flipAndEmit (int director) {
        if (director == this.director) return;
        this.flip(director);

        if (this.director == 0) {
            this.emitState (States.TURN_LEFT);
        }else {
            this.emitState (States.TURN_RIGHT);
        }
    }



    @Override
    public void update() {
        if (this.stateManager.getCurrentState() == null)  return;
        this.stateManager.getCurrentState().update();
        super.update();
    }

    public boolean onGround () {
        return !this.canMoveDown();
    }

    public Types getType() {
        return type;
    }
}
