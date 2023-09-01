package GameStates;

import Main.Game;
import UI.Menu.MenuDashboard;
import UI.Text.BigText;
import UI.button.RectangleButton;
import Utils.LoadSave;
import io.socket.client.Socket;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Dashboard implements BaseState {
    MenuDashboard menuDashboard;
    BufferedImage backgroundImage;
    private Socket socket;
    private Game game;
    public Dashboard (Game game, Socket socket) {
        this.backgroundImage = LoadSave.loadImage(LoadSave.BACKGROUND_MENU);
        this.socket = socket;
        this.game = game;
        this.menuDashboard = new MenuDashboard(this.game);
    }

    @Override
    public void update() {
        this.menuDashboard.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        this.menuDashboard.draw(g);

    }

    @Override
    public void mouseClick(MouseEvent e) {
        this.menuDashboard.onMouseClick(e);
    }

    @Override
    public void mouseRelease(MouseEvent e) {
        this.menuDashboard.onMouseRelease(e);
    }

    @Override
    public void mouseEnter(MouseEvent e) {
        this.menuDashboard.onMouseEnter(e);
    }

    @Override
    public void keyPress(KeyEvent e) {
        this.menuDashboard.onKeyPress(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyRelease(KeyEvent e) {
        this.menuDashboard.onKeyRelease(e);
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

    public Game getGame() {
        return game;
    }
}
