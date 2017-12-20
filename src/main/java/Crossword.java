public class Crossword {

    private final Tile[][] tiles;

    public Crossword(Tile[][] tiles) {
        this.tiles = tiles;
    }

    int width() {
        return tiles[0].length;
    }

    int height() {
        return tiles.length;
    }

    Tile getValueAtTile(int row, int col) {
        return tiles[row][col];
    }

    void setValueAtTile(int row, int col, Tile tile) {
        tiles[row][col] = tile;
    }
}
