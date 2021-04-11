package ui.bracket;

import ui.textpane.HighlightableTextPane;

import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BracketMatcher {
    private final Map<BracketLine, BracketLine> bracketLinePairMap;

    public BracketMatcher() {
        this.bracketLinePairMap = new HashMap<>();
    }

    public void populateBrackets(String json, HighlightableTextPane textPane) throws BadLocationException {
        Pattern bracketMatcherRegex = Pattern.compile("[{}\\[\\]]");
        Matcher matcher = bracketMatcherRegex.matcher(json);

        Stack<Bracket> stack = new Stack<>();

        while (matcher.find()) {
            int matchingStartIndex = matcher.start();

            String bracket = json.substring(matchingStartIndex, matchingStartIndex + 1);

            boolean isMatchingBracket = false;
            if (!stack.isEmpty()) {
                isMatchingBracket = checkMatching(stack.peek().getText(), bracket);
            }

            if (isMatchingBracket) {
                int lastBracketOffset = stack.pop().getOffset();

                BracketLine bracketLine = new BracketLine(textPane.modelToView(matchingStartIndex));
                BracketLine matchingBracketLine = new BracketLine(textPane.modelToView(lastBracketOffset));

                bracketLinePairMap.put(bracketLine, matchingBracketLine);
                bracketLinePairMap.put(matchingBracketLine, bracketLine);
            } else {
                stack.push(new Bracket(bracket, matchingStartIndex));
            }
        }
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

    public Integer getMatchingBracketLocation(Rectangle highlightingRectangle, HighlightableTextPane textPane) {
        BracketLine matchingBracketLine = bracketLinePairMap.get(new BracketLine(highlightingRectangle));

        if (matchingBracketLine == null) {
            return null;
        }

        return textPane.viewToModel(matchingBracketLine.getLine().getLocation());
    }
}
