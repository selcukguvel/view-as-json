package ui.textpane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

public abstract class HighlightableTextPane extends JTextPane {
    public abstract Rectangle getCaretLine() throws BadLocationException;

    public abstract Rectangle getMatchingLine(Rectangle caretLine) throws BadLocationException;

    public abstract void scrollToMatchingLine();
}
