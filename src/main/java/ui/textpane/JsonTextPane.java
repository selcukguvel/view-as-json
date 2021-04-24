package ui.textpane;

import ui.bracket.Bracket;
import ui.highlighter.TextPaneHighlighterImpl;
import ui.bracket.BracketMatcher;
import ui.style.JsonStyleManager;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;

public class JsonTextPane implements HighlightableTextPane {
    private final BracketMatcher bracketMatcher;
    private final JScrollPane scrollPane;
    private final JTextComponent textComponent;
    private final JsonStyleManager jsonStyleManager;

    public JsonTextPane(
        BracketMatcher bracketMatcher,
        JScrollPane scrollPane,
        JTextComponent textComponent,
        JsonStyleManager jsonStyleManager
    ) {
        this.bracketMatcher = bracketMatcher;
        this.scrollPane = scrollPane;
        this.textComponent = textComponent;
        this.jsonStyleManager = jsonStyleManager;
    }

    @Override
    public JTextComponent getTextComponent() {
        return textComponent;
    }

    public void enableHighlighter() {
        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(this);
        textComponent.setHighlighter(highlighter);

        JsonTextPaneListener textPaneListener = new JsonTextPaneListenerImpl(highlighter, this);
        textComponent.addKeyListener(textPaneListener);
        textComponent.addMouseMotionListener(textPaneListener);
        textComponent.addCaretListener(textPaneListener);
        textComponent.addMouseListener(textPaneListener);
    }

    public void addContent(String jsonString) throws BadLocationException {
        jsonStyleManager.format(jsonString);
        List<Bracket> matchingBrackets = bracketMatcher.getMatchingBrackets(jsonString);
        bracketMatcher.populateBrackets(matchingBrackets, getTextComponent());
        textComponent.setCaretPosition(0);
    }

    @Override
    public Rectangle getCaretLine() throws BadLocationException {
        int caretLocation = textComponent.getCaretPosition();
        Rectangle caretLine = textComponent.modelToView(caretLocation);
        return new Rectangle(0, caretLine.y, textComponent.getWidth(), caretLine.height);
    }

    @Override
    public Rectangle getMatchingLine(Rectangle caretLine) {
        Rectangle matchingBracketLine = bracketMatcher.getMatchingBracketLine(caretLine);
        Rectangle matchingBracketRectangle = null;
        if (matchingBracketLine != null) {
            matchingBracketRectangle = new Rectangle(0, matchingBracketLine.y, textComponent.getWidth(), matchingBracketLine.height);
        }
        return matchingBracketRectangle;
    }

    @Override
    public void scrollToMatchingLine(Rectangle caretLine) {
        Rectangle matchingBracketLine = bracketMatcher.getMatchingBracketLine(caretLine);

        if (matchingBracketLine != null) {
            Rectangle visibleWindow = textComponent.getVisibleRect();

            boolean isNewCaretLineBelow = matchingBracketLine.y > caretLine.y;
            if (isNewCaretLineBelow) {
                boolean isMatchingBracketLineNotSeen = matchingBracketLine.y >= (visibleWindow.y + visibleWindow.height);
                if (isMatchingBracketLineNotSeen) {
                    int newScrollBarValue = matchingBracketLine.y - (visibleWindow.height / 2);
                    scrollPane.getVerticalScrollBar().setValue(newScrollBarValue);
                }
            } else {
                boolean isMatchingBracketLineNotSeen = matchingBracketLine.y <= visibleWindow.y;
                if (isMatchingBracketLineNotSeen) {
                    int newScrollBarValue = matchingBracketLine.y - caretLine.height;
                    scrollPane.getVerticalScrollBar().setValue(newScrollBarValue);
                }
            }

            int matchingBracketLinePosition = textComponent.viewToModel(matchingBracketLine.getLocation());
            textComponent.setCaretPosition(matchingBracketLinePosition);
            textComponent.repaint(0, matchingBracketLine.y, textComponent.getWidth(), matchingBracketLine.height);
        }
    }
}
