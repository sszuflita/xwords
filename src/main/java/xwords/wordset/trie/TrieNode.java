package xwords.wordset.trie;

import com.google.common.collect.Maps;

import java.util.Map;

public class TrieNode {
    private final String prefix;
    private boolean isWord;
    private final Map<Character, TrieNode> children;

    TrieNode(String prefix, boolean isWord) {
        this.prefix = prefix;
        this.isWord = isWord;
        this.children = Maps.newHashMap();
    }

    Map<Character, TrieNode> children() {
        return children;
    }

    String prefix() {
        return prefix;
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
}