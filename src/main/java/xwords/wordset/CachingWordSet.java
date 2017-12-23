package xwords.wordset;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import xwords.PartialFill;

import java.util.Set;

public class CachingWordSet implements WordSet {

    private final LoadingCache<PartialFill, Boolean> isFillFeasibleCache;
    private final LoadingCache<PartialFill, Set<String>> validWordsCache;

    public CachingWordSet(WordSet delegate) {
        this.validWordsCache = Caffeine.newBuilder()
                .recordStats()
                .build(delegate::validWords);
        this.isFillFeasibleCache = Caffeine.newBuilder()
                .recordStats()
                .build(delegate::isFillFeasible);
    }

    @Override
    public Set<String> validWords(PartialFill partialFill) {
        return validWordsCache.get(partialFill);
    }

    @Override
    public boolean isFillFeasible(PartialFill partialFill) {
        return isFillFeasibleCache.get(partialFill);
    }
}
