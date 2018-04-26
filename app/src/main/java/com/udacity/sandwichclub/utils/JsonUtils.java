package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String MAIN_NAME = "mainName";
    private static final String NAME = "name";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichDetails = new JSONObject(json);

            JSONObject name = sandwichDetails.getJSONObject(NAME);
            String mainName = name.optString(MAIN_NAME);

            JSONArray alsoKnownAsArray = name.getJSONArray(ALSO_KNOWN_AS);
            ArrayList<String> alsoKnownAs = JSONArrayToArrayList(alsoKnownAsArray);

            String placeOfOrigin = sandwichDetails.optString(PLACE_OF_ORIGIN);

            String description = sandwichDetails.optString(DESCRIPTION);

            String image = sandwichDetails.optString(IMAGE);

            JSONArray ingredientsArray = sandwichDetails.getJSONArray(INGREDIENTS);
            ArrayList<String> ingredients = JSONArrayToArrayList(ingredientsArray);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description,
                    image, ingredients);

        } catch (JSONException e) {
            Log.v("JSON Error", "No Sandwich Details Found.");
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<String> JSONArrayToArrayList(JSONArray jsonArray){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i<jsonArray.length(); i++){
            list.add(jsonArray.optString(i));
        }
        return list;
    }
}
