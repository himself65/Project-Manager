package com.coms3091mc3.projectmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.utils.Const;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String tag_login_req = "login_req";
    String url;
    EditText username, password;
    Button btnLogin, btnRegister;
    ProgressBar pBar;
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

    public void submit(View v) {
        pBar.setVisibility(View.VISIBLE);
        uri = Uri.parse(Const.API_SERVER + "/" + v.getTag().toString()).buildUpon();
        uri.appendQueryParameter("username", username.getText().toString());
        uri.appendQueryParameter("password", password.getText().toString());
//        url = Const.MOCK_SERVER;
//        url+="/login";
//                url = "https://api.androidhive.info/volley/string_response.html";
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username.getText().toString());
        params.put("userPassword", password.getText().toString());
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, uri.build().toString(),
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        Log.d("login_debug", response.toString());
                        btnLogin.setClickable(false);
                        btnRegister.setClickable(false);
                        try{
                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            if(response.getInt("status") == 200 && v.getTag().toString().equals("login")) { //Login Success
                                Intent intentHome = new Intent(LoginActivity.this, MainActivity.class);
                                Const.setUsername(username.getText().toString());
                                pBar.setVisibility(View.INVISIBLE);
                                startActivity(intentHome);
                                finish();
                            }
                            if(response.getInt("status") == 200 && v.getTag().toString().equals("register")){
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                pBar.setVisibility(View.INVISIBLE);
                            }
                        }
                        catch(Exception e){
                            Log.d("login_debug",e.getMessage());
//                            e.printStackTrace();
                        }
                        finally{
                            pBar.setVisibility(View.INVISIBLE);
                            btnLogin.setClickable(false);
                            btnRegister.setClickable(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("login_debug_error", "Error: " + error.toString());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress dialog
                pBar.setVisibility(View.INVISIBLE);
            }
        });
        AppController.getInstance().addToRequestQueue(loginRequest, tag_login_req);

    }
}