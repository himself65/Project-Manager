package com.coms3091mc3.projectmanager.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.coms3091mc3.projectmanager.AppController;
import com.coms3091mc3.projectmanager.BuildConfig;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.databinding.FragmentDashboardBinding;
import com.coms3091mc3.projectmanager.store.DashboardDataModal;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        Context context = getContext();
        if (context != null) {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = BuildConfig.apiUrl + "/projects";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONArray projects = response.getJSONArray("projects");
                            for (int i = 0; i < projects.length(); i++) {
                                JSONObject object = (JSONObject) projects.get(i);
                                Project project = new Project(
                                        object.getInt("id"),
                                        object.getString("name"),
                                        object.getString("created_date")
                                );
                                binding.getModal().projectsAdapter.add(project.getName());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Logger.getLogger("json").log(Level.INFO, error.toString()));

            AppController.getInstance().addToRequestQueue(request);
        }
        binding.setModal(new DashboardDataModal(context));
        View view = binding.getRoot();
        Button button = view.findViewById(R.id.add_project);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.getLogger("button").log(Level.INFO, "clicked!");
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}