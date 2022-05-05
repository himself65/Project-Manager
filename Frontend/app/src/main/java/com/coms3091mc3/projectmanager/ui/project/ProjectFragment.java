package com.coms3091mc3.projectmanager.ui.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.Selection;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
import android.widget.TextView;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectFragment extends Fragment {
    private FragmentProjectBinding binding;
    private JSONArray teamsArray = new JSONArray();
    private int projectID;
    TextView descriptionView;
    private String projectAnnouncements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectBinding.inflate(inflater, container, false);
        binding.setModal(new ProjectDataModel(getContext()));
        View view = binding.getRoot();
        projectID = (Integer) getArguments().get("projectID");

        descriptionView = view.findViewById(R.id.descriptionTextView);
        descriptionView.setMovementMethod(new ScrollingMovementMethod());
        setAnnouncements();

        Button btnDesc = view.findViewById(R.id.btnProjectDescription);
        btnDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProjectDesc();
            }
        });

        Log.d("project_debug","Entered project with id : " + projectID);
        String url = Const.API_SERVER + "/project/" + projectID;
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
                ProjectFragmentDirections.ActionNavigationProjectToNavigationTask action = ProjectFragmentDirections.actionNavigationProjectToNavigationTask(task.getTaskID(), projectID);
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
                            JSONObject task_project = object.getJSONObject("taskProject");
                            if(task_project.getInt("projectID") != projectID)
                                continue;
                            task.setStatus(object.getInt("status"));
//                            Log.d("task_debug", "Task Request: " + task_project.toString());
                            binding.getModal().tasksAdapter.add(task);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("task_debug", "Task Request Eror: " + error.getMessage());
                }
        );

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                project -> {
                    try {
                        JSONObject projectDetails = project.getJSONObject("project");
                        binding.getModal().project.set(
                                new Project(
                                        projectDetails.getInt("projectID"),
                                        projectDetails.getString("projectName"),
                                        projectDetails.getString("dateCreated")
                                )
                        );
                        binding.getModal().project.get().setAdmin(projectDetails.getInt("admin"));
                        setOverviewText(projectDetails.getInt("admin"));
//                        appendDescriptionText(projectDetails.getJSONObject("announcement").get);
                        Log.d("PROJECT_FRAGMENT","PROJECT DEBUG: ADMIN - " + binding.getModal().project.get().getAdmin());
                    } catch (JSONException e) {
                        Log.d("project_debug", "get project error: " +e.getMessage());
                        e.printStackTrace();
                    }
                },
                error -> {
                    Logger.getLogger("json").log(Level.INFO, error.toString());
                    Log.d("project_debug","Get project error: " + error.getMessage());
                });
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

        Menu menu = popup.getMenu();
