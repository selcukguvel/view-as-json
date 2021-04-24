package bracket;

import java.awt.*;
import java.util.Objects;

public class BracketLine {
    private final Rectangle line;

    public BracketLine(Rectangle line) {
        this.line = line;
    }

    public Rectangle getLine() {
        return line;
    }

    @Override
    public boolean equals(Object line) {
        if (this == line) return true;
        if (line == null || getClass() != line.getClass()) return false;
        BracketLine that = (BracketLine) line;
        return this.line.y == that.line.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line.y);
    }
}
