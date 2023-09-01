package Utils;

import Main.Game;

public class HelperMethods {

    public static boolean isSolid(double x, double y, int[][] mapData) {
        int xIndex = (int)(y / Game.TILES_SIZE);
        int yIndex = (int)(x / Game.TILES_SIZE);

        int value = mapData[xIndex][yIndex];

        return value != 11;

    }

}
