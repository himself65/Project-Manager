package com.coms3091mc3.projectmanager.ui.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.data.Task;
import com.coms3091mc3.projectmanager.data.Team;
import com.coms3091mc3.projectmanager.databinding.FragmentProjectBinding;
import com.coms3091mc3.projectmanager.store.ProjectDataModel;
import com.coms3091mc3.projectmanager.utils.Const;
import com.coms3091mc3.projectmanager.view.AddProjectDialogFragment;
import com.coms3091mc3.projectmanager.view.AddTeamDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectFragment extends Fragment {
    private FragmentProjectBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectBinding.inflate(inflater, container, false);
        binding.setModal(new ProjectDataModel(getContext()));
        View view = binding.getRoot();
        int id = (Integer) getArguments().get("projectID");
        String url = Const.API_SERVER + "/project/" + id;
        String tasksUrl = url + "/tasks";
        Button button = view.findViewById(R.id.add_project);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });

        JsonArrayRequest tasksRequest = new JsonArrayRequest(Request.Method.GET, tasksUrl, null,
                tasks -> {
                    try {
                        for (int i = 0; i < tasks.length(); i++) {
                            JSONObject object = (JSONObject) tasks.get(i);
                            Task task = new Task(
                                    object.getInt("taskID"),
                                    object.getString("taskName")
                            );
                            binding.getModal().tasksAdapter.add(task);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Logger.getLogger("json").log(Level.INFO, error.toString())
        );

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                project -> {
                    try {
                        binding.getModal().project.set(
                                new Project(
                                        project.getInt("projectID"),
                                        project.getString("projectName"),
                                        project.getString("dateCreated")
                                )
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Logger.getLogger("json").log(Level.INFO, error.toString()));
        AppController.getInstance().addToRequestQueue(request);
        AppController.getInstance().addToRequestQueue(tasksRequest);
        return view;
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.add_team) {
                    FragmentManager fragmentManager = getChildFragmentManager();
                    AddTeamDialogFragment fragment = new AddTeamDialogFragment(new AddTeamDialogFragment.AddTeamDialogListener() {
                        @Override
                        public void onDialogPositiveClick(Team team) {
                            Logger.getGlobal().log(Level.INFO, team.getTeamName());
                            String url = Const.API_SERVER + "/project" + binding.getModal().project.get().getId() + "/addTeam";
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("teamName", team.getTeamName());
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), response -> {

                            }, error -> {

                            });
                            AppController.getInstance().addToRequestQueue(request);
                        }
                    });
                    fragment.show(fragmentManager, "addTeam");
                }
                return true;
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_project_menu, popup.getMenu());
        popup.show();
    }

    public void addTask() {

    }

    public void addTeam() {

    }

    public void addUser() {

    }

}