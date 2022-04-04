package com.coms3091mc3.projectmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coms3091mc3.projectmanager.data.Project;

public class ProjectAdapter extends ArrayAdapter<Project> {
    public ProjectAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Project project = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item, parent, false);
        }
        // Lookup view for data population
        TextView projectID = (TextView) convertView.findViewById(R.id.projectID);
        TextView projectName = (TextView) convertView.findViewById(R.id.projectName);
        TextView projectCreatedDate = (TextView) convertView.findViewById(R.id.projectCreatedDate);
        // Populate the data into the template view using the data object
        projectID.setText(String.valueOf(project.getId()));
        projectName.setText(project.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}