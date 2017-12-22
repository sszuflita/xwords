package xwords;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;
import xwords.Crossword;
import xwords.Orientation;
import xwords.PartialFill;
import xwords.Tile;

public class CrosswordTest {

    @Test
    public void fillPartialTest() {
        Crossword crossword = new Crossword(new Tile[][]{
                new Tile[]{Tile.A, Tile.EMPTY, Tile.EMPTY},
                new Tile[]{ Tile.A, Tile.A, Tile.A},
                new Tile[]{ Tile.A, Tile.A, Tile.A}
        });
        PartialFill partialFill = new PartialFill(ImmutableList.of(Tile.A, Tile.EMPTY, Tile.EMPTY), 0, 0, Orientation.ACROSS);
        Crossword abc = crossword.withPartialFill(partialFill, "ABC");

        Crossword expected = new Crossword(new Tile[][]{
                new Tile[]{Tile.A, Tile.B, Tile.C},
                new Tile[]{ Tile.A, Tile.A, Tile.A},
                new Tile[]{ Tile.A, Tile.A, Tile.A}
        });
        Assert.assertEquals(abc, expected);
    }

}