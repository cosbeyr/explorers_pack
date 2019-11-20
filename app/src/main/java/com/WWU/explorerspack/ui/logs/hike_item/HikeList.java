package com.WWU.explorerspack.ui.logs.hike_item;

import com.WWU.explorerspack.utilities.StorageUtilities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HikeList {

    public static List<HikeItem> ITEMS = new ArrayList<HikeItem>();

    public static Map<String, HikeItem> ITEM_MAP = new HashMap<String, HikeItem>();

    /**
     * A hike item representing a hike.
     */
    public static class HikeItem {
        public final String id;

        public HikeItem(String id) {
            this.id = id;
        }
        public static void addItem(String id) {
            HikeItem item = new HikeItem(id);
            ITEMS.add(item);
            ITEM_MAP.put(item.id, item);
        }
    }

    /*
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();


    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        //Add some sample items.
        for (int i = 1; i <= 2; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    } */
}
