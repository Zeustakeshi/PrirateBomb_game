package GameStates;

import Entity.Player.Bomber.Bomber;
import Entity.Player.Player;
import Main.Game;
import Managers.Bomb.BombManager;
import Maps.MapManager;
import io.socket.client.Socket;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Testing implements BaseState {
    private Game game;
    private final MapManager mapManager;
    private BombManager bombManager;
    private Player player;
    private Socket socket;
    public Testing (Game game, Socket socket) {
        this.game = game;
        this.socket = socket;

        this.mapManager = new MapManager();
        this.bombManager = new BombManager(this.game, socket);

//        this.player = new Bomber(socket, this.bombManager);
        this.loadMapData();
    }

    private void loadMapData () {
        int[][] mapData = this.mapManager.getCurrentMap().getMapData();
        this.player.loadMapData( mapData);
        this.bombManager.loadMapData(mapData);

    }


    @Override
    public void update() {
       this.player.update();
       this.bombManager.update();
    }

    @Override
    public void draw(Graphics g) {
        this.mapManager.draw(g);
        this.player.draw(g);
        this.bombManager.draw(g);
    }

    @Override
    public void mouseClick(MouseEvent e) {

    }

    @Override
    public void mouseRelease(MouseEvent e) {

    }

    @Override
    public void mouseEnter(MouseEvent e) {

    }

    @Override
    public void keyPress(KeyEvent e) {
        this.player.handleInput(e, true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyRelease(KeyEvent e) {
        this.player.handleInput(e, false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void onMounted() {

    }

    @Override
    public void onUnmounted() {

    }
}
