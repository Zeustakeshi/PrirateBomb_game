package Managers.Bomb;

import Main.Game;
import Objects.Bomb.Bomb;
import io.socket.client.Socket;

import java.awt.*;
public class BombManager {
    private Bomb currentbomb;
    private int[][] mapData;
    private Socket socket;
    private Game game;
    private String roomId;

    public BombManager (Game game, Socket socket) {
        this.socket = socket;
        this.game = game;
    }

    public void loadMapData (int[][] mapData) {
        this.mapData = mapData;
    }

    public void createNew (int x, int y) {
        this.currentbomb = new Bomb(x, y, Game.SCALE);
        this.currentbomb.loadMapData(this.mapData);
    }

    public Bomb getCurrentbomb() {
        return currentbomb;
    }

    public void draw (Graphics g) {
        if (this.currentbomb == null) return;
        this.currentbomb.draw(g);
    }

    public void update () {
        if (this.currentbomb == null) return;
        this.currentbomb.update();
    }

}
