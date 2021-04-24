package textpane;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import ui.bracket.Bracket;
import ui.bracket.BracketMatcher;
import ui.highlighter.TextPaneHighlighterImpl;
import ui.style.JsonStyleManager;
import ui.textpane.JsonTextPane;
import ui.textpane.JsonTextPaneListener;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class JsonTextPaneTest {
    @InjectMocks
    JsonTextPane jsonTextPane;

    @Mock
    BracketMatcher bracketMatcher;

    @Mock
    JScrollPane scrollPane;

    @Mock
    JTextComponent textComponent;

    @Mock
    JsonStyleManager jsonStyleManager;

    @Test
    public void testEnableHighlighter() {
        jsonTextPane.enableHighlighter();

        Mockito.verify(textComponent).setHighlighter(Mockito.any(TextPaneHighlighterImpl.class));
        Mockito.verify(textComponent).addKeyListener(Mockito.any(JsonTextPaneListener.class));
        Mockito.verify(textComponent).addMouseMotionListener(Mockito.any(JsonTextPaneListener.class));
        Mockito.verify(textComponent).addCaretListener(Mockito.any(JsonTextPaneListener.class));
        Mockito.verify(textComponent).addMouseListener(Mockito.any(JsonTextPaneListener.class));
    }

    @Test
    public void testAddContent() throws BadLocationException {
        String jsonString = "mock-json";
        List<Bracket> matchingBrackets = new ArrayList<>();
        Mockito.when(bracketMatcher.getMatchingBrackets(jsonString)).thenReturn(matchingBrackets);

        jsonTextPane.addContent(jsonString);

        Mockito.verify(jsonStyleManager).format(jsonString);
        Mockito.verify(bracketMatcher).populateBrackets(matchingBrackets, textComponent);
        Mockito.verify(textComponent).setCaretPosition(0);
    }

    @Test
    public void testGetCaretLine() throws BadLocationException {
        int caretLocation = 0;
        int textComponentWidth = 200;
        Rectangle mockCaretLine = TextPaneTestData.getLine(10, 20);
        Mockito.when(textComponent.getWidth()).thenReturn(textComponentWidth);
        Mockito.when(textComponent.getCaretPosition()).thenReturn(caretLocation);
        Mockito.when(textComponent.modelToView(caretLocation)).thenReturn(mockCaretLine);

        Rectangle caretLine = jsonTextPane.getCaretLine();

        Assert.assertEquals(
            new Rectangle(0, mockCaretLine.y, textComponentWidth, mockCaretLine.height),
            caretLine
        );
    }

    @Test
    public void testGetNotNullMatchingLine() {
        Rectangle caretLine = TextPaneTestData.getLine(10, 20);
        Rectangle mockMatchingLine = TextPaneTestData.getLine(10, 40);
        Mockito.when(bracketMatcher.getMatchingBracketLine(caretLine)).thenReturn(mockMatchingLine);

        int mockTextComponentWidth = 200;
        Mockito.when(textComponent.getWidth()).thenReturn(mockTextComponentWidth);

        Rectangle matchingLine = jsonTextPane.getMatchingLine(caretLine);

        Assert.assertEquals(
            new Rectangle(0, mockMatchingLine.y, mockTextComponentWidth, mockMatchingLine.height),
            matchingLine
        );
    }

    @Test
    public void testGetNullMatchingLine() {
        Rectangle caretLine = TextPaneTestData.getLine(10, 20);
        Mockito.when(bracketMatcher.getMatchingBracketLine(caretLine)).thenReturn(null);

        Rectangle matchingLine = jsonTextPane.getMatchingLine(caretLine);

        Assert.assertNull(matchingLine);
    }

    @Test
    public void testScrollToSeenMatchingLineInBelow() {
        Rectangle caretLine = TextPaneTestData.getLine(10, 20);
        Rectangle matchingLine = TextPaneTestData.getLine(10, 40);

        Mockito.when(bracketMatcher.getMatchingBracketLine(caretLine)).thenReturn(matchingLine);

        Rectangle visibleWindow = TextPaneTestData.getVisibleWindow(0);
        stubTextComponent(visibleWindow, matchingLine);

        jsonTextPane.scrollToMatchingLine(caretLine);

        Mockito.verifyNoInteractions(scrollPane);
        verifyMatchingLineRepaint(matchingLine, visibleWindow);
    }

    @Test
    public void testScrollToNotSeenMatchingLineInBelow() {
        Rectangle caretLine = TextPaneTestData.getLine(10, 20);
        Rectangle matchingLine = TextPaneTestData.getLine(10, 500);

        Mockito.when(bracketMatcher.getMatchingBracketLine(caretLine)).thenReturn(matchingLine);

        Rectangle visibleWindow = TextPaneTestData.getVisibleWindow(0);
        stubTextComponent(visibleWindow, matchingLine);

        JScrollBar verticalScrollBar = Mockito.mock(JScrollBar.class);
        Mockito.when(scrollPane.getVerticalScrollBar()).thenReturn(verticalScrollBar);

        jsonTextPane.scrollToMatchingLine(caretLine);

        Mockito.verify(verticalScrollBar).setValue(matchingLine.y - (visibleWindow.height / 2));
        verifyMatchingLineRepaint(matchingLine, visibleWindow);
    }

    @Test
    public void testScrollToSeenMatchingLineInAbove() {
        Rectangle caretLine = TextPaneTestData.getLine(10, 40);
        Rectangle matchingLine = TextPaneTestData.getLine(10, 20);

        Mockito.when(bracketMatcher.getMatchingBracketLine(caretLine)).thenReturn(matchingLine);

        Rectangle visibleWindow = TextPaneTestData.getVisibleWindow(0);
        stubTextComponent(visibleWindow, matchingLine);

        jsonTextPane.scrollToMatchingLine(caretLine);

        Mockito.verifyNoInteractions(scrollPane);
        verifyMatchingLineRepaint(matchingLine, visibleWindow);
    }

    @Test
    public void testScrollToNotSeenMatchingLineInAbove() {
        Rectangle caretLine = TextPaneTestData.getLine(10, 500);
        Rectangle matchingLine = TextPaneTestData.getLine(10, 20);

        Mockito.when(bracketMatcher.getMatchingBracketLine(caretLine)).thenReturn(matchingLine);

        Rectangle visibleWindow = TextPaneTestData.getVisibleWindow(40);
        stubTextComponent(visibleWindow, matchingLine);

        JScrollBar mockVerticalScrollBar = Mockito.mock(JScrollBar.class);
        Mockito.when(scrollPane.getVerticalScrollBar()).thenReturn(mockVerticalScrollBar);

        jsonTextPane.scrollToMatchingLine(caretLine);

        Mockito.verify(mockVerticalScrollBar).setValue(matchingLine.y - caretLine.height);
        verifyMatchingLineRepaint(matchingLine, visibleWindow);
    }

    @Test
    public void testScrollToNullMatchingLine() {
        Rectangle caretLine = TextPaneTestData.getLine(10, 20);
        Mockito.when(bracketMatcher.getMatchingBracketLine(caretLine)).thenReturn(null);

        jsonTextPane.scrollToMatchingLine(caretLine);

        Mockito.verifyNoInteractions(textComponent);
        Mockito.verifyNoInteractions(scrollPane);
    }

    private void verifyMatchingLineRepaint(Rectangle matchingLine, Rectangle mockVisibleWindow) {
        // TODO: Mockito.when(matchingLine.getLocation())..
        Mockito.verify(textComponent).viewToModel(Mockito.any(Point.class));
        Mockito.verify(textComponent).setCaretPosition(matchingLine.y);
        Mockito.verify(textComponent).repaint(0, matchingLine.y, mockVisibleWindow.width, matchingLine.height);
    }

    private void stubTextComponent(Rectangle visibleWindow, Rectangle matchingLine) {
        Mockito.when(textComponent.getVisibleRect()).thenReturn(visibleWindow);
        Mockito.when(textComponent.viewToModel(Mockito.any(Point.class))).thenReturn(matchingLine.y);
        Mockito.when(textComponent.getWidth()).thenReturn(visibleWindow.width);
    }
}
