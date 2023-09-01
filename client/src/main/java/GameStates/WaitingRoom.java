package GameStates;

import Entity.Player.Player;
import Entity.Player.PlayerConstants;
import Entity.Player.PlayerDisplayAnimation;
import Main.Game;
import UI.Boards.WaitingBoard;
import UI.Icons.Icon;
import UI.Icons.SmallIcon;
import UI.button.Button;
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

public class WaitingRoom implements BaseState {

    private Game game;
    private Socket socket;
    private BufferedImage backgroundImage;
    private WaitingBoard waitingBoard;
    private Map<String ,PlayerDisplayAnimation> players;
    private Button buttonHome;
    private String roomId;

    public WaitingRoom (Game game, Socket socket) {
        this.game = game;
        this.socket = socket;
        this.backgroundImage = LoadSave.loadImage(LoadSave.PLAYING_BACKGROUND);
        this.initBoard();
        this.players = new HashMap<>();
        this.roomId = "";

    }

    private void initBoard () {
        double boardScale = 3.5;

        this.waitingBoard = new WaitingBoard(this.game,  boardScale);

        int boardX = (int) (Game.GAME_WIDTH * 0.5 - this.waitingBoard.getWidth() * 0.5);
        int boardY = (int) (Game.GAME_HEIGHT * 0.5 - this.waitingBoard.getHeight() * 0.5);


        this.waitingBoard.setPosition(boardX, boardY);
        this.buttonHome = new SmallButton(
                new SmallIcon(Icon.Icons.HOME, boardScale ),
                boardX,
                boardY,
                boardScale * 0.5
        );
        this.buttonHome.setPosition(
                boardX + (int)(28 * Game.SCALE),
                boardY + this.waitingBoard.getHeight() - this.buttonHome.getHeight() - (int)(15 * Game.SCALE)
        );
    }

    @Override
    public void onMounted() {
        final WaitingRoom _this = this;
        this.socket.on("join-room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String jsonString = args[0].toString();
                try {
                    JSONObject roomInfo = new JSONObject(jsonString);
                    _this.roomId = roomInfo.getString("id");
                    JSONArray playersArray = roomInfo.getJSONArray("players");

                    for (int i = 0; i < playersArray.length(); ++i) {
                        JSONObject player = playersArray.getJSONObject(i);

                        String state = player.getString("state");
                        String type = player.getString("type");
                        String playerId = player.getString("id");
                        _this.players.put(playerId, new PlayerDisplayAnimation(
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

        // listen when a user leave room
        this.socket.on("user leave room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String playerId = args[0].toString();
                _this.players.remove(playerId);
            }
        });

        // listen on room cancel
        this.socket.on("cancel-room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String playerId = args[0].toString();
                _this.game.setState(GameStates.DASHBOARD);
            }
        });

        // listen on room cancel
        this.socket.on("start-room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                _this.socket.emit("enter-room", _this.roomId);
                _this.game.setState(GameStates.PLAYING);
            }
        });
    }

    @Override
    public void onUnmounted() {
        this.socket.off("join-room");
        this.socket.off("new user join room");
        this.socket.off ("user leave room");
        this.socket.off("cancel-room");
        this.socket.off("start-room");
    }

    @Override
    public void update() {
        this.waitingBoard.update();
        for (Map.Entry<String, PlayerDisplayAnimation> player: this.players.entrySet()) {
            player.getValue().update();
        }
    }
    private void drawPlayer (Graphics g) {
        int posY = (int) (160 * Game.SCALE);
        int posX = (int) (260 * Game.SCALE);
        int index = 0;
        for (Map.Entry<String, PlayerDisplayAnimation> player: this.players.entrySet()) {
            PlayerDisplayAnimation animation = player.getValue();
            player.getValue().draw(g, animation.getWidth() * index + posX + 100 * index, posY);
            index++;
        }


    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        this.waitingBoard.draw(g);
        this.buttonHome.draw(g);
        this.drawPlayer(g);


    }

    @Override
    public void mouseClick(MouseEvent e) {
        if (this.buttonHome.isMouseEnter(e)) {
            this.buttonHome.onMouseClick(e);
            this.buttonHome.resetState();
            this.socket.emit("leave-room", this.roomId);
            this.game.setState(GameStates.DASHBOARD);
        }
    }

    @Override
    public void mouseRelease(MouseEvent e) {

    }

    @Override
    public void mouseEnter(MouseEvent e) {

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
