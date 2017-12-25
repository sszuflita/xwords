package xwords.wordset;

import java.util.Objects;

public class ScoredWord {

    private final String word;
    private final int score;

    public ScoredWord(String word, int score) {
        this.word = word;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoredWord that = (ScoredWord) o;
        return score == that.score &&
                Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, score);
    }

    public int getScore() {
        return score;
    }

    public String getWord() {
        return word;
    }
}
