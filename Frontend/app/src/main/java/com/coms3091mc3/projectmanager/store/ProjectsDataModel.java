package com.coms3091mc3.projectmanager.store;

import android.content.Context;

import com.coms3091mc3.projectmanager.ProjectAdapter;
import com.coms3091mc3.projectmanager.R;

public class ProjectsDataModel {
    public ProjectAdapter projectsAdapter;

    public ProjectsDataModel(Context context) {
        this.projectsAdapter = new ProjectAdapter(context, R.layout.fragment_item);
    }
}
