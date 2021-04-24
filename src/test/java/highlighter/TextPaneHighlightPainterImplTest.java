package highlighter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import style.Colors;
import textpane.HighlightableTextPane;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;

@RunWith(MockitoJUnitRunner.class)
public class TextPaneHighlightPainterImplTest {
    @InjectMocks
    TextPaneHighlightPainterImpl painter;

    @Mock
    HighlightableTextPane textPane;

    @Mock
    Graphics graphics;

    @Mock
    Shape bounds;

    @Mock
    JTextComponent textComponent;

    @Test
    public void testPaintWithDifferentCaretAndMatchingLine() throws BadLocationException {
        Rectangle caretLine = HighlighterTestData.getLine(10, 20);
        Rectangle matchingLine = HighlighterTestData.getLine(10, 40);

        Mockito.when(textPane.getCaretLine()).thenReturn(caretLine);
        Mockito.when(textPane.getMatchingLine(caretLine)).thenReturn(matchingLine);

        painter.paint(graphics, 0, 0, bounds, textComponent);

        Mockito.verify(graphics).setColor(Colors.matchingLineBackground);
        Mockito.verify(graphics).fillRect(matchingLine.x, matchingLine.y, matchingLine.width, matchingLine.height);

        Mockito.verify(graphics).setColor(Colors.caretLineBackground);
        Mockito.verify(graphics).fillRect(caretLine.x, caretLine.y, caretLine.width, caretLine.height);

        Mockito.verify(graphics, Mockito.times(2)).setColor(Mockito.any(Color.class));
        Mockito.verify(graphics, Mockito.times(2)).fillRect(
            Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()
        );
    }

    @Test
    public void testPaintWithSameCaretAndMatchingLine() throws BadLocationException {
        Rectangle caretLine = HighlighterTestData.getLine(10, 20);
        Rectangle matchingLine = HighlighterTestData.getLine(10, 20);

        Mockito.when(textPane.getCaretLine()).thenReturn(caretLine);
        Mockito.when(textPane.getMatchingLine(caretLine)).thenReturn(matchingLine);

        painter.paint(graphics, 0, 0, bounds, textComponent);

        Mockito.verify(graphics).setColor(Colors.caretLineBackground);
        Mockito.verify(graphics).fillRect(caretLine.x, caretLine.y, caretLine.width, caretLine.height);

        Mockito.verify(graphics, Mockito.times(1)).setColor(Mockito.any(Color.class));
        Mockito.verify(graphics, Mockito.times(1)).fillRect(
            Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()
        );
    }

    @Test
    public void testPaintWithNullMatchingLine() throws BadLocationException {
        Rectangle caretLine = HighlighterTestData.getLine(10, 20);

        Mockito.when(textPane.getCaretLine()).thenReturn(caretLine);
        Mockito.when(textPane.getMatchingLine(caretLine)).thenReturn(null);

        painter.paint(graphics, 0, 0, bounds, textComponent);

        Mockito.verify(graphics).setColor(Colors.caretLineBackground);
        Mockito.verify(graphics).fillRect(caretLine.x, caretLine.y, caretLine.width, caretLine.height);

        Mockito.verify(graphics, Mockito.times(1)).setColor(Mockito.any(Color.class));
        Mockito.verify(graphics, Mockito.times(1)).fillRect(
            Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()
        );
    }
}
