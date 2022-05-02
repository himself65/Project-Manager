package com.coms3091mc3.projectmanager.store;

import android.content.Context;

import com.coms3091mc3.projectmanager.ProjectAdapter;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.TeamsAdapter;

public class ChatDataModel {
    public TeamsAdapter teamsAdapter;

    public ChatDataModel(Context context) {
        this.teamsAdapter = new TeamsAdapter(context, R.layout.fragment_item);
    }
}
