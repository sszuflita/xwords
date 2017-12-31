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

        Assert.assertEquals(abc.downs(),
                ImmutableList.of(
                        new OrderedPartialFill(
                                new PartialFill(ImmutableList.of(Tile.A, Tile.A, Tile.A), 0, 0, Orientation.DOWN),
                                1),
                        new OrderedPartialFill(
                                new PartialFill(ImmutableList.of(Tile.B, Tile.A, Tile.A), 0, 0, Orientation.DOWN),
                                2),
                        new OrderedPartialFill(
                                new PartialFill(ImmutableList.of(Tile.C, Tile.A, Tile.A), 0, 0, Orientation.DOWN),
                                3)));

              Assert.assertEquals(abc.acrosses(),
                ImmutableList.of(
                        new OrderedPartialFill(
                                new PartialFill(ImmutableList.of(Tile.A, Tile.B, Tile.C), 0, 0, Orientation.DOWN),
                                1),
                        new OrderedPartialFill(
                                new PartialFill(ImmutableList.of(Tile.A, Tile.A, Tile.A), 0, 0, Orientation.DOWN),
                                4),
                        new OrderedPartialFill(
                                new PartialFill(ImmutableList.of(Tile.A, Tile.A, Tile.A), 0, 0, Orientation.DOWN),
                                5)));
    }

}