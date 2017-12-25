package xwords.wordset.trie;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.OptionalInt;

public class TrieNode {
    private final String prefix;
    private boolean isWord;
    private final Map<Character, TrieNode> children;
    private OptionalInt score;

    TrieNode(String prefix, boolean isWord, OptionalInt score) {
        this.prefix = prefix;
        this.isWord = isWord;
        this.children = Maps.newHashMap();
        this.score = score;
    }

    Map<Character, TrieNode> children() {
        return children;
    }

    public String prefix() {
        return prefix;
    }

    public OptionalInt getScore() {
        return score;
    }

    boolean isWord() {
        return isWord;
    }

    void addChild(Character character, TrieNode trieNode) {
        children.put(character, trieNode);
    }

    void setIsWordToTrue() {
        isWord = true;
    }

    public void setScoreTo(int newScore) {
        score = OptionalInt.of(newScore);
    }
}