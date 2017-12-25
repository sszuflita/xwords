package xwords;/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class CrosswordBuilderTest {
    @Test
    public void testEmptyGrid() throws IOException {
        Path path = Paths.get("/Users/sszuflita/Downloads/XwiWordList.txt");
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] base = new Tile[]{
                Tile.EMPTY, Tile.EMPTY, Tile.EMPTY,
                Tile.EMPTY, Tile.EMPTY, Tile.EMPTY,
                Tile.EMPTY, Tile.EMPTY, Tile.EMPTY
        };

        Set<Crossword> crosswords = builder.solveFromGrid(new Crossword(base, 3, 3, 0));

        Assert.assertTrue(crosswords.size() > 0);
    }

    @Test
    public void testNonEmptyGrid() throws IOException {
        Path path = Paths.get("/Users/sszuflita/Downloads/XwiWordList.txt");
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] base = new Tile[]{
                Tile.A, Tile.EMPTY, Tile.EMPTY,
                Tile.EMPTY, Tile.EMPTY, Tile.EMPTY,
                Tile.EMPTY, Tile.EMPTY, Tile.EMPTY
        };

      Set<Crossword> crosswords = builder.solveFromGrid(new Crossword(base, 3, 3, 0));

      Assert.assertTrue(crosswords.size() > 0);
    }

    @Test
    public void testNonEmptyGridWithB() throws IOException {
        Path path = Paths.get("/Users/sszuflita/Downloads/XwiWordList.txt");
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] base = new Tile[]{
                Tile.B, Tile.EMPTY, Tile.EMPTY,
                Tile.EMPTY, Tile.EMPTY, Tile.EMPTY,
                Tile.R, Tile.EMPTY, Tile.EMPTY
        };

        Set<Crossword> crosswords = builder.solveFromGrid(new Crossword(base, 3, 3, 0));

        Assert.assertTrue(crosswords.size() > 0);
    }

}
