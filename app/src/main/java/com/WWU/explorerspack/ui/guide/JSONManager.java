package com.WWU.explorerspack.ui.guide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONManager {
    private static JSONManager instance;
    private JSONObject guide;


    //Singleton accessor
    public static JSONManager getInstance (String json) {
        if(instance == null) {
            instance = new JSONManager(json);
        }
        return instance;
    }

    //Constructor
    private JSONManager(String json)  {
        if(json != null) {
            try {
                guide = new JSONObject(json);
            } catch (Exception e) {
                //Do Nothing
            }
        }
    }



    //Get an array consisting of the chapter names
    public JSONArray getL1Tiles() throws JSONException{
        JSONArray result = null;
        if(guide != null) {
            try {
                result = guide.names();
            } catch (Exception e) {
                //Do nothing
            }
        }

        return result;
    }

    //Get an array consisting of the subchapter names of a provided chapter
    public JSONArray getL2Tiles(String chapter) {
        JSONArray result = null;
        JSONObject chapterObject = getChapterObject(chapter);
        if(chapterObject != null) {
            try {
                result = chapterObject.names();
            } catch (Exception e) {
                //Do nothing
            }
        }

        return result;
    }



    //Returns the entire JSONObject of a chapter
    public JSONObject getChapterObject(String chapter) {
        JSONObject result = null;
        System.out.println("Getting Chapter Object" + chapter);
        try {
            result = guide.getJSONObject(chapter);
        } catch (Exception e) {
            //Do nothing
        }

        return result;
    }
}
