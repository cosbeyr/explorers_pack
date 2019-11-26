package com.WWU.explorerspack.ui.logs.hiking_maps.MapContent;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample hikeName for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MapListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<MapListItem> ITEMS = new ArrayList<MapListItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, MapListItem> ITEM_MAP = new HashMap<String, MapListItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addMapListItem(createMapListItem(i));
        }
    }

    private static void addMapListItem(MapListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static MapListItem createMapListItem(int position) {
        return new MapListItem(String.valueOf(position), "Item " + position, makeDetails(position),"0.0");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore lat information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of hikeName.
     */
    public static class MapListItem {
        public final String id;//the index of the list
        public final String hikeName;//the hike name
        public final String lat;//latitude of hike
        public final String lon;//longitude of hike
        public  String imageURL;
        public  String stars;
        public  String length;
        public String location;
        public Bitmap imageMap;
        public boolean isdummy =false;


        public MapListItem(String id, String hikeName, String lat, String lon) {
            this.id = id;
            this.hikeName = hikeName;
            this.lat = lat;
            this.lon = lon;
            isdummy =true;
        }

        public MapListItem(String id, String hikeName, String lat, String lon, String imageURL, String stars, String length, String location, Bitmap imageMap) {
            this.id = id;
            this.hikeName = hikeName;
            this.lat = lat;
            this.lon = lon;
            this.imageURL = imageURL;
            this.stars = stars;
            this.length = length;
            this.location = location;
            this.imageMap = imageMap;
        }

        @Override
        public String toString() {
            return hikeName;
        }
    }
}
