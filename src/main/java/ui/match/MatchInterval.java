package ui.match;

public class MatchInterval {
    private final int start;
    private final int end;

    public MatchInterval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean containsStartOfInterval(MatchInterval other) {
        return other.start >= this.start && other.start < this.end;
    }
}
