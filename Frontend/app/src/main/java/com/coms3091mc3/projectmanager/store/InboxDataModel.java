package com.coms3091mc3.projectmanager.store;

import android.content.Context;

import com.coms3091mc3.projectmanager.ProjectAdapter;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.TeamsAdapter;

public class InboxDataModel {
    public TeamsAdapter teamsAdapter;

    public InboxDataModel(Context context) {
        this.teamsAdapter = new TeamsAdapter(context, R.layout.fragment_item);
    }
}
