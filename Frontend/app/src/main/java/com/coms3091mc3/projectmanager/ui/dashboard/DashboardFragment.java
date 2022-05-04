package com.coms3091mc3.projectmanager.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.coms3091mc3.projectmanager.BuildConfig;
import com.coms3091mc3.projectmanager.LoginActivity;
import com.coms3091mc3.projectmanager.MainActivity;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.data.Task;
import com.coms3091mc3.projectmanager.databinding.FragmentDashboardBinding;
import com.coms3091mc3.projectmanager.store.DashboardDataModal;
import com.coms3091mc3.projectmanager.ui.project.ProjectFragment;
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

    @Override
    public void onDestroy() {
        AppController.getInstance().cancelPendingRequests("DashboardFragment");
        super.onDestroy();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        Context context = getContext();
        String url = Const.API_SERVER + "/user/" + Const.user.getUserID() + "/projects";
        String tasksUrl = Const.API_SERVER + "/user/" + Const.user.getUserID() + "/tasks";
        binding.setModal(new DashboardDataModal(context));
        //get user projects
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray projects = response.getJSONArray("projects");
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
        AppController.getInstance().addToRequestQueue(request, "DashboardFragment");

        //get user tasks
        JsonObjectRequest userRequest = new JsonObjectRequest(Request.Method.GET, tasksUrl, null,
                response -> {
                    try {
                        JSONArray tasks = response.getJSONArray("tasks");
                        for (int i = 0; i < tasks.length(); i++) {
                            JSONObject object = (JSONObject) tasks.get(i);
                            Task task = new Task(
                                    object.getInt("id"),
                                    object.getString("task")
                            );
                            JSONObject project = object.getJSONObject("taskProject");
                            task.setStatus(object.getInt("status"));
                            task.setProjectID(project.getInt("projectID"));
                            binding.getModal().tasksAdapter.add(task);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("dashboard_fragment","Error getting user tasks: " + error.getMessage());
                }
        );
        AppController.getInstance().addToRequestQueue(userRequest, "DashboardFragment");
        View view = binding.getRoot();
        Button button = view.findViewById(R.id.add_project);
        GridView gridView = view.findViewById(R.id.dashboard_grid_layout);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Project project = binding.getModal().projectsAdapter.getItem(i);
                DashboardFragmentDirections.ActionNavigationDashboardToNavigationProject action = DashboardFragmentDirections.actionNavigationDashboardToNavigationProject(project.getId());
                Navigation.findNavController(view).navigate(action);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });

        GridView taskView = view.findViewById(R.id.tasksGridView);
        taskView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = binding.getModal().tasksAdapter.getItem(i);
                DashboardFragmentDirections.ActionNavigationDashboardToNavigationTask action = DashboardFragmentDirections.actionNavigationDashboardToNavigationTask(task.getTaskID(),task.getProjectID());
                Navigation.findNavController(view).navigate(action);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
        return view;
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.add_project) {
                    addProject();
                }
                if (id == R.id.logout){
                    logout();
                }
                return true;
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_dashboard_menu, popup.getMenu());
        popup.show();
    }

    public void addProject() {
        FragmentManager fragmentManager = getChildFragmentManager();
        AddProjectDialogFragment fragment = new AddProjectDialogFragment(new AddProjectDialogFragment.AddProjectDialogListener() {
            @Override
            public void onDialogPositiveClick(Project project) {
                String url = Const.API_SERVER + "/project";
                Map<String, String> params = new HashMap<String, String>();
                params.put("projectName", project.getName());
                params.put("username", Const.user.getUsername());

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                        new JSONObject(params),
                        response -> {
                            Logger.getLogger("json").log(Level.INFO, response.toString());
                            binding.getModal().projectsAdapter.add(project);
                        },
                        error -> Logger.getLogger("json").log(Level.INFO, error.toString()));
                AppController.getInstance().addToRequestQueue(request);
            }
        });
        fragment.show(fragmentManager, "hello");
    }

    public void logout() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", Const.user.getUsername());
        Log.d("logout_debug",Const.user.toString());
        logoutRequest(params);
    }

    //NOTE: Must Add trailing '/' at end of URL for PUT requests (Android Volley)
    void logoutRequest(Map<String, String> params){
        String url = Const.API_SERVER + "/logout/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                response -> {
                    try {
                        Log.d("logout_debug","Logout Request 1 " + url);
                        Log.d("logout_debug","Logout Request : " + response.getString("message"));
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intentLogout = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intentLogout);
                        getActivity().finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    VolleyLog.d("logout_debug", "Error: " + error.toString());
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}