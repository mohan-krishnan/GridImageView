package com.gridimage.codepath.net;

import android.util.Log;

import com.gridimage.codepath.models.SearchOpts;
import com.gridimage.codepath.util.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by mkrish4 on 10/16/15.
 */
public class GoogleClient {
    private static final String API_BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

    private static final String SITE_PARAM="as_sitesearch";
    private static final String COLOR_PARAM="imgcolor";
    private static final String SIZE_PARAM="imgsz";
    private static final String TYPE_PARAM="imgtype";
    private static final String START_PARAM="start";


    private AsyncHttpClient client;

    public GoogleClient() {
        this.client = new AsyncHttpClient();
    }

    // Method for accessing the search API
    public void getImages(final String query, SearchOpts searchOpts, JsonHttpResponseHandler handler) {
        try {
            StringBuilder sb = new StringBuilder(API_BASE_URL).append(URLEncoder.encode(query, "utf-8")).append("&rsz=8");
            fillSearchOptParams(searchOpts, sb);
            Log.d("DEBUG", "Invoking with url " + sb.toString());
            int startRow = searchOpts != null ? searchOpts.startRow : 0;
            client.get(sb.toString() + getStartParam(startRow), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void fillSearchOptParams(SearchOpts searchOpts, StringBuilder sb) {
        if (searchOpts != null) {
            if (searchOpts.color != null) {
                sb.append("&").append(COLOR_PARAM).append("=").append(Utils.colorKey(searchOpts.color));
            }
            if (searchOpts.imageSize != null) {
                sb.append("&").append(SIZE_PARAM).append("=").append(Utils.sizeKey(searchOpts.imageSize));
            }
            if (searchOpts.type != null) {
                sb.append("&").append(TYPE_PARAM).append("=").append(Utils.typeKey(searchOpts.type));
            }
            if (searchOpts.site != null) {
                sb.append("&").append(SITE_PARAM).append("=").append(searchOpts.site);
            }
            if (searchOpts.startRow > 0) {
                sb.append("&").append(START_PARAM).append("=").append(searchOpts.startRow);
            }
        }
    }

    private String getStartParam(int startRow) {
        if (startRow > 0)
            return "&" + START_PARAM + "=" + startRow;
        else return "";
    }
}
