package bracket;

import org.junit.Assert;
import org.junit.Test;

public class BracketLineTest {

    @Test
    public void equalsTest() {
        BracketLine firstBracketLine = BracketTestData.getBracketLine(10, 20);
        BracketLine secondBracketLine = BracketTestData.getBracketLine(20, 20);

        Assert.assertEquals(firstBracketLine, secondBracketLine);

        BracketLine thirdBracketLine = BracketTestData.getBracketLine(10, 40);

        Assert.assertNotEquals(firstBracketLine, thirdBracketLine);
    }

    @Test
    public void hashCodeTest() {
        BracketLine firstBracketLine = BracketTestData.getBracketLine(10, 20);
        BracketLine secondBracketLine = BracketTestData.getBracketLine(20, 20);

        Assert.assertEquals(firstBracketLine.hashCode(), secondBracketLine.hashCode());

        BracketLine thirdBracketLine = BracketTestData.getBracketLine(10, 40);

        Assert.assertNotEquals(firstBracketLine.hashCode(), thirdBracketLine.hashCode());
    }
}
