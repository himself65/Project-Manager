package com.coms3091mc3.projectmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.utils.Const;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.coms3091mc3.projectmanager.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Uri.Builder uri = new Uri.Builder();
    String tag_project_req = "project_req";
    JSONArray teams;
    boolean viewAdd = false;
    int projectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get project details from intent parameters
        Intent prevIntent = getIntent();
        projectID = prevIntent.getIntExtra("projectID", 0);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void dropdownMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile:
//                archive(item);
                        return true;
                    case R.id.settings:
//                delete(item);
                        return true;
                    case R.id.logout:
                        logout(menuItem);
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
    }

    public void projectMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.listMembers:
                        listMembers(v);
                        return true;
                    case R.id.addMembers:
                        addMembers(v);
                        return true;
                    case R.id.viewTeams:
                        viewAdd = false;
                        getTeams(v);
                        return true;
                    case R.id.addTeam:
                        addTeam(v);
                        return true;
                    case R.id.addTask:
                        viewAdd = true;
                        getTeams(v);
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.project_menu, popup.getMenu());
        popup.show();
    }

    public void logout(MenuItem item){
        uri = Uri.parse(Const.MOCK_SERVER + "/logout").buildUpon();
//        uri = Uri.parse(Const.API_SERVER + "/logout").buildUpon();
        ProgressBar pBar = findViewById(R.id.progressBar);
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", Const.username);
        JsonObjectRequest logoutRequest;
        logoutRequest = new JsonObjectRequest(Request.Method.POST, uri.build().toString(),
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("status") == 200) { //Logout Success
                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intentLogout);
                            finish();
                        }
                    } catch (Exception e) {
                        Log.d("logout_debug", e.getMessage());
//                            e.printStackTrace();
                    } finally {
                        pBar.setVisibility(View.INVISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("logout_debug", "Error: " + error.toString());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress dialog
                pBar.setVisibility(View.INVISIBLE);
            }
        });
        AppController.getInstance().addToRequestQueue(logoutRequest, "logout_request");
    }

    public void listMembers(View v){
        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", username.getText().toString()); //pass project id

        projectRequest(params, v, "users");

    }

    public void addMembers(View v){
        Map<String, String> params = new HashMap<String, String>();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText dialogInput = new EditText(getBaseContext());
        dialogInput.setLayoutParams(lp);
        alertBuilder.setView(dialogInput);

        alertBuilder.setMessage("Enter username")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(dialogInput.getText().toString().length() < 4){ //at least 4 characters
                            Toast.makeText(getApplicationContext(), "Name must be at least 4 characters", Toast.LENGTH_LONG).show();
                            return;
                        }
                        params.put("username", dialogInput.getText().toString());
                        projectRequest(params, v, "addUser");
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

    public void getTeams(View v){
        Map<String, String> params = new HashMap<String, String>();
        params.put("project",String.valueOf(5)); //pass project ID
        projectRequest(params, v, "teams"); //get list of teams
    }

    public void addTeam(View v){
        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", username.getText().toString()); //pass project id
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText dialogInput = new EditText(getBaseContext());
        dialogInput.setLayoutParams(lp);
        alertBuilder.setView(dialogInput);

        alertBuilder.setMessage("Enter Team Name")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(dialogInput.getText().toString().length() < 4){ //at least 4 characters
                            Toast.makeText(getApplicationContext(), "Name must be at least 4 characters", Toast.LENGTH_LONG).show();
                            return;
                        }
                        params.put("username", dialogInput.getText().toString());
                        projectRequest(params, v, "addTeam");
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

    public void addTask(View v){
        if(teams != null && teams.length() > 0){
            int[] projectTeamsID = new int[teams.length()];
            String[] projectTeamsName = new String[teams.length()];
            Map<String, String> params = new HashMap<String, String>();

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            for(int i = 0; i < teams.length(); i++){
                try{
                    projectTeamsID[i] = teams.getJSONObject(i).getInt("id");
                    projectTeamsName[i] = teams.getJSONObject(i).getString("name");
                }
                catch(Exception e){
                    Log.e("project_debug",e.getMessage());
                }
            }
            ArrayAdapter<String> teamsArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projectTeamsName);
            alertBuilder.setTitle("Create Task");

            LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addTaskView = inflater.inflate(R.layout.add_task,null);
            EditText taskName = (EditText)addTaskView.findViewById(R.id.popup_input);
            Spinner teamList = (Spinner)addTaskView.findViewById(R.id.teamList);
            teamList.setAdapter(teamsArray);
            alertBuilder.setView(addTaskView);

            alertBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(taskName.getText().toString().length() < 4){ //at least 4 characters
                        Toast.makeText(getApplicationContext(), "Project Name must be at least 4 characters", Toast.LENGTH_LONG).show();
                        return;
                    }
                    params.put("taskName", taskName.getText().toString()); //task name

                    params.put("assignedTeam", String.valueOf(projectTeamsID[teamList.getSelectedItemPosition()] )); //team ID
                    projectRequest(params, v, "addTask");
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
        else{
            Toast.makeText(getApplicationContext(), "No teams exists in the project yet", Toast.LENGTH_LONG);
        }
    }

    public void projectRequest(Map<String, String> params, View v, String tag){
        uri = Uri.parse(Const.MOCK_SERVER + "/project/projectID/" + tag).buildUpon();
//        uri = Uri.parse(Const.API_SERVER + "/project/" + tag).buildUpon();
        JsonObjectRequest projectRequest = new JsonObjectRequest(Request.Method.POST, uri.build().toString(),
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getInt("status") != 200){
                                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG);
                            }
                            else{
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);

                                switch(tag){
                                    case "users": //Get List of Members Request Success
                                        alertBuilder.setTitle("List of Members")
                                                .setPositiveButton("BACK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        return;
                                                    }
                                                });

                                        String[] memberList = new String[response.getJSONArray("users").length()];
                                        for(int i = 0; i < memberList.length; i++){
                                            memberList[i] = response.getJSONArray("users").getString(i);
                                        }

                                        alertBuilder.setItems(memberList, null);

                                        //handle on click item
//                                alertBuilder.setItems(memberList, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    }
//                                });

                                        // Create the AlertDialog object and return it
                                        alertBuilder.create().show();
                                        break;
                                    case "addUser": //add user to project
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                        break;
                                    case "teams": //get list of teams on project
                                        teams = response.getJSONArray("teams");
                                        Log.d("project_debug","Teams: " + teams.toString());
                                        if(viewAdd){ //view teams and add task action
                                            addTask(v);
                                        }
                                        else { //view teams action
                                            alertBuilder.setTitle("List of Teams")
                                                    .setPositiveButton("BACK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            return;
                                                        }
                                                    });

                                            String[] teamList = new String[teams.length()];
                                            for(int i = 0; i < teamList.length; i++){
                                                teamList[i] = teams.getJSONObject(i).getString("name");
                                            }

//                                            alertBuilder.setItems(teamList, null);
                                            //handle on click item
                                            alertBuilder.setItems(teamList, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) { //open team details page
                                                    Intent newIntent = new Intent(MainActivity.this, TeamActivity.class);
                                                    newIntent.putExtra("teamId", 0);
                                                    newIntent.putExtra("teamName",teamList[i]);
                                                    startActivity(newIntent);
                                                    finish();
                                                }
                                            });
                                            // Create the AlertDialog object and return it
                                            alertBuilder.create().show();
                                        }
                                        break;
                                    case "addTask": //create a task and assing a team to it
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                        break;
                                    case "addTeam": //create a new team
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        }
                        catch(Exception e){
                            Log.d("project_debug",tag + ": " + e.getMessage());
//                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("project_debug", "Error: " + error.toString());
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(projectRequest, tag_project_req);
    }

    @Override
    public void onBackPressed() { //Handle back button pressed on android


    }

}