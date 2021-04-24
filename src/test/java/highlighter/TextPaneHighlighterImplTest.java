package highlighter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import textpane.HighlightableTextPane;

import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TextPaneHighlighterImplTest {
    @Mock
    HighlightableTextPane textPane;

    @Test
    public void testHighlightMatchingLinesWithNotNullMatchingLine() throws BadLocationException {
        Rectangle newCaretLine = HighlighterTestData.getLine(10, 80);
        Rectangle newMatchingLine = HighlighterTestData.getLine(10, 100);
        Mockito.when(textPane.getCaretLine()).thenReturn(newCaretLine);
        Mockito.when(textPane.getMatchingLine(newCaretLine)).thenReturn(newMatchingLine);

        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);
        TextPaneHighlighterImpl spiedHighlighter = Mockito.spy(highlighter);

        List<Rectangle> previouslyHighlightedLines = getPreviouslyHighlightedLines();
        Mockito.when(spiedHighlighter.getHighlightedLines()).thenReturn(previouslyHighlightedLines);

        int mockTextComponentWidth = 200;
        JTextComponent textComponent = getMockTextComponent(textPane, mockTextComponentWidth);

        spiedHighlighter.highlightMatchingLines();

        Mockito.verify(textPane).getCaretLine();
        Mockito.verify(textPane).getMatchingLine(newCaretLine);

        List<Rectangle> newLines = Arrays.asList(newCaretLine, newMatchingLine);
        verifyRepaintLines(mockTextComponentWidth, textComponent, previouslyHighlightedLines, newLines);

        Assert.assertEquals(
            newLines,
            spiedHighlighter.getHighlightedLines()
        );
    }

    @Test
    public void testHighlightMatchingLinesWithNullMatchingLine() throws BadLocationException {
        Rectangle newCaretLine = HighlighterTestData.getLine(10, 80);
        Mockito.when(textPane.getCaretLine()).thenReturn(newCaretLine);
        Mockito.when(textPane.getMatchingLine(newCaretLine)).thenReturn(null);

        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);
        TextPaneHighlighterImpl spiedHighlighter = Mockito.spy(highlighter);

        List<Rectangle> previouslyHighlightedLines = getPreviouslyHighlightedLines();
        Mockito.when(spiedHighlighter.getHighlightedLines()).thenReturn(previouslyHighlightedLines);

        int mockTextComponentWidth = 200;
        JTextComponent textComponent = getMockTextComponent(textPane, mockTextComponentWidth);

        spiedHighlighter.highlightMatchingLines();

        Mockito.verify(textPane).getCaretLine();
        Mockito.verify(textPane).getMatchingLine(newCaretLine);

        List<Rectangle> newLines = Collections.singletonList(newCaretLine);
        verifyRepaintLines(mockTextComponentWidth, textComponent, previouslyHighlightedLines, newLines);

        Assert.assertEquals(
            newLines,
            spiedHighlighter.getHighlightedLines()
        );
    }

    private JTextComponent getMockTextComponent(HighlightableTextPane textPane, int mockTextComponentWidth) {
        JTextComponent textComponent = Mockito.mock(JTextComponent.class);
        Mockito.when(textPane.getTextComponent()).thenReturn(textComponent);
        Mockito.when(textComponent.getWidth()).thenReturn(mockTextComponentWidth);

        return textComponent;
    }

    private List<Rectangle> getPreviouslyHighlightedLines() {
        Rectangle previousCaretLine = HighlighterTestData.getLine(10, 20);
        Rectangle previousMatchingLine = HighlighterTestData.getLine(10, 40);
        return new ArrayList<>(
            Arrays.asList(previousCaretLine, previousMatchingLine)
        );
    }

    private void verifyRepaintLines(
        int textComponentWidth,
        JTextComponent textComponent,
        List<Rectangle> previouslyHighlightedLines,
        List<Rectangle> newLines
    ) {
        for (Rectangle line : previouslyHighlightedLines) {
            Mockito.verify(textComponent).repaint(0, line.y, textComponentWidth, line.height);
        }
        for (Rectangle line : newLines) {
            Mockito.verify(textComponent).repaint(0, line.y, textComponentWidth, line.height);
        }
    }

    @Test
    public void testInstall() {
        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);

        JTextComponent textComponent = Mockito.mock(JTextComponent.class);
        highlighter.install(textComponent);
        Mockito.verifyNoInteractions(textComponent);
        Mockito.verifyNoInteractions(textPane);
    }

    @Test
    public void testDeInstall() {
        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);

        JTextComponent textComponent = Mockito.mock(JTextComponent.class);
        highlighter.deinstall(textComponent);
        Mockito.verifyNoInteractions(textComponent);
        Mockito.verifyNoInteractions(textPane);
    }

    @Test
    public void testPaint() {
        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);
        TextPaneHighlighterImpl spiedHighlighter = Mockito.spy(highlighter);

        TextPaneHighlightPainterImpl painter = Mockito.mock(TextPaneHighlightPainterImpl.class);
        Mockito.when(spiedHighlighter.getPainter()).thenReturn(painter);

        JTextComponent textComponent = Mockito.mock(JTextComponent.class);
        Rectangle bounds = Mockito.mock(Rectangle.class);
        Mockito.when(textComponent.getBounds()).thenReturn(bounds);
        Mockito.when(textPane.getTextComponent()).thenReturn(textComponent);

        Graphics graphics = Mockito.mock(Graphics.class);
        Graphics2D graphics2D = Mockito.mock(Graphics2D.class);
        Mockito.when(graphics.create()).thenReturn(graphics2D);
        spiedHighlighter.paint(graphics);

        Mockito.verify(textPane).getTextComponent();
        Mockito.verify(graphics).create();
        Mockito.verify(painter).paint(graphics2D, 0, 0, bounds, textComponent);
        Mockito.verify(graphics2D).dispose();
    }

    @Test
    public void testAddHighlight() {
        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);

        Highlighter.HighlightPainter highlightPainter = Mockito.mock(Highlighter.HighlightPainter.class);
        Object object = highlighter.addHighlight(0, 0, highlightPainter);

        Assert.assertNull(object);
        Mockito.verifyNoInteractions(highlightPainter);
        Mockito.verifyNoInteractions(textPane);
    }

    @Test
    public void testRemoveHighlight() {
        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);

        Object tag = Mockito.mock(Object.class);
        highlighter.removeHighlight(tag);

        Mockito.verifyNoInteractions(tag);
        Mockito.verifyNoInteractions(textPane);
    }

    @Test
    public void testRemoveAllHighlights() {
        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);

        highlighter.removeAllHighlights();

        Mockito.verifyNoInteractions(textPane);
    }

    @Test
    public void testChangeHighlight() {
        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);

        Object tag = Mockito.mock(Object.class);
        highlighter.changeHighlight(tag, 0, 0);

        Mockito.verifyNoInteractions(tag);
        Mockito.verifyNoInteractions(textPane);
    }

    @Test
    public void testGetHighlights() {
        TextPaneHighlighterImpl highlighter = new TextPaneHighlighterImpl(textPane);

        Highlighter.Highlight[] highlights = highlighter.getHighlights();

        Assert.assertEquals(0, highlights.length);
        Mockito.verifyNoInteractions(textPane);
    }
}
