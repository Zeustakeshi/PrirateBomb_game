package UI.Text;

import Utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class SmallText implements Text {
    private String text;
    private int defaultWidth, defaultHeight, width, height, space;
    private double scale;
    private BufferedImage[] images;
    private BufferedImage image;

    public SmallText (String text, double scale) {
        this.text = text;
        this.scale = scale;
        this.defaultWidth = 5;
        this.defaultHeight = 6;
        this.width = (int)(this.defaultWidth * this.scale);
        this.height = (int)(this.defaultHeight * this.scale);
        this.space = (int)(10 * this.scale);
        this.image = LoadSave.loadImage(LoadSave.SMALL_TEXT);

        this.images = this.getImages(this.text);
    }


    @Override
    public BufferedImage[] getImages(String text) {
        List<Integer> textImageIndex = Text.StringToIndex(text);
        BufferedImage[] images = new BufferedImage[textImageIndex.size()];
        for (int i = 0; i < textImageIndex.size(); ++i) {

            images[i] = image.getSubimage(
                    (textImageIndex.get(i) - 26) * this.defaultWidth,
                    0,
                    this.defaultWidth,
                    this.defaultHeight
            );
        }
        return images;
    }

    @Override
    public void draw(Graphics g, int x, int y) {
        for (int i = 0; i < this.images.length; ++i) {
            g.drawImage(
                    this.images[i],
                    x + (i * this.space),
                    y,
                    this.width,
                    this.height,
                    null
            );
        }
    }

    @Override
    public void update() {

    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String newText) {
        this.text = newText;
        this.images = this.getImages(newText);
    }

    @Override
    public void setSpace(int space) {
        this.space = space;
    }

    @Override
    public int getWidth() {
        return ((this.images.length) * (this.space))  ;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
