package Main;

import GameStates.*;
import Managers.Socket.SocketManager;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.awt.*;
import java.net.URI;

public class Game implements Runnable {

    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.8f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 30;
    private final int UPS_SET = 60;

    private SocketManager socketManager;

    // Game state
    private BaseState currentState;

    public Game () {
        this.initSocket();
        this.initClass();
        this.gamePanel.requestFocus();
        this.startGameLoop();

    }

    public void setState(GameStates state) {
        Socket socket = this.socketManager.getSocket();
        this.currentState.onUnmounted();
        switch (state) {
            case TESTING -> this.currentState = new Testing(this, socket);
            case DASHBOARD -> this.currentState = new Dashboard(this, socket);
            case CREATE_ROOM -> this.currentState = new CreateRoom(this,socket);
            case PLAYING -> this.currentState = new Playing(this, socket);
            case JOIN_ROOM -> this.currentState = new JoinRoom(this, socket);
            case WAITING_ROOM -> this.currentState = new WaitingRoom(this, socket);
        }
        this.currentState.onMounted();
        GameStates.state = state;
    }

    public BaseState getCurrentState () {
        return this.currentState;
    }

    private void initSocket () {
        URI uri = URI.create("http://localhost:4000");

        // @formatter:off
        IO.Options options = IO.Options.builder()
                .setTransports(new String[]{"websocket"})
                .build();
        // @formatter:on

        this.socketManager = new SocketManager(uri, options);

        this.socketManager.getSocket().connect();
        this.socketManager.listenError();
    }

    private void initClass () {
        this.currentState = new Dashboard(this, this.socketManager.getSocket());
        this.gamePanel = new GamePanel(this);
        this.gameWindow = new GameWindow(this.gamePanel);
    }

    public void draw (Graphics g) {
        this.currentState.draw(g);
    }

    public void update () {
        this.gamePanel.update();
        this.currentState.update();
    }

    private void startGameLoop() {
        this.gameThread = new Thread(this);
        gameThread.start();
    }



    @Override
    public void run() {
        double nanoTime = 1000000000.0;
        double timePerFrame = nanoTime / this.FPS_SET;
        double timePerUpdate = nanoTime / this.UPS_SET;
        long lastCheck = System.currentTimeMillis();

        long previousTime = System.nanoTime();


        int frames = 0;
        int updates = 0;
        double deltaU = 0;
        double deltaF = 0;



        while (true) {

            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                this.update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                this.gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS:: " +  frames + " |UPS: " + updates);
                frames = 0;
                updates = 0;
            }

        }
    }
}
