package xwords;

import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CrosswordBuilderBenchmarks {

    @Test
    public void test3by3grid() throws IOException {
        Path path = Paths.get("/Users/sszuflita/Downloads/XwiWordList.txt");

        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[][] tiles = ParseUtils.fromPath(Paths.get("src/test/resources/3by3.txt"));

        int numberOfTrials = 5;
        long totalTime= runTestInTrials(builder, tiles, 5);
        System.out.println(totalTime / numberOfTrials);
    }

        @Test
    public void test4by4grid() throws IOException {
        Path path = Paths.get("/Users/sszuflita/Downloads/XwiWordList.txt");

        CrosswordBuilder builder = CrosswordBuilder.fromPath(path);

        Tile[][] tiles = ParseUtils.fromPath(Paths.get("src/test/resources/4by4.txt"));

        int num_trials = 5;
        long totalTime = runTestInTrials(builder, tiles, num_trials);
        System.out.println(totalTime / num_trials);
    }

    private long runTestInTrials(CrosswordBuilder builder, Tile[][] tiles, int num_trials) {
        long totalTime = 0L;
        for (int i = 0; i < num_trials; i++) {
            Stopwatch started = Stopwatch.createStarted();
            Set<Crossword> crosswords = builder.solveFromGrid(new Crossword(tiles));
            started.stop();
            totalTime += started.elapsed(TimeUnit.MILLISECONDS);
            Assert.assertTrue(!crosswords.isEmpty());
        }
        return totalTime;
    }
}
