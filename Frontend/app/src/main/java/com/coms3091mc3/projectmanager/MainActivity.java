package com.coms3091mc3.projectmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.utils.Const;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.coms3091mc3.projectmanager.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Button btnDropdownMenu;
    Uri.Builder uri = new Uri.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        btnDropdownMenu = findViewById(R.id.btnMenu);
        btnDropdownMenu.setText(Const.username);
    }

    public void dropdownMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile:
//                archive(item);
                        return true;
                    case R.id.settings:
//                delete(item);
                        return true;
                    case R.id.logout:
                        logout(menuItem);
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
    }

    public void logout(MenuItem item){
        uri = Uri.parse(Const.MOCK_SERVER + "/logout").buildUpon();
        uri.appendQueryParameter("username",Const.username);
//        url = Const.MOCK_SERVER;
//        url+="/login";
//                url = "https://api.androidhive.info/volley/string_response.html";
        StringRequest loginRequest;
        loginRequest = new StringRequest(Request.Method.GET, uri.build().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("login_debug", response.toString());

//                        pBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
                Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("login_debug_error","Error: "+ error.toString());
                // hide the progress dialog
//                        pBar.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            public Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("login_debug_status",String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            };
            //for POST method
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("username",username.getText().toString());
//                params.put("password",password.getText().toString());
//                return params;
//            };
        };
        AppController.getInstance().addToRequestQueue(loginRequest, "logout_request");
    }


}