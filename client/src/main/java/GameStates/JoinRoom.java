package GameStates;

import Main.Game;
import UI.Boards.Board;
import UI.Boards.EnterRoomIdBoard;
import UI.Icons.Icon;
import UI.Icons.SmallIcon;
import UI.Text.SmallText;
import UI.Text.Text;
import UI.button.Button;
import UI.button.SmallButton;
import Utils.LoadSave;
import io.socket.client.Socket;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class JoinRoom implements BaseState {
    private Socket socket;
    private Game game;
    private BufferedImage backgroundImage;
    private Board enterRoomIdBoard;
    private UI.button.Button[][] buttonNumbers;
    private Button buttonEnter, buttonDelete, buttonZero;
    private final int buttonCol = 3;
    private final int buttonRow = 4;

    private Text textRoomId;
    public JoinRoom (Game game, Socket socket) {
        this.game = game;
        this.socket = socket;
        this.backgroundImage = LoadSave.loadImage(LoadSave.PLAYING_BACKGROUND);
        this.enterRoomIdBoard = new EnterRoomIdBoard(this.game, this.socket, 2.4 * Game.SCALE);
        this.initBoard();
        this.textRoomId = new SmallText("", 3);
    }

    private void emitRoomId () {
        String roomId = this.textRoomId.getText().trim();
        if (roomId.isEmpty()) return;
        this.socket.emit("join-room", roomId);

    }

    private void setTextRoomId (String newText) {
        if (this.textRoomId.getText().length() > 4) return;
        this.textRoomId.setText(newText);
    }

    private void initBoard () {

        int boardX = (int)(Game.GAME_WIDTH * 0.5 - this.enterRoomIdBoard.getWidth() * 0.5);
        int boardY = (int) (Game.GAME_HEIGHT * 0.5 - this.enterRoomIdBoard.getHeight() * 0.5);

        this.enterRoomIdBoard.setPosition(boardX, boardY);

        this.buttonNumbers = new Button[this.buttonRow][this.buttonCol];
        int cnt = 1;
        for (int i = 0; i < this.buttonRow - 1; ++i) {
            for (int j = 0; j < this.buttonCol; ++j) {
                this.buttonNumbers[i][j] = new SmallButton(
                        new SmallText(String.valueOf(cnt), 3.5),
                        boardX, boardY, 2
                );
                cnt++;
            }
        }

        this.buttonDelete = new SmallButton(
                new SmallIcon(Icon.Icons.DELETE, 3.5),
                boardX, boardY, 2
        );

        this.buttonEnter =  new SmallButton(
                new SmallIcon(Icon.Icons.NEXT, 3.5),
                boardX, boardY, 2
        );

        this.buttonZero =  new SmallButton(
                new SmallText(String.valueOf(0), 3.5),
                boardX, boardY, 2
        );


        this.buttonNumbers[3][0] = this.buttonDelete;
        this.buttonNumbers[3][1] = this.buttonZero;
        this.buttonNumbers[3][2] = this.buttonEnter;

        for (int i = 0; i < this.buttonRow; ++i) {
            for (int j = 0; j < this.buttonCol; ++j) {
                this.buttonNumbers[i][j].setPosition(
                        (int)(boardX + 110 * j + 90 * Game.SCALE),
                        (int)(boardY + 90 * i + 120 * Game.SCALE)
                );
            }
        }

    }


    @Override
    public void onMounted() {
    }

    @Override
    public void onUnmounted() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        this.enterRoomIdBoard.draw(g);
        for (int i = 0; i < this.buttonRow; ++i) {
            for (int j = 0; j < this.buttonCol; ++j) {
                this.buttonNumbers[i][j].draw(g);
            }
        }
        this.textRoomId.draw(
                g,
                (int)(this.enterRoomIdBoard.getX() +
                        this.enterRoomIdBoard.getWidth() * 0.5 -
                        this.textRoomId.getWidth() * 0.5),
                (int)(this.enterRoomIdBoard.getY() + 68 * Game.SCALE)
        );
    }

    @Override
    public void mouseClick(MouseEvent e) {
        this.enterRoomIdBoard.onMouseClick(e);
        int count = 1;
        for (int i = 0; i < this.buttonRow - 1; ++i) {
            for (int j = 0; j < this.buttonCol; ++j) {
                Button button = this.buttonNumbers[i][j];
                if (button.isMouseEnter(e)) {
                    button.onMouseClick(e);
                    this.setTextRoomId(this.textRoomId.getText() + count);
                }
                count++;
            }
        }
        if (this.buttonZero.isMouseEnter(e)){
            this.buttonZero.onMouseClick(e);
            this.setTextRoomId(this.textRoomId.getText() + 0);
        }
        if (this.buttonDelete.isMouseEnter(e)) {
            this.buttonDelete.onMouseClick(e);
            this.textRoomId.setText("");
        }
        if (this.buttonEnter.isMouseEnter(e)) {
            this.buttonEnter.onMouseClick(e);
            this.emitRoomId();
            this.game.setState(GameStates.WAITING_ROOM);
        }
    }

    @Override
    public void mouseRelease(MouseEvent e) {
        this.enterRoomIdBoard.onMouseRelease(e);
        for (int i = 0; i < this.buttonRow - 1; ++i) {
            for (int j = 0; j < this.buttonCol; ++j) {
                if (this.buttonNumbers[i][j].isMouseEnter(e)) {
                    this.buttonNumbers[i][j].onMouseRelease(e);
                }
            }
        }
        if (this.buttonZero.isMouseEnter(e)){
            this.buttonZero.onMouseRelease(e);
        }
        if (this.buttonDelete.isMouseEnter(e)) {
            this.buttonDelete.onMouseRelease(e);
        }
        if (this.buttonEnter.isMouseEnter(e)) {
            this.buttonEnter.onMouseRelease(e);
        }
    }

    @Override
    public void mouseEnter(MouseEvent e) {
        this.enterRoomIdBoard.onMouseEnter(e);
    }

    @Override
    public void keyPress(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyRelease(KeyEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }


}
