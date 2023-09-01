package UI.Icons;

import UI.button.Button;
import Utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SmallIcon implements Icon {
    private Icons icon;
    private int defaultWidth, defaultHeight, width, height, space;
    private double scale;
    private BufferedImage image;
    public SmallIcon (Icons icon, double scale) {
        this.icon = icon;
        this.scale = scale;
        this.defaultWidth = 8;
        this.defaultHeight = 6;
        this.width = (int)(this.defaultWidth * this.scale);
        this.height = (int)(this.defaultHeight * this.scale);
        this.image = LoadSave.loadImage(LoadSave.SMALL_ICON);
    }



    @Override
    public void draw(Graphics g, int x, int y) {
        BufferedImage subImage = this.image.getSubimage(
                Icon.getIconIndex(this.icon) * this.defaultWidth,
                0,
                this.defaultWidth,
                this.defaultHeight
        );

        g.drawImage(subImage, x, y, this.width, this.height, null);

    }

    @Override
    public void update() {

    }


    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
