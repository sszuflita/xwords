package xwords.wordset.trie;

import xwords.PartialFill;
import xwords.Tile;
import xwords.wordset.ScoredWord;
import xwords.wordset.WordSet;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrieWordSet implements WordSet {

    private final TrieNode root;
    private final Map<String, ScoredWord> validWords;

    public TrieWordSet(Set<ScoredWord> validWords) {
        this.root = buildTrie(validWords);
        this.validWords = validWords.stream().collect(Collectors.toMap(word -> word.getWord().toUpperCase(),
                Function.identity()));
    }

    private TrieNode buildTrie(Set<ScoredWord> validWords) {
        TrieNode root = new TrieNode("", false, OptionalInt.empty());
        for (ScoredWord word : validWords) {
            TrieNode currentNode = root;
            String upperCaseWord = word.getWord().toUpperCase();
            for (int i = 0; i < upperCaseWord.length(); i++) {
                char character = upperCaseWord.charAt(i);
                boolean isTerminal = i == (upperCaseWord.length() - 1);
                if (currentNode.children().containsKey(character)) {
                    if (isTerminal) {
                        currentNode.setIsWordToTrue();
                        currentNode.setScoreTo(word.getScore());
                    }
                    currentNode = currentNode.children().get(character);
                } else {
                    String childPrefix = currentNode.prefix() + character;

                    OptionalInt childScore;
                    if (isTerminal) {
                        childScore = OptionalInt.of(word.getScore());
                    } else {
                        childScore = OptionalInt.empty();
                    }

                    TrieNode child = new TrieNode(childPrefix, isTerminal, childScore);
                    currentNode.children().put(character, child);
                    currentNode = child;
                }
            }
        }
        return root;
    }


    @Override
    public Set<ScoredWord> validWords(PartialFill partialFill) {
        return validWordsStream(partialFill.getTiles(), root).collect(Collectors.toSet());
    }

    private Stream<ScoredWord> validWordsStream(List<Tile> tiles, TrieNode node) {
        if (tiles.isEmpty() && node.isWord()) {
            return Stream.of(
                    new ScoredWord(node.prefix(), node.getScore().getAsInt())
            );
        }
        if (tiles.isEmpty() && !node.isWord()) {
            return Stream.empty();
        }

        if (!tiles.contains(Tile.EMPTY)) {
            String potentialString = node.prefix() + tiles.stream().map(Tile::toString).collect(Collectors.joining());
            if (validWords.containsKey(potentialString)) {
                return Stream.of(validWords.get(potentialString));
            }
            return Stream.empty();
        }
        Tile tile = tiles.get(0);
        List<Tile> remainingTiles = tiles.subList(1, tiles.size());
        if (tile.equals(Tile.EMPTY)) {
            return node.children().values().stream()
                    .flatMap(childNode -> validWordsStream(remainingTiles, childNode));
        }
        Character character = tile.name().charAt(0);
        if (!node.children().containsKey(character)) {
            return Stream.of();
        }

        return validWordsStream(tiles.subList(1, tiles.size()), node.children().get(character));
    }

    @Override
    public boolean isFillFeasible(PartialFill partialFill) {
        return validWordsStream(partialFill.getTiles(), root).findAny().isPresent();
    }


}
