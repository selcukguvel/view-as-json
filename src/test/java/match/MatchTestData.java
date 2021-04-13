package match;

import ui.match.MatchInterval;

public class MatchTestData {
    public static MatchInterval getMatchInterval(int start, int end) {
        return new MatchInterval(start, end);
    }
}
