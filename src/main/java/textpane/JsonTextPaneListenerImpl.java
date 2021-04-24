package textpane;

import highlighter.TextPaneHighlighter;

import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JsonTextPaneListenerImpl implements JsonTextPaneListener {
    private final TextPaneHighlighter highlighter;
    private final HighlightableTextPane textPane;

    public JsonTextPaneListenerImpl(TextPaneHighlighter highlighter, HighlightableTextPane textPane) {
        this.highlighter = highlighter;
        this.textPane = textPane;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        highlighter.highlightMatchingLines();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            try {
                Rectangle caretLine = textPane.getCaretLine();
                textPane.scrollToMatchingLine(caretLine);
            } catch (BadLocationException ignored) {
            }
        }
        highlighter.highlightMatchingLines();
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        highlighter.highlightMatchingLines();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        highlighter.highlightMatchingLines();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        highlighter.highlightMatchingLines();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
