package com.coms3091mc3.projectmanager.store;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.R;

public class DashboardDataModal {
    public ObservableField<String> name;
    public ArrayAdapter<String> projectsAdapter;

    public DashboardDataModal(Context context) {
        this.name = new ObservableField<>("UNKNOWN");
        this.projectsAdapter = new ArrayAdapter<>(context, R.layout.fragment_item, R.id.content);
    }
}
