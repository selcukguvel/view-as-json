package dialog;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ErrorDialog extends DialogWrapper {
    private final JLabel errorLabel;

    public ErrorDialog(String errorMessage) {
        super(true);

        this.errorLabel = new JLabel(errorMessage);
        init();
        setTitle("Error");
    }

    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel();
        dialogPanel.add(errorLabel);

        return dialogPanel;
    }

    @Override
    protected @NotNull Action[] createActions() {
        return new Action[]{getOKAction()};
    }
}