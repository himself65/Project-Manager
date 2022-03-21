package com.coms3091mc3.projectmanager.ui.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.data.Project;

public class ProjectFragment extends Fragment {
    private Project project;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false);
    }
}