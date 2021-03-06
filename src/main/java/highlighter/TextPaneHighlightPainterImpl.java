package highlighter;

import style.Colors;
import textpane.HighlightableTextPane;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class TextPaneHighlightPainterImpl implements TextPaneHighlightPainter {
    private final HighlightableTextPane textPane;

    public TextPaneHighlightPainterImpl(HighlightableTextPane textPane) {
        this.textPane = textPane;
    }

    @Override
    public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
        try {
            Rectangle caretLine = textPane.getCaretLine();
            Rectangle matchingLine = textPane.getMatchingLine(caretLine);
            if (matchingLine != null && !caretLine.equals(matchingLine)) {
                draw(g, Colors.matchingLineBackground, matchingLine);
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
