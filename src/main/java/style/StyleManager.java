package style;

import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import static javax.swing.text.StyleConstants.*;

public class StyleManager {
    public static Style getDefaultStyle(StyledDocument doc) {
        Style style = doc.addStyle("DefaultStyle", null);
        style.addAttribute(FontSize, 16);
        style.addAttribute(FontFamily, "Helvetica");
        style.addAttribute(Foreground, Colors.defaultText);

        return style;
    }

    public static Style getJsonKeyStyle(StyledDocument doc) {
        Style style = doc.addStyle("JsonKeyStyle", null);
        style.addAttribute(FontSize, 16);
        style.addAttribute(FontFamily, "Helvetica");
        style.addAttribute(Foreground, Colors.jsonKey);

        return style;
    }

    public static Style getStringJsonValueStyle(StyledDocument doc) {
        Style style = doc.addStyle("StringJsonValueStyle", null);
        style.addAttribute(FontSize, 16);
        style.addAttribute(FontFamily, "Helvetica");
        style.addAttribute(Foreground, Colors.stringJsonValue);

        return style;
    }

    public static Style getNumberJsonValueStyle(StyledDocument doc) {
        Style style = doc.addStyle("NumberJsonValueStyle", null);
        style.addAttribute(FontSize, 16);
        style.addAttribute(FontFamily, "Helvetica");
        style.addAttribute(Foreground, Colors.numberJsonValue);

        return style;
    }

    public static Style getBooleanJsonValueStyle(StyledDocument doc) {
        Style style = doc.addStyle("BooleanJsonValueStyle", null);
        style.addAttribute(FontSize, 16);
        style.addAttribute(FontFamily, "Helvetica");
        style.addAttribute(Foreground, Colors.booleanJsonValue);

        return style;
    }

    public static Style getNullJsonValueStyle(StyledDocument doc) {
        Style style = doc.addStyle("NullJsonValueStyle", null);
        style.addAttribute(FontSize, 16);
        style.addAttribute(FontFamily, "Helvetica");
        style.addAttribute(Foreground, Colors.nullJsonValue);

        return style;
    }
}
