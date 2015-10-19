package com.gridimage.codepath.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkrish4 on 10/16/15.
 */
public class ImageResult implements Serializable{
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public static ImageResult fromJson(JSONObject jsonObject) throws JSONException {
        ImageResult imageResult =  new ImageResult();
        imageResult.fullUrl = jsonObject.getString("url");
        imageResult.thumbUrl = jsonObject.getString("tbUrl");
        imageResult.title = jsonObject.getString("title");
        return imageResult;
    }

    public static List<ImageResult> fromJson(JSONArray jsonArray) throws JSONException {
        List<ImageResult> imageResults = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            imageResults.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return imageResults;
    }
}
