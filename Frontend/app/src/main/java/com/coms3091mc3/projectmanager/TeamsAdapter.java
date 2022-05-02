package com.coms3091mc3.projectmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coms3091mc3.projectmanager.data.Task;
import com.coms3091mc3.projectmanager.data.Team;

public class TeamsAdapter extends ArrayAdapter<Team> {
    public TeamsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Team team = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_task_item, parent, false);
        }
        // Lookup view for data population
        TextView teamID = (TextView) convertView.findViewById(R.id.taskID);
        TextView teamName = (TextView) convertView.findViewById(R.id.taskName);
        // Populate the data into the template view using the data object
        teamID.setText(String.valueOf(team.getTeamID()));
        teamName.setText(team.getTeamName());
        // Return the completed view to render on screen
        return convertView;
    }
}