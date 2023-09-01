package Maps;

public class Map implements IMap {
    private final int[][] mapData;
    public Map (int[][] mapData) {
        this.mapData = mapData;
    }

    @Override
    public int[][] getMapData() {
        return this.mapData;
    }

    @Override
    public int getMapImage(int x, int y) {
        return this.mapData[x][y];
    }
}
