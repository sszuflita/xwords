package xwords.wordset.trie;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;
import xwords.PartialFill;
import xwords.Tile;
import xwords.wordset.WordSet;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TrieWordSet implements WordSet {


    private final TrieNode root;
    private final Set<String> validWords;
    private final LoadingCache<CacheKey, Set<String>> validWordSetCache;

    class CacheKey {
        private final List<Tile> tiles;
        private final TrieNode node;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(tiles, cacheKey.tiles) &&
                    Objects.equals(node, cacheKey.node);
        }

        @Override
        public int hashCode() {

            return Objects.hash(tiles, node);
        }

        CacheKey(List<Tile> tiles, TrieNode node) {
            this.tiles = tiles;
            this.node = node;
        }

        public List<Tile> getTiles() {
            return tiles;
        }

        public TrieNode getNode() {
            return node;
        }
    }

    public TrieWordSet(Set<String> validWords) {
        this.root = buildTrie(validWords);
        this.validWords = validWords;
        this.validWordSetCache = Caffeine.newBuilder()
                .recordStats()
                .build(key -> validWordSet(key.getTiles(), key.getNode()));
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
        return validWordSetCache.get(new CacheKey(partialFill.getTiles(), root));
    }



    private Set<String> validWordSet(List<Tile> tiles, TrieNode node) {
        if (tiles.isEmpty() && node.isWord()) {
            return ImmutableSet.of(node.prefix());
        }
        if (tiles.isEmpty() && !node.isWord()) {
            return ImmutableSet.of();
        }

        if (!tiles.contains(Tile.EMPTY)) {
            String potentialString = node.prefix() + tiles.stream().map(Tile::toString).collect(Collectors.joining());
            if (validWords.contains(potentialString)) {
                return ImmutableSet.of(potentialString);
            }
            return ImmutableSet.of();
        }
        Tile tile = tiles.get(0);
        List<Tile> remainingTiles = tiles.subList(1, tiles.size());
        if (tile.equals(Tile.EMPTY)) {
            return node.children().values().stream()
                    .flatMap(childNode -> validWordSet(remainingTiles, childNode).stream())
                    .collect(Collectors.toSet());
        }
        Character character = tile.name().charAt(0);
        if (!node.children().containsKey(character)) {
            return ImmutableSet.of();
        }

        return validWordSet(tiles.subList(1, tiles.size()), node.children().get(character));
    }

    @Override
    public boolean isFillFeasible(PartialFill partialFill) {
        return !validWordSetCache.get(new CacheKey(partialFill.getTiles(), root)).isEmpty();
    }
}
