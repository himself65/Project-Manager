package com.coms3091mc3.projectmanager.ui.task;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.TaskActivity;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Task;
import com.coms3091mc3.projectmanager.databinding.FragmentProjectBinding;
import com.coms3091mc3.projectmanager.databinding.FragmentTaskBinding;
import com.coms3091mc3.projectmanager.store.ProjectDataModel;
import com.coms3091mc3.projectmanager.store.TaskDataModel;
import com.coms3091mc3.projectmanager.ui.project.ProjectFragmentDirections;
import com.coms3091mc3.projectmanager.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TaskFragment extends Fragment {
    private FragmentTaskBinding binding;
    private int projectID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        binding.setModal(new TaskDataModel());
        View view = binding.getRoot();
        int id = (Integer) getArguments().get("taskID");
        projectID = (Integer) getArguments().get("projectID");
        taskRequest(id);

        Button btnCompleteTask = view.findViewById(R.id.btnCompleteTask);

        btnCompleteTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                completeTask(id);
            }
        });
        return binding.getRoot();
    }

    void taskRequest(int id) {
        String url = Const.API_SERVER + "/task/" + id;

        JsonObjectRequest taskRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,
                object -> {
                    try {
                        Task task = new Task(
                                object.getInt("taskID"),
                                object.getString("taskName")
                        );
                        task.setStatus(object.getInt("status"));
                        task.setTeamName(object.getString("teamName"));
                        binding.getModal().task.set(task);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        AppController.getInstance().addToRequestQueue(taskRequest);
    }

    void completeTask(int id){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle("Confirm to complete task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        completeTaskRequest(id);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        return;
                    }
                });
        // Create the AlertDialog object and return it
        alertBuilder.create().show();
    }

    void completeTaskRequest(int id) {
        String url = Const.API_SERVER + "/task/" + id + "/complete";
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", Const.username);

        //NOTE: Must Add trailing '/' at end of URL for PUT requests (Android Volley)
        JsonObjectRequest taskRequest = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                response -> {
                    try {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                        Log.d("task_debug", response.toString());
//                        TaskFragmentDirections.ActionNavigationTaskToNavigationProject action = TaskFragmentDirections.actionNavigationTaskToNavigationProject(projectID);
//                        Navigation.findNavController(getView()).navigate(action);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        AppController.getInstance().addToRequestQueue(taskRequest);
    }

}
