package com.coms3091mc3.projectmanager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coms3091mc3.projectmanager.data.Task;

public class TasksAdapter extends ArrayAdapter<Task> {
    public TasksAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_task_item, parent, false);
        }
        // Lookup view for data population
        TextView taskID = (TextView) convertView.findViewById(R.id.taskID);
        TextView taskName = (TextView) convertView.findViewById(R.id.taskName);
        // Populate the data into the template view using the data object
        taskID.setText(String.valueOf(task.getTaskID()));
        taskName.setText(task.getTaskName());
        taskName.setTextColor( task.getStatus() == 0 ? Color.rgb(255, 0, 0) : Color.rgb(0, 255, 0));

        // Return the completed view to render on screen
        return convertView;
    }
}