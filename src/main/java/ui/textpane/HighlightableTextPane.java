package ui.textpane;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;

public interface HighlightableTextPane {
    Rectangle getCaretLine() throws BadLocationException;

    Rectangle getMatchingLine(Rectangle caretLine) throws BadLocationException;

    void scrollToMatchingLine(Rectangle caretLine);

    JTextComponent getTextComponent();
}
