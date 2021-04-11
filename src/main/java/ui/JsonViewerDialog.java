package ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;
import ui.textpane.JsonTextPane;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class JsonViewerDialog extends DialogWrapper {
    private final JsonTextPane jsonTextPane;
    private final JScrollPane scrollPane;

    public JsonViewerDialog(String jsonString) {
        super(true);

        this.scrollPane = new JBScrollPane();
        this.jsonTextPane = new JsonTextPane(scrollPane, jsonString);
        init();
        setTitle("View as Json");
    }

    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel();
        dialogPanel.setPreferredSize(new Dimension(650, 480));

        scrollPane.getViewport().add(jsonTextPane);
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
            String text = jsonTextPane.getText();
            clipboard.setContents(new StringSelection(text), null);
        }
    }
}