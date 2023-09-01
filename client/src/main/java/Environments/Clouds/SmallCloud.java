package Environments.Clouds;

import Main.Game;
import Utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SmallCloud extends Cloud {
    public static int DEFAULT_WIDTH = 74;
    public static int DEFAULT_HEIGHT = 24;

    private  int x, y, width, height, defaultWidth, defaultHeight;
    private BufferedImage image;

    public SmallCloud (int x, int y) {
        this.x = x;
        this.y = y;
        this.setSizeDefault(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.image = LoadSave.loadImage(LoadSave.MAP_ENVIRONMENT_SMALL_CLOUD);
    }

    @Override
    public void setSizeDefault(int defaultWidth, int defaultHeight) {
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.width = (int)(this.defaultWidth * Game.SCALE);
        this.height = (int)(this.defaultHeight * Game.SCALE);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image, this.x, this.y, this.width, this.height, null);
    }

    @Override
    public void update() {

    }

    public static int getWidth() {
        return (int)(DEFAULT_WIDTH * Game.SCALE);
    }

    public static int getWidth(int space) {
        return (int)((DEFAULT_WIDTH + space) * Game.SCALE);
    }

    public static int getHeight() {
        return (int)( DEFAULT_HEIGHT * Game.SCALE);
    }
}
