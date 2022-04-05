package com.coms3091mc3.projectmanager.ui.task;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import com.coms3091mc3.projectmanager.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskFragment extends Fragment {
    private FragmentTaskBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        binding.setModal(new TaskDataModel());
        int id = (Integer) getArguments().get("taskID");
        taskRequest(id);
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
}
