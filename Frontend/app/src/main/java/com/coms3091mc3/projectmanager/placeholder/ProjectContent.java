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
public class ProjectContent {

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
        return "2021-02-16";
    }
}