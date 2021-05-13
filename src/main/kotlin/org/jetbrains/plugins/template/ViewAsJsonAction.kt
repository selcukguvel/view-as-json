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
import com.intellij.openapi.fileEditor.impl.LoadTextUtil
import com.intellij.openapi.util.TextRange
import dialog.ErrorDialog
import dialog.JsonViewerDialog
import java.lang.RuntimeException

class ViewAsJsonAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val presentation = e.presentation
        presentation.isEnabledAndVisible = isTextSelectionMode(e) || isJsonFileSelectionMode(e)
    }

    private fun isTextSelectionMode(e: AnActionEvent): Boolean {
        val editor = e.getData(CommonDataKeys.EDITOR)
        return editor?.selectionModel?.hasSelection(true) == true
    }

    private fun isJsonFileSelectionMode(e: AnActionEvent): Boolean {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        return virtualFile?.extension == "json";
    }

    override fun actionPerformed(e: AnActionEvent) {
        val errorMessage: String
        val content = when {
            isTextSelectionMode(e) -> {
                errorMessage = "Make sure you selected a valid Json string"
                getSelectedText(e)
            }
            isJsonFileSelectionMode(e) -> {
                errorMessage = "Make sure Json file you selected contains a valid Json string"
                getTextFromJsonFile(e)
            }
            else -> throw RuntimeException("Invalid mode")
        }

        val jsonString = getJsonString(content)
        if (jsonString == null) {
            val errorDialog = ErrorDialog(errorMessage)
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

    private fun getTextFromJsonFile(e: AnActionEvent): String {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        return LoadTextUtil.loadText(virtualFile!!).toString()
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
