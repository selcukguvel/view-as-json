package bracket;

import ui.bracket.BracketLine;

import java.awt.*;

public class BracketTestData {

    public static BracketLine getBracketLine(int lineX, int lineY) {
        Rectangle line = getLine(lineX, lineY);
        return new BracketLine(line);
    }

    public static Rectangle getLine(int lineX, int lineY) {
        return new Rectangle(lineX, lineY, 200, 20);
    }
}
