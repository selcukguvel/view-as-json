package ui.highlighter;

import ui.style.Colors;
import ui.textpane.HighlightableTextPane;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class MyHighlightPainterImpl implements MyHighlightPainter {
    private final HighlightableTextPane textPane;

    public MyHighlightPainterImpl(HighlightableTextPane textPane) {
        this.textPane = textPane;
    }

    @Override
    public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
        try {
            Rectangle caretLine = textPane.getCaretLine();
            Rectangle matchingLine = textPane.getMatchingLine(caretLine);
            if (matchingLine != null) {
                if (!caretLine.equals(matchingLine)) {
                    draw(g, Colors.matchingLineBackground, matchingLine);
                }
            }
            draw(g, Colors.caretLineBackground, caretLine);
        } catch (BadLocationException ignored) {
        }
    }

    private void draw(Graphics g, Color color, Rectangle rect) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
}
