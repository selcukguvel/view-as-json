package dialog;

import com.intellij.openapi.actionSystem.AnActionEvent;

@FunctionalInterface
public interface ReplaceSelectedActionFunction {
    void apply(AnActionEvent actionEvent, String formattedText);
}
