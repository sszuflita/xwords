import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Crossword {

    private final Tile[][] tiles;

    public Crossword(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
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

    public List<PartialFill> toPartialFill() {
        List<PartialFill> result = Lists.newArrayList();
        for (int row = 0; row < height(); row++) {

            List<Tile> currentFill = Lists.newArrayList();
            int startRow = row;
            int startCol = 0;

            for (int col = 0; col < width(); col++) {
                // handle end of word
                Tile currentTile = getValueAtTile(row, col);
                if (currentTile == Tile.BLACK) {
                    if (currentFill.size() > 0) {
                        result.add(new PartialFill(currentFill, startRow, startCol, Orientation.ACROSS));
                        currentFill = Lists.newArrayList();
                    }
                    startCol = col + 1;
                } else if (col == width() - 1) {
                    currentFill.add(currentTile);
                    result.add(new PartialFill(currentFill, startRow, startCol, Orientation.ACROSS));
                } else {
                    currentFill.add(currentTile);
                }
            }
        }

        for (int col = 0; col < height(); col++) {

            List<Tile> currentFill = Lists.newArrayList();
            int startRow = 0;
            int startCol = col;

            for (int row = 0; row < width(); row++) {
                Tile currentTile = getValueAtTile(row, col);
                if (currentTile == Tile.BLACK) {
                    if (currentFill.size() > 0) {
                        result.add(new PartialFill(currentFill, startRow, startCol, Orientation.DOWN));
                        currentFill = Lists.newArrayList();
                    }
                    startRow = row + 1;
                } else if (row == height() - 1) {
                    currentFill.add(currentTile);
                    result.add(new PartialFill(currentFill, startRow, startCol, Orientation.DOWN));
                } else {
                    currentFill.add(currentTile);
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                Tile valueAtTile = getValueAtTile(row, col);
                result.append(valueAtTile.toString());
            }
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crossword crossword = (Crossword) o;

        if (tiles.length != crossword.tiles.length) {
            return false;
        }
        for (int i = 0; i < tiles.length; i++) {
            if (!Arrays.equals(tiles[i], crossword.tiles[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tiles);
    }
}
