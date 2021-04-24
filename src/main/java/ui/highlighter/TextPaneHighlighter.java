package ui.highlighter;

import javax.swing.text.Highlighter;

public interface TextPaneHighlighter extends Highlighter {
    void highlightMatchingLines();
}
