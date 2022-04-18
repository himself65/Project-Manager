package com.coms3091mc3.projectmanager.store;

import android.content.Context;

import com.coms3091mc3.projectmanager.ProjectAdapter;
import com.coms3091mc3.projectmanager.R;

/**
 * The type Projects data model.
 */
public class ProjectsDataModel {
    /**
     * The Projects adapter.
     */
    public ProjectAdapter projectsAdapter;

    /**
     * Instantiates a new Projects data model.
     *
     * @param context the context
     */
    public ProjectsDataModel(Context context) {
        this.projectsAdapter = new ProjectAdapter(context, R.layout.fragment_item);
    }
}
