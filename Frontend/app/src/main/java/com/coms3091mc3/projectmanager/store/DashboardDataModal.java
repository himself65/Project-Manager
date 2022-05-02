package com.coms3091mc3.projectmanager.store;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.ProjectAdapter;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.utils.Const;

public class DashboardDataModal {
    public ObservableField<String> name;
    public ProjectAdapter projectsAdapter;

    private Context context;

    public DashboardDataModal(Context context) {
        this.context = context;
        this.name = new ObservableField<>(Const.user.getFullname());
        this.projectsAdapter = new ProjectAdapter(context, R.layout.fragment_item);
    }
}
