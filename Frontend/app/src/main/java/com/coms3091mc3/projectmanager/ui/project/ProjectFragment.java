package com.coms3091mc3.projectmanager.ui.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.MainActivity;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.TasksAdapter;
import com.coms3091mc3.projectmanager.TeamActivity;
import com.coms3091mc3.projectmanager.TeamsAdapter;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.data.Task;
import com.coms3091mc3.projectmanager.data.Team;
import com.coms3091mc3.projectmanager.databinding.FragmentProjectBinding;
import com.coms3091mc3.projectmanager.store.ProjectDataModel;
import com.coms3091mc3.projectmanager.utils.Const;
import com.coms3091mc3.projectmanager.view.AddTeamDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Project fragment.
 */
public class ProjectFragment extends Fragment {
    private FragmentProjectBinding binding;
    private JSONArray teamsArray = new JSONArray();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectBinding.inflate(inflater, container, false);
        binding.setModal(new ProjectDataModel(getContext()));
        View view = binding.getRoot();
        int id = (Integer) getArguments().get("projectID");
        String url = Const.API_SERVER + "/project/" + id;
        String tasksUrl = Const.API_SERVER + "/user/" + Const.user.getUserID() + "/tasks";
        getTeams();
        Button button = view.findViewById(R.id.add_project);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });

        GridView gridView = view.findViewById(R.id.projectTasks);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = binding.getModal().tasksAdapter.getItem(i);
                ProjectFragmentDirections.ActionNavigationProjectToNavigationTask action = ProjectFragmentDirections.actionNavigationProjectToNavigationTask(task.getTaskID(), id);
                Navigation.findNavController(view).navigate(action);
            }
        });

        JsonObjectRequest tasksRequest = new JsonObjectRequest(Request.Method.GET, tasksUrl, null,
                response -> {
                    try {
                        JSONArray tasks = response.getJSONArray("tasks");
                        for (int i = 0; i < tasks.length(); i++) {
                            JSONObject object = (JSONObject) tasks.get(i);
                            Task task = new Task(
                                    object.getInt("id"),
                                    object.getString("task")
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

    /**
     * Show menu.
     *
     * @param v the v
     */
    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.addTeam) {
                    addTeam();
                } else if (id == R.id.listMembers) {
                    listMembers();
                } else if (id == R.id.listTeams) {
                    listTeams();
                } else if (id == R.id.addMembers) {
                    addMember();
                } else if (id == R.id.addTask) {
                    addTask();
                }
                return true;
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_project_menu, popup.getMenu());
        popup.show();
    }

    /**
     * Gets teams.
     */
    void getTeams() {
        String url = Const.API_SERVER + "/project/" + (Integer) getArguments().get("projectID") + "/teams";
        JsonObjectRequest teamsRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
//                    teamsArray = teams;
                    JSONArray teams = null;
                    try {
                        teams = response.getJSONArray("teams");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < teams.length(); i++) {
                        try {
                            JSONObject object = teams.getJSONObject(i);
                            int id = object.getInt("teamID");
                            String name = object.getString("teamName");
                            Team team = new Team(id, name);
                            teamsArray.put(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    VolleyLog.d("project_debug", "Error: " + error.toString());
                    error.printStackTrace();
//                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
        AppController.getInstance().addToRequestQueue(teamsRequest);
    }

    /**
     * List teams.
     */
    public void listTeams() {
        String url = Const.API_SERVER + "/project/" + binding.getModal().project.get().getId() + "/" + "teams";
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        JsonObjectRequest teamsRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    JSONArray teams = null;
                    try {
                        teams = response.getJSONArray("teams");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertBuilder.setTitle("List of Teams");
                    TeamsAdapter adapter = new TeamsAdapter(getContext(), R.layout.fragment_task_item);
                    for (int i = 0; i < teams.length(); i++) {
                        try {
                            JSONObject object = teams.getJSONObject(i);
                            int id = object.getInt("teamID");
                            String name = object.getString("teamName");
                            Team team = new Team(id, name);
                            adapter.add(team);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    alertBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Team team = (Team) adapter.getItem(i);
                            int id = team.getTeamID();
                            ProjectFragmentDirections.ActionNavigationProjectToNavigationTeam action = ProjectFragmentDirections.actionNavigationProjectToNavigationTeam(id);
                            Navigation.findNavController(getView()).navigate(action);
                        }
                    });
                    alertBuilder.create().show();
                },
                error -> {
                    VolleyLog.d("project_debug", "Error: " + error.toString());
                    error.printStackTrace();
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
        AppController.getInstance().addToRequestQueue(teamsRequest);
    }

    /**
     * Add member.
     */
    public void addMember() {
        Map<String, String> params = new HashMap<String, String>();
        Context context = getContext();
//        params.put("username", username.getText().toString()); //insert team id here

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText dialogInput = new EditText(context);
        dialogInput.setLayoutParams(lp);
        alertBuilder.setView(dialogInput);

        alertBuilder.setMessage("Enter username")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialogInput.getText().toString().length() < 4) { //at least 4 characters
                            Toast.makeText(context, "Name must be at least 4 characters", Toast.LENGTH_LONG).show();
                            return;
                        }
                        params.put("username", dialogInput.getText().toString());
                        // todo: post to add user
                        addMemberRequest(params);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        return;
                    }
                });
        // Create the AlertDialog object and return it
        alertBuilder.create().show();
    }

    /**
     * List members.
     */
    public void listMembers() {
        String url = Const.API_SERVER + "/project/" + binding.getModal().project.get().getId() + "/" + "users";
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        JsonObjectRequest usersRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    JSONArray users = null;
                    try {
                        users = response.getJSONArray("users");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertBuilder.setTitle("List of Members")
                            .setPositiveButton("BACK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    return;
                                }
                            });

                    String[] memberList = new String[users.length()];
                    for (int i = 0; i < memberList.length; i++) {
                        try {
                            memberList[i] = users.getJSONObject(i).getString("username");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    alertBuilder.setItems(memberList, null);
                    alertBuilder.create().show();
                },
                error -> {
                    VolleyLog.d("project_debug", "Error: " + error.toString());
                    error.printStackTrace();
                    Toast.makeText(getContext(), "Unknown Error", Toast.LENGTH_LONG).show();
                }
        );
        AppController.getInstance().addToRequestQueue(usersRequest);
    }

    /**
     * Add team.
     */
    public void addTeam() {
        Map<String, String> params = new HashMap<String, String>();
        Context context = getContext();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText dialogInput = new EditText(context);
        dialogInput.setLayoutParams(lp);
        alertBuilder.setView(dialogInput);

        alertBuilder.setMessage("Enter Team Name")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialogInput.getText().toString().length() < 4) { //at least 4 characters
                            Toast.makeText(context, "Name must be at least 4 characters", Toast.LENGTH_LONG).show();
                            return;
                        }
                        params.put("teamName", dialogInput.getText().toString());
                        addTeamRequest(params);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        return;
                    }
                });
        // Create the AlertDialog object and return it
        alertBuilder.create().show();
    }

    /**
     * Add task.
     */
    void addTask() {
        if (teamsArray != null && teamsArray.length() > 0) {
            int[] projectTeamsID = new int[teamsArray.length()];
            String[] projectTeamsName = new String[teamsArray.length()];
            Map<String, String> params1 = new HashMap<String, String>();
            Map<String, Integer> params2 = new HashMap<String, Integer>();

            for (int i = 0; i < teamsArray.length(); i++) {
                try {
                    projectTeamsID[i] = teamsArray.getJSONObject(i).getInt("teamID");
                    projectTeamsName[i] = teamsArray.getJSONObject(i).getString("teamName");
                } catch (Exception e) {
                    Log.e("project_debug", e.getMessage());
                }
            }
            ArrayAdapter<String> teamNames = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, projectTeamsName);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addTaskView = inflater.inflate(R.layout.fragment_add_task, null);
            EditText taskName = (EditText) addTaskView.findViewById(R.id.editTextTextTaskName);
            Spinner teamList = (Spinner) addTaskView.findViewById(R.id.assignTeamSpinner);
            teamList.setAdapter(teamNames);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
            alertBuilder.setView(addTaskView);
            alertBuilder.setMessage("Add a Task");
            alertBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (taskName.getText().toString().length() < 4) { //at least 4 characters
                        Toast.makeText(getContext(), "Task Name must be at least 4 characters", Toast.LENGTH_LONG).show();
                        return;
                    }
                    params1.put("task",taskName.getText().toString()); //task name
                    params2.put("team_id", projectTeamsID[teamList.getSelectedItemPosition()]); //team ID
                    Log.d("params","Added task " + taskName.getText().toString());
                    Log.d("params","Added team id " + String.valueOf(projectTeamsID[teamList.getSelectedItemPosition()]));
                    try {
                        addTaskRequest(params1, params2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            })
            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    return;
                }
            });
            // Create the AlertDialog object and return it
            alertBuilder.create().show();
        } else {
            Toast.makeText(getContext(), "No teams exists in the project yet", Toast.LENGTH_LONG);
        }
    }

    /**
     * Add team request.
     *
     * @param query the query
     */
    void addTeamRequest(Map<String, String> query) {
        //NOTE: Must Add trailing '/' at end of URL for PUT requests (Android Volley)
        String url = Const.API_SERVER + "/project/" + binding.getModal().project.get().getId() + "/addTeam/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(query),
                response -> {
                    Log.d("project_debug", response.toString());
                    try {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * Add member request.
     *
     * @param query the query
     */
    void addMemberRequest(Map<String, String> query){
        String url = Const.API_SERVER + "/project/" + binding.getModal().project.get().getId() + "/" + "addUser/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(query),
                response -> {
                    try {
                        Log.d("project_debug", response.getString("message"));
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Unexpected error", Toast.LENGTH_LONG).show();
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * Add task request.
     *
     * @param params1 the params 1
     * @param params2 the params 2
     * @throws JSONException the json exception
     */
    void addTaskRequest(Map<String, String> params1, Map<String, Integer> params2) throws JSONException {
        JSONObject query = new JSONObject();
        query.put("task", params1.get("task"));
        query.put("team_id", params2.get("team_id"));
        Log.d("params",query.toString());
        String url = Const.API_SERVER + "/project/" + binding.getModal().project.get().getId() + "/addTask/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                query,
                response -> {
                    try {
                        Log.d("project_debug", response.getString("message"));
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }
}