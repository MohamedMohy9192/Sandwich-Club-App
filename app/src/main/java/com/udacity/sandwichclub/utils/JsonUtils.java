package com.udacity.sandwichclub.utils;

import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject sandwichItem = new JSONObject(json);
            JSONObject sandwichName = sandwichItem.getJSONObject("name");

            String sandwichMainName = sandwichName.getString("mainName");

            JSONArray knownAsArray = sandwichName.getJSONArray("alsoKnownAs");

            List<String> listOfKnownAsNames = new ArrayList<>();

            for (int i = 0; i < knownAsArray.length(); i++) {
                String knownAsName = knownAsArray.optString(i);
                listOfKnownAsNames.add(knownAsName);
            }

            String placeOfOrigin = sandwichItem.optString("placeOfOrigin");
            String description = sandwichItem.getString("description");
            String imageUrl = sandwichItem.getString("image");

            JSONArray sandwichIngredients = sandwichItem.getJSONArray("ingredients");

            List<String> listOfIngredients = new ArrayList<>();

            for (int i = 0; i < sandwichIngredients.length(); i++) {
                String ingredient = sandwichIngredients.getString(i);
                listOfIngredients.add(ingredient);
            }

            return new Sandwich(
                    sandwichMainName,
                    listOfKnownAsNames,
                    placeOfOrigin,
                    description,
                    imageUrl,
                    listOfIngredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
