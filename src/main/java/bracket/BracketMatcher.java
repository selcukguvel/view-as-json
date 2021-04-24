package bracket;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BracketMatcher {
    private final Map<BracketLine, BracketLine> bracketLinePairMap;

    public BracketMatcher() {
        this.bracketLinePairMap = new HashMap<>();
    }

    public Map<BracketLine, BracketLine> getBracketLinePairMap() {
        return bracketLinePairMap;
    }

    public void populateBrackets(List<Bracket> matchingBrackets, JTextComponent textComponent)
        throws BadLocationException {

        Stack<Bracket> stack = new Stack<>();
        for (Bracket bracket : matchingBrackets) {
            int bracketIndex = bracket.getIndex();
            String bracketString = bracket.getText();

            boolean isMatchingBracket = false;
            if (!stack.isEmpty()) {
                isMatchingBracket = checkMatching(stack.peek().getText(), bracketString);
            }

            if (isMatchingBracket) {
                int lastBracketIndex = stack.pop().getIndex();

                BracketLine bracketLine = new BracketLine(textComponent.modelToView(bracketIndex));
                BracketLine matchingBracketLine = new BracketLine(textComponent.modelToView(lastBracketIndex));

                bracketLinePairMap.put(bracketLine, matchingBracketLine);
                bracketLinePairMap.put(matchingBracketLine, bracketLine);
            } else {
                stack.push(new Bracket(bracketString, bracketIndex));
            }
        }
    }

    public List<Bracket> getMatchingBrackets(String json) {
        Pattern bracketMatcherRegex = Pattern.compile("[{}\\[\\]]");
        Matcher matcher = bracketMatcherRegex.matcher(json);

        List<Bracket> matchingBrackets = new ArrayList<>();
        while (matcher.find()) {
            int matchingStartIndex = matcher.start();
            String bracket = json.substring(matchingStartIndex, matchingStartIndex + 1);

            matchingBrackets.add(new Bracket(bracket, matchingStartIndex));
        }

        return matchingBrackets;
    }

    private boolean checkMatching(String lastBracketInStack, String currentBracket) {
        switch (currentBracket) {
            case "[":
                return lastBracketInStack.equals("]");
            case "]":
                return lastBracketInStack.equals("[");
            case "{":
                return lastBracketInStack.equals("}");
            case "}":
                return lastBracketInStack.equals("{");
            default:
                return false;
        }
    }

    public Rectangle getMatchingBracketLine(Rectangle currentLine) {
        BracketLine matchingBracketLine = getBracketLinePairMap().get(new BracketLine(currentLine));

        if (matchingBracketLine == null) {
            return null;
        }

        return matchingBracketLine.getLine();
    }
}
