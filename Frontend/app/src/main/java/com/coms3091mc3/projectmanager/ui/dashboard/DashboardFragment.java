package com.coms3091mc3.projectmanager.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.coms3091mc3.projectmanager.AppController;
import com.coms3091mc3.projectmanager.BuildConfig;
import com.coms3091mc3.projectmanager.MyItemRecyclerViewAdapter;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.databinding.FragmentDashboardBinding;
import com.coms3091mc3.projectmanager.store.ProjectStore;
import com.coms3091mc3.projectmanager.store.UserStore;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView gridView = view.findViewById(R.id.dashboard_grid_view);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        Context context = getContext();
        if (context != null) {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = BuildConfig.apiUrl + "/projects";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONArray projects = response.getJSONArray("projects");
                            for (int i = 0; i < projects.length(); ++i) {
                                Object object = projects.get(i);
                                Gson gson = new Gson();
                                Project project = gson.fromJson(response.toString(), Project.class);
                                ObservableArrayList<Project> array = binding.getProjectStore().projects;
                                array.add(project);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Logger.getLogger("json").log(Level.INFO, error.toString()));

            AppController.getInstance().addToRequestQueue(request);
        }
        ProjectStore projectStore = new ProjectStore();
        gridView.setAdapter(new MyItemRecyclerViewAdapter(projectStore.projects));
        binding.setUser(new UserStore());
        binding.setProjectStore(projectStore);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}