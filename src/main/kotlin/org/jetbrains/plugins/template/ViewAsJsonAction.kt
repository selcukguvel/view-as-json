package org.jetbrains.plugins.template

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSyntaxException
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.util.TextRange
import ui.ErrorDialog
import ui.JsonViewerDialog

class ViewAsJsonAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val presentation = e.presentation
        presentation.isEnabledAndVisible = editor?.selectionModel?.hasSelection(true) == true
    }

    override fun actionPerformed(e: AnActionEvent) {
        val selectedText = getSelectedText(e)

        val jsonString = getJsonString(selectedText)
        if (jsonString == null) {
            val errorDialog = ErrorDialog("Make sure you selected a valid Json string")
            errorDialog.setResizable(false)
            errorDialog.show()
        } else {
            val jsonDialog = JsonViewerDialog(jsonString)
            jsonDialog.setResizable(false)
            jsonDialog.show()
        }
    }

    private fun getSelectedText(e: AnActionEvent): String {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val document = editor.document

        val primaryCaret: Caret = editor.caretModel.primaryCaret
        val start: Int = primaryCaret.selectionStart
        val end: Int = primaryCaret.selectionEnd

        return document.getText(TextRange.create(start, end))
    }

    private fun getJsonString(content: String?): String? {
        val gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()

        return try {
            when (val jsonElement = JsonParser.parseString(content)) {
                is JsonObject -> gson.toJson(jsonElement.asJsonObject)
                is JsonArray -> gson.toJson(jsonElement.asJsonArray)
                is JsonPrimitive -> gson.toJson(jsonElement.asJsonPrimitive)
                is JsonNull -> gson.toJson(jsonElement.asJsonNull)
                else -> throw JsonSyntaxException("Unrecognized json element type")
            }
        } catch (e: JsonSyntaxException) {
            null
        }
    }
}
