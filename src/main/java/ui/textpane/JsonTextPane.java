package ui.textpane;

import com.intellij.util.ui.JBUI;
import ui.highlighter.MyHighlighterImpl;
import ui.bracket.BracketMatcher;
import ui.style.Colors;
import ui.style.JsonStyleManager;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;

import static ui.style.StyleManager.*;

public class JsonTextPane extends HighlightableTextPane {
    private final BracketMatcher bracketMatcher;
    private final JsonTextPaneListener textPaneListener;
    private final JScrollPane scrollPane;

    public JsonTextPane(JScrollPane scrollPane, String jsonString) {
        this.bracketMatcher = new BracketMatcher();
        MyHighlighterImpl highlighter = new MyHighlighterImpl(this);
        this.textPaneListener = new JsonTextPaneListenerImpl(highlighter, this);
        this.scrollPane = scrollPane;

        setSize(new Dimension(960, 720));
        init();

        try {
            addContent(jsonString);
            setHighlighter(highlighter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setBackground(Colors.jsonTextPaneBackground);
        setMargin(JBUI.insets(20));
        setCaretColor(getBackground());

        addKeyListener(textPaneListener);
        addMouseMotionListener(textPaneListener);
        addCaretListener(textPaneListener);
        addMouseListener(textPaneListener);

        setEditable(false);
    }


    public void addContent(String jsonString) throws BadLocationException {
        addStyledContentToDocument(jsonString);
        bracketMatcher.populateBrackets(jsonString, this);
        setCaretPosition(0);
    }

    private void addStyledContentToDocument(String jsonString) throws BadLocationException {
        StyledDocument doc = (StyledDocument) getDocument();
        doc.insertString(0, jsonString, getDefaultStyle(doc));
        new JsonStyleManager(doc).format(jsonString);
    }

    @Override
    public Rectangle getCaretLine() throws BadLocationException {
        int caretLocation = getCaretPosition();
        Rectangle caretLine = modelToView(caretLocation);
        return new Rectangle(0, caretLine.y, getWidth(), caretLine.height);
    }

    @Override
    public Rectangle getMatchingLine(Rectangle caretLine) {
        Rectangle matchingBracketLine = bracketMatcher.getMatchingBracketLine(caretLine);
        Rectangle matchingBracketRectangle = null;
        if (matchingBracketLine != null) {
            matchingBracketRectangle = new Rectangle(0, matchingBracketLine.y, getWidth(), matchingBracketLine.height);
        }
        return matchingBracketRectangle;
    }

    @Override
    public void scrollToMatchingLine() {
        SwingUtilities.invokeLater(() -> {
            try {
                Rectangle caretLine = getCaretLine();
                Rectangle matchingBracketLine = bracketMatcher.getMatchingBracketLine(caretLine);

                if (matchingBracketLine != null) {
                    Rectangle visibleWindow = getVisibleRect();

                    boolean isNewCaretLineBelow = matchingBracketLine.y > caretLine.y;
                    if (isNewCaretLineBelow) {
                        if (matchingBracketLine.y >= (visibleWindow.y + visibleWindow.height)) {
                            int newScrollBarValue = matchingBracketLine.y - (visibleWindow.height / 2);
                            scrollPane.getVerticalScrollBar().setValue(newScrollBarValue);
                        }
                    } else {
                        if (matchingBracketLine.y <= visibleWindow.y) {
                            int newScrollBarValue = matchingBracketLine.y;
                            newScrollBarValue -= caretLine.height;
                            scrollPane.getVerticalScrollBar().setValue(newScrollBarValue);
                        }
                    }

                    setCaretPosition(viewToModel(matchingBracketLine.getLocation()));
                    repaint(0, matchingBracketLine.y, getWidth(), matchingBracketLine.height);
                }
            } catch (BadLocationException ignored) {
            }
        });
    }
}
