package ui.highlighter;

import javax.swing.text.Highlighter;

public interface MyHighlighter extends Highlighter {
    void highlightMatchingLines();
}
