package com.coms3091mc3.projectmanager.ui.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.databinding.FragmentProjectsBinding;
import com.coms3091mc3.projectmanager.store.ProjectsDataModel;
import com.coms3091mc3.projectmanager.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectsFragment extends Fragment {

    private FragmentProjectsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        binding.setModal(new ProjectsDataModel(getContext()));
        View root = binding.getRoot();
        String url = Const.API_SERVER + "/user/" + Const.user.getUserID() + "/projects";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray projects = response.getJSONArray("users"); //TODO: change key to projects
                        for (int i = 0; i < projects.length(); i++) {
                            JSONObject object = (JSONObject) projects.get(i);
                            Project project = new Project(
                                    object.getInt("projectID"),
                                    object.getString("projectName"),
                                    object.getString("dateCreated")
                            );
                            binding.getModal().projectsAdapter.add(project);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Logger.getLogger("json").log(Level.INFO, error.toString())
        );

        AppController.getInstance().addToRequestQueue(request);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}