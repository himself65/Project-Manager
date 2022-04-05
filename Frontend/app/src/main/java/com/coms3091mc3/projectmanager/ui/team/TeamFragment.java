package com.coms3091mc3.projectmanager.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Team;
import com.coms3091mc3.projectmanager.databinding.FragmentTeamBinding;
import com.coms3091mc3.projectmanager.store.TeamDataModel;
import com.coms3091mc3.projectmanager.utils.Const;

import org.json.JSONException;

public class TeamFragment extends Fragment {
    FragmentTeamBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeamBinding.inflate(inflater, container, false);
        binding.setModal(new TeamDataModel());
        int id = (Integer) getArguments().get("teamID");
        teamRequest(id);
        return binding.getRoot();
    }

    void teamRequest(int id) {
        String url = Const.API_SERVER + "/team/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                object -> {
                    try {
                        String teamName = object.getString("teamName");
                        int teamID = object.getInt("teamID");
                        Team team = new Team(teamID, teamName);
                        binding.getModal().team.set(team);
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
