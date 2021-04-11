package ui.highlighter;

import ui.textpane.HighlightableTextPane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyHighlighterImpl implements MyHighlighter {
    private final MyHighlightPainter painter;
    private final HighlightableTextPane textPane;
    private final List<Rectangle> highlightedLines;

    public MyHighlighterImpl(HighlightableTextPane textPane) {
        this.textPane = textPane;
        this.painter = new MyHighlightPainterImpl(textPane);
        this.highlightedLines = new ArrayList<>();
    }

    public void highlightMatchingLines() {
        SwingUtilities.invokeLater(() -> {
            try {
                Rectangle caretLine = textPane.getCaretLine();
                Rectangle matchingLine = textPane.getMatchingLine(caretLine);

                if (isDifferentBracketPairSelected(caretLine)) {
                    removeHighlightedLines();
                    highlightNewLines(caretLine, matchingLine);
                }
            } catch (BadLocationException ignored) {
            }
        });
    }

    private void removeHighlightedLines() {
        for (Rectangle line : highlightedLines) {
            textPane.repaint(0, line.y, textPane.getWidth(), line.height);
        }
    }

    private void highlightNewLines(Rectangle caretLine, Rectangle matchingLine) {
        textPane.repaint(0, caretLine.y, textPane.getWidth(), caretLine.height);
        if (matchingLine != null)
            textPane.repaint(0, matchingLine.y, textPane.getWidth(), matchingLine.height);

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
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            this.painter.paint(g2d, 0, 0, this.textPane.getBounds(), this.textPane);
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
