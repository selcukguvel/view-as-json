package match;

import org.junit.Assert;
import org.junit.Test;

public class MatchIntervalListTest {

    @Test
    public void isIntervalAlreadyMatchedTest() {
        MatchIntervalList matchIntervalList = new MatchIntervalList();
        matchIntervalList.add(MatchTestData.getMatchInterval(10, 20));
        matchIntervalList.add(MatchTestData.getMatchInterval(25, 30));
        matchIntervalList.add(MatchTestData.getMatchInterval(35, 40));

        MatchInterval matchedInterval = MatchTestData.getMatchInterval(15, 20);
        Assert.assertTrue(matchIntervalList.isIntervalAlreadyMatched(matchedInterval));

        matchedInterval = MatchTestData.getMatchInterval(40, 50);
        Assert.assertFalse(matchIntervalList.isIntervalAlreadyMatched(matchedInterval));
    }

    @Test
    public void addTest() {
        MatchIntervalList matchIntervalList = new MatchIntervalList();
        Assert.assertEquals(0, matchIntervalList.getMatchIntervalList().size());

        MatchInterval matchInterval = MatchTestData.getMatchInterval(10, 20);
        matchIntervalList.add(matchInterval);

        Assert.assertEquals(1, matchIntervalList.getMatchIntervalList().size());
        Assert.assertSame(matchInterval, matchIntervalList.getMatchIntervalList().get(0));
    }
}
