package com.coms3091mc3.projectmanager.placeholder;

import com.coms3091mc3.projectmanager.data.Project;

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
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<Project> ITEMS = new ArrayList<Project>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<Integer, Project> ITEM_MAP = new HashMap<Integer, Project>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(Project item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static Project createPlaceholderItem(int position) {
        return new Project(position, "Item " + position, makeDate(position));
    }

    private static String makeDate(int position) {
        return "2022-02-12";
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        /**
         * The Id.
         */
        public final String id;
        /**
         * The Content.
         */
        public final String content;
        /**
         * The Details.
         */
        public final String details;

        /**
         * Instantiates a new Placeholder item.
         *
         * @param id      the id
         * @param content the content
         * @param details the details
         */
        public PlaceholderItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}