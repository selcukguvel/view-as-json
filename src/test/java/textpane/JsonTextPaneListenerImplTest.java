package textpane;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ui.highlighter.MyHighlighter;
import ui.textpane.HighlightableTextPane;
import ui.textpane.JsonTextPaneListenerImpl;

import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

@RunWith(MockitoJUnitRunner.class)
public class JsonTextPaneListenerImplTest {
    @InjectMocks
    private JsonTextPaneListenerImpl jsonTextPaneListenerImpl;

    @Mock
    private MyHighlighter highlighter;

    @Mock
    private HighlightableTextPane textPane;

    @Mock
    private KeyEvent keyEvent;

    @Mock
    private MouseEvent mouseEvent;

    @Mock
    private CaretEvent caretEvent;

    @Test
    public void keyTypedTest() {
        jsonTextPaneListenerImpl.keyTyped(keyEvent);

        Mockito.verifyNoInteractions(highlighter);
    }

    @Test
    public void keyPressedTest() {
        jsonTextPaneListenerImpl.keyPressed(keyEvent);

        Mockito.verify(highlighter).highlightMatchingLines();
    }

    @Test
    public void keyReleasedWithSpaceButtonTest() throws BadLocationException {
        Mockito.when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_SPACE);

        Rectangle mockCaretLine = TextPaneTestData.getLine(10, 20);
        Mockito.when(textPane.getCaretLine()).thenReturn(mockCaretLine);

        jsonTextPaneListenerImpl.keyReleased(keyEvent);

        Mockito.verify(textPane).getCaretLine();
        Mockito.verify(textPane).scrollToMatchingLine(mockCaretLine);
        Mockito.verify(highlighter).highlightMatchingLines();
    }

    @Test
    public void keyReleasedWithoutSpaceButtonTest() {
        Mockito.when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);

        jsonTextPaneListenerImpl.keyReleased(keyEvent);

        Mockito.verifyNoInteractions(textPane);
        Mockito.verify(highlighter).highlightMatchingLines();
    }

    @Test
    public void caretUpdateTest() {
        jsonTextPaneListenerImpl.caretUpdate(caretEvent);

        Mockito.verify(highlighter).highlightMatchingLines();
    }

    @Test
    public void mouseDraggedTest() {
        jsonTextPaneListenerImpl.mouseDragged(mouseEvent);

        Mockito.verify(highlighter).highlightMatchingLines();
    }

    @Test
    public void mouseMovedTest() {
        jsonTextPaneListenerImpl.mouseMoved(mouseEvent);

        Mockito.verifyNoInteractions(highlighter);
    }

    @Test
    public void mouseClickedTest() {
        jsonTextPaneListenerImpl.mouseClicked(mouseEvent);

        Mockito.verifyNoInteractions(highlighter);
    }

    @Test
    public void mousePressedTest() {
        jsonTextPaneListenerImpl.mousePressed(mouseEvent);

        Mockito.verify(highlighter).highlightMatchingLines();
    }

    @Test
    public void mouseReleasedTest() {
        jsonTextPaneListenerImpl.mouseReleased(mouseEvent);

        Mockito.verifyNoInteractions(highlighter);
    }

    @Test
    public void mouseEnteredTest() {
        jsonTextPaneListenerImpl.mouseEntered(mouseEvent);

        Mockito.verifyNoInteractions(highlighter);
    }

    @Test
    public void mouseExitedTest() {
        jsonTextPaneListenerImpl.mouseExited(mouseEvent);

        Mockito.verifyNoInteractions(highlighter);
    }
}
