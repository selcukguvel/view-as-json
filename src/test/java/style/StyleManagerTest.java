package style;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ui.style.Colors;
import ui.style.StyleManager;

import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import java.awt.*;

import static javax.swing.text.StyleConstants.*;
import static org.mockito.ArgumentMatchers.eq;

public class StyleManagerTest {
    @Test
    public void getDefaultStyleTest() {
        StyledDocument doc = Mockito.mock(StyledDocument.class);
        Style style = Mockito.mock(Style.class);

        Mockito.when(doc.addStyle(Mockito.anyString(), Mockito.any())).thenReturn(style);
        StyleManager.getDefaultStyle(doc);

        verifyStyleAttributes(style, 16, "Helvetica", Colors.defaultText);
    }

    @Test
    public void getJsonKeyStyleTest() {
        StyledDocument doc = Mockito.mock(StyledDocument.class);
        Style style = Mockito.mock(Style.class);

        Mockito.when(doc.addStyle(Mockito.anyString(), Mockito.any())).thenReturn(style);
        StyleManager.getJsonKeyStyle(doc);

        verifyStyleAttributes(style, 16, "Helvetica", Colors.jsonKey);
    }

    @Test
    public void getStringJsonValueStyleTest() {
        StyledDocument doc = Mockito.mock(StyledDocument.class);
        Style style = Mockito.mock(Style.class);

        Mockito.when(doc.addStyle(Mockito.anyString(), Mockito.any())).thenReturn(style);
        StyleManager.getStringJsonValueStyle(doc);

        verifyStyleAttributes(style, 16, "Helvetica", Colors.stringJsonValue);
    }

    @Test
    public void getIntegerJsonValueStyleTest() {
        StyledDocument doc = Mockito.mock(StyledDocument.class);
        Style style = Mockito.mock(Style.class);

        Mockito.when(doc.addStyle(Mockito.anyString(), Mockito.any())).thenReturn(style);
        StyleManager.getIntegerJsonValueStyle(doc);

        verifyStyleAttributes(style, 16, "Helvetica", Colors.integerJsonValue);
    }

    @Test
    public void getBooleanJsonValueStyleTest() {
        StyledDocument doc = Mockito.mock(StyledDocument.class);
        Style style = Mockito.mock(Style.class);

        Mockito.when(doc.addStyle(Mockito.anyString(), Mockito.any())).thenReturn(style);
        StyleManager.getBooleanJsonValueStyle(doc);

        verifyStyleAttributes(style, 16, "Helvetica", Colors.booleanJsonValue);
    }

    @Test
    public void getNullJsonValueStyleTest() {
        StyledDocument doc = Mockito.mock(StyledDocument.class);
        Style style = Mockito.mock(Style.class);

        Mockito.when(doc.addStyle(Mockito.anyString(), Mockito.any())).thenReturn(style);
        StyleManager.getNullJsonValueStyle(doc);

        verifyStyleAttributes(style, 16, "Helvetica", Colors.nullJsonValue);
    }

    public void verifyStyleAttributes(
        Style style,
        int fontSize,
        String fontFamily,
        Color foreGround
    ) {
        ArgumentCaptor<Integer> fontSizeCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(style).addAttribute(eq(FontSize), fontSizeCaptor.capture());

        ArgumentCaptor<String> fontFamilyCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(style).addAttribute(eq(FontFamily), fontFamilyCaptor.capture());

        ArgumentCaptor<Color> foregroundCaptor = ArgumentCaptor.forClass(Color.class);
        Mockito.verify(style).addAttribute(eq(Foreground), foregroundCaptor.capture());

        Assert.assertEquals(fontSize, (int) fontSizeCaptor.getValue());
        Assert.assertEquals(fontFamily, fontFamilyCaptor.getValue());
        Assert.assertEquals(foreGround, foregroundCaptor.getValue());
    }
}
