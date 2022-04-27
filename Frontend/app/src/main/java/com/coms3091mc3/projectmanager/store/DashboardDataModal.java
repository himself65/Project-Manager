package com.coms3091mc3.projectmanager.store;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.ProjectAdapter;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.utils.Const;

/**
 * The type Dashboard data modal.
 */
public class DashboardDataModal {
    /**
     * The Name.
     */
    public ObservableField<String> name;
    /**
     * The Projects adapter.
     */
    public ProjectAdapter projectsAdapter;

    private Context context;

    /**
     * Instantiates a new Dashboard data modal.
     *
     * @param context the context
     */
    public DashboardDataModal(Context context) {
        this.context = context;
        this.name = new ObservableField<>(Const.user.getFullname());
        this.projectsAdapter = new ProjectAdapter(context, R.layout.fragment_item);
    }
}