//        Log.d("project_menu",menu.findItem(R.id.addMembers).setEnabled(false) + "");
        if(binding.getModal().project.get().getAdmin() != Const.user.getUserID()){
            menu.findItem(R.id.addMembers).setVisible(false);
            menu.findItem(R.id.addTeam).setVisible(false);
            menu.findItem(R.id.addTask).setVisible(false);
            binding.getRoot().findViewById(R.id.btnProjectDescription).setVisibility(View.GONE);
        }

        popup.show();
    }

    void getTeams() {
        String url = Const.API_SERVER + "/project/" + projectID + "/teams";
        JsonObjectRequest teamsRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
//                    teamsArray = teams;
                    JSONArray teams = null;
                    teamsArray = new JSONArray();

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

    public void listTeams() {
//        String url = Const.API_SERVER + "/project/" + binding.getModal().project.get().getId() + "/" + "teams";
        String url = Const.API_SERVER + "/project/" + projectID + "/" + "teams";
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
                    Log.d("project_debug", "Error: " + error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
        AppController.getInstance().addToRequestQueue(teamsRequest);
    }

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

                    alertBuilder.setItems(memberList, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("project_fragment","Clicking user: " + i);
                        }
                    });
                    alertBuilder.create().show();
                },
                error -> {
                    Log.d("project_fragment", "Error: " + error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(getContext(), "Listing member error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
        AppController.getInstance().addToRequestQueue(usersRequest);
    }

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
                        addTeamRequest(params, dialogInput.getText().toString());
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
            Log.d("project_fragment","Error adding task: No teams exists");
            Toast.makeText(getContext(), "No teams exists in the project yet", Toast.LENGTH_LONG).show();
        }
    }

    void addTeamRequest(Map<String, String> query, String teamName) {
        //NOTE: Must Add trailing '/' at end of URL for PUT requests (Android Volley)
//        String url = Const.API_SERVER + "/project/" + binding.getModal().project.get().getId() + "/addTeam/";
        String url = Const.API_SERVER + "/project/" + projectID + "/addTeam/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(query),
                response -> {
                    try {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                        Log.d("project_debug","Team added : "+ response.getString("message"));
                        JSONObject tmp = new JSONObject();
                        tmp.put("teamID",response.getInt("team_id"));
                        tmp.put("teamName",teamName);
                        teamsArray.put(tmp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("project_debug",error.getMessage());
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }

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
                        Task task = new Task(response.getInt("task_id"), params1.get("task"));
                        JSONArray users = response.getJSONArray("team_users");
                        for(int i = 0; i < users.length(); i++){
                            //if user is in the team that the task was assigned to, update tasksAdapter
                            if(users.getJSONObject(i).getString("username") == Const.user.getUsername()){
                                binding.getModal().tasksAdapter.add(task);
                                break;
                            }
                        }
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

    void setOverviewText(int adminID){
        TextView overviewText = binding.getRoot().findViewById(R.id.overviewText);
        String url = Const.API_SERVER + "/user/" + adminID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        overviewText.setText("Group Admin: " + response.getString("fullName"));
                    } catch (JSONException e) {
                        Log.d("project_fragment","Error setting overview text: " + e.getMessage());
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("project_fragment","Error setting overview text: " + error.getMessage());
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }

    void setDescriptionText(String s){
        try{
            descriptionView.setText("Announcements" + "\n\n" + s);
        }
        catch(Exception e){
            Log.d("project_fragment", "Error setting description text: " + e.getMessage());
            Toast.makeText(getContext(),"Error setting description text: " + e.getMessage(),Toast.LENGTH_SHORT);
        }

    }

    void addProjectDesc(){
        String url = Const.API_SERVER + "/project/" + projectID + "/addAnnouncement/";

        Context context = getContext();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText dialogInput = new EditText(context);
        dialogInput.setLayoutParams(lp);
        alertBuilder.setView(dialogInput);

        alertBuilder.setMessage("Enter Announcement")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialogInput.getText().toString().length() < 4) { //at least 4 characters
                            Toast.makeText(context, "Text must be at least 4 characters", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("announcement", dialogInput.getText().toString());
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                                new JSONObject(params),
                                response -> {
                                    try {
//                                        descriptionView.append("\n" + dialogInput.getText().toString() + "\n\t" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
                                        descriptionView.setText("Announcements\n\n" + dialogInput.getText().toString() + "\n\t" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + "\n" + projectAnnouncements);
                                    } catch (Exception e) {
                                        Log.d("project_fragment","Error adding description: " + e.getMessage());
                                        e.printStackTrace();
                                    }
                                },
                                error -> {
                                    Log.d("project_fragment","Error adding description: " + error.getMessage());
                                }
                        );
                        AppController.getInstance().addToRequestQueue(request);
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

    void setAnnouncements(){
        String url = Const.API_SERVER + "/project/" + projectID + "/announcements";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null,
                response -> {
                    try {
                        JSONArray announcements = response.getJSONArray("announcements");
                        String s = "";
                        for(int i = announcements.length() - 1; i >= 0; i--){
                            s += announcements.getJSONObject(i).getString("message")+ "\n\t" +
                                    announcements.getJSONObject(i).getString("dateCreated");
                            s += "\n";
                        }
                        projectAnnouncements = s;
                        Log.d("project_fragment","Set announcements: " + response.toString());
                        setDescriptionText(s);
//                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("project_fragment", "Error setting announcements: " + error.getMessage());
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }
}