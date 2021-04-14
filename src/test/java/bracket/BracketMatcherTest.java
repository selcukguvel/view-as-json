package bracket;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ui.bracket.BracketLine;
import ui.bracket.BracketMatcher;

import java.util.HashMap;
import java.util.Map;

public class BracketMatcherTest {

    @Test
    public void getMatchingBracketLineTest() {
        BracketLine currentBracketLine = BracketTestData.getBracketLine(10, 20);
        BracketLine matchingBracketLine = BracketTestData.getBracketLine(10, 60);
        BracketLine nonBracketLine = BracketTestData.getBracketLine(10, 40);

        BracketMatcher bracketMatcher = Mockito.spy(BracketMatcher.class);

        Map<BracketLine, BracketLine> bracketLinePairMap = new HashMap<>();
        bracketLinePairMap.put(currentBracketLine, matchingBracketLine);
        bracketLinePairMap.put(matchingBracketLine, currentBracketLine);

        Mockito.when(bracketMatcher.getBracketLinePairMap()).thenReturn(bracketLinePairMap);

        Assert.assertEquals(
                matchingBracketLine.getLine(),
                bracketMatcher.getMatchingBracketLine(currentBracketLine.getLine())
        );
        Assert.assertEquals(
                currentBracketLine.getLine(),
                bracketMatcher.getMatchingBracketLine(matchingBracketLine.getLine())
        );
        Assert.assertNull(bracketMatcher.getMatchingBracketLine(nonBracketLine.getLine()));
    }
}
