package xwords;

import java.util.List;
import java.util.Objects;

public class PartialFill {

    private final List<Tile> letters;
    private final int startRow;
    private final int startCol;
    private final Orientation orientation;

    public PartialFill(List<Tile> letters, int startRow, int startCol, Orientation orientation) {
        this.letters = letters;
        this.startRow = startRow;
        this.startCol = startCol;
        this.orientation = orientation;
    }

    public List<Tile> getTiles() {
        return letters;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tile tile : letters) {
            stringBuilder.append(tile.toString());
        }
        return stringBuilder.toString();
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartialFill that = (PartialFill) o;
        return Objects.equals(letters, that.letters);

    }

    @Override
    public int hashCode() {
        return Objects.hash(letters);
    }
}
