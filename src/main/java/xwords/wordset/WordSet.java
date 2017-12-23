package xwords.wordset;

import xwords.PartialFill;

import java.util.Set;

public interface WordSet {

    Set<String> validWords(PartialFill partialFill);

    boolean isWordFeasible(PartialFill partialFill);
}
