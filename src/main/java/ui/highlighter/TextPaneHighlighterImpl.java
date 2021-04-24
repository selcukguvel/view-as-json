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

    public TextPaneHighlightPainter getPainter() {
        return painter;
    }

    public HighlightableTextPane getTextPane() {
        return textPane;
    }

    public List<Rectangle> getHighlightedLines() {
        return highlightedLines;
    }

    @Override
    public void highlightMatchingLines() {
        try {
            Rectangle caretLine = getTextPane().getCaretLine();
            Rectangle matchingLine = getTextPane().getMatchingLine(caretLine);

            // TODO Prevent highlighting if same caret line is selected
            removeHighlightedLines();
            highlightNewLines(caretLine, matchingLine);
        } catch (BadLocationException ignored) {
        }
    }

    private void removeHighlightedLines() {
        JTextComponent textComponent = getTextPane().getTextComponent();
        for (Rectangle line : getHighlightedLines()) {
            textComponent.repaint(0, line.y, textComponent.getWidth(), line.height);
        }
    }

    private void highlightNewLines(Rectangle caretLine, Rectangle matchingLine) {
        JTextComponent textComponent = getTextPane().getTextComponent();
        textComponent.repaint(0, caretLine.y, textComponent.getWidth(), caretLine.height);
        if (matchingLine != null)
            textComponent.repaint(0, matchingLine.y, textComponent.getWidth(), matchingLine.height);

        getHighlightedLines().clear();
        getHighlightedLines().add(caretLine);
        if (matchingLine != null)
            getHighlightedLines().add(matchingLine);
    }


    @Override
    public void install(JTextComponent c) {
    }

    @Override
    public void deinstall(JTextComponent c) {
    }

    @Override
    public void paint(Graphics g) {
        JTextComponent textComponent = getTextPane().getTextComponent();
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            getPainter().paint(g2d, 0, 0, textComponent.getBounds(), textComponent);
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
