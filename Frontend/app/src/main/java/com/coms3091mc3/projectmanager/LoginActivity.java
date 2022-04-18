package com.coms3091mc3.projectmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.User;
import com.coms3091mc3.projectmanager.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Login activity.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * The Tag login req.
     */
    String tag_login_req = "login_req";
    /**
     * The Username.
     */
    EditText username, /**
     * The Password.
     */
    password;
    /**
     * The Btn login.
     */
    Button btnLogin, /**
     * The Btn register.
     */
    btnRegister;
    /**
     * The P bar.
     */
    ProgressBar pBar;
    /**
     * The Uri.
     */
    Uri.Builder uri = new Uri.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        pBar = findViewById(R.id.progressBar);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    /**
     * Submit.
     *
     * @param v the v
     */
    public void submit(View v) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username.getText().toString());
        params.put("userPassword", password.getText().toString());

        if(v.getTag().toString().equals("register")){ //Action Register
//            pBar.setVisibility(View.VISIBLE);
            Log.d("login_debug", "Popup window");
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            final EditText dialogInput = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            dialogInput.setLayoutParams(lp);
            alertBuilder.setView(dialogInput);
            alertBuilder.setMessage("Enter a name")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(dialogInput.getText().toString().length() < 4){ //at least 4 characters
                                Toast.makeText(getApplicationContext(), "Name must be at least 4 characters", Toast.LENGTH_LONG).show();
                                pBar.setVisibility(View.INVISIBLE);
                                return;
                            }
                            //insert input as full name parameter for register function
                            params.put("full_name", dialogInput.getText().toString());
                            registerRequest(params);
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            pBar.setVisibility(View.INVISIBLE);
                            return;
                        }
                    })
                    .setOnCancelListener(
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            //When you touch outside of dialog bounds,
                            //the dialog gets canceled and this method executes.
                            pBar.setVisibility(View.INVISIBLE);
                            return;
                        }
                    });
            // Create the AlertDialog object and return it
            alertBuilder.create().show();

        }

        if(v.getTag().toString().equals("login")) { //Action Login
            pBar.setVisibility(View.VISIBLE);
            loginRequest(params);
        }

    }

    /**
     * Login request.
     *
     * @param params the params
     */
    void loginRequest(Map<String, String> params){
        //NOTE: Must Add trailing '/' at end of URL for PUT requests (Android Volley)
        String url = Const.API_SERVER + "/login/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                new JSONObject(params),
                response -> {
                    try {
                        btnLogin.setClickable(false);
                        btnRegister.setClickable(false);
                        Log.d("login_debug",response.getString("message"));
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();

                        Intent intentHome = new Intent(LoginActivity.this, MainActivity.class);
                        Const.user = new User(response.getInt("user_id"), response.getString("username"), response.getString("fullname"));
                        pBar.setVisibility(View.INVISIBLE);
                        startActivity(intentHome);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finally{
                        pBar.setVisibility(View.INVISIBLE);
                        btnLogin.setClickable(true);
                        btnRegister.setClickable(true);
                    }

                },
                error -> {
                    VolleyLog.d("login_debug_error", "Error: " + error.toString());
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    // hide the progress dialog
                    pBar.setVisibility(View.INVISIBLE);
                }
        );
        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * Register request.
     *
     * @param params the params
     */
//NOTE: Must Add trailing '/' at end of URL for PUT requests (Android Volley)
    void registerRequest(Map<String, String> params) { //Register User
        String url = Const.API_SERVER + "/register";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params),
                response -> {
                    try {
                        Log.d("register_debug",response.getString("message"));
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
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