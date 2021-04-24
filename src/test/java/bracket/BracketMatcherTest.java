package bracket;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ui.bracket.Bracket;
import ui.bracket.BracketLine;
import ui.bracket.BracketMatcher;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.util.*;
import java.util.stream.Collectors;

public class BracketMatcherTest {

    @Test
    public void populateBracketsTest() throws BadLocationException {
        BracketMatcher bracketMatcher = new BracketMatcher();

        JTextComponent textComponent = Mockito.mock(JTextComponent.class);
        Mockito.when(textComponent.modelToView(Mockito.anyInt())).thenAnswer(
            invocation -> {
                int bracketIndex = (int) invocation.getArguments()[0];
                return BracketTestData.getLine(10, bracketIndex);
            }
        );

        List<Bracket> brackets = Arrays.asList(
            new Bracket("{", 0),
            new Bracket("[", 70),
            new Bracket("]", 100),
            new Bracket("}", 102)
        );
        List<BracketLine> bracketLines = brackets.stream().map(bracket ->
            BracketTestData.getBracketLine(10, bracket.getIndex())
        ).collect(Collectors.toList());

        bracketMatcher.populateBrackets(brackets, textComponent);
        Map<BracketLine, BracketLine> bracketLinePairMap = bracketMatcher.getBracketLinePairMap();

        Assert.assertEquals(bracketLines.get(1), bracketLinePairMap.get(bracketLines.get(2)));
        Assert.assertEquals(bracketLines.get(2), bracketLinePairMap.get(bracketLines.get(1)));

        Assert.assertEquals(bracketLines.get(0), bracketLinePairMap.get(bracketLines.get(3)));
        Assert.assertEquals(bracketLines.get(3), bracketLinePairMap.get(bracketLines.get(0)));
    }

    @Test
    public void getMatchingBracketsTest() {
        BracketMatcher bracketMatcher = new BracketMatcher();
        String jsonString = BracketTestData.getJsonString();

        List<Bracket> expectedBrackets = Arrays.asList(
            new Bracket("{", 0),
            new Bracket("[", 70),
            new Bracket("]", 100),
            new Bracket("}", 102)
        );

        List<Bracket> matchingBrackets = bracketMatcher.getMatchingBrackets(jsonString);

        Assert.assertEquals(
            expectedBrackets,
            matchingBrackets
        );
    }

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
