package xwords.wordset;

import xwords.PartialFill;
import xwords.Tile;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicWordSet implements WordSet {

    private final Map<Integer, Set<String>> validWords;

    public BasicWordSet(Set<String> validWords) {
        this.validWords = validWords.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.toSet()));
    }

    @Override
    public Set<String> validWords(PartialFill partialFill) {
        return validWords.get(partialFill.getTiles().size())
                .parallelStream()
                .filter(word -> matches(word, partialFill))
                .collect(Collectors.toSet());
    }

    boolean matches(String word, PartialFill partialFill) {
        if (word.length() != partialFill.getTiles().size()) {
            return false;
        }
        for (int i = 0; i < partialFill.getTiles().size(); i++) {
            Tile tile = partialFill.getTiles().get(i);
            if (!(tile.equals(Tile.EMPTY) || tile.name().charAt(0) == word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isWordFeasible(PartialFill partialFill) {
        if (!partialFill.getTiles().contains(Tile.EMPTY)) {
            return validWords.get(partialFill.getTiles().size()).contains(partialFill.toString());
        }

        return validWords.get(partialFill.getTiles().size()).stream()
                .anyMatch(validWord -> matches(validWord, partialFill));
    }
}
