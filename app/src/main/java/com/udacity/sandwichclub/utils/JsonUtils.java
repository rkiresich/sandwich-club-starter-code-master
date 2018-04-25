package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichDetails = new JSONObject(json);

            JSONObject name = sandwichDetails.getJSONObject("name");
            String mainName = name.getString("mainName");

            ArrayList<String> alsoKnownAs = new ArrayList<String>();
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            for(int i = 0; i<alsoKnownAsArray.length(); i++){
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }

            String placeOfOrigin = sandwichDetails.getString("placeOfOrigin");

            String description = sandwichDetails.getString("description");

            String image = sandwichDetails.getString("image");

            ArrayList<String> ingredients = new ArrayList<String>();
            JSONArray ingredientsArray = sandwichDetails.getJSONArray("ingredients");
            for(int i = 0; i<ingredientsArray.length(); i++){
                ingredients.add(ingredientsArray.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description,
                    image, ingredients);

        } catch (JSONException e) {
            System.out.println("No Sandwich Details Found.");
            e.printStackTrace();
            return null;
        }
    }
}
