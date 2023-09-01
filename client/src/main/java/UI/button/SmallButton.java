package UI.button;

import ColliderBox.RectangleColliderBox;
import UI.Icons.Icon;
import UI.Text.Text;
import Utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SmallButton implements Button {
    private BufferedImage image;
    private RectangleColliderBox bounds;
    private int x, y, defaultWidth, defaultHeight, width, height;
    private double scale;
    private int frameX, frameY;
    private Icon icon;
    private Text text;

    public SmallButton (Icon icon, int x, int y, double scale) {
        this.icon = icon;
        this.init(x, y, scale);
    }

    public SmallButton (Text text, int x, int y, double scale) {
        this.text = text;
        this.init(x, y, scale);
    }

    private void init (int x, int y, double scale){
        this.x = x;
        this.y = y;
        this.defaultWidth = 42;
        this.defaultHeight = 42;
        this.scale = scale;
        this.width = (int) (this.defaultWidth * this.scale);
        this.height = (int) (this.defaultHeight * this.scale);
        this.frameX = 0;
        this.frameY = 0;
        this.image = LoadSave.loadImage(LoadSave.SMALL_BUTTON);
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
        if (this.icon != null) {
            this.icon.draw(
                    g,
                    (int) (this.x + this.width * 0.5 - this.icon.getWidth() * 0.5),
                    (int) (this.y + this.height * 0.45 - this.icon.getHeight() * 0.5)
            );
        }

        if (this.text != null) {
            this.text.draw(
                    g,
                    (int) (this.x + this.width * 0.6 - this.text.getWidth() * 0.5),
                    (int) (this.y + this.height * 0.45 - this.text.getHeight() * 0.5)
            );
        }


    }

    @Override
    public void update() {

    }

    @Override
    public void onMouseRelease(MouseEvent e) {
        this.resetState();
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        this.frameX = 2;
    }

    @Override
    public void onMouseEnter(MouseEvent e) {
        this.frameX = 1;
    }

    @Override
    public void onMouseDragged(MouseEvent e) {

    }

    @Override
    public boolean isMouseEnter(MouseEvent e) {
        return this.bounds.contains(e.getX(), e.getY());
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void resetState() {
        this.frameX = 0;
    }
}
