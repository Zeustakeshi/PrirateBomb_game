package Managers.Player;

import Entity.Entity;
import Entity.Player.BaldPirate;
import Entity.Player.BigGuy.BigGuy;
import Entity.Player.Bomber.Bomber;
import Entity.Player.Cucumber.Cucumber;
import Entity.Player.Player;
import Entity.Player.PlayerConstants;
import Entity.Player.PlayerDisplayAnimation;
import Main.Game;
import Managers.Bomb.BombManager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class PlayerManger {
    private Game game;
    private Socket socket;
    private final Map<String, Player> players;
    private Player mainPlayer;

    private BombManager bombManager;

    public PlayerManger (Game game, Socket socket, BombManager bombManager) {
        this.bombManager = bombManager;
        this.game = game;
        this.socket = socket;
        this.players = new HashMap<>();
    }

        public void addPlayer (String roomId, String playerId, PlayerConstants.Types type) {
            this.players.put(playerId, this.getPlayerObject(type, playerId, roomId ));
    }


    public void onMounted () {
        PlayerManger _this = this;
        this.socket.on("update-state", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String jsonString = args[0].toString();
                try {
                    JSONObject playerObject = new JSONObject(jsonString);
                    String state = playerObject.getString("state");
                    String playerId = playerObject.getString("playerId");
                    if (Objects.equals(playerId, _this.socket.id())) return;
                    JSONObject payload = playerObject.getJSONObject("payload");
                    int posX = payload.getInt("x");
                    int posY = payload.getInt("y");
                    int director = payload.getInt("director");

                    _this.setPlayerPosition(playerId, posX, posY, director);
                    _this.players.get(playerId).setState(PlayerConstants.States.valueOf(state));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void onUnmounted () {
        this.socket.off("update-state");
    }

    public void setPlayerPosition (String playerId, int x, int y, int director) {
        this.players.get(playerId).setPosition(x, y, director);
    }

    public void loadMapData (String playerId, int[][] mapData) {
        this.players.get(playerId).loadMapData(mapData);
    }

    public void loadMainPlayer (String playerId) {
        if (Objects.equals(this.socket.id(), playerId)) this.mainPlayer = this.players.get(playerId);
    }

    private Player getPlayerObject (PlayerConstants.Types type, String id, String roomId) {
        return switch (type) {
            case BOMBER -> new Bomber(this.socket, this.bombManager, id, roomId);
            case CUCUMBER -> new Cucumber(this.socket, this.bombManager, id, roomId);
            case BIG_GUY -> new BigGuy(this.socket,this.bombManager, id, roomId);
            case BALD_PIRATE -> null;
        };
    }

    public void draw (Graphics g) {
        if (this.players.isEmpty()) return;
        for (Map.Entry<String, Player> item : this.players.entrySet()) {
            Player player = item.getValue();
            if (player == null) return;
            player.draw(g);
        }
    }

    public void update () {
        if (this.players.isEmpty()) return;
        for (Map.Entry<String, Player> item: this.players.entrySet()) {

            Player player = item.getValue();
            if (player == null) return;
            player.update();
        }
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void handleInput (KeyEvent key, boolean isPress) {
        this.mainPlayer.handleInput(key, isPress);
    }

}
