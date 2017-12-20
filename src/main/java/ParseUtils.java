import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class ParseUtils {

    private ParseUtils() {};

    public static Tile[][] fromPath(Path path) throws IOException {
        return Files.lines(path)
                .filter(line -> !line.isEmpty())
                .map(ParseUtils::parseLine)
                .collect(Collectors.toList()).toArray(new Tile[0][0]);
    }

    private static Tile[] parseLine(String line) {
        Tile[] result = new Tile[line.length()];
        for (int idx = 0; idx < line.length(); idx++) {
            String tileString = line.substring(idx, idx + 1);
            result[idx] = Tile.fromString(tileString);
        }
        return result;
    }

}
