package ui.highlighter;

import ui.textpane.HighlightableTextPane;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TextPaneHighlighterImpl implements TextPaneHighlighter {
    private final TextPaneHighlightPainter painter;
    private final HighlightableTextPane textPane;
    private final List<Rectangle> highlightedLines;

    public TextPaneHighlighterImpl(HighlightableTextPane textPane) {
        this.textPane = textPane;
        this.painter = new TextPaneHighlightPainterImpl(textPane);
        this.highlightedLines = new ArrayList<>();
    }

    public void highlightMatchingLines() {
        try {
            Rectangle caretLine = textPane.getCaretLine();
            Rectangle matchingLine = textPane.getMatchingLine(caretLine);

            if (isDifferentBracketPairSelected(caretLine)) {
                removeHighlightedLines();
                highlightNewLines(caretLine, matchingLine);
            }
        } catch (BadLocationException ignored) {
        }
    }

    private void removeHighlightedLines() {
        JTextComponent textComponent = textPane.getTextComponent();
        for (Rectangle line : highlightedLines) {
            textComponent.repaint(0, line.y, textComponent.getWidth(), line.height);
        }
    }

    private void highlightNewLines(Rectangle caretLine, Rectangle matchingLine) {
        JTextComponent textComponent = textPane.getTextComponent();
        textComponent.repaint(0, caretLine.y, textComponent.getWidth(), caretLine.height);
        if (matchingLine != null)
            textComponent.repaint(0, matchingLine.y, textComponent.getWidth(), matchingLine.height);

        highlightedLines.clear();
        highlightedLines.add(caretLine);
        if (matchingLine != null)
            highlightedLines.add(matchingLine);
    }

    public boolean isDifferentBracketPairSelected(Rectangle highlightedLine) {
        for (Rectangle line : highlightedLines) {
            if (line.y == highlightedLine.y) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void install(JTextComponent c) {
    }

    @Override
    public void deinstall(JTextComponent c) {
    }

    @Override
    public void paint(Graphics g) {
        JTextComponent textComponent = textPane.getTextComponent();
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            painter.paint(g2d, 0, 0, textComponent.getBounds(), textComponent);
        } finally {
            g2d.dispose();
        }
    }

    @Override
    public Object addHighlight(int p0, int p1, HighlightPainter p) {
        return null;
    }

    @Override
    public void removeHighlight(Object tag) {
    }

    @Override
    public void removeAllHighlights() {
    }

    @Override
    public void changeHighlight(Object tag, int p0, int p1) {
    }

    @Override
    public Highlight[] getHighlights() {
        return new Highlight[0];
    }
}
