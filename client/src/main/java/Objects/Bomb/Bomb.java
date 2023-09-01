package Objects.Bomb;

import Managers.States.StateManager;
import Objects.Bomb.States.BombStates;
import Objects.Bomb.States.Detonation;
import Objects.Bomb.States.Idle;
import Objects.GameObject;
import Utils.LoadSave;
import Objects.Bomb.BombConstants.*;
import io.socket.client.Socket;

public class Bomb extends GameObject {

    private StateManager<States, BombStates> stateManager;
    private int countDown;
    public Bomb( int x, int y, double scale) {
        super();
        this.scale = scale;
        this.objectWidth = 96;
        this.objectHeight = 108;
        this.width = (int)(this.objectWidth * this.scale);
        this.height = (int)(this.objectHeight * this.scale);
        this.colliderBox.x = x;
        this.colliderBox.y = y;
        this.colliderBox.width = 50;
        this.colliderBox.height = 50;
        this.offSetY = -35;

        this.countDown = 100;

        this.loadImage(LoadSave.OBJECT_BOMB);
        this.initStates();
    }

    private void initStates () {
        this.stateManager = new StateManager<>();
        this.stateManager.addState(States.IDLE, new Idle(this));
        this.stateManager.addState(States.DETONATION, new Detonation(this));
        this.setStateAndEmit(States.DETONATION);
    }

    public void emitState (States state) {
//        this.socket.emit();
    }

    public void setState(States state, boolean savePreviousState) {

        BombStates currentState = this.stateManager.getCurrentState();
        if (currentState != null && currentState.state != state)  {
            this.animationTick = 0;
            this.frameX = 0;

        }
        this.stateManager.setState(state, savePreviousState);
        this.stateManager.getCurrentState().enter();
    }

    public void setState(States state ) {

        BombStates currentState = this.stateManager.getCurrentState();
        if (currentState != null && currentState.state != state)  {
            this.animationTick = 0;
            this.frameX = 0;

        }
        this.stateManager.setState(state, false);
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
        this.stateManager.getCurrentState().enter();
        this.emitState (this.stateManager.getCurrentState().state);
    }

    @Override
    public void update() {
        super.update();
        this.stateManager.getCurrentState().update();
        if (this.countDown == 0) {
            this.setState(States.IDLE);
        }else {
            this.countDown = this.countDown - 1;
        }
    }
}
