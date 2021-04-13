package bracket;

import ui.bracket.BracketLine;

import java.awt.*;

public class BracketTestData {

    public static BracketLine getBracketLine(int lineX, int lineY) {
        Rectangle line = new Rectangle(lineX, lineY, 200, 20);
        return new BracketLine(line);
    }
}
