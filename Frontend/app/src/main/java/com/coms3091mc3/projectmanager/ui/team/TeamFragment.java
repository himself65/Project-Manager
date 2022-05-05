package com.coms3091mc3.projectmanager.ui.team;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.TeamActivity;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.data.Team;
import com.coms3091mc3.projectmanager.data.User;
import com.coms3091mc3.projectmanager.databinding.FragmentTeamBinding;
import com.coms3091mc3.projectmanager.store.TeamDataModel;
import com.coms3091mc3.projectmanager.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeamFragment extends Fragment {
    FragmentTeamBinding binding;

    ListView lv;
    List<String> userList = new ArrayList<String>();
    List<Integer> onlineStatusList = new ArrayList<Integer>();
    ArrayAdapter<String> usersAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeamBinding.inflate(inflater, container, false);
        binding.setModal(new TeamDataModel());
        int id = (Integer) getArguments().get("teamID");
        getTeamRequest(id);
        View view = binding.getRoot();
        Button button = view.findViewById(R.id.btnAddUser);
        lv = (ListView) view.findViewById(R.id.user_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMember();
            }
        });
        return view;
    }

    void getTeamRequest(int id) {
        String url = Const.API_SERVER + "/team/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                object -> {
                    try {
                        String teamName = object.getString("teamName");
                        int teamID = object.getInt("teamID");
                        Team team = new Team(teamID, teamName);
                        binding.getModal().team.set(team);

                        getMembersRequest(id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("team_fragment","Error getting team: " + error.getMessage());
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }

    void addMember() {
        Map<String, String> params = new HashMap<String, String>();
        Context context = getContext();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText dialogInput = new EditText(context);
        Log.d("team_fragment","Dialog input " + dialogInput.getId());
        dialogInput.setLayoutParams(lp);
        alertBuilder.setView(dialogInput);

        alertBuilder.setMessage("Enter username")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialogInput.getText().toString().length() < 4) { //at least 4 characters
                            Toast.makeText(context.getApplicationContext(), "Name must be at least 4 characters", Toast.LENGTH_LONG).show();
                            return;
                        }
                        params.put("username", dialogInput.getText().toString());
                        // todo: query to add team member
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

    void getMembersRequest(int id){ //get list of users
        String url = Const.API_SERVER + "/team/" + id + "/users";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray users = response.getJSONArray("users");
                        for (int i = 0; i < users.length(); i++) {
                            JSONObject object = (JSONObject) users.get(i);
                            User user = new User(
                                    object.getInt("userId"),
                                    object.getString("username"),
                                    object.getString("fullName")
                            );
                            userList.add(object.getString("fullName"));
                            //TODO:
                            onlineStatusList.add(object.getInt("loggedIn"));
//                            Log.d("team_fragment",object.toString());
                        }
                        usersAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1,
                                userList);
                        lv.setAdapter(usersAdapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                if(onlineStatusList.get(i) == 1){
                                    view.setBackgroundColor(Color.rgb(0,200,0));
                                }
                                else {
                                    view.setBackgroundColor(Color.rgb(200, 0, 0));
                                }
                            }
                        });
                        //TODO:
                        for(int i = 0; i < lv.getCount(); i++){
                            lv.performItemClick(lv.getAdapter().getView(i,null,null),
                                    i, lv.getAdapter().getItemId(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("team_fragment","Error getting members: " + error.getMessage());
                });

        AppController.getInstance().addToRequestQueue(request);
    }

    //NOTE: Must Add trailing '/' at end of URL for PUT requests (Android Volley)
    void addMemberRequest(Map<String, String> params) { //add member to team
        String url = Const.API_SERVER + "/team/" + binding.getModal().team.get().getTeamID() +"/addUser/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                response -> {
                    try {
                        Log.d("team_fragment","Add Member: " + response.toString());
                        if(response.getInt("status") == 200){
                            userList.add(response.getJSONObject("user").getString("fullName"));
                            onlineStatusList.add(response.getJSONObject("user").getInt("loggedIn"));
                            usersAdapter = new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_list_item_1,
                                    userList);
                            lv.setAdapter(usersAdapter);
                        }
                        Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("team_fragment","Error eddign member: " + error.getMessage());
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }
}
