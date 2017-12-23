package xwords.wordset.trie;

import xwords.PartialFill;
import xwords.Tile;
import xwords.wordset.WordSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrieWordSet implements WordSet {


    private final TrieNode root;

    public TrieWordSet(Set<String> validWords) {
        this.root = buildTrie(validWords);
    }

    private TrieNode buildTrie(Set<String> validWords) {
        TrieNode root = new TrieNode("", false);
        for (String word : validWords) {
            TrieNode currentNode = root;
            String upperCaseWord = word.toUpperCase();
            for (int i = 0; i < upperCaseWord.length(); i++) {
                char character = upperCaseWord.charAt(i);
                boolean isTerminal = i == (upperCaseWord.length() - 1);
                if (currentNode.children().containsKey(character)) {
                    if (isTerminal) {
                        currentNode.setIsWordToTrue();
                    }
                    currentNode = currentNode.children().get(character);
                } else {
                    String childPrefix = currentNode.prefix() + character;
                    TrieNode child = new TrieNode(childPrefix, isTerminal);
                    currentNode.children().put(character, child);
                    currentNode = child;
                }
            }
        }
        return root;
    }


    @Override
    public Set<String> validWords(PartialFill partialFill) {
        return validWordsStream(partialFill.getTiles(), root).collect(Collectors.toSet());
    }

    private Stream<String> validWordsStream(List<Tile> tiles, TrieNode node) {
        if (tiles.isEmpty() && node.isWord()) {
            return Stream.of(node.prefix());
        }
        if (tiles.isEmpty() && !node.isWord()) {
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
    public boolean isWordFeasible(PartialFill partialFill) {
        return validWordsStream(partialFill.getTiles(), root).findAny().isPresent();
    }


}
