package com.example.frontendexperiment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.frontendexperiment.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {

    private ActivityMain3Binding binding;
    EditText newUser;
    EditText newPassword;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null){
            Utility.welcomeText = "Welcome, " + ((String)b.get("username"));
        }

        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main3);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        newUser = findViewById(R.id.newUsername);
        newPassword = findViewById(R.id.newPassword);
//        logout = findViewById(R.id.btnLogout);
    }

    public void chgUser(View v){
        Utility.username = newUser.getText().toString();
        Toast.makeText(getApplicationContext(), "Username Changed!", Toast.LENGTH_SHORT).show();
    }

    public void chgPassword(View v){
        Utility.password = newPassword.getText().toString();
        Toast.makeText(getApplicationContext(), "Password Changed!", Toast.LENGTH_SHORT).show();
    }

//    public void logout(){
//        Intent loginPage = new Intent(this, MainActivity.class);
//        loginPage.putExtra("logout", true);
//        loginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        loginPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        loginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(loginPage);
//        finish();
//    }

}