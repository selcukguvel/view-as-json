package ui.style;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class StyleManager {
    public static Style getDefaultStyle(StyledDocument doc) {
        Style style = doc.addStyle("DefaultStyle", null);
        StyleConstants.setFontSize(style, 16);
        StyleConstants.setFontFamily(style, "Helvetica");
        StyleConstants.setForeground(style, Colors.defaultText);

        return style;
    }

    public static Style getJsonKeyStyle(StyledDocument doc) {
        Style style = doc.addStyle("JsonKeyStyle", null);
        StyleConstants.setFontSize(style, 16);
        StyleConstants.setFontFamily(style, "Helvetica");
        StyleConstants.setForeground(style, Colors.jsonKey);

        return style;
    }

    public static Style getStringJsonValueStyle(StyledDocument doc) {
        Style style = doc.addStyle("StringJsonValueStyle", null);
        StyleConstants.setFontSize(style, 16);
        StyleConstants.setFontFamily(style, "Helvetica");
        StyleConstants.setForeground(style, Colors.stringJsonValue);

        return style;
    }

    public static Style getIntegerJsonValueStyle(StyledDocument doc) {
        Style style = doc.addStyle("IntegerJsonValueStyle", null);
        StyleConstants.setFontSize(style, 16);
        StyleConstants.setFontFamily(style, "Helvetica");
        StyleConstants.setForeground(style, Colors.integerJsonValue);

        return style;
    }

    public static Style getBooleanJsonValueStyle(StyledDocument doc) {
        Style style = doc.addStyle("BooleanJsonValueStyle", null);
        StyleConstants.setFontSize(style, 16);
        StyleConstants.setFontFamily(style, "Helvetica");
        StyleConstants.setForeground(style, Colors.booleanJsonValue);

        return style;
    }

    public static Style getNullJsonValueStyle(StyledDocument doc) {
        Style style = doc.addStyle("NullJsonValueStyle", null);
        StyleConstants.setFontSize(style, 16);
        StyleConstants.setFontFamily(style, "Helvetica");
        StyleConstants.setForeground(style, Colors.nullJsonValue);

        return style;
    }
}
