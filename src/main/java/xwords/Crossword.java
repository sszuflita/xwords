package xwords;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Crossword {

    private final Tile[] tiles;
    private final int height;
    private final int width;
    private final double score;

    public Crossword(Tile[] tiles, int height, int width, double score) {
        this.tiles = tiles;
        this.height = height;
        this.width = width;
        this.score = score;
    }

    public int filledTiles() {
        int count = 0;
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                if (!getValueAtTile(row, col).equals(Tile.EMPTY)) {
                    count++;
                }
            }
        }
        return count;
    }

    int width() {
        return width;
    }

    int height() {
        return height;
    }

    Tile getValueAtTile(int row, int col) {
        return tiles[row * width + col];
    }

    public double getScore() {
        return score;
    }

    private void setValueAtTile(int row, int col, Tile tile) {
        tiles[row * width + col] = tile;
    }

    public Crossword withPartialFill(PartialFill partialFill, String validWord, int score) {
        Tile[] newTiles = new Tile[width * height];
        System.arraycopy(tiles, 0, newTiles, 0, width * height);
        fillPartial(partialFill, validWord, newTiles);
        return new Crossword(newTiles, height, width, this.score + Math.log(score));
    }

    private void fillPartial(PartialFill partialFill, String validFill, Tile[] otherTiles) {
        if (partialFill.getOrientation() == Orientation.DOWN) {
            int col = partialFill.getStartCol();
            int startRow = partialFill.getStartRow();
            for (int idx = 0; idx < validFill.length(); idx++) {
                Tile tile = Tile.valueOf(validFill.substring(idx, idx + 1));
                otherTiles[(startRow + idx) * width + col] = tile;
            }
        } else {
            int row = partialFill.getStartRow();
            int startCol = partialFill.getStartCol();
            for (int idx = 0; idx < validFill.length(); idx++) {
                Tile tile = Tile.valueOf(validFill.substring(idx, idx + 1));
                otherTiles[row * width + (startCol + idx)] = tile;
            }
        }
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

        for (int col = 0; col < width(); col++) {

            List<Tile> currentFill = Lists.newArrayList();
            int startRow = 0;
            int startCol = col;

            for (int row = 0; row < height(); row++) {
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
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public String printFlipped() {
        StringBuilder result = new StringBuilder();
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                Tile valueAtTile = getValueAtTile(row, col);
                result.append(valueAtTile.toString());
                result.append(" ");
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

        return Arrays.equals(tiles, crossword.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tiles);
    }
}
