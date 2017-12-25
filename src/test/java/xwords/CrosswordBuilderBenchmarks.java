package xwords;

import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CrosswordBuilderBenchmarks {

    int numberOfTrials = 1;
    Path path = Paths.get("/Users/sszuflita/Documents/sszuflita-edited.txt");

    @Test
    public void test3by3grid() throws IOException {
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] tiles = ParseUtils.fromPath(Paths.get("src/test/resources/3by3.txt"));

        long totalTime= runTestInTrials(builder, tiles, 3, 3, numberOfTrials);
        System.out.println(totalTime / numberOfTrials);
    }

    @Test
    public void test4by4grid() throws IOException {
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] tiles = ParseUtils.fromPath(Paths.get("src/test/resources/4by4.txt"));

        long totalTime = runTestInTrials(builder, tiles, 4, 4, numberOfTrials);
        System.out.println(totalTime / numberOfTrials);
    }

    @Test
    public void test5by5grid() throws IOException {
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] tiles = ParseUtils.fromPath(Paths.get("src/test/resources/5by5.txt"));

        long totalTime = runTestInTrials(builder, tiles, 5, 5, numberOfTrials);
        System.out.println(totalTime / numberOfTrials);
    }

    @Test
    public void testInterestingGrid() throws IOException {
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] tiles = ParseUtils.fromPath(Paths.get("src/test/resources/interestingGrid.txt"));

        long totalTime = runTestInTrials(builder, tiles, 8, 8, numberOfTrials);
        System.out.println(totalTime / numberOfTrials);
    }

    @Test
    public void testMondaysPuz() throws IOException {
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] tiles = ParseUtils.fromPath(Paths.get("src/test/resources/mondaysPuz.txt"));

        long totalTime = runTestInTrials(builder, tiles, 15, 15, numberOfTrials);
        System.out.println(totalTime / numberOfTrials);
    }

    @Test
    public void testFridaysPuz() throws IOException {
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] tiles = ParseUtils.fromPath(Paths.get("src/test/resources/fridaysPuz.txt"));

        long totalTime = runTestInTrials(builder, tiles, 15, 15, numberOfTrials);
        System.out.println(totalTime / numberOfTrials);
    }

        @Test
    public void buildPuzzle() throws IOException {
        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[] tiles = ParseUtils.fromPath(Paths.get("src/test/resources/realPuz.txt"));

        long totalTime = runTestInTrials(builder, tiles, 15, 15, numberOfTrials);
        System.out.println(totalTime / numberOfTrials);
    }


    private long runTestInTrials(CrosswordBuilder builder, Tile[] tiles, int width, int height, int num_trials) {
        long totalTime = 0L;
        for (int i = 0; i < num_trials; i++) {
            Stopwatch started = Stopwatch.createStarted();
            Set<Crossword> crosswords = builder.solveFromGrid(new Crossword(tiles, width, height, 0));
            started.stop();
            totalTime += started.elapsed(TimeUnit.MILLISECONDS);
            Assert.assertTrue(!crosswords.isEmpty());


            List<Crossword> xword = crosswords.stream().sorted(CrosswordBuilder::compare).collect(Collectors.toList());

            System.out.println(xword.get(0));
            int a = 5;

        }
        return totalTime;
    }
}
