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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.coms3091mc3.projectmanager.BuildConfig;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.databinding.FragmentDashboardBinding;
import com.coms3091mc3.projectmanager.store.DashboardDataModal;
import com.coms3091mc3.projectmanager.utils.Const;
import com.coms3091mc3.projectmanager.view.AddProjectDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            String url = Const.API_SERVER + "/project";

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    projects -> {
                        try {
                            for (int i = 0; i < projects.length(); i++) {
                                JSONObject object = (JSONObject) projects.get(i);
                                Project project = new Project(
                                        object.getInt("projectID"),
                                        object.getString("projectName"),
                                        object.getString("dateCreated")
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
                FragmentManager fragmentManager = getChildFragmentManager();
                AddProjectDialogFragment fragment = new AddProjectDialogFragment(new AddProjectDialogFragment.AddProjectDialogListener() {
                    @Override
                    public void onDialogPositiveClick(String projectName) {
                        String url = Const.API_SERVER + "/project";
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("projectName", projectName);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                                new JSONObject(params),
                                response -> {
                                    Logger.getLogger("json").log(Level.INFO, response.toString());
                                    binding.getModal().projectsAdapter.add(projectName);
                                },
                                error -> Logger.getLogger("json").log(Level.INFO, error.toString()));
                        AppController.getInstance().addToRequestQueue(request);
                    }
                });
                fragment.show(fragmentManager, "hello");
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