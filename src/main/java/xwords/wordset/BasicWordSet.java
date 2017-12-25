package xwords.wordset;

import xwords.PartialFill;
import xwords.Tile;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicWordSet implements WordSet {

    private final Map<Integer, Set<ScoredWord>> validWords;

    public BasicWordSet(Set<ScoredWord> validWords) {
        this.validWords = validWords.stream()
                .collect(Collectors.groupingBy(scoredWord -> scoredWord.getWord().length(),
                        Collectors.toSet()));
    }

    @Override
    public Set<ScoredWord> validWords(PartialFill partialFill) {
        return validWords.get(partialFill.getTiles().size())
                .parallelStream()
                .filter(word -> matches(word.getWord(), partialFill))
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
    public boolean isFillFeasible(PartialFill partialFill) {
        if (!partialFill.getTiles().contains(Tile.EMPTY)) {
            return validWords.get(partialFill.getTiles().size()).contains(partialFill.toString());
        }

        return validWords.get(partialFill.getTiles().size()).stream()
                .anyMatch(validWord -> matches(validWord.getWord(), partialFill));
    }
}
