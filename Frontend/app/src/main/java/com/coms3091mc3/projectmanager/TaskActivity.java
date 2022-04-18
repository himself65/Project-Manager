package com.coms3091mc3.projectmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

/**
 * The type Task activity.
 */
public class TaskActivity extends AppCompatActivity {

    /**
     * The Btn complete task.
     */
    Button btnCompleteTask;
    /**
     * The Uri.
     */
    Uri.Builder uri = new Uri.Builder();
    /**
     * The Tag task req.
     */
    String tag_task_req = "task_req";
    /**
     * The Task name.
     */
    TextView task_name, /**
     * The Team name.
     */
    team_name, /**
     * The Task description.
     */
    task_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task);

        loadTask();
        btnCompleteTask = findViewById(R.id.btnCompleteTask);
        btnCompleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeTask(view);
            }
        });

        task_name = findViewById(R.id.taskName);
        team_name = findViewById(R.id.teamName);
        task_description = findViewById(R.id.taskDescription);
    }

    /**
     * Load task.
     */
    public void loadTask(){
        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", id); //pass in task id

        taskRequest(params, "load");
    }

    /**
     * Complete task.
     *
     * @param v the v
     */
    public void completeTask(View v){
        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", id); //pass in task id

        taskRequest(params, "complete");
    }

    /**
     * Task request.
     *
     * @param params the params
     * @param tag    the tag
     */
    public void taskRequest(Map<String, String> params, String tag){
        uri = Uri.parse(Const.MOCK_SERVER + "/project/task/" + tag).buildUpon();
//        uri = Uri.parse(Const.API_SERVER + "/project/" + tag).buildUpon();

        JsonObjectRequest taskRequest = new JsonObjectRequest(Request.Method.POST, uri.build().toString(),
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        btnCompleteTask.setClickable(false); //lock the complete task button
//                        btnRegister.setClickable(false);
                        try{
                            if(response.getInt("status") == 200 && tag.equals("complete")) { //complete task request
                                //Reload the page
                                Log.d("task_debug", response.toString());
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            }
                            if(response.getInt("status") == 200 && tag.equals("load")) { //load task request
                                task_name.setText(response.getString("name"));
                                task_description.setText(response.getString("description"));
                                team_name.setText(response.getString("teamName"));
                                if(response.getInt("taskStatus") == 1) { //task completed
                                    TextView task_status = findViewById(R.id.textTaskStatus);
                                    task_status.setBackgroundColor(ContextCompat.getColor(TaskActivity.this,R.color.teal_700));
                                    task_status.setText("Status - Complete");
                                }
                                Log.d("task_debug", response.toString());
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        }
//                        try{
//                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
//                            if(response.getInt("status") == 200 && v.getTag().toString().equals("login")) { //Login Success
//                                Intent intentHome = new Intent(LoginActivity.this, MainActivity.class);
//                                Const.setUsername(username.getText().toString());
//                                pBar.setVisibility(View.INVISIBLE);
//                                startActivity(intentHome);
//                                finish();
//                            }
//                            if(response.getInt("status") == 200 && v.getTag().toString().equals("register")){ //Register Success
//                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
//                                pBar.setVisibility(View.INVISIBLE);
//                            }
//                        }
                        catch(Exception e){
                            Log.d("task_debug",e.getMessage());
//                            e.printStackTrace();
                        }
                        finally{
//                            pBar.setVisibility(View.INVISIBLE);
                            btnCompleteTask.setClickable(true);
//                            btnRegister.setClickable(true);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("task_debug_error", "Error: " + error.toString());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        AppController.getInstance().addToRequestQueue(taskRequest, tag_task_req);
    }
}