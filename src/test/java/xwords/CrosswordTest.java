package xwords;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

public class CrosswordTest {

    @Test
    public void fillPartialTest() {
        Crossword crossword = new Crossword(new Tile[]{
                Tile.A, Tile.EMPTY, Tile.EMPTY,
                Tile.A, Tile.A, Tile.A,
                Tile.A, Tile.A, Tile.A
        }, 3, 3, 0);
        PartialFill partialFill = new PartialFill(ImmutableList.of(Tile.A, Tile.EMPTY, Tile.EMPTY), 0, 0, Orientation.ACROSS);
        Crossword abc = crossword.withPartialFill(partialFill, "ABC", 1);

        Crossword expected = new Crossword(new Tile[]{
                Tile.A, Tile.B, Tile.C,
                Tile.A, Tile.A, Tile.A,
                Tile.A, Tile.A, Tile.A
        }, 3, 3, 0);
        Assert.assertEquals(abc, expected);
    }

}