package GameStates;

import Entity.Player.PlayerConstants;
import Entity.Player.PlayerDisplayAnimation;
import Environments.Clouds.BigCloud;
import Environments.Clouds.Cloud;
import Environments.Clouds.SmallCloud;
import Main.Game;
import Managers.Bomb.BombManager;
import Managers.Player.PlayerManger;
import Maps.MapManager;
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
import java.util.concurrent.ThreadLocalRandom;

public class Playing  implements BaseState{
    private MapManager mapManager;
    private final PlayerManger playerManger;
    private BombManager bombManager;
    private Socket socket;
    private BufferedImage backgroundImage;
    private final int bigCloudAmount = 3, smallCloudAmount = 6;
    private Cloud[] bigClouds, smallClouds;
    private Game game;
    private String roomId;

    public Playing (Game game, Socket socket) {
        this.socket = socket;
        this.game = game;
        this.initEnvironment();
        this.mapManager = new MapManager();
        this.bombManager = new BombManager(this.game, this.socket);
        this.bombManager.loadMapData(this.mapManager.getCurrentMap().getMapData());
        this.playerManger = new PlayerManger(this.game, this.socket, this.bombManager);

        this.roomId = "";
    }

    @Override
    public void onMounted() {
        Playing _this = this;
        this.socket.on("room-info", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String jsonString = args[0].toString();
                try {
                    JSONObject roomInfo = new JSONObject(jsonString);
                    JSONArray playersArray = roomInfo.getJSONArray("players");
                    String roomId = roomInfo.getString("id");
                    for (int i = 0; i < playersArray.length(); ++i) {
                        JSONObject player = playersArray.getJSONObject(i);
                        String type = player.getString("type");
                        String playerId = player.getString("id");
                        JSONObject position = player.getJSONObject("position");
                        int director = player.getInt("director");
                        int x = position.getInt("x");
                        int y = position.getInt("y");

                        _this.playerManger.addPlayer(roomId, playerId, PlayerConstants.Types.valueOf(type));
                        _this.playerManger.setPlayerPosition(playerId, x, y, director);
                        _this.playerManger.loadMapData(playerId, _this.mapManager.getCurrentMap().getMapData());
                        _this.playerManger.loadMainPlayer(playerId);

                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.playerManger.onMounted();
    }

    @Override
    public void onUnmounted() {
        this.socket.off("room-info");
        this.playerManger.onUnmounted();
    }

    private void initEnvironment () {
        this.backgroundImage = LoadSave.loadImage(LoadSave.PLAYING_BACKGROUND);

        // init big clouds
        this.bigClouds = new BigCloud[this.bigCloudAmount];
        for (int i = 0; i < this.bigClouds.length; ++i) {
            this.bigClouds[i] = new BigCloud(
                    BigCloud.getWidth() * i,
                    (int)(204 * Game.SCALE)
            );
        }

        // init small clouds
        this.smallClouds = new SmallCloud[this.smallCloudAmount];
        for (int i = 0; i < this.smallClouds.length; ++i) {
            int smallCloudSpace = ThreadLocalRandom.current().nextInt(100, (int)(Game.GAME_WIDTH * 0.3));
            int randomHeight = ThreadLocalRandom.current().nextInt(120, 180);
            this.smallClouds[i] = new SmallCloud(
                    100 + (SmallCloud.getWidth(smallCloudSpace)) * i ,
                    (int)(randomHeight * Game.SCALE)
            );
        }


    }

    private void drawEnvironment (Graphics g) {
        // draw background
        g.drawImage(this.backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        // draw big clouds
        for (Cloud bigCloud : this.bigClouds) {
            bigCloud.draw(g);
        }
        //draw small clouds
        for (Cloud smallCloud : this.smallClouds) {
            smallCloud.draw(g);
        }

    }


    @Override
    public void update() {
        this.mapManager.update();
        this.playerManger.update();
        this.bombManager.update();
    }

    @Override
    public void draw(Graphics g) {
        this.drawEnvironment(g);
        this.mapManager.draw(g);
        this.playerManger.draw(g);
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
        this.playerManger.handleInput(e, true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyRelease(KeyEvent e) {
        this.playerManger.handleInput(e, false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }



    public Game getGame() {
        return game;
    }
}
