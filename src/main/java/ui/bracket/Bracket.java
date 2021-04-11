package ui.bracket;

public class Bracket {
    private final String text;
    private final int offset;

    public Bracket(String text, int offset) {
        this.text = text;
        this.offset = offset;
    }

    public String getText() {
        return text;
    }

    public int getOffset() {
        return offset;
    }
}
