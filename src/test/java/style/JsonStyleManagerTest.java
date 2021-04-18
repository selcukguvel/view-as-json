package style;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ui.style.JsonStyleManager;
import ui.style.StyleManager;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static style.StyleTestData.*;

public class JsonStyleManagerTest {
    @Test
    public void formatTest() throws BadLocationException {
        String jsonString = StyleTestData.getCountryJsonString();
        StyledDocument doc = Mockito.mock(StyledDocument.class);

        Style defaultStyle = Mockito.mock(Style.class);
        Style jsonKeyStyle = Mockito.mock(Style.class);
        Style stringJsonValueStyle = Mockito.mock(Style.class);
        Style numberJsonValueStyle = Mockito.mock(Style.class);
        Style booleanJsonValueStyle = Mockito.mock(Style.class);
        Style nullJsonValueStyle = Mockito.mock(Style.class);

        try (MockedStatic<StyleManager> styleManager = Mockito.mockStatic(StyleManager.class)) {
            styleManager.when(() -> StyleManager.getDefaultStyle(doc))
                .thenReturn(defaultStyle);
            styleManager.when(() -> StyleManager.getJsonKeyStyle(doc))
                .thenReturn(jsonKeyStyle);
            styleManager.when(() -> StyleManager.getStringJsonValueStyle(doc))
                .thenReturn(stringJsonValueStyle);
            styleManager.when(() -> StyleManager.getNumberJsonValueStyle(doc))
                .thenReturn(numberJsonValueStyle);
            styleManager.when(() -> StyleManager.getBooleanJsonValueStyle(doc))
                .thenReturn(booleanJsonValueStyle);
            styleManager.when(() -> StyleManager.getNullJsonValueStyle(doc))
                .thenReturn(nullJsonValueStyle);

            JsonStyleManager jsonStyleManager = new JsonStyleManager(doc);
            jsonStyleManager.format(jsonString);

            verifyDefaultStyle(doc, defaultStyle, jsonString);
            verifyJsonStyle(doc, jsonKeyStyle, jsonString, getJsonKeysOfCountryJsonString());
            verifyJsonStyle(doc, stringJsonValueStyle, jsonString, getStringJsonValuesOfCountryJsonString());
            verifyJsonStyle(doc, numberJsonValueStyle, jsonString, getNumberJsonValuesOfCountryJsonString());
            verifyJsonStyle(doc, booleanJsonValueStyle, jsonString, getBooleanJsonValuesOfCountryJsonString());
            verifyJsonStyle(doc, nullJsonValueStyle, jsonString, getNullJsonValuesOfCountryJsonString());
        }
    }

    private void verifyDefaultStyle(StyledDocument doc, Style style, String jsonString) throws BadLocationException {
        ArgumentCaptor<String> jsonStringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(doc).insertString(
            eq(0), jsonStringCaptor.capture(), eq(style)
        );
        Assert.assertEquals(jsonString, jsonStringCaptor.getValue());
    }

    private void verifyJsonStyle(StyledDocument doc, Style style, String jsonString, List<String> expectedJsonParts) {
        ArgumentCaptor<Integer> indexCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> lengthCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.verify(doc, Mockito.times(expectedJsonParts.size())).setCharacterAttributes(
            indexCaptor.capture(), lengthCaptor.capture(), eq(style), eq(true)
        );

        List<Integer> capturedIndexes = indexCaptor.getAllValues();
        List<Integer> capturedLengths = lengthCaptor.getAllValues();

        for (int i = 0; i < expectedJsonParts.size(); i++) {
            String expectedJsonPart = expectedJsonParts.get(i);

            int index = capturedIndexes.get(i);
            int length = capturedLengths.get(i);

            String actualJsonPart = jsonString.substring(index, index + length);
            Assert.assertEquals(expectedJsonPart, actualJsonPart);
        }
    }
}
