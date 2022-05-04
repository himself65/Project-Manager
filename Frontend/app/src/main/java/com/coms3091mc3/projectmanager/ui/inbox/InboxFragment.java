package com.coms3091mc3.projectmanager.ui.inbox;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.data.Task;
import com.coms3091mc3.projectmanager.data.Team;
import com.coms3091mc3.projectmanager.databinding.FragmentInboxBinding;
import com.coms3091mc3.projectmanager.store.InboxDataModel;
import com.coms3091mc3.projectmanager.store.ProjectsDataModel;
import com.coms3091mc3.projectmanager.ui.project.ProjectFragmentDirections;
import com.coms3091mc3.projectmanager.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InboxFragment extends Fragment {

    private FragmentInboxBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InboxViewModel inboxViewModel =
                new ViewModelProvider(this).get(InboxViewModel.class);

        binding = FragmentInboxBinding.inflate(inflater, container, false);
        binding.setModal(new InboxDataModel(getContext()));
        View root = binding.getRoot();

//        final TextView textView = binding.textInbox;
//        inboxViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        String url = Const.API_SERVER + "/user/" + Const.user.getUserID() + "/teams";
//        String url = "https://bd9f22ed-10c9-4c41-a415-b951634333f6.mock.pstmn.io/user/3/teams";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray teams = response.getJSONArray("teams");
                        for (int i = 0; i < teams.length(); i++) {
                            JSONObject object = (JSONObject) teams.get(i);
                            Team team = new Team(
                                    object.getInt("teamID"),
                                    object.getString("teamName")
                            );
                            binding.getModal().teamsAdapter.add(team);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("inbox_fragment","Inbox error : " + error.getMessage());
                }
        );

        AppController.getInstance().addToRequestQueue(request);

        GridView gridView = root.findViewById(R.id.inbox_list);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Team team = binding.getModal().teamsAdapter.getItem(i);
                InboxFragmentDirections.ActionNavigationInboxToNavigationChat action = InboxFragmentDirections.actionNavigationInboxToNavigationChat(team.getTeamID());
                Navigation.findNavController(view).navigate(action);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}