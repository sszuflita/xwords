package xwords.wordset;

import xwords.PartialFill;

import java.util.Set;

public interface WordSet {

    Set<ScoredWord> validWords(PartialFill partialFill);

    boolean isFillFeasible(PartialFill partialFill);
}
