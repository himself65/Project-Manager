package com.coms3091mc3.projectmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.utils.Const;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamActivity extends AppCompatActivity {

    Uri.Builder uri = new Uri.Builder();
    Button btnAddUser;
    ListView listUser;
    List<String> userList = new ArrayList<String>();
    JSONObject teamDetails = new JSONObject();
    String tag_team_req = "team_tag";
    int teamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_team);

        //Set team name and team ID from parameters
        Intent prevIntent = getIntent();
        TextView teamName = findViewById(R.id.teamName);
        teamName.setText(prevIntent.getStringExtra("teamName"));
        teamID = prevIntent.getIntExtra("teamId",0);

        //Initialize buttons and lists
        btnAddUser = findViewById(R.id.btnAddUser);
        listUser = findViewById(R.id.user_list);

        //set click listeners
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(view);
            }
        });
        //Get users in team
        Map<String, String> params = new HashMap<String, String>();
//        params.put("username", username.getText().toString()); //insert team id here

        teamRequest(params, "users");

    }

    public void addUser(View v){
        Map<String, String> params = new HashMap<String, String>();
//        params.put("username", username.getText().toString()); //insert team id here

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TeamActivity.this);
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
                        teamRequest(params, "add");
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

    public void teamRequest(Map<String, String> params, String tag){
        uri = Uri.parse(Const.MOCK_SERVER + "/team/" + tag).buildUpon();
//        uri = Uri.parse(Const.API_SERVER + "/" + v.getTag().toString()).buildUpon();
        JsonObjectRequest teamRequest = new JsonObjectRequest(Request.Method.POST, uri.build().toString(),
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        Log.d("team_debug", response.toString());
                        try{
                            if(response.getInt("status") == 200 && tag.equals("add")) { //add user to team
                                btnAddUser.setClickable(false);
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            }
                            if(response.getInt("status") == 200 && tag.equals("users")){ //list users in team
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                Log.d("team_debug",response.getJSONArray("users").toString());
                                for(int i = 0; i < (response.getJSONArray("users").length()); i++){
                                    userList.add(response.getJSONArray("users").getJSONObject(i).getString("fullname"));
                                }
                                ArrayAdapter<String> userListAdapter = new ArrayAdapter<String>(TeamActivity.this, android.R.layout.simple_list_item_1, userList);
                                listUser.setAdapter(userListAdapter);
                            }
                        }
                        catch(Exception e){
                            Log.d("team_debug",e.getMessage());
//                            e.printStackTrace();
                        }
                        finally{
                            btnAddUser.setClickable(true);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("team_debug_error", "Error: " + error.toString());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(teamRequest, tag_team_req);
    }

    public void returnToProject(View v){
        Intent intentReturn = new Intent(TeamActivity.this, MainActivity.class);
        startActivity(intentReturn);
        finish();
    }

    @Override
    public void onBackPressed() { //Handle back button pressed on android
        returnToProject(this.findViewById(android.R.id.content));

    }
}