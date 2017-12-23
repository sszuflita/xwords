package xwords;

import com.google.common.collect.Streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParseUtils {

    private ParseUtils() {};

    public static Tile[] fromPath(Path path) throws IOException {
        return Files.lines(path)
                .filter(line -> !line.isEmpty())
                .flatMap(ParseUtils::parseLine)
                .collect(Collectors.toList()).toArray(new Tile[0]);
    }

    private static Stream<Tile> parseLine(String line) {
        Stream<Tile> tileStream = Stream.empty();
        for (int i = 0; i < line.length(); i++) {
            Tile tile = Tile.fromString(line.substring(i, i + 1));
            tileStream = Streams.concat(tileStream, Stream.of(tile));
        }
        return tileStream;
    }

}
