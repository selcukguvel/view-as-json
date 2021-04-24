package match;

import org.junit.Assert;
import org.junit.Test;

public class MatchIntervalTest {

    @Test
    public void containsStartOfIntervalTest() {
        MatchInterval firstMatchInterval = MatchTestData.getMatchInterval(10, 20);
        MatchInterval secondMatchInterval = MatchTestData.getMatchInterval(10, 15);
        Assert.assertTrue(firstMatchInterval.containsStartOfInterval(secondMatchInterval));

        firstMatchInterval = MatchTestData.getMatchInterval(10, 20);
        secondMatchInterval = MatchTestData.getMatchInterval(12, 15);
        Assert.assertTrue(firstMatchInterval.containsStartOfInterval(secondMatchInterval));

        firstMatchInterval = MatchTestData.getMatchInterval(10, 20);
        secondMatchInterval = MatchTestData.getMatchInterval(15, 20);
        Assert.assertTrue(firstMatchInterval.containsStartOfInterval(secondMatchInterval));

        firstMatchInterval = MatchTestData.getMatchInterval(10, 20);
        secondMatchInterval = MatchTestData.getMatchInterval(20, 21);
        Assert.assertFalse(firstMatchInterval.containsStartOfInterval(secondMatchInterval));
    }

}
