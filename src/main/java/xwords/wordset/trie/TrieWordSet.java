package xwords.wordset.trie;

import com.google.common.collect.ImmutableSet;
import xwords.PartialFill;
import xwords.Tile;
import xwords.wordset.WordSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrieWordSet implements WordSet {


    private final TrieNode root;

    public TrieWordSet(Set<String> validWords) {
        this.root = buildTrie(validWords);
    }

    private TrieNode buildTrie(Set<String> validWords) {
        TrieNode root = new TrieNode("", false);
        for (String word : validWords) {
            TrieNode currentNode = root;
            for (int i = 0; i < word.length(); i++) {
                char character = word.charAt(i);
                boolean isTerminal = i == (word.length() - 1);
                if (currentNode.children().containsKey(character)) {
                    if (isTerminal) {
                        currentNode.setIsWordToTrue();
                    }
                    currentNode = currentNode.children().get(character);
                } else {
                    String childPrefix = currentNode.prefix() + character;
                    TrieNode child = new TrieNode(childPrefix, isTerminal);
                    currentNode.children().put(character, child);
                }
            }
        }
        return root;
    }


    @Override
    public Set<String> validWords(PartialFill partialFill) {
        return validWordsHelper(partialFill.getTiles(), root);
    }

    private Set<String> validWordsHelper(List<Tile> tiles, TrieNode node) {
        if (tiles.isEmpty() && node.isWord()) {
            return ImmutableSet.of(node.prefix());
        }
        if (tiles.isEmpty() && !node.isWord()) {
            return ImmutableSet.of();
        }
        Tile tile = tiles.get(0);
        List<Tile> remainingTiles = tiles.subList(1, tiles.size());
        if (tile.equals(Tile.EMPTY)) {
            return node.children().values().stream()
                    .flatMap(childNode -> validWordsHelper(remainingTiles, childNode).stream())
                    .collect(Collectors.toSet());
        }
        Character character = tile.name().charAt(0);
        if (!node.children().containsKey(character)) {
            return ImmutableSet.of();
        }

        return validWordsHelper(tiles.subList(1, tiles.size()), node.children().get(character));
    }

    @Override
    public boolean isWordFeasible(PartialFill partialFill) {
        return false;
    }


}
