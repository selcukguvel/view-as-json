package ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import ui.bracket.BracketMatcher;
import ui.style.Colors;
import ui.style.JsonStyleManager;
import ui.textpane.JsonTextPane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class JsonViewerDialog extends DialogWrapper {
    private final JsonTextPane jsonTextPane;
    private final JScrollPane scrollPane;

    public JsonViewerDialog(String jsonString) throws BadLocationException {
        super(true);

        this.scrollPane = new JBScrollPane();
        this.jsonTextPane = getJsonTextPane(jsonString);

        init();
        setTitle("View as JSON");
    }

    private JsonTextPane getJsonTextPane(String jsonString) throws BadLocationException {
        JTextPane textComponent = new JTextPane();
        JsonTextPane jsonTextPane = new JsonTextPane(
            new BracketMatcher(), scrollPane, textComponent, new JsonStyleManager(textComponent)
        );
        jsonTextPane.enableHighlighter();
        setLayoutOfJsonTextPane(jsonTextPane);
        jsonTextPane.addContent(jsonString);

        return jsonTextPane;
    }

    private void setLayoutOfJsonTextPane(JsonTextPane jsonTextPane) {
        JTextComponent textComponent = jsonTextPane.getTextComponent();
        textComponent.setSize(new Dimension(960, 720));
        textComponent.setBackground(Colors.jsonTextPaneBackground);
        textComponent.setMargin(JBUI.insets(20));
        textComponent.setCaretColor(textComponent.getBackground());
        textComponent.setEditable(false);
    }

    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel();
        dialogPanel.setPreferredSize(new Dimension(960, 720));

        scrollPane.getViewport().add(jsonTextPane.getTextComponent());
        scrollPane.setPreferredSize(dialogPanel.getPreferredSize());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        dialogPanel.add(scrollPane);

        return dialogPanel;
    }

    @Override
    protected @NotNull Action[] createActions() {
        return new Action[]{getCopyAction()};
    }

    private Action getCopyAction() {
        return new CopyAction();
    }

    private class CopyAction extends DialogWrapperAction {
        protected CopyAction() {
            super("Copy");
        }

        @Override
        protected void doAction(ActionEvent e) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String text = jsonTextPane.getTextComponent().getText();
            clipboard.setContents(new StringSelection(text), null);
        }
    }
}