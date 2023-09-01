package Maps;

import Main.Game;
import Utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager implements IMapManager {
    public static final int MAP_IMAGE_ROW = 4;
    private static final int MAP_IMAGE_COL = 12;
    public static final int MAP_IMAGE_AMOUNT = MAP_IMAGE_COL * MAP_IMAGE_ROW;
    private IMap[] maps;
    private IMap currentMap;
    private BufferedImage[] mapImages;

    public MapManager () {
        this.loadMap();
    }

    private void loadMap () {
        this.maps = new IMap[] {
                new Map(this.importMapData())
        };
        this.currentMap = this.maps[0];
        this.importMapTiles();
    }



    private void importMapTiles() {
        BufferedImage image = LoadSave.loadImage(LoadSave.MAP_DATA);
        this.mapImages = new BufferedImage[MAP_IMAGE_AMOUNT];

        for (int i = 0; i < MAP_IMAGE_ROW; ++i) {
            for (int j = 0; j < MAP_IMAGE_COL; ++j) {
                int index = i * MAP_IMAGE_COL + j;
                this.mapImages[index] = image.getSubimage(
                        j * Game.TILES_DEFAULT_SIZE,
                        i * Game.TILES_DEFAULT_SIZE,
                        Game.TILES_DEFAULT_SIZE,
                        Game.TILES_DEFAULT_SIZE
                );
            }
        }
    }

    private int[][] importMapData() {
        BufferedImage img = LoadSave.loadImage(LoadSave.MAP_1);
        int[][] images = new int[img.getHeight()][img.getWidth()];
        for (int i = 0; i < img.getHeight(); ++i) {
            for (int j = 0; j < img.getWidth(); ++j) {
                Color color = new Color(img.getRGB(j, i));
                int valueRed = color.getRed();
                if (valueRed >= MAP_IMAGE_AMOUNT) valueRed = 0;
                images[i][j] = valueRed;

            }
        }
        return images;
    }


    @Override
    public void setCurrentMap(int index) {
        this.currentMap = this.maps[index];
    }

    @Override
    public IMap getCurrentMap() {
        return this.currentMap;
    }


    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < Game.TILES_IN_HEIGHT; ++i) {
            for (int j = 0; j < this.currentMap.getMapData()[0].length; ++j) {
                int index = this.currentMap.getMapImage(i, j);
                g.drawImage(
                        this.mapImages[index],
                        j * Game.TILES_SIZE,
                        i * Game.TILES_SIZE,
                        Game.TILES_SIZE,
                        Game.TILES_SIZE,
                        null
                );
            }
        }
    }

    @Override
    public void update() {

    }
}
