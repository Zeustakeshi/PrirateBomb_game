package UI.button;

import ColliderBox.RectangleColliderBox;
import UI.Text.Text;
import Utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RectangleButton implements Button {

    private BufferedImage image;
    private final RectangleColliderBox bounds;
    private int x, y, defaultWidth, defaultHeight, width, height;
    private double scale;
    private int frameX, frameY;
    private Text text;
    public RectangleButton (Text text, int x, int y, double scale) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.defaultWidth = 139;
        this.defaultHeight = 53;
        this.scale = scale;
        this.width = (int) (this.defaultWidth * this.scale);
        this.height = (int) (this.defaultHeight * this.scale);
        this.frameX = 0;
        this.frameY = 0;
        this.image = LoadSave.loadImage(LoadSave.BUTTON);
        this.bounds = new RectangleColliderBox(
                this.x,
                this.y,
                this.width,
                this.height
        );
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds.update(this.x, this.y, this.width, this.height);
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage subImage = this.image.getSubimage(
                this.defaultWidth * this.frameX,
                this.defaultHeight * this.frameY,
                this.defaultWidth,
                this.defaultHeight
        );

        g.drawImage(subImage, this.x, this.y, this.width, this.height,  null);
        this.text.draw(
                g,
                (int) (this.x + this.width * 0.5 - this.text.getWidth() * 0.5),
                (int) (this.y + this.height * 0.5 - this.text.getHeight() * 0.5)
        );
    }


    @Override
    public void update() {

    }

    @Override
    public void onMouseRelease(MouseEvent e) {
        if (this.bounds.contains(e.getX(), e.getY())) {
            this.resetState();
        }
    }

    @Override
    public void onMouseClick(MouseEvent e) {
//        if (this.bounds.contains(e.getX(), e.getY())) {
//            this.frameX = 1;
//        }
    }

    @Override
    public void onMouseEnter(MouseEvent e) {
    }

    @Override
    public void onMouseDragged(MouseEvent e) {

    }

    public void resetState() {
        this.frameX = 0;
        this.frameY = 0;
    }

    @Override
    public boolean isMouseEnter(MouseEvent e) {
        return this.bounds.contains(e.getX(), e.getY());
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
