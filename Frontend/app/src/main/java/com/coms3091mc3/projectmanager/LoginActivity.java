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
import com.android.volley.toolbox.StringRequest;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.utils.Const;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    StringRequest loginRequest;
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

    public void submit(View v){
        pBar.setVisibility(View.VISIBLE);
        uri = Uri.parse(Const.MOCK_SERVER + "/" + v.getTag().toString()).buildUpon();
        uri.appendQueryParameter("username",username.getText().toString());
        uri.appendQueryParameter("password",password.getText().toString());
//        url = Const.MOCK_SERVER;
//        url+="/login";
//                url = "https://api.androidhive.info/volley/string_response.html";
        loginRequest = new StringRequest(Request.Method.POST, uri.build().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("login_debug", response.toString());
                btnLogin.setClickable(false);
                btnRegister.setClickable(false);
                Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
                if(username.getText().toString().equals("login") && password.getText().toString().equals("test")
                        && v.getTag().toString().equals("login")){ //login if action was login with correct credentials
                    Intent intentHome = new Intent(LoginActivity.this, MainActivity.class);
                    Const.setUsername(username.getText().toString());
                    pBar.setVisibility(View.INVISIBLE);
                    startActivity(intentHome);
                    finish();
                }
                else if(v.getTag().toString().equals("register")){

                    pBar.setVisibility(View.INVISIBLE);
                }
                pBar.setVisibility(View.INVISIBLE);
                btnLogin.setClickable(true);
                btnRegister.setClickable(true);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("login_debug_error","Error: "+ error.toString());
                // hide the progress dialog
                pBar.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            public Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("login_debug_status",String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            };
            //for POST method
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username.getText().toString());
                params.put("password",password.getText().toString());
                return params;
            };
        };
        AppController.getInstance().addToRequestQueue(loginRequest, tag_login_req);

    }
}