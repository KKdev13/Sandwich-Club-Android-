package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)
    {

        String fullName = null;
        String origin = null;
        String desc = null;
        String image = null;
        List<String> ing = null;
        List<String> aka = null;

        try {
            JSONObject main = new JSONObject(json);
            JSONObject name = main.getJSONObject("name");
            fullName = name.getString("mainName");

            JSONArray AKAJsonArray = name.getJSONArray("alsoKnownAs");
            aka = new ArrayList<>();

            if (AKAJsonArray.length() != 0){
                for(int i = 0; i < AKAJsonArray.length(); i++){
                    aka.add(AKAJsonArray.getString(i));
                }
            }

            origin = main.getString("placeOfOrigin");
            desc = main.getString("description");
            image = main.getString("image");

            JSONArray ingJA = main.getJSONArray("ingredients");
            ing = new ArrayList<>();

            if(ingJA.length() != 0){
                for (int j = 0; j < ingJA.length(); j++){
                    ing.add(ingJA.getString(j));
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }

        Sandwich sandwich = new Sandwich(fullName, aka, origin, desc, image, ing);
        return sandwich;

    }
}
