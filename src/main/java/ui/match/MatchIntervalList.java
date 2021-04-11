package ui.match;

import java.util.ArrayList;
import java.util.List;


public class MatchIntervalList {
    private final List<MatchInterval> matchIntervalList;

    public MatchIntervalList() {
        this.matchIntervalList = new ArrayList<>();
    }

    public void add(MatchInterval matchInterval) {
        matchIntervalList.add(matchInterval);
    }

    public boolean isIntervalAlreadyMatched(MatchInterval currentMatchInterval) {
        for (MatchInterval matchInterval : matchIntervalList) {
            if (matchInterval.containsStartOfInterval(currentMatchInterval)) {
                return true;
            }
        }
        return false;
    }
}
