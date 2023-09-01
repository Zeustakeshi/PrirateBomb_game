package Entity.Player;

import Entity.Entity;
import Managers.Bomb.BombManager;
import io.socket.client.Socket;

import java.awt.event.KeyEvent;

public class BaldPirate extends Player {
    public BaldPirate(Socket socket, BombManager bombManager, String id, String roomId) {
        super(socket, bombManager,  PlayerConstants.Types.BALD_PIRATE, id, roomId);
    }

    @Override
    public void handleInput(KeyEvent key, boolean isPress) {

    }

    @Override
    protected void initStates() {

    }

}
