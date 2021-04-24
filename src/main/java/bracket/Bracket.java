package bracket;

import java.util.Objects;

public class Bracket {
    private final String text;
    private final int index;

    public Bracket(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object bracket) {
        if (this == bracket) return true;
        if (bracket == null || getClass() != bracket.getClass()) return false;
        Bracket that = (Bracket) bracket;
        return this.text.equals(that.text) && this.index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.text, this.index);
    }
}
