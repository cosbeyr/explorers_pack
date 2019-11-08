package com.WWU.explorerspack.ui.guide.ChapterData;

import com.WWU.explorerspack.ui.guide.JSONManager;

import org.json.JSONException;
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
public class ChapterContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ChapterItem> ITEMS = new ArrayList<ChapterItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ChapterItem> ITEM_MAP = new HashMap<String, ChapterItem>();

    //private static final int COUNT = 29;

    //static {
        // Add some sample items.
       // for (int i = 1; i <= COUNT; i++) {
            //addItem(createChapterItem(i));
       // }
    //}

    public static void addItem(ChapterItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

//    private static ChapterItem createChapterItem(int position) {
//        return new ChapterItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ChapterItem {
        public final String id;
        public final String content;
        public final String details;
        public final JSONObject myObj;

//        public ChapterItem(String id, String content, String details) {
//            this.id = id;
//            this.content = content;
//            this.details = details;
//        }

        public ChapterItem(String id, String content, JSONManager manager) throws JSONException {
            this.id = id;
            this.content =content;
            this.details ="";
            this.myObj = manager.getChapterObject(content);
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
