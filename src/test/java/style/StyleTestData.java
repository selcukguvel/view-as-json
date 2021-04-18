package style;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StyleTestData {
    public static String getCountryJsonString() {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        return gson.toJson(getCountryJsonObject());
    }

    private static JsonObject getCountryJsonObject() {
        JsonObject countryJsonObject = new JsonObject();
        countryJsonObject.addProperty("id", "country-id");
        countryJsonObject.addProperty("name", "Turkey");
        countryJsonObject.add("capital", getCapitalJsonObject());
        countryJsonObject.add("example_cities", getExampleCitiesJsonArray());

        return countryJsonObject;
    }

    private static JsonObject getCapitalJsonObject() {
        JsonObject capitalJsonObject = new JsonObject();
        capitalJsonObject.add("id", null);
        capitalJsonObject.addProperty("name", "Ankara");
        capitalJsonObject.addProperty("number_of_districts", 25);
        capitalJsonObject.add("coordinates", getCapitalCoordinatesJsonObject());
        capitalJsonObject.addProperty("is_in_central_anatolia", true);

        return capitalJsonObject;
    }

    private static JsonObject getCapitalCoordinatesJsonObject() {
        JsonObject capitalCoordinateJsonObject = new JsonObject();
        capitalCoordinateJsonObject.addProperty("latitude", 39.925533);
        capitalCoordinateJsonObject.addProperty("longitude", 32.866287);

        return capitalCoordinateJsonObject;
    }

    private static JsonArray getExampleCitiesJsonArray() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("Tarsus");
        jsonArray.add("Adana");

        return jsonArray;
    }

    public static List<String> getJsonKeysOfCountryJsonString() {
        return Arrays.asList(
            "\"id\":",
            "\"name\":",
            "\"capital\":",
            "\"id\":",
            "\"name\":",
            "\"number_of_districts\":",
            "\"coordinates\":",
            "\"latitude\":",
            "\"longitude\":",
            "\"is_in_central_anatolia\":",
            "\"example_cities\":"
        );
    }

    public static List<String> getStringJsonValuesOfCountryJsonString() {
        return Arrays.asList(
            "\"country-id\"",
            "\"Turkey\"",
            "\"Ankara\"",
            "\"Tarsus\"",
            "\"Adana\""
        );
    }

    public static List<String> getNumberJsonValuesOfCountryJsonString() {
        return Arrays.asList(
            "25",
            "39.925533",
            "32.866287"
        );
    }

    public static List<String> getBooleanJsonValuesOfCountryJsonString() {
        return Collections.singletonList("true");
    }

    public static List<String> getNullJsonValuesOfCountryJsonString() {
        return Collections.singletonList("null");
    }
}
