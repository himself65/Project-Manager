package com.coms3091mc3.projectmanager.ui.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.TaskActivity;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.Project;
import com.coms3091mc3.projectmanager.data.Task;
import com.coms3091mc3.projectmanager.databinding.FragmentProjectBinding;
import com.coms3091mc3.projectmanager.databinding.FragmentTaskBinding;
import com.coms3091mc3.projectmanager.store.ProjectDataModel;
import com.coms3091mc3.projectmanager.store.TaskDataModel;
import com.coms3091mc3.projectmanager.ui.project.ProjectFragmentDirections;
import com.coms3091mc3.projectmanager.utils.Const;
import com.coms3091mc3.projectmanager.view.AddProjectDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        TextView editDescription = view.findViewById(R.id.descriptionTitle);
        editDescription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                editDesc(view, id);
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
                        JSONObject taskObject = object.getJSONObject("task");
                        Task task = new Task(
                                taskObject.getInt("id"),
                                taskObject.getString("task")
                        );
                        Log.d("TASK_FRAGMENT",object.toString());
                        task.setStatus(taskObject.getInt("status"));
//                        JSONObject project = object.getJSONObject()
                        task.setTeamName(object.getJSONObject("team").getString("teamName"));
                        task.setDescription(taskObject.getString("taskDescription"));
                        binding.getModal().task.set(task);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Log.d("TASK_FRAGMENT",binding.getModal().task.get().getDescription() + "");
                }, error -> {
            Log.d("task_fragment","Task request error : " + error.getMessage());
        });
        AppController.getInstance().addToRequestQueue(taskRequest);
    }

    void completeTask(int taskID){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle("Confirm to complete task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        completeTaskRequest(taskID);
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

    void completeTaskRequest(int taskID) {
        String url = Const.API_SERVER + "/task/" + taskID + "/complete/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", Const.user.getUsername());

        //NOTE: Must Add trailing '/' at end of URL for PUT requests (Android Volley)
        JsonObjectRequest taskRequest = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                response -> {
                    try {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                        Log.d("task_debug", response.toString());
                        binding.textTaskStatus.setText("COMPLETE");
                        binding.textTaskStatus.setBackgroundColor(Color.rgb(0,255,0));
//                        TaskFragmentDirections.ActionNavigationTaskToNavigationProject action = TaskFragmentDirections.actionNavigationTaskToNavigationProject(projectID);
//                        Navigation.findNavController(getView()).navigate(action);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    Log.d("task_debug","Complete task request error : " + error.getMessage());
        });
        AppController.getInstance().addToRequestQueue(taskRequest);
    }

    void editDesc(View view, int taskID){
        Map<String, String> params = new HashMap<String, String>();
        Context context = getContext();

        params.put("id",taskID + "");

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText dialogInput = new EditText(context);
        dialogInput.setLayoutParams(lp);
        alertBuilder.setView(dialogInput);

        alertBuilder.setMessage("Enter description")
                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        params.put("description", dialogInput.getText().toString());
                        Log.d("task_debug","Edit desc: " + dialogInput.getText().toString());
                        editDescRequest(taskID, params, dialogInput.getText().toString());
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
        Log.d("task_fragment","Edit Description clicked : " + taskID);
    }

    void editDescRequest(int id, Map<String, String> params, String desc){
        String url = Const.API_SERVER + "/task/" + id + "/description/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                response -> {
                    Log.d("task_fragment","Edit Description Request: " + response.toString());
                    try{
                        binding.taskDescription.setText(desc);
                        Toast.makeText(getContext(),"Successfully editted description",Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e){
                        Log.d("task_fragment","Edit Description Error : " + e.getMessage());
                        Toast.makeText(getContext(),"Error editing description: " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                },
                error ->{
                    Log.d("task_debug","Edit description error : " + error.getMessage());
                    Toast.makeText(getContext(),"Error editing description: " + error.getMessage(),Toast.LENGTH_SHORT).show();
                });
        AppController.getInstance().addToRequestQueue(request);
    }

}
