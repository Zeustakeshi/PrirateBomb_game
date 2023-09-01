package Entity.Player;
import ColliderBox.RectangleColliderBox;
import Entity.Player.PlayerConstants.*;
import Utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerDisplayAnimation {

    protected int frameX, frameY, maxFrame;
    protected int spriteWidth, spriteHeight;
    protected double spriteX, spriteY, scale;
    protected int animationSpeed, animationTick;
    protected int director;
    protected int width;
    protected int height;
    protected BufferedImage img;

    public PlayerDisplayAnimation (Types type, States state, double scale) {
        this.director = 1;
        this.frameX = 0;
        this.maxFrame = PlayerConstants.GetSpriteAnimationFrame(type, state);
        this.frameY = PlayerConstants.GetSpriteIndex(type, state);
        this.animationTick = 0;
        this.animationSpeed = 5;
        this.scale = scale;
        this.spriteX = 0;
        this.spriteY = 0;
        this.spriteWidth = PlayerConstants.getSpriteSize(type)[0];
        this.spriteHeight = PlayerConstants.getSpriteSize(type)[1];
        this.width = (int) (this.spriteWidth * this.scale);
        this.height = (int) (this.spriteHeight * this.scale);
        this.img = PlayerConstants.getSpriteImage(type);
    }

    public void draw (Graphics g, int x, int y){
        BufferedImage subImage = this.img.getSubimage(
                this.frameX * this.spriteWidth,
                this.frameY * this.spriteHeight,
                this.spriteWidth,
                this.spriteHeight
        );

        g.drawImage(subImage, x, y, this.width, this.height, null);
    }

    public void update () {
        this.animationTick++;
        if (this.animationTick >= this.animationSpeed) {
            this.animationTick = 0;
            if (this.frameX < this.maxFrame - 1) {
                this.frameX++;
            }else {
                this.frameX = 0;
            }
        }
    }

    public int getWidth() {
        return width;
    }
}
