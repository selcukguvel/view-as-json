package textpane;

import java.awt.*;

public class TextPaneTestData {
    public static Rectangle getLine(int x, int y) {
        return new Rectangle(x, y, 200, 20);
    }

    public static Rectangle getVisibleWindow(int y) {
        return new Rectangle(10, y, 200, 500);
    }
}
