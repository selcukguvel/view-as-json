package ui.style;

import ui.match.MatchInterval;
import ui.match.MatchIntervalList;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ui.style.StyleManager.*;

public class JsonStyleManager {
    private final StyledDocument doc;

    public JsonStyleManager(JTextComponent textComponent) {
        this.doc = (StyledDocument) textComponent.getDocument();
    }

    public void format(String jsonString) throws BadLocationException {
        doc.insertString(0, jsonString, getDefaultStyle(doc));

        Pattern keyMatcherRegex = Pattern.compile("\".*\":");
        Matcher keyMatcher = keyMatcherRegex.matcher(jsonString);

        int start = 0;
        while (keyMatcher.find()) {
            int matchedKeyStartIndex = keyMatcher.start();
            int matchedKeyEndIndex = keyMatcher.end();

            addStyledJsonValueToDocument(jsonString, start, matchedKeyStartIndex);
            doc.setCharacterAttributes(matchedKeyStartIndex, matchedKeyEndIndex - matchedKeyStartIndex, getJsonKeyStyle(doc), true);

            start = matchedKeyEndIndex;
        }
        addStyledJsonValueToDocument(jsonString, start, jsonString.length());
    }

    private void addStyledJsonValueToDocument(String jsonString, int start, int matchedKeyStartIndex) {
        String matchedJsonValue = jsonString.substring(start, matchedKeyStartIndex);

        MatchIntervalList stringMatchIntervals = matchWithStringJsonValue(matchedJsonValue, start);
        matchWithNumberJsonValue(stringMatchIntervals, matchedJsonValue, start);
        matchWithBooleanJsonValue(stringMatchIntervals, matchedJsonValue, start);
        matchWithNullJsonValue(stringMatchIntervals, matchedJsonValue, start);
    }

    private MatchIntervalList matchWithStringJsonValue(String matchedJsonValue, int start) {
        MatchIntervalList matchIntervals = new MatchIntervalList();

        Pattern stringMatcherRegex = Pattern.compile("\".*\"");
        Matcher stringMatcher = stringMatcherRegex.matcher(matchedJsonValue);

        while (stringMatcher.find()) {
            int matchedStringStartIndex = stringMatcher.start() + start;
            int matchedStringEndIndex = stringMatcher.end() + start;

            doc.setCharacterAttributes(matchedStringStartIndex, matchedStringEndIndex - matchedStringStartIndex, getStringJsonValueStyle(doc), true);

            matchIntervals.add(new MatchInterval(matchedStringStartIndex, matchedStringEndIndex));
        }

        return matchIntervals;
    }

    private void matchWithBooleanJsonValue(MatchIntervalList stringMatchIntervals, String matchedJsonValue, int start) {
        String booleanJsonValueRegex = "true|false";
        matchWithNonStringJsonValue(stringMatchIntervals, matchedJsonValue, booleanJsonValueRegex, start, getBooleanJsonValueStyle(doc));
    }

    private void matchWithNullJsonValue(MatchIntervalList stringMatchIntervals, String matchedJsonValue, int start) {
        String nullJsonValueRegex = "null";
        matchWithNonStringJsonValue(stringMatchIntervals, matchedJsonValue, nullJsonValueRegex, start, getNullJsonValueStyle(doc));
    }

    private void matchWithNumberJsonValue(MatchIntervalList stringMatchIntervals, String matchedJsonValue, int start) {
        String numberJsonValueRegex = "\\b\\d*\\.?\\d+\\b";
        matchWithNonStringJsonValue(stringMatchIntervals, matchedJsonValue, numberJsonValueRegex, start, getNumberJsonValueStyle(doc));
    }

    private void matchWithNonStringJsonValue(MatchIntervalList stringMatchIntervals, String matchedJsonValue, String JsonValueRegex, int start, Style style) {
        Pattern matcherRegex = Pattern.compile(JsonValueRegex);
        Matcher matcher = matcherRegex.matcher(matchedJsonValue);

        while (matcher.find()) {
            int matchStartIndex = matcher.start() + start;
            int matchEndIndex = matcher.end() + start;

            MatchInterval matchInterval = new MatchInterval(matchStartIndex, matchEndIndex);
            if (stringMatchIntervals.isIntervalAlreadyMatched(matchInterval)) {
                continue;
            }

            doc.setCharacterAttributes(matchStartIndex, matchEndIndex - matchStartIndex, style, true);
        }
    }
}
