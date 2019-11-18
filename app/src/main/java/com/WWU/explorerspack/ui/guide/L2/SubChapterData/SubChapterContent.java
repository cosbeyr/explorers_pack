package com.WWU.explorerspack.ui.guide.L2.SubChapterData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubChapterContent {

    /**
     * An array of sample (dummy) items.
     */
    public  List<SubChapterItem> ITEMS = new ArrayList<SubChapterItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
//    public  Map<String, SubChapterItem> ITEM_MAP = new HashMap<String, SubChapterItem>();

    //private static final int COUNT = 29;

    //static {
    // Add some sample items.
    // for (int i = 1; i <= COUNT; i++) {
    //addItem(createChapterItem(i));
    // }
    //}

    public void addItem(SubChapterItem item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

    public SubChapterContent(){}



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
    public static class SubChapterItem {
        public final String id;
        public final String chapter;
        public final String subChapter;
        //public final JSONObject myObj;

//        public ChapterItem(String id, String content, String details) {
//            this.id = id;
//            this.content = content;
//            this.details = details;
//        }

        public SubChapterItem(String id, String chapter, String subChapter){
            this.id = id;
            this.chapter = chapter;
            this.subChapter = subChapter;
//            this.myObj = manager.getChapterObject(content);
        }

        @Override
        public String toString() {
            return id + " " + chapter + " " + subChapter;
        }
    }
}
