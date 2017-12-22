package xwords.wordset;

import xwords.PartialFill;
import xwords.Tile;

import java.util.Set;
import java.util.stream.Collectors;

public class BasicWordSet implements WordSet {

    private final Set<String> validWords;

    public BasicWordSet(Set<String> validWords) {
        this.validWords = validWords;
    }

    @Override
    public Set<String> validWords(PartialFill partialFill) {
        return validWords.parallelStream()
                .filter(word -> matches(word, partialFill))
                .collect(Collectors.toSet());
    }

    boolean matches(String word, PartialFill partialFill) {
        if (word.length() != partialFill.getLetters().size()) {
            return false;
        }
        for (int i = 0; i < partialFill.getLetters().size(); i++) {
            Tile tile = partialFill.getLetters().get(i);
            if (!(tile.equals(Tile.EMPTY) || tile.name().charAt(0) == word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isWordValid(String word) {
        return false;
    }
}
