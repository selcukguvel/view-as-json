package bracket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ui.bracket.BracketLine;

import java.awt.*;

public class BracketTestData {

    public static BracketLine getBracketLine(int lineX, int lineY) {
        Rectangle line = getLine(lineX, lineY);
        return new BracketLine(line);
    }

    public static Rectangle getLine(int lineX, int lineY) {
        return new Rectangle(lineX, lineY, 200, 20);
    }

    public static String getJsonString() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Turkey");
        jsonObject.addProperty("capital_city", "Ankara");

        JsonArray jsonArray = new JsonArray();
        jsonArray.add("Tarsus");
        jsonArray.add("Adana");

        jsonObject.add("example_cities", jsonArray);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        return gson.toJson(jsonObject);
    }
}
