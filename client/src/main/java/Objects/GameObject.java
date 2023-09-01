package Objects;

import ColliderBox.RectangleColliderBox;
import Main.Game;
import Utils.HelperMethods;
import Utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected int frameX, frameY, maxFrame, vx, vy;
    protected RectangleColliderBox colliderBox;
    protected int objectWidth, objectHeight;
    protected double objectX, objectY, scale, offSetX, offSetY;
    protected int animationSpeed, animationTick;
    protected int weight, width, height;
    protected BufferedImage img;
    protected int[][] mapData;
    
    public GameObject () {
        this.loadDefaultProperties();
    }

    protected void loadDefaultProperties() {
        this.frameY = 1;
        this.frameX = 0;
        this.animationTick = 0;
        this.animationSpeed = 5;
        this.maxFrame = 1;
        this.scale = 1;
        this.weight = 20;
        this.objectX = 0;
        this.objectY = 0;
        this.vx = 0;
        this.vy = 0;
        this.offSetY = 0;
        this.offSetX = 0;
        this.colliderBox = new RectangleColliderBox();
    }

    protected void loadImage (String path) {
        this.img = LoadSave.loadImage(path);
    }

    protected void updateObjectPos() {
        this.objectX =
                this.colliderBox.x -
                        this.width * 0.5 +
                        this.colliderBox.width * 0.5 + this.offSetX;
        this.objectY =
                this.colliderBox.y -
                        this.height * 0.5 +
                        this.colliderBox.height * 0.5 + this.offSetY;
    }

    public void draw (Graphics g) {
        BufferedImage subImage = this.img.getSubimage(
                this.objectWidth * this.frameX,
                this.objectHeight * this.frameY,
                this.objectWidth,
                this.objectHeight
        );

        g.drawImage(
                subImage,
                (int)this.objectX,
                (int)this.objectY,
                this.width,
                this.height,
                null
        );
        this.colliderBox.draw(g, 0, Color.ORANGE);

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
        this.checkCanMove();

        this.colliderBox.x += this.vx;
        if (this.canMoveDown()) {
            this.colliderBox.y += (this.vy + this.weight);
        }else {
            this.colliderBox.y += (this.vy);
        }

        this.updateObjectPos();

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

    public void loadMapData(int[][] mapData) {
        this.mapData = mapData;
    }
    
    public boolean canMoveLeft () {
        double indexX = this.colliderBox.x;
        double indexY = this.colliderBox.y;
        return indexX > 0 &&
                !HelperMethods.isSolid(indexX, indexY, this.mapData) &&
                !HelperMethods.isSolid(indexX, indexY + this.colliderBox.height, this.mapData);
    }

    public boolean canMoveRight () {
        double indexX = this.colliderBox.x + this.colliderBox.width;
        double indexY = this.colliderBox.y;
        int maxWidth = (this.mapData[0].length - 1) * Game.TILES_SIZE;
        return indexX < maxWidth &&
                !HelperMethods.isSolid(indexX, indexY, this.mapData) &&
                !HelperMethods.isSolid(indexX, indexY + this.colliderBox.height, this.mapData);
    }

    public boolean canMoveUp () {
        double indexX = this.colliderBox.x;
        double indexY = this.colliderBox.y;
        return indexY > 0 &&
                !HelperMethods.isSolid(indexX, indexY, this.mapData) &&
                !HelperMethods.isSolid(indexX + this.colliderBox.width, indexY, this.mapData);
    }

    public boolean canMoveDown () {
        double indexX = this.colliderBox.x;
        double indexY = this.colliderBox.y + this.colliderBox.height ;
        return indexY  < Game.GAME_HEIGHT &&
                !HelperMethods.isSolid(indexX, indexY, this.mapData) &&
                !HelperMethods.isSolid(indexX + this.colliderBox.width, indexY, this.mapData);
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

    public void setFrameY(int frameY) {
        this.frameY = frameY;
    }

    public void setMaxFrame(int maxFrame) {
        this.maxFrame = maxFrame;
    }
    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getMaxFrame() {
        return maxFrame;
    }

    public int getFrameX() {
        return frameX;
    }

    public RectangleColliderBox getColliderBox() {
        return colliderBox;
    }
}


