package Entity;

import ColliderBox.RectangleColliderBox;
import Entity.Player.PlayerConstants.Types;
import Main.Game;
import Utils.HelperMethods;
import Utils.LoadSave;
import io.socket.client.Socket;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int frameX, frameY, maxFrame, vx, vy;
    protected RectangleColliderBox hitBox;
    protected int spriteWidth, spriteHeight;
    protected double spriteX, spriteY, scale;
    protected int animationSpeed, animationTick;
    protected int director;
    protected int weight, width, height;
    protected double offsetX, offsetY;
    protected double health, maxHealth;
    protected BufferedImage img;
    protected int power;
    protected int[][] mapData;
    protected Types type;
    protected Socket socket;

    public Entity(Socket socket) {
        this.socket = socket;
        this.loadDefaultProperties();
    }
    public void setPosition (int x, int y, int director) {
        this.hitBox.x = x;
        this.hitBox.y = y;
        this.flip(director);

    }


    protected void loadDefaultProperties() {

        this.director = 1;
        this.frameX = 0;
        this.frameY = 0;
        this.animationTick = 0;
        this.animationSpeed = 5;
        this.maxFrame = 1;
        this.vx = 0;
        this.vy = 0;
        this.scale = 1;
        this.weight = 20;
        this.spriteX = 0;
        this.spriteY = 0;
        this.offsetX = 0;
        this.offsetY = 0;
        this.health = 0;
        this.maxHealth = 100;
        this.hitBox = new RectangleColliderBox();
    }


    public void flip (int director) {
        if (this.director == director) return;

        this.spriteX += this.width;
        this.width *= -1;
        this.director = director;
        this.vx *= -1;
    }


    protected void updateSpritePos () {
        this.spriteX =
                this.hitBox.x -
                        this.width * 0.5 +
                        this.hitBox.width * 0.5 +
                        this.offsetX;
        this.spriteY =
                this.hitBox.y -
                        this.height * 0.5 +
                        this.hitBox.height * 0.5 +
                        this.offsetY;
    }


    public void draw (Graphics g) {
        BufferedImage subImage = this.img.getSubimage(
                this.spriteWidth * this.frameX,
                this.spriteHeight * this.frameY,
                this.spriteWidth,
                this.spriteHeight
        );

        g.drawImage(
                subImage,
                (int)this.spriteX,
                (int)this.spriteY,
                this.width,
                this.height,
                null
        );
        this.hitBox.draw(g, 0, Color.RED);

    }

    public void update() {
        this.animationTick++;
        if (this.animationTick >= this.animationSpeed) {
            this.animationTick = 0;
            if (this.frameX < this.maxFrame - 1) {
                this.frameX++;
            }else {
                this.frameX = 0;
            }
        }
        this.updateSpritePos();
        this.checkCanMove();

        this.hitBox.x += this.vx;
        if (this.canMoveDown()) {
            this.hitBox.y += (this.vy + this.weight);
        }else {
            this.hitBox.y += (this.vy);
        }
    }

    public boolean canMoveLeft () {
        double indexX = this.hitBox.x + this.vx;
        double indexY = this.hitBox.y + this.vy;
        return indexX > 0 &&
                !HelperMethods.isSolid(indexX, indexY, this.mapData) &&
                !HelperMethods.isSolid(indexX, indexY + this.hitBox.height, this.mapData);
    }

    public boolean canMoveRight () {
        double indexX = this.hitBox.x + this.hitBox.width + this.vx;
        double indexY = this.hitBox.y + this.vy;
        int maxWidth = (this.mapData[0].length - 1) * Game.TILES_SIZE;
        return indexX < maxWidth &&
                !HelperMethods.isSolid(indexX, indexY, this.mapData) &&
                !HelperMethods.isSolid(indexX, indexY + this.hitBox.height, this.mapData);
    }

    public boolean canMoveUp () {
        double indexX = this.hitBox.x + this.vx;
        double indexY = this.hitBox.y + this.vy;
        return indexY > 0 &&
                !HelperMethods.isSolid(indexX, indexY, this.mapData) &&
                !HelperMethods.isSolid(indexX + this.hitBox.width, indexY, this.mapData);
    }

    public boolean canMoveDown () {
        double indexX = this.hitBox.x + this.vx;
        double indexY = this.hitBox.y + this.hitBox.height + this.vy + this.weight;
        return indexY  < Game.GAME_HEIGHT &&
                !HelperMethods.isSolid(indexX, indexY, this.mapData) &&
                !HelperMethods.isSolid(indexX + this.hitBox.width, indexY, this.mapData);
    }

    public void loadMapData(int[][] mapData) {
        this.mapData = mapData;
    }

    protected void checkCanMove () {

        if(this.vx > 0 && !this.canMoveRight()) {
            this.vx = 0;
        }
        if (this.vx < 0 && !this.canMoveLeft()) {
            this.vx = 0;
        }
        if (this.vy < 0 && !this.canMoveUp()) {
            this.vy = 0;
        }
        if (this.vy > 0 && !this.canMoveDown()) {
            this.vy = 0;
        }
    }

    public RectangleColliderBox getHitBox() {
        return hitBox;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int[][] getMapData() {
        return mapData;
    }

    public void setFrameY(int frameY) {
        this.frameY = frameY;
    }

    public void setMaxFrame(int maxFrame) {
        this.maxFrame = maxFrame;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getPower() {
        return power;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }


    public int getDirector() {
        return director;
    }

    public int getMaxFrame() {
        return maxFrame;
    }

    public int getFrameX() {
        return frameX;
    }

    public abstract void handleInput(KeyEvent key, boolean isPress);
}
