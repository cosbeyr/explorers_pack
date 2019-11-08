package com.WWU.explorerspack.ui.guide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONManager {
    JSONObject guide;
    boolean isCreated = false;//might not be necessary.

    public JSONManager(String json) throws JSONException {
        if(json != null) {
            guide = new JSONObject(json);
        }
    }


    public JSONArray getL1Tiles() throws JSONException{
        if(guide != null)
            return guide.names();
        else
            return null;
    }

    public JSONObject getChapterObject(String chapter) throws JSONException{
        return guide.getJSONObject(chapter);
    }
}
