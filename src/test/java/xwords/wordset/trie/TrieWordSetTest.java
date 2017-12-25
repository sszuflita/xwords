package xwords.wordset.trie;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;
import xwords.Orientation;
import xwords.PartialFill;
import xwords.Tile;
import xwords.wordset.ScoredWord;

import java.util.Set;

public class TrieWordSetTest {

    @Test
    public void wordSetWorks() {
        ScoredWord abc = new ScoredWord("ABC", 1);
        ScoredWord aba = new ScoredWord("ABA", 1);
        ScoredWord abcd = new ScoredWord("ABCD", 1);

        TrieWordSet trieWordSet = new TrieWordSet(ImmutableSet.of(
                abc, aba, abcd));


        Assert.assertTrue(trieWordSet.isFillFeasible(new PartialFill(
                ImmutableList.of(Tile.A, Tile.B, Tile.C), 0, 0, Orientation.ACROSS)));

        Set<ScoredWord> strings = trieWordSet.validWords(new PartialFill(
                ImmutableList.of(Tile.EMPTY, Tile.B, Tile.EMPTY), 0, 0, Orientation.ACROSS));

        Assert.assertTrue(strings.contains(abc) && strings.contains(aba));
    }

}