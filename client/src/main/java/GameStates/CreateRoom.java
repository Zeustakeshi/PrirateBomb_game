package GameStates;

import Entity.Player.PlayerConstants;
import Entity.Player.PlayerDisplayAnimation;
import Main.Game;
import UI.Boards.CreateRoomBoard;
import UI.Icons.Icon;
import UI.Icons.SmallIcon;
import UI.Text.BigText;
import UI.button.Button;
import UI.button.RectangleButton;
import UI.button.SmallButton;
import Utils.LoadSave;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateRoom implements BaseState {

        private Socket socket;
        private CreateRoomBoard createRoomBoard;
        private BufferedImage backgroundImage;
        private BigText textRoomId;
        private Map<String , PlayerDisplayAnimation> players;
        private Game game;
        private Button buttonStart, buttonHome;

    public CreateRoom(Game game, Socket socket) {
        this.socket = socket;
        this.game = game;

        this.backgroundImage = LoadSave.loadImage(LoadSave.PLAYING_BACKGROUND);
        this.players = new HashMap<>();
        this.initBoard();



    }

    private void initBoard () {
        double boardScale = 3.5;
        this.createRoomBoard = new CreateRoomBoard(this.game,this.socket, boardScale);

        int boardX = (int) (Game.GAME_WIDTH * 0.5 - this.createRoomBoard.getWidth() * 0.5);
        int boardY = (int) (Game.GAME_HEIGHT * 0.5 - this.createRoomBoard.getHeight() * 0.5);

        this.createRoomBoard.setPosition(boardX, boardY);


        this.buttonStart = new RectangleButton(
                new BigText("Start" , boardScale * 0.5),
                boardX,
                boardY,
                boardScale * 0.3
        );

        this.buttonHome = new SmallButton(
                new SmallIcon(Icon.Icons.HOME, boardScale ),
                boardX,
                boardY,
                boardScale * 0.5
        );

        this.buttonStart.setPosition(
                boardX  + this.createRoomBoard.getWidth() - this.buttonStart.getWidth() - (int)(28 * Game.SCALE),
                boardY + this.createRoomBoard.getHeight() - this.buttonStart.getHeight() - (int)(15 * Game.SCALE));

        this.buttonHome.setPosition(
                boardX  + (int)(28 * Game.SCALE),
                boardY + this.createRoomBoard.getHeight() - this.buttonHome.getHeight() - (int)(15 * Game.SCALE));
    }


    @Override
    public void onMounted() {
        this.listenServer();
    }

    @Override
    public void onUnmounted() {
        this.removeListener();
    }

    private void removeListener () {
        this.socket.off("new-game");
        this.socket.off("new user join room");
        this.socket.off("user leave room");
        this.socket.off("start-room");
    }

    private void listenServer (){
        this.socket.emit("new-game");

        final CreateRoom _this = this;
        this.socket.on("new-game", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String jsonString = args[0].toString();
                try {

                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray playersArray = jsonObject.getJSONArray("players");

                    String roomId = jsonObject.getString("id");
                    _this.textRoomId = new BigText( roomId,2);

                    for (int i = 0; i < playersArray.length(); i++) {
                        JSONObject playerObject = playersArray.getJSONObject(i);

                        String state = playerObject.getString("state");
                        String type = playerObject.getString("type");
                        String playerId = playerObject.getString("id");
                        _this.players.put (playerId, new PlayerDisplayAnimation(
                                PlayerConstants.Types.valueOf(type),
                                PlayerConstants.States.valueOf(state),
                                1.2 * Game.SCALE
                        ));

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // listen when new user join room
        this.socket.on("new user join room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String jsonString = args[0].toString();
                try {
                    JSONObject playerObject = new JSONObject(jsonString);

                    String state = playerObject.getString("state");
                    String type = playerObject.getString("type");
                    String playerId = playerObject.getString("id");
                    _this.players.put(playerId, new PlayerDisplayAnimation(
                            PlayerConstants.Types.valueOf(type),
                            PlayerConstants.States.valueOf(state),
                            1.2 * Game.SCALE
                    ));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // listen a user leave room
        this.socket.on("user leave room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String playerId = args[0].toString();
                _this.players.remove(playerId);
            }
        });

        // listen start room
        this.socket.on("start-room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                _this.game.setState(GameStates.PLAYING);
            }
        });

    }


    @Override
    public void update() {
        this.createRoomBoard.update();
        for (Map.Entry<String, PlayerDisplayAnimation> player: this.players.entrySet()) {
            player.getValue().update();
        }
    }


    void drawRoomID (Graphics g) {
        if (this.textRoomId == null) return;
        int x = (int) (
                this.createRoomBoard.getX() +
                        this.createRoomBoard.getWidth() * 0.5 -
                        this.textRoomId.getWidth() * 0.5
        );
        int y = (this.createRoomBoard.getY() + this.textRoomId.getHeight() + 20);
        this.textRoomId.draw(g, x, y);

    }

    private void drawPlayer (Graphics g) {
        int posY = (int) (160 * Game.SCALE);
        int posX = (int) (260 * Game.SCALE);
        int index = 0;
        for (Map.Entry<String, PlayerDisplayAnimation> player : this.players.entrySet()) {
            PlayerDisplayAnimation animation = player.getValue();
            animation.draw(g, animation.getWidth() * index + posX + 100 * index, posY) ;
            index++;
        }

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        this.createRoomBoard.draw(g);
        this.buttonStart.draw(g);
        this.buttonHome.draw(g);
        this.drawRoomID(g);
        this.drawPlayer(g);
    }

    @Override
    public void mouseClick(MouseEvent e) {
        this.createRoomBoard.onMouseClick(e);
        if (this.buttonHome.isMouseEnter(e)) {
            this.buttonHome.onMouseClick(e);
            this.socket.emit("cancel-room",this.textRoomId.getText());
            this.game.setState(GameStates.DASHBOARD);
        }
        if (this.buttonStart.isMouseEnter(e)) {
            this.buttonStart.onMouseClick(e);
            this.socket.emit("start-room", this.textRoomId.getText());
            this.socket.emit("enter-room", this.textRoomId.getText());
            this.game.setState(GameStates.PLAYING);
        }
    }

    @Override
    public void mouseRelease(MouseEvent e) {
        if (this.buttonHome.isMouseEnter(e)) this.buttonHome.onMouseRelease(e);
        if (this.buttonStart.isMouseEnter(e)) this.buttonStart.onMouseRelease(e);
    }

    @Override
    public void mouseEnter(MouseEvent e) {
        this.createRoomBoard.onMouseEnter(e);
        if (this.buttonHome.isMouseEnter(e)) this.buttonHome.onMouseEnter(e);
        if (this.buttonStart.isMouseEnter(e)) this.buttonStart.onMouseEnter(e);

    }

    @Override
    public void keyPress(KeyEvent e) {
        this.createRoomBoard.onKeyPress(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyRelease(KeyEvent e) {
        this.createRoomBoard.onKeyRelease(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.createRoomBoard.onMouseDragged(e);

    }

    public Game getGame() {
        return game;
    }
}
