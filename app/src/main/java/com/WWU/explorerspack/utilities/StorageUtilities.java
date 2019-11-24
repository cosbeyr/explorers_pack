package com.WWU.explorerspack.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StorageUtilities {
    public static final String jsonStorageName = "hikeLogs.json";
    public static boolean create(Context context, String fileName, String jsonString){
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
    }

    public static boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    public static JSONObject template(boolean includeExampleHike){
        JSONObject hikeLogs = new JSONObject();
        JSONObject hike = new JSONObject();
        JSONObject photo = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            photo.put("JPEG_20191114_152929_8217285707265593563.jpg", "/storage/emulated/0/JPEG_20191114_152929_8217285707265593563.jpg");
            jsonArray.put(photo);
            hike.put("notes", "example notes...");
            hike.put("map","map path on disk for processing");
            hike.put("photos", jsonArray);
            if (includeExampleHike){
                hikeLogs.put("example hike", hike);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hikeLogs;
    }

    public static String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            fis.close();
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("guide.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
