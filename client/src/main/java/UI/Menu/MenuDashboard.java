package UI.Menu;

import GameStates.GameStates;
import Main.Game;
import UI.Text.BigText;
import UI.button.Button;
import UI.button.RectangleButton;
import Utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/*
*       Create
*       Join
*       Options
*       Help
*       Exit
*
* */
/**********************/

public class MenuDashboard implements Menu {
    private Button buttonCreateRoom, buttonJoinRoom, buttonOptions, buttonHelp, buttonExit;
    private BufferedImage backgroundImage;
    private int backgroundWidth, backgroundHeight;
    private int x, y;
    private double scale;
    private Game game;
    public MenuDashboard (Game game) {
        this.game = game;
        this.scale = 1.7 * Game.SCALE;
        this.loadBackground();
        this.init();
        this.loadButtons();

    }


    private void init () {
        this.x = (int) (Game.GAME_WIDTH * 0.5 - this.backgroundWidth * 0.5);
        this.y = (int) (Game.GAME_HEIGHT * 0.5 - this.backgroundHeight * 0.5);
    }

    @Override
    public void loadButtons() {
        this.buttonCreateRoom = new RectangleButton(
                new BigText("new room", this.scale * 0.6),
                (int) (this.x + 80 * Game.SCALE),
                (int)(this.y + 100 * Game.SCALE),
                this.scale * 0.6
        );
        this.buttonJoinRoom = new RectangleButton(
                new BigText("Join room", this.scale * 0.6),
                (int) (this.x + 80 * Game.SCALE),
                (int)(this.y + 160 * Game.SCALE),
                this.scale * 0.6
        );
        this.buttonOptions = new RectangleButton(
                new BigText("Options", this.scale * 0.6),
                (int) (this.x + 80 * Game.SCALE),
                (int)(this.y + 220 * Game.SCALE),
                this.scale * 0.6
        );
        this.buttonHelp = new RectangleButton(
                new BigText("Helps", this.scale * 0.6),
                 (int) (this.x + 80 * Game.SCALE),
                (int)(this.y + 280 * Game.SCALE),
                this.scale * 0.6
        );
        this.buttonExit = new RectangleButton(
                new BigText("Exit", this.scale * 0.6),
                 (int) (this.x + 80 * Game.SCALE),
                (int)(this.y + 340 * Game.SCALE),
                this.scale * 0.6
        );
    }

    @Override
    public void loadBackground() {
        this.backgroundImage = LoadSave.loadImage(LoadSave.DASHBOARD_MENU);
        this.backgroundWidth = (int)(this.backgroundImage.getWidth() * this.scale);
        this.backgroundHeight = (int)(this.backgroundImage.getHeight() * this.scale);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.backgroundImage, this.x, this.y, this.backgroundWidth, this.backgroundHeight, null);
        this.buttonCreateRoom.draw(g);
        this.buttonJoinRoom.draw(g);
        this.buttonOptions.draw(g);
        this.buttonHelp.draw(g);
        this.buttonExit.draw(g);

    }

    @Override
    public void draw(Graphics g, int offset) {

    }

    @Override
    public void update() {

    }

    @Override
    public void onMouseClick(MouseEvent e) {
        if (this.buttonJoinRoom.isMouseEnter(e)) {
            this.buttonJoinRoom.onMouseClick(e);
            this.game.setState(GameStates.JOIN_ROOM);
        }
        if (this.buttonCreateRoom.isMouseEnter(e)) {
            this.buttonCreateRoom.onMouseClick(e);
            this.game.setState(GameStates.CREATE_ROOM);
        }
        if (this.buttonHelp.isMouseEnter(e)) {
            this.buttonHelp.onMouseClick(e);
            this.game.setState(GameStates.HELP);
        }
        if (this.buttonOptions.isMouseEnter(e)) {
            this.buttonOptions.onMouseClick(e);
            this.game.setState(GameStates.OPTIONS);
        }
        if (this.buttonExit.isMouseEnter(e)) {
            this.buttonExit.onMouseClick(e);
            this.game.setState(GameStates.QUIT);
        }
    }

    @Override
    public void onMouseRelease(MouseEvent e) {
        this.buttonJoinRoom.onMouseRelease(e);
        this.buttonCreateRoom.onMouseRelease(e);
        this.buttonHelp.onMouseRelease(e);
        this.buttonOptions.onMouseRelease(e);
        this.buttonExit.onMouseRelease(e);

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
}
