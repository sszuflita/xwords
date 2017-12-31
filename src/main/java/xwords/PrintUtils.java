package xwords;

import java.util.StringJoiner;

public class PrintUtils {

    private PrintUtils() {}

    public static void printPuzzle(Crossword xword) {

        System.out.println(xword);

        System.out.println("ACROSS");
        StringJoiner acrossJoiner = new StringJoiner("\n");
        xword.acrosses().forEach(across -> acrossJoiner.add(across.toString()));
        System.out.println(acrossJoiner.toString());

        System.out.println("DOWN");
        StringJoiner downJoiner = new StringJoiner("\n");
        xword.downs().forEach(down -> downJoiner.add(down.toString()));

        System.out.println(downJoiner.toString());


    }
}
