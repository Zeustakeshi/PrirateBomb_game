package UI.Boards;

import GameStates.GameStates;
import Main.Game;
import UI.Icons.Icon;
import UI.Icons.SmallIcon;
import UI.Text.BigText;
import UI.button.Button;
import UI.button.RectangleButton;
import UI.button.SmallButton;
import Utils.LoadSave;
import io.socket.client.Socket;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CreateRoomBoard implements Board {
    private int defaultWidth, defaultHeight, width, height, x, y;
    private BufferedImage image;

    private Game game;
    private Socket socket;
    public CreateRoomBoard(Game game, Socket socket, double scale) {
        this.socket = socket;
        this.game = game;
        this.defaultWidth = 232;
        this.defaultHeight = 188;
        this.width = (int)(this.defaultWidth * scale);
        this.height = (int)(this.defaultHeight * scale);
        this.image = LoadSave.loadImage(LoadSave.CREATE_ROOM_BOARD);
        this.x = 0;
        this.y = 0;

    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image,this.x , this.y, this.width, this.height, null);

    }


    @Override
    public void update() {

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void onMouseClick(MouseEvent e) {

    }

    @Override
    public void onMouseRelease(MouseEvent e) {

    }

    @Override
    public void onMouseEnter(MouseEvent e) {

    }

    @Override
    public void onMouseDragged(MouseEvent e) {

    }

    @Override
    public void onKeyPress(KeyEvent e) {

    }

    @Override
    public void onKeyRelease(KeyEvent e) {

    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
